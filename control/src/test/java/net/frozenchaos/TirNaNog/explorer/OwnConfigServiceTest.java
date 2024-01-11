package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.TirNaNogProperties;
import net.frozenchaos.TirNaNog.capabilities.OwnCapabilityApplicationsService;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class OwnConfigServiceTest {
    @Test
    void getOwnConfig() {
        //given
        TirNaNogProperties properties = new TirNaNogProperties();
        String name = "unitTestOwnConfigName";
        properties.put("net.frozenchaos.TirNaNog.module_name", name);
        OwnCapabilityApplicationsService capabilityApplicationsService = mock(OwnCapabilityApplicationsService.class);
        OwnConfigService ownConfigService = new OwnConfigService(properties, capabilityApplicationsService);

        //when
        ModuleConfig result = ownConfigService.getOwnConfig();

        //then
        assertEquals("127.0.0.1", result.getIp());
        assertEquals(name, result.getName());
        assertEquals(false, result.isHardwareInterfaceOnly());
        assertEquals(0, result.getLastMessageTimestamp());
    }
}