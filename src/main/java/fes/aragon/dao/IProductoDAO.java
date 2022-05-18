package fes.aragon.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Productos;
import fes.aragon.formulario.ClientesFormulario;

@Service
public interface IProductoDAO extends PagingAndSortingRepository<Productos, Integer>{
	/*public List<Productos> todosProductos();
	public Productos datosProductos(int id);
	public void registrarProductos(Productos producto);
	public void modificarProductos(Productos producto);
	public void eliminarProductos(Productos producto);
	public Productos buscarXnombre(String nombre);*/
}
