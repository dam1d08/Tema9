package com.dam1d.ejer4;

public class Pregunta {
	//static char[] op = {'a','b','c','d'};
	private int correct;
	String preg;
	String[] opcion = new String[4];
	
	public int getCorrect() {
		return correct;
	}
	
	public Pregunta(String preg, String[] opcion, int correct ) {
		this.preg = preg;
		this.opcion = opcion;
		this.correct = correct;
		
	}
	
	public void MuestraPregunta() {
		System.out.println(this.preg+"\n");
		for (int i = 0; i < this.opcion.length; i++) {
			System.out.println((i+1)+") "+this.opcion[i]);
			
		}
		System.out.print("Respuesta: ");
		
	}

}
