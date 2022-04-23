// bluetooth module
#include <SoftwareSerial.h>
#include "Adafruit_Soundboard.h"

#define SFX_TX 2
#define SFX_RX 3
#define SFX_RST 4

SoftwareSerial ss = SoftwareSerial(SFX_TX, SFX_RX);

Adafruit_Soundboard sfx = Adafruit_Soundboard(&ss, NULL, SFX_RST);

void setup() {
  Serial.begin(9600);
  ss.begin(9600);
  //BT.begin(9600);
  pinMode(A6, INPUT);
  pinMode(A2, OUTPUT);
  pinMode(A3, OUTPUT);
  pinMode(A4, OUTPUT);
  pinMode(A5, OUTPUT);
  // LED tests
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(13, OUTPUT);

  // play, stop, loop
  pinMode(A1, INPUT_PULLUP);
  pinMode(A0, INPUT_PULLUP);
  pinMode(12, INPUT_PULLUP);

  if (!sfx.reset()) {
//    Serial.println("Not found");
    while (1);
  } else {
//    Serial.println("SFX board found");
  }
}

boolean block_found[16];
boolean looping = false;

void loop() {
  checkNewBlocks();
  if (digitalRead(A1) == LOW) {
      delay(800);
      int blocks[16];
      checkBlocks(blocks);
      sendData(blocks); 
      play(blocks);
      delay(1000);
  }
  while (looping) {
      delay(800);
      int blocks[16];
      checkBlocks(blocks);
      sendData(blocks); 
      play(blocks);
      delay(2000);
      if (digitalRead(A0) == LOW) {
        looping = false;
        break;
      }
  }
  if (digitalRead(12) == LOW) {
    Serial.print("Start loop");
    looping = true;
  }
  
  
}

char getValue(int i) {
  if (620 <= i && i <= 635) {
    return 'q';
  } else if (405 <= i && i <= 415) {
    return 'h';
  } else if (172 <= i && i <= 180) {
    return 'r';
  } else if (928 <= i && i <= 935) {
    return 'e';
  } else if (885 <= i && i <= 892) {
    return 'd';
  } else if (1000 <= i && i <= 1020) {
    return 'c';
  } else if (89 <= i && i <= 97) {
    return 'g';
  } else if (695 <= i && i <= 700) {
    return 'b';
  } else if (735 <= i && i <= 845) {
    return 'f';
   } else if (500 <= i && i <= 520) {
    return 'a';
  } else {
    return ' ';
  }
}

void playNote (char note, boolean half) {
  boolean played = true;
  switch(note) {
    case 'e': 
      if (half) {
        played = sfx.playTrack((uint8_t)11);
      } else {
        played = sfx.playTrack((uint8_t)10);
      }
      break;
    case 'd':
      if (half) {
        played = sfx.playTrack((uint8_t)9);
      } else {
        played = sfx.playTrack((uint8_t)8);
      }
      break;
    case 'g':
      if (half) {
        played = sfx.playTrack((uint8_t)1);
      } else {
        played = sfx.playTrack((uint8_t)14);
      }
      break;
    case 'b':
      if (half) {
        played = sfx.playTrack((uint8_t)5);
      } else {
        played = sfx.playTrack((uint8_t)4);
      }
      break;
    case 'f':
      if (half) {
        played = sfx.playTrack((uint8_t)13);
      } else {
        played = sfx.playTrack((uint8_t)12);
      }
      break;
    case 'c':
      if (half) {
        played = sfx.playTrack((uint8_t)7);
      } else {
        played = sfx.playTrack((uint8_t)6);
      }
      break;
    case 'a':
      if (half) {
        played = sfx.playTrack((uint8_t)3);
      } else {
        played = sfx.playTrack((uint8_t)2);
      }
      break;
  }
  if (!played) {
//    Serial.println("Failed to play track");
//    Serial.println(note);
  } else {
//    Serial.println(note);
  }
}

void turnOffLED() {
  for (int i = 5; i < 13; i++) {
    if (i == 12) {
      digitalWrite(13, LOW);
    } else {
      digitalWrite(i, LOW);
    }
  }
}

void play(int blocks[]) {
//  Serial.println("Playing blocks");
  char r;
  char p;
  boolean playedHalf = false;
  int led = 5;
  turnOffLED();
  for (int i = 0; i < 16; i+=2) {
    if (digitalRead(A0) == LOW) {
      looping = false;
      turnOffLED();
      break;
    }
    boolean gotRhythm = false;
    if (getValue(blocks[i]) == 'q' ||
        getValue(blocks[i]) == 'h' ||
        getValue(blocks[i]) == 'r') {
          r = getValue(blocks[i]);
          gotRhythm = true;
    } else {
      p = getValue(blocks[i]);
    }
    if (gotRhythm) {
      p = getValue(blocks[i+1]);
    } else {
      r = getValue(blocks[i+1]);
    }

    if (digitalRead(A0) == LOW) {
      looping = false;
      turnOffLED();
      break;
    }

    if (playedHalf) {
      playedHalf = false;
      turnOffLED();
    } else {
      if (r == 'q') {
        playedHalf = false;
        playNote(p, false);
      } else if (r == 'h') {
        playNote(p, true);
        playedHalf = true;
      } else if (r == ' ') {
        playedHalf = false;
      }  
    }
    
    if (led == 12) {
      digitalWrite(13, HIGH);
    } else {
      digitalWrite(led, HIGH);
    }
    led++;
    delay(700);
    turnOffLED();
    flushInput();
  }
  turnOffLED();
}

void flushInput() {
  uint16_t timeoutloop = 0;
  while (timeoutloop++ < 40) {
    while (ss.available()) {
      ss.read(); 
      timeoutloop = 0;
    }
    delay(1);
  }
}

void sendData(int blocks[]) {
  char buffer[100];
  sprintf(buffer, "%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d;",
    blocks[0],
    blocks[1],
    blocks[2],
    blocks[3],
    blocks[4],
    blocks[5],
    blocks[6],
    blocks[7],
    blocks[8],
    blocks[9],
    blocks[10],
    blocks[11],
    blocks[12],
    blocks[13],
    blocks[14],
    blocks[15]);
  Serial.println(buffer);
  Serial.flush();
}

void setMuxSelect(int blockNum) {
  for (int i = 0; i < 4; i++) {
    digitalWrite(19-i, LOW);
  }
  for (int i = 0; i < 4; i++) {
    (blockNum >> i) & 1 ? digitalWrite(19-i, HIGH) : digitalWrite(19-i, LOW);
  }
}

void checkBlocks(int blocks[]) {
  for (int i = 0; i < 16; i++) {
    setMuxSelect(i);
    int voltVal = analogRead(A7);
    blocks[i] = voltVal;
  }
}

void checkNewBlocks() {
  for (int i = 0; i < 16; i++) {
    setMuxSelect(i);
    int voltVal = analogRead(A7);
    char c = getValue(voltVal);
    if (c == 'q' || c == 'h' || c == 'r') {
    } else if (c != ' ') {
      if (!block_found[i]) {
//        Serial.println("New block found");
      int ledNumb = 5 + (i / 2);
      if (ledNumb == 12) {
        digitalWrite(13, HIGH);
      } else {
        digitalWrite(ledNumb, HIGH);
      }
      playNote(c, false);
      delay(250);
      if (ledNumb == 12) {
        digitalWrite(13, LOW);
      } else {
        digitalWrite(ledNumb, LOW);
      }
      block_found[i] = true;  
      }
    } else {
      block_found[i] = false;
    }
    delay(100);
  }
}
