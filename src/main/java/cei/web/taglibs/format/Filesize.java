package cei.web.taglibs.format;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cei.util.Formatter;

@SuppressWarnings("serial")
public class Filesize extends BodyTagSupport {
	private String value;

	public int doEndTag() throws JspException {
		if(value == null || "".equals(value)) value = bodyContent.getString();

		try {
			pageContext.getOut().write(Formatter.filesize(value));
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}

		value = null;
		return SKIP_BODY;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
