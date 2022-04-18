// bluetooth module
#include <SoftwareSerial.h>
#include "Thread.h"
SoftwareSerial BT(2, 3);

void setup() {
  Serial.begin(9600);
  BT.begin(38400);
  pinMode(A0, INPUT);
  pinMode(A1, OUTPUT);
  pinMode(A2, OUTPUT);
  pinMode(A3, OUTPUT);
  pinMode(A4, OUTPUT);
  // LED tests
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(12, OUTPUT);
}

void loop() {
  checkBlocks();
  // play pin
  if (digitalRead(10)) {
    playOnce();
  }
  // loop pin
  if (digitalRead(11)) {
    playLoop();
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


void testLED() {
  for (int i = 5; i < 13; i++) {
    digitalWrite(i, HIGH);
    delay(1000);
    digitalWrite(i, LOW);
  }
}

void checkBlocks() {
//  testLED();
  for (int i = 0; i < 16; i++) {
    setMuxSelect(i);
    int voltVal = analogRead(A0);
    // check if its a pitch block
    if (voltVal > 1000) {
      // play sound and light up led
    }
//    Serial.print();
//    Serial.print(voltValn'\');
    sendData(voltVal);
  }
}

void playOnce() {
  // play sound
  // light up led
  // throughout loop check stop pin
  for (int i = 0; i < 16; i++) {
    digitalWrite(i, HIGH);
    // check stop pin here
  }
}

void playLoop() {
  while (true) {
    playOnce();
    // check stop pin here
    // if so, break
  }
}
