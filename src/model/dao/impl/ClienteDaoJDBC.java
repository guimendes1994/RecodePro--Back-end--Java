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
import model.dao.ClienteDao;
import model.entities.Cliente;
import model.entities.Viagem;

public class ClienteDaoJDBC implements ClienteDao {
	
	private Connection conn;
	
	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Cliente cliente) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO cliente "
					+ "(nome, cpf, email, telefone, viagem_ID) "
					+ "VALUES "
					+ "(?, ?, ? , ?, ?) ",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, cliente.getNome());
			st.setInt(2, cliente.getCpf());
			st.setString(3, cliente.getEmail());
			st.setInt(4, cliente.getTelefone());
			st.setInt(5, cliente.getViagem_ID());
			
			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					cliente.setCliente_ID(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro inesperado, nenhuma linha foi alterada!");
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
	public void update(Cliente cliente) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE cliente "
					+ "SET email = ?, telefone = ? "
					+ "WHERE cliente_ID = ? ");
			
			st.setString(1, cliente.getEmail());
			st.setInt(2, cliente.getTelefone());
			st.setInt(3, cliente.getCliente_ID());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
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
			st = conn.prepareStatement("DELETE FROM cliente WHERE cliente_ID = ?");
			st.setInt(1, id);
			int teste = st.executeUpdate();
			if(teste == 0) {
				throw new DbException("Cliente não existente");
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
	public Cliente findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select cliente.*, V.origem, V.destino, V.codigo_linha, LA.nome "
					+ "FROM cliente "
					+ "INNER JOIN viagem V ON V.viagem_ID = cliente.viagem_ID "
					+ "INNER JOIN linha_aerea LA ON LA.linha_id = V.codigo_linha "
					+ "WHERE cliente_ID = ? ");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Viagem viagem = instantiateViagem(rs);
				Cliente cliente= instantiateCliente(rs, viagem);
				return cliente;
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

	private Cliente instantiateCliente(ResultSet rs, Viagem viagem) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setCliente_ID(rs.getInt("cliente_ID"));
		cliente.setNome(rs.getString("nome"));
		cliente.setCpf(rs.getInt("cpf"));
		cliente.setEmail(rs.getString("email"));
		cliente.setTelefone(rs.getInt("telefone"));
		cliente.setViagem_ID(rs.getInt("viagem_ID"));
		cliente.setViagem(viagem);
		return cliente;
	}

	private Viagem instantiateViagem(ResultSet rs) throws SQLException {
		Viagem viagem = new Viagem();
		viagem.setViagem_ID(rs.getInt("viagem_ID"));
		viagem.setOrigem(rs.getString("origem"));
		viagem.setDestino(rs.getString("destino"));
		viagem.setCodigo_linha(rs.getInt("codigo_linha"));
		return viagem;
	}

	@Override
	public List<Cliente> findAll(Cliente cliente) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT cliente.*, V.origem, V.destino, V.codigo_linha, LA.nome FROM cliente "
					+ "INNER JOIN viagem V ON V.viagem_ID = cliente.viagem_ID "
					+ "INNER JOIN linha_aerea LA ON LA.linha_id = V.codigo_linha "
					+ "ORDER BY cliente.nome");
			rs = st.executeQuery();
			List<Cliente> list = new ArrayList<>();
			Map<Integer, Viagem> map = new HashMap<>();
			
			while (rs.next()) {
				Viagem viagem = map.get(rs.getInt("viagem_ID"));
				
				if(viagem == null) {
					viagem = instantiateViagem(rs);
					map.put(rs.getInt("cliente_ID"), viagem);
				}
				Cliente clientes = instantiateCliente(rs, viagem);
				list.add(clientes);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
