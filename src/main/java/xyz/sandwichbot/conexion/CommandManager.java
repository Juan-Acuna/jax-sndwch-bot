package xyz.sandwichbot.conexion;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xyz.sandwichbot.conexion.anotaciones.*;

public class CommandManager {
	private static CommandManager _i = new CommandManager();
	private static Connection con;
	private static PreparedStatement _insert, _select, _update, _delete;
	private static String insert, select, update, delete;
	protected CommandManager() {}
	public static void setConexion(Connection con) {
		if(_i == null)
			_i = new CommandManager();
		CommandManager.con = con;
	}
	public static boolean insert(Object objeto, boolean autoId) throws Exception {
		formatStrings();
		String tabla = objeto.getClass().getSimpleName();
		String campos = "";
		String valores = "";
		boolean privAlwd = objeto.getClass().getDeclaredAnnotation(PrivateFieldsAllowed.class)!=null;
		Field[] flds;
		if(privAlwd) {
			flds = objeto.getClass().getDeclaredFields();
		}else {
			flds = objeto.getClass().getFields();
		}
		ArrayToStringSeparator sep = null;
		for(Field f : flds) {
			f.setAccessible(true);
			if(f.getDeclaredAnnotation(ExcludeField.class)!=null) {
				if(f.getDeclaredAnnotation(PrimaryKey.class) != null)
					throw new Exception("No se puede excluir una clave primaria. [CommandManager.update("+tabla+")]");
				continue;
			}
			campos += f.getName()+", ";
			Object o = f.get(objeto);
			sep = f.getDeclaredAnnotation(ArrayToStringSeparator.class);
			if(f.getDeclaredAnnotation(PrimaryKey.class) != null) {
				if(o instanceof Boolean)
					throw new Exception("No se puede usar un booleano como clave primaria. [CommandManager.update("+tabla+")]");
				if(autoId)
					o="null";
			}
			if(sep!=null) {
				if(o!=null) {
					String str = "";
					for(Object obj : (Object[])o) {
						str += sep.value()+obj;
					}
					o=str.substring(1);
				}else {
					o="null";
				}
			}
			if(o==null)
				o="null";
			if(o instanceof Integer || o instanceof Double || o instanceof Float || o instanceof Short || o instanceof Long) {
				valores += o+", ";
			}else if(o instanceof Boolean) {
				valores += ((boolean)o?"1, ":"0, ");
			}else if(o.equals("null")){
				valores += o+", ";
			}else {
				valores += "'"+o+"', ";
			}
		}
		System.out.println("campos:"+campos);
		System.out.println("length:"+campos.length());
		campos = campos.substring(0,campos.length()-2);
		valores = valores.substring(0,valores.length()-2);
		insert = insert.replace("TABLA",tabla);
		insert = insert.replace("CAMPOS",campos);
		insert = insert.replace("VALORES",valores);
		_insert = con.prepareStatement(insert);
		try {
			_insert.execute();
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		_insert.close();
		return true;
	}
	public static <T extends Object> T select(Object id, Class<T> tipo) throws Exception{
		formatStrings();
		String tabla = tipo.getSimpleName();
		String condicion = null;
		boolean privAlwd = tipo.getDeclaredAnnotation(PrivateFieldsAllowed.class)!=null;
		Field[] fs;
		if(privAlwd) {
			fs = tipo.getDeclaredFields();
		}else {
			fs = tipo.getFields();
		}
		for(Field f : fs) {
			f.setAccessible(true);
			if(f.getDeclaredAnnotation(PrimaryKey.class) != null) {
				if(f.getDeclaredAnnotation(ExcludeField.class)!=null) {
					throw new Exception("No se puede excluir una clave primaria.");
				}
				String tp = f.getType().getSimpleName();
				switch(tp) {
					case "Long":
					case "long":
					case "Integer":
					case "int":
					case "Short":
					case "short":
					case "Double":
					case "double":
					case "Float":
					case "float":
						condicion = f.getName()+"="+id;
						break;
					case "Boolean":
					case "boolean":
						throw new Exception("Un identificador no puede ser booleano.");
					default:
						condicion = f.getName()+"='"+id+"'";
						break;
				}
				break;
			}
		}
		if(condicion==null)
			throw new Exception("No se puede seleccionar una tabla sin identificador.");
		select = select.replace("TABLA",tabla);
		select = select.replace("CONDICION",condicion);
		_select = con.prepareStatement(select);
		ResultSet  r;
		try {
			r = _select.executeQuery();
		}catch(Exception ex) {
			return null;
		}
		r.next();
		T t = tipo.newInstance();
		ArrayToStringSeparator sep = null;
		for(Field f : fs) {
			if(f.getDeclaredAnnotation(ExcludeField.class)!=null) {
				continue;
			}
			String tp = f.getType().getSimpleName();
			sep = f.getDeclaredAnnotation(ArrayToStringSeparator.class);
			if(sep!=null) {
				if(r.getObject(f.getName())!=null) {
					String[] ls = r.getString(f.getName()).split(sep.value());
					switch(tp) {
					case "String[]":
						f.set(t, ls);
						break;
					case "Char[]":
					case "char[]":
						char[] c = new char[ls.length];
						for(int i=0;i<ls.length;i++) {
							c[i]=ls[i].charAt(0);
						}
						f.set(t, c);
						break;
					case "Short[]":
					case "short[]":
						short[] c2 = new short[ls.length];
						for(int i=0;i<ls.length;i++) {
							c2[i]=Short.parseShort(ls[i]);
						}
						f.set(t, c2);
						break;
					case "Integer[]":
					case "int[]":
						int[] c3 = new int[ls.length];
						for(int i=0;i<ls.length;i++) {
							c3[i]=Integer.parseInt(ls[i]);
						}
						f.set(t, c3);
						break;
					case "Long[]":
					case "long[]":
						long[] c4 = new long[ls.length];
						for(int i=0;i<ls.length;i++) {
							c4[i]=Long.parseLong(ls[i]);
						}
						f.set(t, c4);
						break;
					case "Double[]":
					case "double[]":
						double[] c5 = new double[ls.length];
						for(int i=0;i<ls.length;i++) {
							c5[i]=Double.parseDouble(ls[i]);
						}
						f.set(t, c5);
						break;
					case "Float[]":
					case "float[]":
						float[] c6 = new float[ls.length];
						for(int i=0;i<ls.length;i++) {
							c6[i]=Float.parseFloat(ls[i]);
						}
						f.set(t, c6);
						break;
					case "Boolean[]":
					case "boolean[]":
						boolean[] c7 = new boolean[ls.length];
						for(int i=0;i<ls.length;i++) {
							c7[i]=Boolean.parseBoolean(ls[i]);
						}
						f.set(t, c7);
						break;
					case "Object[]":
					default:
						Object[] c8 = new Object[ls.length];
						for(int i=0;i<ls.length;i++) {
							c8[i]=ls[i];
						}
						f.set(t, c8);
						break;
					}
					continue;
				}else {
					continue;
				}
			}
			switch(tp) {
				case "Long":
				case "long":
					f.set(t, r.getLong(f.getName()));
					break;
				case "Integer":
				case "int":
					f.set(t, r.getInt(f.getName()));
					break;
				case "Short":
				case "short":
					f.set(t, r.getShort(f.getName()));
					break;
				case "Double":
				case "double":
					f.set(t, r.getDouble(f.getName()));
					break;
				case "Float":
				case "float":
					f.set(t, r.getFloat(f.getName()));
					break;
				case "Boolean":
				case "boolean":
					try {
						f.set(t, r.getBoolean(f.getName()));
					}catch(SQLException ex2) {
						f.set(t, (r.getShort(f.getName())!=0));
					}
					break;
				case "Char":
				case "char":
				case "String":
					f.set(t, r.getString(f.getName()));
					break;
				default:
					f.set(t, r.getObject(f.getName()));
					break;
			}
		}
		r.close();
		return t;
	}
	public static <T extends Object> List<T> selectAll(Class<T> tipo) throws Exception{
		formatStrings();
		String tabla = tipo.getSimpleName();
		boolean privAlwd = tipo.getDeclaredAnnotation(PrivateFieldsAllowed.class)!=null;
		Field[] fs;
		if(privAlwd) {
			fs = tipo.getDeclaredFields();
		}else {
			fs = tipo.getFields();
		}
		select = select.replace("TABLA",tabla);
		select = select.replace(" WHERE CONDICION","");
		_select = con.prepareStatement(select);
		ResultSet r;
		try {
			r = _select.executeQuery();
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		T t;
		List<T> l = new ArrayList<T>();
		while(r.next()) {
			t = tipo.newInstance();
			ArrayToStringSeparator sep = null;
			for(Field f : fs) {
				f.setAccessible(true);
				if(f.getDeclaredAnnotation(ExcludeField.class)!=null) {
					continue;
				}
				String tp = f.getType().getSimpleName();
				sep = f.getDeclaredAnnotation(ArrayToStringSeparator.class);
				if(sep!=null) {
					if(r.getObject(f.getName())!=null) {
						String[] ls = r.getString(f.getName()).split(sep.value());
						switch(tp) {
						case "String[]":
							f.set(t, ls);
							break;
						case "Char[]":
						case "char[]":
							char[] c = new char[ls.length];
							for(int i=0;i<ls.length;i++) {
								c[i]=ls[i].charAt(0);
							}
							f.set(t, c);
							break;
						case "Short[]":
						case "short[]":
							short[] c2 = new short[ls.length];
							for(int i=0;i<ls.length;i++) {
								c2[i]=Short.parseShort(ls[i]);
							}
							f.set(t, c2);
							break;
						case "Integer[]":
						case "int[]":
							int[] c3 = new int[ls.length];
							for(int i=0;i<ls.length;i++) {
								c3[i]=Integer.parseInt(ls[i]);
							}
							f.set(t, c3);
							break;
						case "Long[]":
						case "long[]":
							long[] c4 = new long[ls.length];
							for(int i=0;i<ls.length;i++) {
								c4[i]=Long.parseLong(ls[i]);
							}
							f.set(t, c4);
							break;
						case "Double[]":
						case "double[]":
							double[] c5 = new double[ls.length];
							for(int i=0;i<ls.length;i++) {
								c5[i]=Double.parseDouble(ls[i]);
							}
							f.set(t, c5);
							break;
						case "Float[]":
						case "float[]":
							float[] c6 = new float[ls.length];
							for(int i=0;i<ls.length;i++) {
								c6[i]=Float.parseFloat(ls[i]);
							}
							f.set(t, c6);
							break;
						case "Boolean[]":
						case "boolean[]":
							boolean[] c7 = new boolean[ls.length];
							for(int i=0;i<ls.length;i++) {
								c7[i]=Boolean.parseBoolean(ls[i]);
							}
							f.set(t, c7);
							break;
						case "Object[]":
						default:
							Object[] c8 = new Object[ls.length];
							for(int i=0;i<ls.length;i++) {
								c8[i]=ls[i];
							}
							f.set(t, c8);
							break;
						}
						continue;
					}else {
						continue;
					}
				}
				switch(tp) {
					case "Long":
					case "long":
						f.set(t, r.getLong(f.getName()));
						break;
					case "Integer":
					case "int":
						f.set(t, r.getInt(f.getName()));
						break;
					case "Short":
					case "short":
						f.set(t, r.getShort(f.getName()));
						break;
					case "Double":
					case "double":
						f.set(t, r.getDouble(f.getName()));
						break;
					case "Float":
					case "float":
						f.set(t, r.getFloat(f.getName()));
						break;
					case "Boolean":
					case "boolean":
						try {
							f.set(t, r.getBoolean(f.getName()));
						}catch(SQLException ex2) {
							f.set(t, r.getShort(f.getName()));
						}
						break;
					case "Char":
					case "char":
					case "String":
						f.set(t, r.getString(f.getName()));
						break;
					default:
						f.set(t, r.getObject(f.getName()));
						break;
				}
			}
			l.add(t);
		}
		r.close();
		return l;
	}
	public static <T extends Object> List<T> selectWhere(String campo, Object valor, Class<T> tipo) throws Exception{//POR TERMINAR
		formatStrings();
		if(campo==null || valor==null)
			throw new Exception("Campo o valor incompleto.");
		String tabla = tipo.getSimpleName();
		String condicion = null;
		boolean privAlwd = tipo.getDeclaredAnnotation(PrivateFieldsAllowed.class)!=null;
		Field[] fs;
		if(privAlwd) {
			fs = tipo.getDeclaredFields();
		}else {
			fs = tipo.getFields();
		}
		String tp = valor.getClass().getSimpleName();
		switch(tp) {
			case "Long":
			case "long":
			case "Integer":
			case "int":
			case "Short":
			case "short":
			case "Double":
			case "double":
			case "Float":
			case "float":
				condicion = campo+"="+valor;
				break;
			case "Boolean":
			case "boolean":
				condicion = campo+"='"+((boolean)valor?"1":"0")+"'";
			default:
				condicion = campo+"='"+valor+"'";
				break;
		}
		select = select.replace("TABLA",tabla);
		select = select.replace("CONDICION",condicion);
		_select = con.prepareStatement(select);
		ResultSet r;
		try {
			r = _select.executeQuery();
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		T t;
		List<T> l = new ArrayList<T>();
		while(r.next()) {
			t = tipo.newInstance();
			ArrayToStringSeparator sep = null;
			for(Field f : fs) {
				f.setAccessible(true);
				if(f.getDeclaredAnnotation(ExcludeField.class)!=null) {
					continue;
				}
				tp = f.getType().getSimpleName();
				sep = f.getDeclaredAnnotation(ArrayToStringSeparator.class);
				if(sep!=null) {
					String[] ls = r.getString(f.getName()).split(sep.value());
					switch(tp) {
					case "String[]":
						f.set(t, ls);
						break;
					case "Char[]":
					case "char[]":
						char[] c = new char[ls.length];
						for(int i=0;i<ls.length;i++) {
							c[i]=ls[i].charAt(0);
						}
						break;
					case "Short[]":
					case "short[]":
						short[] c2 = new short[ls.length];
						for(int i=0;i<ls.length;i++) {
							c2[i]=Short.parseShort(ls[i]);
						}
						break;
					case "Integer[]":
					case "int[]":
						int[] c3 = new int[ls.length];
						for(int i=0;i<ls.length;i++) {
							c3[i]=Integer.parseInt(ls[i]);
						}
						break;
					case "Long[]":
					case "long[]":
						long[] c4 = new long[ls.length];
						for(int i=0;i<ls.length;i++) {
							c4[i]=Long.parseLong(ls[i]);
						}
						break;
					case "Double[]":
					case "double[]":
						double[] c5 = new double[ls.length];
						for(int i=0;i<ls.length;i++) {
							c5[i]=Double.parseDouble(ls[i]);
						}
						break;
					case "Float[]":
					case "float[]":
						float[] c6 = new float[ls.length];
						for(int i=0;i<ls.length;i++) {
							c6[i]=Float.parseFloat(ls[i]);
						}
						break;
					case "Boolean[]":
					case "boolean[]":
						boolean[] c7 = new boolean[ls.length];
						for(int i=0;i<ls.length;i++) {
							c7[i]=Boolean.parseBoolean(ls[i]);
						}
						break;
					case "Object[]":
					default:
						Object[] c8 = new Object[ls.length];
						for(int i=0;i<ls.length;i++) {
							c8[i]=ls[i];
						}
						break;
					}
					continue;
				}
				switch(tp) {
					case "Long":
					case "long":
						f.set(t, r.getLong(f.getName()));
						break;
					case "Integer":
					case "int":
						f.set(t, r.getInt(f.getName()));
						break;
					case "Short":
					case "short":
						f.set(t, r.getShort(f.getName()));
						break;
					case "Double":
					case "double":
						f.set(t, r.getDouble(f.getName()));
						break;
					case "Float":
					case "float":
						f.set(t, r.getFloat(f.getName()));
						break;
					case "Boolean":
					case "boolean":
						try {
							f.set(t, r.getBoolean(f.getName()));
						}catch(SQLException ex2) {
							f.set(t, (r.getShort(f.getName())!=0));
						}
						break;
					case "Char":
					case "char":
					case "String":
						f.set(t, r.getString(f.getName()));
						break;
					default:
						f.set(t, r.getObject(f.getName()));
						break;
				}
			}
			l.add(t);
		}
		r.close();
		return l;
	}
	public static boolean update(Object objeto) throws Exception {
		formatStrings();
		String tabla = objeto.getClass().getSimpleName();
		String valores = "";
		String condicion = null;
		boolean privAlwd = objeto.getClass().getDeclaredAnnotation(PrivateFieldsAllowed.class)!=null;
		Field[] flds;
		if(privAlwd) {
			flds = objeto.getClass().getDeclaredFields();
		}else {
			flds = objeto.getClass().getFields();
		}
		ArrayToStringSeparator sep = null;
		for(Field f : flds) {
			f.setAccessible(true);
			if(f.getDeclaredAnnotation(ExcludeField.class)!=null) {
				if(f.getDeclaredAnnotation(PrimaryKey.class) != null)
					throw new Exception("No se puede excluir una clave primaria. [CommandManager.update("+tabla+")]");
				continue;
			}
			sep = f.getDeclaredAnnotation(ArrayToStringSeparator.class);
			Object o = f.get(objeto);
			if(sep!=null) {
				if(o!=null) {
					String str = "";
					for(Object obj : (Object[])o) {
						str += sep.value()+obj;
					}
					o=str.substring(1);
				}else {
					o="null";
				}
			}
			if(f.getDeclaredAnnotation(PrimaryKey.class) != null) {
				if(o instanceof Integer || o instanceof Double || o instanceof Float || o instanceof Short || o instanceof Long) {
					condicion = f.getName()+"="+o;
				}else if(o instanceof Boolean){
					throw new Exception("No se puede usar un booleano como clave primaria. [CommandManager.update("+tabla+")]");
				}else {
					condicion = f.getName()+"='"+o+"'";
				}
			}else {
				if(o==null)
					o="null";
				if(o instanceof Integer || o instanceof Double || o instanceof Float || o instanceof Short || o instanceof Long) {
					valores += f.getName() + "=" + o + ", ";
				}else if(o instanceof Boolean) {
					valores += f.getName() + "=" + ((boolean)o?"1, ":"0, ");
				}else if(o.equals("null")){
					valores += f.getName() + "=" + o+", ";
				}else {
					valores += f.getName() + "='"+o+"', ";
				}
			}
		}
		if(condicion==null)
			throw new Exception("No se puede actualizar una tabla sin identificador.");
		valores = valores.substring(0,valores.length()-2);
		update = update.replace("TABLA",tabla);
		update = update.replace("VALORES",valores);
		update = update.replace("CONDICION",condicion);
		_update = con.prepareStatement(update);
		boolean b;
		try {
			_update.execute();
			b = _update.getUpdateCount() != -1;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		_update.close();
		return b;
	}
	public static boolean delete(Object objeto) throws Exception {
		formatStrings();
		String tabla = objeto.getClass().getSimpleName();
		String condicion = null;
		Field[] flds = objeto.getClass().getFields();
		for(Field f : flds) {
			f.setAccessible(true);
			Object o = f.get(objeto);
			if(f.getDeclaredAnnotation(PrimaryKey.class) != null) {
				if(o==null) {
					throw new Exception("No se puede eliminar con una clave primaria nula. [CommandManager.update("+tabla+")]");
				}
				if(o instanceof Integer || o instanceof Double || o instanceof Float || o instanceof Short || o instanceof Long) {
					condicion = f.getName()+"="+o;
				}else {
					condicion = f.getName()+"='"+o+"'";
				}
			}
			
		}
		if(condicion==null)
				throw new Exception("No se puede eliminar una tabla sin identificador.");
		delete = delete.replace("TABLA",tabla);
		delete = delete.replace("CONDICION",condicion);
		_delete = con.prepareStatement(delete);
		boolean b;
		try {
			b =_delete.execute();
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		_delete.close();
		return b;
	}
	private static void formatStrings() {
		insert = "INSERT INTO TABLA (CAMPOS) VALUES (VALORES)";
		select = "SELECT * FROM TABLA WHERE CONDICION";
		update = "UPDATE TABLA SET VALORES WHERE CONDICION";
		delete = "DELETE FROM TABLA WHERE CONDICION";
	}
}
