package cei.web.taglibs.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cei.domains.SystemCode;
import cei.util.TreeHtmlBuilder;


public class TreeTag extends SimpleTagSupport {
	private String id = null;
	private String cssClass = "cei-tree";
	private String startCode = null;
	private List<SystemCode> list;
	
	public void setId(String id) {
		this.id = id;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = " " + cssClass;
	}

	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}

	public void setList(List<SystemCode> list) {
		this.list = list;
	}

	public void doTag() throws JspException, IOException {
		if(list == null || list.size() == 0) return;

		JspWriter out = getJspContext().getOut();
		
		Document document = TreeHtmlBuilder.getDocument(list);
		Element root = document.getDocumentElement(); 

		if(id != null)  root.setAttribute("id", id);
		root.setAttribute("class", cssClass);
		root.setAttribute("data-startCode", startCode);

		out.println(TreeHtmlBuilder.getString(document));
	}
}
