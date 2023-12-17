package net.icaripa.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.icaripa.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService {
	
	private List<Categoria> lista = null;
	
	public CategoriasServiceImpl() {
		lista = new LinkedList<Categoria>();
		try {
			
			Categoria categoria1 = new Categoria();
			categoria1.setId(1);
			categoria1.setNombre("Ventas");
			categoria1.setDescripcion("Registro y seguimientos de Ventas");
			
			Categoria categoria2 = new Categoria();
			categoria2.setId(2);
			categoria2.setNombre("Contabilidad");
			categoria2.setDescripcion("Trabajos relacionados con Contabilidad");
			
			Categoria categoria3 = new Categoria();
			categoria3.setId(3);
			categoria3.setNombre("Transporte");
			categoria3.setDescripcion("Manejo de diferentes medios de transportes");
			
			Categoria categoria4 = new Categoria();
			categoria4.setId(4);
			categoria4.setNombre("Informatica");
			categoria4.setDescripcion("Creacion de paginas Web");
			
			Categoria categoria5 = new Categoria();
			categoria5.setId(5);
			categoria5.setNombre("Construccion");
			categoria5.setDescripcion("Trabajos relacionados con Construccion");
			
			Categoria categoria6 = new Categoria();
			categoria6.setId(6);
			categoria6.setNombre("Educacion");
			categoria6.setDescripcion("Clases Semi-Presenciales");
			
			lista.add(categoria1);
			lista.add(categoria2);
			lista.add(categoria3);
			lista.add(categoria4);
			lista.add(categoria5);
			lista.add(categoria6);
			
			
		}catch (Exception e) {
			System.out.println("Error "+ e.getMessage());
		}
	}

	@Override
	public void guardar(Categoria categoria) {
		lista.add(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		return lista;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for (Categoria cat : lista) {
			if (cat.getId()==idCategoria) {
				return cat;
			}
		}		
		return null;	
	}

}
