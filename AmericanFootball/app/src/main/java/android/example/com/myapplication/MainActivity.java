package android.example.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private int i = 1;
    private long lostTime = 0;
    private int scoreTeamA = 0;
    private int scoreTeamB = 0;
    private long timeWhenPaused = 0;
    private long timeWhenStopped = 0;
    private long timeWhenStopped1 = 0;

    private Button touchdownTeamA;
    private Button touchdownTeamB;
    private Button fieldGoalTeamA;
    private Button fieldGoalTeamB;
    private Button oneExtraTeamA;
    private Button oneExtraTeamB;
    private Button twoExtraTeamA;
    private Button twoExtraTeamB;
    private Button safetyTeamA;
    private Button safetyTeamB;
    private Button quarters;
    private Button resetScore;
    private Button resetTimer;
    private Chronometer chronometer;
    private Chronometer chronometer2;
    private Button pauseTimer;
    private Button resumeTimer;
    private Button stopTimer;
    private Button overTime;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        /* Touchdown click event of Team A*/
        touchdownTeamA = findViewById(R.id.sexForTeamA);
        touchdownTeamA.setOnClickListener(view -> {
            scoreTeamA += 6;
            displayForTeamA(scoreTeamA);
        });

        /* Field Goal click event of Team A*/
        fieldGoalTeamA = findViewById(R.id.threeForTeamA);
        fieldGoalTeamA.setOnClickListener(view -> {
            scoreTeamA += 3;
            displayForTeamA(scoreTeamA);
        });

        /* one-Extra-Point click event of Team A*/
        oneExtraTeamA = findViewById(R.id.oneExtraForTeamA);
        oneExtraTeamA.setOnClickListener(view -> {
            scoreTeamA++;
            displayForTeamA(scoreTeamA);
        });

        /* Two-Extra-Points click event of Team A*/
        twoExtraTeamA = findViewById(R.id.twoExtraForTeamA);
        twoExtraTeamA.setOnClickListener(view -> {
            scoreTeamA += 2;
            displayForTeamA(scoreTeamA);
        });

        /* Safety click event of Team A*/
        safetyTeamA = findViewById(R.id.twoForTeamA);
        safetyTeamA.setOnClickListener(view -> {
            scoreTeamA += 2;
            displayForTeamA(scoreTeamA);
        });

        /* Touchdown click event of Team B*/
        touchdownTeamB = findViewById(R.id.sexForTeamB);
        touchdownTeamB.setOnClickListener(view -> {
            scoreTeamB += 6;
            displayForTeamB(scoreTeamB);
        });

        /* Field Goal click event of Team B*/
        fieldGoalTeamB = findViewById(R.id.threeForTeamB);
        fieldGoalTeamB.setOnClickListener(view -> {
            scoreTeamB += 3;
            displayForTeamB(scoreTeamB);
        });

        /* one-Extra-Point click event of Team B*/
        oneExtraTeamB = findViewById(R.id.oneExtraForTeamB);
        oneExtraTeamB.setOnClickListener(view -> {
            scoreTeamB++;
            displayForTeamB(scoreTeamB);
        });

        /* Two-Extra-Points click event of Team B*/
        twoExtraTeamB = findViewById(R.id.twoExtraForTeamB);
        twoExtraTeamB.setOnClickListener(view -> {
            scoreTeamB += 2;
            displayForTeamB(scoreTeamB);
        });

        /* Safety click event of Team B*/
        safetyTeamB = findViewById(R.id.twoForTeamB);
        safetyTeamB.setOnClickListener(view -> {
            scoreTeamB += 2;
            displayForTeamB(scoreTeamB);
        });

        /* Reset the score for Both Team A and Team B*/
        resetScore = findViewById(R.id.resetScore);
        resetScore.setOnClickListener(view -> {
            scoreTeamA = 0;
            scoreTeamB = 0;
            displayForTeamA(scoreTeamA);
            displayForTeamB(scoreTeamB);
        });

        /* Reset Time*/
        resetTimer = findViewById(R.id.reset_timer);
        resetTimer.setOnClickListener((View.OnClickListener) view -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenStopped = 0;
            chronometer.stop();
            chronometer2.setBase(SystemClock.elapsedRealtime());
            lostTime = 0;
            chronometer2.stop();
        });

        /*Chronometer click event to start time*/
        chronometer = findViewById(R.id.timer);
        chronometer2 = findViewById(R.id.timer2);
        chronometer.setOnClickListener(view -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            chronometer2.setBase(SystemClock.elapsedRealtime());
            chronometer2.start();
        });

        /*Pause Button click event to pause time of the match*/
        pauseTimer = findViewById(R.id.pause);
        pauseTimer.setOnClickListener(view -> {
            timeWhenPaused = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            chronometer2.stop();
        });

        /*Pause Button click event to resume time of the match*/
        resumeTimer = findViewById(R.id.resume);
        resumeTimer.setOnClickListener(view -> {
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenPaused);
            chronometer.start();
            chronometer2.start();
        });

        /*Pause Button click event to stop time at the end of the match*/
        stopTimer = findViewById(R.id.stop);
        stopTimer.setOnClickListener(view -> {
            timeWhenStopped = chronometer.getBase() + SystemClock.elapsedRealtime();
            chronometer.stop();
            timeWhenStopped1 = chronometer2.getBase() + SystemClock.elapsedRealtime();
            chronometer2.stop();
        });

        /*Overtime Button click event to calculate the overtime of the match if there is*/
        overTime = findViewById(R.id.overtime);
        overTime.setOnClickListener(view -> {
            lostTime = (timeWhenStopped1 - timeWhenStopped) + SystemClock.elapsedRealtime();
            chronometer.setBase(lostTime);
            chronometer.stop();
            chronometer2.stop();
            chronometer2.setBase(SystemClock.elapsedRealtime());
        });

        /*Quarter Button click event to keep track of quarters of the match*/
        quarters = findViewById(R.id.quarter);
        quarters.setOnClickListener(view -> {
            i++;
            display(i);
        });

        /*Quarter Button long click event to reset the counter to 1*/
        quarters.setOnLongClickListener(view -> {
            i = 0;
            return false;
        });
    }

    /*Method to display Team A Score*/
    private void displayForTeamA(int score) {
        TextView scoreView = findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /*Method to display Team B Score*/
    private void displayForTeamB(int score) {
        TextView scoreView = findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /*Method to display quarter number*/
    private void display(int quarter) {
        quarters.setText(String.valueOf(quarter));
    }
}
