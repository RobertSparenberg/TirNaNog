package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/*
The explorer service is used to inform this device of other TirNaNog devices on the network.
It will actively look for devices and keep its records of them updated whenever possible.
 */
@Service
public class Explorer implements ApplicationListener<ContextRefreshedEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ModuleConfigRepository moduleConfigRepository;
    private final Timer timer;

    private Broadcaster broadcaster;
    private Telephone telephone;
    private boolean started = false;

    @Autowired
    public Explorer(ModuleConfigRepository moduleConfigRepository, Timer timer) {
        this.moduleConfigRepository = moduleConfigRepository;
        this.timer = timer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(!this.started) {
            logger.info("Initializing TirNaNog device explorer");
            try {

                telephone = new Telephone();
                broadcaster = new Broadcaster(moduleConfigRepository, timer, telephone);
                this.started = true;
                logger.info("ITirNaNog device explorer started");
            } catch(Exception e) {
                logger.error("Error initializing the TirNaNog device Explorer, shutting down the explorer", e);
                if(telephone != null) {
                    telephone.destroyGracefully();
                }
                if(broadcaster != null) {
                    broadcaster.destroyGracefully();
                }
            }
        }
    }
}
