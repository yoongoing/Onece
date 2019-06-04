package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class activeVerify extends AppCompatActivity {
    private String nonce;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_active_verify);

        Intent intent = getIntent();
        nonce = intent.getExtras().getString("nonce");
        id = intent.getExtras().getString("id");
        System.out.println("==============I`m in verification===============");
        System.out.println("==============I`m in verification===============");
        System.out.println(nonce);
        System.out.println("==============I`m in verification===============");
        System.out.println("==============I`m in verification===============");

        RSACryptor.getInstance().init(id);
        final String decrypt = RSACryptor.getInstance().decrypt(nonce);



        System.out.println("=====================");
        System.out.println(decrypt);
        System.out.println("=====================");
        System.out.println("=====================");
        System.out.println("=====================");
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference("jeff");
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DataSnapshot childRef = dataSnapshot.child(id);
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(id+"/nonce",decrypt);
                mRootRef.updateChildren(childUpdates);

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
}
