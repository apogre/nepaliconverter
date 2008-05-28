package np.org.mpp.tools.conversion.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParser extends DefaultHandler {
	File xmlFile;

	HashMap<String, String> hashMap = new HashMap<String, String>();

	private String key;
	private String value;

	public SAXParser(String xmlFilePath) {
		if (new File(xmlFilePath).exists()) {
			xmlFile = new File(xmlFilePath);
			initParser();
		} else {
			System.err.println("File not found!");
			return;
		}
	}

	private void initParser() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(xmlFile, this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startDocument() {
	}

	public void endDocument() {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		value = attributes.getValue(0);
	}


	public void characters(char[] ch, int start, int length) {
		key = new String(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName) {
			hashMap.put(key, value);
	}

	public HashMap<String, String> getHashMap(){
		return hashMap;
	}
}