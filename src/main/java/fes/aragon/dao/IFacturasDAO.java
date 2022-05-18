package fes.aragon.dao;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;

@Service
public interface IFacturasDAO extends PagingAndSortingRepository<Facturas, Integer>{
	/*public List<Clientes> todosClientes();
	public List<Facturas> traerFacturas();
	public List<Facturas> traerFacturasCliente(Clientes cliente);
	public Facturas traerFacturaId(int id);
	public void registrarFacturas(Facturas factura);
	public void modificarFacturas(Facturas factura);
	public void eliminarFacturas(Facturas factura);
	public Facturas factXReferencia(String referencia);*/
}
