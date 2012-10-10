package cei.web.spring.view.type;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cei.util.convert.ConvertToJson;
import cei.web.spring.view.Views;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Json implements Views {
	private static final Logger log = LoggerFactory.getLogger("--- Json ---");
	
	private Object data;
	
	public Json() {
		data = new HashMap();
	}
	
	public Json(Object data) {
		this.data = data;
	}
	
	public Json set(Object data) {
		this.data = data;
		
		return this;
	}
	
	public Json add(String key, Object value) {
		if(data == null) data = new HashMap();
		((Map)data).put(key, value);

		return this;
	}
	
	public Json remove(String key) {
		if(data instanceof Map) {
			((Map)data).remove(key);
		}
		
		return this;
	}
	
	public String getContentType() {
		return "text/plain";
	}
	
	public Object getData() {
		return data;
	}
	
	public void render(Map param,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(data == null) {
			if(param.get("data") != null)
				data = param.get("data");
		}
		
		response.setContentType(getContentType());
		response.setCharacterEncoding("UTF-8");
		
		Writer writer = response.getWriter();
		writer.write(ConvertToJson.getInstance().getJson(data));
		writer.flush();
		writer.close();
		
		if(log.isDebugEnabled()) {
			log.debug("--- Output ------------------------");
			log.debug("data : {}", data);
			log.debug("type : {}", getContentType());
		}
	}
}
