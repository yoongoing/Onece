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

import java.util.concurrent.ExecutionException;


public class Activity_SignUp extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etRetype;
    private EditText etPhone;
    private EditText etRealname;
    private Button btn_signUp;
    private String url;
    private final String server_ip = "http://192.168.1.72:7777/!method=r";
//    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRetype = findViewById(R.id.etRetype);
        etPhone = findViewById(R.id.etPhone);
        etRealname = findViewById(R.id.etrealName);
        btn_signUp = findViewById(R.id.btn_signUp);
//        btnCancel = (Button) findViewById(R.id.btnCancel);



        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 전화번호 입력 확인
                if (etUsername.getText().toString().length() == 0) {
                    Toast.makeText(Activity_SignUp.this, "전화를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if (etPassword.getText().toString().length() == 0) {
                    Toast.makeText(Activity_SignUp.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if (etRetype.getText().toString().length() == 0) {
                    Toast.makeText(Activity_SignUp.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etRetype.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if (!etPassword.getText().toString().equals(etRetype.getText().toString())) {
                    Toast.makeText(Activity_SignUp.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etRetype.setText("");
                    etPassword.requestFocus();
                    return;
                }
                if(etPhone.getText().toString().length() == 0){
                    Toast.makeText(Activity_SignUp.this, "전화번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPhone.requestFocus();
                }
                if(etRealname.getText().toString().length() == 0){
                    Toast.makeText(Activity_SignUp.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etRealname.requestFocus();
                }

                Intent result = new Intent();
                result.putExtra("name", etUsername.getText().toString());

                String data = "&id="+etUsername.getText()
                        + "&password=" +etPassword.getText()
                        + "&phoneNUm=" + etPhone.getText()
                        + "&name=" + etRealname.getText();

                url = server_ip + data;
                NetworkTask networkTask = new NetworkTask(url,null);
                try {
                    networkTask.execute();
                    String res =  networkTask.execute().get();
                    if(res.equals("success")) {
                        setResult(RESULT_OK, result);
                        finish();
                    } else {
                        Toast.makeText(Activity_SignUp.this, "sex!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Activity_SignUp.this, "메롱", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }


}

