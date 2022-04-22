package com.example.beatwalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ExploreActivity extends AppCompatActivity {

    private ProgressBar pgsBar;
    private int i = 0;
    private Handler hdlr = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        pgsBar = (ProgressBar) findViewById(R.id.pBar);

        Button button= (Button)findViewById(R.id.update_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String input = "630,910,910,630,410,890,410,0,1015,410,410,0,0,174,630,910;";
                char[][] parsed = parseInput(input);

                char[] notes = parsed[1];
                char[] rhythm = parsed[0];
                int[] note_ids = {R.id.let_pos1, R.id.let_pos2, R.id.let_pos3, R.id.let_pos4, R.id.let_pos5, R.id.let_pos6, R.id.let_pos7, R.id.let_pos8};
                int[] q_note_ids = {R.id.qnote_pos1, R.id.qnote_pos2, R.id.qnote_pos3, R.id.qnote_pos4, R.id.qnote_pos5, R.id.qnote_pos6, R.id.qnote_pos7, R.id.qnote_pos8};
                int[] h_note_ids = {R.id.hnote_pos1, R.id.hnote_pos2, R.id.hnote_pos3, R.id.hnote_pos4, R.id.hnote_pos5, R.id.hnote_pos6, R.id.hnote_pos7, R.id.hnote_pos8};
                int[] notation = {R.id.notation1, R.id.notation2, R.id.notation3, R.id.notation4, R.id.notation5, R.id.notation6, R.id.notation7, R.id.notation8};

                for (int i = 0; i < notes.length; i++) {
                    ImageView note_block = (ImageView)findViewById(note_ids[i]);
                    ImageView notation_index = (ImageView)findViewById(notation[i]);

                    if (notes[i] == 'a') {
                        note_block.setImageResource(R.drawable.a_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(533);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'b') {
                        note_block.setImageResource(R.drawable.b_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(507);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'c') {
                        note_block.setImageResource(R.drawable.c_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(655);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'd') {
                        note_block.setImageResource(R.drawable.d_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(622);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'e') {
                        note_block.setImageResource(R.drawable.e_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(606);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'f') {
                        note_block.setImageResource(R.drawable.f_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(580);
                        notation_index.setVisibility(View.VISIBLE);
                    } else if (notes[i] == 'g') {
                        note_block.setImageResource(R.drawable.g_note);
                        note_block.setVisibility(View.VISIBLE);
                        notation_index.setTranslationY(552);
                        notation_index.setVisibility(View.VISIBLE);
                    } else {
                        note_block.setVisibility(View.INVISIBLE);
                        notation_index.setVisibility(View.INVISIBLE);
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
                        notation_index.setTranslationY(527);
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

                    } else {
                        notation_index.setVisibility(View.INVISIBLE);
                        ImageView rhy_block1 = (ImageView) findViewById(q_note_ids[i]);
                        rhy_block1.setVisibility(View.INVISIBLE);
                        ImageView rhy_block2 = (ImageView) findViewById(h_note_ids[i]);
                        rhy_block2.setVisibility(View.INVISIBLE);
                    }
                }


                i = pgsBar.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 1;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {
                                    pgsBar.setProgress(i);
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        pgsBar.setProgress(0);
                    }
                }).start();
            }
        });
    }
    public char[][] parseInput(String str) {
        int counter = 0;
        String remove_semicolon = str.substring(0, str.length()-1);
        String[] arr = remove_semicolon.split(",");
        //System.out.println(arr);
        char[] notes = {' ', 'h', 'q', 'r'};
        char[] letters = {' ', 'a', 'b', 'c', 'd','e', 'f', 'g'};
        char[][] output = new char[2][8];
        for (int i = 0; i < 8; i++) {
            char val = getValue(Integer.parseInt(arr[counter]));
            System.out.println(val + "is letter: " + Arrays.binarySearch(letters, val));
            counter++;
            char val2 = getValue(Integer.parseInt(arr[counter]));
            System.out.println(val2 + "is note " + Arrays.binarySearch(notes, val2));

            if(Arrays.binarySearch(letters, val) >= 0 && Arrays.binarySearch(notes, getValue(Integer.parseInt(arr[counter]))) >= 0 ) {
                val = getValue(Integer.parseInt(arr[counter]));
                String temp = arr[counter - 1];
                arr[counter] = temp;
            }
            output[0][i] = val;

            if (val == 'r') {
                output[1][i] = ' ';
            } else if (val == 'h') {
                output[1][i] = getValue(Integer.parseInt(arr[counter]));
                if (counter+1 < 16) {
                    arr[counter+1] = "0";
                }
            } else {
                output[1][i] = getValue(Integer.parseInt(arr[counter]));
            }
            counter++;
        }

        return output;
    }

    public char getValue(int i) {
        if (620 <= i && i <= 635) {
            return 'q';
        } else if (405 <= i && i <= 415) {
            return 'h';
        } else if (172 <= i && i <= 180) {
            return 'r';
        } else if (900 <= i && i <= 940) {
            return 'e';
        } else if (885 <= i && i <= 892) {
            return 'd';
        } else if (1010 <= i && i <= 1020) {
            return 'c';
        } else if (89 <= i && i <= 97) {
            return 'g';
        } else if (695 <= i && i <= 700) {
            return 'b';
        } else if (836 <= i && i <= 845) {
            return 'f';
        } else {
            return ' ';
        }
    }
}