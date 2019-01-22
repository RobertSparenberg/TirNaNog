package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.automation.TimerParameterDefinition;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.capabilities.parameters.ParameterDefinition;
import net.frozenchaos.TirNaNog.utils.ScheduledTask;

public class SetTimer extends Action {
    private String name = "";
    private int delay = 0;

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        if(delay > 0 && !name.isEmpty()) {
            automationControl.getTimer().addTask(new ScheduledTask(delay) {
                @Override
                public void doTask() {
                    synchronized(function) {
                        function.onParameter(new Parameter("localhost.timer." + name) {
                            @Override
                            protected boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition) {
                                return parameterDefinition instanceof TimerParameterDefinition && parameterDefinition.getName().equals("localhost.timer." + name);
                            }
                        }, automationControl);
                    }
                }
            });
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
