package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class login extends AppCompatActivity {
    private String nonce;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            nonce = getIntent().getExtras().getString("num1");
            token = getIntent().getExtras().getString("Token");
        }

        Button b1 = findViewById(R.id.button);
        Button b2 = findViewById(R.id.button2);
        //b1의 리스너
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Activity_SignUp.class);
                intent.putExtra("Token", token);
                startActivity(intent);
                finish();
            }
        });
        //b2의 리스너
        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), activeVerify.class);
                if(nonce != null) {
                    intent.putExtra("nonce", nonce);
                }
            }
        });
    }
    public void onBackPressed(){
//        super.onBackPressed();
    }
}