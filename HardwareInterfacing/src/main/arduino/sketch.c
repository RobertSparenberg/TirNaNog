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

unsigned long previousTimeMillis = 0;
unsigned int delta = 0;

// MESSAGING RELATED VARIABLES //
char receivedMessage[64];
char receivedChar;
char* receivedMessageSeparator;
char* receivedMessagePart;

long receivedMessageId = -1;
byte receivedMessageType;
byte receivedPinNumber;
byte receivedPinType;
int receivedValue;
byte receivedPinSignalType;

byte receivedMessageIndex = 0;
bool messageReady = false;
long lastMessageId = -1; //java int is the same size as arduino long

// OTHER VARIABLES //
long readValue;


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
        receiveMessage();
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


/**
 * base message definition:
 * messageId
 * messageType (0 = config, 1 = command)
 * other values are type specific
 **/
void receiveMessage() {
    if(messageReady) {
        receivedMessageId = strtol(receivedMessage, receivedMessagePart, 10);
        if(receivedMessageId > lastMessageId) {
            lastMessageId = receivedMessageId;
            ++receivedMessagePart;

            receivedMessageType = receivedMessagePart-1;
            ++receivedMessagePart;
            ++receivedMessagePart;

            if(receivedMessageType == 0) {
                receiveConfigMessage();
            } else if(receivedMessageType == 1) {
                receiveCommandMessage();
            }
        }
    }
}

/**
 * config message definition (in addition to basic message definition):
 * pinNumber
 * pinType (0 = sensor, 1 = actuator)
 **/
void receiveConfigMessage() {
    receivedPinNumber = receivedMessagePart-1;
    ++receivedMessagePart;
    ++receivedMessagePart;
    receivedPinType = receivedMessagePart-1;

    if(receivedPinType == 0) {
        pinMode(receivedPinNumber, INPUT);
    } else {
        pinMode(receivedPinNumber, OUTPUT);
    }

    Serial.print(receivedMessageId);
    Serial.print(":OK\n");
}

/**
 * command message definition (in addition to basic message definition)
 * pinNumber
 * pinType (0 = sensor, 1 = actuator)
 * pinSignalType (0 = digital, 1 = analog)
 * valueMultiplier (only if pinSignalType == analog)
 **/
void receiveCommandMessage() {
    receivedPinNumber = receivedMessagePart-1;
    ++receivedMessagePart;
    ++receivedMessagePart;
    receivedPinType = receivedMessagePart-1;
    ++receivedMessagePart;
    ++receivedMessagePart;
    receivedPinSignalType = receivedMessagePart-1;

    if(receivedPinType == 0) {
        //sensor pin
        if(receivedPinSignalType == 0) {
            //digital
            Serial.print(receivedMessageId);
            Serial.print(':');
            Serial.print(digitalRead(receivedPinNumber));
            Serial.print('\n');
        } else {
            //analog
            Serial.print(receivedMessageId);
            Serial.print(':');
            Serial.print(analogRead(receivedPinNumber));
            Serial.print('\n');
        }
    } else {
        //actuator pin
        ++receivedMessagePart;
        ++receivedMessagePart;
        receivedValue = atoi(receivedMessagePart);

        if(receivedPinSignalType == 0) {
            //digital
            digitalWrite(receivedPinNumber, receivedValue > 0 ? HIGH : LOW);
        } else {
            //analog
            analogWrite(receivedPinNumber, receivedValue);
        }

        Serial.print(receivedMessageId);
        Serial.print(":OK\n");
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