package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


//인증이 진행되는 class 로 차후 광고 삽임등의 동작을 이 class 에 추가하면 됨.

public class activeVerify extends AppCompatActivity {
    private String nonce;
    private String id;
    private TextView loadStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_verify);


        Intent intent = getIntent();
        nonce = intent.getExtras().getString("nonce");
        id = intent.getExtras().getString("id");


        RSACryptor.getInstance().init(id);
        final String decrypt = RSACryptor.getInstance().decrypt(nonce);



        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference("jeff");
        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                final Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(id+"/nonce", decrypt);
                mRootRef.updateChildren(childUpdates);


                final DatabaseReference checker = FirebaseDatabase.getInstance().getReference("jeff").child(id);
                checker.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("compelete")&&dataSnapshot.child("compelete").getValue().toString().equals("true")){
                            final Map<String, Object> childUpdates1 = new HashMap<>();
                            childUpdates1.put("/compelete", false);
                            checker.updateChildren(childUpdates1);
                            Toast.makeText(activeVerify.this, "인증이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                            delayedFinish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return;




                }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });








    }
    public void onBackPressed(){
//        super.onBackPressed();
    }

//    TimerTask addTask = new TimerTask(){
//
//        @Override
//        public void run() {
////            String
//        }
//    }

    private void delayedFinish(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ActivityCompat.finishAffinity(activeVerify.this);
            }
        },3000);
    }

}
