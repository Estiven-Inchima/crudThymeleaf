package com.bolsadeideas.springboot.form.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;


//vamos a usar los generic de java, ya que vamos a paginar una lista de clientes o productos o cualquier tipo de clase de entidad
//entonces la idea es que sea lo mas generico posible 
public class PageRender<T> {
	

	private String url;
	
	private Page<T> page; 
	
	private int totalPaginas;
	private int numElementosPorPagina;
	private int paginaActual;

	// vamos a tener una coleccion por que son varias paginas
	private List<PageItem> paginas; 

	// creamos el constructor
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		// inicializamos la lista
		this.paginas = new ArrayList<PageItem>();

		numElementosPorPagina = page.getSize();// contiene la cantidad de elementos por pagina, viene del controlador
												// esta marcado como 4(Pageable pageRequest = PageRequest.of(page, 4);)
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;// este +1 es para que muestra los indices de la pagina desde 1 y no desde
											// 0.

		int desde, hasta;
		// ahora vamos a calcular el desde al hasta
		if (totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (paginaActual <= numElementosPorPagina / 2) {
				desde = 1;
				hasta = numElementosPorPagina;
			} else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			} else {
				desde = paginaActual - numElementosPorPagina / 2;
				hasta = numElementosPorPagina;
			}
		}
		//estas son las paginas que se va a mostrar en la vista
		/*ahora atraves de un for vamos a recorrer el hasta y vamos a empezar a 
		 * llenar las paginas con cada uno de los items, su numero, si es actual o 
		 * no*/
		for(int i=0;i<hasta;i++) {
			//desde +i es para que muestre la pagina 0 como 1 y la 1 como 2, es mas para la parte visual
			//paginaActual == desde+i esto retorna true o false segun el valor coincide de esa forma sabemos si es la pagina actual
			paginas.add(new PageItem(desde + i, paginaActual == desde+i));
		}
	}

	//agregamos los metodos get para poder acceder desde la vista

	public String getUrl() {
		return url;
	}


	public int getTotalPaginas() {
		return totalPaginas;
	}


	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	/*estos metodos get son para saber: 
	 * o si tiene paginas siguiente o si 
	 * tiene paginas atras*/
	
	//si es primera pagina
	public boolean isFirst() {
		return page.isFirst();
	}
	//si es ultima pagina
	public boolean isLast() {
		return page.isLast();
	}
	//si tiene siguiente(una pagina despues de esta)
	public boolean isHasNext() {
		return page.hasNext();
	}
	//si tiene anterior(una pagina antes de esta)
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
}
