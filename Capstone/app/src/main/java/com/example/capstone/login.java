package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class login extends AppCompatActivity {
    private String nonce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        final String token = FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( login.this,  new OnSuccessListener<InstanceIdResult>() {

            @Override
            public void onSuccess(InstanceIdResult InstanceIdResult) {
            } //현재는 로그인 버튼을 눌렀을떄 토큰이 생성되고 그 토큰을 가지고 파이어 베이스에 등록 되게 해 놓았음

        }).getResult().getToken();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        Button b1 = findViewById(R.id.btn_signUp);
        Button b2 = findViewById(R.id.btn_login);
=======
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            System.out.println("--------------------------");
            Log.i("dd","Extra : " + extras.getString("num1"));
            nonce = getIntent().getExtras().getString("num1");
            System.out.println("--------------------------");
        }

        Button b1 = findViewById(R.id.button);
        Button b2 = findViewById(R.id.button2);
>>>>>>> e3c6404355da03fa0540b4439d28ffdca7d601db
        //b1의 리스너
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Activity_SignUp.class);
                intent.putExtra("Token", token);
                startActivity(intent);
            }
        });
        //b2의 리스너
        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), activeVerify.class);
                if(nonce != null) {
                    intent.putExtra("nonce", nonce);
                }
                System.out.println("----------------------------");
                System.out.println("----------------------------");
                System.out.println("i push nonce !!!!!!!!!!!!");
                System.out.println("----------------------------");
                System.out.println("----------------------------");
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
//        super.onBackPressed();
    }
}