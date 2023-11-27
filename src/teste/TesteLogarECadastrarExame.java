package teste;

import dao.FerritinaDao;
import dao.UsuarioDao;
import model.Ferritina;
import model.Usuario;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TesteLogarECadastrarExame {
    public static void main(String[] args) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu id");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Digite sua senha");
        String senha = scanner.nextLine();
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setSenha(senha);
        UsuarioDao usuarioDao = new UsuarioDao();
        if(usuarioDao.logar(usuario)){
            boolean programa = true;
            System.out.println("Bem vindo "+usuario.getPaciente().getNome());
            while (programa){
                FerritinaDao ferritinaDao = new FerritinaDao();
                System.out.println("O que quer fazer?");
                System.out.println("1 - Cadastrar exame");
                System.out.println("2 - Mostrar exames");
                System.out.println("3 - Sair");
                int escolha = Integer.parseInt(scanner.nextLine());
                switch (escolha){
                    case 1:
                        System.out.println("Digite o valor do exame de Ferritina");
                        float resultado = Float.parseFloat(scanner.nextLine());
                        Ferritina ferritina = new Ferritina();
                        ferritina.setResultado(resultado);
                        ferritinaDao.adicionar(ferritina,usuario);
                        break;
                    case 2:
                        List<Ferritina> ferritinaList = ferritinaDao.listarExames(usuario);
                        for (Ferritina f:
                             ferritinaList) {
                            System.out.print("Resultado: "+f.getResultado()+" ");
                            System.out.print("Valor min Masculino: "+Ferritina.minM+" ");
                            System.out.print("Valor max Masculino: "+Ferritina.maxM+" ");
                            System.out.print("Valor min Feminino: "+Ferritina.minF+" ");
                            System.out.print("Valor min Feminino: "+Ferritina.maxF+" ");
                            System.out.print("\n");
                        }
                        break;
                    case 3:
                        programa=false;
                }

            }

        }else{
            System.out.println("Usuario invalido");
        }
    }
}
