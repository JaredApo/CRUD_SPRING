package fes.aragon.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fes.aragon.dao.IClienteDAO;
import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;
import fes.aragon.entidades.FacturasProductos;
import fes.aragon.formulario.ClientesFormulario;
import fes.aragon.servicio.IClienteServicio;
import fes.aragon.utilerias.RenderPagina;

@Controller
public class ClienteController {
	@Autowired
	private IClienteServicio clientes;
	
	@GetMapping(value = "/")
	public String inicio(Model modelo) {
		return "inicio";
	}

	@GetMapping(value = "listaclientes")
	public String listaclientes(@RequestParam(name="page",defaultValue = "0")int page, Model modelo) {
		Pageable pagReq=PageRequest.of(page,2);
		Page<Clientes> cl=clientes.findAll(pagReq);
		RenderPagina<Clientes> render=new RenderPagina<>("listaclientes", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("page", render);
		return "/clientes/listacliente";
	}
	
	
	@GetMapping(value = "listaclientesInsertar")
	public String listaclientesInsertar(Model modelo) {
		Clientes cliente=new Clientes();
		modelo.addAttribute("titulo", "Nuevo cliente");
		modelo.addAttribute("cliente", cliente);
		return "/clientes/listaclienteInsertar";
	}
	
	@PostMapping(value = "/formInsertar")
	public String formClientesInsertar(@Valid @ModelAttribute("cliente") Clientes cliente, 
			BindingResult resultado, Model modelo, RedirectAttributes flash, SessionStatus estatus) {
		if (resultado.hasErrors()) {
			modelo.addAttribute("titulo", "Error crear o modificar el cliente");
			return "clientes/listaclienteInsertar";
		}
		if (cliente.getIdClientes() > 0) {
			this.clientes.save(cliente);
			flash.addFlashAttribute("success", "El cliente se modifico con exito");
		} else {
			this.clientes.save(cliente);
			flash.addFlashAttribute("success", "El cliente nuevo se registro con exito");
		}
		modelo.addAttribute("cls", this.clientes.findAll());
		estatus.setComplete();
		if(cliente.getIdClientes() > 0) {
			return "redirect:/listaclientes";
		}else {
			return "redirect/:listaclientesInsertar";
		}
	}
	
	@GetMapping(value = "listaclientesModificar")
	public String listaclientesModificar(@RequestParam(name="page",defaultValue = "0")int page, Model modelo) {
		Pageable pagReq=PageRequest.of(page,2);
		Page<Clientes> cl=clientes.findAll(pagReq);
		RenderPagina<Clientes> render=new RenderPagina<>("listaclientesModificar", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("page", render);
		return "/clientes/listaclienteModificar";
	}
		
	@GetMapping(value = "/formInsertar/{id}")
	public String editarClientes(@PathVariable(value="id") int id, Model modelo) {
		Clientes cliente=null;
		if(id>0) {
			cliente=clientes.findOne(Long.valueOf(id));
		}else {
			modelo.addAttribute("cls",clientes.findAll());
			return "/clientes/listaclienteModificar";
		}
		modelo.addAttribute("titulo","Modificar cliente");
		modelo.addAttribute("cliente",cliente);
		return "/clientes/listaclienteInsertar";
	}
	
	@GetMapping(value = "listaclientesEliminar")
	public String listaclientesEliminar(@RequestParam(name="page",defaultValue = "0")int page, Model modelo) {
		Pageable pagReq=PageRequest.of(page,2);
		Page<Clientes> cl=clientes.findAll(pagReq);
		RenderPagina<Clientes> render=new RenderPagina<>("listaclientesEliminar", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("page", render);
		return "/clientes/listaclienteEliminar";
	}
	
	@GetMapping(value = "/formEliminar/{id}")
	public String eliminararClientes(@PathVariable(value="id") int id, Model modelo, RedirectAttributes flash) {
		if(id>0) {
			clientes.delete(Long.valueOf(id));
		}
		modelo.addAttribute("cls",clientes.findAll());
		flash.addFlashAttribute("success", "El cliente se elimino con exito");
		return "redirect:/listaclientesEliminar";
	}
}
