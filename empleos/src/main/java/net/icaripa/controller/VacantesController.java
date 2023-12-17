package net.icaripa.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.icaripa.model.Vacante;
import net.icaripa.service.ICategoriasService;
import net.icaripa.service.IVacantesService;
import net.icaripa.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.imagenes}")//se realiza a nivel de la clase para utilizarla en en metodo "guardar"
	private String ruta;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		System.out.println("Vacante Lista: " +  lista);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas());//Se le pasan datos al modelo para mostrarlo en la vista formVacante
		return "vacantes/formVacante";
	}
	
	@PostMapping("/save")//Configurando el Data Binding, declarando un parametro del tipo del modelo
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes, 
			@RequestParam("archivoImagen") MultipartFile multiPart) {//Se agrega BindingResult result para mostrar errores y RedirectAttributes para mostrar msj
		if(result.hasErrors()) {//En caso de ocurrir errores, enviar el formulario
			for (ObjectError error: result.getAllErrors()) {//Es opcional, es para ver los errores en consola
				System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}
		
		if (!multiPart.isEmpty()) {
			//String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null){ // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}
		
		serviceVacantes.guardar(vacante);//Se agrega la clase servicio para llamar al metodo guardar
		attributes.addFlashAttribute("msg", "Registro Guardado");//Se muestra mjs al usuario con addFlashAttribute
		System.out.println("Vacante: " + vacante);
		return "redirect:/vacantes/index";//Se redirecciona una peticion Get a la Url vacantes/index
	}
	
	/*@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
						@RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha, 
						@RequestParam("destacado") int destacado,@RequestParam("salario") double salario, 
						@RequestParam("detalles") String detalle)  {
		return "vacantes/listVacantes";
	}*/
	
	@GetMapping("/delete")//Se realiza la peticion directamente en al Url con "?"
	public String eliminar(@RequestParam("id") int idVacante, Model model) {
		model.addAttribute("id", idVacante);
		System.out.println("Borrando vacante con id: " + idVacante);// Imprime en consola
		
		return "mensaje";
	}
	
	@GetMapping("/view/{id}")//Creando Url Dinamicas(cambiantes)
	public String verDetalles(@PathVariable("id") int idVacante, Model model) {//Se le pasa el valor contenido en {id} al parametro idVacante
		
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		
		System.out.println("vacante: " + vacante);	
		model.addAttribute("vacante", vacante);
		
		//Buscar los detalles de la vacante en la BD...
		
		return "detalle";//Retorna el nombre de una vista, la cual tiene que estar almacenada en el directorio "vacantes" todo en templates
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {//Se usa este metodo para recibir fecha de un formulario sin problemas
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
