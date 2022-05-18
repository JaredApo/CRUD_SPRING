package fes.aragon.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import fes.aragon.entidades.Clientes;
import fes.aragon.formulario.ClientesFormulario;

@Service
public interface IClienteDAO extends PagingAndSortingRepository<Clientes, Integer>{
	
}
