/*
 * @(#)XMLParser.java	2008/02/21
 * Copyright © 2007 Sambad Project www.sambad.org, Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.tools.conversion.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The {@link DOMParser} class is used to parse the font.xml file. It adds all
 * the font and the respective unicode character in a HashMap to be accessed by
 * other classes.
 * 
 * @author Abhishek Shrestha
 */
public class DOMParser {

    /**
     * A {@link HashMap} for pairing the unicode and font the characters.
     * <p>
     * Here the unicode character is made the Key for a particular font Value to
     * form the <K,V> ie key-value pair in the hashmap.
     */
    static HashMap<String, String> hashMap = new HashMap<String, String>();

    public DOMParser(String xmlFilePath) {
	try {
	    // Create a DocumentBuilderFactory
	    DocumentBuilderFactory factory = DocumentBuilderFactory
		    .newInstance();
	    File xmlFile = new File(xmlFilePath);
	    // Create a DocumentBuilder
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    // Parse an XML document
	    Document document = builder.parse(xmlFile);
	    // Retrieve root element
	    Element rootElement = document.getDocumentElement();
	    // start parsing
	    visitNode(rootElement);
	} catch (SAXException e) {
	    System.err.println(e.getMessage());
	} catch (ParserConfigurationException e) {
	    System.err.println(e.getMessage());
	} catch (IOException e) {
	    System.err.println(e.getMessage());
	}
    }

    /**
     * Returns the {@link HashMap} created by the Key Value pair of unicode and
     * font
     * 
     * @return the {@link HashMap} created after parsing.
     */
    public HashMap<String, String> getHashMap() {
	return hashMap;
    }

    /**
     * a recursive method which examines all of the nodes exhasutively.
     * 
     * @param visitNode
     *                the node to be visited to find further child nodes
     */
    public void visitNode(Element visitNode) {
	String trans = null;
	String unicode = null;
	/*
	 * either get attribute specified as font name or get the unicode
	 * character
	 */
	if (visitNode.hasAttributes()) {
	    NamedNodeMap attributes = visitNode.getAttributes();
	    for (int j = 0; j < attributes.getLength(); j++) {
		Attr charUnicode = (Attr) (attributes.item(j));
		unicode = charUnicode.getValue().trim();
	    }
	}
	// get the children of the node and get the the font name
	NodeList nodeList = visitNode.getChildNodes();
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    // Retrieve Element nodes
	    if (node.getNodeType() == Node.ELEMENT_NODE) {
		Element element = (Element) node;
		// recursive call to visitNode method to process
		visitNode(element);
	    } else if (node.getNodeType() == Node.TEXT_NODE) {
		trans = node.getNodeValue().trim();

		if (trans.length() > 0) {
		    hashMap.put(trans, unicode);
		}
	    }
	}
    }
}