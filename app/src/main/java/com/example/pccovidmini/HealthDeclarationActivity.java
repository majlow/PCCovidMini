package com.example.pccovidmini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class HealthDeclarationActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private CheckBox checkBox1;
    private EditText date1;
    private CheckBox checkBox2;
    private EditText date2;
    private CheckBox checkBox3;
    private EditText date3;
    private CheckBox checkBox4;
    private EditText date4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_declaration);

        context = this;

        connectView();
    }

    private void connectView() {
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.ic_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_save:
            case R.id.ic_back:
                super.onBackPressed();
                break;
        }
    }
}