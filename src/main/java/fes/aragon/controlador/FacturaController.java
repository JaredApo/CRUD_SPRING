package fes.aragon.controlador;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fes.aragon.dao.IClienteDAO;
import fes.aragon.dao.IFacturasDAO;
import fes.aragon.dao.IFacturasDAOaux;
import fes.aragon.dao.IProductoDAO;
import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;
import fes.aragon.entidades.FacturasProductos;
import fes.aragon.entidades.Productos;
import fes.aragon.formulario.ClientesFormulario;
import fes.aragon.servicio.IClienteServicio;
import fes.aragon.servicio.IFacturasServicio;
import fes.aragon.utilerias.RenderPagina;

@Controller
public class FacturaController {
	@Autowired
	private IFacturasServicio facturas;
	@Autowired
	private IClienteServicio clientes;
	@Autowired
	private IFacturasDAOaux facturas2;
	
	@GetMapping(value = "listafacturas")
	public String listafacturas(Model modelo, @RequestParam(name="page",defaultValue = "0")int page) {
		Pageable pagReq=PageRequest.of(page,2);
		Page<Clientes> cl=clientes.findAll(pagReq);
		RenderPagina<Clientes> render=new RenderPagina<>("listafacturas", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("page", render);
		return "/facturas/listaClientesFacturas";
	}
	
	@GetMapping(value = "listaFacturas/{id}")
	public String listaFacturas(@PathVariable(value = "id") int id, Model modelo, @RequestParam(name="page",defaultValue = "0")int page) {
		Clientes cliente=null;
		List<Facturas> listaFact=null;
		if(id>0) {
			cliente= this.clientes.findOne(Long.valueOf(id));
			//cliente= this.clientes.datosClientes(id);
			listaFact=this.facturas2.traerFacturasCliente(cliente);
			
		}
		modelo.addAttribute("titulo","Lista Facturas");
		modelo.addAttribute("cls", facturas.findAllC());
		modelo.addAttribute("facturas",listaFact);
		modelo.addAttribute("cliente",cliente);
		
		/*Pageable pagReq=PageRequest.of(page,2);
		Page<Facturas> cl=facturas.findAll(pagReq);
		RenderPagina<Facturas> render=new RenderPagina<>("listaFacturas", cl);
		modelo.addAttribute("cls", cl);
		modelo.addAttribute("facturas",listaFact);
		modelo.addAttribute("cliente",cliente);
		modelo.addAttribute("page", render);*/
		
		return "/facturas/listafactura";
	}
	
	@GetMapping(value="insertarFacturas/{idCliente}")
	public String IngresarFactura(@PathVariable(value = "idCliente") int idCliente,Model modelo) {
		Clientes cliente= this.clientes.findOne(Long.valueOf(idCliente));
		//Clientes cliente= this.clientes.datosClientes(idCliente);
		Facturas factura=new Facturas();
		factura.setIdClientes(cliente);
		modelo.addAttribute("factura",factura);
		modelo.addAttribute("cliente",cliente);
		modelo.addAttribute("titulo","Insertar Factura");
		return "facturas/listaInsertarFactura";
	}
	
	@PostMapping(value = "/formInsertarF")
	public String formFacturasInsertar(@Valid @ModelAttribute("factura") Facturas factura,
			@RequestParam(value="id") int id, RedirectAttributes flash, BindingResult resultado, Model modelo) {
		Clientes cliente= this.clientes.findOne(Long.valueOf(id));
		//Clientes cliente= this.clientes.datosClientes(id);
		factura.setIdClientes(cliente);
		if (resultado.hasErrors()) {
			modelo.addAttribute("titulo", "Error crear o modificar el producto");
			return "facturas/listaInsertarFactura";
		}
		if(factura.getIdFacturas()==null ||factura.getIdFacturas()<1) {
			this.facturas.save(factura);
			modelo.addAttribute("titulo","Lista Facturas");
			flash.addFlashAttribute("success", "La factura se añadio con exito");
		}else {
			Facturas fac=this.facturas.findOne(Long.valueOf(factura.getIdFacturas()));
			fac.setReferenciaFacturas(factura.getReferenciaFacturas());
			fac.setFechaFacturas(factura.getFechaFacturas());
			this.facturas.save(fac);
			modelo.addAttribute("titulo","Editar Factura");
			flash.addFlashAttribute("success", "La factura se modifico con exito");
		}
		List<Facturas> listaFacCli=this.facturas2.traerFacturasCliente(cliente);
		modelo.addAttribute("factura",listaFacCli);
		modelo.addAttribute("cliente",cliente);
		return "redirect:/listaFacturas/"+cliente.getIdClientes();
	}
	
	@RequestMapping(value="modificarFactura/{idFactura}")
	public String modificarFactura(@PathVariable(value = "idFactura") int idFactura, Model modelo) {
		Facturas factura=this.facturas.findOne(Long.valueOf(idFactura));
		System.out.println(factura);
		Clientes cliente= factura.getIdClientes();
		System.out.println(factura.getIdFacturas()+" "+factura.getReferenciaFacturas()+" "+factura.getFechaFacturas());
		modelo.addAttribute("factura",factura);
		modelo.addAttribute("cliente",cliente);
		modelo.addAttribute("titulo","Modificar Factura: "+factura.getIdFacturas());
		modelo.addAttribute("idFacturas",idFactura);
		return "facturas/listaInsertarFactura";
	}
	
	@RequestMapping(value = "/formFactEliminar/{id}")
	public String eliminarFacturas(@PathVariable(value = "id")int id, RedirectAttributes flash, Model modelo) {
		Facturas factura=null;
		Clientes cliente=null;
	
		factura= facturas.findOne(Long.valueOf(id));
		System.out.println(factura.getFechaFacturas()+" "+factura.getIdFacturas());
		cliente=factura.getIdClientes();
		cliente.getFacturasList().remove(factura);
		facturas.delete(Long.valueOf(factura.getIdFacturas())); //dudaaaaaaaaaaaaa
		
		List<Facturas> listaFact=this.facturas2.traerFacturasCliente(cliente);
		modelo.addAttribute("titulo","Lista Facturas");
		modelo.addAttribute("facturas",listaFact);
		modelo.addAttribute("cliente",cliente);
		flash.addFlashAttribute("success", "La factura se elimino con exito");
		return "/facturas/listafactura";
	}

}
