package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.capabilities.parameters.ParameterDefinition;
import net.frozenchaos.TirNaNog.utils.ScheduledTask;

import javax.persistence.*;

@Entity
@Table(name = "action_set_timer")
@DiscriminatorValue(value = "setTimer")
public class SetTimer extends Action {
    @Embedded
    private final TimerParameterDefinition timerParameterDefinition = new TimerParameterDefinition();
    @Column(name = "delay", nullable = false)
    private int delay = 0;

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        if(delay > 0 && !timerParameterDefinition.getName().isEmpty()) {
            automationControl.getTimer().addTask(new ScheduledTask(delay) {
                @Override
                public void doTask() {
                    synchronized(function) {
                        function.onParameter(getTimerNamespace(automationControl), new Parameter<Long>(timerParameterDefinition) {
                            @Override
                            protected boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition) {
                                return parameterDefinition instanceof TimerParameterDefinition && parameterDefinition.getName().equals(getTimerNamespace(automationControl));
                            }

                            @Override
                            public Long getValue() {
                                return automationControl.getTimer().getTime();
                            }
                        }, automationControl);
                    }
                }
            });
        }
    }

    private String getTimerNamespace(AutomationControl automationControl) {
        return automationControl.getOwnModuleConfig().getName() + ".timer." + timerParameterDefinition.getName();
    }

    public String getName() {
        return timerParameterDefinition.getName();
    }

    public void setName(String name) {
        timerParameterDefinition.setName(name);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Embeddable
    public class TimerParameterDefinition extends ParameterDefinition<Long> {
        @Column(name = "name", nullable = false)
        private String timerName = "Unnamed";

        public TimerParameterDefinition() {
            super("Timer", ParameterType.OUTPUT);
        }

        @Override
        public String getName() {
            return timerName;
        }

        @Override
        public void setName(String name) {
            timerName = name;
        }
    }
}
