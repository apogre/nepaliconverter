package np.org.mpp.conv4.utils.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.FileNotFoundException;

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
			throw new IllegalStateException("File not found: "+xmlFilePath);
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
			String oldVal = hashMap.put(key, value);
      if (oldVal != null && oldVal.length()>0 && !oldVal.equals(value)) {
        System.err.println("Warning: "+xmlFile+": old value "+oldVal+" replaces with "+value+" for key "+key);
      }
	}

	public HashMap<String, String> getHashMap(){
		return hashMap;
	}
}
