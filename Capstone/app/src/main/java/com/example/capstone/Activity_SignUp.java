package com.example.capstone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.security.PublicKey;

public class Activity_SignUp extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etRetype;
    private EditText etPhone;
    private EditText etRealname;
    private Button btn_signUp;
    private String url;
    private final String server_ip = "http://192.168.1.29:9000/?method=r";


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




        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                // ID 입력 확인
                if (etUsername.getText().toString().length() == 0) {
                    Toast.makeText(Activity_SignUp.this, "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
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


                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference("jeff");
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        DataSnapshot childRef = dataSnapshot.child(etUsername.getText().toString());
                        if (childRef.exists()) {
                            Toast.makeText(Activity_SignUp.this, "이미 등록되어있는 아이디 입니다. 시발러", Toast.LENGTH_SHORT).show();

                        }else{
                            Intent result = new Intent();
                            result.putExtra("name", etUsername.getText().toString());
                            String str_pub;
                            String token =FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Activity_SignUp.this,  new OnSuccessListener<InstanceIdResult>() {
                                @Override
                                public void onSuccess(InstanceIdResult instanceIdResult) {
                                    String newToken = instanceIdResult.getToken();
                                    Log.e("newToken",newToken);
                                } //현재는 로그인 버튼을 눌렀을떄 토큰이 생성되고 그 토큰을 가지고 파이어 베이스에 등록 되게 해 놓았음

                            }).getResult().getToken();

                            RSACryptor.getInstance().init();
                            PublicKey publickey = RSACryptor.getInstance().getPublicKey();
                            byte[] byte_publickey = publickey.getEncoded();
                            System.out.println(Base64.encodeToString(byte_publickey, Base64.DEFAULT));
                            String hexPub = Utils.getHex(byte_publickey);
                            System.out.println("-----------------------------------------");
                            System.out.println(hexPub);
                            System.out.println("-----------------------------------------");

                            String data = "&id="+etUsername.getText()
                                    + "&password=" +etPassword.getText()
                                    + "&phone=" + etPhone.getText()
                                    + "&name=" + etRealname.getText()
                                   + "&publickey="+hexPub
                                    +"&token="+token;

                            url = server_ip + data;


                            NetworkTask networkTask = new NetworkTask(url,null);
                            networkTask.execute();
                            try {
                                String res =  networkTask.execute().get();
                                if(res.equals("OK")) {
                                    setResult(RESULT_OK, result);
                                    finish();
                                } else {
                                    Toast.makeText(Activity_SignUp.this, "서버 연결 오류. 잠시후 재 시도해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(Activity_SignUp.this, url, Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }


                });




            }
        } );

    }

}

