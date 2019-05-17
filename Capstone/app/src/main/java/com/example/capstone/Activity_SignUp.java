package com.example.capstone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_SignUp extends Activity {

    protected EditText id;
    protected EditText pw;
    protected EditText retype_pw;
    protected EditText phoneNum;
    protected EditText username;
    protected Button signup;
    protected String[] dataSet;
    protected String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
    //회원정보
        username = findViewById(R.id.etUsername);
        id = findViewById(R.id.etId);
        pw = findViewById(R.id.etPassword);
        retype_pw = findViewById(R.id.etRetype);
        phoneNum = findViewById(R.id.etPhone);
    //회원가입버튼
        signup = findViewById(R.id.btn_signUp);

    //회원가입 버튼 클릭 시
        signup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원정보가 모두 입력되지 않은 경우
                if(id.getText().toString().length() == 0 || pw.getText().toString().length() == 0 ||
                phoneNum.getText().toString().length() == 0 || username.getText().toString().length() == 0 ||
                retype_pw.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(),"회원 정보를 확인 하세요.",Toast.LENGTH_SHORT).show();
                } else {
                    //재입력한 비밀번호가 다를경우
//                    if(pw.getText().toString().equals(retype_pw.getText().toString())){
//                        Toast.makeText(getApplicationContext(),"회원정보를 확인 하세요2.",Toast.LENGTH_SHORT).show();
//                    } else {
//                        makeDataSet(id,pw,phoneNum,username);
//                        makeQueryMsg(query);
                        String ip = "http://192.168.1.54:9310/?method=r&id=sex&publickey=1";
//                        String url = ip.concat(query);
//                        System.out.println(query);
                        NetworkTask networkTask = new NetworkTask(ip, null);
                        networkTask.execute();

//                    }
                }
            }
        });


    }
    private void makeDataSet(EditText id,EditText pw,EditText phoneNum,EditText username){
        this.dataSet[0] = "&id=".concat(id.getText().toString());
        this.dataSet[1] =  "&pw=".concat(pw.getText().toString());
        this.dataSet[2] = "&phoneNum=".concat( phoneNum.getText().toString());
        this.dataSet[3] = "&username=".concat(username.getText().toString());
        this.dataSet[4] = "&publickey=daehan";

    }

    private String makeQueryMsg(String query){
        query = "?method=r";
        int count = this.dataSet.length;
        for(int index = 0; index < count; index++){
            query.concat(this.dataSet[index]);
        }
        return query;

    }
}
