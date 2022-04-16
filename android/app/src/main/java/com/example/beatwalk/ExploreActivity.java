package com.example.beatwalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);


        Button button= (Button)findViewById(R.id.update_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                char[] notes = {' ', 'd', ' ', 'e', ' ', 'c', 'b', 'f'};
                char[] rhythm = {'r','h', ' ', 'h', ' ', 'q', 'q', 'q'};
                int[] note_ids = {R.id.let_pos1, R.id.let_pos2, R.id.let_pos3, R.id.let_pos4, R.id.let_pos5, R.id.let_pos6, R.id.let_pos7, R.id.let_pos8};
                int[] q_note_ids = {R.id.qnote_pos1, R.id.qnote_pos2, R.id.qnote_pos3, R.id.qnote_pos4, R.id.qnote_pos5, R.id.qnote_pos6, R.id.qnote_pos7, R.id.qnote_pos8};
                int[] h_note_ids = {R.id.hnote_pos1, R.id.hnote_pos2, R.id.hnote_pos3, R.id.hnote_pos4, R.id.hnote_pos5, R.id.hnote_pos6, R.id.hnote_pos7};
                int[] notation = {R.id.notation1, R.id.notation2, R.id.notation3, R.id.notation4, R.id.notation5, R.id.notation6, R.id.notation7, R.id.notation8};

                for (int i = 0; i < notes.length; i++) {
                    ImageView note_block = (ImageView)findViewById(note_ids[i]);
                    ImageView notation_index = (ImageView)findViewById(notation[i]);

                    if (notes[i] == 'a') {
                        note_block.setImageResource(R.drawable.a_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(493);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'b') {
                        note_block.setImageResource(R.drawable.b_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(467);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'c') {
                        note_block.setImageResource(R.drawable.c_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(615);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'd') {
                        note_block.setImageResource(R.drawable.d_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(587);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'e') {
                        note_block.setImageResource(R.drawable.e_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(560);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'f') {
                        note_block.setImageResource(R.drawable.f_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(540);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'g') {
                        note_block.setImageResource(R.drawable.g_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(512);
                        notation_index.setVisibility(View.VISIBLE);
                    }
                }

                for (int i = 0; i < rhythm.length; i++) {
                    ImageView notation_index = (ImageView)findViewById(notation[i]);
                    if (rhythm[i] == 'q') {
                        ImageView rhy_block = (ImageView)findViewById(q_note_ids[i]);
                        rhy_block.setImageResource(R.drawable.quarter_note);
                        rhy_block.setVisibility(View.VISIBLE);
                        if (notes[i] == 'c') {
                            notation_index.setImageResource(R.drawable.c_notation_q);
                        } else {
                            notation_index.setImageResource(R.drawable.q_notation);
                        }
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (rhythm[i] == 'r') {
                        ImageView rhy_block = (ImageView) findViewById(q_note_ids[i]);
                        rhy_block.setImageResource(R.drawable.rest_note);
                        rhy_block.setVisibility(View.VISIBLE);
                        notation_index.setImageResource(R.drawable.rest_notation);
                        notation_index.setTranslationY(496);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (rhythm[i] == 'h') {
                        ImageView rhy_block = (ImageView) findViewById(h_note_ids[i]);
                        rhy_block.setVisibility(View.VISIBLE);
                        if (notes[i] == 'c') {
                            notation_index.setImageResource(R.drawable.c_notation_h);
                        } else {
                            notation_index.setImageResource(R.drawable.h_notation);
                        }
                        notation_index.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
    }
}