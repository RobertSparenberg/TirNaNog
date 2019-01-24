/**
 * Main class for the CatCoop capabilities for TirNaNog
 * @author imahilus
 */
package net.frozenchaos.CatCoop;


import net.frozenchaos.TirNaNog.capabilities.Capability;
import net.frozenchaos.TirNaNog.capabilities.ParameterProcessor;
import net.frozenchaos.TirNaNog.capabilities.parameters.EnumParameter;
import net.frozenchaos.TirNaNog.capabilities.parameters.EnumParameterDefinition;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.capabilities.parameters.ParameterDefinition;
import net.frozenchaos.TirNaNog.module.TirNaNogCapabilityBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CatCoop extends TirNaNogCapabilityBase {
    private Logger logger = LoggerFactory.getLogger(CatCoop.class);
    private EnumParameterDefinition doorInputParameterDefinition = new EnumParameterDefinition("Open", ParameterDefinition.ParameterType.INPUT, "Open", "Close");

    public CatCoop() throws Exception {
    }

    @Override
    protected List<Capability> getCapabilities() {
        List<Capability> capabilities = new ArrayList<>();
        capabilities.add(createCapability("Door", doorInputParameterDefinition));
        return capabilities;
    }

    @Override
    protected List<ParameterProcessor> getParameterProcessors() {
        List<ParameterProcessor> processors = new ArrayList<>();
        processors.add(new ParameterProcessor(doorInputParameterDefinition) {
            @Override
            public void processParamter(Parameter parameter) {
                EnumParameter enumParameter = (EnumParameter) parameter;
                logger.info("New value received for parameter '" + doorInputParameterDefinition.getName() + "' value: '" + enumParameter.getValue() + '\'');
            }
        });
        return processors;
    }

    @Override
    protected void onShutdown() {
    }

    private Capability createCapability(String name, ParameterDefinition... inputParameters) {
        List<ParameterDefinition> parameters = new ArrayList<>();
        Collections.addAll(parameters, inputParameters);
        return new Capability(name, parameters);
    }
}
