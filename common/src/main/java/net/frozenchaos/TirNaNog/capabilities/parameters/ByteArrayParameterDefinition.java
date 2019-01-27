package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ByteArrayParameterDefinition extends ParameterDefinition {
    public ByteArrayParameterDefinition() {
        super("Unknown ByteArrayParameterDefinition", ParameterType.OUTPUT);
    }

    public ByteArrayParameterDefinition(String name, ParameterType parameterType) {
        super(name, parameterType);
    }
}
