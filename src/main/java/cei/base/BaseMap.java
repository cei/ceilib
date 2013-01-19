package cei.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class BaseMap extends HashMap<String, Object> {
	private static final Logger log = LoggerFactory.getLogger("--- Request Parameters ---");

	public static final String PAGE = "__page__";
	public static final String ROW_PER_PAGE = "__rowPerPage__";
	
	public BaseMap() {
		super();
	}

	public BaseMap(HttpServletRequest request) {
		super();
		getItParameter(request);
	}

	private void getItParameter(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, Object> param = request.getParameterMap();

		Iterator<String> en = param.keySet().iterator();
		while(en.hasNext()) {
			String name = en.next();
			Object value = param.get(name);
			value = (value instanceof Object[]) ? ((Object[])value)[0] : value;

			if(log.isDebugEnabled())
				log.debug("{} = {}", name, value);

			add(name, value);
		}
	}

	public static List<BaseMap> asList(HttpServletRequest request) {
		List<BaseMap> list = new ArrayList<BaseMap>();

		@SuppressWarnings("unchecked")
		Map<String, Object> param = request.getParameterMap();

		Object[] arrayValue = null;
		BaseMap map = null;

		Iterator<String> en = param.keySet().iterator();
		while(en.hasNext()) {
			String name = en.next();
			Object value = param.get(name);
			
			if(value instanceof Object[]) {
				arrayValue = (Object[])value;
				
				for(int i = 0; i < arrayValue.length; i++) {
					if(list.size() > i) map = list.get(i);
					else {
						map = new BaseMap();
						list.add(map);
					}

					map.add(name, arrayValue[i]);
				}
			}
			else {
				if(list.size() > 0) map = list.get(0);
				else {
					map = new BaseMap();
					list.add(map);
				}

				map.add(name, value);
			}
		}

		for(BaseMap baseMap : list) log.debug("{}", baseMap);

		return list;
	}
	
	public static List<BaseMap> asList(HttpServletRequest request, String ... params) {
		List<BaseMap> list = new ArrayList<BaseMap>();
		Object paramObject = null;
		Object[] paramObjects = null;

		@SuppressWarnings("unchecked")
		Map<String, Object> param = request.getParameterMap();

		for(String paramName : params) {
			if(param.containsKey(paramName)) {
				paramObject = param.get(paramName);

				if(paramObject instanceof Object[]) {
					paramObjects = (Object[])paramObject;

					for(int i = 0; i < paramObjects.length; i++) {
						if(list.size() < i + 1) list.add(new BaseMap());
						list.get(i).add(paramName, paramObjects[i]);
					}
				}
				else {
					if(list.size() < 1) list.add(new BaseMap());
					list.get(0).add("paramName", paramObject);
				}
			}
		}

		return list;
	}

	public void add(String name, Object value) {
		super.put(name, value);
	}

	public void remove(String name) {
		super.remove(name);
	}

	public boolean has(String name) {
		return super.containsKey(name) && !"".equals(super.get(name));
	}

	public Object get(String name) {
		return super.get(name);
	}
	
	public Integer getInteger(String name) {
		Object value = get(name);

		if(value instanceof BigDecimal) return ((BigDecimal)value).intValue();
		else if(value instanceof Number) return (Integer)value;

		return 0;
	}

	public String getString(String name) {
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