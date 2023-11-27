package teste;

import java.util.HexFormat;

public class TesteHex {

    public static void main(String[] args) {
        String teste ="1234567$";
        byte[] lista =teste.getBytes();
        for (int i = 0; i < lista.length; i++) {
            System.out.println(lista[i]);
        }

    }

    public static String toHex(String value) {
        return HexFormat.of().formatHex(value.getBytes());
    }
}
