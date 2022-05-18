package fes.aragon.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;

public interface IFacturasServicio {
	public List<Clientes> findAllC();
	public List<Facturas> findAllF();
	public Page<Facturas> findAll(Pageable pegeable);
	public void save(Facturas factura);
	public Facturas findOne(Long id);
	public void delete(Long id);
}
