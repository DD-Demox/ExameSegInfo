package controller;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;


public class Criptografia {

    public static SecretKey gerarChave(String chave) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        byte[] chaveB = chave.getBytes();
        DESKeySpec desKeySpec = new DESKeySpec(chaveB);
        return secretKeyFactory.generateSecret(desKeySpec);
    }

    public static String gerarChave() throws NoSuchAlgorithmException {
        SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public static byte[] criptografar(String msg, String chave) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        byte[] msgB = msg.getBytes();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE,gerarChave(chave));
        return cipher.doFinal(msgB);

    }

    public static String descriptografar(Blob msg, String chave) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, SQLException {
        byte[] msgB = msg.getBytes(1,(int)msg.length());
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE,gerarChave(chave));
        byte[] msgBDescriptografa = cipher.doFinal(msgB);
        return new String(msgBDescriptografa);
    }
}
