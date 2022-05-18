package fes.aragon.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fes.aragon.entidades.Clientes;

public interface IClienteServicio {
	public List<Clientes> findAll();
	public Page<Clientes> findAll(Pageable pegeable);
	public void save(Clientes clientes);
	public Clientes findOne(Long id);
	public void delete(Long id);
}
