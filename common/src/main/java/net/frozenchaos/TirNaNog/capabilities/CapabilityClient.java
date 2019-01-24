package net.frozenchaos.TirNaNog.capabilities;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Client class for connecting to the local TirNaNog application.
 * All Capability applications should use it to communicate with the TirNaNog network.
 */
public class CapabilityClient extends Thread {
    static final int CAPABILITIES_PORT = 42000;

    private final Logger logger = LoggerFactory.getLogger(CapabilityClient.class);
    private final CapabilityApplication profile;
    private final List<ParameterProcessor> processors = new ArrayList<>();
    private Socket socket;
    private boolean stopRequested = false;

    public CapabilityClient(CapabilityApplication profile, List<ParameterProcessor> processors) throws IOException {
        super("Socket reading thread");
        this.processors.addAll(processors);
        this.profile = profile;
    }

    @Override
    public void run() {
        while(!stopRequested) {
            try {
                logger.info("Connecting to the local TirNaNog control application...");
                socket = new Socket("localhost", CAPABILITIES_PORT);
                JAXB.marshal(profile, socket.getOutputStream());
                logger.debug("Connected; capability profile sent");
            } catch(IOException e) {
                try {
                    logger.info("Failed to connect to the local TirNaNog control application");
                    socket = null;
                    Thread.sleep(5000);
                } catch(InterruptedException ignored) {
                }
            }
            if(!stopRequested && socket != null) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    boolean nextLineHasTheClassName = false;
                    String closingTag = null;
                    String line;

                    while(!stopRequested) {
                        if((line = reader.readLine()) != null) {
                            line = line.trim();
                            if(line.startsWith("<?xml")) {
                                closingTag = null;
                                nextLineHasTheClassName = true;
                                clearStringBuilder(stringBuilder);
                                stringBuilder.append(line);
                            } else if(nextLineHasTheClassName) {
                                closingTag = "</"+line.substring(1, line.length()-1)+'>';
                                nextLineHasTheClassName = false;
                                stringBuilder.append(line);
                            } else if(closingTag != null) {
                                stringBuilder.append(line);
                                if(closingTag.equals(line)) {
                                    processXml(stringBuilder.toString());
                                    clearStringBuilder(stringBuilder);
                                    closingTag = null;
                                }
                            }
                        }
                    }
                } catch(Exception e) {
                    if(!stopRequested) {
                        System.out.println("Error in capability thread: "+e.toString());
                    } else {
                        stopGracefully();
                    }
                }
            }
        }
    }

    private void processXml(String xml) {
        try {
            Parameter parameter = JAXB.unmarshal(new StringReader(xml), Parameter.class);
            for(ParameterProcessor processor : processors) {
                if(processor.canProcess(parameter)) {
                    processor.processParamter(parameter);
                }
            }
        } catch(Exception e) {
            System.out.println("Error unmarshalling capability message: '"+xml+"', "+e.toString());
        }
    }

    public synchronized void stopGracefully() {
        stopRequested = true;
        if(socket != null) {
            try {
                socket.close();
            } catch(IOException ignored) {
            }
        }
    }

    private void clearStringBuilder(StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
    }
}
