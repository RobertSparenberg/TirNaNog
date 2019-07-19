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

unsigned long currentMillis;
unsigned long previousTimeMillis = 0;
unsigned int delta = 0;
byte status = 0;

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
    receiveFromSerial();
    updateTime();

    if(status < 3) {
        sync();
    } else if(status == 3 && messageReady) {
        receiveMessage();
    }

    if(messageReady) {
        resetMessage();
    }
}

void sync() {
    if(status != 1) {
        if(messageReady && strcmp(receivedMessage, HANDSHAKE_ACCEPT) == 0) {
            status += 1;
        } else {
            delayUntilHandshakeBroadcast += delta;
            if(delayUntilHandshakeBroadcast >= HANDSHAKE_INIT_BROADCAST_DELAY) {
                Serial.print(HANDSHAKE_INIT);
                delayUntilHandshakeBroadcast = 0;
            }
        }
    }
    
    if(messageReady && status != 2 && strcmp(receivedMessage, HANDSHAKE_INIT) == 0) {
        Serial.print(HANDSHAKE_ACCEPT);
        Serial.print(MESSAGE_END);
        status += 2;
    }
}


/**
 * base message definition:
 * messageId
 * separator ":"
 * messageType (0 = config, 1 = command)
 * other values are type specific
 **/
void receiveMessage() {
    if(messageReady) {
        receivedMessageId = strtol(receivedMessage, &receivedMessagePart, 10);
        if(receivedMessageId > lastMessageId) {
            lastMessageId = receivedMessageId;
            ++receivedMessagePart;
            receivedMessageType = *receivedMessagePart;
            ++receivedMessagePart;
            ++receivedMessagePart;

            Serial.print("messageId: ");
            Serial.println(receivedMessageId);
            Serial.print("messageType: ");
            Serial.println(receivedMessageType);

            if(receivedMessageType == '0') {
                receiveConfigMessage();
            } else if(receivedMessageType == '1') {
                receiveCommandMessage();
            }
        } else {
          Serial.print(receivedMessageId);
          Serial.print(":OLD:");
          Serial.print(lastMessageId + 1);
          Serial.print("\n");
        }
    }
}


/**
 * config message definition (in addition to basic message definition):
 * pinNumber
 * pinType (0 = sensor, 1 = actuator)
 **/
void receiveConfigMessage() {
    Serial.println("config message");
    
    receivedPinNumber = (int)strtol(receivedMessagePart, &receivedMessagePart, 10);
    Serial.print("pin number: ");
    Serial.println(receivedPinNumber);
    //receivedPinNumber = *receivedMessagePart;
    //++receivedMessagePart;
    ++receivedMessagePart;
    receivedPinType = *receivedMessagePart;
    Serial.print("pin type: ");
    Serial.println(receivedPinType);

    if(receivedPinType == '0') {
        pinMode(receivedPinNumber, INPUT);
    } else {
        pinMode(receivedPinNumber, OUTPUT);
    }

    Serial.print(receivedMessageId);
    Serial.print(MESSAGE_SEPARATOR + "OK" + MESSAGE_END);
}

/**
 * command message definition (in addition to basic message definition)
 * pinNumber
 * pinType (0 = sensor, 1 = actuator)
 * pinSignalType (0 = digital, 1 = analog)
 * valueMultiplier (only if pinSignalType == analog)
 **/
void receiveCommandMessage() {
    Serial.print("command message\n");
    
    receivedPinNumber = (int)strtol(receivedMessagePart, &receivedMessagePart, 10);
    Serial.print("pin number: ");
    Serial.println(receivedPinNumber);
    //receivedPinNumber = *receivedMessagePart;
    //++receivedMessagePart;
    ++receivedMessagePart;
    receivedPinType = *receivedMessagePart;
    ++receivedMessagePart;
    ++receivedMessagePart;
    receivedPinSignalType = *receivedMessagePart;

    Serial.print("pin type: ");
    Serial.println(receivedPinType);
    Serial.print("signal type: ");
    Serial.println(receivedPinSignalType);

    if(receivedPinType == '0') {
        //sensor pin
        if(receivedPinSignalType == '0') {
            //digital
            Serial.print(receivedMessageId);
            Serial.print(MESSAGE_SEPARATOR);
            Serial.print(digitalRead(receivedPinNumber));
            Serial.print(MESSAGE_END);
        } else {
            //analog
            Serial.print(receivedMessageId);
            Serial.print(MESSAGE_SEPARATOR);
            Serial.print(analogRead(receivedPinNumber));
            Serial.print(MESSAGE_END);
        }
    } else {
        //actuator pin
        ++receivedMessagePart;
        ++receivedMessagePart;
        receivedValue = atoi(receivedMessagePart);
        
        Serial.print("actuator value: ");
        Serial.println(receivedValue);

        if(receivedPinSignalType == '0') {
            //digital
            digitalWrite(receivedPinNumber, receivedValue > 0 ? HIGH : LOW);
        } else {
            //analog
            analogWrite(receivedPinNumber, receivedValue);
        }

        Serial.print(receivedMessageId);
        Serial.print(MESSAGE_SEPARATOR + "OK" + MESSAGE_END);
    }
}

void receiveFromSerial() {
    while (Serial.available() > 0) {
        receivedChar = Serial.read();
        if(receivedChar == MESSAGE_END) {
            if(status >= 3 && strcmp(receivedMessage, HANDSHAKE_INIT) == 0) {
                reset();
                return;
            } else {
                messageReady = true;
            }
            Serial.print("message receieved (");
            Serial.print(receivedMessageIndex);
            Serial.print(") -");
            Serial.print(receivedMessage);
            Serial.println("-");
            receivedMessage[receivedMessageIndex] = '\0';
            receivedMessageIndex = 0;
        } else {
            receivedMessage[receivedMessageIndex] = receivedChar;
            receivedMessageIndex += 1;
        }
    }
}

void updateTime() {
    currentMillis = millis();
    if(previousTimeMillis > currentMillis) {
        //overflow situation; grab the remainder off of the max unsigned long value and add the overflow
        delta = (4294967295 - previousTimeMillis) + currentMillis;
    } else {
        delta = currentMillis - previousTimeMillis;
    }
    previousTimeMillis = currentMillis;
}

void resetMessage() {
    messageReady = false;
    receivedMessageIndex = 0;
    for(int i = 0; i < 64; i++) {
        receivedMessage[i] = '\0';
    }
}

void reset() {
    status = 0;
    lastMessageId = 0;
    resetMessage();
}