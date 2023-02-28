package com.example.pccovidmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // creating variables for our edit text and buttons.
    private Context context;
    private EditText editEmail, editPassWord;
//    private Button  loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        connectView();
    }
    private void connectView() {
        editEmail = (EditText) findViewById(R.id.et_email);
        editPassWord = (EditText) findViewById(R.id.et_passWord);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.tv_forgotPass).setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                register();
                break;
            case R.id.tv_forgotPass:
                forgotPass();
                break;
        }
    }
    private void login() {
//        boolean error = false;
        // get data
        String email = editEmail.getText().toString().trim();
        String passWord = editPassWord.getText().toString().trim();
        // National ID empty
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(context.getResources().getString(R.string.error_field_required));
            editEmail.requestFocus();
            error = true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email!");
            editEmail.requestFocus();
            error = true;
        }
        if (TextUtils.isEmpty(passWord)) {
            editPassWord.setError(context.getResources().getString(R.string.error_field_required));
            editPassWord.requestFocus();
            error = true;
        }
        if (passWord.length()<=7){
            editPassWord.setError("Min password length should be more than 7 characters!");
            editPassWord.requestFocus();
            error = true;
        }

        // all data is ok

        if (!error) {
            // create intent to show Main Activity
            Intent intent = new Intent(context, MainActivity.class);

//            // send data if need
//            intent.putExtra("National ID", email);

            // start Main Activity
            startActivity(intent);
        }
    }
    private void register() {
        Intent intent = new Intent(context, RegisterActivity.class);

        // send data if need

        // start Main Activity
        startActivity(intent);
    }

    private void forgotPass() {
        Intent intent = new Intent(context, otp_code.class);

        // send data if need

        // start Main Activity
        startActivity(intent);
    }

}

