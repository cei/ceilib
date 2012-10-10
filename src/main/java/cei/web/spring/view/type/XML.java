package cei.web.spring.view.type;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cei.util.convert.ConvertToXML;
import cei.web.spring.view.Views;


public class XML implements Views {
	private static final Logger log = LoggerFactory.getLogger("--- XML ---");

	private Object data = null;

	public XML() {}

	public XML(Object data) {
		this.data = data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getContentType() {
		return "application/xml";
	}

	public Object getData() {
		return data;
	}

	public void render(Map<String, ?> param,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if(data == null) {
			if(param.get("data") != null)
				data = param.get("data");
		}

		response.setContentType(getContentType());
		response.setCharacterEncoding("UTF-8");
		
		Writer writer = response.getWriter();
		writer.write(ConvertToXML.getInstance().getXMLString(data));
		writer.flush();
		writer.close();

		if(log.isDebugEnabled()) {
			log.debug("--- Output ------------------------");
			log.debug("data : {}", data);
			log.debug("type : {}", getContentType());
		}
	}

}
