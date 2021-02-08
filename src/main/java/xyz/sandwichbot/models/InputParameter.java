package xyz.sandwichbot.models;


public class InputParameter {
	private String key = "none";
	private String value = "none";
	private InputParamType type;
	
	public InputParameter() {
		type=InputParamType.Custom;
	}
	public InputParameter(String clave) {
		this.key=clave;
		type=InputParamType.Custom;
	}
	public InputParameter(String clave, String valor) {
		this.key=clave;
		this.value=valor;
	}
	public InputParameter(String clave, InputParamType tipo) {
		this.key=clave;
		this.type=tipo;
	}
	public InputParameter(String clave, String valor, InputParamType tipo) {
		this.key=clave;
		this.value=valor;
		this.type=tipo;
	}
	public void setKey(String clave) {
		this.key=clave;
	}
	public void setValue(String valor) {
		this.value=valor;
	}
	public void setType(InputParamType tipo) {
		this.type=tipo;
	}
	public String getKey() {
		return key;
	}
	public InputParamType getType() {
		return type;
	}
	public String getValueAsString() {
		return value;
	}
	public int getValueAsInt() {
		return Integer.parseInt((String)value);
	}
	public double getValueAsDouble() {
		return Double.parseDouble(value);
	}
	public float getValueAsFloat() {
		return Float.parseFloat(value);
	}
	public char getValueAsChar() {
		return value.charAt(0);
	}
	public boolean getValueAsBoolean(String[] standarTrue) {
		for(String s : standarTrue) {
			if(value.equals(s)) {
				return true;
			}
		}
		return false;
	}
	public boolean getValueAsBoolean(String textTrue) {
		if(value.equals(textTrue)){
			return true;
		}
		return false;
	}
	public static enum InputParamType{
		Standar,
		Custom,
		Invalid
	}
}