package com.dam1d.ejer4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Entrenador {
	final static String FILENAME = System.getProperty("user.home")
			+ "/test.db4o";
	private static int correctas = 0;

	public static void BorraDatos() {
		new File(FILENAME).delete();
	}

	/**
	 * Carga en la base de datos las preguntas contenidas en un fichero de texto
	 * plano
	 * 
	 * @param preguntas
	 *            Fichero con las preguntas que queremos para la sesión de
	 *            entrenamiento
	 */
	public static void CargaPreguntas(ObjectContainer db, String preguntas) {
		String[] res = new String[4];
		int correct;
			// do something with db4o
			try {
				BufferedReader buff = new BufferedReader(new FileReader(
						preguntas));
				String preg = buff.readLine();
				boolean eof = false;
				while (!eof) {
					for (int i = 0; i < res.length; i++) {
						res[i] = buff.readLine();
						
					}
					String corr = buff.readLine();
					correct = Integer.valueOf(corr);
					Pregunta pregunta = new Pregunta(preg, res, correct);
					db.store(pregunta);
					preg = buff.readLine();
					preg = buff.readLine();
					if (preg == null)
						eof = true;

				}
				buff.close();
				System.out.println("Utilizando preguntas de " + preguntas);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}

	public static void BateriaPreguntas(ObjectContainer db, int numero) {
		Query query = db.query();
		query.constrain(Pregunta.class);
		ObjectSet<Pregunta> bateria = query.execute();
		//Pregunta pregunta;
		//Scanner sc = new Scanner(System.in);
		for (int i = 0; i < numero; i++) {
			bateria.next().MuestraPregunta();
//			int r = sc.nextInt();
//			if (r == pregunta.getCorrect()) {
//				System.out.println("¡Correcto!");
//				correctas++;
//
//			} else {
//				System.out.println("Incorrecto");
//				
//			}
//			System.out.println("");

		}
		//sc.close();
		System.out.println("Has respondido correctamente " + correctas
				+ " de las " + numero + " preguntas del test");

	}

	public static void main(String[] args) {
		BorraDatos();
		ObjectContainer db = Db4oEmbedded.openFile(
				Db4oEmbedded.newConfiguration(), FILENAME);
		try {
			CargaPreguntas(db, "test1eva.txt");
			BateriaPreguntas(db, 3);
			
		} finally {
			db.close();
		}

	}

}
