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

  public static List<Node> list(final NodeList nl) throws Exception {

    return new AbstractList<Node>() {

      public Node get(int i) {
        return nl.item(i);
      }

      public int size() {
        return nl.getLength();
      }
      ;
    };
  }
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
    String fn = "/home/j/esperanto/nepala_vortaro/espeanto-enkonduko";

    //orw.convert("test/testtextKantipur.odt", "tmp/slet.odt", conversionHandler);

    //orw.convert("../nepalitext_odt/esperanto-vortaro/vortaro_restajxo_kantipur.odt", "tmp/slet.odt", f2u);
    //orw.convert("/tmp/espeanto-enkonduko1.odt", "tmp/slet.odt", f2u);

    //orw.convert("test/esperanto-enkonduko.odt", "tmp/slet.odt", f2u);
    //orw.convert(fn+".odt", fn+"-converted.odt", f2u);
    orw.convert(fn+".odt", "tmp/slet.odt", f2u);

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
    trans.setOutputProperty("indent", "yes");
    trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

    trans.transform(new DOMSource(odfDocument.getContentDom()), new StreamResult(new File("tmp/foer-content.xml")));
    trans.transform(new DOMSource(odfDocument.getStylesDom()), new StreamResult(new File("tmp/foer-styles.xml")));
    
    
    final HashMap<String, String> fontReplacement = new HashMap<String, String>();
    
    NodeAction<String> replaceText = new NodeAction<String>() {


        protected void apply(Node textNode, String replace, int depth) {
          if (textNode.getNodeType() != Node.TEXT_NODE) return;
          if (textNode.hasChildNodes()) return;
          
          String teksto=textNode.getTextContent();
          if (teksto.trim().length()==0) return;

          OdfStylableElement parentNodeWithSty=findOdfStylableParentNode(textNode);
          String font = findFont(parentNodeWithSty);    
/*
          if (font==null) {
            // Ingen 
            OdfStyles odfDocumentStyles = ((OdfFileDom) textNode.getOwnerDocument()).getOdfDocument().getOrCreateDocumentStyles();
            
            System.err.println("odfDocument.getDocumentStyles() = " + odfDocumentStyles.getDefaultStyles());
            
            while (parentNodeWithSty!=null && font==null) {
              System.err.println("parentNode.getStyleFamily() = " + parentNodeWithSty.getStyleFamily());
              OdfDefaultStyle sty = odfDocumentStyles.getDefaultStyle( parentNodeWithSty.getStyleFamily());
              if (sty != null) {
                System.err.println("sty = " + sty);
                font = sty.getProperty(OdfTextProperties.FontName);              
              }
              parentNodeWithSty=findOdfStylableParentNode(parentNodeWithSty.getParentNode());
            }
            
            
            //System.err.println("parentNode = " + parentNodeWithSty+" har ingen font!"+parentNodeWithSty.getParentNode()+"  så font="+font);
            System.err.println("  xxx default så font="+font);
            //font = findFont(parentNode.getParentNode());      
          }
  */        

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
    
    /*
    OdfStyles styless=odfDocument.getDocumentStyles();
    OdfFileDom stylesDom=odfDocument.getStylesDom();
    OdfFileDom contentDom=odfDocument.getContentDom();



    final HashMap<String, OdfStyleBase> stylenameToStyle=new HashMap<String, OdfStyleBase>();
    final HashSet<OdfStyleBase> styles=new HashSet<OdfStyleBase>();
    class X1 {

      private void add(Iterable<OdfStyle> stylesForFamily) {
        for (OdfStyle s : stylesForFamily) {
          styles.add(s);
          OdfStyleBase olds = stylenameToStyle.put(s.getName(), s);
          if (olds != null) 
            System.err.println("olds = " + olds+"   s="+s);
        }
      }
    }
    ;
    X1 x1=new X1();

    x1.add(styless.getStylesForFamily(OdfStyleFamily.Paragraph));
    x1.add(styless.getStylesForFamily(OdfStyleFamily.Text));
    x1.add(styless.getStylesForFamily(OdfStyleFamily.TableCell));
    x1.add(styless.getStylesForFamily(OdfStyleFamily.Graphic));
    x1.add(styless.getStylesForFamily(OdfStyleFamily.Presentation));
    x1.add(styless.getStylesForFamily(OdfStyleFamily.Chart));
    x1.add(styless.getStylesForFamily(OdfStyleFamily.List));
*/
    
    //System.err.println("odfDocument.getDocumentStyles() = " + odfDocument.getDocumentStyles());
    //x1.add(styless.getDefaultStyles());
    
    //System.err.println("stylenameToStyle = " + stylenameToStyle);
    //System.exit(0);


    // get the ODF content as DOM tree representation
    // NB! Regenarates automatic style names!
    //Document content = odfDocument.getContent();


/*
    if (progressMonitor!=null) {
      progressMonitor.setMaximum(nl.getLength());
    }
*/

    
    
  /*
    for (OdfStyleBase style : styles) {
      String oldFont = getFont(style);
      String newFont=conversionHandler.giveFontReplacement(oldFont);
      if (newFont!=null && !newFont.equals(oldFont)) {
        //if (DEBUG)
          System.err.println("A ="+oldFont+"->"+newFont+"  "+style);
        
        System.err.println("err = mangler at sætte til Arial");
        style.setProperty(OdfTextProperties.FontName, "Arial");
        //style.setProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.STYLE.toString(), OdfParagraphStyle.FontName.toString(), "Arial");
        //style.setProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.STYLE.toString(), OdfParagraphStyle.FontNameComplex.toString(), "Arial");
        //if (DEBUG) {
          System.err.println("B ="+oldFont+"->"+newFont+"  "+style);
        //}
      }
    }
*/


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
      trans.transform(new DOMSource(odfDocument.getContentDom()), new StreamResult(new File("tmp/efter-content.xml")));
      trans.transform(new DOMSource(odfDocument.getStylesDom()), new StreamResult(new File("tmp/efter-styles.xml")));
    }
  }

  public OdfStylableElement findOdfStylableParentNode(Node node) {
    if (node==null) return null;
    if (node instanceof OdfStylableElement) return (OdfStylableElement) node;
    return findOdfStylableParentNode(node.getParentNode());
  }


  
  private String findFont(Node textNode) {
    if (textNode==null) return null;
    OdfStylableElement parentNode=findOdfStylableParentNode(textNode);
    if (parentNode==null) return null;

    String font = parentNode.getProperty(OdfTextProperties.FontName);
    if (font != null) return font;

    font = findFont(parentNode.getParentNode());
    return font;
  }
  
  /*
  private String findFont(OdfStylableElement parentNode) {
    OdfStylableElement orgNode=parentNode;
    
    String font = parentNode.getProperty(OdfTextProperties.FontName);
    System.err.println("font = " + font);

    
    OdfStyleBase style=null;//stylenameToStyle.get(parentNode.getStyleName());
    
    System.err.println("style = " + style);

    //String font=getFont(style);

    do {
      while (font==null && style!=null) {

        // DOESENT WORK! 
        style = style.getParentStyle();
        //style=stylenameToStyle.get(style.getParentNode().getNodeName());
        font=getFont(style);
      }

      if (font==null) {
        // Search up to parent node with style and try again
        Node n3=parentNode.getParentNode();
        while (n3!=null&&!(n3 instanceof OdfStylableElement)) {
          n3=n3.getParentNode();
          if (DEBUG) {
            //System.err.println(" parent "+n3);
          }
        }
        parentNode=(OdfStylableElement) n3;
        if (DEBUG) {
          System.err.println(" parent "+parentNode);
        }
        if (parentNode==null) {
          if (DEBUG) {
            System.err.println("parentNode==null!  for "+orgNode);
          }
          break;
        }
        style=stylenameToStyle.get(parentNode.getStyleName());
        font=getFont(style);
      }
    } while (font==null);

    if (DEBUG) {
      System.err.println(font);
    }
    return style;
  }
*/
  
  
  
  /*
  private OdfStyleBase findStyleWithFont(HashMap<String, OdfStyleBase> stylenameToStyle, OdfStylableElement parentNode) {
    //String styleName = parentNode.getStyleName();
    OdfStylableElement orgNode=parentNode;
    
    System.err.println("parentNode.getStyleName() = " + parentNode.getStyleName());
    System.err.println("parentNode.getStyleFam() = " + parentNode.getStyleFamily());
    //System.err.println("parentNode.getAutoStyle() = " + parentNode.getAutomaticStyle());
    
    String font = parentNode.getProperty(OdfTextProperties.FontName);
    System.err.println("font = " + font);
    
    OdfStyleBase style=stylenameToStyle.get(parentNode.getStyleName());
    
    System.err.println("style = " + style);

    //String font=getFont(style);

    do {
      while (font==null && style!=null) {

        // DOESENT WORK! 
        style = style.getParentStyle();
        //style=stylenameToStyle.get(style.getParentNode().getNodeName());
        font=getFont(style);
      }

      if (font==null) {
        // Search up to parent node with style and try again
        Node n3=parentNode.getParentNode();
        while (n3!=null&&!(n3 instanceof OdfStylableElement)) {
          n3=n3.getParentNode();
          if (DEBUG) {
            //System.err.println(" parent "+n3);
          }
        }
        parentNode=(OdfStylableElement) n3;
        if (DEBUG) {
          System.err.println(" parent "+parentNode);
        }
        if (parentNode==null) {
          if (DEBUG) {
            System.err.println("parentNode==null!  for "+orgNode);
          }
          break;
        }
        style=stylenameToStyle.get(parentNode.getStyleName());
        font=getFont(style);
      }
    } while (font==null);

    if (DEBUG) {
      System.err.println(font);
    }
    return style;
  }
*/
  
  private static String getFont(OdfStyleBase sty) {
    if (sty==null) {
      return null;
    }
    String font=sty.getProperty(OdfTextProperties.FontName);
    return font;
  }

  
  ProgressMonitor progressMonitor=null;

  public void setProgressListener(ProgressMonitor b) {
    progressMonitor=b;
    if (progressMonitor!=null) {
      progressMonitor.setMinimum(0);
    }

  }
}

