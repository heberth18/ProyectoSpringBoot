package net.icaripa.service;

import java.util.List;

import net.icaripa.model.Categoria;

public interface ICategoriasService {

	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
}
