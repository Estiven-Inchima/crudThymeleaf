package com.bolsadeideas.springboot.form.app.controllers;



import java.io.IOException;
import java.net.MalformedURLException;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.bolsadeideas.springboot.form.app.models.entity.Cliente;
import com.bolsadeideas.springboot.form.app.models.service.IClienteService;
import com.bolsadeideas.springboot.form.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.form.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller 
@SessionAttributes("cliente")
public class ClienteController {

	
	@Autowired 
	private IClienteService clienteService;
	
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	
	
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){ 
	
		
	
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		
	
		
	
	return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename()+"\"").body(recurso);
		
	}
	
	
	
	
	//metodo para mostrar detalle
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) { 
		
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		
		if(cliente == null) {
			flash.addAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: "+ cliente.getNombre());
		return "ver";
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value = "listar", method = RequestMethod.GET) 
																
	
	public String listar(@RequestParam(name="page",defaultValue="0") int page,Model model) { 
										
		
		
		Pageable pageRequest = PageRequest.of(page, 4);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar",clientes);
		
		model.addAttribute("titulo", "Listado de Cliente");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page",pageRender);
		return "listar";
	
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) { 
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de cliente");
		return "form";
		
	}
	
	
	 
	//metodo para guardar
	@RequestMapping(value="/form", method=RequestMethod.POST)
	
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,@RequestParam("file") MultipartFile foto,RedirectAttributes flash,SessionStatus status) {
		
		
		//validamos si contiene errores
		if(result.hasErrors()) {

			model.addAttribute("titulo","Formulario de Cliente");
			
			return "form";
		}
		
		//validamos que foto no sea vacio
		if(!foto.isEmpty()){
			
			/*ESTA PARTE ES PARA Eliminar una imagen al editar*/
			if(cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto()!=null && !cliente.getFoto().isEmpty()) {

				uploadFileService.delete(cliente.getFoto());
				
				
				
				
			}
			
			

			
		
			
			
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename+"'");
			
			
			
			cliente.setFoto(uniqueFilename);
			
			
		}
		
		
		
		
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con éxito!" : "Cliente creado con éxito";
		
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success",mensajeFlash);
		return "redirect:listar";
	}
	
	
	
	
    //***	esto es para modificar ***
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model,RedirectAttributes flash) { 
		
		Cliente cliente = null;
	
		if(id>0) {
			
			cliente = clienteService.findOne(id);
			
			if(cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no exste en la BBDD");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser cero!");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo","Editar Cliente");
		
		return "form";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id>0) {
			
			
			Cliente cliente = clienteService.findOne(id);
			
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito");
			System.out.println("se elimino");
			

			
			if(uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "Foto "+cliente.getFoto()+" eliminada con exito!");
			}
			
		}
		
		return "redirect:/listar";
	}
	
	
	
}
