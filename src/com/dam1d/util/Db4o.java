package com.dam1d.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.query.Query;

public class Db4o {
	/**
	 * Carga los datos de los alumnos de un fichero de texto. Cada línea del fichero contiene los datos de un alumno y los campos están separados por tabuladores
	 * 
	 * @param bd
	 * @param fic
	 */
	public static void cargaAlumnos(ObjectContainer bd, String fic) {
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fic));
			boolean eof = false;
			String alu = buff.readLine();
			String[] col = new String[3];
			while (!eof) {
				col = alu.split("\t");
				Alumno a = new Alumno(col[0], Integer.parseInt(col[1]), Double.parseDouble(col[2]));
				bd.store(a);
				alu = buff.readLine();
				if (alu == null) eof = true;
				
			}
			buff.close();
		} catch (DatabaseClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Muestra por pantalla un conjunto de alumnos. Generalmente dicho conjunto se obtiene como resultado de una consulta a la base de datos.
	 * El formato en que se ve el resultado es: [nombre y apellido] ([edad]) Nota: [nota]
	 * 
	 * @param res Conjunto de alumnos para mostrar
	 */
	public static void mostrarResultado(ObjectSet<Alumno> res) {
		System.out.println("Recuperados "+res.size()+" objetos");
		while (res.hasNext()) {
			System.out.println(res.next());
			
		}
		
	}
	
	/**
	 * Muestra por pantalla todos los objetos de la base de datos
	 * 
	 * @param bd base de datos de objetos que deseamos ver por pantalla
	 */
	public static void muestraAlumnos (ObjectContainer bd) {
		Alumno a = new Alumno(null, 0, 0);
		ObjectSet<Alumno> res = bd.queryByExample(a);
		mostrarResultado(res);
		
	}
	
	/**
	 * Muestra en pantalla todos los alumnos de una determinada edad
	 * 
	 * @param bd Base de datos de consulta
	 * @param e Entero que indica la edad de los alumnos que queremos consultar
	 */
	public static void muestraAlumnosEdad(ObjectContainer bd, int e) {
		Alumno a = new Alumno(null, e, 0);
		ObjectSet<Alumno> res = bd.queryByExample(a);
		mostrarResultado(res);
		
	}
	
	/**
	 * Actualiza la nota de un alumno con un nombre dado como parámetro
	 * 
	 * @param bd Base de datos de consulta
	 * @param nombre Cadena de caracteres que coincide con el nombre del alumno cuya nota deseamos cambiar
	 * @param nota Nueva nota que queremos ponerle al alumno
	 */
	public static void actualizaNotaNombre(ObjectContainer bd, String nombre, double nota) {
		ObjectSet<Alumno> res = bd.queryByExample(new Alumno(nombre, 0, 0));
		Alumno a = res.next();
		a.setNota(nota);
		bd.store(a);
		muestraAlumnos(bd);
		
	}
	
	/**
	 * Borra un alumno de la base datos dado su nombre
	 * 
	 * @param bd Base de datos de consulta
	 * @param nombre Cadena de caracteres con el nombre y apellido del alumno que queremos borrar
	 */
	public static void borrarAlumnoNombre(ObjectContainer bd, String nombre) {
		ObjectSet<Alumno> res = bd.queryByExample(new Alumno(nombre,0,0));
		Alumno a = res.next();
		bd.delete(a);
		muestraAlumnos(bd);
		
	}
	
	/**
	 * Obtiene todos los alumnos de la base de datos utilizando la API SODA
	 * 
	 * @param bd Base de datos de consulta
	 */
	public static void consultaSODAAlumnos (ObjectContainer bd) {
		Query consulta = bd.query();
		consulta.constrain(Alumno.class);
		ObjectSet<Alumno> resultado = consulta.execute();
		mostrarResultado(resultado);
		
	}
	
	/**
	 * Encuentra a la más guapa de la clase
	 * 
	 * @param bd Base de datos de consulta
	 */
	public static void consultaSODAIsabel (ObjectContainer bd) {
		Query consulta = bd.query();
		consulta.constrain(Alumno.class);
		consulta.descend("nombre").constrain("Isabel Galindo");
		ObjectSet<Alumno> resultado = consulta.execute();
		mostrarResultado(resultado);
		
	}
	
	/**
	 * Muestra en pantalla a los alumnos de una edad dada como parametro
	 * 
	 * @param bd Base de datos de consulta
	 * @param e Entero con la edad de los alumnos que deseamos mostrar
	 */
	public static void consultaSODAEdad(ObjectContainer bd, int e) {
		Query consulta = bd.query();
		consulta.constrain(Alumno.class);
		consulta.descend("edad").constrain(e);
		ObjectSet<Alumno> resultado = consulta.execute();
		mostrarResultado(resultado);
		
	}
	 
	public static void main (String[] args) {
		new File("alumnos.db4o").delete();
		ObjectContainer bd = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "alumnos.db4o");
		try {
			cargaAlumnos(bd, "alumnos.txt");
			muestraAlumnos(bd);
			consultaSODAIsabel(bd);
			consultaSODAEdad(bd, 26);
			
		} finally {
			bd.close();
		}
		
	}

}
