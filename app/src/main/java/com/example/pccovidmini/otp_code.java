package com.example.pccovidmini;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.text.TextUtils;
public class otp_code extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private EditText editEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_code);
        context = this;

        connectView();
    }

    private void connectView() {
        editEmail = (EditText) findViewById(R.id.editEmail);

        findViewById(R.id.reset_pass).setOnClickListener(this);
        findViewById(R.id.ic_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.reset_pass:
                LoginMain_OTP();
                break;
            case R.id.ic_back:
                super.onBackPressed();
                break;
        }
    }

    private void LoginMain_OTP() {
        boolean error = false;
        String email = editEmail.getText().toString().trim();



        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            error = true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email!");
            editEmail.requestFocus();
            error = true;
        }



        // all data is ok

        if (!error) {
            // create intent to show Main Activity
            Intent intent = new Intent(context, LoginActivity.class);

            // send data if need
//            intent.putExtra("OTP", email);


            // start Main Activity
            startActivity(intent);
        }
    }
}