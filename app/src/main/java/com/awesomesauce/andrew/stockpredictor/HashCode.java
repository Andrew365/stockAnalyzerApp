package com.awesomesauce.andrew.stockpredictor;


import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by andrew on 8/29/15.
 */
public class HashCode {
    public static int NO_OPTIONS=0;
    private String SHAHash;
    private static String convertToHex(byte[] data) throws java.io.IOException
    {



        StringBuffer sb = new StringBuffer();
        String hex=null;

        hex= Base64.encodeToString(data, 0, data.length, NO_OPTIONS);

        sb.append(hex);

        return sb.toString();
    }


    public String computeSHAHash(String password)
    {
        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("myapp", "Error initializing SHA1 message digest");
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        try {
            SHAHash=convertToHex(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String noEqualsSignHash = SHAHash.replace("=", "");
        String CleanSHAHash =  noEqualsSignHash.replaceAll("[|?*<\":>+\\[\\]/']", "");

       return CleanSHAHash;
    }
}
