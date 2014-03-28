package com.dam1d.ejer2;

import java.io.File;

import com.dam1d.util.Alumno;
import com.dam1d.util.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Constraint;
import com.db4o.query.Query;

public class Ejercicio2 {
	
	// Obtiene los alumnos que no se llaman Andrés Rosique o Juan Gámez
	public static void main(String[] args) {
		new File("alumnos.db4o").delete();
		ObjectContainer bd = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "alumnos.db4o");
		try {
			Db4o.cargaAlumnos(bd, "alumnos.txt");
			Query consulta = bd.query();
			consulta.constrain(Alumno.class);
			Constraint noandres = consulta.descend("nombre").constrain("Andrés Rosique").not();
			Constraint nojuan = consulta.descend("nombre").constrain("Juan Gámez").not();
			consulta.constrain(nojuan).or(noandres);
			ObjectSet<Alumno> resultado = consulta.execute();
			Db4o.mostrarResultado(resultado);
			
		} finally {
			bd.close();
		}

	}

}
