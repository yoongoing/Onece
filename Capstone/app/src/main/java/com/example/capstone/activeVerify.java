package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//인증이 진행되는 class 로 차후 광고 삽임등의 동작을 이 class 에 추가하면 됨.

public class activeVerify extends AppCompatActivity {
    private String nonce;
    private String id;
    private TextView loadStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_verify);
        Timer timer = new Timer();
//        timer.schedule(addTask,1000);


        Intent intent = getIntent();
        nonce = intent.getExtras().getString("nonce");
        id = intent.getExtras().getString("id");


        RSACryptor.getInstance().init(id);
        final String decrypt = RSACryptor.getInstance().decrypt(nonce);



        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference("jeff");
        mRootRef.addValueEventListener(new ValueEventListener() {

            Boolean complete = null;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(id + "/nonce", decrypt);
                mRootRef.updateChildren(childUpdates);


                DataSnapshot childRef = dataSnapshot.child(id+"/complete";
                if (childRef.exists()) {
                    if(!complete) {
                        Map<String, Object> childUpdates1 = new HashMap<>();
                        childUpdates1.put(id + "/nonce", decrypt);
                        mRootRef.updateChildren(childUpdates1);
                        
                    } else {
                        Map<String, Object> childUpdates1 = new HashMap<>();
                        childUpdates1.put(id+"/complete", false);
                        mRootRef.updateChildren(childUpdates1);
                        Intent intent = new Intent(getApplicationContext(),Activity_verify_com.class);
                        startActivity(intent);
                    }
                }


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

}
