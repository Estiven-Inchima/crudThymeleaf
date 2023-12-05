package com.bolsadeideas.springboot.form.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




import com.bolsadeideas.springboot.form.app.models.entity.Cliente;


public interface IClienteDaoCrudRepository extends JpaRepository<Cliente, Long> {

	
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);
	
}
