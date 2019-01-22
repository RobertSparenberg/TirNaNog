package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.data.RecordRepository;
import net.frozenchaos.TirNaNog.explorer.Explorer;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomationControl {
    private final RecordRepository recordRepository;
    private final ModuleConfigRepository moduleConfigRepository;
    private final Timer timer;
    private final Explorer explorer;

    @Autowired
    public AutomationControl(RecordRepository recordRepository, ModuleConfigRepository moduleConfigRepository, Timer timer, Explorer explorer) {
        this.recordRepository = recordRepository;
        this.moduleConfigRepository = moduleConfigRepository;
        this.timer = timer;
        this.explorer = explorer;
    }

    public RecordRepository getRecordRepository() {
        return recordRepository;
    }

    public ModuleConfigRepository getModuleConfigRepository() {
        return moduleConfigRepository;
    }

    public Timer getTimer() {
        return timer;
    }

    public Explorer getExplorer() {
        return explorer;
    }
}
