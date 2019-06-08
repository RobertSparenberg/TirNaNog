package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.automation.triggers.Trigger;
import net.frozenchaos.TirNaNog.automation.triggers.TriggerRepository;
import net.frozenchaos.TirNaNog.capabilities.OwnCapabilityApplicationsService;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.utils.ScheduledTask;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BroadcasterTest {
    @Test
    public void testBroadcast() throws Exception {
        //setup
        TestPort testPort = new TestPort();
        ModuleConfigRepository moduleConfigRepository = mock(ModuleConfigRepository.class);
        OwnConfigService ownConfigService = mock(OwnConfigService.class);
        OwnCapabilityApplicationsService ownCapabilityApplicationsService = mock(OwnCapabilityApplicationsService.class);
        TriggerRepository triggerRepository = mock(TriggerRepository.class);
        Timer timer = mock(Timer.class);
        Properties properties = mock(Properties.class);

        //given
        String name = "BroadcasterTest_testBroadcast";
        String[] triggerQualifiers = {name + ".Test.TestTrigger1", "OtherName.Test.TestTrigger2", "OtherName.Test.DifferentName"};
        when(triggerRepository.findAll()).thenReturn(CreateTriggers(triggerQualifiers));
        ArgumentCaptor<ScheduledTask> taskCaptor = ArgumentCaptor.forClass(ScheduledTask.class);

        //when
        Broadcaster broadcaster = new Broadcaster(moduleConfigRepository, ownConfigService, ownCapabilityApplicationsService, triggerRepository, timer, properties);
        verify(timer).addTask(taskCaptor.capture());
        ScheduledTask broadcastTask = taskCaptor.getValue();
        assertNotNull(broadcastTask);
        broadcastTask.doTask();

        //then
        assertEquals(1, testPort.getPacketsReceived());
        ModuleConfig broadcast = testPort.getModuleConfig();
        assertEquals(name, broadcast.getName());
        List<String> subscribedParameters = broadcast.getSubscribedParameters();
        assertEquals(2, subscribedParameters.size());
        assertEquals(triggerQualifiers[1], subscribedParameters.get(0));
        assertEquals(triggerQualifiers[2], subscribedParameters.get(1));
    }

    private Iterable<Trigger> CreateTriggers(String[] triggerQualifiers) {
        List<Trigger> triggers = new ArrayList<>();
        for(String triggerQualifier : triggerQualifiers) {
            triggers.add(new Trigger() {
                @Override
                public String getParameterQualifier() {
                    return triggerQualifier;
                }

                @Override
                public boolean isTriggered(Parameter parameter) {
                    return false;
                }
            });
        }
        return triggers;
    }

    private class TestPort {
        private final DatagramSocket socket;
        private final byte[] buffer;
        private final Thread thread;
        private boolean stop = false;
        private int packetsReceived = 0;
        private ModuleConfig moduleConfig;

        public TestPort() throws Exception {
            socket = new DatagramSocket(Broadcaster.BROADCAST_PORT);
            buffer = new byte[1024];
            thread = new Thread(this::listen);
        }

        private void listen() {
            try {
                while(!stop) {
                    DatagramPacket receivedBroadcast = new DatagramPacket(buffer, buffer.length);
                    socket.receive(receivedBroadcast);
                    if(!stop) {
                        packetsReceived += 1;
                        moduleConfig = JAXB.unmarshal(new StringReader(new String(buffer).trim()), ModuleConfig.class);
                    }
                }
            } catch(Exception ignored) {}
        }

        public void stop() {
            try {
                stop = true;
                socket.close();
                thread.interrupt();
                thread.join(1000);
            } catch(Exception ignored) {}
        }

        public ModuleConfig getModuleConfig() {
            return moduleConfig;
        }

        public int getPacketsReceived() {
            return packetsReceived;
        }
    }
}