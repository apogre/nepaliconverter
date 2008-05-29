package np.org.npp.conv.string2;

import java.util.*;

public class Font2UnicodeMapping {
  String name;

  public Font2UnicodeMapping(String name) {
    this.name = name;
  }

  class Element {
    public String fontLetter, unicLetter;
    int elemNr;

    public String toString() {
      return "r"+elemNr+":'"+fontLetter + "'->"+unicLetter;
    }
  }

  HashMap<String,Element> f2u = new HashMap<String,Element>();
  ArrayList<Element> elem = new ArrayList<Element>();

  int maxfontLetter = 0;

  public void addLetter(String fontLetter, String unicLetter) {
    if (fontLetter.length()==0) return;
    Element e = new Element();
    e.fontLetter = fontLetter;
    e.unicLetter = unicLetter;

    e.elemNr = elem.size();
    elem.add(e);

    Element old = f2u.put(fontLetter,e);
    if (old != null) System.out.println("WARNING: Duplicate keys. Replacing "+old+" with "+e);

    //System.out.println(e);

    maxfontLetter = Math.max(maxfontLetter, fontLetter.length());
  }

  public void checkConsistency() {
    for (char c=33; c<255; c++) {
      /*
      if (c == 'l') continue; // ि
      if (c == 109) continue; // ्क
      if (c >= 128 && c<=159) continue;
      if (c == 165 ) continue; //
      if (c == 171 ) continue; //
      if (c == 176 ) continue; //
      if (c == 212 ) continue; //
      if (c == 216 ) continue; //
      */
      if (f2u.get(""+c)==null) {
        System.out.println("WARNING: Character missing in table: \t"+c+"\t"+c+"\tChar "+c+" "+(int)c+" "+Integer.toHexString(c));
      }
    }

    for (Element e : f2u.values()) {
      if (e.fontLetter.length()>1) {
        Element eSingleLetter = f2u.get(e.fontLetter.substring(0,1));
        if (eSingleLetter.elemNr > e.elemNr) {
          System.out.println("WARNING: Rule "+e+" will never be used as it is before general rule "+eSingleLetter);
          e.elemNr = 999;
          System.out.println("         Changed to "+e+" to repair this");
        }
      }
    }
  }


  String NONSPAC = Devanagari.types[Character.NON_SPACING_MARK];

  public String toUnicode(String input) {
    StringBuffer sb = new StringBuffer(input.length());

    // replace all with Unicode
    int i=0;
    while (i<input.length()) {
      Element e=null;

      if (input.charAt(i)=='<') {
        //System.err.println("XXX");
      }

      for (int j=1; j<maxfontLetter && j+i<=input.length(); j++) {
        Element e2 = f2u.get( input.substring(i, i+j) );
        if (e2 != null) {
          if (e==null) e = e2;
          else if (e2.elemNr>e.elemNr) e = e2; // last elements in table have highest priority
        }
      }

      if (e!=null) {
        // found a transscription
        sb.append(e.unicLetter);
        i = i + e.fontLetter.length();
      } else {
        // none found; add original letter
        sb.append(input.charAt(i));
        i++;
      }
    }


    String s = sb.toString();

    // put the ii ि  after the following consonant
    String ii = "ि";
    String allConsonantsWithPunct = "([" + Devanagari.NAZALIZATIONS +"]*["+ Devanagari.CONSONANTS+"])";
    s = s.replaceAll( ii + allConsonantsWithPunct, "$1"+ii );

    // put all nazalizations after the vocal flags
    s = s.replaceAll( "([" + Devanagari.NAZALIZATIONS +"]+)(["+ Devanagari.VOCALFLAGS+"]+)", "$2$1" );



    // remove all duplicate flags and other non-spacing signs
    for (int j=0; j<NONSPAC.length(); j++) s = s.replaceAll(NONSPAC.charAt(j)+"+",NONSPAC.substring(j,j+1));

    // replace aa + e flags with o
    // This could also be done in the mapping file.
    s = s.replaceAll("ाे","ो");

    return s;
  }
}
