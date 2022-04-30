package com.example.mainapplicationpomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnStartStopMain;
    private TextView txtTimeMain;
    private CountDownTimer countDownTimer; //Для работы с таймером
    private long timeLeftInMillSeconds = 600000; //Это начальное значение таймера 10:00

    private boolean timerRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartStopMain = findViewById(R.id.btn_startstop_main);
        txtTimeMain = findViewById(R.id.txt_time_main);

        btnStartStopMain.setOnClickListener(v -> {
            startStop();
        });
        updateTimer(); //При включении приложения устанавливаем значения таймера по умолчанию
    }

    public void startStop() { //Включаем таймер - когда он выключен. Выключаем - когда включен.
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() { //Включение таймера
        timerRunning = true; //Отображаем, что таймер включён

        countDownTimer = new CountDownTimer(timeLeftInMillSeconds, 1000) { // Начало отсчёта, шаг счёта (мс)
            @Override
            public void onTick(long millisUntilFinished) { //millisUntilFinished - текущее время на таймере
                timeLeftInMillSeconds = millisUntilFinished;
                updateTimer(); //Мы меняем таймер в соответствии со значением timeLeftInMillSeconds каждый тик
                //Произошёл тик - поменяли в TextView в main_activity - алгоритм работы
            }

            @Override
            public void onFinish() { //Что делаем, когда таймер достигнет нуля
                btnStartStopMain.setText("FINISH"); //Таймер завершился - пишем соответстующую надпись на кнопке startstop
            }

        }.start(); //Включаем таймер

        btnStartStopMain.setText("START"); //Таймер заработал - меняем надпись на кнопке startstop
    }

    public void stopTimer() { //Выключение таймера
        countDownTimer.cancel(); //Приостанавливаем таймер
        timerRunning = false; //Отображаем, что таймер выключен
        btnStartStopMain.setText("START"); //Когда таймер выключен подсвечиваем соотв. фразу на кнопке startstop
    }

    public void updateTimer() { //Обновление данных таймера
        int minutes = (int) timeLeftInMillSeconds / 60000;
        int seconds= (int) timeLeftInMillSeconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) {
            timeLeftText += "0";
            timeLeftText += seconds;
        } else {
            timeLeftText += seconds;
        }

        txtTimeMain.setText(timeLeftText);
    }
}