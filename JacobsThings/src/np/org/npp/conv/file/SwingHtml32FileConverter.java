package np.org.npp.conv.file;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.net.*;


/*
  public String xxconvert(Charset inEnc, String inFile, String font, int maxLen) throws IOException {

    HTMLEditorKit ek = new HTMLEditorKit();
    Document doc = new DefaultStyledDocument(); //DefaultStyledDocument();
    doc = ek.createDefaultDocument();
    doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);

    System.err.println(ek.getContentType());
    FileInputStream fis = new FileInputStream(inFile);
    try {
      ek.read(fis, doc, 0);
    }
    catch (ChangedCharSetException e) {
      e.printStackTrace();
      System.err.println(e.getCharSetSpec());
    }
    catch (BadLocationException ex) {
      ex.printStackTrace();
    }
    //toUnicode(doc.getDefaultRootElement());

    StringWriter sw = new StringWriter();
    try {
      ek.write(sw, doc, 0, doc.getLength());
    }
    catch (BadLocationException ex1) {
    }

    lastConversionRes = sw.toString();
    return lastConversionRes;
  }
*/

/*
public class SwingHtml32FileConverter extends FileConverter {

  public String name() { return "HTML"; }

  public void toUnicode(Element el) {
    try {
      for (Enumeration en = el.getAttributes().getAttributeNames(); en.hasMoreElements(); ) {
	Object name = en.nextElement();
	Object val = el.getAttributes().getAttribute(name);

	if (name.toString().contains("Kantipur")) {

	}
	System.out.println(name + ":" + name.getClass() + "=" + val + ":" + val.getClass());
	System.out.println(el.getStartOffset() + " " + el.getName() + " - " + el.getElementCount());
	System.out.println("el.getAttributes().getClass();=" +el.getAttributes().getClass());



	if ("font-family".equals(name.toString()) || "font".equals(name.toString()) || "family".equals(name.toString())) {
	  //System.out.println(n + ":" + n.getClass() + "=" + v + ":" + v.getClass());
	  //System.out.println(el.getStartOffset() + " " + el.getName() + " - " + el.getElementCount());
	  //System.out.println("el.getAttributes().getClass();=" +el.getAttributes().getClass());

	  int ofs = el.getStartOffset();
	  int len = el.getEndOffset() - ofs;

	  String txt = el.getDocument().getText(ofs, len);
	  System.out.println("txtel=" + txt+ "   "+val.toString());

	  //el.getDocument().insertString(5, "dsd", null);

	  String unic = font2Unicode.toUnicode(val.toString(), txt);

	  //MutableAttributeSet a = (MutableAttributeSet) el.getAttributes().copyAttributes();

	  if (!txt.equals(unic)) {

	    MutableAttributeSet a = new SimpleAttributeSet(el.getAttributes());
	    System.out.println(""+a);
	    a.removeAttribute(name);
	    StyleConstants.setFontFamily(a, "Arial");
	    //System.out.println(javax.swing.text.html.CSS.Attribute.FONT);
	    //System.out.println(javax.swing.text.html.CSS.Attribute.FONT_FAMILY);
	    //a.removeAttribute(javax.swing.text.html.CSS.Attribute.FONT);
	    //a.addAttribute(n, f);
	    System.out.println(""+a);
	    Document d = el.getDocument();
	    d.remove(ofs, len);
	    //d.insertString(ofs, unic, a);
	    d.insertString(ofs, " NNNNEEEEWWWW[" + unic + "] ", a);
	  }
	}
      }
      //System.out.println("al="+el.getAttributes());
      for (int i = 0; i < el.getElementCount(); i++)
	toUnicode(el.getElement(i));
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }



  public void convert() throws IOException {
    JEditorPane jep = new JEditorPane();
    //jep.setEditorKit(new HTMLEditorKit() {
    jep.setEditorKit(new RTFEditorKit() {

      public Document createDefaultDocument() {
	Document doc = super.createDefaultDocument();
	//((HTMLDocument)doc).setAsynchronousLoadPriority( -1);

	if (p.maxLen<Integer.MAX_VALUE) doc.addDocumentListener(new DocumentListener() {
	  boolean stopped;
	  public void insertUpdate(DocumentEvent e) {
	    if (!stopped && e.getOffset() > p.maxLen) {
	      stopped = true;
	      throw new RuntimeException("stop");
	    }
	  };
	  public void removeUpdate(DocumentEvent e) {};
	  public void changedUpdate(DocumentEvent e) {};
	}
	);

	return doc;
      }
    });


    URLConnection c = new File(p.inFile).toURL().openConnection();
    System.out.println(c.getContentType());

    jep.setPage(new File(p.inFile).toURL());

    JScrollPane sp = new JScrollPane(jep);
    sp.setPreferredSize(new Dimension(800,800));

    JOptionPane.showConfirmDialog(null,sp);


    Document doc = (Document)jep.getDocument();

    toUnicode(doc.getDefaultRootElement());
    JOptionPane.showConfirmDialog(null,sp);


    try {
      StringWriter sw = new StringWriter();
      jep.getEditorKit().write(sw, doc, 0, doc.getLength());
      System.out.println(sw);
      lastConversionRes = doc.getText(0, doc.getLength());
    }
    catch (BadLocationException ex) {
      ex.printStackTrace();
      lastConversionRes = ex.getMessage();
    }
  }




  public void convertRTF() throws IOException,
      javax.swing.text.BadLocationException {

    RTFEditorKit kit = new RTFEditorKit() {

      public Document createDefaultDocument() {
	Document doc = super.createDefaultDocument();
	//((HTMLDocument)doc).setAsynchronousLoadPriority( -1);

	if (p.maxLen<Integer.MAX_VALUE) doc.addDocumentListener(new DocumentListener() {
	  boolean stopped;
	  public void insertUpdate(DocumentEvent e) {
	    if (!stopped && e.getOffset() > p.maxLen) {
	      stopped = true;
	      throw new RuntimeException("stop");
	    }
	  };
	  public void removeUpdate(DocumentEvent e) {};
	  public void changedUpdate(DocumentEvent e) {};
	}
	);

	return doc;
      }
    };

    Document doc = kit.createDefaultDocument();

    // Load an RTF file into the editor
    try {
      FileInputStream fi = new FileInputStream(p.inFile);
      kit.read(fi, doc, 0);
    }
    catch (Exception e) {
      System.out.println("File not found");
    }



    JEditorPane jep = new JEditorPane();
    jep.setContentType("text/rtf");


    jep.setDocument(doc);

    JScrollPane sp = new JScrollPane(jep);
    sp.setPreferredSize(new Dimension(800,800));

    JOptionPane.showConfirmDialog(null,sp);


    toUnicode(doc.getDefaultRootElement());
    JOptionPane.showConfirmDialog(null,sp);


     FileOutputStream fos = new FileOutputStream("res.rtf");
      kit.write(fos, doc, 0, doc.getLength());
      lastConversionRes = doc.getText(0, doc.getLength());
  }




  public static void main(String[] args) throws Exception {
    SwingHtml32FileConverter c = new SwingHtml32FileConverter();
    c.p.inFile = "/home/esperanto/nepala vortaro/vortooo.rtf";
    //c.p.inFile = "/home/esperanto/nepala vortaro/nepalaVortaro/vorto03j2.htmlxxx";
    //c.p.inFile = "MS-nepali.html";
    c.p.maxLen = 10000;
    c.convertRTF();
  }
}
*/
