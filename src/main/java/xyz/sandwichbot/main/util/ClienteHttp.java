package xyz.sandwichbot.main.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ClienteHttp {
	
	public class Metodo{
		public static final String GET = "GET";
		public static final String POST= "POST";
	}

	public static String peticionHttp(String url) throws Exception {
		  // Esto es lo que vamos a devolver
		  String resultado = "";
		  // Crear un objeto de tipo URL
		  URL uri = new URL(url);

		  // Abrir la conexión e indicar que será de tipo GET
		  HttpURLConnection conexion = (HttpURLConnection) uri.openConnection();
		  conexion.addRequestProperty("user-agent", "Mozilla/5.0");
		  conexion.addRequestProperty("Accept-Charset", "utf-8");
		  conexion.setRequestMethod("GET");
		  conexion.connect();
		  // Búferes para leer
		  BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		  String linea;
		  // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
		  while ((linea = rd.readLine()) != null) {
		    resultado += linea;
		  }
		  // Cerrar el BufferedReader
		  rd.close();
		  // Regresar resultado, pero como cadena, no como StringBuilder
		  ByteBuffer buffer = StandardCharsets.UTF_16BE.encode(resultado); 

		  return StandardCharsets.UTF_16BE.decode(buffer).toString();
		}
}
