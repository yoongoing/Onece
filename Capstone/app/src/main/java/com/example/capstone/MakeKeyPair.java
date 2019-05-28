package com.example.capstone;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.support.annotation.RequiresApi;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;


import static android.security.keystore.KeyProperties.*;


class MakeKeyPair {
    final private int KEY_LENGTH_BIT = 2048;
    final private String alias = "Onece_Key";

    @RequiresApi(api = Build.VERSION_CODES.M)
    MakeKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, IOException {

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        if (!keyStore.isKeyEntry(alias)) {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(
                    KEY_ALGORITHM_RSA, "AndroidKeyStore");
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    alias,
                    PURPOSE_SIGN | PURPOSE_VERIFY)
                    .setDigests(DIGEST_SHA256,
                            DIGEST_SHA512)
                    .setKeySize(KEY_LENGTH_BIT)
                    .build());
            KeyPair keypair = kpg.generateKeyPair();
            System.out.println("-------------------------");
            System.out.println("Not alias in keyStore!!!!!");
            System.out.println("Make KeyPair");
            System.out.println("-------------------------");
        }
    }

    protected PublicKey getPublic() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        KeyStore.Entry entry = keyStore.getEntry(alias, null);
        PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
        return publicKey;
    }
}