package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private Button buttonStartPause;

    private boolean isRunning = false;
    private long startTime = 0L;
    private long elapsedTime = 0L;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStartPause = findViewById(R.id.buttonStartPause);
        Button buttonReset = findViewById(R.id.buttonReset);

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPauseTimer();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
    }

    private void startPauseTimer() {
        if (isRunning) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        isRunning = true;
        buttonStartPause.setText("Pause");
        startTime = SystemClock.uptimeMillis() - elapsedTime;
        handler.postDelayed(updateTimer, 0);
    }

    private void pauseTimer() {
        isRunning = false;
        buttonStartPause.setText("Start");
        elapsedTime = SystemClock.uptimeMillis() - startTime;
        handler.removeCallbacks(updateTimer);
    }

    private void resetTimer() {
        isRunning = false;
        buttonStartPause.setText("Start");
        elapsedTime = 0L;
        handler.removeCallbacks(updateTimer);
        updateTimerUI();
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = SystemClock.uptimeMillis() - startTime;
            updateTimerUI();
            handler.postDelayed(this, 100);
        }
    };

    private void updateTimerUI() {
        int minutes = (int) (elapsedTime / (60 * 1000));
        int seconds = (int) (elapsedTime % (60 * 1000) / 1000);
        int milliseconds = (int) (elapsedTime % 1000);

        String timerText = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
        textViewTimer.setText(timerText);
    }
}
