package fes.aragon.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fes.aragon.dao.IProductoDAO;
import fes.aragon.entidades.Clientes;
import fes.aragon.entidades.Productos;

@Service
public class ProductoServicioImpl implements IProductoServicio{
	@Autowired
	private IProductoDAO productoDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Productos> findAll() {
		// TODO Auto-generated method stub
		return (List<Productos>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Productos> findAll(Pageable pegeable) {
		// TODO Auto-generated method stub		
		return productoDao.findAll(pegeable);
	}

	@Override
	@Transactional
	public void save(Productos productos) {
		// TODO Auto-generated method stub
		productoDao.save(productos);
	}

	@Override
	@Transactional(readOnly=true)
	public Productos findOne(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id.intValue()).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(id.intValue()>0) {
			productoDao.deleteById(id.intValue());
		}
		
	}

	/*
	@Override
	@Transactional(readOnly=true)
	public Productos forName(String name) {
		// TODO Auto-generated method stub
		return productoDao.buscarXnombre(name);
	}*/

}
