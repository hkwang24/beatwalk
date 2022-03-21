const double bpm = 120;
double period; //in ms

const int playButtonPin = 1;

const int ledPin[] = {2, 3, 4, 5}; // {2 4}
                                   // {3 5}

const int buttonPin[] = {6, 7, 8, 9}; // {6 8}
                                      // {7 9}

const int buzzer1Pin = 10;
const double buzzer1Freq = 130.81; //C3 note
const int buzzer2Pin = 11; //2 because buzzer
const double buzzer2Freq = 391.995; //G4 note

const int thingCount = 4;

int song[4] = {}; //le song is 4 bytes
bool playing = false;

int lastButtonState[4] = {};
int currentButtonState[4] = {};

int lastPlayButtonState = 0;
int currentPlayButtonState = 0;

unsigned long timeNow;
bool breakMe = false;

int incomingByte = 0; //for incoming serial data - write 1 to play song and write 0 to stop song

void setup() {
  Serial.begin(9600);
  period = 1000 * (60 / bpm);
  pinMode(playButtonPin, INPUT);
  for (int i = 0; i < thingCount; i++) {
    pinMode(ledPin[i], OUTPUT);
    pinMode(buttonPin[i], INPUT);
  }
  pinMode(buzzer1Pin, OUTPUT);
  pinMode(buzzer2Pin, OUTPUT);
}

void loop() {
  if ((playing || incomingByte) && (song[0] || song[1] || song[2] || song[3])) {
    for (int i = 0; i < thingCount; i++) {
      digitalWrite(ledPin[i], LOW);
    }
    currentPlayButtonState = 0;
    lastPlayButtonState = 0;
    while (1) {
      //play the song
      noTone(buzzer1Pin);
      noTone(buzzer2Pin);
      for (int i = 0; i < thingCount / 2; i++) {
        digitalWrite(ledPin[i], song[i]);
        digitalWrite(ledPin[i + 2], LOW);
      }
      if (song[0]) {
        tone(buzzer1Pin, buzzer1Freq);
      }
      if (song[1]) {
        tone(buzzer2Pin, buzzer2Freq);
      }
      
      delayFunc(period);
      
      if (breakMe) {
        breakMe = false;
        break;
      }
      
      noTone(buzzer1Pin);
      noTone(buzzer2Pin);
      
      for (int i = 0; i < thingCount / 2; i++) {
        digitalWrite(ledPin[i], LOW);
        digitalWrite(ledPin[i + 2], song[i + 2]);
      }

      if (song[2]) {
        tone(buzzer1Pin, buzzer1Freq);
      }
      if (song[3]) {
        tone(buzzer2Pin, buzzer2Freq);
      }

      delayFunc(period);

      if (breakMe) {
        breakMe = false;
        break;
      }
    }
    playing = false;
    noTone(buzzer1Pin);
    noTone(buzzer2Pin);
    
  } else {
    currentPlayButtonState = 0;
    lastPlayButtonState = 0;
    while (!(lastPlayButtonState == HIGH && currentPlayButtonState == LOW) && !incomingByte) {
      for (int i = 0; i < thingCount; i++) {
        lastButtonState[i] = currentButtonState[i];
        currentButtonState[i] = digitalRead(buttonPin[i]);

        if (lastButtonState[i] == HIGH && currentButtonState[i] == LOW) {
          song[i] = !song[i];
        }
        digitalWrite(ledPin[i], song[i]);
      }
      lastPlayButtonState = currentPlayButtonState;
      currentPlayButtonState = digitalRead(playButtonPin);
      if (Serial.available() > 0) {
        incomingByte = Serial.read();
      }
    }
    playing = true;
  }
}

void delayFunc(double per) {
  timeNow = millis();
  while (millis() < timeNow + per) {
    lastPlayButtonState = currentPlayButtonState;
    currentPlayButtonState = digitalRead(playButtonPin);
    if (Serial.available() > 0) {
      incomingByte = Serial.read();
    }
    if ((lastPlayButtonState == HIGH && currentPlayButtonState == LOW) || !incomingByte) {
      breakMe = true;
      break;
    }
  }
}
