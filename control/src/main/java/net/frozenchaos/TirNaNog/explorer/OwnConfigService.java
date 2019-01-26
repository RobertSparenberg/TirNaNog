package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.capabilities.CapabilityApplication;
import net.frozenchaos.TirNaNog.capabilities.CapabilityServer;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class OwnConfigService {
    private static final String MODULE_NAME_PROPERTY = "net.frozenchaos.TirNaNog.module_name";

    private final CapabilityServer capabilityServer;
    private final ModuleConfig ownConfig;

    @Autowired
    public OwnConfigService(Properties properties, CapabilityServer capabilityServer) {
        this.capabilityServer = capabilityServer;

        String name = properties.getProperty(MODULE_NAME_PROPERTY, "Unknown");
        ownConfig = new ModuleConfig(name, "localhost", false);
    }

    public ModuleConfig getOwnConfig() {
        List<CapabilityApplication> capabilityApplicationsList = capabilityServer.getCapabilityApplications();
        CapabilityApplication capabilityApplications[] = new CapabilityApplication[capabilityApplicationsList.size()];
        capabilityApplicationsList.toArray(capabilityApplications);

        ownConfig.setCapabilityApplications(capabilityApplications);
        return ownConfig;
    }
}