package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.automation.triggers.Trigger;
import net.frozenchaos.TirNaNog.automation.triggers.TriggerRepository;
import net.frozenchaos.TirNaNog.capabilities.OwnCapabilityApplicationsService;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.utils.ScheduledTask;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The broadcaster is responsible for letting the TirNaNog network know about this module and what its IP is.
 * Each other module is responsible for ringing this module with the Telephone class to let us know where it is and that it heard our broadcast.
 */
public class Broadcaster {
    static final int BROADCAST_PORT = 42001;
    private static final String REBROADCAST_DELAY_PROPERTY = "net.frozenchaos.TirNaNog.broadcast_delay";
    private final int REBROADCAST_DELAY = 41000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ModuleConfigRepository moduleConfigRepository;
    private final OwnConfigService ownConfigService;
    private final OwnCapabilityApplicationsService ownCapabilityApplicationsService;
    private final TriggerRepository triggerRepository;

    private final DatagramSocket broadcastSocket;
    private final DatagramSocket listenSocket;
    private final InetAddress broadcastAddress;
    private final Thread listenThread;

    private final String ownModuleName;

    private boolean stopRequested = false;

    public Broadcaster(ModuleConfigRepository moduleConfigRepository,
                       OwnConfigService ownConfigService,
                       OwnCapabilityApplicationsService ownCapabilityApplicationsService,
                       TriggerRepository triggerRepository,
                       Timer timer,
                       Properties properties) throws IOException, JAXBException {
        this.ownConfigService = ownConfigService;
        this.ownCapabilityApplicationsService = ownCapabilityApplicationsService;
        this.triggerRepository = triggerRepository;
        ownModuleName = ownConfigService.getOwnConfig().getName();
        logger.trace("Broadcaster initializing");
        this.moduleConfigRepository = moduleConfigRepository;

        broadcastSocket = new DatagramSocket();
        broadcastSocket.setBroadcast(true);
        broadcastSocket.setReuseAddress(true);
        broadcastAddress = InetAddress.getByName("192.168.1.255");

        listenSocket = new DatagramSocket(BROADCAST_PORT);
        listenThread = new Thread(this::receiveBroadcast);
        listenThread.start();

        int rebroadcastDelay;
        try {
            rebroadcastDelay = Integer.valueOf(properties.getProperty(REBROADCAST_DELAY_PROPERTY));
        } catch(NumberFormatException ignored) {
            rebroadcastDelay = REBROADCAST_DELAY;
        }
        ScheduledTask broadcastTask = new ScheduledTask(rebroadcastDelay) {
            @Override
            public void doTask() {
                try {
                    findOtherModules();
                } catch(Exception e) {
                    logger.debug("Error during broadcast", e);
                }
                if(!stopRequested) {
                    timer.addTask(this);
                }
            }
        };
        timer.addTask(broadcastTask);
        logger.trace("Broadcaster initialized");
    }

    private void receiveBroadcast() {
        while(!stopRequested) {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket receivedBroadcast = new DatagramPacket(buffer, buffer.length);
                listenSocket.receive(receivedBroadcast);
                if(!stopRequested) {
                    //todo: find a simpler way to unmarshal the string
                    ModuleConfig moduleConfig = JAXB.unmarshal(new StringReader(new String(buffer).trim()), ModuleConfig.class);
                    if(!ownModuleName.equals(moduleConfig.getName())) {
                        moduleConfig.setLastMessageTimestamp(System.currentTimeMillis());
                        moduleConfig.setIp(receivedBroadcast.getAddress().getHostAddress());
                        logger.trace("Broadcaster saving moduleconfig: "+moduleConfig.toString());
                        moduleConfigRepository.save(moduleConfig);
                    }
                }
            } catch(Exception e) {
                logger.error("Error receiving broadcast from another module", e);
            }
        }
    }

    public void findOtherModules() throws IOException {
        byte[] ownConfig = getMarshaledOwnConfig();
        DatagramPacket packet = new DatagramPacket(ownConfig, ownConfig.length, broadcastAddress, BROADCAST_PORT);
        broadcastSocket.send(packet);
        logger.trace("Sending broadcast");
        broadcastSocket.close();
    }

    private byte[] getMarshaledOwnConfig() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ModuleConfig ownConfig = ownConfigService.getOwnConfig();
        ownConfig.setCapabilityApplications(ownCapabilityApplicationsService.getCapabilityApplications());
        List<String> parameters = new ArrayList<>();
        for(Trigger trigger : triggerRepository.findAll()) {
            if(!trigger.getParameterQualifier().startsWith(ownConfig.getName())) {
                parameters.add(trigger.getParameterQualifier());
            }
        }
        ownConfig.setSubscribedParameters(parameters);
        JAXB.marshal(ownConfig, outputStream);
        return outputStream.toByteArray();
    }

    public void destroyGracefully() {
        stopRequested = true;

        broadcastSocket.close();
        listenThread.interrupt();
        listenSocket.close();
    }
}
