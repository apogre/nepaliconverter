package np.org.npp.conv.file;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import javax.xml.parsers.*;

import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import np.org.npp.conv.string.*;





public class OpenDocumentConverter {
  static final int DEBUG = 0;


  public static void main(String[] args) throws Exception {
    String fn = "aLotOfStyles-lil";
    //String fn = "BLT2lil";
    //String fn = "/home/j/esperanto/nepala vortaro/lille-kant";
    ZipFile zif = new ZipFile(fn+".odt");

    ZipOutputStream zof = new ZipOutputStream(new FileOutputStream(fn+"-ud.odt"));

    Document styles=null;
    Document content=null;
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();


    for (Enumeration zee =zif.entries(); zee.hasMoreElements();) {
      ZipEntry ze = (ZipEntry)zee.nextElement();

      //System.out.println(""+ze.getName()+"/"+ze.getComment()+"/"+ze.getCompressedSize()+"/"+ze.getSize()+"/"+ze.getMethod()+"/"+ze.getCrc()+"/"+ze.isDirectory());

      InputStream is = zif.getInputStream(ze);

      if (ze.getName().equals("styles.xml")) {
	styles = db.parse(is);
      } else
      if (ze.getName().equals("content.xml")) {
	content = db.parse(is);
      } else {
	//byte[] b = new byte[(int)size];
	//if (is.read(b)!=size) throw new InternalError(""+size);
	//zof.write(b);
	ZipEntry ze2 = new ZipEntry(ze.getName());
	zof.putNextEntry(ze2);
	byte[] b = new byte[1];
	while (is.read(b)>=0) { zof.write(b); }
	zof.closeEntry();
      }

      if (content != null && styles!=null) {
	{
	  convertStylesAndContent(styles, content);

	  {
	    zof.putNextEntry(new ZipEntry("styles.xml"));
	    OutputFormat format = new OutputFormat(styles);
	    //format.setIndenting(true); //format.setLineSeparator("\n");
	    new XMLSerializer(zof, format).serialize(styles);
	    zof.closeEntry();
	  }
	  zof.putNextEntry(new ZipEntry("content.xml"));
	  OutputFormat format = new OutputFormat(content);
	  //format.setIndenting(true); //format.setLineSeparator("\n");
	  new XMLSerializer(zof, format).serialize(content);
	  zof.closeEntry();
	  content=styles=null;
	}

      }

    }

    zof.close();
    System.out.println("----------- FINO ------------" +fn+"-ud.odt");

  }



  static ArrayList<Style> styles = new ArrayList<Style>();


  private static Map<String,Node> fontfaceNameToFontFamilyAttrNode(Document styles) throws Exception {
    Map<String,Node> fontfaceNameToFontFamilyAttrNode = new HashMap<String,Node>();

    {
      NodeList nl = styles.getElementsByTagName("style:font-face");

      for (int i = 0; i < nl.getLength(); i++) {
	NamedNodeMap na = nl.item(i).getAttributes();
	String styleName = na.getNamedItem("style:name").getNodeValue();
	Node nstyleFont = na.getNamedItem("svg:font-family");

	fontfaceNameToFontFamilyAttrNode.put(styleName, nstyleFont);
      }

      if (DEBUG>1) System.out.println("fontfaceNameToFontFamilyAttrNode=" + fontfaceNameToFontFamilyAttrNode);
    }
    return fontfaceNameToFontFamilyAttrNode;
  }



  public static class Style {
    Node node;
    String name;
    String fontBefore;
    String fontAfter;
    Style parentStyle;
    Style stylenodeDecidingFont;

    public String toString() {
      return name+":"+fontBefore+":"+node;
    }

    public static Map<String,Style> findStyleNodes(Document styles) {
      Map<String,Style> styleNameToNode = new LinkedHashMap<String,Style>();

      NodeList nl = styles.getElementsByTagName("style:style");

      for (int i = 0; i < nl.getLength(); i++) {
	Style s = new Style();
	s.node = nl.item(i);
	NamedNodeMap na = s.node.getAttributes();
	s.name = na.getNamedItem("style:name").getNodeValue();
	styleNameToNode.put(s.name, s);
      }

      if (DEBUG>1) System.out.println("styleNameToNode=" + styleNameToNode.values());

      return styleNameToNode;
    }
  }



