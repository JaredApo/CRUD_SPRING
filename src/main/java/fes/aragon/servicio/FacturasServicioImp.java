package fes.aragon.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fes.aragon.dao.IClienteDAO;
import fes.aragon.dao.IFacturasDAO;
import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Facturas;

@Service
public class FacturasServicioImp implements IFacturasServicio{
	@Autowired
	private IFacturasDAO facturaDao;
	@Autowired
	private IClienteDAO clienteDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Clientes> findAllC() {
		// TODO Auto-generated method stub		
		return (List<Clientes>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Facturas> findAllF() {
		// TODO Auto-generated method stub		
		return (List<Facturas>) facturaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Facturas facturas) {
		// TODO Auto-generated method stub
		facturaDao.save(facturas);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Facturas findOne(Long id) {
		// TODO Auto-generated method stub		
		return facturaDao.findById(id.intValue()).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(id.intValue()>0) {
			facturaDao.deleteById(id.intValue());
		}
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Facturas> findAll(Pageable pegeable) {
		// TODO Auto-generated method stub		
		return facturaDao.findAll(pegeable);
	}
}
