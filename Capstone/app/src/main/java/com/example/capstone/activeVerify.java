package com.example.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class activeVerify extends AppCompatActivity {
    String nonce;
    String decrypt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_active_verify);

//        Intent intent = getIntent();
//        nonce = intent.getExtras().getString("num1");


    }

    public void onBackPressed(){
//        super.onBackPressed();
    }
}
