package com.example.wb.kkoikrzyyk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //0-yellow, 1-red, 2-none
    int activePlayer = 0;
    int [] gameScore = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions ={{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    int yellow = 0;
    int red = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCell = Integer.parseInt(counter.getTag().toString());
        if(gameScore[tappedCell] == 2){
            gameScore[tappedCell] = activePlayer;
            counter.setTranslationY(-1000f);
            if(activePlayer == 0){
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360f).setDuration(300);
            for(int[] winningPosition : winningPositions){
                if(gameScore[winningPosition[0]] == gameScore[winningPosition[1]] &&
                        gameScore[winningPosition[1]] == gameScore[winningPosition[2]] &&
                        gameScore[winningPosition[0]] != 2){
                    LinearLayout messageLayout = findViewById(R.id.messageLayout);
                    TextView messageText = findViewById(R.id.messageText);
                    String winner = "Czerwony";
                    if(gameScore[winningPosition[0]] == 0){
                        winner = "Żółty";
                        yellow++;
                    } else {
                        red++;
                    }
                    TableLayout tableLayout = findViewById(R.id.tableLayout);
                    tableLayout.setVisibility(View.INVISIBLE);
                    messageText.setText(winner+" wygrał");
                    TextView scoreText = findViewById(R.id.scoreText);
                    scoreText.setText("Czerwony:"+Integer.toString(red)+" Żółty:"+Integer.toString(yellow));
                    messageLayout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameOver = true;
                    for (int counterState : gameScore){
                        if(counterState == 2){
                            gameOver = false;
                        }
                    }
                    if(gameOver){
                        LinearLayout messageLayout = findViewById(R.id.messageLayout);
                        TextView messageText = findViewById(R.id.messageText);
                        messageText.setText("REMIS");
                        TableLayout tableLayout = findViewById(R.id.tableLayout);
                        tableLayout.setVisibility(View.INVISIBLE);
                        messageLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout messageLayout = findViewById(R.id.messageLayout);
        messageLayout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for(int i = 0; i < gameScore.length; i++){
            gameScore[i] = 2;
        }
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        for(int i = 0; i < tableLayout.getChildCount(); i++){
            for(int j = 0; j < ((TableRow) tableLayout.getChildAt(i)).getChildCount(); j++){
                ((ImageView)((TableRow) tableLayout.getChildAt(i)).getChildAt(j)).setImageResource(0);
            }
        }
        tableLayout.setVisibility(View.VISIBLE);
    }
}
