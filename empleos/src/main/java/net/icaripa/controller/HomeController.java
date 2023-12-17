package net.icaripa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.icaripa.model.Vacante;
import net.icaripa.service.IVacantesService;

//Logica de procesamiento de las peticiones Http
@Controller
public class HomeController {
	
	@Autowired
	private IVacantesService serviceVacantes;//Se le inyectan los metodos a la variable serviceVacantes para poder utilizarlos
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Analista Programador");
		vacante.setDescripcion("Se solicita Programador de Software para dar soporte");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		
		model.addAttribute("vacante", vacante);
		
		return "detalle";
	}
	
	@GetMapping("/listado")//Renderizando en una vista un elemento de tipo "List" utilizando thymeleaf
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado"; 
	}
	
	@GetMapping("/")//Es el directorio raiz de toda la app
	public String mostrarHome(Model model){
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "home";
	}
	
}
