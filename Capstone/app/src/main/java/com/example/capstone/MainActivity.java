package com.example.capstone;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class MainActivity extends AppCompatActivity {
    TextView tvData;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
               
                String token = FirebaseInstanceId.getInstance().getInstanceId().toString();


                String temp = "http://192.168.0.26:9000/?method=r&id=testId&publickey=1234&token="+token;
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
    }
}
