package com.dam1d.util;

public class Proyecto {
	private String descripcion;
	private Alumno al;
	
	public Proyecto(String descripcion) {
		this.descripcion = descripcion;
		this.al = null;
		
	}
	
	public Alumno getAlumno() {
		return al;
		
	}
	
	public void setAlumno(Alumno a) {
		this.al = a;
		
	}
	
	public String getDescripcion() {
		return descripcion;
		
	}

	@Override
	public String toString() {
		return descripcion + " --> " + al;
	}
	
	

}
