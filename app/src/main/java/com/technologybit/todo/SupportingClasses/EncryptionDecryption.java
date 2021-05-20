package com.technologybit.todo.SupportingClasses;


import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryption {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String data, String pass) throws Exception {
        SecretKeySpec key = generateKey(pass);
        @SuppressLint("GetInstance") Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
        Log.i("Encrypted", encryptedValue);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String pass) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = pass.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        return new SecretKeySpec(key, "AES");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(String data, String pass) throws Exception {

        SecretKeySpec key = generateKey(pass);
        @SuppressLint("GetInstance") Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decVal = Base64.getDecoder().decode(data);
        byte[] decodeVal = c.doFinal(decVal);
        return new String(decodeVal);
    }


}
