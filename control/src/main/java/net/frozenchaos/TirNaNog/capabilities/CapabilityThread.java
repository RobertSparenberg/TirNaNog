package net.frozenchaos.TirNaNog.capabilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;

class CapabilityThread extends Thread {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CapabilityServer capabilityServer;

    private Socket socket;
    private CapabilityApplication profile = null;
    private boolean stopRequested;

    CapabilityThread(Socket socket, CapabilityServer capabilityServer) {
        this.socket = socket;
        this.capabilityServer = capabilityServer;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            boolean nextLineHasTheClassName = false;
            String className = null;
            String closingTag = null;
            String line;

            //JAXB.unmarshal(new StringReader(stringBuilder.toString().trim()), ModuleConfig.class);
            while(!stopRequested) {
                if((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(line.startsWith("<?xml")) {
                        closingTag = null;
                        nextLineHasTheClassName = true;
                        clearStringBuilder(stringBuilder);
                        stringBuilder.append(line);
                    } else if(nextLineHasTheClassName) {
                        className = line.substring(1, line.length()-1);
                        closingTag = "</" + className + '>';
                        nextLineHasTheClassName = false;
                        stringBuilder.append(line);
                    } else if(closingTag != null) {
                        stringBuilder.append(line);
                        if(closingTag.equals(line)) {
                            processXml(stringBuilder.toString(), className);
                            clearStringBuilder(stringBuilder);
                            className = null;
                            closingTag = null;
                        }
                    }
                }
            }
        } catch(Exception e) {
            logger.error("Error on CapabilityThread with name '"+getCapabilityName()+"'", e);
            capabilityServer.removeCapability(this);
        }
    }

    private void processXml(String xml, String className) {
        try {
            if("CapabilityIdentification".equals(className)) {
                synchronized(profile) {
                    profile = JAXB.unmarshal(new StringReader(xml), CapabilityApplication.class);
                }
            }
        } catch(Exception e) {
            logger.error("Error unmarshalling capability message", xml, e);
        }
    }

    public void stopGracefully() {
        stopRequested = true;
        try {
            socket.close();
        } catch(IOException ignored) {
        }
    }

    private void clearStringBuilder(StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
    }

    public CapabilityApplication getProfile() {
        synchronized(profile) {
            return profile;
        }
    }

    private String getCapabilityName() {
        CapabilityApplication profile = getProfile();
        if(profile != null) {
            return profile.getName();
        } else {
            return "NoProfileKnownYet";
        }
    }
}
