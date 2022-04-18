package com.example.beatwalk;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beatwalk.R;
import com.example.beatwalk.bluetooth.Bluetooth;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

public class SongOne extends AppCompatActivity {

    Bluetooth btHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_song_one);

        btHandler = new Bluetooth();
        System.out.println("Trying to set up bluetooth");
        btHandler.setup();
        System.out.println("Bluetooth setup");

        Button button= (Button)findViewById(R.id.update_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                System.out.println("BUTTON PRESSED");
//
//                String msg = btHandler.getMessage();

                char[] notes = {'c', 'b', ' ', 'a', ' ', ' ', 'e', 'f'};
                char[] rhythm = {'q','h', ' ', 'h', ' ', 'r', 'q', 'q'};
                int[] note_ids = {R.id.let_pos1, R.id.let_pos2, R.id.let_pos3, R.id.let_pos4, R.id.let_pos5, R.id.let_pos6, R.id.let_pos7, R.id.let_pos8};
                int[] q_note_ids = {R.id.qnote_pos1, R.id.qnote_pos2, R.id.qnote_pos3, R.id.qnote_pos4, R.id.qnote_pos5, R.id.qnote_pos6, R.id.qnote_pos7, R.id.qnote_pos8};
                int[] h_note_ids = {R.id.hnote_pos1, R.id.hnote_pos2, R.id.hnote_pos3, R.id.hnote_pos4, R.id.hnote_pos5, R.id.hnote_pos6, R.id.hnote_pos7};

                for (int i = 0; i < notes.length; i++) {
                    ImageView note_block = (ImageView)findViewById(note_ids[i]);

                    if (notes[i] == 'a') {
                        note_block.setImageResource(R.drawable.a_note);
                        note_block.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'b') {
                        note_block.setImageResource(R.drawable.b_note);
                        note_block.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'c') {
                        note_block.setImageResource(R.drawable.c_note);
                        note_block.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'd') {
                        note_block.setImageResource(R.drawable.d_note);
                        note_block.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'e') {
                        note_block.setImageResource(R.drawable.e_note);
                        note_block.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'f') {
                        note_block.setImageResource(R.drawable.f_note);
                        note_block.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'g') {
                        note_block.setImageResource(R.drawable.g_note);
                        note_block.setVisibility(View.VISIBLE);
                    }
                }

                for (int i = 0; i < rhythm.length; i++) {
                    if (rhythm[i] == 'q') {
                        ImageView rhy_block = (ImageView)findViewById(q_note_ids[i]);
                        rhy_block.setImageResource(R.drawable.quarter_note);
                        rhy_block.setVisibility(View.VISIBLE);
                    } else if (rhythm[i] == 'r') {
                        ImageView rhy_block = (ImageView) findViewById(q_note_ids[i]);
                        rhy_block.setImageResource(R.drawable.rest_note);
                        rhy_block.setVisibility(View.VISIBLE);
                    } else if (rhythm[i] == 'h') {
                        ImageView rhy_block = (ImageView) findViewById(h_note_ids[i]);
                        rhy_block.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}

