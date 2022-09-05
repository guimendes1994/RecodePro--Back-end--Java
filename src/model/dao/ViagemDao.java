package model.dao;

import java.util.List;

import model.entities.Viagem;

public interface ViagemDao {

	void insert(Viagem viagem);
	void update(Viagem viagem);
	void deleteByID(Integer id);
	Viagem findById(Integer id);
	List<Viagem> findAll(Viagem viagem);
}
