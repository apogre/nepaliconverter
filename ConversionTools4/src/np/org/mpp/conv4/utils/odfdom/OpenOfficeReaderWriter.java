package np.org.mpp.conv4.utils.odfdom;

import java.util.*;

import javax.swing.ProgressMonitor;
import javax.xml.xpath.*;

import np.org.mpp.conv4.f2u.F2UConversionHandler;
import np.org.mpp.conv4.utils.ConversionHandler;
import np.org.mpp.conv4.utils.GeneralReaderWriter;
import org.openoffice.odf.doc.OdfDocument;
import org.openoffice.odf.doc.element.text.OdfSpan;
import org.openoffice.odf.dom.OdfNamespace;
import org.openoffice.odf.dom.element.OdfStylableElement;
import org.openoffice.odf.dom.style.OdfParagraphStyle;
import org.openoffice.odf.dom.style.OdfStyle;
import org.openoffice.odf.dom.style.props.OdfStylePropertiesSet;
import org.w3c.dom.*;

public class OpenOfficeReaderWriter implements GeneralReaderWriter {

//  http://develop.opendocumentfellowship.com/spec/

  static boolean DEBUG = false;

  public static List<Node> list(final NodeList nl) throws Exception {

    return new AbstractList<Node>() {
      public Node get(int i) { return nl.item(i); }

      public int size() { return nl.getLength(); };

    };
  }


  static ConversionHandler conversionHandler = new ConversionHandler() {
    public String convertText(String font, String text) {
      System.out.println("convertText("+font+", '"+text+"')");
      return text;
    }

    public String giveFontReplacement(String font) {
      return font;
    }
  };


  public static void main(String[] args) throws Exception {
    OpenOfficeReaderWriter orw = new OpenOfficeReaderWriter();
    //"/home/j/esperanto/nepala vortaro/provo.odt"; "/home/j/Dokumenter/oop/oopj354.odt";


    //String fn = "test/aLotOfStyles";
    //orw.convert(fn+".odt", fn+"-converted.odt", new F2UConversionHandler());

    orw.convert("test/testtextKantipur.odt", "tmp/slet.odt", conversionHandler);

  }

