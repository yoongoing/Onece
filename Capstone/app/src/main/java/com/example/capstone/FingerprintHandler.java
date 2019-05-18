package com.example.capstone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.CancellationSignal;

@TargetApi(Build.VERSION_CODES.M)

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

    CancellationSignal cancellationSignal;
    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    //메소드들 정의
    public void startAuthor(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("인증 에러 발생" + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("인증 실패", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: "+ helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("앱 접근이 허용되었습니다.", true);

    }

    public void stopFingerAuth(){
        if(cancellationSignal != null && !cancellationSignal.isCanceled()){
            cancellationSignal.cancel();
        }
    }

    private void update(String s, boolean b) {
        final TextView tv_message = ((Activity)context).findViewById(R.id.tv_message);
        final ImageView iv_fingerprint = ((Activity)context).findViewById(R.id.iv_fingerprint);
        final LinearLayout linearLayout = ((Activity)context).findViewById(R.id.ll_secure);

        //안내 메세지 출력
        tv_message.setText(s);

        if(b == false){
            tv_message.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else {//지문인증 성공
            tv_message.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            iv_fingerprint.setImageResource(R.mipmap.done);
            linearLayout.setVisibility(LinearLayout.VISIBLE);

            Intent intent = new Intent(context.getApplicationContext(),login.class);
            context.startActivity(intent);
        }
    }
}
