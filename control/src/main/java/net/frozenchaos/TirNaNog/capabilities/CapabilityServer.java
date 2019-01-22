package net.frozenchaos.TirNaNog.capabilities;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CapabilityServer implements ApplicationListener<ContextRefreshedEvent> {
    private static final int SOCKET_TIMEOUT = 5000;
    private static final long CAPABILITY_SHUTDOWN_TIMEOUT = 2000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AutomationControl automationControl;
    private final List<CapabilityThread> runningCapabilities = new ArrayList<>();
    private final Thread acceptConnectionsThread;
    private ServerSocket serverSocket;

    private AtomicBoolean stopRequested = new AtomicBoolean(false);

    @Autowired
    public CapabilityServer(AutomationControl automationControl) {
        logger.info("Capability Server initializing");
        this.automationControl = automationControl;
        this.acceptConnectionsThread = new Thread(this::acceptConnections);
        this.acceptConnectionsThread.start();
        logger.info("Capability Server initialized");
    }

    public List<CapabilityApplication> getCapabilityApplications() {
        synchronized(runningCapabilities) {
            List<CapabilityApplication> capabilityApplications = new ArrayList<>(runningCapabilities.size());
            for(CapabilityThread thread : runningCapabilities) {
               capabilityApplications.add(thread.getProfile());
            }
            return capabilityApplications;
        }
    }

    private void acceptConnections() {
        try {
            serverSocket = new ServerSocket(CapabilityClient.CAPABILITIES_PORT);
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);
            while(!stopRequested.get()) {
                try {
                    Socket capabilitySocket = serverSocket.accept();
                    startCapabilityThread(capabilitySocket);
                } catch(SocketTimeoutException ignored) {
                }
            }
        } catch(Exception e) {
            try {
                serverSocket.close();
            } catch(IOException ignored) {
            }
        }
    }

    private void startCapabilityThread(Socket capabilitySocket) {
        logger.info("Registering new Capability application");
        CapabilityThread capabilityThread;
        synchronized(runningCapabilities) {
            if(!stopRequested.get()) {
                capabilityThread = new CapabilityThread(capabilitySocket, this, automationControl);
                runningCapabilities.add(capabilityThread);
                capabilityThread.start();
            }
        }
    }

    void removeCapability(CapabilityThread capability) {
        if(!this.stopRequested.get()) {
            synchronized(runningCapabilities) {
                runningCapabilities.remove(capability);
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        stopRequested.set(true);
        try {
            acceptConnectionsThread.join(SOCKET_TIMEOUT + 1000);
        } catch(Exception ignored) {
        }
        synchronized(runningCapabilities) {
            for(int i = runningCapabilities.size()-1; i >= 0; i--) {
                CapabilityThread capability = runningCapabilities.get(i);
                capability.stopGracefully();
                try {
                    capability.join(CAPABILITY_SHUTDOWN_TIMEOUT);
                } catch(InterruptedException ignored) {
                }
                runningCapabilities.remove(i);
            }
        }
    }
}