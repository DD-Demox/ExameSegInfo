package teste;

import controller.Hash;

import java.security.NoSuchAlgorithmException;

public class TesteHash {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String senha = "123456";
        String hex = Hash.hashSha1(senha);
        System.out.println(hex);
    }
}
