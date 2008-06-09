package np.org.mpp.conv4.old_f2u;

import java.util.HashSet;



public class Old_F2UConversionHandler implements ConversionHandler {

  Font2Unicode f2u = new Font2Unicode();

  public Old_F2UConversionHandler() {
      f2u.interactive = false;
  }


  public String convertText(String font, String text) {
    if (text == null) return null;
    if (text.trim().length() == 0) return text;

    String text2 = f2u.toUnicode(font, text);
    if (!text2.equals(text)) {
      fontsConverted.add(font);
    }
    return text2;
  }

  HashSet<String> fontsConverted = new HashSet<String>();


  public String giveFontReplacement(String font) {
    if (fontsConverted.contains(font)) {
      return "Arial";
    } else {
      return font;
    }
  }

}