  public void convert(String inFile, String outFile, ConversionHandler conversionHandler) throws Exception {


    // loads the ODF document from the path
    OdfDocument odfDocument = OdfDocument.load(inFile);

    if (DEBUG) System.out.println(""
                       +odfDocument.getDocumentStyles()+"\n\n"
                       +odfDocument.getDefaultStyles()+"\n\n"
                       +odfDocument.getAutomaticStylesInternal()+"\n\n"
                       +odfDocument.getAutomaticStyles()+"\n\n"
      );


    // get the ODF content as DOM tree representation
    Document content = odfDocument.getContent();

    System.out.println(""+content);


    NodeList nl = content.getElementsByTagName("*");

    if (progressMonitor!=null) {
      progressMonitor.setMaximum(nl.getLength());
    }

    for (int nodeNr=0; nodeNr<nl.getLength(); nodeNr++) {

      if (progressMonitor!=null) {
        progressMonitor.setProgress(nodeNr);
      }
      Node n = nl.item(nodeNr);
      if (DEBUG) System.out.println("\n==========" + n + "=============");
      if (!n.getNodeName().startsWith("text:")) continue;
      if (!(n instanceof OdfStylableElement)) continue;
      OdfStylableElement parentNode = (OdfStylableElement) n;


      for (Node node : list(parentNode.getChildNodes())) {

        if (node.getNodeType() == Node.TEXT_NODE) {
          if (DEBUG) System.out.println();
          if (DEBUG) System.out.println(node);
          Node textNode = node;

          OdfStyle style = findStyleWithFont(odfDocument, parentNode);
          String font = getFont(style);


          String teksto = textNode.getTextContent();
          String teksto2 = conversionHandler.convertText(font, teksto);
          if (!teksto.trim().equals(teksto2.trim())) {
            if (DEBUG) System.out.println("CONV "+teksto+" -> "+teksto2);
            if (DEBUG) System.out.println("CONV "+style);
            textNode.setTextContent(teksto2);
          }



        }

      }
    }


    HashSet<OdfStyle> styles = new HashSet<OdfStyle>();

    styles.addAll(odfDocument.getAutomaticStyles().values());
    styles.addAll(odfDocument.getAutomaticStylesInternal().values());
    styles.addAll(odfDocument.getDefaultStyles().values());
    styles.addAll(odfDocument.getDocumentStyles().values());

    for (OdfStyle style : styles) {
      String font = conversionHandler.giveFontReplacement(getFont(style));
      if (font != null) {
        if (DEBUG) System.out.println("A =" + font + "  " + style);

        style.setProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.STYLE.toString(), OdfParagraphStyle.FontName.toString(), "Arial");
        style.setProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.STYLE.toString(), OdfParagraphStyle.FontNameComplex.toString(), "Arial");
        if (DEBUG) System.out.println("B =" + font + "  " + style);
      }
    }



    if (outFile != null) odfDocument.save(outFile);
  }

  private OdfStyle findStyleWithFont(OdfDocument odfDocument, OdfStylableElement parentNode) {
      //String styleName = parentNode.getStyleName();
      OdfStyle style = getStyle(odfDocument, parentNode.getStyleName());

      String font = getFont(style);

      do {
        while (font == null && style != null) {

          // DOESENT WORK! style = style.getParentStyle();
          style = getStyle(odfDocument, style.getParentName());
          font = getFont(style);
        }

        if (font == null) {
          // Search up to parent node with style and try again
          Node n3 = parentNode.getParentNode();
          while (n3 != null && !(n3 instanceof OdfStylableElement)) {
            n3 = n3.getParentNode();
            if (DEBUG) System.out.println(" parent "+ n3);
          }
          parentNode = (OdfStylableElement) n3;
          if (DEBUG) System.out.println(" parent "+ parentNode);
          style = getStyle(odfDocument, parentNode.getStyleName());
          font = getFont(style);
        }
      } while (font == null && parentNode!=null);

      if (DEBUG) System.out.println(font);
      return style;
  }



    /*

    TextDocument td = (TextDocument) OpenDocumentFactory.load();
    System.out.println("top level body structure:");
    Body body = td.getBody();
    body.getParent();
// iterate over the body contents
    for (INode element : body) {
        // handle sections
        if (element instanceof Section) {
            Section section = (Section) element;
            for (INode se : section) {
                // handle paragraphs
                if (se instanceof Paragraph) {
              Paragraph paragraph = (Paragraph)se;
                    for (INode pe : paragraph) {
                        // handle portions
                        if (pe instanceof Portion) {
                            Portion portion = (Portion)pe;
                            System.out.println("    portion: {" + portion.toString() +"}");
                        }
                    }
                }
            }
        }
    }
    */

  private static String getFont(OdfStyle sty) {
    if (sty == null) return null;
    String font = sty.getProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.STYLE.toString(), OdfParagraphStyle.FontName.toString());
    if (DEBUG) System.out.println("  fon "+sty.getName()+"="+font);
    return font;
  }

//  static styleColl

  private static OdfStyle getStyle(OdfDocument odfDocument, String styleName) {
    OdfStyle sty = odfDocument.getDocumentStyles().getStyle(styleName);
    if (sty == null) {
      sty = odfDocument.getAutomaticStylesInternal().getStyle(styleName);
    }
    if (sty == null) {
      sty = odfDocument.getAutomaticStyles().getStyle(styleName);
    }
    if (sty == null) {
      sty = odfDocument.getDefaultStyles().getStyle(styleName);
    }
    if (DEBUG) System.out.println("  sty "+styleName+"="+sty);
    return sty;
  }





