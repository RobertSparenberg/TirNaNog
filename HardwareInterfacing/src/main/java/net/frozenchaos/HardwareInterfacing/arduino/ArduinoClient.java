package net.frozenchaos.HardwareInterfacing.arduino;

import com.fazecast.jSerialComm.SerialPort;
import net.frozenchaos.HardwareInterfacing.configuration.ActuatorPinDefinition;
import net.frozenchaos.HardwareInterfacing.configuration.PinDefinition;
import net.frozenchaos.HardwareInterfacing.configuration.SensorPinDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ArduinoClient {
    private static final String CONFIG_ACTUATOR_DEFIINITION = "ACT";
    private static final String CONFIG_SENSOR_DEFIINITION = "SNS";
    private static char MESSAGE_SEPARATOR = ':';
    private static char MESSAGE_END = '\n';
    private static final String HANDSHAKE_INIT = "TirNaNog Arduino Init" + MESSAGE_END;
    private static final String HANDSHAKE_ACCEPT = "TirNaNog Arduino Accept" + MESSAGE_END;

    private final Logger logger = LoggerFactory.getLogger(ArduinoInterface.class);
    private final SerialPort port;
    private final List<PinDefinition> pins;
    private boolean stopRequested = false;
    private boolean acceptRequested = false;
    private boolean acceptedReceived = false;
    private boolean configured = false;
    private long messageCounter = 0;

    public ArduinoClient(SerialPort port, List<PinDefinition> pins) {
        this.port = port;
        this.pins = pins;
        Thread initThread = new Thread(this::init);
        initThread.start();
    }

    public boolean isReady() {
        return configured;
    }

    public void stop() {
        stopRequested = true;
    }

    private void init() {
        logger.trace("Establishing connection with arduino on port: " + port.getSystemPortName());
        port.setBaudRate(9600);
        port.setParity(SerialPort.NO_PARITY);
        port.setNumStopBits(1);
        port.setNumDataBits(8);
        Thread receiveHandshakeThread = new Thread(this::receiveHandshake);
        Thread sendHandshakeThread = new Thread(this::sendHandshake);
        receiveHandshakeThread.start();
        sendHandshakeThread.start();
        while(!stopRequested && !(acceptRequested && acceptedReceived)) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ignored) { }
        }
        configure();
    }

    private void sendHandshake() {
        OutputStream outputStream = port.getOutputStream();
        while(!stopRequested && !(acceptRequested && acceptedReceived)) {
            if(acceptRequested) {
                if(!sendMessage(outputStream, HANDSHAKE_ACCEPT)) {
                    return;
                }
            } else {
                sendMessage(outputStream, HANDSHAKE_INIT);
            }
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ignored) { }
        }
    }

    private void receiveHandshake() {
        InputStream inputStream = port.getInputStream();
        while(!stopRequested && !(acceptRequested && acceptedReceived)) {
            String message = receiveMessage(inputStream);
            if(HANDSHAKE_ACCEPT.equals(message)) {
                acceptedReceived = true;
            } else if(HANDSHAKE_INIT.equals(message)) {
                acceptRequested = true;
            }
        }
    }

    private void configure() {
        if(pins == null) {
            return;
        }
        OutputStream outputStream = port.getOutputStream();
        InputStream inputStream = port.getInputStream();

        try {
            while(inputStream.available() > 0) {
                receiveMessage(inputStream);
            }
        } catch(IOException e) {
            logger.error("Error trying to configure Arduino: " + e.toString());
            stop();
        }

        long messageId;
        String acceptedReply;
        for(PinDefinition pin : pins) {
            messageId = messageCounter++;
            acceptedReply = messageId + MESSAGE_SEPARATOR + "OK";
            if(pin instanceof SensorPinDefinition) {
                String configMessage = messageId + MESSAGE_SEPARATOR
                        + MessageType.CONFIG.toString() + MESSAGE_SEPARATOR
                        + pin.getPinNumber() + MESSAGE_SEPARATOR
                        + CONFIG_SENSOR_DEFIINITION + MESSAGE_SEPARATOR
                        + ((SensorPinDefinition) pin).getValueMultiplier()
                        + MESSAGE_END;
                while(true) {
                    sendMessage(outputStream, configMessage);
                    if(acceptedReply.equals(receiveMessage(inputStream))) {
                        break;
                    }
                }
            } else if(pin instanceof ActuatorPinDefinition) {
                String configMessage = messageId + MESSAGE_SEPARATOR
                       + MessageType.CONFIG.toString() + MESSAGE_SEPARATOR
                       + pin.getPinNumber() + MESSAGE_SEPARATOR
                       + CONFIG_ACTUATOR_DEFIINITION + MESSAGE_SEPARATOR
                       + ((ActuatorPinDefinition) pin).getPinSignalType().toString()
                       + MESSAGE_END;
                while(true) {
                    sendMessage(outputStream, configMessage);
                    if(acceptedReply.equals(receiveMessage(inputStream))) {
                        break;
                    }
                }
            }
        }
        //final 'config done' message to get the all clear
        messageId = messageCounter++;
        acceptedReply = messageId + MESSAGE_SEPARATOR + "DONE";
        String message = messageId + MESSAGE_SEPARATOR
                + MessageType.CONFIG.toString() + MESSAGE_SEPARATOR
                + "DONE" + MESSAGE_END;
        while(true) {
            sendMessage(outputStream, message);
            if(acceptedReply.equals(receiveMessage(inputStream))) {
                configured = true;
                break;
            }
        }
    }

    private boolean sendMessage(OutputStream outputStream, String message) {
        try {
            outputStream.write(message.getBytes());
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    private String receiveMessage(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        while(!stopRequested) {
            try {
                if(inputStream.available() > 0) {
                    char read = (char) inputStream.read();
                    if(read == '\n') {
                        return sb.toString();
                    } else {
                        sb.append(read);
                    }
                }
            } catch(IOException e) {
                break;
            }
        }
        return "";
    }

    private enum MessageType {
        CONFIG, COMMAND
    }
}
