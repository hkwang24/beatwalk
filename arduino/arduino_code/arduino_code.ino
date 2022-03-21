// bluetooth module
#include <SoftwareSerial.h>

void setup() {
  Serial.begin(9600);
  pinMode(A0, INPUT);
  pinMode(A1, OUTPUT);
  pinMode(A2, OUTPUT);
  pinMode(A3, OUTPUT);
  pinMode(A4, OUTPUT);
}

void loop() {

  for (int i = 0; i < 16; i++) {
    setMuxSelect(i);
    int val = analogRead(A0);
  }
}

void setMuxSelect(int blockNum) {
  for (int i = 0; i < 4; i++) {
    (blockNum >> i) & i ? analogWrite(4-i, 255) : analogWrite(4-i, 255);
  }
}
