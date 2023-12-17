package net.icaripa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.icaripa.model.Categoria;
import net.icaripa.service.CategoriasServiceImpl;

@Controller
@RequestMapping(value="/categorias")//Anotación @RequestMapping a nivel de clase
public class CategoriasController {
	
	@Autowired
	private CategoriasServiceImpl serviceCategorias;

	//GetMapping("/index")
	@RequestMapping(value="/index", method=RequestMethod.GET)//Anotación @RequestMapping a nivel de metodo
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategorias.buscarTodas();
		System.out.println("Categorias: " + lista);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}
	
	//GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "categorias/formCategoria";
		}
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categoría fueron guardados!");
		System.out.println("Categoria: " + categoria);
		return "redirect:/categorias/index";
	}
	
	/*//PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion){//Los parametros de @RequestPram deben coincidir con los del input
		System.out.println("Categoria: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		return "categorias/listCategorias";
	}*/
}
