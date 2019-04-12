package salud.isa.gsonMedDB;

import java.io.IOException;


import com.google.gson.stream.JsonReader;

public class ReadMedicine extends CadenaDeMando {

	private static final String MEDICINES_TAGNAME = "medicines";
	private static final String NAME_FIELD_TAGNAME = "name";
		
	public ReadMedicine(CadenaDeMando rm) throws IOException {
		// TODO Auto-generated constructor stub
		setSucessor(rm);
		
	}

	private StringBuffer readMedicines(JsonReader reader) throws IOException {
		StringBuffer medicineData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			medicineData.append(readMedicineEntry(reader)).append("\n");
			reader.endObject();
		}
		medicineData.append("\n");
		reader.endArray();
		return medicineData;
	}
	
	private String readMedicineEntry(JsonReader reader) throws IOException {
		//	        reader.require(XmlPullParser.START_TAG, ns, SINGLE_ELEMENT_TAGNAME);
		String medName = null;
		while(reader.hasNext()){
			String name = reader.nextName();
			switch (name) {
			case NAME_FIELD_TAGNAME:
				medName = reader.nextString();
				break;
			default:
				reader.skipValue();
			}
		}

		return medName;
	}

	
	
	@Override
	public String analisis(String name, JsonReader reader) throws IOException {
		// TODO Auto-generated method stub
		StringBuffer readData = new StringBuffer();
		if (name.equals(MEDICINES_TAGNAME)) {
		
			readData.append(readMedicines(reader)).append("\n");
		}else if (sucessor!=null){
			readData.append(sucessor.analisis(name, reader));
		}else {
			reader.skipValue();
			readData.append("Category " + name + " not processed.").append("\n");
		}
		
		return readData.toString();
	}

	

	
	
}
