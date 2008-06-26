package np.org.mpp.conv4.translit;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;

import np.org.mpp.conv4.utils.*;
import np.org.mpp.conv4.utils.xml.*;

public class NepaliTransliterationJacob implements ConversionHandler  {

  HashMap<String, String> devToRomanMap;
  HashMap<String, String> specialRulesMap;
  ArrayList<String> suffices = new ArrayList<String>();

  static String ALL = Devanagari.ALL;
  static String CON = Devanagari.CONSONANTS;
  static String VFL = Devanagari.VOCALFLAGS;
  static String NAZ = Devanagari.NAZALIZATIONS;
  static String HAL = Devanagari.HALANTA; // "्"; // Halanta


  public NepaliTransliterationJacob() {
      this("res/transliteration/NepaliJacobVortaro.xml", true);
  }


	public NepaliTransliterationJacob(String translitXmlFile, boolean useExceptions) {
    devToRomanMap = new SAXParser(translitXmlFile).getHashMap();

    String checkChars = ALL.replaceAll(HAL,"");
    for (int i=0; i<checkChars.length(); i++) {
      String c = ""+checkChars.charAt(i);
      if (devToRomanMap.get(c)==null) {
        System.err.println("Missing devanagari character in map: "+c);
//        System.err.println("	<char trans=\""+DevAlRomana.m.get(c)+"\">"+c+"</char>");
        System.err.println("	<char trans=\"\">"+c+"</char>");
      }
    }

    if (useExceptions)
        specialRulesMap = new SAXParser("res/transliteration/NepaliSpecialRules.xml").getHashMap();
    else
        specialRulesMap = new HashMap();

    System.out.println("specialRulesMap raw = " + specialRulesMap);
    for (Iterator<Entry<String,String>> ie= specialRulesMap.entrySet().iterator(); ie.hasNext(); ) {
      Entry<String,String> e = ie.next();
      if (e.getKey()==null || e.getValue()==null|| e.getKey().trim().length()==0|| e.getValue().trim().length()==0) {
        ie.remove();
        continue;
      }

      if (e.getValue().equals("suffix")) {
        suffices.add(e.getKey());
        ie.remove();
      }
    }

    //System.out.println("specialRulesMap = " + specialRulesMap);
    //System.out.println("suffices = " + suffices);
	}



  public String giveFontReplacement(String font) { return font; }

