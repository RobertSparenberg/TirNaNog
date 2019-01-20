package net.frozenchaos.TirNaNog.module;

import net.frozenchaos.TirNaNog.capabilities.Capability;
import net.frozenchaos.TirNaNog.capabilities.CapabilityApplication;
import net.frozenchaos.TirNaNog.capabilities.CapabilityClient;
import net.frozenchaos.TirNaNog.capabilities.ParameterProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import java.lang.reflect.Modifier;
import java.util.List;

public abstract class TirNaNogCapabilityBase {
    protected static final Logger logger = LoggerFactory.getLogger(TirNaNogCapabilityBase.class);
    private TirNaNogCapabilityConfiguration configuration = null;
    private CapabilityApplication capabilityApplication = null;

    public static void main(String args[]) throws Exception {
        Class moduleBaseClass = null;
        for(Class clazz : TirNaNogCapabilityBase.class.getClasses()) {
            if(!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                moduleBaseClass = clazz;
                break;
            }
        }
        if(moduleBaseClass != null) {
            logger.info("Initializing TirNaNog capability using base class '" + moduleBaseClass.getName() + '\'');
        } else {
            logger.error("Could not initialize TirNaNog capability; no suitable base class found");
            System.exit(1);
        }
    }

    protected abstract List<Capability> getCapabilities();

    protected abstract List<ParameterProcessor> getParameterProcessors();

    private void init() throws Exception {
        configuration = JAXB.unmarshal("", TirNaNogCapabilityConfiguration.class);
        capabilityApplication = new CapabilityApplication(configuration.getName(), getCapabilities());
        CapabilityClient capabilityClient = new CapabilityClient(capabilityApplication, getParameterProcessors());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                capabilityClient.stopGracefully();
            }
        });

        capabilityClient.run();
    }
}
