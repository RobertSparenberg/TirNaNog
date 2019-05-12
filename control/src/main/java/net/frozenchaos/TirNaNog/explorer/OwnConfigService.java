package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.TirNaNogProperties;
import net.frozenchaos.TirNaNog.capabilities.OwnCapabilityApplicationsService;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnConfigService {
    private static final String MODULE_NAME_PROPERTY = "net.frozenchaos.TirNaNog.module_name";

    private final OwnCapabilityApplicationsService capabilityApplicationsService;
    private final ModuleConfig ownConfig;

    @Autowired
    public OwnConfigService(TirNaNogProperties properties, OwnCapabilityApplicationsService capabilityApplicationsService) {
        this.capabilityApplicationsService = capabilityApplicationsService;

        String name = properties.getProperty(MODULE_NAME_PROPERTY, "Unknown");
        ownConfig = new ModuleConfig(name, "localhost", false);
    }

    public ModuleConfig getOwnConfig() {
        ownConfig.setCapabilityApplications(capabilityApplicationsService.getCapabilityApplications());
        return ownConfig;
    }
}
