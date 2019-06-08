/**
 * Main class for the CatCoop capabilities for TirNaNog
 * @author imahilus
 */
package net.frozenchaos.HardwareInterfacing.arduino;


import com.fazecast.jSerialComm.SerialPort;
import net.frozenchaos.HardwareInterfacing.configuration.Configuration;
import net.frozenchaos.HardwareInterfacing.configuration.PinDefinition;
import net.frozenchaos.TirNaNog.capabilities.Capability;
import net.frozenchaos.TirNaNog.capabilities.ParameterProcessor;
import net.frozenchaos.TirNaNog.module.TirNaNogCapabilityBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArduinoInterface extends TirNaNogCapabilityBase {
    private final Logger logger = LoggerFactory.getLogger(ArduinoInterface.class);
    private boolean stopRequested = false;
    private final Configuration config;

    public ArduinoInterface() throws Exception {
        config = JAXB.unmarshal(new File("config.xml"), Configuration.class);
        Thread arduinoFinder = new Thread(this::scanPorts);
    }

    @Override
    protected String getName() {
        return "Default";
    }

    @Override
    protected List<Capability> getCapabilities() {
        List<Capability> capabilities = new ArrayList<>();
//        capabilities.add(createCapability("Door", doorInputParameterDefinition));
//        logger.info("Found " + capabilities.size() + " capabilities");
        return capabilities;
    }

    @Override
    protected List<ParameterProcessor> getParameterProcessors() {
        List<ParameterProcessor> processors = new ArrayList<>();
//        processors.add(new ParameterProcessor(doorInputParameterDefinition) {
//            @Override
//            public void processParamter(Parameter parameter) {
//                EnumParameter enumParameter = (EnumParameter) parameter;
//                logger.info("New value received for parameter '" + doorInputParameterDefinition.getName() + "' value: '" + enumParameter.getValue() + '\'');
//            }
//        });
//        logger.info("Found " + processors.size() + " parameter processors");
        return processors;
    }

    @Override
    protected void onShutdown() {
        stopRequested = true;
    }

    private void scanPorts() {
        while(!stopRequested) {
            for(SerialPort port : SerialPort.getCommPorts()) {
                try {
                    List<PinDefinition> arduinoPins = config.getPinDefinitions().stream()
                            .filter(pX -> pX.getBoard() == PinDefinition.Board.ARDUINO).collect(Collectors.<PinDefinition>toList());
                    new ArduinoClient(port, arduinoPins);
                } catch(Exception ignored) {
                }
            }
            try {
                Thread.sleep(500);
            } catch(Exception ignored) { }
        }
    }
}
