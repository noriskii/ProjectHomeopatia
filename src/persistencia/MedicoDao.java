package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import builder.BuilderMedico;
import entity.Medico;

public class MedicoDao implements iMedicoDao {

	private Connection con;
	private GenericDao gDao;

	public MedicoDao() {
		gDao = new GenericDao();
		con = gDao.getConnection();
	}

	@Override
	public void adicionarMedico(Medico medico) throws SQLException {
		String sql = "insert into Medico values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, medico.getNome());
		stmt.setInt(2, medico.getCrm());
		stmt.setString(3, medico.getEspecialidade());
		stmt.setString(4, medico.getEndereco());
		stmt.setInt(5, medico.getNumero());
		stmt.setString(6, medico.getBairro());
		stmt.setString(7, medico.getCep());
		stmt.setString(8, medico.getCidade());
		stmt.setString(9, medico.getEstado());
		stmt.setString(10, medico.getTelefone());
		stmt.setString(11, medico.getCelular());
		stmt.setString(12, medico.getEmail());
		stmt.setString(13, medico.getCoordenadas());
		stmt.executeUpdate();
	}

	@Override
	public List<Medico> listarMedico() throws SQLException {

		String sql = "select  Medico.crm, Medico.nome, Especialidade.especialidade, Medico.endereco, Medico.numero,Medico.telefone, Medico.celular, Medico.email from Medico inner join Especialidade on Medico.idEspecialidade = Especialidade.idEspecialidade";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Medico> listaMedico = new ArrayList<Medico>();
		while (rs.next()) {
			Medico medico = new Medico();
			medico = BuilderMedico.buildMedico(rs);
			listaMedico.add(medico);
		}

		rs.close();
		stmt.close();

		return listaMedico;
	}

	@Override
	public  Medico consultaMedicoCRM(int crm) throws SQLException {
		String sql = "select  Medico.crm, Medico.nome, Especialidade.especialidade, Medico.endereco, Medico.numero,Medico.telefone, Medico.celular, Medico.email from Medico inner join Especialidade on Medico.idEspecialidade = Especialidade.idEspecialidade where Medico.crm=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, crm);
		ResultSet rs = stmt.executeQuery();
		Medico medico = null;
		if (rs.next()) {
			medico = new Medico();
			medico = BuilderMedico.buildMedico(rs);
		}

		rs.close();
		stmt.close();

		return medico;
	}
	
	@Override
	public  Medico consultaMedicoAlterar(int crm) throws SQLException {
		String sql = "select * from Medico inner join Especialidade on Medico.idEspecialidade = Especialidade.idEspecialidade where Medico.crm=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, crm);
		ResultSet rs = stmt.executeQuery();
		Medico medico = null;
		if (rs.next()) {
			medico = new Medico();
			medico = BuilderMedico.buildMedicoCompleto(rs);
		}

		rs.close();
		stmt.close();

		return medico;
	}

	@Override
	public List<Medico> consultaMedicoNome(String nome) throws SQLException {
		String sql = "select  Medico.crm, Medico.nome, Especialidade.especialidade, Medico.endereco, Medico.numero,Medico.telefone, Medico.celular, Medico.email from Medico inner join Especialidade on Medico.idEspecialidade = Especialidade.idEspecialidade where Medico.nome like ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, "%" + nome + "%");
		ResultSet rs = stmt.executeQuery();
		List<Medico> listaMedico = new ArrayList<Medico>();
		while(rs.next()){
			Medico medico = new Medico();
			medico = BuilderMedico.buildMedico(rs);
			listaMedico.add(medico);
		}
		
		rs.close();
		stmt.close();
		
		return listaMedico;
	}

	@Override
	public void alterarMedico(Medico medico) throws SQLException {
		String sql = "update Medico set nome=?, idEspecialidade-= ?, endereco=?, numero=?, bairro=?, cep=?, cidade=?, estado=?, telefone=?, celular=? email=?, coordenadas=? where crm =?"; 
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, medico.getNome());
		stmt.setString(2, medico.getEspecialidade());
		stmt.setString(3, medico.getEndereco());
		stmt.setInt(4, medico.getNumero());
		stmt.setString(5, medico.getBairro());
		stmt.setString(6, medico.getCep());
		stmt.setString(7, medico.getCidade());
		stmt.setString(8, medico.getEstado());
		stmt.setString(9, medico.getTelefone());
		stmt.setString(10, medico.getCelular());
		stmt.setString(11, medico.getEmail());
		stmt.setString(12, medico.getCoordenadas());
		stmt.setInt(13, medico.getCrm());
		stmt.executeQuery();
		
		stmt.close();
	}

	@Override
	public void excluirMedico(int crm) throws SQLException {
		String sql = "delete from Medico where crm=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, crm);
		stmt.executeQuery();
		stmt.close();
	}

}
