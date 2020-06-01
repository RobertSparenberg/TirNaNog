package net.frozenchaos.TirNaNog.data;

import net.frozenchaos.TirNaNog.TirNaNogProperties;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ModuleConfigRepository {
    private static final int MODULE_KEEP_CACHED_TIME = 200000;
    private static final String MODULE_KEEP_CACHED_TIME_PROPERTY = "net.frozenchaos.TirNaNog.keep_modules_cached_time";
    private final List<ModuleConfigEventListener> listeners = new ArrayList<>();

    private final Timer timer;

    private int moduleKeepCachedTime;

    private Map<String, ModuleConfig> configs = new ConcurrentHashMap<>();

    public ModuleConfigRepository(Timer timer, TirNaNogProperties properties) {
        this.timer = timer;
        try {
            moduleKeepCachedTime = Integer.parseInt(properties.getProperty(MODULE_KEEP_CACHED_TIME_PROPERTY));
        } catch(NumberFormatException ignored) {
            moduleKeepCachedTime = MODULE_KEEP_CACHED_TIME;
        }
    }

    public void addListener(ModuleConfigEventListener listener) {
        listeners.add(listener);
    }

    public ModuleConfig findByName(String name) {
        ModuleConfig moduleConfig = configs.get(name);
        if(moduleConfig != null) {
            long timeSinceLastBroadcast = timer.getTime()-moduleConfig.getLastMessageTimestamp();
            if(timeSinceLastBroadcast <= moduleKeepCachedTime) {
                configs.remove(name);
                moduleConfig = null;
            }
        }
        return moduleConfig;
    }

    public void save(ModuleConfig moduleConfig) {
        configs.put(moduleConfig.getName(), moduleConfig);
        for(ModuleConfigEventListener listener : listeners) {
            listener.onModuleConfigSave(moduleConfig);
        }
    }

    public void remove(ModuleConfig moduleConfig) {
        configs.remove(moduleConfig.getName());
        for(ModuleConfigEventListener listener : listeners) {
            listener.onModuleConfigRemove(moduleConfig);
        }
    }
}
