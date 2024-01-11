package net.frozenchaos.TirNaNog.data;

import net.frozenchaos.TirNaNog.capabilities.CapabilityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ModuleConfigTest {
    @Test
    public void settersTest() {
        //given
        ModuleConfig moduleConfig = new ModuleConfig("a", "127.0.0.1", false);
        String name = "testModuleConfigSettersTest";
        String ip = "234.212.19.87";
        boolean isHardwareInterface = true;
        long timestamp = 3587325325L;
        List<String> subscribedParameters = Arrays.asList("test1", "param2", "unexpected3");
        List<CapabilityApplication> capabilities = Arrays.asList(newCapability("testSettersCapability1"), newCapability("testSettersCapability2"));

        //when
        moduleConfig.setName(name);
        moduleConfig.setIp(ip);
        moduleConfig.setHardwareInterfaceOnly(isHardwareInterface);
        moduleConfig.setLastMessageTimestamp(timestamp);

        //then
        assertEquals(name, moduleConfig.getName());
        assertEquals(ip, moduleConfig.getIp());
        assertEquals(isHardwareInterface, moduleConfig.isHardwareInterfaceOnly());
        assertEquals(timestamp, moduleConfig.getLastMessageTimestamp());
        assertEquals(subscribedParameters, moduleConfig.getSubscribedParameters());
        assertEquals(capabilities, moduleConfig.getCapabilityApplications());
    }

    private CapabilityApplication newCapability(String name) {
        return new CapabilityApplication(name, new ArrayList<>());
    }
}