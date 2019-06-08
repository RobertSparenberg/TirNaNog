//////////////////////////////////////////////////////
//             TirNaNog Arduino Sketch              //
// This sketch works with the HardwareInterfacing   //
// TirNaNog CapabilityApplication software          //
// to configure and control additional IO pins      //
// communication is through a serial connection     //
// using the USB port.                              //
// An arduino is more suited for direct interfacing //
// with certain sensors and actuators than the pi.  //
//////////////////////////////////////////////////////

int incomingByte = 0;
    private static final String CONFIG_ACTUATOR_DEFIINITION = "ACT";
    private static final String CONFIG_SENSOR_DEFIINITION = "SNS";
    private static char MESSAGE_SEPARATOR = ':';
    private static char MESSAGE_END = '\n';
    private static final String HANDSHAKE_INIT = "TirNaNog Arduino Init" + MESSAGE_END;
    private static final String HANDSHAKE_ACCEPT = "TirNaNog Arduino Accept" + MESSAGE_END;

void setup() {
    Serial.begin(9600);
}

void loop() {
if (Serial.available() > 0) {
    incomingByte = Serial.read();
    Serial.print((char)incomingByte);
    Serial.print('!');
    }
}