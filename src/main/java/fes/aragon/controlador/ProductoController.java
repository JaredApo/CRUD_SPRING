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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fes.aragon.dao.IClienteDAO;
import fes.aragon.dao.IProductoDAO;
import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;
import fes.aragon.entidades.FacturasProductos;
import fes.aragon.entidades.Productos;
import fes.aragon.formulario.ClientesFormulario;
import fes.aragon.servicio.IClienteServicio;
import fes.aragon.servicio.IProductoServicio;
import fes.aragon.utilerias.RenderPagina;

@Controller
public class ProductoController {
	@Autowired
	private IProductoServicio productos;
	
	
	@GetMapping(value = "listaproductos")
	public String listaproductos(@RequestParam(name="page",defaultValue = "0")int page, Model modelo) {
		Pageable pagReq=PageRequest.of(page,2);
		Page<Productos> cl=productos.findAll(pagReq);
		RenderPagina<Productos> render=new RenderPagina<>("listaproductos", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("page", render);
		return "/productos/listaproducto";
	}
	
	@GetMapping(value = "listaproductoInsertar")
	public String listaproductosInsertar(Model modelo) {
		Productos producto=new Productos();
		modelo.addAttribute("titulo", "Nuevo producto");
		modelo.addAttribute("producto", producto);
		return "/productos/listaproductoInsertar";
	}

	@PostMapping(value = "/formInsertarP")
	public String formProductosInsertar(@Valid @ModelAttribute("producto") Productos producto, 
			BindingResult resultado, RedirectAttributes flash, Model modelo) {
		if (resultado.hasErrors()) {
			modelo.addAttribute("titulo", "Error crear o modificar el producto");
			return "productos/listaproductoInsertar";
		}
		if(producto.getIdProductos()>0) {
			this.productos.save(producto);
			flash.addFlashAttribute("success", "El producto se modifico con exito");
		}else {
			this.productos.save(producto);
			flash.addFlashAttribute("success", "El producto se agrego con exito");
		}

		modelo.addAttribute("cls", this.productos.findAll()); 
		return "redirect:/listaproductos";
	}
	
	@GetMapping(value = "listaproductoModificarP")
	public String listaproductosModificar(@RequestParam(name="page",defaultValue = "0")int page, Model modelo) {
		Pageable pagReq=PageRequest.of(page,2);
		Page<Productos> cl=productos.findAll(pagReq);
		RenderPagina<Productos> render=new RenderPagina<>("listaproductoModificarP", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("page", render);
		return "/productos/listaproductoModificar";
	}
		
	@GetMapping(value = "/formInsertarP/{id}")
	public String editarProductos(@PathVariable(value="id") int id, Model modelo) {
		Productos producto=null;
		if(id>0) {
			producto=productos.findOne(Long.valueOf(id));
		}else {
			modelo.addAttribute("cls",productos.findAll());
			return "/productos/listaproductoModificar";
		}
		modelo.addAttribute("titulo","Modificar producto");
		modelo.addAttribute("producto",producto);
		return "/productos/listaproductoInsertar";
	}
	
	@GetMapping(value = "listaproductoEliminar")
	public String listaproductoEliminar(@RequestParam(name="page",defaultValue = "0")int page, Model modelo) {
		Pageable pagReq=PageRequest.of(page,2);
		Page<Productos> cl=productos.findAll(pagReq);
		RenderPagina<Productos> render=new RenderPagina<>("listaproductoEliminar", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("page", render);
		return "/productos/listaproductoEliminar";
	}
	
	@GetMapping(value = "/formEliminarP/{id}")
	public String eliminararProductos(@PathVariable(value="id") int id, RedirectAttributes flash,  Model modelo) {
		//Productos producto=null;
		if(id>0) {
			//producto=productos.findOne(Long.valueOf(id));
			productos.delete(Long.valueOf(id));
		}
		modelo.addAttribute("cls",productos.findAll());
		flash.addFlashAttribute("success", "El producto se elimino con exito");
		return "redirect:/listaproductoEliminar";
	}
}
