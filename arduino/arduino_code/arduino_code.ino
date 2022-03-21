// bluetooth module
#include <SoftwareSerial.h>
SoftwareSerial BT(2, 3);

void setup() {
  Serial.begin(9600);
  BT.begin(38400);
  pinMode(A0, INPUT);
  pinMode(A1, OUTPUT);
  pinMode(A2, OUTPUT);
  pinMode(A3, OUTPUT);
  pinMode(A4, OUTPUT);
}

void loop() {

  for (int i = 0; i < 16; i++) {
    setMuxSelect(i);
    int voltVal = analogRead(A0);
    sendData(voltVal);
  }
}

void setMuxSelect(int blockNum) {
  for (int i = 0; i < 4; i++) {
    (blockNum >> i) & i ? analogWrite(4-i, 255) : analogWrite(4-i, 255);
  }
}

void sendData(int voltVal) {
    BT.write(voltVal);
}
