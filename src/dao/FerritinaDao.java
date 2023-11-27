package dao;

import controller.Criptografia;
import factory.ConnectionFactory;
import model.Ferritina;
import model.Usuario;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sql.rowset.serial.SerialBlob;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FerritinaDao {
    private Connection conexao;

    public FerritinaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
        String sql = "select * from valorespadroes";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            if(rs.getString("sexo").equals("masculino")){
                Ferritina.minM = rs.getFloat("valor_min");
                Ferritina.maxM = rs.getFloat("valor_max");
            }else{
                Ferritina.minF = rs.getFloat("valor_min");
                Ferritina.maxF = rs.getFloat("valor_max");
            }
        }
    }

    public void adicionar(Ferritina ferritina, Usuario usuario) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        String sql = "insert into ferritina(paciente,resultado) values(?,?)";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setLong(1,usuario.getPaciente().getId());
        byte[] resultadoCriptografado = Criptografia.criptografar(Float.toString(ferritina.getResultado()),usuario.getSenhaResultado().getSenha());
        Blob blob = new SerialBlob(resultadoCriptografado);
        ps.setBlob(2,blob);
        ps.executeUpdate();
        ps.close();
    }
    public List<Ferritina> listarExames(Usuario usuario) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        List<Ferritina> ferritinas = new ArrayList<>();
        String sql = "select * from ferritina where paciente=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setLong(1,usuario.getPaciente().getId());
        ResultSet rs = ps.executeQuery();
        if(!rs.isBeforeFirst()){
            ferritinas = null;
            return ferritinas;
        }else{
            while (rs.next()){
                Ferritina ferritina = new Ferritina();
                float resultado = Float.parseFloat(Criptografia.descriptografar(rs.getBlob("resultado"),usuario.getSenhaResultado().getSenha()));
                ferritina.setResultado(resultado);
                ferritinas.add(ferritina);
            }
            return ferritinas;
        }



    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
}
