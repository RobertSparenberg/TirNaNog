package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.automation.ParameterListener;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.ModuleConfigEventListener;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
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
import java.util.ArrayList;
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
public class NotificationService implements ModuleConfigEventListener {
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int DELAY_BETWEEN_NOTIFICATIONS = 2000;
    private static final int RETRY_ATTEMPTS = 3;
    private static final int NOTIFICATION_PORT = 42002;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ModuleConfigRepository moduleConfigRepository;
    private final List<ParameterListener> listeners = new ArrayList<>();
    private final ServerSocket notificationServerSocket;
    private final String ownName;
    private final Thread exchangeListenThread;
    private final Timer timer;

    private final ConcurrentHashMap<String, Long> parameterTimestamps = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<String>> parameterRegistrations = new ConcurrentHashMap<>();

    private boolean stopRequested = false;

    public NotificationService(OwnConfigService ownConfigService, ModuleConfigRepository moduleConfigRepository, Timer timer) throws IOException {
        this.moduleConfigRepository = moduleConfigRepository;
        logger.trace("NotificationService Initializing");
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
                    for(ParameterListener listener : listeners) {
                        listener.onParameter(parameter.getQualifier(), parameter);
                    }
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
            ModuleConfig destination = moduleConfigRepository.findByName(destinationName);
            if(destination != null) {
                timer.addTask(new ParameterSendTask(parameterToSend, destination));
            }
        }
    }

    public void onLocalParameter(String capabilityName, Parameter parameter) {
        String qualifier = ownName + '.' + capabilityName + '.' + parameter.getName();
        for(ParameterListener listener : listeners) {
            listener.onParameter(qualifier, parameter);
        }
    }

    @Override
    public void onModuleConfigSave(ModuleConfig moduleConfig) {
        for(String parameter : moduleConfig.getSubscribedParameters()) {
            if(parameter.startsWith(ownName)) {
                if(!parameterRegistrations.containsKey(parameter)) {
                    parameterRegistrations.put(parameter, new CopyOnWriteArrayList<>());
                }
                List<String> registeredModules = parameterRegistrations.get(parameter);
                if(!registeredModules.contains(moduleConfig.getName())) {
                    registeredModules.add(moduleConfig.getName());
                }
            }
        }
    }

    @Override
    public void onModuleConfigRemove(ModuleConfig moduleConfig) {
        for(String parameter : moduleConfig.getSubscribedParameters()) {
            List<String> registeredModules = parameterRegistrations.get(parameter);
            if(registeredModules != null) {
                registeredModules.remove(moduleConfig.getName());
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

    public void addListener(ParameterListener listener) {
        listeners.add(listener);
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
