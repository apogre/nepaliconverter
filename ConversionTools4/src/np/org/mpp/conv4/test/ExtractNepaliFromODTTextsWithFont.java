package np.org.mpp.conv4.test;

import java.io.*;
import java.util.*;
import np.org.mpp.conv4.utils.GeneralReaderWriter;
import np.org.mpp.conv4.utils.OpenOfficeJacobsOldReaderWriter;
import np.org.mpp.conv4.utils.ConversionHandler;



public class ExtractNepaliFromODTTextsWithFont {

  //OpenOfficeODFDOMReaderWriter odtrw = new OpenOfficeODFDOMReaderWriter();
  GeneralReaderWriter odtrw = new OpenOfficeJacobsOldReaderWriter();

  HashMap<String,HashSet<String>> fontwords = new HashMap<String,HashSet<String>>();

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
        if (word.length()<40)
            words.add(word.trim());
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


  private void traverse(File file) {
    if (file.isDirectory()) {
      if (file.isHidden()) return;
      for (File f : file.listFiles()) {
        traverse(f);
      }
    } else
      try {
        System.out.println("Reading " + file);
        odtrw.convert(file.getPath(), "tmp/delete.odt", conversionHandler);
        //System.out.println("fontwords = " + fontwords.keySet());
      } catch (Exception e) {
        e.printStackTrace();
      }
  }



  private void save() throws FileNotFoundException {
    for (String font : fontwords.keySet()) {
      TreeSet<String> words = new TreeSet<String>(fontwords.get(font));

      PrintWriter pw = new PrintWriter("tmp/words_"+font+".txt");

      for (String word : words) {
        pw.println(word);
      }
      pw.close();
    }
  }

  private void saveHtml() throws FileNotFoundException {
    for (String font : fontwords.keySet()) {
      TreeSet<String> words = new TreeSet<String>(fontwords.get(font));

      PrintWriter pw = new PrintWriter("tmp/words_"+font+".html");
      pw.println("<html><body><table>");

      for (String word : words) {
        pw.println("<tr><td><p>" + word + "</p></td><td><p><font face='" + font + "'>" + word + "</font></p></td></tr>");
      }
      pw.println("</table></body></html>");
      pw.close();
    }
  }


  public static void main(String[] args) throws FileNotFoundException {
    ExtractNepaliFromODTTextsWithFont e = new ExtractNepaliFromODTTextsWithFont();
    e.traverse(new File("../nepalitext_odt/"));
    //e.traverse(new File("test"));
    //e.traverse(new File("test/testtextKantipur.odt"));
    e.save();
  }


}
