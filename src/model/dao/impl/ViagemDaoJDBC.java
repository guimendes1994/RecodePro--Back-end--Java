package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ViagemDao;
import model.entities.Cliente;
import model.entities.Viagem;

public class ViagemDaoJDBC implements ViagemDao{

	private Connection conn;
	
	public ViagemDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Viagem viagem) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO viagem "
					+ "(origem, destino, codigo_linha) "
					+ "VALUES "
					+ "(?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, viagem.getOrigem());
			st.setString(2, viagem.getDestino());
			st.setInt(3, viagem.getCodigo_linha());
			
			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					viagem.setViagem_ID(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro inesperado! Nenhuma linha foi alterada!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Viagem viagem) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE viagem "
					+ "SET origem = ?, destino = ?, "
					+ "WHERE viagem_ID = ?");
			
			st.setString(1, viagem.getOrigem());
			st.setString(2, viagem.getDestino());
			st.setInt(3, viagem.getViagem_ID());
			
			st.executeUpdate();
		
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteByID(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM viagem WHERE viagem_ID = ? ");
			st.setInt(1, id);
			int teste = st.executeUpdate();
			if (teste == 0) {
				throw new DbException("Erro inesperado. Nenhuma linha foi alterada!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Viagem findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT viagem.*, C.nome FROM viagem "
					+ "INNER JOIN linha_aerea C "
					+ "ON C.linha_ID = viagem.codigo_linha "
					+ "WHERE viagem_ID = ? ");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				
				Viagem viagem = instantiateViagem(rs);
				return viagem;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Viagem> findAll(Viagem viagem) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT viagem.*, C.cliente_ID, C.nome, C.cpf, C.email, C.telefone, L.nome, L.linha_ID FROM viagem "
					+ "INNER JOIN cliente C ON C.viagem_ID = viagem.viagem_ID "
					+ "INNER JOIN linha_aerea L ON L.linha_ID = viagem.codigo_linha "
					+ "ORDER BY viagem.viagem_ID");
			rs = st.executeQuery();
			List<Viagem> list = new ArrayList<>();
			Map<Integer, Cliente> map = new HashMap<>();
			while (rs.next()) {
				Cliente cliente = map.get(rs.getInt("cliente_ID"));
				if(cliente == null) {
					cliente = instantiateCliente(rs);
					map.put(rs.getInt("viagem_ID"), cliente);
				}
				viagem = instantiateViagem(rs);
				list.add(viagem);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Viagem instantiateViagem(ResultSet rs) throws SQLException {
		Viagem viagem = new Viagem();
		viagem.setViagem_ID(rs.getInt("viagem_ID"));
		viagem.setOrigem(rs.getString("origem"));
		viagem.setDestino(rs.getString("destino"));
		viagem.setCodigo_linha(rs.getInt("codigo_linha"));
		return viagem;
	}

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setCliente_ID(rs.getInt("cliente_ID"));
		cliente.setNome(rs.getString("nome"));
		cliente.setCpf(rs.getInt("cpf"));
		cliente.setEmail(rs.getString("email"));
		cliente.setTelefone(rs.getInt("telefone"));
		return cliente;
	}

}