  private static void convertStylesAndContent(Document styles, Document content) throws Exception {

    final Map<String,Node> fontfaceNameToFontFamilyAttrNode = fontfaceNameToFontFamilyAttrNode(styles);

    final Map<String,Style> styleNameToNode = Style.findStyleNodes(styles);

    styleNameToNode.putAll(Style.findStyleNodes(content));

    if (DEBUG>2) System.out.println("Samlet styleNameToNode=" + styleNameToNode.values());


    class X
    {


      public void findNodeDecidingFont(Style s, Map<String,Style> styleNameToNode) {

	if (s.stylenodeDecidingFont==null) {
	  // See if there is a child with an attribute specifying font, like
	  // <style:text-properties style:font-name="Kantipur"/>
	  NodeList nl = s.node.getChildNodes();
	  for (int i = 0; i<nl.getLength(); i++) {
	    Node n = nl.item(i);
	    //System.out.println(s.name+ ": "+n.getNodeName()+"="+n.getNodeValue());
	    NamedNodeMap na = n.getAttributes();
	    Node fontName;
	    if (na != null && (fontName=na.getNamedItem("style:font-name"))!=null) {
	      s.fontAfter = s.fontBefore = fontName.getNodeValue();
	      s.stylenodeDecidingFont = s;
	      if (DEBUG>1) System.out.println("Had font name: "+s);
	      return;
	    }
	  }

	  // Look at style:parent-style-name
	  Node parentStyleName = s.node.getAttributes().getNamedItem("style:parent-style-name");
	  if (parentStyleName != null) {
	    s.parentStyle = styleNameToNode.get(parentStyleName.getNodeValue());
	    if (s.parentStyle==null) throw new IllegalStateException("Unknown parentStyle:"+parentStyleName.getNodeValue());
	    findNodeDecidingFont(s.parentStyle, styleNameToNode);
	    s.stylenodeDecidingFont = s.parentStyle.stylenodeDecidingFont;
	    s.fontAfter = s.fontBefore = s.parentStyle.fontBefore;
	    if (DEBUG>1) System.out.println("Had parent: "+s+"    => "+s.parentStyle);
	    return;
	  }

	  // Nothing... hmmm.
	  if (DEBUG>1) System.out.println("No font found for style "+s.name);
	  s.stylenodeDecidingFont = s;
	  s.fontAfter = s.fontBefore = null;
	}
      }



      public void convertTextNodeToUnicode(Node textNode) {

	if (DEBUG>0) System.out.println("-- Konv "+textNode);

	String text = textNode.getNodeValue();
	if (text == null) return;
	if (text.trim().length() == 0) return;

	Node enclosingNode = textNode;

	int stopcounter = 0;
	Style s = null;

	while (true) {
	  if (stopcounter++>100) return;
	  Node style = enclosingNode.hasAttributes() ? enclosingNode.getAttributes().getNamedItem("text:style-name") : null;
	  s = style != null ? styleNameToNode.get(style.getNodeValue()) : null;
	  if (DEBUG>2) System.out.println("  konv"+stopcounter+ " "+enclosingNode + "   stylen=" + style+"  s="+s);

	  if (style==null) {
	    enclosingNode = enclosingNode.getParentNode();
	    continue;
	  }

          if (s == null) {
            System.err.println("HVAD" + textNode);
            return;
          }
	  if (s.fontBefore != null) break;
	  enclosingNode = enclosingNode.getParentNode();
	}


	String font = fontfaceNameToFontFamilyAttrNode.get(s.fontBefore).getNodeValue();
	if (DEBUG>1) System.out.println("   KONV? ("+s.fontBefore+" -> "+font+")    "+text);
	String teksto = textNode.getNodeValue();

	Font2Unicode font2Unicode = new Font2Unicode();
        //font2Unicode.interactive = false;

	String teksto2 = font2Unicode.toUnicode(font, teksto);

	if (!teksto2.equals(teksto)) {
          convertedFonts.add(font);
          //if (DEBUG>0)
            System.out.println("   KONV! ("+font+")    "+teksto+" -> "+teksto2);
	  //textNode.setNodeValue("१११" + teksto2 + "१११");
	  textNode.setNodeValue(teksto2);
	}
      }

      HashSet convertedFonts = new HashSet();

    }
    X x = new X();

    for (Style s : styleNameToNode.values()) x.findNodeDecidingFont(s, styleNameToNode);





    NodeList nl = content.getElementsByTagName("*");
    for (int i=0; i<nl.getLength(); i++) {
      Node n = nl.item(i);
      if (!n.getNodeName().startsWith("text:")) continue;

      //System.out.println(""+n+" "+n.getNodeType());




      {
	NodeList nl2 = n.getChildNodes();

	for (int j = 0; j < nl2.getLength(); j++) {
	  Node n2 = nl2.item(j);
	  //System.out.println("  nl2 " + n2+ n2.getNodeType());
	  if (n2.getNodeType() == Node.TEXT_NODE) {
	    x.convertTextNodeToUnicode(n2);
	  }
	}

      }
    }
    System.out.println("convertedFonts "+x.convertedFonts);


  }
/*
   <text:p text:style-name="List_20_2_20_Start">
      <text:span text:style-name="T21">yy</text:span>
      <text:span text:style-name="T22">yy</text:span>
      <text:span text:style-name="T19">yy</text:span>
   </text:p>

   <text:h text:style-name="P9" text:outline-level="2">Ne konvertu</text:h>
   <text:p text:style-name="Standard">Jen teksto kun normala tipografio: xxxxxx</text:p>


  styleNameToNode={Index=Index:null:[style:style: null], Heading_20__28_user_29_=Heading_20__28_user_29_:null:[style:style: null], Heading_20_3=Heading_20_3:null:[style:style: null], NepaliTegnTypografi2=NepaliTegnTypografi2:null:[style:style: null], List_20_2_20_Start=List_20_2_20_Start:null:[style:style: null], NepaliTegnTypografi=NepaliTegnTypografi:null:[style:style: null], List=List:null:[style:style: null], Index_20__28_user_29_=Index_20__28_user_29_:null:[style:style: null], Heading=Heading:null:[style:style: null], caption=caption:null:[style:style: null], Text_5f_20_5f_body=Text_5f_20_5f_body:null:[style:style: null], Graphics=Graphics:null:[style:style: null], Standard=Standard:null:[style:style: null], Text_20_body=Text_20_body:null:[style:style: null], Heading_20_2=Heading_20_2:null:[style:style: null], OLE=OLE:null:[style:style: null], Caption=Caption:null:[style:style: null], Heading_20_1=Heading_20_1:null:[style:style: null], Frame=Frame:null:[style:style: null]}

  styleNameToNode={T21=T21:null:[style:style: null], T14=T14:null:[style:style: null], T22=T22:null:[style:style: null], T11=T11:null:[style:style: null], T9=T9:null:[style:style: null], P7=P7:null:[style:style: null], T12=T12:null:[style:style: null], P1=P1:null:[style:style: null], T1=T1:null:[style:style: null], T13=T13:null:[style:style: null], P5=P5:null:[style:style: null], P3=P3:null:[style:style: null], T20=T20:null:[style:style: null], T7=T7:null:[style:style: null], T3=T3:null:[style:style: null], T10=T10:null:[style:style: null], P6=P6:null:[style:style: null], T18=T18:null:[style:style: null], P4=P4:null:[style:style: null], T6=T6:null:[style:style: null], T4=T4:null:[style:style: null], T15=T15:null:[style:style: null], T17=T17:null:[style:style: null], T5=T5:null:[style:style: null], P8=P8:null:[style:style: null], P9=P9:null:[style:style: null], T16=T16:null:[style:style: null], T8=T8:null:[style:style: null], P2=P2:null:[style:style: null], T2=T2:null:[style:style: null], T19=T19:null:[style:style: null]}

  Samlet styleNameToNode={Heading_20_3=Heading_20_3:null:[style:style: null], T14=T14:null:[style:style: null], List_20_2_20_Start=List_20_2_20_Start:null:[style:style: null], NepaliTegnTypografi=NepaliTegnTypografi:null:[style:style: null], T9=T9:null:[style:style: null], caption=caption:null:[style:style: null], T1=T1:null:[style:style: null], P5=P5:null:[style:style: null], P3=P3:null:[style:style: null], T20=T20:null:[style:style: null], Frame=Frame:null:[style:style: null], Index=Index:null:[style:style: null], T3=T3:null:[style:style: null], P6=P6:null:[style:style: null], T18=T18:null:[style:style: null], T17=T17:null:[style:style: null], P8=P8:null:[style:style: null], P9=P9:null:[style:style: null], T16=T16:null:[style:style: null], Heading_20_2=Heading_20_2:null:[style:style: null], T19=T19:null:[style:style: null], T21=T21:null:[style:style: null], List=List:null:[style:style: null], T22=T22:null:[style:style: null], T11=T11:null:[style:style: null], P7=P7:null:[style:style: null], Text_5f_20_5f_body=Text_5f_20_5f_body:null:[style:style: null], T12=T12:null:[style:style: null], P1=P1:null:[style:style: null], Standard=Standard:null:[style:style: null], Text_20_body=Text_20_body:null:[style:style: null], T13=T13:null:[style:style: null], OLE=OLE:null:[style:style: null], T7=T7:null:[style:style: null], Heading_20__28_user_29_=Heading_20__28_user_29_:null:[style:style: null], T10=T10:null:[style:style: null], NepaliTegnTypografi2=NepaliTegnTypografi2:null:[style:style: null], P4=P4:null:[style:style: null], T6=T6:null:[style:style: null], Index_20__28_user_29_=Index_20__28_user_29_:null:[style:style: null], T4=T4:null:[style:style: null], Heading=Heading:null:[style:style: null], T15=T15:null:[style:style: null], T5=T5:null:[style:style: null], Graphics=Graphics:null:[style:style: null], T8=T8:null:[style:style: null], P2=P2:null:[style:style: null], T2=T2:null:[style:style: null], Caption=Caption:null:[style:style: null], Heading_20_1=Heading_20_1:null:[style:style: null]}
*/

}
