package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow , 1 = red.
    int [] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int [][] winningPositions = { {0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };
    int activePlayer = 0;
    int freeFields = gameState.length;
    boolean gameover = false;
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && !gameover) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(1800).setDuration(300);
            freeFields--;
            for (int i=0; i<winningPositions.length; i++) {
                    int [] winningPosition = winningPositions[i];
                    String winner = "";
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {
                    // someone won
                    gameover = true;
                    freeFields = gameState.length;
                    if (activePlayer == 0) {
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    winnerTextView.setText( winner +" has won!");
                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);
                    return;
                    //Toast.makeText(this, winner + " has won!", Toast.LENGTH_LONG).show();
                }

            }
            if (freeFields==0 && !gameover){
                gameover = true;
                freeFields=gameState.length;

                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                winnerTextView.setText( "It's a tie!");
                winnerTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }

        }
    }

    public void playAgain(View view){
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        //for some reason regular way crashes
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById( R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView)gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        for (int j=0; j<gameState.length; j++){
            gameState[j] = 2;
        }
        gameover = false;
        activePlayer = 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}