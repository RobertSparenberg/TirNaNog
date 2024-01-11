package net.frozenchaos.TirNaNog.data;

import net.frozenchaos.TirNaNog.TirNaNogProperties;
import net.frozenchaos.TirNaNog.utils.ScheduledTask;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class ModuleConfigRepositoryTest {
    @Test
    void save() {
        //given
        Timer timer = mock(Timer.class);
        TirNaNogProperties properties = new TirNaNogProperties();
        ModuleConfigRepository repository = new ModuleConfigRepository(timer, properties);
        ModuleConfigEventListener listener = mock(ModuleConfigEventListener.class);
        repository.addListener(listener);
        ArgumentCaptor<ModuleConfig> moduleConfigCaptor = ArgumentCaptor.forClass(ModuleConfig.class);

        String name = "testModuleConfigRepositoryModuleConfigName";
        String ip = "123.456.789.100";
        boolean hardwareInterfaceOnly = false;

        //when
        ModuleConfig testConfig = new ModuleConfig(name, ip, hardwareInterfaceOnly);
        repository.save(testConfig);

        //then
        verify(listener).onModuleConfigSave(moduleConfigCaptor.capture());
        ModuleConfig eventConfig = moduleConfigCaptor.getValue();
        assertEquals(testConfig, eventConfig);
    }

    @Test
    void remove() {
        //given
        Timer timer = mock(Timer.class);
        TirNaNogProperties properties = new TirNaNogProperties();
        ModuleConfigRepository repository = new ModuleConfigRepository(timer, properties);
        ModuleConfigEventListener listener = mock(ModuleConfigEventListener.class);
        repository.addListener(listener);

        //when

        //then

    }
}