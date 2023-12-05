package com.bolsadeideas.springboot.form.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final static String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException { 

		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);
	
		Resource recurso = null;

	
		recurso = new UrlResource(pathFoto.toUri());
		
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error:no se puede cargar la imagen: " + pathFoto.toString());
																								
		}

		

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);
		
		log.info("rootPath: " + rootPath);
		
		Files.copy(file.getInputStream(), rootPath);

		
		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		
		Path rootPath = getPath(filename);
		//obtenemos el archivo
		File archivo = rootPath.toFile();
		//validamos si el archivo existe y que sea accesible(si se puede leer) 
		if(archivo.exists() && archivo.canRead()) {
			
			if(archivo.delete()) { 
				
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath(); 
	}

	//con esto elimina la carpeta con su contenido
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());//le pasamos el objeto carpeta de nombre uploads para que lo borre
		
	}

	//con esto crear la carpeta
	@Override
	public void init() throws IOException{
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));//creamos la carpeta uploads
		
	}

}
