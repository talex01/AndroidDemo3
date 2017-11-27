package com.example.watcher.project03;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView nameView = (TextView) findViewById(R.id.nameResult2);
        TextView ageView = (TextView) findViewById(R.id.ageResult2);
        TextView resultView = (TextView) findViewById(R.id.result2);
        TextView salaryView = (TextView) findViewById(R.id.salaryResult2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            String name = intent.getStringExtra("EXTRA_NAME");
            String age = intent.getStringExtra("EXTRA_AGE");
            String result = intent.getStringExtra("EXTRA_RESULT");
            String salary = intent.getStringExtra("EXTRA_SALARY");

            nameView.setText(name);
            ageView.setText(age);
            resultView.setText(result);
            salaryView.setText(String.valueOf("$" + salary));
        }
    }

    // замена шрифта для всей активности
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void makeCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+381231234567"));
        startActivity(intent);
    }

    public void sendEmail(View view) {
        String[] email = {"Silicon@Valley.com"};
        String subject = "Хочу работать программистом";
        String message = "Возьмите на работу";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        // need this to prompts email client only
        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Выберите почтовый клиент:"));
    }
}
