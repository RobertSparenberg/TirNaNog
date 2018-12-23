package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.Capability;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.utils.ScheduledTask;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXB;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/*
The telephone is responsible for ringing other TirNaNog devices to tell them about this modules IP.
Additionally it is responsible for being rang and being told other modules their IP if they've changed.

So the singular concern is synchronizing IP addresses between TirNaNog modules.

A module knows what the last IP is that they've broadcast through the Broadcaster class.
Any module that hasn't responded through the Telephone is considered to know this module by its old IP.
 */
public class Telephone {
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int DELAY_BETWEEN_CALLS = 2000;
    private static final int TELEPHONE_PORT = 42002;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ModuleConfigRepository moduleConfigRepository;
    private final Queue<ModuleConfig> scheduledCalls;
    private final ServerSocket serverSocket;
    private final Socket clientSocket;

    private final Thread inboundThread;
    private final byte[] marshaledOwnConfig;

    private boolean stopRequested = false;

    public Telephone(ModuleConfigRepository moduleConfigRepository, Timer timer, List<Capability> ownCapabilities) throws IOException {
        this.moduleConfigRepository = moduleConfigRepository;
        this.scheduledCalls = new ConcurrentLinkedDeque<>();
        this.serverSocket = new ServerSocket(TELEPHONE_PORT);
        this.clientSocket = new Socket();
        this.clientSocket.setSoTimeout(SOCKET_TIMEOUT);

        ModuleConfig ownConfig = moduleConfigRepository.findByIp("localhost");
        ownConfig.getCapabilities().clear();
        ownConfig.getCapabilities().addAll(ownCapabilities);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(ownConfig, outputStream);
        this.marshaledOwnConfig = outputStream.toByteArray();

        this.inboundThread = new Thread(this::receiveCall);
        ScheduledTask outboundCallTask = new ScheduledTask(DELAY_BETWEEN_CALLS) {
            @Override
            public void doTask() {
                try {
                    ringOtherModule();
                } catch(IOException e) {
                    logger.debug("Error during ringing other module", e);
                }
                if(!stopRequested) {
                    timer.addTask(this);
                }
            }
        };
        timer.addTask(outboundCallTask);
    }

    private void receiveCall() {
        while(!stopRequested) {
            try {
                Socket socket = this.serverSocket.accept();
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                ModuleConfig moduleConfig = JAXB.unmarshal(stringBuilder.toString(), ModuleConfig.class);
                moduleConfig.setLastMessageTimestamp(System.currentTimeMillis());
                moduleConfig.setIp(socket.getInetAddress().toString());
                logger.trace("telephone received call from: " + moduleConfig.getIp());
                logger.trace("received phonecall", moduleConfig);
                moduleConfigRepository.save(moduleConfig);
            } catch(SocketTimeoutException ignored) {
            } catch(IOException e) {
                logger.error("Error accepting incoming socket", e);
            }
        }
    }

    private void ringOtherModule() throws IOException {
        ModuleConfig moduleToRing = this.scheduledCalls.poll();
        if(moduleToRing != null) {
            this.clientSocket.connect(new InetSocketAddress(moduleToRing.getIp(), TELEPHONE_PORT));
            this.clientSocket.getOutputStream().write(this.marshaledOwnConfig);
            this.clientSocket.close();
        }
    }

    public void destroyGracefully() {
        this.stopRequested = true;
        try {
            this.serverSocket.close();
        } catch(Exception ignored) {
        }
        try {
            this.inboundThread.join(SOCKET_TIMEOUT+1000);
        } catch(Exception ignored) {
        }
    }

    public void addContactToRing(ModuleConfig moduleConfig) {
        this.scheduledCalls.add(moduleConfig);
    }
}
