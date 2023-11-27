package teste;

import dao.PacienteDao;
import dao.UsuarioDao;
import model.Paciente;
import model.Usuario;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Scanner;

public class TesteCriaPaciente {
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu nome");
        String nome = scanner.nextLine();
        System.out.println("Digite seu cpf");
        String cpf = scanner.nextLine();
        boolean senhaB = true;
        String senha = null;
        while(senhaB){
            System.out.println("Digite sua senha(Max 8 char)");
            senha = scanner.nextLine();
            if (senha.length()>8 || senha.isEmpty()){
                System.out.println("senha invalida");
            }
            else{
                senhaB = false;
            }
        }


        Paciente paciente = new Paciente();
        paciente.setCpf(cpf);
        paciente.setNome(nome);
        PacienteDao pacienteDao = new PacienteDao();
        pacienteDao.adicionar(paciente);
        Usuario usuario = new Usuario(paciente);
        usuario.setSenha(senha);
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.adicionar(usuario);
        System.out.println(usuario.getSenhaResultado().getSenha());
        System.out.println("Sucesso");





    }
}
