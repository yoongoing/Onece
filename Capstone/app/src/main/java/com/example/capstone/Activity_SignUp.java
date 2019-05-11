package com.example.capstone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Activity_SignUp extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etRetype;
    private Button btn_signUp;
//    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRetype = findViewById(R.id.etRetype);
        btn_signUp = findViewById(R.id.btn_signUp);
//        btnCancel = (Button) findViewById(R.id.btnCancel);

        // 비밀번호 일치 검사
        etRetype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etRetype.getText().toString();

                if( password.equals(confirm) ) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etRetype.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etRetype.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // 전화번호 입력 확인
            if( etUsername.getText().toString().length() == 0 ) {
                Toast.makeText(Activity_SignUp.this, "전화를 입력하세요!", Toast.LENGTH_SHORT).show();
                etUsername.requestFocus();
                return;
            }

            // 비밀번호 입력 확인
            if( etPassword.getText().toString().length() == 0 ) {
                Toast.makeText(Activity_SignUp.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                etPassword.requestFocus();
                return;
            }

            // 비밀번호 확인 입력 확인
            if( etRetype.getText().toString().length() == 0 ) {
                Toast.makeText(Activity_SignUp.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                etRetype.requestFocus();
                return;
            }

            // 비밀번호 일치 확인
            if( !etPassword.getText().toString().equals(etRetype.getText().toString()) ) {
                Toast.makeText(Activity_SignUp.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                etPassword.setText("");
                etRetype.setText("");
                etPassword.requestFocus();
                return;
            }

            Intent result = new Intent();
            result.putExtra("name", etUsername.getText().toString());

            // 자신을 호출한 Activity로 데이터를 보낸다.
            setResult(RESULT_OK, result);
            finish();
            }
        });



//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }
}

