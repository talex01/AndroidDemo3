package com.example.watcher.project03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        EditText to = (EditText) findViewById(R.id.to);
        EditText subject = (EditText) findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String[] str_to = bundle.getStringArray(Intent.EXTRA_EMAIL);
            String str_subject = bundle.getString(Intent.EXTRA_SUBJECT);
            String str_body = bundle.getString(Intent.EXTRA_TEXT);

            to.setText(str_to != null ? str_to[0] : null);
            subject.setText(str_subject);
            body.setText(str_body);
        }
    }

    // замена шрифта для всей активности
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void sendEmailFromActivity(View view) {
        Toast.makeText(this, "Сообщение отправлено!", Toast.LENGTH_SHORT).show();
        super.finish();
    }
}
