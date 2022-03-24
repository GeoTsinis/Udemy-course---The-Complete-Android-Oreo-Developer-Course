package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Δήλωση αντικειμένων
    TextView timerTextView;
    SeekBar timerSeekBar;
    Button goButton;
    CountDownTimer countDownTimer;
    // Έλεγχος αν το χρονόμετρο είναι σε λειτουργία
    boolean counterIsActive = false;

    // Επαναφορά του χρονομέτρου στην αρχική του κατάσταση
    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
    }

    public void buttonClicked(View view) {
        if (counterIsActive) {          // ελέγχουμε αν το χρονόμετρο τρέχει με κλίκ το σταματάμε
            resetTimer();
        }
        else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
    // τα * 1000 είναι για να κάνουμε τα ms σε s και το + 100 είναι για να τελειώνει μόλις πάει ακριβώς 0

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = new MediaPlayer().create(getApplicationContext(), R.raw.air_horn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    // μέθοδος για να απεικονίζονται τα λεπτά και τα δευτερόλεπτα
    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.countdownTextView);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}