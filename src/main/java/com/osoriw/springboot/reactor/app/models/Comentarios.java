package com.osoriw.springboot.reactor.app.models;

import java.util.ArrayList;
import java.util.List;

public class Comentarios {

	private List<String> comentarios =  new ArrayList<>();

	public void setComentario(String comentario) {
		this.comentarios.add(comentario);
	}

	@Override
	public String toString() {
		return "comentarios=" + comentarios;
	}

}
