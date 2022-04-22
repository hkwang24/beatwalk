package com.example.beatwalk;

import android.Manifest;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.beatwalk.R;
import com.example.beatwalk.bluetooth.Bluetooth;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

public class SongOne extends AppCompatActivity {

    private ProgressBar pgsBar;
    private int i = 0;
    private boolean passes = true;
    private Handler hdlr = new Handler();
    private Bluetooth btHandler;
    private Handler viewHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_song_one);

         btHandler = new Bluetooth();
        System.out.println("Trying to set up bluetooth");
         btHandler.setup();
         System.out.println("setup complete");
         btHandler.start();
         System.out.println("started");
        // CHECKING PERMISSIONS HERE

        pgsBar = (ProgressBar) findViewById(R.id.pBar);

        Button button= (Button)findViewById(R.id.update_button);


        viewHandler = new Handler();
        postHandler();

    }

    public char[][] parseInput(String str) {
        int counter = 0;
        String remove_semicolon = str.substring(0, str.length()-1);
        String[] arr = remove_semicolon.split(",");
        System.out.println(arr);
        char[][] output = new char[2][8];
        for (int i = 0; i < 8; i++) {
            char val = getValue(Integer.parseInt(arr[counter]));
            output[0][i] = val;
            //System.out.println(val);
            counter++;
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

    //functin takes int returns char

    public char getValue(int i) {
        if (620 <= i && i <= 635) {
            return 'q';
        } else if (405 <= i && i <= 415) {
            return 'h';
        } else if (172 <= i && i <= 180) {
            return 'r';
        } else if (928 <= i && i <= 931) {
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

    public boolean[] checkSong(char[][] input, char[][] expected) {
        boolean array[];
        array = new boolean[8];
        for (int i = 0; i < input[0].length; i++) {
            if (input[0][i] == expected[0][i] && input[1][i] == expected[1][i]) {
                array[i] = true;
            }
        }
        return array;
    }

    public void postHandler() {
        hdlr.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (btHandler.hasMessage()) {
                String msg = btHandler.getMessage();
                System.out.println(msg);
                char[][] input = parseInput(msg);
                char[] notes = input[1];
                char[] rhythm = input[0];
                
                int[] note_ids = {R.id.let_pos1, R.id.let_pos2, R.id.let_pos3, R.id.let_pos4, R.id.let_pos5, R.id.let_pos6, R.id.let_pos7, R.id.let_pos8};
                int[] q_note_ids = {R.id.qnote_pos1, R.id.qnote_pos2, R.id.qnote_pos3, R.id.qnote_pos4, R.id.qnote_pos5, R.id.qnote_pos6, R.id.qnote_pos7, R.id.qnote_pos8};
                int[] h_note_ids = {R.id.hnote_pos1, R.id.hnote_pos2, R.id.hnote_pos3, R.id.hnote_pos4, R.id.hnote_pos5, R.id.hnote_pos6, R.id.hnote_pos7, R.id.hnote_pos8};

                for (int i = 0; i < notes.length; i++) {
                    ImageView note_block = (ImageView) findViewById(note_ids[i]);

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
                    } else {
                        note_block.setVisibility(View.INVISIBLE);
                    }
                }

                for (int i = 0; i < rhythm.length; i++) {
                    if (rhythm[i] == 'q') {
                        ImageView rhy_block = (ImageView) findViewById(q_note_ids[i]);
                        rhy_block.setImageResource(R.drawable.quarter_note);
                        rhy_block.setVisibility(View.VISIBLE);
                    } else if (rhythm[i] == 'r') {
                        ImageView rhy_block = (ImageView) findViewById(q_note_ids[i]);
                        rhy_block.setImageResource(R.drawable.rest_note);
                        rhy_block.setVisibility(View.VISIBLE);
                    } else if (rhythm[i] == 'h') {
                        ImageView rhy_block = (ImageView) findViewById(h_note_ids[i]);
                        rhy_block.setVisibility(View.VISIBLE);
                    } else {
                        ImageView rhy_block = (ImageView) findViewById(h_note_ids[i]);
                        rhy_block.setVisibility(View.INVISIBLE);
                        ImageView rhy_block2 = (ImageView) findViewById(q_note_ids[i]);
                        rhy_block2.setVisibility(View.INVISIBLE);
                    }
                }

                char[][] input2 = {notes, rhythm};
                char[] expected_letters = {'e', 'd', 'c', 'd', 'e', 'e', 'e', ' '};
                char[] expected_notes = {'q', 'q', 'q', 'q', 'q', 'q', 'h', ' '};
                char[][] expected = {expected_letters, expected_notes};
                final boolean[] correct = checkSong(input2, expected);

                final ImageView lyric1 = (ImageView) findViewById(R.id.lyric1);
                final ImageView lyric2 = (ImageView) findViewById(R.id.lyric2);
                final ImageView lyric3 = (ImageView) findViewById(R.id.lyric3);
                final ImageView lyric4 = (ImageView) findViewById(R.id.lyric4);
                final ImageView lyric5 = (ImageView) findViewById(R.id.lyric5);
                final ImageView lyric6 = (ImageView) findViewById(R.id.lyric6);
                final ImageView lyric7 = (ImageView) findViewById(R.id.lyric7);

                final ImageView error1 = (ImageView) findViewById(R.id.error1);
                final ImageView error2 = (ImageView) findViewById(R.id.error2);
                final ImageView error3 = (ImageView) findViewById(R.id.error3);
                final ImageView error4 = (ImageView) findViewById(R.id.error4);
                final ImageView error5 = (ImageView) findViewById(R.id.error5);
                final ImageView error6 = (ImageView) findViewById(R.id.error6);
                final ImageView error7 = (ImageView) findViewById(R.id.error7);
                final ImageView error8 = (ImageView) findViewById(R.id.error8);

                final ImageView tryAgain = (ImageView) findViewById(R.id.try_again);
                final ImageView greatJob = (ImageView) findViewById(R.id.great_job);

                for (int i = 0; i < 8; i++) {
                    if (!correct[i]) {
                        passes = false;
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
                                    if (i == 2) {
                                        lyric1.setVisibility(View.VISIBLE);
                                        if (!correct[0]) {
                                            error1.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (i == 15) {
                                        lyric2.setVisibility(View.VISIBLE);
                                        if (!correct[1]) {
                                            error2.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (i == 27) {
                                        lyric3.setVisibility(View.VISIBLE);
                                        if (!correct[2]) {
                                            error3.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (i == 40) {
                                        lyric4.setVisibility(View.VISIBLE);
                                        if (!correct[3]) {
                                            error4.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (i == 52) {
                                        lyric5.setVisibility(View.VISIBLE);
                                        if (!correct[4]) {
                                            error5.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (i == 65) {
                                        lyric6.setVisibility(View.VISIBLE);
                                        if (!correct[5]) {
                                            error6.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (i == 77) {
                                        lyric7.setVisibility(View.VISIBLE);
                                        if (!correct[6]) {
                                            error7.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (i == 90) {
                                        if (!correct[7]) {
                                            error8.setVisibility(View.VISIBLE);
                                        }
                                    }
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

                        hdlr.post(new Runnable() {
                            public void run() {
                                if (passes) {
                                    greatJob.setVisibility(View.VISIBLE);
                                } else {
                                    tryAgain.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        try {
                            // Sleep for 100 milliseconds to show the progress slowly.
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        pgsBar.setProgress(0);
                        lyric1.setVisibility(View.INVISIBLE);
                        lyric2.setVisibility(View.INVISIBLE);
                        lyric3.setVisibility(View.INVISIBLE);
                        lyric4.setVisibility(View.INVISIBLE);
                        lyric5.setVisibility(View.INVISIBLE);
                        lyric6.setVisibility(View.INVISIBLE);
                        lyric7.setVisibility(View.INVISIBLE);

                        error1.setVisibility(View.INVISIBLE);
                        error2.setVisibility(View.INVISIBLE);
                        error3.setVisibility(View.INVISIBLE);
                        error4.setVisibility(View.INVISIBLE);
                        error5.setVisibility(View.INVISIBLE);
                        error6.setVisibility(View.INVISIBLE);
                        error7.setVisibility(View.INVISIBLE);
                        error8.setVisibility(View.INVISIBLE);

                        greatJob.setVisibility(View.INVISIBLE);
                        tryAgain.setVisibility(View.INVISIBLE);
                    }
                }).start();
            }
                hdlr.postDelayed(this, 1000);
        }

        }, 1000);
    }
}

