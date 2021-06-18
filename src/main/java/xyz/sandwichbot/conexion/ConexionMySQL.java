package xyz.sandwichbot.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;

import xyz.sandwichbot.configuracion.Global;

public class ConexionMySQL {
	private final String USUARIO = Global.DB_USER;
	private final String CLAVE = Global.DB_PWD;
	private final String HOST = Global.DB_HOST;
	private final String SCHEMA = Global.DB_SCHEMA;
	private final String CON_STRING = "jdbc:mysql://"+USUARIO+":"+CLAVE+"@"+HOST+"/"+SCHEMA+"?reconnect=true";
	private Connection con = null;
	
	private static ConexionMySQL _instancia = new ConexionMySQL();
	
	protected ConexionMySQL() {
		
	}
	public static ConexionMySQL getInstancia() {
		if(_instancia==null)
			_instancia=new ConexionMySQL();
		return _instancia;
	}
	public static Connection conectar() {
		if(_instancia==null)
			_instancia = new ConexionMySQL();
		try {
			//Class.forName("com.mysql.cj.cdbc.Driver");
			Driver d = new Driver();
			//_instancia.con = DriverManager.getConnection(_instancia.CON_STRING);
			_instancia.con = d.connect(_instancia.CON_STRING, null);
			if(_instancia.con!=null) {
				System.out.println("Conectado!");
			}else {
				System.out.println("No ha sido posible establecer la conexion.");
			}
			return _instancia.con;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static Connection getConexion() {
		if(_instancia==null)
			_instancia=new ConexionMySQL();
		return _instancia.con;
	}
	public static void commit() throws SQLException {
		if(_instancia==null)
			_instancia=new ConexionMySQL();
		_instancia.con.commit();
	}
	public static ResultSet query(String query) throws SQLException {
		PreparedStatement s = _instancia.con.prepareStatement(query);
		s.closeOnCompletion();
		return s.executeQuery();
	}
}
