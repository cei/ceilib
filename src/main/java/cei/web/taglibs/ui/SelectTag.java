package cei.web.taglibs.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cei.domains.SystemCode;

@SuppressWarnings("serial")
public class SelectTag extends BodyTagSupport {
	private String id;
	private String name;
	private String selected;
	private String style;
	private String cssClass;
	private String onchange;
	private String attributes;
	private String disabled;
	private String text = "TEXT";
	private String value = "VALUE";
	private boolean after = false;
	private List<SystemCode> items;
	
	public int doEndTag() throws JspException {
		StringBuffer tagBuilder = new StringBuffer("<select");
		
		if(id != null) tagBuilder.append(" id=\"" + id + "\"");
		if(name != null) tagBuilder.append(" name=\"" + name + "\"");
		if(style != null) tagBuilder.append(" style=\"" + style + "\"");
		if(cssClass != null) tagBuilder.append(" class=\"" + cssClass + "\"");
		if("disabled".equals(disabled)) tagBuilder.append(" disabled=\"disabled\"");
		if(onchange != null) tagBuilder.append(" " + onchange + " ");
		if(attributes != null) tagBuilder.append(" " + attributes + " ");

		tagBuilder.append(">");
			
		if(!after && bodyContent != null)
			tagBuilder.append(bodyContent.getString());

		if(items != null) {
			for(int i = 0; i < items.size(); i++) {
				Object listItem = items.get(i);
				String text = null;
				String val = null;

				if(listItem instanceof SystemCode) {
					SystemCode code = (SystemCode)listItem;
					text = code.getValue();
					val = code.getCode();
				}
				else if(listItem instanceof Map) {
					Map<?, ?> code = (Map<?,?>)listItem;
					text = (String)code.get(this.text);
					val = (String)code.get(this.value);
				}
				else if(listItem instanceof String) {
					text = (String)listItem;
					val = (String)listItem;
				}
				else continue;

				if(text == null && val == null) continue;
				
				tagBuilder.append("<option");
				tagBuilder.append(" value=\"");
				tagBuilder.append(val);
				tagBuilder.append("\"");
				
				if(selected != null && (selected.equals(val) || selected.equals(text))) {
					tagBuilder.append(" selected=\"selected\"");
				}
				
				tagBuilder.append(">");
				tagBuilder.append(text);
				tagBuilder.append("</option>");
			}
		}

		if(after && bodyContent != null)
			tagBuilder.append(bodyContent.getString());
			
		tagBuilder.append("</select>");
		if(id != null && selected != null)
			tagBuilder.append("<script type='text/javascript'>var _e = document.getElementById('" + id + "'); for(var i =0; i < _e.options.length; i++) {if(_e.options[i].text == '" + selected + "' || _e.options[i].value == '" + selected + "') { _e.options[i].selected = true; _e.selectedIndex = i;break;}};</script>");

		try {
			pageContext.getOut().write(tagBuilder.toString());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setAfter(boolean bodyToTail) {
		this.after = bodyToTail;
	}
	
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public void setItems(List<SystemCode> items) {
		this.items = items;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
