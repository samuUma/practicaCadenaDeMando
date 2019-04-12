package salud.isa.gsonMedDB;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public abstract class CadenaDeMando {
	
	protected CadenaDeMando sucessor;
	protected String respuesta;
	
	public void setSucessor(CadenaDeMando s) {
		sucessor=s;
	}
	
	abstract public String analisis( String name, JsonReader reader) throws IOException;
}
