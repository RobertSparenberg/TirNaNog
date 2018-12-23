package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.Capability;
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
import java.util.List;

/*
The broadcaster is responsible for letting the TirNaNog network know about this module and what its IP is.
Each other module is responsible for ringing this module with the Telephone class to let us know where it is and that it heard our broadcast.
 */
public class Broadcaster {
    private static final int BROADCAST_PORT = 42001;
    private static final int REBROADCAST_DELAY = 41000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ModuleConfigRepository moduleConfigRepository;
    private final Telephone telephone;

    private final byte[] marshaledOwnConfig;
    private final String ownName;
    private final DatagramSocket broadcastSocket;
    private final DatagramSocket listenSocket;
    private final InetAddress broadcastAddress;
    private final Thread listenThread;

    private boolean stopRequested = false;

    public Broadcaster(ModuleConfigRepository moduleConfigRepository, Timer timer, Telephone telephone, List<Capability> ownCapabilities) throws IOException, JAXBException {
        logger.trace("Broadcaster initializing");
        this.moduleConfigRepository = moduleConfigRepository;
        this.telephone = telephone;

        this.broadcastSocket = new DatagramSocket();
        this.broadcastSocket.setBroadcast(true);
        this.broadcastSocket.setReuseAddress(true);
        this.broadcastAddress = InetAddress.getByName("192.168.1.255");

        ModuleConfig ownConfig = moduleConfigRepository.findByIp("localhost");
        this.ownName = ownConfig.getName();
        ownConfig.getCapabilities().clear();
        ownConfig.getCapabilities().addAll(ownCapabilities);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(ownConfig, outputStream);
        this.marshaledOwnConfig = outputStream.toByteArray();

        this.listenSocket = new DatagramSocket(BROADCAST_PORT);
        this.listenThread = new Thread(this::receiveBroadcast);
        this.listenThread.start();

        ScheduledTask broadcastTask = new ScheduledTask(REBROADCAST_DELAY) {
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
                this.listenSocket.receive(receivedBroadcast);
                if(!stopRequested) {
                    //todo: find a simpler way to unmarshall the string
                    ModuleConfig moduleConfig = JAXB.unmarshal(new StringReader(new String(buffer).trim()), ModuleConfig.class);
                    if(!this.ownName.equals(moduleConfig.getName())) {
                        moduleConfig.setLastMessageTimestamp(System.currentTimeMillis());
                        moduleConfig.setIp(receivedBroadcast.getAddress().getHostAddress());
                        logger.trace("Broadcaster saving moduleconfig: " + moduleConfig.toString());
                        moduleConfig = moduleConfigRepository.save(moduleConfig);
                        this.telephone.addContactToRing(moduleConfig);
                    }
                }
            } catch(Exception e) {
                this.logger.error("Error receiving broadcast from another module", e);
            }
        }
    }

    public void findOtherModules() throws IOException {
        DatagramPacket packet = new DatagramPacket(marshaledOwnConfig, marshaledOwnConfig.length, broadcastAddress, BROADCAST_PORT);
        this.broadcastSocket.send(packet);
        this.logger.trace("Sending broacast");
        this.broadcastSocket.close();
    }

    public void destroyGracefully() {
        this.stopRequested = true;

        this.broadcastSocket.close();
        this.listenThread.interrupt();
        this.listenSocket.close();
    }
}
