package com.example.beatwalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpToExplore(View view) {
        Intent i = new Intent(this, ExploreActivity.class);
        startActivityForResult(i, 2);
    }

    public void jumpToPlay(View view) {
        Intent i = new Intent(this, PlayActivity.class);
        startActivityForResult(i, 2);
    }
}
