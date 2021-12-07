const int bpm = 60; //max is 120 since time for thing to go down and up is 1s
int period; //in ms

const int playButtonPin = 2;
const int saveButtonPin = 3; //dont need until app integration

const int SRSerialPin = 4;
const int SRClockPin = 5;
const int SROEPin = 6;

const int pressedPin[] = {7, 8, 9, 10}; // {7 9}
                                        // {8 10}

const int buzzer1Pin = 11;
const double buzzer1Freq = 261.63; //C note
const int buzzer2Pin = 12; //2 because buzzer
const double buzzer2Freq = 293.665; //D note

int thingState[12] = {}; 
//2 motor control bits (up, down) + 1 LED bit per thing (idk what to call them, pin is a bad name)
//motor control and LED bits for each thing are adjacent

const int thingCount = 4;

bool song[4] = {}; //le song is 4 bytes
bool playing = false;
bool firstLoop = true;

void setup() {
  period = 1000 * (bpm / 60);
  pinMode(playButtonPin, INPUT);
  pinMode(saveButtonPin, INPUT);
  pinMode(SRSerialPin, OUTPUT);
  pinMode(SRClockPin, OUTPUT);
  pinMode(SROEPin, OUTPUT);
  for (int i = 0; i < thingCount; i++) {
    pinMode(pressedPin[i], INPUT);
  }
  pinMode(buzzer1Pin, OUTPUT);
  pinMode(buzzer2Pin, OUTPUT);
}

void loop() {
  if (playing) {
    while (digitalRead(playButtonPin) == LOW) {
      //play the song
      for (int i = 0; i < thingCount / 2; i++) {
        thingState[i * 3] = 0; //up off
        thingState[(i + 1) * 3] = song[i]; //down if set
      }
      writeToSR();
      if (firstLoop) {
        delay(500); //will need to fine tune for actual up/down time
        firstLoop = false;
      } else {
        delay(period - 500); //delay accounting for movement time of motors
      }
      
      playNotes(0);
      
      for (int i = 0; i < thingCount / 2; i++) {
        thingState[i * 3] = song[i]; //up if set
        thingState[(i + 1) * 3] = 0; //down off
      }
      writeToSR();
      delay(period - 500);

      for (int i = thingCount / 2; i < thingCount; i++) {
        thingState[i * 3] = 0;
        thingState[(i + 1) * 3] = song[i];
      }
      writeToSR();
      delay(period - 500);

      playNotes(1);
      
      for (int i = thingCount / 2; i < thingCount; i++) {
        thingState[i * 3] = song[i];
        thingState[(i + 1) * 3] = 0;
      }
      writeToSR();
      delay(period - 500);
    }
    playing = false;
    firstLoop = true;
    
  } else {
    while (digitalRead(playButtonPin) == LOW) { //while play button isn't pressed
      for (int i = 0; i < thingCount; i++) {
        if (digitalRead(pressedPin[i + 7]) == HIGH) {
          song[i] = song[i] ? false : true; //record song from pressed things
        }
        thingState[(i + 1) * 3] = song[i]; //update thing output state
      }
      writeToSR(); //update LEDs
    }
    playing = true;
  }
}

//writes to shift register and outputs
void writeToSR() {
  //push thing state into SR
  for (int i = 0; i < thingCount * 3; i++) {
    digitalWrite(SRSerialPin, thingState[i] ? HIGH : LOW);
    digitalWrite(SRClockPin, HIGH);
    digitalWrite(SRClockPin, LOW);
  }
  //write thing state to things
  digitalWrite(SROEPin, HIGH);
  digitalWrite(SROEPin, LOW);
}

//plays notes depending on song
void playNotes(int beat) { //beat is 0 indexed
  if (song[2 * beat]) {
    tone(buzzer1Pin, buzzer1Freq);
  } else {
    noTone(buzzer1Pin);
  }
  if (song[2 * beat + 1]) {
    tone(buzzer2Pin, buzzer2Freq);
  } else {
    noTone(buzzer2Pin);
  }
}
