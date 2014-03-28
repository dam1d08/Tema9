package com.dam1d.ejer1;

import java.io.File;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.dam1d.util.*;

public class Ejercicio1 {

	// Obtiene los alumnos que no se llaman Fernando Gil y los ordena de forma ascendente
	public static void main(String[] args) {
		new File("alumnos.db4o").delete();
		ObjectContainer bd = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "alumnos.db4o");
		try {
			Db4o.cargaAlumnos(bd, "alumnos.txt");
			Query consulta = bd.query();
			consulta.constrain(Alumno.class);
			consulta.descend("nombre").constrain("Fernando Gil").not();
			consulta.descend("edad").orderAscending();
			ObjectSet<Alumno> resultado = consulta.execute();
			Db4o.mostrarResultado(resultado);
			
		} finally {
			bd.close();
		}
	}
}
