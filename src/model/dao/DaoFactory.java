package model.dao;

import db.DB;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.LinhaAereaDaoJDBC;
import model.dao.impl.ViagemDaoJDBC;

public class DaoFactory {

	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	public static ViagemDao createViagemDao() {
		return new ViagemDaoJDBC(DB.getConnection());
	}
	public static LinhaAereaDao createLinhaAereaDao() {
		return new LinhaAereaDaoJDBC(DB.getConnection());
	}
}
