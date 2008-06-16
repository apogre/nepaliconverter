package np.org.mpp.conv4.test;

import java.io.*;
import java.util.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import np.org.mpp.conv4.utils.ConversionHandler;
import np.org.mpp.conv4.utils.GeneralReaderWriter;
import org.openoffice.odf.doc.OdfDocument;
import org.openoffice.odf.doc.OdfFileDom;



public class ExtractNepaliFromODTTextsWithFont {

  //OpenOfficeODFDOMReaderWriter odtrw = new OpenOfficeODFDOMReaderWriter();
  GeneralReaderWriter odtrw = new np.org.mpp.conv4.utils.odfdom.OpenOfficeReaderWriter();

  HashMap<String,HashSet<String>> fontwords = new HashMap<String,HashSet<String>>();


  HashSet<String> lookForWords = new HashSet<String>(Arrays.asList(new String[] {
  "\\m", "\\mPd\\", "j|mflGtsf/Laf6", "m", "m\\", "", "", "", }));

  File actualFile;
  XPath  xpath = XPathFactory.newInstance().newXPath();

  ConversionHandler conversionHandler = new ConversionHandler() {
    /**
     * Convert text.
     * This migt be to Unicode, from Unicode or a transliteration
     * @param text The text to be converted
     * @param font The font the text is encoded in (if any)
     * @return The converted text
     */
    public String convertText(String font, String text) {
      if (text.length()==0) return text;

      //System.out.println("convertText("+font+", '"+text+"')");

      HashSet<String> words = fontwords.get(font);
      if (words == null) {
        System.out.println("Created words for font " + font);
        words = new HashSet<String>();
        fontwords.put(font, words);
      }

      if (text.indexOf("Ldfg")!=-1) {
          ;
      }

      for (String word : text.split("[ \\n\\t]")) {
        word = word.trim();
        if (word.length()==0) continue;
        if (word.length()<40)
            words.add(word);
        if (lookForWords.contains(word)) {
            System.err.println(word+" found in " +actualFile+": "+text);
        }
      }

      return text;
    }

    /**
     * Specify which font a given font should be replaced with.
     * NOTE: This method must only be called AFTER calls to convertText(),
     * @param font The original font. Ex. "Preeti"
     * @return The replacement font. Ex. "Arial".
     * If no replacement of this font should be made the original font should be returned.
     */
    public String giveFontReplacement(String font) {
      return font;
    }

  };



  public ExtractNepaliFromODTTextsWithFont() {
  }

  private static int documentNumber = 0;

  private void traverse(File file) {
    if (file.isDirectory()) {
      if (file.isHidden()) return;
      for (File f : file.listFiles()) {
        traverse(f);
      }
    } else
      try {
        System.out.println(++documentNumber +" Reading " + file);

        OdfFileDom content = OdfDocument.load( file ).getContent( );
        if (content.toString().indexOf("text:change-start text:change-id")>0) {
            System.out.println("Changesets found, skipping " + file);
        } else {

          actualFile = file;
          //odtrw.convert(file.getPath(), "tmp/delete.odt", conversionHandler);
          odtrw.convert(file.getPath(), null, conversionHandler);
          //System.out.println("fontwords = " + fontwords.keySet());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }



  private void save() throws FileNotFoundException {
    for (String font : fontwords.keySet()) {
      if (font.indexOf("Arial") != -1) continue;
      if (font.indexOf("Times") != -1) continue;
      if (font.indexOf("Sans") != -1) continue;
      TreeSet<String> words = new TreeSet<String>(fontwords.get(font));
      PrintWriter pw = new PrintWriter("tmp/words_"+font+".txt");
      for (String word : words) {
        pw.println(word);
      }
      pw.close();
      System.out.println("wrote words for "+font);

    }
  }


  public static void main(String[] args) throws FileNotFoundException {
    ExtractNepaliFromODTTextsWithFont e = new ExtractNepaliFromODTTextsWithFont();
    e.traverse(new File("../nepalitext_odt"));
    //e.traverse(new File("../nepalitext_odt/MS/ICOC"));
    //e.traverse(new File("test"));
    //e.traverse(new File("test/testtextKantipur.odt"));
    e.save();
  }


}
