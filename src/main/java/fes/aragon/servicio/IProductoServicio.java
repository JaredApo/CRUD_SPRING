package fes.aragon.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Productos;

public interface IProductoServicio {
	public List<Productos> findAll();
	public Page<Productos> findAll(Pageable pegeable);
	public void save(Productos productos);
	public Productos findOne(Long id);
	public void delete(Long id);
	//public Productos forName(String name);
}
