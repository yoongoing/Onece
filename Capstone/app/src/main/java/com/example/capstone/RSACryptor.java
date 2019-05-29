package com.example.capstone;

import android.os.Build;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSACryptor {
    private static final String TAG = "RSACryptor";
    private static final String alias = "Onece_Key";
    private KeyStore.Entry keyEntry;

    //비대칭 암호화(공개키) 알고리즘 호출 상수
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    RSACryptor(){}

    private static class RSACryptorHolder{
        static final RSACryptor INSTANCE = new RSACryptor();
    }

    public static RSACryptor getInstance(){
        return RSACryptorHolder.INSTANCE;
    }

    public void init(){
        try {

            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);


            if(!ks.containsAlias(alias)){
                initAndroidM(alias);
            }
            keyEntry = ks.getEntry(alias, null);
        }catch (KeyStoreException | IOException | NoSuchAlgorithmException |
                CertificateException | UnrecoverableEntryException e ){
            Log.e(TAG, "Initialize fail", e);
        }
    }

    private void initAndroidM(String alias) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
                kpg.initialize(new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4))
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                        .setDigests(KeyProperties.DIGEST_SHA512, KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA256)
                        .setUserAuthenticationRequired(false)
                        .build());

                kpg.generateKeyPair();

                Log.d(TAG, "RSA Initialize");
            }
        } catch (GeneralSecurityException e) {
            Log.e(TAG, "init error", e);
        }
    }


    public String encrypt(String plain){
        try {
            byte[] bytes = plain.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //Public Key로 암호화
            cipher.init(Cipher.ENCRYPT_MODE, ((KeyStore.PrivateKeyEntry) keyEntry).getCertificate().getPublicKey());
            byte[] encryptedBytes = cipher.doFinal(bytes);
            return new String(Base64.encode(encryptedBytes, Base64.DEFAULT));


        }catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            Log.e(TAG, "Encrypt fail",  e);
            return plain;
        }
    }

    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //Private Key로 복호화
            cipher.init(Cipher.DECRYPT_MODE, ((KeyStore.PrivateKeyEntry) keyEntry).getPrivateKey());
            byte[] base64Bytes = encryptedText.getBytes("UTF-8");
            byte[] decryptedBytes = Base64.decode(base64Bytes, Base64.DEFAULT);

            return new String(cipher.doFinal(decryptedBytes));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            Log.e(TAG, "Decrypt fail",  e);
            return encryptedText;
        }
    }
}

