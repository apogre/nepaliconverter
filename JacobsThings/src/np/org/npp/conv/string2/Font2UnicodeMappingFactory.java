package np.org.npp.conv.string2;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;
import javax.xml.parsers.*;

import org.apache.xml.serialize.*;
import org.w3c.dom.*;

public class Font2UnicodeMappingFactory {



  public static void main(String[] args) throws Exception {

    Font2UnicodeMappingFactory fc = new Font2UnicodeMappingFactory();
    Font2UnicodeMapping f2u = fc.getMapping("preeti");

    f2u.checkOkAllChars();

    test(f2u, // namaste saathi, tapaai dherai din pachhi aaunubhayo kina?
         "gd:t] ;fyL, tkfO{ w]/} lbg kl5 cfpg' eof] lsg <",
         "नमस्ते साथी, तपाई धेरै दिन पछि आउनु भयो किन?");


    test(f2u, // ma aaphno saajhedaar sansthaa herna gaeko thie
         "d cfˆgf] ;fem]bf/ ;+:yf x]g{ uPsf] lyPF .",
         "म आफ्नो साझेदार संस्था हेर्न गएको थिएँ।");


    test(f2u, // tapaai laai aaphno kaam garne Thaau kasto laagyo ?
         "tkfO{nfO{ cfˆgf] sfd ug]{ 7fpF s:tf] nfUof] <",
         "तपाईलाई आफ्नो काम गर्ने ठाउँ कस्तो लाग्यो?");


    test(f2u, //
         "",
         "");

  }

  private static void test(Font2UnicodeMapping f2u, String f, String u) {
    String u2 = f2u.toUnicode(f);
    System.out.println("-----------");
    System.out.println(u2);
    System.out.println(u);
  }

  public Font2UnicodeMapping getMapping(String name) throws Exception {
    Font2UnicodeMapping f2u = new Font2UnicodeMapping(name);

    Document content = readOdtContent(name+"_unicode.odt");

    readOdtTable(content, f2u);

    return f2u;
  }


  private Document readOdtContent(String filename) throws Exception {
    ZipFile zif = new ZipFile(filename);
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

    //System.out.println("Legas "+ dosiero);

    for (Enumeration zee =zif.entries(); zee.hasMoreElements();) {
      ZipEntry ze = (ZipEntry)zee.nextElement();
      InputStream is = zif.getInputStream(ze);
      if (ze.getName().equals("content.xml")) {
        Document content = db.parse(is);
        return content;
      }
    }
    throw new FileNotFoundException("Did not find contents in "+filename);
  }



  private void readOdtTable(Document content, Font2UnicodeMapping f2u) throws Exception {

    OutputFormat format = new OutputFormat(content);
    format.setIndenting(false); format.setLineSeparator("\n");
    StringWriter sw = new StringWriter();
    new XMLSerializer(sw, format).serialize(content);

    String tuto = sw.toString();

    //System.out.println(tuto.substring(15000,16000));

    // find table
    Matcher m = Pattern.compile("<table:table-row>" // row start
                                +".*?<table:table-cell.*?>.*?<text:p.*?>(.*?)</text:p>.*?</table:table-cell>" // first cell
                                +".*?<table:table-cell.*?>.*?<text:p.*?>(.*?)</text:p>.*?</table:table-cell>" // 2nd cell
                                +".*?" // rest of rows are ignored
                                +"</table:table-row>" // row end
        , Pattern.UNIX_LINES & Pattern.DOTALL & Pattern.MULTILINE ).matcher(tuto);


    while (m.find()) {
      String col1 = foriguLigojnKajSpacojn(m.group(1));
      String col2 = foriguLigojnKajSpacojn(m.group(2));

      System.out.println(col1 + "  -> "+col2);
      f2u.addLetter(col1, col2);
    }

  }


  private static String foriguLigojnKajSpacojn(String esp) {
    //dev = dev.replaceAll("<text:a [^>]*> *\\w</text:a>",""); // forigu cxiuj unuliterajn ligojn
    //dev = dev.replaceAll("<text:span text:style-name=\"\\w\">(\\s)</text:span>","$1"); // forigu tipojn kiu nur kosideras spacojn
    //esp = esp.replaceAll("\\s?<text:a [^>]*>\\s*\\w</text:a>",""); // forigu cxiuj unuliterajn ligojn kaj evt spaco antauxe
    //esp = esp.replaceAll("<text:span text:style-name=\"\\w\">(\\s)*</text:span>","$1"); // forigu tipojn kiu nur kosideras spacojn
    //esp = esp.replaceAll("<text:soft-page-break/>","");
    esp = esp.replaceAll("<text:.*?>","");
    esp = esp.replaceAll("</text:.*?>","");
    esp = esp.replaceAll("&amp;","&");
    esp = esp.replaceAll("&quot;","\"");

    if (!esp.replaceAll("&.*?;","").equals(esp)) {
      new IllegalStateException("Incomplete translation: "+esp);
    }
    return esp.trim();
  }

}
