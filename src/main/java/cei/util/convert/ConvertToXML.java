package cei.util.convert;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.sql.Clob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import cei.util.Formatter;

public class ConvertToXML {
	private static final Logger log = LoggerFactory.getLogger("--- Converting Xml ---");
	private static final String ROOT_TAG_NAME = "cei";

	private static volatile ConvertToXML instance = null;
	
	private ConvertToXML() {}
	
	public static ConvertToXML getInstance() {
		if(instance == null) {
			synchronized (ConvertToXML.class) {
				if(instance == null)
					instance = new ConvertToXML();
			}
		}

		return instance;
	}

	public Document getXMLDocument(Object data) {
		return getXMLDocument(data, ROOT_TAG_NAME);
	}

	public Document getXMLDocument(Object data, String rootElementName) {
		if(data == null) return null;

		long startTime = System.currentTimeMillis();
		Document document = convert(data, rootElementName);

		if(log.isDebugEnabled())
			log.debug("process time {} ms.", System.currentTimeMillis() - startTime);

		return document;
	}

	public String getXMLString(Object data) {
		return getXMLString(data, ROOT_TAG_NAME);
	}

	public String getXMLString(Object data, String rootElementName) {
		if(data == null) return null;

		long startTime = System.currentTimeMillis();
		StringWriter sw = new StringWriter();

		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(getXMLDocument(data, rootElementName)), new StreamResult(sw));
		}
		catch(TransformerException e) {
			e.printStackTrace();
		}

		if(log.isDebugEnabled())
			log.debug("process time {} ms.", System.currentTimeMillis() - startTime);

		return sw.toString();
	}
	
	private Document convert(Object data, String rootNodeName) {
		Document xml = null;
		DocumentBuilder builder = null;
		Node root = null;

		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		catch(ParserConfigurationException e) {
			e.printStackTrace();
		}

		xml = builder.newDocument();
		root = xml.createElement(rootNodeName);

		xml.appendChild(root);
		treeComposit(root, data);
		
		return xml;
	}
	
	@SuppressWarnings("rawtypes")
	private void treeComposit(Node parent, Object obj) {	
		if(obj == null) return;

		Document xml = parent.getOwnerDocument();
		
		if(obj instanceof String) {
			addChildNode(parent, null, (String)obj, true);
		}
		else if(obj instanceof Number) {
			addChildNode(parent, null, obj.toString());
		}
		else if(obj instanceof Date) {
			addChildNode(parent, null, Long.toString(((Date)obj).getTime()));
		}
		else if(obj instanceof Character) {
			addChildNode(parent, null, Character.toString((Character)obj));
		}
		else if(obj instanceof Boolean) {
			addChildNode(parent, null, Boolean.toString((Boolean)obj));
		}
		else if(obj instanceof Object[]) {
			for(Object v : (Object[])obj) {
				Element node = xml.createElement("list");
				node.setAttribute("javaType", obj.getClass().getCanonicalName());
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof int[]) {
			for(int v : (int[])obj) {
				Element node = xml.createElement("int");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof float[]) {
			for(float v : (float[])obj) {
				Element node = xml.createElement("float");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof double[]) {
			for(double v : (double[])obj) {
				Element node = xml.createElement("double");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof short[]) {
			for(short v : (short[])obj) {
				Element node = xml.createElement("short");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof long[]) {
			for(long v : (long[])obj) {
				Element node = xml.createElement("long");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof char[]) {
			for(char v : (char[])obj) {
				Element node = xml.createElement("char");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof byte[]) {
			for(byte v : (byte[])obj) {
				Element node = xml.createElement("byte");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof boolean[]) {
			for(boolean v : (boolean[])obj) {
				Element node = xml.createElement("boolean");
				treeComposit(node, v);
				parent.appendChild(node);
			}
		}
		else if(obj instanceof List) {
			for(int i = 0; i < ((List)obj).size(); i++) {
				Element listNode = xml.createElement("list");
				treeComposit(listNode, ((List)obj).get(i));
				parent.appendChild(listNode);
			}
		}
		else if(obj instanceof Map) {
			Iterator it = ((Map)obj).keySet().iterator();
			while(it.hasNext()) {
				Object name = it.next();
				Node node = xml.createElement(name.toString());
				treeComposit(node, ((Map)obj).get(name));
				parent.appendChild(node);
			}
		}
		else if(obj instanceof Date) {
			addChildNode(parent, null, Long.toString(((Date)obj).getTime()));
		}
		else if(obj instanceof Clob) {
			addChildNode(parent, null, Formatter.CLOB2String((Clob)obj), true);
		}
		else {
			Class<?> clazz = obj.getClass();
			Object value = null;

			do{
				for(Field field : clazz.getDeclaredFields()) {
					String name = field.getName();
					Element node = xml.createElement(name);

					try {
						value = clazz.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(obj);
					}
					catch(Exception e) {
						try {
							value = clazz.getMethod("is" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(obj);
						}
						catch(Exception e1) {
							value = null;
						}
					}

					treeComposit(node, value);
					parent.appendChild(node);
				}

				clazz = clazz.getSuperclass();
			}
			while(clazz != null);
		}
	}
	
	private void addChildNode(Node node, String name, String value, boolean CDATAType) {
		Document xml = node.getOwnerDocument();
		Text text = null;
		
		if(value != null)
			text = (!CDATAType) ? xml.createTextNode(value) : xml.createCDATASection(value);
		
		if(name != null && value != null) {
			Element element = xml.createElement(name);
			element.appendChild(text);
			node.appendChild(element);
		}
		else if(name != null && (value == null || "".equals(value))) {
			Element element = xml.createElement(name);
			element.appendChild(text);
		}
		else if(name == null && (value != null && !"".equals(value))) {
			node.appendChild(text);
		}
	}
	
	private void addChildNode(Node node, String name, String value) {
		addChildNode(node, name, value, false);
	}
}
