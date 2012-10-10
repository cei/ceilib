package cei.web.spring.view.type;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import cei.web.spring.view.Views;

public class Page extends AbstractView implements Views {
	private String view = null;
	
	
	public Page(String view) {
		this.view = view;
	}

	public Object getData() {
		return view;
	}

	protected void renderMergedOutputModel(Map<String, Object> param,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html");
		response.sendRedirect(view);
	}
}
