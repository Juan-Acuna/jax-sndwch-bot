package xyz.sandwichframework.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * Representa un Comando extra (comandos que se activan por otros comandos a la espera de una respuesta).
 * Represents an Extra command (commands which are activated by others[commands] and wait for an answer).
 * @author Juancho
 * @version 1.1
 */
public class ModelExtraCommand extends CommandBase implements Comparable<ModelExtraCommand> {
	private static Map<String, ModelExtraCommand> cont = Collections.synchronizedMap(new HashMap<String, ModelExtraCommand>());
	Method _each = null;
	Method _after = null;
	Method _no = null;
	Method _finally = null;
	public static ModelExtraCommand find(String id) {
		return cont.get(id.toLowerCase());
	}
	public static ArrayList<ModelExtraCommand> getAsList() {
		ArrayList<ModelExtraCommand> l = new ArrayList<ModelExtraCommand>(cont.values());
		Collections.sort(l);
		return l;
	}
	public static int getExtraCommandCount() {
		return cont.size();
	}
	public static void compute(ModelExtraCommand xcmd) {
		ModelExtraCommand m = cont.get(xcmd.id);
		if(m==null){
			cont.put(xcmd.id.toLowerCase(), xcmd);
			m = xcmd;
		}
		//System.out.println("comando computado: "+m.getName());
		if(xcmd.action!=null)
			m.setAction(xcmd.action);
		if(xcmd._each!=null)
			m.setEach(xcmd._each);
		if(xcmd._after!=null)
			m.setAfter(xcmd._after);
		if(xcmd._finally!=null)
			m.setFinally(xcmd._finally);
		if(xcmd._no!=null)
			m.setNoExecuted(xcmd._no);
		
		/*if(m.action!=null) {
			System.out.println("accion: "+m.action);
		}
		if(m._each!=null) {
			System.out.println("each: "+m._each);
		}
		if(m._after!=null) {
			System.out.println("after: "+m._after);
		}
		if(m._finally!=null) {
			System.out.println("finally: "+m._finally);
		}
		if(m._no!=null) {
			System.out.println("no: "+m._no);
		}*/
		
	}
	
	public ModelExtraCommand() {
		super();
	}
	public ModelExtraCommand(String name, Method action) {
		super(name);
		this.action = action;
	}
	public void setName(String name) {
		this.id=name;
	}
	public String getName() {
		return id;
	}
	public void setAction(Method a) {
		action=a;
	}
	public void setEach(Method e) {
		_each=e;
	}
	public void setAfter(Method a) {
		_after=a;
	}
	public void setNoExecuted(Method n) {
		_no=n;
	}
	public void setFinally(Method f) {
		_finally=f;
	}
	public void Run(ExtraCmdPacket packet) {
		try {
			action.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void eachRun(ExtraCmdPacket packet) {
		if(_each==null)
			return;
		try {
			_each.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void afterRun(ExtraCmdPacket packet) {
		if(_after==null)
			return;
		try {
			_after.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void NoRun(ExtraCmdPacket packet) {
		if(_no==null)
			return;
		try {
			_no.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void finallyRun(ExtraCmdPacket packet) {
		if(_finally==null)
			return;
		try {
			_finally.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(ModelExtraCommand o) {
		return id.compareTo(o.id);
	}
}
