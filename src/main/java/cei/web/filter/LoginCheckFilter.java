package cei.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cei.domains.BaseUser;

public class LoginCheckFilter implements Filter {
	private static final String BYPASS_URI = "bypasses";
	private static final String INIT_PAGE = "initpage";

	private String initpage;
	private String[] bypasses; 

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession();

		String uri = request.getRequestURI();
		boolean bypassable = false;
		
		if(session.getAttribute(BaseUser.SESSION_NAME) != null) bypassable = true;
		else {
			if("/".equals(uri)) bypassable = true;
			else {
				for(String bypass : bypasses) {
					if(uri.startsWith(bypass)) {
						bypassable = true;
						break;
					}
				}
			}
		}

		if(bypassable) chain.doFilter(request, response);
		else response.sendRedirect(initpage);
	}

	public void init(FilterConfig config) throws ServletException {
		String bypass = config.getInitParameter(BYPASS_URI);

		initpage = config.getInitParameter(INIT_PAGE);

		bypasses = (bypass == null || "".equals(bypass)) ? new String[]{} : bypass.split("\n");
		for(int i = 0; i < bypasses.length; i++) {
			bypasses[i] = bypasses[i].trim();
		}
	}
	
}
