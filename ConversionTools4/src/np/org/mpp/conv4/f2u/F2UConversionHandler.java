package np.org.mpp.conv4.f2u;

import java.util.HashMap;
import java.util.HashSet;

import np.org.mpp.conv4.utils.ConversionHandler;



public class F2UConversionHandler implements ConversionHandler {

  private HashMap<String,Font2UnicodeMapping> mappings = new HashMap<String,Font2UnicodeMapping>();

  private Font2UnicodeMappingFactory f2uFactory = new Font2UnicodeMappingFactory();

  public static final Font2UnicodeMapping identity = new Font2UnicodeMapping.Identity();


  public F2UConversionHandler() {
    mappings.put("arial", identity);
    mappings.put("arial narrow", identity);
    mappings.put("helvetica", identity);
    mappings.put("palatino", identity);
    setStandardFontReplacement("Kalimati");
    //setStandardFontReplacement("ArialMT");
  }


  /**
   * Get an appropriate mapping for a given font name
   * @param font The font name, ex. "Preeti"
   * @return a Font2UnicodeMapping for that font. If no mapping could be found
   * the identity mapping will be returned
   * (thus not convertint e.g. Arial and Times New Roman)
   */
  public Font2UnicodeMapping findMapping(String font) {
    Font2UnicodeMapping mapping = mappings.get(font);

    if (mapping == null) try {
      font = font.toLowerCase().replaceAll("[0..9]", "");
      mapping = mappings.get(font);
      if (mapping == null) {
        mapping = f2uFactory.getMapping(font.toLowerCase().replaceAll("[0..9]", ""));
      }
    } catch (Exception e) {
      //e.printStackTrace();
      System.err.println(e);
      mapping = new Font2UnicodeMapping.Identity();
    } finally {
      mappings.put(font, mapping);
    }

    return mapping;
  }

  HashSet<String> fontsConverted = new HashSet<String>();

  public String convertText(String font, String text) {
    if (text == null) return null;
    if (text.trim().length() == 0) return text;

    Font2UnicodeMapping mapping = findMapping(font);
    String text2 = mapping.toUnicode(text);
    if (!text2.equals(text)) {
      fontsConverted.add(font);
      
      //System.err.println("fontsConverted = " + fontsConverted);
    }
    return text2;
  }

  private String standardFontReplacement = "Arial";
  
  public void setStandardFontReplacement(String f) {
    standardFontReplacement = f;
    mappings.put(f.toLowerCase(), identity);    
  }

      
      
  public String giveFontReplacement(String font) {
    if (fontsConverted.contains(font)) {
      return standardFontReplacement;
    } else {
      return font;
    }
  }

}
