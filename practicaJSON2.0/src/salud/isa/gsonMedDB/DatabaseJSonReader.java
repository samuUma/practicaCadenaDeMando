package salud.isa.gsonMedDB;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

/**
 * Created by jmalvarez on 11/5/16.
 * http://developer.android.com/intl/es/training/basics/network-ops/xml.html
 */
public class DatabaseJSonReader {
	
	private CadenaDeMando cadena;
	private JsonReader json;
	public DatabaseJSonReader(JsonReader j){
		json=j;
	}

	public void setCadenaDeMando(CadenaDeMando d) {
		cadena=d;
	}
	public String parse() throws IOException {
					
		json.beginObject();
		StringBuffer readData = new StringBuffer();
		
		while (json.hasNext()) {
			String name = json.nextName();
			readData.append(cadena.analisis(name,json));
		}

		json.endObject();
		json.close();
		json.close();

		return readData.toString();
	}

}
