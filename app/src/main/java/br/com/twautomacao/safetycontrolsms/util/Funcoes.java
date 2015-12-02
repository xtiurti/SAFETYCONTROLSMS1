package br.com.twautomacao.safetycontrolsms.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.telephony.SmsManager;

/**
 * Created by Ricardo on 18/08/2014.
 */
public class Funcoes {

    public String gerarMD5 (String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        String crypto = hash.toString(16);
        if (crypto.length() %2 != 0)
            crypto = "0" + crypto;
        return crypto;
    }
    
    public static boolean smsSender(String to, String msg) {
        SmsManager smsManager = SmsManager.getDefault();
        try {
             	smsManager.sendTextMessage(to, null, msg, null, null);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }
    
    public static String removeMascara(String s) {
        s=s.replace("(","");
        s=s.replace(")","");
        s=s.replace("-","");
        s=s.trim();
        return s;
    }
}
