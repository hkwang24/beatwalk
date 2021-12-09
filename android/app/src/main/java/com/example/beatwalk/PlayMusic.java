package com.example.beatwalk;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beatwalk.R;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

public class PlayMusic extends AppCompatActivity {

    //MediaPlayer mediaplayer;  //we use this to play the audio stream i believe

    private Button play;
    private Button pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music);
        //mediaplayer = MediaPlayer.create(PlayMusic.this, )
        //context is MediaPlayer, next field should be audiofile
        play = (Button) findViewById(R.id.play_button);

        pause = (Button) findViewById(R.id.pause_button);
        play.setBackgroundResource(R.color.gray);
        pause.setBackgroundResource(R.color.gray);
    }



    public void playMusic(View view) {
        //this where u put ur magical arduino stuff and connect to the mediaplayer
        //with smth like mediaplayer.play()
        play.setBackgroundResource(R.color.gray);
        pause.setBackgroundResource(R.color.blue);

    }

    public void pauseMusic(View view) {
        //mediaplayer.pause(
        pause.setBackgroundResource(R.color.gray);
        play.setBackgroundResource(R.color.blue);
    }




}
