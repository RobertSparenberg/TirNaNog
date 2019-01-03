/**
 * Main class for the CatCoop capability for TirNaNog
 * @author imahilus
 */
package net.frozenchaos.CatCoop;


import net.frozenchaos.TirNaNog.capabilities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] varargs) throws InterruptedException, IOException {
        List<Capability> capabilities = new ArrayList<>();
        capabilities.add(createCapability("Door", new IntegerParameterDefinition("Open", 0, 1, 1)));
        CapabilityApplication profile = new CapabilityApplication("CatCoop", capabilities);
        CapabilityClient client = new CapabilityClient(profile);
        client.start();
    }

    private static Capability createCapability(String name, ParameterDefinition inputParameter) {
        List<ParameterDefinition> parameters = new ArrayList<>();
        parameters.add(inputParameter);
        Capability capability = new Capability(name, parameters);
        return capability;
    }
}
