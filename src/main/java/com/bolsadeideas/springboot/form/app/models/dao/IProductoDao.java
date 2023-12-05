package com.bolsadeideas.springboot.form.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.form.app.models.entity.Producto;

public interface IProductoDao  extends CrudRepository<Producto, Long>{

	
	
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> finByNombre(String term);

	public List<Producto> findByNombreLikeIgnoreCase(String term);
	

}