  public String convertText(String font, String dev) {
    if (dev.indexOf("विचार")!=-1) {
      System.out.println("XXX");
    }
    // Make 'modified' devanagari easier to transliterate

    // remove all Zero Width (Non) Joiner's
    dev = dev.replaceAll("["+Devanagari.ZWJ+Devanagari.ZWNJ+"]", "");

    StringBuffer rezulto = new StringBuffer(dev.length()*2);
    int pos = 0;

    // Iterate through each dev word
    Matcher m = Pattern.compile("["+ALL+"]+").matcher(dev); // search for Dev words
    while (m.find()) {
      rezulto.append(dev.substring(pos, m.start())); // append before match
      String word = m.group(); // the mached Dev word
      String orgWord = word;

      // General rule for word ending:

      if (word.equals("तर")) {
        System.out.println("XXX7");
      }

      String log = "";

      boolean dontAppendHalanta = false;

      String specialRule = specialRulesMap.get(word);
      if (specialRule != null) {
        log = word + " -> "+specialRule+" mentioned as special exception";
        word = specialRule;
        dontAppendHalanta = true;
        //System.out.println(orgWord+" -> "+word+" - "+log);
      }

      if (!dontAppendHalanta)
        for (String suf : suffices) {
          if (word.endsWith(suf)) {
            log = "Ending with suffix " + suf + " -> do nothing";
            dontAppendHalanta = true;
            //System.out.println(orgWord+" -> "+word+" - "+log);
          }
        }

      if (dontAppendHalanta) {
        ;
      } else
      if (!word.matches(".*["+CON+"]")) {
        log = "Not ending on a consonant -> do nothing";
        // This includes ending on a nazalization ["+NAZ+"]*, as it seems you cant put halanta on a consonant with nazalization??!
      } else
      if (word.endsWith(HAL)) {
        log="Already ends on halanta (so do nothing)";
        throw new IllegalStateException("This rule should never be reached.");
      } else
      if (!word.matches(".*["+CON+"].*["+CON+"].*")) {
        log = "One consonant only -> keep ending a (do nothing)";
      } else
      if (!word.matches(".*["+CON+"].*["+CON+"].*["+CON+"].*")) {
        log = "Two consonant only -> NO ending a, so append halanta";
        //System.out.println(orgWord+" -> "+word+" - "+log);
        word = word + HAL;
      } else
      if (word.matches(".*["+CON+"]"+HAL+"["+CON+"]")) {
         log="Last syllable consists of half consonant and whole consonant -> keep ending a (do nothing)";
      } else {
        log="Three or more consonants -> NO ending a, so append halanta";
        word = word + HAL;
      }

      word = simpleTransliterate(word);

      // post-processing: Putting ~ over previous vocal - or making it a n

      if (word.contains("~")) {
        word = word.replaceAll("a~", "ã");
        word = word.replaceAll("u~","ũ");
        word = word.replaceAll("i~","ĩ");
        word = word.replaceAll("o~","õ");

        //word = word.replaceAll("e~","e"); // ~ over e doesent exists ?
        //word = word.replaceAll("~","");
      }

      //System.out.println(orgWord+" -> "+word+" - "+log);

      rezulto.append(word);
      pos = m.end(); // pos just after this matched Dev word
    }

    rezulto.append(dev.substring(pos)); // append after last match

/* PROBLEMOJ

maastir	मास्तिर (ना.यो)	supren    DEVAS ESTI maastira   CXAR finagjo tira

mushkilale	मुस्किलले (क्रिवि)	apenaŭ ri  DEVAS ESTI mushkille	 CXAR finajxo le

musalamaan	मुसलमान s (ना)	muslimano vi   DEVAS ESTI musalamaan   CXAR ??? (estas kunigo de musal KAJ maan??)


     tab	तब (क्रिवि)	tiam ri   DEVAS ESTI taba

 daiv	दैव (ना)	dio ri vi


 matalab	मतलब s (ना)	signifo ri vi     matlab	 ???
 */



		return rezulto.toString();
	}

  private String simpleTransliterate(String dev) {

    StringBuffer rezulto = new StringBuffer(dev.length()*2);
    boolean warn = false;

    if (dev.contains("मिति")) {
      //System.err.println("XXX2");
    }

    // Now do transliteration - replace all recognized Devanagari chars with romanization,
    // leaving all others (so e.g. spaces, HTML incl formatting codes or whatever is intact
    for (int i=0; i<dev.length(); i++) {
      String d = dev.substring(i,i+1);

      if (d.equals(HAL) || VFL.contains(d)) { // vocal flag or halanta - make previous consonant half (delete last a)
        int l = rezulto.length();

        //if (l==0 || !CON.contains(dev.substring(i-1,i))) { // ā
        if (l==0 || ("ae".indexOf(rezulto.charAt(l-1))==-1)) { // ā
          // NOT good... we have a dangling halanta or vocal flag!
          System.err.println("Warn: Halanta or vocal flag ignored on character "+i+" in word "+dev);
          warn = true;
        } else
          rezulto.setLength(rezulto.length()-1);
      }

      if (!d.equals(HAL)) {
        String k = (String) devToRomanMap.get(d);
        if (k != null)
          rezulto.append(k); // found in transliteration table
        else if (NAZ.contains(d))
          ; // nazalisation not mentioned in XML file - delete
        else
          rezulto.append(d); // not found - leave as is
      }
    }

    if (warn) {
      System.err.println("      Transliteration became: " + rezulto);
      //throw new IllegalStateException();
    }

    return rezulto.toString();
  }

}
