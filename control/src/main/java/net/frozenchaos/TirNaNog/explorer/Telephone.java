package net.frozenchaos.TirNaNog.explorer;

/**
 * Todo: The telephone is still needed to retrieve updates from other nodes, but it doesn't actually serve a purpose in discovery
 * The telephone is responsible for ringing other TirNaNog devices to tell them about this modules IP.
 * Additionally it is responsible for being rang and being told other modules their IP if they've changed.
 *
 * So the singular concern is synchronizing IP addresses between TirNaNog modules.
 *
 * A module knows what the last IP is that they've broadcast through the Broadcaster class.
 * Any module that hasn't responded through the Telephone is considered to know this module by its old IP.
 */
public class Telephone {
    /*
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int DELAY_BETWEEN_CALLS = 2000;
    private static final int TELEPHONE_PORT = 42002;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ModuleConfigRepository moduleConfigRepository;
    private final Queue<ModuleConfig> scheduledCalls = new ConcurrentLinkedDeque<>();
    private final ServerSocket serverSocket;
    private final Socket clientSocket;

    private final Thread inboundThread;
    private final byte[] marshaledOwnConfig;
    private final String ownName;

    private boolean stopRequested = false;

    public Telephone(ModuleConfigRepository moduleConfigRepository, CapabilityServer timer, Timer ownCapabilities) throws IOException {
        logger.trace("Telephone Initializing");
        this.moduleConfigRepository = moduleConfigRepository;
        serverSocket = new ServerSocket(TELEPHONE_PORT);
        clientSocket = new Socket();
        clientSocket.setSoTimeout(SOCKET_TIMEOUT);

        ModuleConfig ownConfig = moduleConfigRepository.findByIp("localhost");
        this.ownName = ownConfig.getName();
        ownConfig.getCapabilityApplications().clear();
        ownConfig.getCapabilityApplications().addAll(ownCapabilities);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(ownConfig, outputStream);
        marshaledOwnConfig = outputStream.toByteArray();

        inboundThread = new Thread(this::receiveCall);
        inboundThread.start();

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
        logger.trace("Telephone initialized");
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
                logger.trace("received telephone xml: " + stringBuilder.toString());
                //todo: find a simpler way to unmarshall the string
                ModuleConfig moduleConfig = JAXB.unmarshal(new StringReader(stringBuilder.toString().trim()), ModuleConfig.class);
                if(!ownName.equals(moduleConfig.getName())) {
                    moduleConfig.setLastMessageTimestamp(System.currentTimeMillis());
                    moduleConfig.setIp(socket.getInetAddress().toString());
                    logger.trace("Broadcaster saving moduleconfig: " + moduleConfig.toString());
                    moduleConfigRepository.save(moduleConfig);
                }
            } catch(SocketTimeoutException ignored) {
            } catch(Exception e) {
                logger.error("Error accepting incoming socket", e);
            }
        }
    }

    private void ringOtherModule() throws IOException {
        ModuleConfig moduleToRing = scheduledCalls.poll();
        if(moduleToRing != null) {
            logger.trace("Telephone is ringing other module: " + moduleToRing.getIp());
            clientSocket.connect(new InetSocketAddress(moduleToRing.getIp(), TELEPHONE_PORT));
            clientSocket.getOutputStream().write(marshaledOwnConfig);
            clientSocket.close();
        }
    }

    public void destroyGracefully() {
        this.stopRequested = true;
        try {
            serverSocket.close();
        } catch(Exception ignored) {
        }
        try {
            inboundThread.join(SOCKET_TIMEOUT+1000);
        } catch(Exception ignored) {
        }
    }

    public void addContactToRing(ModuleConfig moduleConfig) {
        scheduledCalls.add(moduleConfig);
    }
    */
}
