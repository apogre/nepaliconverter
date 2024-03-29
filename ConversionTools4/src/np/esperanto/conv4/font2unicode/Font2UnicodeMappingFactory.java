/*
 * Copyright (C) 2009  Nepala Esperanto-Asocio, http://www.esperanto.org.np/
 * Author: Jacob Nordfalk
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */
package np.esperanto.conv4.font2unicode;
import java.util.ArrayList;

import np.esperanto.conv4.test.TestF2U;
import np.esperanto.conv4.odfdom.SpreadsheetReader;

public class Font2UnicodeMappingFactory {


  public static void main(String[] args) throws Exception {

    Font2UnicodeMappingFactory fc = new Font2UnicodeMappingFactory();
    Font2UnicodeMapping f2u = fc.getMapping("preeti");

    test(f2u,
         "7fpFm",
         "ठाऊँ");

    test(f2u,
         "",
         "");

    test(f2u,
         "",
         "");


    test(f2u, // namaste saathi, tapaai dherai din pachhi aaunubhayo kina?
         "gd:t] ;fyL, tkfO{ w]/} lbg kl5 cfpg' eof] lsg<",
         "नमस्ते साथी, तपाई धेरै दिन पछि आउनु भयो किन?");


    test(f2u, // ma aaphno saajhedaar sansthaa herna gaeko thie
         "d cfˆgf] ;fem]bf/ ;+:yf x]g{ uPsf] lyPF",
         "म आफ्नो साझेदार संस्था हेर्न गएको थिएँ");

    test(f2u, // tapaai laai aaphno kaam garne Thaau kasto laagyo ?
         "tkfO{nfO{ cfˆgf] sfd ug]{ 7fpF s:tf] nfUof]<",
         "तपाईलाई आफ्नो काम गर्ने ठाउँ कस्तो लाग्यो?");


    test(f2u, //
         "",
         "");

  }

  private static void test(Font2UnicodeMapping f2u, String f, String u) {
    System.out.println("-----------");
    System.out.println("FONT:    "+f);
    String ures = f2u.toUnicode(f);
    System.out.println("RESULT:  "+ures);
    if (ures.equals(u))
      System.out.println("OK");
    else
      System.out.println("CORRECT: "+u + TestF2U.diffStringDetail(u,ures));
  }

  public Font2UnicodeMapping getMapping(String name) throws Exception {
    Font2UnicodeMapping f2u = new Font2UnicodeMapping(name);

    //ArrayList<ArrayList<String>> table = np.org.mpp.conv4.utils.jopendocument.JOpenDocumentSpreadsheetReader.read("res/"+name+"_unicode.ods",0,4);
    ArrayList<ArrayList<String>> table = SpreadsheetReader.read("res/"+name+"_unicode.ods",0,4);
    for (ArrayList<String> row : table) {
        String comment = row.get(0)+row.get(2);
        if (comment != null && comment.indexOf("DEBUG")!=-1) {
            System.out.println("DEBUG "+row);
            System.out.println(" FONT "+f2u.hex(row.get(1)));
            System.out.println(" UNIC "+f2u.hex(row.get(2)));
        }
        f2u.addLetter(row.get(1), row.get(2));
    }

    f2u.init();

    return f2u;
  }



  //The old way 'by hand' (without ODFDOM):
/*

  public Font2UnicodeMapping getMapping(String name) throws Exception {
    Font2UnicodeMapping f2u = new Font2UnicodeMapping(name);

    Document content = xxxreadOdtContent("res/"+name+"_unicode.ods");
    xxxreadOdtTable(content, f2u);


    return f2u;
  }



  private Document xxxreadOdtContent(String filename) throws Exception {
    File file = new File(filename);
    if (!file.exists())
        throw new FileNotFoundException(filename);
    ZipFile zif = new ZipFile(file);
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



  private void xxxreadOdtTable(Document content, Font2UnicodeMapping f2u) throws Exception {

    OutputFormat format = new OutputFormat(content);
    format.setIndenting(false); format.setLineSeparator("\n");
    StringWriter sw = new StringWriter();
    new XMLSerializer(sw, format).serialize(content);


    String tuto = sw.toString();

    //System.out.println(tuto.substring(40000,50000));

    tuto = tuto.replace('\n',' '); // Pattern.DOTALL doesent always work, so replace \n!


    // find table
    Matcher m = Pattern.compile("<table:table-row.*?>" // row start
                                //+".*?<table:table-cell.*?>.*?<text:p.*?>(.*?)</text:p>.*?</table:table-cell>" // first cell
                                //+".*?<table:table-cell.*?>.*?<text:p.*?>(.*?)</text:p>.*?</table:table-cell>" // 2nd cell
                                +".*?((<table:table-cell/>)|<table:table-cell.*?>(.*?)</table:table-cell>)" // first cell
                                +".*?((<table:table-cell/>)|<table:table-cell.*?>(.*?)</table:table-cell>)" // first cell
                                +".*?" // rest of rows are ignored
                                +"</table:table-row>" // row end
  //      , Pattern.UNIX_LINES & Pattern.DOTALL & Pattern.MULTILINE ).matcher(tuto);
  ).matcher(tuto);


    while (m.find()) {

      String col1 = m.group(3);
      String col2 = m.group(6);
      //System.out.println(m.groupCount() + " "+ col1 + "  -> "+col2);
      if (col1 == null) continue;
      if (col2 == null) col2="";

      // col1 is Preeti. Any superflous spaces should be removed
      String col1text = col1.replaceAll( ".*?<text:p.*?>(.*?)</text:p>.*?", "$1");
      col1 = foriguLigojnKajSpacojn(col1text);
      col1 = col1.trim();

      // col2 is Unicode. One space is OK, all other superflous spaces should be removed


      String col2text = col2.replaceAll( ".*?<text:p.*?>(.*?)</text:p>.*?", "$1");
      if (col2.equals(col2text)) {
        col2 = ""; // no text paragraph. <text:p.*?>
      } else {
        col2 = foriguLigojnKajSpacojn(col2text);
        String trimmed = col2.trim();
        if (trimmed.length()==0 && col2.length()!=0) trimmed = " "; // One space is OK
        col2 = trimmed;
      }

      //System.out.println(col1 + "  -> "+col2);

      f2u.addLetter(col1, col2);
    }

   // System.exit(0);
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
    esp = esp.replaceAll("&lt;","<");
    esp = esp.replaceAll("&gt;",">");

    if (!esp.replaceAll("&.*?;","").equals(esp)) {
      IllegalStateException e = new IllegalStateException("Incomplete translation: "+esp);
      //e.printStackTrace();
      throw e;
    }
    return esp;
  }
*/
}
