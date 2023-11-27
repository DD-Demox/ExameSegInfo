package dao;

import factory.ConnectionFactory;
import model.Paciente;

import java.sql.*;

public class PacienteDao {
    private Connection conexao;

    public PacienteDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void adicionar(Paciente paciente) throws SQLException {
        String sql = "insert into pacientes(nome,cpf) values(?,?)";
        PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,paciente.getNome());
        ps.setString(2,paciente.getCpf());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        long idGerado = 0L;
        while (rs.next()){
            paciente.setId(rs.getLong(1));
        }
        ps.close();
        conexao.close();



    }

}
