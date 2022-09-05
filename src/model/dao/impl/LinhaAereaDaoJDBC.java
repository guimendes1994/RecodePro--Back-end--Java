package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.LinhaAereaDao;
import model.entities.LinhaAerea;

public class LinhaAereaDaoJDBC implements LinhaAereaDao {
	
	private Connection conn;
	
	public LinhaAereaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(LinhaAerea linhaAerea) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO linha_aerea (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, linhaAerea.getNome());
			int row = st.executeUpdate();
			if (row > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					linhaAerea.setLinhaID(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(LinhaAerea linhaAerea) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE linha_aerea SET nome = ? WHERE linha_id = ? ");
			
			st.setString(1, linhaAerea.getNome());
			st.setInt(2, linhaAerea.getLinhaID());
			
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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM linha_aerea WHERE linha_id = ? ");
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
	public LinhaAerea findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM linha_aerea WHERE linha_id = ? ");

			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				LinhaAerea linha = instantiateLinha(rs);
				return linha;
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

	private LinhaAerea instantiateLinha(ResultSet rs) throws SQLException {
		LinhaAerea linha = new LinhaAerea();
		linha.setNome(rs.getString("nome"));
		linha.setLinhaID(rs.getInt("linha_id"));
		return linha;
	}

	@Override
	public List<LinhaAerea> findByAll(LinhaAerea linhaAerea) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM linha_aerea ORDER BY nome ");
			
			rs = st.executeQuery();
			List<LinhaAerea> list = new ArrayList<>();
			while (rs.next()) {
				linhaAerea = instantiateLinha(rs);
				list.add(linhaAerea);
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
