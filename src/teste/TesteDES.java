package teste;

import org.apache.commons.codec.digest.DigestUtils;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class TesteDES {
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String msg = "teste";
        byte[] msgB = msg.getBytes();


        SecretKeyFactory MyKeyFactory = SecretKeyFactory.getInstance("DES");
        String Password = "12sË256784435362";
        byte[] passwordBytes = Password.getBytes();
        DESKeySpec desKeySpec = new DESKeySpec(passwordBytes);
        SecretKey desKey = MyKeyFactory.generateSecret(desKeySpec);

//        KeyGenerator Mygenerator = KeyGenerator.getInstance("DES");
//        SecretKey myDesKey = Mygenerator.generateKey();

        Cipher cipher = Cipher.getInstance("DES");

        cipher.init(Cipher.ENCRYPT_MODE,desKey);
        byte[] dadosCriptografados = cipher.doFinal(msgB);

        cipher.init(Cipher.DECRYPT_MODE,desKey);
        byte[] dadosDescriptografados = cipher.doFinal(dadosCriptografados);

        String msgCriptografada = new String(dadosCriptografados);
        String msgDescriptografada = new String(dadosDescriptografados);

        System.out.println(msg);
        System.out.println(msgCriptografada);
        System.out.println(msgDescriptografada);
        System.out.println(DigestUtils.sha1Hex(msgDescriptografada));




    }
}
