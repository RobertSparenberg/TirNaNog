package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.FunctionRepository;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.RecordRepository;
import net.frozenchaos.TirNaNog.explorer.Explorer;
import net.frozenchaos.TirNaNog.explorer.NotificationService;
import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import net.frozenchaos.TirNaNog.utils.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomationControl implements ParameterListener {
    private final FunctionRepository functionRepository;
    private final RecordRepository recordRepository;
    private final OwnConfigService ownConfigService;
    private final Timer timer;
    private final Explorer explorer;

    @Autowired
    public AutomationControl(FunctionRepository functionRepository,
                             RecordRepository recordRepository,
                             OwnConfigService ownConfigService,
                             NotificationService notificationService,
                             Timer timer,
                             Explorer explorer) {
        this.functionRepository = functionRepository;
        this.recordRepository = recordRepository;
        this.ownConfigService = ownConfigService;
        this.timer = timer;
        this.explorer = explorer;
        notificationService.addListener(this);
    }

    @Override
    public void onParameter(String parameterQualifier, Parameter parameter) {
        for(Function function : functionRepository.findAll()) {
            function.onParameter(parameterQualifier, parameter, this);
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
