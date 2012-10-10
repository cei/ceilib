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
import org.w3c.dom.NodeList;

import cei.domains.SystemCode;

public abstract class TreeHtmlBuilder {
	public static String getString(List<SystemCode> list) {
		return getString(getDocument(list));
	}

	public static String getString(Document document) {
		Writer writer = new StringWriter();

		try {
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.METHOD, "html");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.transform(new DOMSource(document), new StreamResult(writer));
		}
		catch(TransformerException te) {
			te.printStackTrace();
		}
			
		return writer.toString();
	}
	
	public static Document getDocument(List<SystemCode> list) {
		if(list == null || list.size() == 0) return null;

		Document document;
		
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		}
		catch(ParserConfigurationException pce) {
			pce.printStackTrace();
			return null;
		}
		
		Element li = liTag(document, list.get(0));
		list.remove(0);

		Element ul = document.createElement("ul");
		ul.setAttribute("class", "cei-tree");
		ul.appendChild(li);
		document.appendChild(ul);
		
		String cssClass = null;

		int level = 0;
		for(SystemCode code : list) {
			if(level < code.getLevel()) {
				level = code.getLevel();

				Element _li = liTag(document, code);
				ul = document.createElement("ul");
				ul.appendChild(_li);

				cssClass = (li.getAttribute("class") + " parent").trim();
				
				((Element)li.getElementsByTagName("span").item(0)).setAttribute("class", "clip " + cssClass);
				li.setAttribute("class", cssClass);
				li.appendChild(ul);
				li = _li;
			}
			else if(level > code.getLevel()) {
				level -= code.getLevel();
				while(level-- > 0) li = (Element)li.getParentNode().getParentNode();

				level = code.getLevel();

				Element _li = liTag(document, code);
				li.getParentNode().appendChild(_li);
				li = _li;
			}
			else {
				Element _li = liTag(document, code);
				li.getParentNode().appendChild(_li);
				li = _li;
			}
		}

		NodeList nl = document.getElementsByTagName("ul");
		for(int i = 0; i < nl.getLength(); i++) {
			li = (Element)nl.item(i).getLastChild();
			
			cssClass = (li.getAttribute("class") + " last").trim();

			((Element)li.getElementsByTagName("span").item(0)).setAttribute("class", "clip " + cssClass);
			li.setAttribute("class", cssClass);
		}

		return document;
	}
	
	private static Element liTag(Document document, SystemCode code) {

		Element a = document.createElement("a");
		a.setTextContent(code.getValue());
		
		Element span = document.createElement("span");
		span.setAttribute("class", "clip");

		Element li = document.createElement("li");
		li.setAttribute("data-code", code.getCode());
		li.setAttribute("data-parent", code.getParent());
		li.setAttribute("data-description", code.getDescription());
		li.setAttribute("data-level", Integer.toString(code.getLevel()));
		li.setAttribute("data-order", Integer.toString(code.getOrder()));
		li.appendChild(span);
		li.appendChild(a);
		
		return li;
	}
}
