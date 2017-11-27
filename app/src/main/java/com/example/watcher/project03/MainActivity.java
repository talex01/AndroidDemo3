package com.example.watcher.project03;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView salary;
    int salaryInt = 0;
    boolean ok = false;
    EditText age, name;

    // правильные ответы на вопросы
    private int[] correctAnswer = {R.id.q1rb3, R.id.q2rb2, R.id.q3rb4, R.id.q4rb1, R.id.q5rb2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // нельзя отвечать на вопросы, пока не заполнены поля имени и возраста
        disableOrEnableQuestions(false);

        age = (EditText) findViewById(R.id.age2);
        age.setEnabled(false); // нельзя указать возраст до тех пор, пока не указано имя

        name = (EditText) findViewById(R.id.name2);

        // обработчик ввода имени. Длина не должны быть меньше 4 символов
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (name.length() > 3) {
                    age.setEnabled(true);
                } else {
                    age.setEnabled(false);
                    disableOrEnableQuestions(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // обработчик ввода возраста. Возраст должен состоять из 2 цифр, тогда можно отвечать на вопросы
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (age.length() > 1) {
                    disableOrEnableQuestions(true);
                } else {
                    disableOrEnableQuestions(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // обработчик ползунка зарплаты
        salary = (TextView) findViewById(R.id.salary3);
        SeekBar seekBar = ((SeekBar) findViewById(R.id.salary2));
        seekBar.setOnSeekBarChangeListener(this);
     }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        salaryInt = seekBar.getProgress() * 100;
        salary.setText(String.valueOf("$" + salaryInt));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void disableOrEnableQuestions(boolean b) {
        RadioGroup rg = (RadioGroup) findViewById(R.id.questions);
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(b);
        }

    }

    // замена шрифта для всей активности
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void validate(View view) {
        int result = 0;

        for (int i : correctAnswer) {
            if (((RadioButton) findViewById(i)).isChecked()) {
                result += 2;
            }
        }

        CheckBox trip = (CheckBox) findViewById(R.id.trip);
        CheckBox skill = (CheckBox) findViewById(R.id.skill);
        CheckBox experience = (CheckBox) findViewById(R.id.experience);

        if (trip.isChecked()) result++;
        if (skill.isChecked()) result++;
        if (experience.isChecked()) result += 2;

        // подсчет результатов
        if (result < 10) {
            Toast.makeText(MainActivity.this, "К сожалению, Вы не прошли тест", Toast.LENGTH_LONG).show();
            ok = false;
        } else {
            ok = true;
        }

        if (Integer.parseInt(age.getText().toString()) < 21 || Integer.parseInt(age.getText().toString()) > 40) {
            Toast.makeText(MainActivity.this, "К сожалению, Вы нам не подходите по возрасту", Toast.LENGTH_LONG).show();
            ok = false;
        }

        if (salaryInt < 800 || salaryInt > 1600) {
            Toast.makeText(MainActivity.this, "К сожалению, мы не готовы столько платить", Toast.LENGTH_LONG).show();
            ok = false;
        }

        if (ok) {
            Toast.makeText(MainActivity.this, "Вы нам подходите! ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("EXTRA_NAME", name.getText().toString());
            intent.putExtra("EXTRA_AGE", age.getText().toString());
            intent.putExtra("EXTRA_RESULT", String.valueOf(result));
            intent.putExtra("EXTRA_SALARY", String.valueOf(salaryInt));
            startActivity(intent);
        }
    }
}
