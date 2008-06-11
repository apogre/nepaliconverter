package np.org.mpp.conv4.f2u;

import java.util.HashMap;
import java.util.HashSet;

import np.org.mpp.conv4.utils.ConversionHandler;



public class F2UConversionHandler implements ConversionHandler {

  private HashMap<String,Font2UnicodeMapping> mappings = new HashMap<String,Font2UnicodeMapping>();

  private Font2UnicodeMappingFactory fc = new Font2UnicodeMappingFactory();

  public F2UConversionHandler() {
    Font2UnicodeMapping identity = new Font2UnicodeMapping.Identity();
    mappings.put("arial", identity);
    mappings.put("arial narrow", identity);
    mappings.put("helvetica", identity);
    mappings.put("palatino", identity);
  }


  /**
   * Get an appropriate mapping for a given font name
   * @param font The font name, ex. "Preeti"
   * @return a Font2UnicodeMapping for that font. If no mapping could be found
   * the identity mapping will be returned
   * (thus not convertint e.g. Arial and Times New Roman)
   */
  private Font2UnicodeMapping findMapping(String font) {
    Font2UnicodeMapping mapping = mappings.get(font);

    if (mapping == null) try {
      font = font.toLowerCase().replaceAll("[0..9]", "");
      mapping = mappings.get(font);
      if (mapping == null) {
        mapping = fc.getMapping(font.toLowerCase().replaceAll("[0..9]", ""));
      }
    } catch (Exception e) {
      e.printStackTrace();
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
    }
    return text2;
  }


  public String giveFontReplacement(String font) {
    if (fontsConverted.contains(font)) {
      return "Arial";
    } else {
      return font;
    }
  }

}
