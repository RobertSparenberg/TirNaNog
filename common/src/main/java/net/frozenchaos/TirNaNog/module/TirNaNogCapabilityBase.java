package net.frozenchaos.TirNaNog.module;

import net.frozenchaos.TirNaNog.capabilities.Capability;
import net.frozenchaos.TirNaNog.capabilities.CapabilityApplication;
import net.frozenchaos.TirNaNog.capabilities.CapabilityClient;
import net.frozenchaos.TirNaNog.capabilities.ParameterProcessor;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.List;

//todo: cleanup
public abstract class TirNaNogCapabilityBase {
    protected static final Logger logger = LoggerFactory.getLogger(TirNaNogCapabilityBase.class);
//    private TirNaNogCapabilityConfiguration configuration = null;
    private CapabilityApplication capabilityApplication = null;

    public static void main(String args[]) throws Exception {
        Reflections reflections = new Reflections();
        Class moduleBaseClass = null;
        for(Class clazz : reflections.getSubTypesOf(TirNaNogCapabilityBase.class)) {
            logger.trace("Checking suitability of base class '"+clazz.getName()+'\'');
            if(!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                moduleBaseClass = clazz;
                break;
            }
        }
        if(moduleBaseClass != null) {
            logger.trace("Initializing TirNaNog capability using base class '" + moduleBaseClass.getName() + '\'');
            TirNaNogCapabilityBase instance = (TirNaNogCapabilityBase) moduleBaseClass.newInstance();
            instance.init();
        } else {
            logger.error("Could not initialize TirNaNog capability; no suitable base class found");
            System.exit(1);
        }
    }

    private void init() throws Exception {
//        configuration = JAXB.unmarshal(new File("capabilityconfig.xml"), TirNaNogCapabilityConfiguration.class);
        String name = getName();
        logger.info("Initializing " + name);

        capabilityApplication = new CapabilityApplication(name, getCapabilities());
        CapabilityClient capabilityClient = new CapabilityClient(capabilityApplication, getParameterProcessors());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("Shutting down");
                capabilityClient.stopGracefully();
                onShutdown();
            }
        });

        capabilityClient.run();
    }

//    protected TirNaNogCapabilityConfiguration getConfiguration() {
//        return configuration;
//    }

    protected abstract String getName();

    protected abstract List<Capability> getCapabilities();

    protected abstract List<ParameterProcessor> getParameterProcessors();

    protected abstract void onShutdown();
}
