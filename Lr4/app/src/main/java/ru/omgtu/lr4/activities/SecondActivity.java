package ru.omgtu.lr4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import ru.omgtu.lr4.R;

public class SecondActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnOk = findViewById(R.id.btnOk);

        // Получаем имя из Intent
        String userName = getIntent().getStringExtra("user_name");
        setTitle("Привет, " + (userName != null ? userName : "гость"));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем дату
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                // Получаем время (для новых API нужно по-другому)
                int hour, minute;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                // Создаем Calendar и получаем миллисекунды
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, hour, minute, 0);
                long millis = calendar.getTimeInMillis();

                // Отправляем результат обратно
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_date", millis);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}