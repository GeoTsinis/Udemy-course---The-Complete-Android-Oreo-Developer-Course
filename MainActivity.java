package com.example.braintrainerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button resetButton;
    TextView sumTextView;
    TextView timerTextView;
    ConstraintLayout gameLayout;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQs = 0;
    TextView resultTextView;
    TextView scoreTextView;


    public void play(View view) {
        goButton.setVisibility(View.INVISIBLE);
        startGame();
    }

    public void chooseAnswer(View view) {
        if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            resultTextView.setText("Correct!");
            score++;
        } else {
            resultTextView.setText("Wrong :(");
        }
        resultTextView.setVisibility(View.VISIBLE);
        numberOfQs++;
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQs));
        answers.clear();
        generateNumbers();
    }

    public void resetGame(View view){
        score = 0;
        numberOfQs = 0;
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQs));
        gameLayout.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
        resetButton.setVisibility(View.INVISIBLE);
        goButton.setVisibility(View.VISIBLE);
    }

    public void startGame(){
        gameLayout.setVisibility(View.VISIBLE);
        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        answers.clear();
        generateNumbers();
        startCountDown();
    }

    public void generateNumbers(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = rand.nextInt(4);

        for (int i=0; i<4; i++){
            if (i == locationOfCorrectAnswer) {
                answers.add(a+b);
            } else {
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == a+b) {
                    wrongAnswer = rand.nextInt(41);
                }
                if(!answers.contains(wrongAnswer)){
                    answers.add(wrongAnswer);
                }
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void startCountDown() {
        new CountDownTimer(30100,1000){
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000) +"s");
            }

            @Override
            public void onFinish() {
                if(numberOfQs == 0) {
                    resultTextView.setText("Fell asleep?");
                    resultTextView.setVisibility(View.VISIBLE);
                } else {
                    resultTextView.setText("Done!");
                }
                resetButton.setVisibility(View.VISIBLE);
                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = findViewById(R.id.goButton);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        sumTextView = findViewById(R.id.sumTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        resultTextView = findViewById(R.id.resultTextView);
        timerTextView = findViewById(R.id.timerTextView);
        resetButton = findViewById(R.id.tryAgainButton);
        gameLayout = findViewById(R.id.gameLayout);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
    }
}