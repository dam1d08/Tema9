package com.dam1d.ejer3;

import java.io.File;

import com.dam1d.util.Alumno;
import com.dam1d.util.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
//import com.db4o.query.Constraint;
import com.db4o.query.Query;

public class Ejercicio3 {
	
	// Obtiene los alumnos con una nota mayor que 7 y menor que 9
	public static void main(String[] args) {
		new File("alumnos.db4o").delete();
		ObjectContainer bd = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "alumnos.db4o");
		try {
			Db4o.cargaAlumnos(bd, "alumnos.txt");
			Query consulta = bd.query();
			consulta.constrain(Alumno.class);
			//Constraint mayorsiete = consulta.descend("nota").constrain(7).greater();
			consulta.descend("nota").constrain(7).greater();
			consulta.descend("nota").constrain(9).smaller();
			ObjectSet<Alumno> resultado = consulta.execute();
			Db4o.mostrarResultado(resultado);
			
		} finally {
			bd.close();
		}

	}

}
