package fes.aragon.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fes.aragon.dao.IClienteDAO;
import fes.aragon.entidades.Clientes;

@Service
public class ClienteServicioImpl implements IClienteServicio{
	@Autowired
	//lo traemos de la interfaz para hacer cambios y adaptarnos a diferenes interfaces
	private IClienteDAO clienteDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Clientes> findAll() {
		// TODO Auto-generated method stub		
		return (List<Clientes>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Clientes clientes) {
		// TODO Auto-generated method stub
		clienteDao.save(clientes);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Clientes findOne(Long id) {
		// TODO Auto-generated method stub		
		return clienteDao.findById(id.intValue()).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(id.intValue()>0) {
			clienteDao.deleteById(id.intValue());
		}
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Clientes> findAll(Pageable pegeable) {
		// TODO Auto-generated method stub		
		return clienteDao.findAll(pegeable);
	}

}
