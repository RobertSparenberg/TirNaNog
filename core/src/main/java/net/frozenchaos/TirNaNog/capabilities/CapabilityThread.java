package net.frozenchaos.TirNaNog.capabilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

class CapabilityThread extends Thread {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CapabilityServer capabilityServer;

    private Socket socket;
    private String capabilityName = "NameNotSetYet";
    private boolean stopRequested;

    CapabilityThread(Socket socket, CapabilityServer capabilityServer) {
        this.socket = socket;
        this.capabilityServer = capabilityServer;
    }

    @Override
    public void run() {
        try {
            socket.getOutputStream();
            while(!stopRequested) {
                //todo: read socket for regular messages
            }
        } catch(Exception e) {
            logger.error("Error on CapabilityThread with name '"+capabilityName+"'", e);
            capabilityServer.removeCapability(this);
        }
    }

    public void stopGracefully() {
        stopRequested = true;
        try {
            socket.close();
        } catch(IOException ignored) {
        }
    }
}
