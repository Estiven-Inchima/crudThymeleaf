package com.bolsadeideas.springboot.form.app.util.paginator;

public class PageItem {

	private int numero;//numero de pagina
	private boolean actual;//para indicar si es pagina actual o no
	
	public PageItem(int numero, boolean actual) {
		super();
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}

	public boolean isActual() {
		return actual;
	}

	
	
}
