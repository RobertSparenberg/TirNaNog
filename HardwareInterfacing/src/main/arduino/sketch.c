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

const char CONFIG_ACTUATOR_DEFIINITION[] = "ACT";
const char CONFIG_SENSOR_DEFIINITION[] = "SNS";
const char MESSAGE_SEPARATOR = ':';
const char MESSAGE_END = '\n';
const char HANDSHAKE_INIT[] = "TirNaNog Arduino Init";
const char HANDSHAKE_ACCEPT[] = "TirNaNog Arduino Accept";
const char CONFIG_START[] = "START";
const char CONFIG_DONE[] = "DONE";

const int HANDSHAKE_INIT_BROADCAST_DELAY = 1000;
int delayUntilHandshakeBroadcast = HANDSHAKE_INIT_BROADCAST_DELAY;

bool configReady;
bool configDone;

unsigned long previousTimeMillis = 0;
unsigned int delta = 0;
char receivedMessage[64];
char receivedChar;
byte receivedMessageIndex = 0;
bool messageReady = false;
long lastMessageId; //java int is the same size as arduino long


void setup() {
    Serial.begin(9600);
}

void loop() {
    receiveMessages();
    updateTime();

    if(status < 3) {
        if(status & 1 == 0) {
            requestSync();
        }
        if(status & 2 == 0) {
            acceptSync();
        }
    } else {
        receiveConfig();
        receiveCommands();
    }

    //set the flag to false; don't process a message more than once
    messageReady = false;
}

void requestSync() {
    if(messageReady && receivedMessage == HANDSHAKE_ACCEPT) {
        status += 2;
    } else {
        delayUntilHandshakeBroadcast += delta;
        if(delayUntilHandshakeBroadcast >= HANDSHAKE_INIT_BROADCAST_DELAY) {
            Serial.print(HANDSHAKE_INIT);
            delayUntilHandshakeBroadcast = 0;
        }
    }
}

void acceptSync() {
    if(messageReady && receivedMessage == HANDSHAKE_INIT) {
        sendMessage(HANDSHAKE_ACCEPT);
        status += 1;
    }
}

void receiveConfig() {
    if(messageReady) {
        if(!configReady) {
        }
    }
}

void receiveMessages() {
    while (Serial.available() > 0) {
        receivedChar = Serial.read();
        if(receivedChar == MESSAGE_END) {
            if(status >= 3 && receivedMessage == HANDSHAKE_INIT) {
                reset();
            } else {
                messageReady = true;
            }
            receivedMessage[receivedMessageIndex] = '\0';
            receivedMessageIndex = 0;
        } else {
            receivedMessage[receivedMessageIndex] = receivedChar;
            receivedMessageIndex += 1;
        }
    }
}

void sendMessage(char[] message) {
    Serial.print(message);
    Serial.print(MESSAGE_END);
}

void updateTime() {
    unsigned long millis = millis();
    if(previousTimeMillis > millis) {
        //overflow situation; grab the remainder off of the max unsigned long value and add the overflow
        delta = (4294967295 - previousTimeMillis) + millis;
    } else {
        delta = millis - previousTimeMillis;
    }
    previousTimeMillis = millis;
}

void reset() {
    status = 0;
    configReady = false;
    configDone = false;
    lastMessageId = 0;
}