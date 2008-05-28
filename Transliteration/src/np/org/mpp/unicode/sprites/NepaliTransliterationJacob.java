package np.org.mpp.unicode.sprites;

import java.util.*;
import java.util.regex.*;

import np.org.mpp.tools.conversion.xml.*;
import np.org.npp.conv.string2.*;
import vortaro.*;

public class NepaliTransliterationJacob implements Transliterator {

  HashMap<String, String> hashMap;

  String ALL = Devanagari.ALL;
  String CON = Devanagari.CONSONANTS;
  String VFL = Devanagari.VOCALFLAGS;
  String NAZ = Devanagari.NAZALIZATIONS;
  String HAL = Devanagari.HALANTA; // "्"; // Halanta

	public NepaliTransliterationJacob() {
    SAXParser parser = new SAXParser("res/NepaliJacob.xml");
    hashMap = parser.getHashMap();

    String checkChars = ALL.replaceAll(HAL,"");
    for (int i=0; i<checkChars.length(); i++) {
      String c = ""+checkChars.charAt(i);
      if (hashMap.get(c)==null) {
        System.err.println("Missing devanagari character in map: "+c);
        System.err.println("	<char trans=\""+DevAlRomana.m.get(c)+"\">"+c+"</char>");
      }
    }
	}



	public String transliterate(String dev) {

    // Make 'modified' devanagari easier to transliterate



    StringBuffer rezulto = new StringBuffer(dev.length()*2);
    int pos = 0;

    // Iterate through each Dev word
    Matcher m = Pattern.compile("["+ALL+"]+").matcher(dev); // search for Dev words
    while (m.find()) {
      rezulto.append(dev.substring(pos, m.start())); // append before match
      String word = m.group(); // the mached Dev word

      // General rule for word ending:
      // Explicit mention in translit table


      /*
      if (!word.matches(".*["+CON+"]")) {
        // Not ending on a consonant -> do nothing
        // This includes ending on a nazalization ["+NAZ+"]*, as it seems you cant put halanta on a consonant with nazalization??!
      } else
      if (!word.matches(".*[\\"+CON+"].*[\\"+CON+"].*")) {
        // One consonant only -> ending a (do nothing)
      } else
      if (word.endsWith(HAL)) {
        // Already ends on halanta (so do nothing)
      } else
      if (!word.matches(".*["+CON+"]"+HAL+"["+CON+"]")) {
        // Last syllable consists of half consonant and whole consonant -> ending a  (do nothing)
      } else {
        // Three or more consonants -> NO ending a, so append halanta
        word = word + HAL;
      }
*/


      word = simpleTransliterate(word);

      rezulto.append(word);
      pos = m.end(); // pos just after this matched Dev word
    }



		return rezulto.toString();
	}

  private String simpleTransliterate(String dev) {

    StringBuffer rezulto = new StringBuffer(dev.length()*2);

    if (dev.contains("मिति")) {
      //System.err.println("XXX2");
    }

    // Now do transliteration - replace all recognized Devanagari chars with romanization,
    // leaving all others (so e.g. spaces, HTML incl formatting codes or whatever is intact
    for (int i=0; i<dev.length(); i++) {
      String d = dev.substring(i,i+1);

      if (d.equals(HAL) || VFL.contains(d)) { // vocal flag or halanta - make previous consonant half (delete last a)
        int l = rezulto.length();

        if (l==0 || rezulto.charAt(l-1) != 'a') { // ā
          // NOT good... we have a dangling halanta or vocal flag!
          System.err.println("Warn: Halanta or vocal flag ignored on character "+i+" in word "+dev);
        } else
          rezulto.setLength(rezulto.length()-1);
      }

      if (!d.equals(HAL)) {
        String k = (String) hashMap.get(d);
        if (k != null)
          rezulto.append(k); // found in transliteration table
        else if (NAZ.contains(d))
          ; // nazalisation not mentioned in XML file - delete
        else
          rezulto.append(d); // not found - leave as is
      }
    }

    return rezulto.toString();
  }
}
