package com.bolsadeideas.springboot.form.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.bolsadeideas.springboot.form.app.models.dao.IClienteDaoCrudRepository;
import com.bolsadeideas.springboot.form.app.models.dao.IFacturaDao;
import com.bolsadeideas.springboot.form.app.models.dao.IProductoDao;
import com.bolsadeideas.springboot.form.app.models.entity.Cliente;
import com.bolsadeideas.springboot.form.app.models.entity.Factura;
import com.bolsadeideas.springboot.form.app.models.entity.Producto;


@Service
public class ClienteServiceImpl implements IClienteService {

	
	@Autowired

	
	private IClienteDaoCrudRepository clienteDaoRepo;
	
	
	@Autowired
	private IProductoDao productoDao;
	
	
	@Autowired
	private IFacturaDao facturaDao;
	
	
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		
		
		return (List<Cliente>)clienteDaoRepo.findAll(); 
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		
		clienteDaoRepo.save(cliente);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		
		return clienteDaoRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		clienteDaoRepo.deleteById(id);
		
	}

	
	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		
		return clienteDaoRepo.findAll(pageable);
	}

	//metodo para buscar por nombre
	@Override
	@Transactional(readOnly=true)
	public List<Producto> findByNombre(String term) {

		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
		
	}

	//metodo para buscar factura
	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
		
	}

	//metodo para obtener producto por el id
	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Factura findFacturaById(Long id) {
		
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Factura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id) {
		
		return facturaDao.fetchByIdWithClienteWhithItemFacturaWithProducto(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente fetchByIdWithFacturas(Long id) {
		return clienteDaoRepo.fetchByIdWithFacturas(id);
	}

	
	
	
	
	
	
}
