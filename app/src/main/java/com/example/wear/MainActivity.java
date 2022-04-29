package com.example.wear;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wear.databinding.ActivityMainBinding;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    TextView txtDateTime;
    Button btnTime;
    Calendar dateTime = Calendar.getInstance();
    Button btnStartClock;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartClock = findViewById(R.id.btnStartClock);
        txtDateTime = findViewById(R.id.txtDateTime);
        btnTime = findViewById(R.id.btnTime);

        btnStartClock.setOnClickListener(view -> {
            long seconds = (dateTime.getTimeInMillis() - System.currentTimeMillis()) / 1000;
            seconds = seconds < 0 ? 24 * 60 * 60 + seconds: seconds;
            Toast.makeText(this, "Будильник установлен!", Toast.LENGTH_LONG).show();
            //Toast.makeText(this, String.valueOf(seconds), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, Alarm.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds * 1000L - 60, PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0));
        });


        txtDateTime.setText(DateUtils.formatDateTime(this, dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));

        TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateTime.set(Calendar.MINUTE, minute);
                txtDateTime.setText(DateUtils.formatDateTime(getApplicationContext(), dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
            }
        };

        btnTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TimePickerDialog(MainActivity.this, t,
                        dateTime.get(Calendar.HOUR_OF_DAY),
                        dateTime.get(Calendar.MINUTE), true).show();
            }
        });
    }
}