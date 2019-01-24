package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public class ByteArrayParameter extends Parameter<byte[]> {
    private byte[] value;

    public void setValue(byte[] value) {
        this.value = value;
    }

    @XmlElement(name = "value")
    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    protected boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition) {
        return parameterDefinition instanceof ByteArrayParameterDefinition;
    }
}
