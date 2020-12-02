

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static int horasDia; //Horas representadas en minutos
	//Hemos usado esos generos?
	static boolean drama; //codigo 1
	static boolean scifi; //codigo 2
	static boolean komedia; //codigo 3
	static boolean terror; //codigo 4
	//Listas de cada genero
	static String[] listaDrama = new String[256];
	static String[] listaScifi = new String[256];
	static String[] listaKomedia = new String[256];
	static String[] listaTerror = new String[256];
	static int minDrama = 1000;
	static int minScifi = 1000;
	static int minKomedia = 1000;
	static int minTerror = 1000;
	//Lista de peliculas en cada dia
	static String[] listaSabado = new String[256];
	static String[] listaDomingo = new String[256];
	//Contadores
	static int contDrama = 0;
	static int contScifi = 0;
	static int contKomedia = 0;
	static int contTerror = 0;
	static int contSabado;
	static int contDomingo;
	//Para saber el dia
	static boolean esSabado = true;
	//Para saber si estan las listas cargadas
	static boolean cargadas = false;
	
	public static void main(String[] args) {
		bienvenida();
		temporizador(3);
		login();
		lector_csv("peliculas.csv");
		contSabado = 0;
		horasDia=8*60;
		esSabado=true;
		drama = true;
		scifi = true;
		komedia = true;
		terror = true;
		pantalla_generos();
		System.out.println("---------------\nPasamos al Domingo\n---------------");
		contDomingo = 0;
		horasDia=6*60;
		esSabado = false;
		drama = true;
		scifi = true;
		komedia = true;
		terror = true;
		pantalla_generos();
		pantalla_final();
	} // } final de main

	public static void bienvenida() {
		
		System.out.println("      BIENVENIDO AL CINE ELORRIETA - BILBO");
		System.out.println();
		System.out.println("  Disfruta de nuestra amplia oferta en cartelera");
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		System.out.println();
		System.out.println("          Hora actual: " + dateFormat.format(date));

	} // } final de bienvenida
	
	public static void temporizador(int segs){
		int milis = segs * 1000;
		try {
			
			Thread.sleep(milis); // Método que hace esperar programa X milisegundos.
		}
		catch(Exception e) {
			System.exit(0);
		}
	} // } final de temporizador
	
	public static void login() {
		String nombreUsuario="a"; // Designar nombre de usuario
		String pwdUsuario="a"; // Designar contraseña
		
		boolean correcto=false;
		
		while (correcto==false) { // Se pedirá usuario y contraseña hasta que los datos sean correctos
			System.out.println("Introduce tu nombre de usuario"); // Pedir nombre de usuario
			String usuario=sc.nextLine();	//Registro variable usuario desde teclado
			
			System.out.println("Introduce tu contraseña"); // Pedir contraseña
			String contrasena=sc.nextLine(); // Registro variable contraseña desde teclado
			
			if (usuario.equals(nombreUsuario) && contrasena.equals(pwdUsuario)) { // Condición que confirma el registro del usuario
			correcto=true;
			System.out.println("Tu usuario y contraseña son correctos");
			}
			else if(usuario.equals("0") || contrasena.equals("0")) {
				Main.main(null);
				System.exit(0);
			}
		}
	} // } final de login
	
	public static void lector_csv(String ruta) {
		try{
			FileReader archivo = new FileReader(ruta); //Para encontrar el archivo debe estar colocado en la carpeta del proyecto, donde se encuentran las carpetas .setting, bin y src
			System.out.println("Archivo cargado");
			BufferedReader lector = new BufferedReader(archivo); //Se abre un lector con buffer para el archivo
			String cadena = lector.readLine();
			while (cadena!=null) {
				parser_csv(cadena);
				cadena = lector.readLine();
			}
			lector.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Archivo no encontrado, adios");
			e.printStackTrace();
			System.exit(1);
		}
		catch(IOException e) {
			System.out.println("Error en IO, adios");
			e.printStackTrace();
			System.exit(1);
		}
	} // } final de lector_csv
	
	public static void parser_csv(String linea) {
		
		//El contador para los bucles que maneja donde estamos en el String analizado
		int i = 0;
		
		//Bucle para buscar el nombre
		while(linea.charAt(i)!=';') {
			i++;
		}
		
		String nombre = linea.substring(0,i);
		
		i = i+1;
		int j = i; //para saber donde empiezan las horas
		while (linea.charAt(i)!=';') {
			i++;
		}
		String horas = linea.substring(j,i); //Las horas que dura una película, se usan para mostrarselo al usuario
		
		int mins = Integer.parseInt(String.valueOf(horas.charAt(0))) * 60 + Integer.parseInt(horas.substring(2,4)); //Las horas que dura una película en minutos, para uso interno
		
		String minutos = String.valueOf(mins); //El valor en minutos como string para añadirlo a la string en el array
		
		String categoria = linea.substring(i+1,linea.length());
		
		if (categoria.equals("Drama")) {
			if(mins<minDrama) {
				minDrama = mins;
			}
			listaDrama[contDrama] = nombre.concat("+").concat(horas).concat("+").concat(minutos);
			contDrama++;
		}
		else if (categoria.equals("Sci-Fi")) {
			if(mins<minScifi) {
				minScifi = mins;
			}
			listaScifi[contScifi] = nombre.concat("+").concat(horas).concat("+").concat(minutos);
			contScifi++;
		}
		else if (categoria.equals("Komedia")) {
			if(mins<minKomedia) {
				minKomedia = mins;
			}
			listaKomedia[contKomedia] = nombre.concat("+").concat(horas).concat("+").concat(minutos);
			contKomedia++;
		}
		else if(categoria.equals("Terror")) {
			if(mins<minTerror) {
				minTerror = mins;
			}
			listaTerror[contTerror] = nombre.concat("+").concat(horas).concat("+").concat(minutos);
			contTerror++;
		}
		else {
			System.out.println("No he podido meterla");
		}
	} // } final de parser_csv
	
	public static String[] escogerLista(int cod) {
		String[] lista = null;
		switch(cod) {
		case 1:
			lista = listaDrama;
			System.out.println("Drama");
			break;
		case 2:
			lista = listaScifi;
			System.out.println("Sci-Fi");
			break;
		case 3:
			lista = listaKomedia;
			System.out.println("Komedia");
			break;
		case 4:
			lista = listaTerror;
			System.out.println("Terror");
			break;
		default:
			System.out.println("No existe la lista");
			break;
		}
		return lista;
	} // } final de escogerLista
	
	public static String[] filtrarLista(String[] lista) {
		int i = 0;
		String[] filtrada = new String[256];
		int punt = 0;
		i = 0;
		while(lista[i]!=null) {
			int j = lista[i].length()-1;
			while(lista[i].charAt(j)!='+') {
				j--;
			}
			j=j+1;
			int minutos = Integer.parseInt(lista[i].substring(j,lista[i].length()));
			if (minutos <= horasDia && punt<256) {
				filtrada[punt] = lista[i];
				punt++;
			}
			i++;
		}
		return filtrada;
	}// } final de filtrarLista

	public static void printLista(int cod) {
		String[] lista = filtrarLista(escogerLista(cod));
		printListaPlus(lista);
	} // } final de printLista
	
	
	public static void printListaPlus(String[] lista) {
		int i = 0;
		while (lista[i]!=null) {
			String nombre;
			int j=0;
			while (lista[i].charAt(j)!='+') {
				j++;
			}
			nombre = lista[i].substring(0,j);
		
			j=j+1;
			int k = j;
		
			while(lista[i].charAt(j)!='+') {
			j++;
			}
		
			String horas = lista[i].substring(k,j);
		
			System.out.println((i+1) + "- Titulo " + nombre + " Horas " + horas);
		
			i++;
		}
	}
	
	
	
	public static void pantalla_generos() {
		System.out.println("Te quedan " + horasDia / 60 + " horas y " + horasDia % 60 + " minutos");
		System.out.println("--------------------------------");
		System.out.println("Generos a escoger");
		System.out.println("--------------------------------");
		System.out.println("0-Salir");
		if (drama==true) {
			System.out.println("1-Drama");
		}
		if (scifi==true) {
			System.out.println("2-Ciencia ficcion");
		}
		if (komedia==true) {
			System.out.println("3-Comedia");
		}
		if (terror==true) {
			System.out.println("4-Terror");
		}
		System.out.println("--------------------------------");
		if(!drama && !terror && !scifi && !komedia) {
		}
		else {
			menu_generos();
		}
		
		
	} // } final de pantalla_generos
	
	public static void menu_generos(){
		int codigoGenero=cogerInt();
		if (codigoGenero==0) {
			Main.main(null);
			System.exit(0);
		}
		else if (codigoGenero==1 && drama) {
			System.out.println("Has elegido Drama");
			pantallaPeliculas(1);
		}
		else if (codigoGenero==2 && scifi) {
			System.out.println("Has elegido Ciencia ficcion");
			pantallaPeliculas(2);
		}
		else if (codigoGenero==3 && komedia) {
			System.out.println("Has elegido Comedia");
			pantallaPeliculas(3);
		}
		else if (codigoGenero==4 && terror) {
			System.out.println("Has elegido Terror");
			pantallaPeliculas(4);
		}
		else{
			System.out.println("Escoge un genero valido");
			pantalla_generos();
		}
	} // } final de menu_generos
	
	public static void pantallaPeliculas(int codGenero) {
		System.out.println("--------------");
		System.out.printf("Peliculas del genero ");
		printLista(codGenero);
		int horas = horasDia/60;
		int minutos = horasDia % 60;
		System.out.println("Que duran menos de " + horas + " horas y " + minutos + " minutos");
		System.out.println("---------------");
		int mins = elegirPelicula(filtrarLista(escogerLista(codGenero)));
		eliminarGenero(codGenero);
		Calculartiempodisponible(mins);
		
	} // } final de pantallaPeliculas
	
	public static int elegirPelicula(String[] lista) {
		System.out.println("Introduce el código de tu película");
		int cod = 0;
		do {
			cod = cogerInt();
			cod = cod-1;
			if (cod==-1) {
				Main.main(null);
				System.exit(0);
				return 0;
			}
			else if(cod==-2) {
				pantalla_generos();
				System.exit(0);
				return 0;
			}
			else if(lista[cod]==null) {
				System.out.println("Codigo erroneo");
			}
			else {
				int j=lista[cod].length()-1;
				while(lista[cod].charAt(j)!='+'){
					j--;
				}
				int minutos = Integer.parseInt(lista[cod].substring(j,lista[cod].length()));
				if(esSabado == true) {
					listaSabado[contSabado]=lista[cod];
					contSabado++;
					return minutos;
				}
				else {
					listaDomingo[contDomingo]=lista[cod];
					contDomingo++;
					return minutos;
				}
			}
		}while(lista[cod]!=null||cod!=-1||cod!=-2);
		System.exit(0);
		return 0;
		
	} // } final de elegirPelicula
	
	public static void eliminarGenero(int codGenero) {
		switch(codGenero) {
		case 1:
			drama = false;
			break;
		case 2:
			scifi = false;
			break;
		case 3:
			komedia = false;
			break;
		case 4:
			terror = false;
			break;
		}
	} // } final de eliminarGenero
	
	
	
	public static void Calculartiempodisponible(int tiempoPelicula) { 
		//Método que recibe el argumento tiempo de duración de la película seleccionada
			
		int minutosDisponibles= horasDia-tiempoPelicula ;//Minutos disponibles para ver peliculas
		
		int minDuracionDisponible = 1000;
		
		
		if(minDrama<minDuracionDisponible && drama) {
			minDuracionDisponible = minDrama;
		}
		if(minScifi<minDuracionDisponible && scifi) {
			minDuracionDisponible = minScifi;
		}
		if(minKomedia<minDuracionDisponible && komedia) {
			minDuracionDisponible = minKomedia;
		}
		if(minTerror<minDuracionDisponible && terror) {
			minDuracionDisponible = minTerror;
		}
		
			if (minutosDisponibles>=minDuracionDisponible) {
				//Condición para que la duración sea superior a 60 minutos("Ninguna película dura menos de 60 minutos)
				horasDia=minutosDisponibles; //Registro el tiempo que aun queda disponible
				pantalla_generos();//Regreso a la selección de películas disponibles
			}			
	}// } final de Calculartiempodisponible
	
	
	
	public static void pantalla_final(){
		String respuestaSi="si"; // Designar la respuesta si
		String respuestaNo="no"; // Designar la respuesta no
			
		System.out.println("Las peliculas seleccionadas se han guardado.");
		System.out.println("---------------");
		System.out.println("Las peliculas del sabado");
		printListaPlus(listaSabado);
		System.out.println("---------------");
		System.out.println("Las peliculas del domingo");
		printListaPlus(listaDomingo);
		boolean ok = false;
		while (!ok) {
			
			System.out.println("¿Quieres guardar los cambios? Responde con si o no.");
			String respuesta=sc.nextLine();
			
			if (respuesta.contentEquals(respuestaSi)) { // si la respuesta es si muestra lo siente.
				ok = true;
				System.out.println("Disfruta del fin de semana");
				System.out.println("¡Gracias!");
			}
			else if (respuesta.contentEquals(respuestaNo)) { // si la respuesta es no vuelve a empezar desde el pricipio.
				temporizador(2);
				Main.main (null);
				System.exit(0);
			}
			else if (respuesta.equals("0")) {
				Main.main(null);
				System.exit(0);
				}
			else {
				System.out.println("Codigo incorrecto");
			}
		}
		
	
		} // } final de pantalla_final
	
	
	
	public static int cogerInt() {
		boolean ok = false;
		int entero = 0;
		do {
			try {
			ok = true;
			entero = Integer.parseInt(sc.nextLine());
		}	
		catch(Exception e) {
		ok = false;
		System.out.println("Por favor introduce un numero entero");
	}	
	}while(ok==false);
	return entero; 
	}// } final de cogerInt
	
}// } final
