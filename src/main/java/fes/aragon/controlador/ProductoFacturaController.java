package fes.aragon.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fes.aragon.dao.IClienteDAO;
import fes.aragon.dao.IFacturasDAO;
import fes.aragon.dao.IFacturasDAOaux;
import fes.aragon.dao.IProductoDAO;
import fes.aragon.dao.IProductoDAOaux;
import fes.aragon.dao.IProductosFacturasDAO;
import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;
import fes.aragon.entidades.FacturasProductos;
import fes.aragon.entidades.FacturasProductosPK;
import fes.aragon.entidades.Productos;
import fes.aragon.formulario.ClientesFormulario;
import fes.aragon.servicio.IFacturasServicio;
import fes.aragon.servicio.IProductoServicio;
import fes.aragon.utilerias.RenderPagina;

@Controller
@SessionAttributes(names={"cls"})
public class ProductoFacturaController {
	// indica la inyeccion
	@Autowired
	private IProductosFacturasDAO prodFac;
	@Autowired
	private IClienteDAO clientes;
	@Autowired
	private IFacturasDAOaux facturas2;
	@Autowired
	private IProductoServicio productos;
	//private IProductoDAO productos;
	@Autowired
	private IProductoDAOaux productos2;
	@Autowired
	private IFacturasServicio facturas;
	
	/*@GetMapping(value = "listafacturasproductos")
	public String listaproductos(Model modelo) {
		modelo.addAttribute("cls", prodFac.traerProductos());
		modelo.addAttribute("facts",facturas.traerFacturas());
		modelo.addAttribute("fp",new FacturasProductos());
		return "productosFacturas/listaproductosFacturas";
	}*/
	
	@GetMapping(value = "listafacturasproductos")
	public String listaproductos(Model modelo, @RequestParam(name="page",defaultValue = "0")int page) {
		modelo.addAttribute("cls", prodFac.traerRel());
		modelo.addAttribute("productos",productos.findAll());
		modelo.addAttribute("facts",facturas.findAllF());
		modelo.addAttribute("fp",new FacturasProductos());
		return "productosFacturas/listaproductosFacturas";
	}
	
	
	@RequestMapping(value = "listafacturasproductosref")
	public String listaproductosref(@RequestParam Map<String, String> parametros ,Model modelo) {
		System.out.println(parametros.get("referencia"));
		modelo.addAttribute("cls", prodFac.traerporFacturas(facturas2.factXReferencia(
					parametros.get("referencia")
				)));
		modelo.addAttribute("facts",facturas.findAllF());
		modelo.addAttribute("fp",new FacturasProductos());
		return "productosFacturas/listaproductosFacturas";
	}
	
	@GetMapping(value = "listafacturasproductosIn")
	public String listaproductosInsertar(Model modelo) {
		modelo.addAttribute("cls", prodFac.traerProductos());
		modelo.addAttribute("facts",facturas.findAllF());
		modelo.addAttribute("fp",new FacturasProductos());
		return "productosFacturas/listaproductoF";
	}
	
	
	
	
	@SuppressWarnings("finally")
	@GetMapping(value="/formProdFInsertar")
	public String insertarProdInFact(@RequestParam(value="ReferenciaFact", required=true)String referencia,
			@RequestParam(value="idProducto")int idProducto,
			@RequestParam(value="cantidad")int cantidad,Model modelo, RedirectAttributes flash) {
		System.out.println(referencia+"      "+cantidad);	
		Facturas factura=this.facturas2.factXReferencia(referencia);
		try {
			if(factura!=null) {
				FacturasProductos faP=new FacturasProductos();
				faP.setCantidadFacturasProductos(cantidad);
				faP.setFacturas(factura);
				faP.setProductos(this.productos.findOne(Long.valueOf(idProducto)));
				
				faP.setFacturasProductosPK(new FacturasProductosPK(factura.getIdFacturas(),idProducto));
				System.out.println("Rellene el obj factprod de fanny");
				this.prodFac.registrarProdFacturas(faP);
			//modelo.addAttribute("cls", prodFac.traerRel());
		//	modelo.addAttribute("productos",productos.todosProductos());
				modelo.addAttribute("facts",facturas.findAllF());
				modelo.addAttribute("fp",new FacturasProductos());
				flash.addFlashAttribute("success", "producto agregado con exito");
				return "redirect:/listafacturasproductos";
			}
		
		}catch (Exception e) {
			System.out.println("Error ingresando datos");
		}finally {
			modelo.addAttribute("cls", prodFac.traerRel());
			//modelo.addAttribute("productos",productos.todosProductos());
			modelo.addAttribute("facts",facturas.findAllF());
			modelo.addAttribute("fp",new FacturasProductos());
			return "redirect:/listafacturasproductos";
		}
	}
	
	
	
