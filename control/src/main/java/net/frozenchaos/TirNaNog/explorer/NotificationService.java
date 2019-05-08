package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.utils.ScheduledTask;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The NotificationService is responsible for sending notifications to specific modules and receiving them.
 * This is used to subscribe to parameters from other modules, making them send a notification to this module
 * when one of those parameters is fired.
 * It will receive subscribed parameters and send parameters to other modules if they subscribed to it.
 */
@Service
public class NotificationService {
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int DELAY_BETWEEN_NOTIFICATIONS = 2000;
    private static final int RETRY_ATTEMPTS = 3;
    private static final int NOTIFICATION_PORT = 42002;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AutomationControl automationControl;
    private final ServerSocket notificationServerSocket;
    private final String ownName;
    private final Thread exchangeListenThread;
    private final Timer timer;

    private final ConcurrentHashMap<String, Long> parameterTimestamps = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<String>> parameterRegistrations = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ModuleConfig> moduleListings = new ConcurrentHashMap<>();

    private boolean stopRequested = false;

    public NotificationService(OwnConfigService ownConfigService, AutomationControl automationControl, Timer timer) throws IOException {
        logger.trace("NotificationService Initializing");
        this.automationControl = automationControl;
        this.timer = timer;
        notificationServerSocket = new ServerSocket(NOTIFICATION_PORT);

        ModuleConfig ownConfig = ownConfigService.getOwnConfig();
        this.ownName = ownConfig.getName();

        exchangeListenThread = new Thread() {
            @Override
            public void run() {
                receiveNotifications();
            }
        };
        exchangeListenThread.start();
        logger.trace("NotificationService initialized");
    }

    private void receiveNotifications() {
        while(!stopRequested) {
            try {
                Socket socket = notificationServerSocket.accept();
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                Parameter parameter = JAXB.unmarshal(new StringReader(stringBuilder.toString()), Parameter.class);
                logger.trace("Received notification xml: " + stringBuilder.toString());
                if(!ownName.equals(parameter.getName())) {
                    automationControl.onParameter(parameter.getQualifier(), parameter);
                }
            } catch(Exception e) {
                logger.error("Error processing incoming notification", e);
            }
        }
    }

    private void sendParameterNotification(Parameter parameterToSend) throws IOException {
        parameterToSend.setQualifier(ownName + ".parameter." + parameterToSend.getName());
        parameterToSend.setTimestamp(timer.getTime());
        parameterTimestamps.put(parameterToSend.getQualifier(), parameterToSend.getTimestamp());
        for(String destinationName : parameterRegistrations.get(parameterToSend.getQualifier())) {
            ModuleConfig destination = moduleListings.get(destinationName);
            if(destination != null) {
                timer.addTask(new ParameterSendTask(parameterToSend, destination));
            }
        }
    }

    public void onLocalParameter(String capabilityName, Parameter parameter) {
        automationControl.onParameter(ownName + '.' + capabilityName + '.' + parameter.getName(), parameter);
    }

    public void onModuleDiscovery(ModuleConfig moduleConfig) {
        //todo: right now modules can register for parameters, but they're never cleaned up.. add cleanup
        moduleListings.put(moduleConfig.getName(), moduleConfig);
        for(String parameter : moduleConfig.getSubscribedParameters()) {
            if(parameter.startsWith(ownName)) {
                if(!parameterRegistrations.containsKey(parameter)) {
                    parameterRegistrations.put(parameter, new CopyOnWriteArrayList<>());
                }
                List<String> parameterRegistrations = this.parameterRegistrations.get(parameter);
                if(!parameterRegistrations.contains(moduleConfig.getName())) {
                    parameterRegistrations.add(moduleConfig.getName());
                }
            }
        }
    }

    public void destroyGracefully() {
        this.stopRequested = true;
        try {
            notificationServerSocket.close();
        } catch(Exception ignored) {
        }
        try {
            exchangeListenThread.join(SOCKET_TIMEOUT+1000);
        } catch(Exception ignored) {
        }
    }

    /**
     * Job wrapper that this job should be sent.
     */
    private class ParameterSendTask extends ScheduledTask {
        Parameter parameterToSend;
        ModuleConfig destination;
        int attempts = 0;
        int delayBeforeNextAttempt = 0;

        ParameterSendTask(Parameter parameterToSend, ModuleConfig destination) {
            super(0);
            this.parameterToSend = parameterToSend;
            this.destination = destination;
        }

        @Override
        public void doTask() {
            if(parameterTimestamps.get(parameterToSend.getQualifier()) <= parameterToSend.getTimestamp()) {
                //only send if this is the latest parameter value to send
                try {
                    Socket socket = new Socket(destination.getIp(), NOTIFICATION_PORT);
                    JAXB.marshal(parameterToSend, socket.getOutputStream());
                    socket.close();
                } catch(Exception e) {
                    logger.error("Error sending notification '"+parameterToSend.getQualifier()+"' to '"+destination.getName()+':'+destination.getIp()+"' - "+e.toString());
                    attempts += 1;
                    if(attempts < RETRY_ATTEMPTS) {
                        delayBeforeNextAttempt += DELAY_BETWEEN_NOTIFICATIONS;
                        timer.addTask(this);
                    }
                }
            }
        }
    }
}
