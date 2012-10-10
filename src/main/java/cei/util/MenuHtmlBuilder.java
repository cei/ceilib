package cei.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cei.domains.SystemCode;

public abstract class MenuHtmlBuilder {
	private static final String UL = "ul";
	private static final String LI = "li";
	private static final String A = "a";
	
	private MenuHtmlBuilder() {};

	public static final String html(String cssClass, List<SystemCode> list) {
		Document document = null;
		Element div = null;

		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			div = document.createElement("div");
			div.setAttribute("class", cssClass);

			int maxLevel = 0;
			for(SystemCode menuItem : list) if(maxLevel < menuItem.getLevel()) maxLevel = menuItem.getLevel(); 

			for(SystemCode item : list) {
				if(maxLevel == item.getLevel()) {
					Element a = document.createElement(A);
					a.setTextContent(item.getValue());
					if(item.getDescription() != null) a.setAttribute("href", item.getDescription());
					else a.setAttribute("class", "parent");

					Element li = document.createElement(LI);
					li.setAttribute("data-code", item.getCode());
					li.appendChild(a);

					Element ul = document.createElement(UL);
					ul.appendChild(li);

					composit(document, li, item, list);

					div.appendChild(ul);
				}
			}

			document.appendChild(div);

			Writer writer = new StringWriter();
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.METHOD, "html");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.transform(new DOMSource(div), new StreamResult(writer));
			
			return writer.toString();
		}
		catch(ParserConfigurationException pe) {
			pe.printStackTrace();
		}
		catch(TransformerException te) {
			te.printStackTrace();
		}
		catch(IllegalArgumentException iae) {
			iae.printStackTrace();
		}

		return "";
	}
	
	private static void composit(Document document, Element el, SystemCode code, List<SystemCode> menuItems) {
		boolean use = false;
		Element ul = document.createElement(UL);

		for(SystemCode item : menuItems) {
			if(code.getCode().equals(item.getParent())) {
				Element a = document.createElement("a");
				a.setTextContent(item.getValue());
				if(item.getDescription() != null) a.setAttribute("href", item.getDescription());
				else a.setAttribute("class", "parent");

				Element li = document.createElement(LI);
				li.setAttribute("data-code", item.getCode());
				li.setAttribute("data-parent", item.getParent());
				li.appendChild(a);

				composit(document, li, item, menuItems);
				ul.appendChild(li);
				use = true;
			}
		}

		if(use) el.appendChild(ul);
	}
}
