package net.frozenchaos.TirNaNog.capabilities;

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
    private final Socket socket;
    private final List<ParameterProcessor> processors = new ArrayList<>();
    private boolean stopRequested = false;

    public CapabilityClient(CapabilityApplication profile) throws IOException {
        this(profile, new ArrayList<>());
    }

    public CapabilityClient(CapabilityApplication profile, List<ParameterProcessor> processors) throws IOException {
        super("Socket reading thread");
        this.processors.addAll(processors);
        socket = new Socket("localhost", 42000);
        JAXB.marshal(profile, socket.getOutputStream());
    }

    @Override
    public void run() {
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
                        closingTag = "</" + line.substring(1, line.length()-1) + '>';
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
            System.out.println("Error in capability thread: " + e.toString());
        }
    }

    private void processXml(String xml) {
        try {
            ParameterSet parameterSet = JAXB.unmarshal(new StringReader(xml), ParameterSet.class);
            for(Parameter parameter : parameterSet.getParameters()) {
                if(parameter instanceof IntegerParameter) {
                    for(ParameterProcessor processor : processors) {
                        if(processor instanceof IntegerParameterProcessor) {
                            ((IntegerParameterProcessor) processor).processParameter((IntegerParameter) parameter);
                        }
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Error unmarshalling capability message: '"+xml+"', "+e.toString());
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
}
