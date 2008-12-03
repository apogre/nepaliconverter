package np.org.mpp.conv4.utils.odfdom;

import java.util.*;

import javax.swing.ProgressMonitor;
import javax.xml.xpath.*;

import np.org.mpp.conv4.f2u.F2UConversionHandler;
import np.org.mpp.conv4.utils.ConversionHandler;
import np.org.mpp.conv4.utils.GeneralReaderWriter;
import org.openoffice.odf.doc.OdfDocument;
import org.openoffice.odf.doc.element.style.OdfDefaultStyle;
import org.openoffice.odf.doc.element.text.OdfSpan;
import org.openoffice.odf.dom.OdfNamespace;
import org.openoffice.odf.dom.element.OdfStylableElement;
import org.openoffice.odf.dom.style.props.OdfStylePropertiesSet;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openoffice.odf.doc.OdfFileDom;
import org.openoffice.odf.doc.element.office.OdfStyles;
import org.openoffice.odf.doc.element.style.OdfStyle;
import org.openoffice.odf.doc.element.style.OdfTextProperties;
import org.openoffice.odf.dom.element.OdfElement;
import org.openoffice.odf.dom.element.OdfStyleBase;
import org.openoffice.odf.dom.style.OdfStyleFamily;
import org.openoffice.odf.dom.util.NodeAction;

public class OpenOfficeReaderWriter implements GeneralReaderWriter {

//  http://develop.opendocumentfellowship.com/spec/
  static boolean DEBUG=true;


  static ConversionHandler loggingConversionHandler=new ConversionHandler() {

    public String convertText(String font, String text) {
      System.err.println("convertText("+font+", '"+text+"')");
      return text;
    }

    public String giveFontReplacement(String font) {
      return font;
    }
  };

  public static void main(String[] args) throws Exception {
    //DEBUG = true;
    OpenOfficeReaderWriter orw=new OpenOfficeReaderWriter();
    //"/home/j/esperanto/nepala vortaro/provo.odt"; "/home/j/Dokumenter/oop/oopj354.odt";

    F2UConversionHandler f2u=new F2UConversionHandler();
    //f2u.setStandardFontReplacement("Arial");
    
    //String fn = "test/aLotOfStyles";
    //String fn = "/home/j/esperanto/nepala_vortaro/espeanto-enkonduko";

    //orw.convert("test/testtextKantipur.odt", "tmp/slet.odt", conversionHandler);

    //orw.convert("../nepalitext_odt/esperanto-vortaro/vortaro_restajxo_kantipur.odt", "tmp/slet.odt", f2u);
    orw.convert("/tmp/espeanto-enkonduko.odt", "tmp/slet.odt", f2u);

    //orw.convert("test/esperanto-enkonduko.odt", "tmp/slet.odt", f2u);
    //orw.convert(fn+".odt", fn+"-converted.odt", f2u);
    //orw.convert(fn+".odt", "tmp/slet.odt", f2u);

  }

  public static String chopNumber(String font) {
    char endChar=font.charAt(font.length()-1);
    if (endChar>='1'&&endChar<='9') {
      font=font.substring(0, font.length()-1);
    }
    return font;
  }

  public void convert(String inFile, String outFile, final ConversionHandler conversionHandler) throws Exception {

    if (!new File(inFile).exists()) {
      throw new FileNotFoundException(inFile);
    }

    // loads the ODF document from the path
    OdfDocument odfDocument=OdfDocument.loadDocument(inFile);

    
    Transformer trans = TransformerFactory.newInstance().newTransformer();
    if (DEBUG) {
      trans.setOutputProperty("indent", "yes");
      trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      trans.transform(new DOMSource(odfDocument.getContentDom()), new StreamResult(new File("tmp/foer-content.xml")));
      trans.transform(new DOMSource(odfDocument.getStylesDom()), new StreamResult(new File("tmp/foer-styles.xml")));
      
    }
    
    
    
    final HashMap<String, String> fontReplacement = new HashMap<String, String>();
    
    NodeAction<String> replaceText = new NodeAction<String>() {


        protected void apply(Node textNode, String replace, int depth) {
          if (textNode.getNodeType() != Node.TEXT_NODE) return;
          if (textNode.hasChildNodes()) return;
          
          String teksto=textNode.getTextContent();
          if (teksto.trim().length()==0) return;

          String font = StyleUtils.findStylePropertyForNode(textNode, OdfTextProperties.FontName);    

          if (font==null || font.length()==0) {
            System.err.println("!!!!!! font==null for "+textNode);
          } else {
          font=chopNumber(font);
            
            String teksto2=conversionHandler.convertText(font, teksto);
            if (!teksto.trim().equals(teksto2.trim())) {
              //if (DEBUG) 
              System.err.println("CONV "+font+":"+teksto+" -> "+teksto2);
              //textNode.setTextContent("Kkk"+font+":"+teksto2+"kkK");
              textNode.setTextContent(teksto2);
              String newFont = conversionHandler.giveFontReplacement(font);
              fontReplacement.put(font, newFont);
            } else
              System.err.println("     "+font+":"+teksto);
          }
        }

    };
    OdfElement e = (OdfElement) odfDocument.getContentDom().getDocumentElement();
    replaceText.performAction(e, "hejsa");
    


    if (outFile!=null&& !fontReplacement.isEmpty()) {

      NodeAction<String> replaceFontnames = new NodeAction<String>() {

        public void replaceFont(Node fnn) throws DOMException {
          if (fnn==null) return;
          String orgFn = fnn.getNodeValue();
          String newFn = orgFn;
          String[] fna = orgFn.split(",");
          for (String fn:fna) {
            fn=chopNumber(fn).replaceAll("'", "").trim();
            String nFn=fontReplacement.get(fn);
            if (nFn!=null) {
              newFn = newFn.replaceAll(fn, nFn);
            }
          }

          if (!newFn.equals(orgFn)) 
          {
            //System.err.println("replaceFont "+fnn+" -> "+newFn);
            fnn.setNodeValue(newFn);            
            //System.err.println("replaceFont "+fnn);
          }        
        }

        protected void apply(Node node, String replace, int depth) {
          if (node.hasChildNodes()) return;
          NamedNodeMap attr = node.getAttributes();
          if (attr==null) return;

          String nn = node.getNodeName();

          replaceFont(attr.getNamedItem("style:font-name"));
          replaceFont(attr.getNamedItem("style:font-name-complex"));
          replaceFont(attr.getNamedItem("style:font-name-asian"));

          if (node.getNodeName().equals("style:font-face")) {
            replaceFont(attr.getNamedItem("style:name"));
            replaceFont(attr.getNamedItem("svg:font-family"));
          }
        }
      };
      replaceFontnames.performAction(odfDocument.getStylesDom(), "hej2");
      replaceFontnames.performAction(odfDocument.getContentDom(), "hej2");
                
      odfDocument.save(outFile);
      
      if (DEBUG) {
        trans.transform(new DOMSource(odfDocument.getContentDom()), new StreamResult(new File("tmp/efter-content.xml")));
        trans.transform(new DOMSource(odfDocument.getStylesDom()), new StreamResult(new File("tmp/efter-styles.xml")));
      }
    }
  }



  
  
  
  ProgressMonitor progressMonitor=null;

  public void setProgressListener(ProgressMonitor b) {
    progressMonitor=b;
    if (progressMonitor!=null) {
      progressMonitor.setMinimum(0);
    }

  }
}

