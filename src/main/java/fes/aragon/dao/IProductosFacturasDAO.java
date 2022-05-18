package fes.aragon.dao;


import java.util.List;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;
import fes.aragon.entidades.FacturasProductos;
import fes.aragon.entidades.Productos;

public interface IProductosFacturasDAO {
	public List<Facturas> traerporFacturas(Facturas factura);
	public List<Productos> traerProductos();
	public List<FacturasProductos> traerRel();
	public void registrarProdFacturas(FacturasProductos factura);
	public void modificarFacturas(FacturasProductos facProd);
	public void eliminarProdEnFact(int idFactura, int idProducto);
	public FacturasProductos traerFacturasProductos(Facturas fact, Productos prod);
}
