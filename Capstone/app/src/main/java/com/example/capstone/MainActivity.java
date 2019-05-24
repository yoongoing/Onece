package com.example.capstone;

<<<<<<< HEAD
import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
=======
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
>>>>>>> fb5fd49991b83bef7fbca0b10319076a2e9e3fa9
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    TextView tvData;
    Button b1, b2;

    final String TAG = "MainActivity";
    private ImageView iv_fingerprint;
    private TextView tv_message;
    private LinearLayout linearLayout;

    private static final String KEY_NAME = "example_key";
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_fingerprint);

        iv_fingerprint = findViewById(R.id.iv_fingerprint);
        tv_message = findViewById(R.id.tv_message);
        tv_message.setText("Message");
        linearLayout = findViewById(R.id.ll_secure);
        linearLayout.setVisibility(LinearLayout.GONE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){//Manifest에 Fingerprint 퍼미션을 추가해 워야 사용가능
                tv_message.setText("지문을 사용할 수 없는 디바이스 입니다.");
            } else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                tv_message.setText("지문사용을 허용해 주세요.");
                /*잠금화면 상태를 체크한다.*/
            } else if(!keyguardManager.isKeyguardSecure()){
                tv_message.setText("잠금화면을 설정해 주세요.");
            } else if(!fingerprintManager.hasEnrolledFingerprints()){
                tv_message.setText("등록된 지문이 없습니다.");
            } else {//모든 관문을 성공적으로 통과(지문인식을 지원하고 지문 사용이 허용되어 있고 잠금화면이 설정되었고 지문이 등록되어 있을때)
                tv_message.setText("손가락을 홈버튼에 대 주세요.");

                generateKey();
                if(cipherInit()){
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    //핸들러실행
                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                    fingerprintHandler.startAuthor(fingerprintManager, cryptoObject);
                }
            }
        }
    }

    //Cipher Init()
    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit(){
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    //Key Generator
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e){
            throw new RuntimeException(e);
        }
    }

    public ImageView getIv_fingerprint() {
        return iv_fingerprint;
=======
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        tvData = (TextView) findViewById(R.id.name);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);

        //b1의 리스너
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent);
            }
        });
        //b2의 리스너
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String idData = tvData.getText().toString();
                String ip = "http://192.168.1.72:7777/?id=";
                String url = ip.concat(idData);
               
                String token =FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        Log.e("newToken",newToken);

                    } //현재는 로그인 버튼을 눌렀을떄 토큰이 생성되고 그 토큰을 가지고 파이어 베이스에 등록 되게 해 놓았음

                }).toString();
                System.out.println(token);

                String temp = "http://192.168.0.116:9000/?method=r&id=testId&publickey=1234&token="+token;
                NetworkTask networkTask = new NetworkTask(temp, null);
                networkTask.execute();
                
//                화면전환 이벤트
//                Intent intent = new Intent(getApplicationContext(),activeVerify.class);
//                startActivity(intent);
            }
        });
    }
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            if(s.equals("true")) {
                Intent intent = new Intent(getApplicationContext(),activeVerify.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this,"회원 아이디를 확인하세요",Toast.LENGTH_SHORT).show();
            }
        }
>>>>>>> fb5fd49991b83bef7fbca0b10319076a2e9e3fa9
    }
}

//    private Button login_signUp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        login_signUp = findViewById(R.id.login_signUp);
//        login_signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Activity_SignUp.class);
//                startActivityForResult(intent, 1000);
//            }
//        });
//    }



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_main);
//
//
//        Button b1 = findViewById(R.id.button);
//        Button b2 = findViewById(R.id.button2);
//
//        //b1의 리스너
//        b1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                Intent intent = new Intent(getApplicationContext(),signUp.class);
//                startActivity(intent);
//            }
//        });
//        //b2의 리스너
//        b2.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                Intent intent = new Intent(getApplicationContext(),activeVerify.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//    }




