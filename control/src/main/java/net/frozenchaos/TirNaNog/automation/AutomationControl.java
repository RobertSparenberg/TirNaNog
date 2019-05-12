package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.FunctionRepository;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.RecordRepository;
import net.frozenchaos.TirNaNog.explorer.Explorer;
import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomationControl {
    private final FunctionRepository functionRepository;
    private final RecordRepository recordRepository;
    private final OwnConfigService ownConfigService;
    private final Timer timer;
    private final Explorer explorer;
    private Iterable<Function> functions;

    @Autowired
    public AutomationControl(FunctionRepository functionRepository,
                             RecordRepository recordRepository,
                             OwnConfigService ownConfigService,
                             Timer timer,
                             Explorer explorer) {
        this.functionRepository = functionRepository;
        this.recordRepository = recordRepository;
        this.ownConfigService = ownConfigService;
        this.timer = timer;
        this.explorer = explorer;

        functions = functionRepository.findAll();
    }

    public void onParameter(String namespace, Parameter parameter) {
        for(Function function : functions) {
            function.onParameter(namespace, parameter, this);
        }
    }

    public RecordRepository getRecordRepository() {
        return recordRepository;
    }

    public ModuleConfig getOwnModuleConfig() {
        return ownConfigService.getOwnConfig();
    }

    public Timer getTimer() {
        return timer;
    }

    public Explorer getExplorer() {
        return explorer;
    }
}
