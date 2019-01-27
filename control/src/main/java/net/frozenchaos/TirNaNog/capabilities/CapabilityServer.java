package net.frozenchaos.TirNaNog.capabilities;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Service
public class CapabilityServer {
    private static final long CAPABILITY_SHUTDOWN_TIMEOUT = 2000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AutomationControl automationControl;
    private final OwnCapabilityApplicationsService capabilityApplicationsService;
    private final Thread acceptConnectionsThread;
    private ServerSocket serverSocket;

    private boolean stopRequested = false;

    @Autowired
    public CapabilityServer(AutomationControl automationControl, OwnCapabilityApplicationsService capabilityApplicationsService) {
        this.capabilityApplicationsService = capabilityApplicationsService;
        logger.info("Capability Server initializing");
        this.automationControl = automationControl;
        this.acceptConnectionsThread = new Thread(this::acceptConnections);
        this.acceptConnectionsThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        logger.info("Capability Server initialized");
    }

    private void acceptConnections() {
        try {
            serverSocket = new ServerSocket(CapabilityClient.CAPABILITIES_PORT);
            logger.trace("Capability server socket created");
            while(!stopRequested) {
                try {
                    logger.trace("Waiting for a connection to the capability server socket...");
                    Socket capabilitySocket = serverSocket.accept();
                    logger.trace("Registering new Capability application");
                    startCapabilityThread(capabilitySocket);
                } catch(SocketTimeoutException ignored) {
                }
            }
        } catch(Exception e) {
            if(!stopRequested) {
                logger.error("Fatal error in capability server socket: "+e.toString());
            }
            try {
                serverSocket.close();
            } catch(IOException ignored) {
            }
        }
    }

    private void startCapabilityThread(Socket capabilitySocket) {
        CapabilityThread capabilityThread;
        if(!stopRequested) {
            capabilityThread = new CapabilityThread(capabilitySocket, capabilityApplicationsService, automationControl);
            capabilityApplicationsService.addCapabilityApplication(capabilityThread);
            capabilityThread.start();
        }
    }

    private void shutdown() {
        logger.debug("Shutting down capability server");
        stopRequested = true;
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
            acceptConnectionsThread.join(CAPABILITY_SHUTDOWN_TIMEOUT);
        } catch(Exception ignored) {
        }
        capabilityApplicationsService.setStopRequested();
        for(CapabilityThread capability : capabilityApplicationsService.getRunningCapabilities()) {
            capabilityApplicationsService.removeCapabilityApplication(capability);
        }
    }
}