public static void mainXpath(String[] args) throws Exception {
  // loads the ODF document from the path
  //OdfDocument odfDocument = OdfDocument.load("/home/j/esperanto/nepala vortaro/provo.odt");
  OdfDocument odfDocument = OdfDocument.load("aLotOfStyles-lil.odt");
  //OdfDocument odfDocument = OdfDocument.load("/home/j/Dokumenter/oop/oopj354.odt");

  // get the ODF content as DOM tree representation
  Document content = odfDocument.getContent();

  // XPath initialization ''(JDK5 functionality)''
  XPath xpath = XPathFactory.newInstance().newXPath();
  xpath.setNamespaceContext(new OdfNamespace());

  // http://java.sun.com/j2se/1.5.0/docs/api/javax/xml/xpath/package-summary.html
  NodeList nodes = (NodeList) xpath.evaluate("//text:span", content,  XPathConstants.NODESET);
  for (int i = 0, n = nodes.getLength(); i < n; i++) {
    OdfSpan para = (OdfSpan) nodes.item(i);

    if (para.getParentNode()==null) throw new IllegalStateException("No parent node!");

    String styleName = para.getStyleName();

    // Find gammelt testdokument frem igen og se hvordan det opfører sig
    /*
    System.out.println(styleName+": "
                       +odfDocument.getDocumentStyles().getStyle(styleName)+" "
                       +odfDocument.getDefaultStyles().getStyle(styleName)+" "
                       +odfDocument.getAutomaticStylesInternal().getStyle(styleName)+" "
        );
    */

    OdfStyle sty = getStyle(odfDocument, styleName);


    if (sty == null) {
      System.out.println( "\n\nFEJL, INGEN STY!\n" + styleName+ " " +sty + " "
                          + "\n" + para.toString()
                          + "\n" + para
                          + "\nparent=" + para.getParentNode()
          );
      continue;

    }

    String font = getFont(sty);


    //OdfStyleFamily fam = para.getStyleFamily();
    System.out.println( "\n\n" + styleName+ " " +sty + " "+ sty.getParentStyle()
                        + "\n" + para.toString()
                        + "\n" + para
                        + "\nfont=" + font
                        + "\nparent=" + sty.getParentStyle()
//                          + "\n" + para.getDocumentStyle()
        );

    while (font == null && sty != null) {
      sty = sty.getParentStyle();
      font = getFont(sty);
    }

    OdfStylableElement parent = para;
    while (font==null && parent.getParentNode() instanceof OdfStylableElement) {
      parent = (OdfStylableElement) parent.getParentNode();
      if (parent==null) {
        System.out.println( "AARGH INGEN PARENT!");
        break;
      }
      styleName = parent.getStyleName();
      sty = getStyle(odfDocument, styleName);
      font = sty.getProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.STYLE.toString(), OdfParagraphStyle.FontName.toString());
      System.out.println( "FONT ER NULL. PARENT: " + styleName+ " " +sty + " "+ sty.getParentStyle()
                          + "\nfont=" + font
                          + "\n" + para.getParentNode().toString()
//                          + "\n" + para.getDocumentStyle()
          );


    }


    para.setTextContent("hej P versen!");
  }


}


 ProgressMonitor progressMonitor = null;
 public void setProgressListener(ProgressMonitor b) {
   progressMonitor = b;
   if (progressMonitor!=null) {
     progressMonitor.setMinimum(0);
   }

 }

}

/*
nodes = (NodeList) xpath.evaluate("//text:h", odfContent,  XPathConstants.NODESET);
for (int i = 0, n = nodes.getLength(); i < n; i++) {
  OdfHeading para = (OdfHeading) nodes.item(i);
  System.out.println(para.getStyleFamily());
  System.out.println(para);
  para.setTextContent("hej H versen!");
}


System.out.println(""
                   +odfDocument.getDocumentStyles()+"\n\n"
                   +odfDocument.getDefaultStyles()+"\n\n"
                   +odfDocument.getAutomaticStylesInternal()+"\n\n"
                   +odfDocument.getAutomaticStyles()+"\n\n"
  );
 */

