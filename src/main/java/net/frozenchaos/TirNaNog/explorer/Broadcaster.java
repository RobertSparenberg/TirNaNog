package net.frozenchaos.TirNaNog.explorer;

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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
    private final DatagramSocket broadcastSocket;
    private final DatagramSocket listenSocket;
    private final InetAddress broadcastAddress;
    private final Thread listenThread;

    private boolean stopRequested = false;

    public Broadcaster(ModuleConfigRepository moduleConfigRepository, Timer timer, Telephone telephone) throws IOException, JAXBException {
        this.moduleConfigRepository = moduleConfigRepository;
        this.telephone = telephone;

        broadcastSocket = new DatagramSocket(BROADCAST_PORT);
        broadcastSocket.setBroadcast(true);
        broadcastSocket.setReuseAddress(true);
        broadcastAddress = InetAddress.getByName("192.168.1.255");

        listenSocket = new DatagramSocket(BROADCAST_PORT);
        listenSocket.setReuseAddress(true);
        listenThread = new Thread(this::receiveBroadcast);
        listenThread.run();

        ModuleConfig ownConfig = moduleConfigRepository.findByIp("localhost");
        ownConfig.setIp(broadcastSocket.getLocalAddress().toString());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(ownConfig, outputStream);
        this.marshaledOwnConfig = outputStream.toByteArray();

        ScheduledTask broadcastTask = new ScheduledTask(REBROADCAST_DELAY) {
            @Override
            public void doTask() {
                try {
                    findOtherModules();
                } catch(IOException e) {
                    logger.debug("Error during broadcast", e);
                }
                if(!stopRequested) {
                    timer.addTask(this);
                }
            }
        };
        timer.addTask(broadcastTask);
    }

    private void receiveBroadcast() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket receivedBroadcast = new DatagramPacket(buffer, buffer.length);
            while(!stopRequested) {
                listenSocket.receive(receivedBroadcast);
                if(!stopRequested) {
                    ModuleConfig moduleConfig = JAXB.unmarshal(new String(buffer), ModuleConfig.class);
                    moduleConfig.setLastMessageTimestamp(System.currentTimeMillis());
                    moduleConfig = moduleConfigRepository.save(moduleConfig);
                    telephone.addContactToRing(moduleConfig);
                }
            }
        } catch(IOException e) {
            logger.error("Error receiving broadcast from another modules", e);
        }
    }

    public void findOtherModules() throws IOException {
        DatagramPacket packet = new DatagramPacket(marshaledOwnConfig, marshaledOwnConfig.length, broadcastAddress, BROADCAST_PORT);
        broadcastSocket.send(packet);
        broadcastSocket.close();
    }

    public void destroyGracefully() {
        this.stopRequested = true;

        broadcastSocket.close();
        listenThread.interrupt();
        listenSocket.close();
    }
}
