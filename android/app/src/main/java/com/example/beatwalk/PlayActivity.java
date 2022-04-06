package com.example.beatwalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    public void jumpToSong(View view) {
        Intent i = new Intent(this, SongOne.class);
        startActivityForResult(i, 2);
    }

}