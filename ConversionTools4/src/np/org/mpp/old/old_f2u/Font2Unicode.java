package np.org.mpp.old.old_f2u;

import java.util.*;
import javax.swing.*;
import java.util.prefs.*;




/**
 * A Font Specific encoding to Unicode converter. Can interactively ask user about coding when in doubt.
 * Decisions can be stored (using Java Preferences API)
 * @author Jacob Nordfalk
 * @version 1.0
 */
public class Font2Unicode {

  private Map fontNameToFontClass = new LinkedHashMap();

  /**
   * These font names are already Unicode so they don't need conversion.
   * User may contribute to this list in Preferences, in
   * /.java/.userPrefs/np/org/npp/extrafontNameMapping/prefs.xml
   * @see #extrafontNameMapping
   */
  private Set fontAlreadyUnicodeSoDontConvertNames = new LinkedHashSet(Arrays.asList(
      new String[] { "(no conversion)", "Arial", "Arial Narrow", "Times" } ) );


  /**
   * These font names are mapped to these converters.
   * User may contribute to this list in Preferences, in
   * /.java/.userPrefs/np/org/npp/extrafontNameMapping/prefs.xml
   * @see #extrafontNameMapping
   */
  public Font2Unicode() {
    fontNameToFontClass.put("(no conversion)", new DontConvert());
    fontNameToFontClass.put("Kantipur", new Kantipur());
    //fontNameToFontClass.put("Nepali", new Kantipur()); // WE ARE NOT SURE SO LET USER DECIDE
    fontNameToFontClass.put("Preeti", new Preeti());
    fontNameToFontClass.put("Jaga Himali", new Jaga_Himali());
    fontNameToFontClass.put("Kanchan", new Kanchan());
    fontNameToFontClass.put("Himali", new Himali());
    //fontNameToFontClass.put("Fontasy Himali", new Himali()); // WE ARE NOT SURE SO LET USER DECIDE

    /* Unsupported by MPP:
    Rukmini, Akshar, Gauri, Himalb, Sagarmatha
    Shangrila Hybrid ?
    */
  }


  public boolean interactive = true;
  public boolean silent = false;
  public boolean justStripSpaces = false;

  private Preferences extrafontNameMapping = Preferences.userNodeForPackage(this.getClass()).node("extrafontNameMapping");


  /**
   * User contributed exceptions from the conversions.
   * Stored in  ~/.java/.userPrefs/np/org/npp/convertExceptions/prefs.xml
   */
  private Preferences convertExceptions = Preferences.userNodeForPackage(this.getClass()).node("convertExceptions");

  public Collection knownFontNames() {
    return fontNameToFontClass.keySet();
  }


  public String toUnicode(String font, String input) {

    if (input == null || input.trim().length()==0) return input;
    if (font == null || font.trim().length()==0) return input;



    font = extrafontNameMapping.get(font, font);
    /* add an extra space to avoid many exceptions like
	 java.lang.StringIndexOutOfBoundsException: String index out of range: 19
	     at java.lang.String.charAt(String.java:687)
	     at np.org.npp.Preeti.toUnicode(Preeti.java:561)
	     at np.org.npp.NonUnicodeFont.toUnicode(NonUnicodeFont.java:11)
     */
    if (fontAlreadyUnicodeSoDontConvertNames.contains(font)) return input;
    input = "  " +input + "  ";

    NonUnicodeFont nonUnicodeFont = (NonUnicodeFont) fontNameToFontClass.get(font);


    String convertException = convertExceptions.get(font+";"+input,null);
    if (convertException!=null) return convertException;

    String err = null;
    String unic = null;
    boolean problemForConverter = false;
    boolean noConverterFound = false;

    if (nonUnicodeFont!=null) {
      unic = nonUnicodeFont.toUnicode(input);
      if (!silent && nonUnicodeFont.problemsInLastConversion.size()>0) {
        unic = "XXX"+unic;
	problemForConverter = true;
	err = "The following problems occured for converter '"+font+"' used on text '"+input+"':<br/>";
	for (int i = 0; i < nonUnicodeFont.problemsInLastConversion.size(); i++) {
	  Exception e = (Exception) nonUnicodeFont.problemsInLastConversion.get(i);
	  e.printStackTrace();
	  err = err + e.getMessage() +"<br/>";
	}
      }

    }

    if (nonUnicodeFont==null) {
      noConverterFound = true;
      err = "No font converter exists for '"+font+"' used on text '"+input+"'.";
      unic = input;
      unic = "XXX"+unic;
    }

    if (err != null) {
      if (interactive) {

	AskWhichEncoderToUsePanel ask = new AskWhichEncoderToUsePanel();
	ask.set(fontNameToFontClass, font, input, err);
	String chosen;

	if (ask.allGivesSameUnicode) {
	  chosen = ask.fontNames[ask.fontList.getSelectedIndex()];
	} else
	{
	  int ret = JOptionPane.showConfirmDialog(null, ask,
						  "Select converter for " +
						  font,
						  JOptionPane.CANCEL_OPTION,
						  JOptionPane.QUESTION_MESSAGE);
	  System.out.println("ret = " + ret);
	  if (ret != JOptionPane.OK_OPTION) throw new RuntimeException("Cancelled by user");
	  unic = ask.jTextField1.getText();

	  if (ask.dontAskAgain.isSelected()) {
	    if (problemForConverter) try {
	      convertExceptions.put(font + ";" + input, unic);
	    } catch (Exception e) { e.printStackTrace(); }
	    if (noConverterFound) {
	      chosen = ask.fontNames[ask.fontList.getSelectedIndex()];
	      extrafontNameMapping.put(font, chosen);
	      convertExceptions.put(font + ";" + input, unic);
	    }
	  }
	}

      } else {
	System.err.println("WARNING: "+err);
      }
    }

    if (justStripSpaces) return unic.trim();

    if (unic.startsWith(" ") && unic.endsWith("  ")) {
      if (unic.charAt(1)!=' ') {
        new IllegalStateException(unic+" didnt end with a space! input='"+input+"' unic='"+unic+"'.").printStackTrace();
        unic = unic.substring(1,unic.length()-2);
      }
      else
      {
        unic = unic.substring(2,unic.length()-2);
      }
    } else {
      if (err==null) throw new IllegalStateException(unic+" didnt end with a space! input='"+input+"' unic='"+unic+"'.");
    }

    return unic;
  }

  /*

    public String kantipurAlUnikodo(String teksto) {
      return toUnicode("Kantipur", teksto);

	StringBuffer sb = new StringBuffer(teksto.length());
	if (teksto.indexOf("&")!=-1) {
	    System.err.println("&&&&&&&&&&&" + teksto);
	}
	kanti.toUnicode(sb,teksto);
	return sb.toString();
    }
   */

}
