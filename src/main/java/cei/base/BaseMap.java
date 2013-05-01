package cei.base;

import java.util.HashMap;

public class BaseMap<T> extends HashMap<String, T> {
	private static final long serialVersionUID = 2550920975929262430L;

	public final void add(String name, T value) {
		put(name, value);
	}

	public final void remove(String name) {
		remove(name);
	}

	public final boolean has(String name) {
		return containsKey(name) && !"".equals(super.get(name));
	}

	public final T get(String name) {
		return get(name);
	}
	
	public final Integer getInteger(String name) {
		Object value = get(name);

		if(value instanceof Number) return ((Number)value).intValue();
		return 0;
	}

	public final String getString(String name) {
		Object value = get(name);

		if(value == null) return null;
		else if(value instanceof String) return (String)value;
		else if(value instanceof String[]) {
			StringBuffer sb = new StringBuffer();

			for(String s : (String[])value) {
				sb.append(s);
				sb.append(",");
			}

			value = sb.substring(0, sb.length() - 1);
			return value.toString();
		}

		return value.toString();
	}
}