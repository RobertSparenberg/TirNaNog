package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;

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
    private final ModuleConfigRepository moduleConfigRepository;
    private final AutomationControl automationControl;
    private final ServerSocket serverSocket;
    private final String ownName;

    private boolean stopRequested = false;

    public NotificationService(OwnConfigService ownConfigService, ModuleConfigRepository moduleConfigRepository, AutomationControl automationControl) throws IOException {
        logger.trace("NotificationService Initializing");
        this.moduleConfigRepository = moduleConfigRepository;
        this.automationControl = automationControl;
        serverSocket = new ServerSocket(NOTIFICATION_PORT);

        ModuleConfig ownConfig = moduleConfigRepository.findByIp("localhost");
        this.ownName = ownConfig.getName();
        logger.trace("NotificationService initialized");
    }

    /*
    private void receiveCall() {
        while(!stopRequested) {
            try {
                Socket socket = this.serverSocket.accept();
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                logger.trace("received telephone xml: " + stringBuilder.toString());
                //todo: find a simpler way to unmarshall the string
                ModuleConfig moduleConfig = JAXB.unmarshal(new StringReader(stringBuilder.toString().trim()), ModuleConfig.class);
                if(!ownName.equals(moduleConfig.getName())) {
                    moduleConfig.setLastMessageTimestamp(System.currentTimeMillis());
                    moduleConfig.setIp(socket.getInetAddress().toString());
                    logger.trace("Broadcaster saving moduleconfig: " + moduleConfig.toString());
                    moduleConfigRepository.save(moduleConfig);
                }
            } catch(SocketTimeoutException ignored) {
            } catch(Exception e) {
                logger.error("Error accepting incoming socket", e);
            }
        }
    }

    private void ringOtherModule() throws IOException {
        ModuleConfig moduleToRing = scheduledCalls.poll();
        if(moduleToRing != null) {
            logger.trace("Telephone is ringing other module: " + moduleToRing.getIp());
            clientSocket.connect(new InetSocketAddress(moduleToRing.getIp(), TELEPHONE_PORT));
            clientSocket.getOutputStream().write(marshaledOwnConfig);
            clientSocket.close();
        }
    }

    public void destroyGracefully() {
        this.stopRequested = true;
        try {
            serverSocket.close();
        } catch(Exception ignored) {
        }
        try {
            inboundThread.join(SOCKET_TIMEOUT+1000);
        } catch(Exception ignored) {
        }
    }
    */

    public void onLocalParameter(String capabilityName, Parameter parameter) {
        automationControl.onParameter(ownName + '.' + capabilityName + '.' + parameter.getName(), parameter);
    }

    public void onModuleDiscovery(ModuleConfig moduleConfig) {
        //todo: on module discovery, tell it which parameters of it this module subscribes to (use TCP to ensure delivery)
        //it will discover this module when it broadcasts again, so no need to worry about it
    }
}
