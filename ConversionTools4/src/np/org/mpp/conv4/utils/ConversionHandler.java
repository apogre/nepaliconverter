package np.org.mpp.conv4.utils;

/**
 * An interface to be implemented by all classes performing conversion.
 * This may be to Unicode, from Unicode or a transliteration
 * @author Jacob Nordfalk
 *
 */
public interface ConversionHandler {

  /**
   * Convert text.
   * This migt be to Unicode, from Unicode or a transliteration
   * @param text The text to be converted
   * @param font The font the text is encoded in (if any)
   * @return The converted text
   */
  public String convertText(String font, String text);

  /**
   * Specify which font a given font should be replaced with.
   * NOTE: This method must only be called AFTER calls to convertText(),
   * @param font The original font. Ex. "Preeti"
   * @return The replacement font. Ex. "Arial".
   * If no replacement of this font should be made the original font should be returned.
   */
  public String giveFontReplacement(String font);

}
