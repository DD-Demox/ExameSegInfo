package dao;

import controller.Criptografia;
import controller.Hash;
import factory.ConnectionFactory;
import model.Paciente;
import model.Senha;
import model.Usuario;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sql.rowset.serial.SerialBlob;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class UsuarioDao {
    private Connection conexao;

    public UsuarioDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void adicionar(Usuario usuario) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String sql = "insert into usuarios(senha,paciente) values(?,?)";
        PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        String senhaHexSha = DigestUtils.sha1Hex(usuario.getSenha());
        String senhaHexSha = Hash.hashSha1(usuario.getSenha());
        ps.setString(1,senhaHexSha);
        ps.setLong(2,usuario.getPaciente().getId());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        while (rs.next()){
            usuario.setId(rs.getLong(1));
        }
        String chaveSenha = Criptografia.gerarChave();
        Senha senha = new Senha();
        senha.setSenha(chaveSenha);
        usuario.setSenhaResultado(senha);
        Blob chaveSenhaCriptografada = new SerialBlob(Criptografia.criptografar(chaveSenha,usuario.getSenhaParaCriptografia()));
        String sqlS = "insert into senhas(senha,usuario) values (?,?)";
        PreparedStatement psS = conexao.prepareStatement(sqlS);
        psS.setBlob(1,chaveSenhaCriptografada);
        psS.setLong(2,usuario.getId());
        psS.executeUpdate();
        ps.close();
        psS.close();
        conexao.close();
    }
    public boolean logar(Usuario usuario) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String sql = "select * from usuarios where id=? and senha=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        String senhaHexSha = Hash.hashSha1(usuario.getSenha());
        ps.setLong(1,usuario.getId());
        ps.setString(2,senhaHexSha);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()){
            return false;
        }else{
            while(rs.next()){
                String sqlP = "select * from pacientes where id=?";
                PreparedStatement psP = conexao.prepareStatement(sqlP);
                psP.setLong(1,rs.getLong("paciente"));
                ResultSet rsP = psP.executeQuery();
                while(rsP.next()){
                    Paciente paciente = new Paciente();
                    paciente.setId(rsP.getLong("id"));
                    paciente.setNome(rsP.getString("nome"));
                    paciente.setCpf(rsP.getString("cpf"));
                    usuario.setPaciente(paciente);
                }
                String sqlS = "select * from senhas where usuario =?";
                PreparedStatement psS = conexao.prepareStatement(sqlS);
                psS.setLong(1,usuario.getId());
                ResultSet rsS = psS.executeQuery();
                while (rsS.next()){
                    Senha senha = new Senha();
                    String chaveDescriptografada = Criptografia.descriptografar(rsS.getBlob("senha"),usuario.getSenhaParaCriptografia());
                    senha.setSenha(chaveDescriptografada);
                    usuario.setSenhaResultado(senha);

                }
                rsP.close();
                rsS.close();
                psS.close();
                psP.close();

            }
            ps.close();

            conexao.close();
            return true;
        }

    }

}
