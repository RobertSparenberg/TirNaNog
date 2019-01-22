package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.FunctionRepository;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.data.RecordRepository;
import net.frozenchaos.TirNaNog.explorer.Explorer;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomationControl {
    private final FunctionRepository functionRepository;
    private final RecordRepository recordRepository;
    private final ModuleConfigRepository moduleConfigRepository;
    private final Timer timer;
    private final Explorer explorer;
    private Iterable<Function> functions;

    @Autowired
    public AutomationControl(FunctionRepository functionRepository,
                             RecordRepository recordRepository,
                             ModuleConfigRepository moduleConfigRepository,
                             Timer timer,
                             Explorer explorer) {
        this.functionRepository = functionRepository;
        this.recordRepository = recordRepository;
        this.moduleConfigRepository = moduleConfigRepository;
        this.timer = timer;
        this.explorer = explorer;

        functions = functionRepository.findAll();
    }

    public void onParameter(Parameter parameter) {
        for(Function function : functions) {
            function.onParameter(parameter, this);
        }
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
