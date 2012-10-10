package cei.util.convert;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import cei.util.Formatter;

public class ConvertToJson {
	private static volatile ConvertToJson instance = null;

	private ConvertToJson() {}

	public static ConvertToJson getInstance() {
		if (instance == null) {
			synchronized (ConvertToJson.class) {
				if (instance == null)
					instance = new ConvertToJson();
			}
		}

		return instance;
	}
	
	public String getJson(Object data) {
		StringBuffer json = new StringBuffer();
		composit(data, json);

		return json.toString();
	}

	@SuppressWarnings("rawtypes")
	private void composit(Object data, StringBuffer json) {
		if(data == null) {
			json.append("null");
			return;
		}

		if(data instanceof List) {
			boolean use = false;
			json.append("[");

			for(Object one : (List)data) {
				composit(one, json);
				json.append(",");

				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof Map) {
			json.append("{");

			Map mapData = (Map)data;
			Iterator it = mapData.keySet().iterator();
			while(it.hasNext()) {
				Object key = it.next();

				json.append(key.toString());
				json.append(":");
				composit(mapData.get(key), json);
				json.append(",");
			}

			if(mapData.size() > 0) json.deleteCharAt(json.length() - 1);
			json.append("}");
		}
		else if(data instanceof String)		json.append("\'" + str2HTMLCharacter((String)data) + "\'");
		else if(data instanceof Character)	json.append("\'" + str2HTMLCharacter(Character.toString((Character)data)) + "\'");
		else if(data instanceof Number)		json.append(data);
		else if(data instanceof Boolean)		json.append(((Boolean)data).toString());
		else if(data instanceof Date)			json.append(new SimpleDateFormat("yyyyMMdd").format((Date)data));
		else if(data instanceof Object[]) {
			boolean use = false;
			json.append("[");

			for(Object obj : (Object[])data) {
				composit(obj, json);
				json.append(",");

				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof int[]) {
			boolean use = false;
			json.append("[");

			for(Object obj : (int[])data) {
				composit(obj, json);
				json.append(",");
				
				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof float[]) {
			boolean use = false;
			json.append("[");

			for(Object obj : (float[])data) {
				composit(obj, json);
				json.append(",");

				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof long[]) {
			boolean use = false;
			json.append("[");
			for(Object obj : (long[])data) {
				composit(obj, json);
				json.append(",");
				
				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof double[]) {
			boolean use = false;
			json.append("[");
			for(Object obj : (double[])data) {
				composit(obj, json);
				json.append(",");
				
				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof short[]) {
			boolean use = false;
			json.append("[");
			for(Object obj : (short[])data) {
				composit(obj, json);
				json.append(",");
				
				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof byte[]) {
			boolean use = false;
			json.append("[");
			for(Object obj : (byte[])data) {
				composit(obj, json);
				json.append(",");
				
				if(!use) use = true;
			}

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("]");
		}
		else if(data instanceof CLOB) {
			composit(Formatter.CLOB2String((CLOB)data), json);
		}
		else {
			boolean use = false;
			json.append("{");

			Class<?> clazz = data.getClass();
			Object value = null;

			do{
				for(Field field : clazz.getDeclaredFields()) {
					String name = field.getName();

					json.append(name);
					json.append(":");

					try {
						value = clazz.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(data);
						if(value == null) json.append("null");
						else composit(value, json);
					}
					catch(Exception e) {
						try {
							value = clazz.getMethod("is" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(data);
							if(value == null) json.append("null");
							else composit(value, json);
						}
						catch(Exception e1) {
							json.append("null");							
						}
					}

					json.append(",");

					if(!use) use = true;
				}

				clazz = clazz.getSuperclass();
			}
			while(clazz != null);

			if(use) json.deleteCharAt(json.length() - 1);
			json.append("}");
		}
	}

	private String str2HTMLCharacter(String str)
	{
		return str.replaceAll("'",	"\\\\'");
//				.replaceAll(",",	"&#44;")
//				.replaceAll(":",	"&#58;")
//				.replaceAll("{",	"&#123;")
//				.replaceAll("}",	"&#125;")
//				.replaceAll("[",	"&#91;")
//				.replaceAll("]",	"&#93;")
//				.replaceAll("\\\\",	"&#92");
	}
}
