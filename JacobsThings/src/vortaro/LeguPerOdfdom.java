package vortaro;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
//import org.openoffice.odf.dom.element.text.OdfParagraphElement;
import org.openoffice.odf.dom.OdfNamespace;
import org.w3c.dom.Document;
import org.w3c.dom.*;
import org.openoffice.odf.dom.style.*;

import org.openoffice.odf.doc.element.text.*;
import org.openoffice.odf.doc.OdfDocument;
import org.openoffice.odf.dom.style.props.OdfStylePropertiesSet;
import org.openoffice.odf.dom.element.OdfStylableElement;


public class LeguPerOdfdom {
  public static void main(String[] args) throws Exception {

    // loads the ODF document from the path
    //OdfDocument odfDocument = OdfDocument.load("/home/j/esperanto/nepala vortaro/provo.odt");
    OdfDocument odfDocument = OdfDocument.load("aLotOfStyles-lil.odt");


// get the ODF content as DOM tree representation
    Document odfContent = odfDocument.getContent();

// XPath initialization ''(JDK5 functionality)''
    XPath xpath = XPathFactory.newInstance().newXPath();
    xpath.setNamespaceContext(new OdfNamespace());

// receiving the first paragraph "//text:p[1]" ''(JDK5 functionality)''
//       OdfParagraphElement para = (OdfParagraphElement) xpath.evaluate("//text:p[1]", odfContent, XPathConstants.NODE);
//    System.out.println(para.getTextContent());
//    para.setTextContent("hej versen!");

    NodeList nodes;
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

    // http://java.sun.com/j2se/1.5.0/docs/api/javax/xml/xpath/package-summary.html
    nodes = (NodeList) xpath.evaluate("//text:span", odfContent,  XPathConstants.NODESET);
    for (int i = 0, n = nodes.getLength(); i < n; i++) {
      OdfSpan para = (OdfSpan) nodes.item(i);

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
        System.out.println( "\nFEJL, INGEN STY!\n" + styleName+ " " +sty + " "
                            + "\n" + para.toString()
                            + "\n" + para
                            + "\nparent=" + para.getParentNode()
            );
        continue;

      }

      String font = sty.getProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.STYLE.toString(), OdfParagraphStyle.FontName.toString());

      //OdfStyleFamily fam = para.getStyleFamily();
      System.out.println( "\n" + styleName+ " " +sty + " "+ sty.getParentStyle()
                          + "\n" + para.toString()
                          + "\n" + para
                          + "\nfont=" + font
                          + "\nparent=" + sty.getParentStyle()
//                          + "\n" + para.getDocumentStyle()
          );

      OdfStylableElement parent = para;
      while (font==null) {
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


    odfDocument.save("slet.odt");




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
    return sty;
  }
}
