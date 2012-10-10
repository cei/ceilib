package cei.web.spring.view.type;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import cei.web.spring.view.Views;

public class Alert extends AbstractView implements Views {
	private String message = null;
	private String execute = null;
	private boolean close = false;
	
	public Alert() {
	}
	
	public Alert(String message) {
		this.message = message;
	}
	
	public Alert(String message, boolean close) {
		this(message);
		this.close = close;
	}
	
	public Alert(String message, String execute, boolean close) {
		this(message, close);
		this.execute = execute;
	}
	
	public Alert(String message, String execute) {
		this(message);
		this.execute = execute;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void isClose(boolean close) {
		this.close = close;
	}
	
	private String createMessage() {
		StringBuffer html = new StringBuffer();
		
		html.append("<!DOCTYPE html>");
		html.append("<html lang=\"ko\"><head><meta charset=\"utf-8\"><title></title>");
		html.append("<script type=\"text/javascript\">");
		html.append("alert(\"");
		html.append(message.replaceAll("\"", "\\\\\""));
		html.append("\");");

		if(execute != null) html.append(execute);

		if(close) html.append("self.close();");
		

		html.append("</script></head><body></body></html>");
		
		return html.toString();
	}

	public String getData() {
		return message;
	}

	protected void renderMergedOutputModel(Map<String, Object> param,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(message == null) {
			if(param.get("message") != null)
				message = (String)param.get("message");
		}

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		Writer writer = response.getWriter();
		writer.write(createMessage());
		writer.flush();
		writer.close();
	}
}