	@RequestMapping(value = "/formFPEliminar/{idF}/{idP}")
	public String eliminarProductosdeFactura(@PathVariable(value = "idF")int idFactura, 
			@PathVariable(value = "idP")int idProducto, Model modelo,  RedirectAttributes flash) {
		prodFac.eliminarProdEnFact(idFactura, idProducto);
		flash.addFlashAttribute("success", "Eliminado con exito");
		return "redirect:/listafacturasproductos";
	}
	
	
	
	
	@RequestMapping(value="/formmodificarfp/{idFactura}/{idProducto}/{cantidadFP}")
	public String modificarFP(@PathVariable(value = "idFactura")int idFactura, 
			@PathVariable(value = "idProducto")int idProducto,@PathVariable(value = "cantidadFP") double cantidadFP,  Model modelo) {
		FacturasProductos facProductos=new FacturasProductos();
		Productos producto=this.productos.findOne(Long.valueOf(idProducto));
		Facturas factura=this.facturas.findOne(Long.valueOf(idFactura));
		facProductos.setFacturas(factura);
		facProductos.setProductos(producto);
		facProductos.setCantidadFacturasProductos(cantidadFP);
		//System.out.println(factura.getIdFacturas()+" "+factura.getReferenciaFacturas()+" "+factura.getFechaFacturas()+producto.getIdProductos());
		//SyStem.out.println(facProductos.toString());
		System.out.println("oa");
		modelo.addAttribute("cls",facProductos);
		modelo.addAttribute("titulo","Modificar factura: "+factura.getIdFacturas()+" producto "+producto.getIdProductos());
		return "productosFacturas/listaproductosfacturasinsertar";
	}
	
//	@RequestMapping(value="/modificarfacturaproducto/{referenciaFacturas}/{nombreProductos}/{cantidadFacturasProductos}")
//	public String modificarFacturaProducto(@PathVariable(value = "referenciaFacturas")String referenciaFacturas, 
//			@PathVariable(value = "nombreProductos")String nombreProductos,
//			@PathVariable(value = "cantidadFacturasProductos") double cantidadFacturasProductos,  Model modelo) {
//		FacturasProductos facProductos=new FacturasProductos();
//		System.out.println("Entreee con datos "+nombreProductos+" "+cantidadFacturasProductos+" "+referenciaFacturas);
//		Productos producto=this.productos.buscarXnombre(nombreProductos);
//		//producto.setNombreProductos(nombreProductos);
//		//productos.modificarProductos(producto);
//		System.out.println("Si busque nombre");
//		Facturas factura=this.facturas.factXReferencia(referenciaFacturas);
//		//factura.setReferenciaFacturas(referenciaFacturas);
//		System.out.println("Busque factura por referencia");
//		facProductos=prodFac.traerFacturasProductos(factura,producto);
//		System.out.println("Traje facturas productos");
//		facProductos.setCantidadFacturasProductos(cantidadFacturasProductos);
//		System.out.println("Busque nom,y ref");
//		System.out.println("CANTIDAD "+facProductos.getCantidadFacturasProductos());
//		//facProductos.setFacturas(factura);
//		//facProductos.setProductos(producto);
//		//facProductos.setCantidadFacturasProductos(cantidadFacturasProductos);
//		//facturas.modificarFacturas(factura);
//		System.out.println("Antes de modificar");
//		this.prodFac.modificarFacturas(facProductos);
//		
//		//System.out.println(factura.getIdFacturas()+" "+factura.getReferenciaFacturas()+" "+factura.getFechaFacturas()+producto.getIdProductos());
//		System.out.println(facProductos.toString());
//		
//		System.out.println("se modifico");
//		modelo.addAttribute("cls",facProductos);
//		modelo.addAttribute("titulo","Modificar factura: "+factura.getIdFacturas()+" producto "+producto.getIdProductos());
//		return "productosFacturas/listaproductosfacturasinsertar";
//	}
//	

	@RequestMapping(value="/modificarfacturaproducto")
	public String modificarFacturaProducto(@ModelAttribute(value = "cls") FacturasProductos cls,  Model modelo) {
		FacturasProductos facProductos=new FacturasProductos();
		//System.out.println("Entreee con datos "+nombreProductos+" "+cantidadFacturasProductos+" "+referenciaFacturas);
		
		Productos producto=this.productos2.buscarXnombre(cls.getProductos().getNombreProductos()); 
		
		//producto.setNombreProductos(nombreProductos);
		//productos.modificarProductos(producto);
		System.out.println("Si busque nombre");
		Facturas factura=this.facturas2.factXReferencia(cls.getFacturas().getReferenciaFacturas());
		//factura.setReferenciaFacturas(referenciaFacturas);
		System.out.println("Busque factura por referencia");
		
		facProductos=prodFac.traerFacturasProductos(factura,producto); 
		
		System.out.println("Traje facturas productos");
		facProductos.setCantidadFacturasProductos(cls.getCantidadFacturasProductos());
		System.out.println("Busque nom,y ref");
		System.out.println("CANTIDAD "+facProductos.getCantidadFacturasProductos());
		//facProductos.setFacturas(factura);
		//facProductos.setProductos(producto);
		//facProductos.setCantidadFacturasProductos(cantidadFacturasProductos);
		//facturas.modificarFacturas(factura);
		System.out.println("Antes de modificar");
		this.prodFac.modificarFacturas(facProductos);
		
		//System.out.println(factura.getIdFacturas()+" "+factura.getReferenciaFacturas()+" "+factura.getFechaFacturas()+producto.getIdProductos());
		System.out.println(facProductos.toString());
		
		System.out.println("se modifico");
		modelo.addAttribute("cls",facProductos);
		
		modelo.addAttribute("titulo","Modificar factura: "+factura.getIdFacturas()+" producto "+producto.getIdProductos()); 
		
		return "productosFacturas/listaproductosfacturasinsertar";
	}
	
	
	/*@RequestMapping(value = "listaproductofacmodificar")
	public String listaproductosModificar(FacturasProductos facProduc,Model modelo) {
		this.prodFac.modificarFacturas(facProduc);
		return "productosFacturas/listaproductosfacturasinsertar";
	}*/
	
	}



