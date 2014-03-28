package com.dam1d.util;

public class Alumno {
	private String nombre;
	private int edad;
	private double nota;
	public Alumno() {
		this.nombre = null;
		edad = 0;
		nota = 0;
		
	}
	
	public Alumno(String nom, int e, double not) {
		this.nombre = nom;
		this.edad = e;
		this.nota = not;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}
	
	@Override
	public String toString() {
		if (this.nota != -1) return this.nombre+"("+this.edad+") Nota: "+this.nota;
		else return this.nombre+" ("+this.edad+")";
	}
	
	

}
