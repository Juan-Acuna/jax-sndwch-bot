package com.jaxsandwich.discordbot.main.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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

		  // Abrir la conexi�n e indicar que ser� de tipo GET
		  HttpURLConnection conexion = (HttpURLConnection) uri.openConnection();
		  conexion.addRequestProperty("user-agent", "Mozilla/5.0");
		  conexion.addRequestProperty("Accept-Charset","text/html; charset=utf-8");
		  conexion.setRequestMethod("GET");
		  //System.out.println("mensaje: " + conexion.getHeaderField("Content-type"));
		  conexion.connect();
		  // B�feres para leer
		  InputStreamReader isr = new InputStreamReader(conexion.getInputStream(),"utf-8");
		  BufferedReader rd = new BufferedReader(isr);
		  String linea;
		  // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
		  while ((linea = rd.readLine()) != null) {
		    resultado += linea;
		  }
		  // Cerrar el BufferedReader
		  rd.close();
		  // Regresar resultado, pero como cadena, no como StringBuilder
		  ByteBuffer buffer = StandardCharsets.UTF_8.encode(resultado); 

		  return StandardCharsets.UTF_8.decode(buffer).toString();
		}
}
