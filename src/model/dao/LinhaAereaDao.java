package model.dao;

import java.util.List;

import model.entities.LinhaAerea;

public interface LinhaAereaDao {

	void insert(LinhaAerea linhaAerea);
	void update(LinhaAerea linhaAerea);
	void deleteById(Integer id);
	LinhaAerea findById(Integer id);
	List<LinhaAerea> findByAll(LinhaAerea linhaAerea);
}
