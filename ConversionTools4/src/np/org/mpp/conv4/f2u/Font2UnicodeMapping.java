package np.org.mpp.conv4.f2u;

import java.awt.Font;
import java.util.*;

import np.org.mpp.conv4.utils.Devanagari;

public class Font2UnicodeMapping {
	String name;

	public Font2UnicodeMapping(String name) {
		this.name = name;
	}

	static class F2Uelement {
		public String fontLetter, unicLetter;
		int priority;
    boolean used;

		public String toString() {
			return "r" + priority + ":'" + fontLetter + "'->" + unicLetter;
		}
	}

	HashMap<String, F2Uelement> fontLetter_to_F2UElement = new LinkedHashMap<String, F2Uelement>();
	ArrayList<F2Uelement> elem = new ArrayList<F2Uelement>();

	int maxfontLetter = 0;
  int lineNr = 0;

	public void addLetter(String fontLetter, String unicLetter) {
    lineNr++;


    if (fontLetter == null)	return;
		if (fontLetter.length() == 0)	return;

    // BUGFIX - spreadsheetreader dont handle <table:table-cell table:number-columns-repeated="2"> !!!
    if (unicLetter == null)	unicLetter=fontLetter;

    if (unicLetter.equalsIgnoreCase("unicode")) return;


    if (unicLetter.trim().equalsIgnoreCase("NONSPAC")) {
        for (int i=1; i<fontLetter.length(); i++) {
            String s = fontLetter.substring(i-1,i).trim();
            if (s.length()>0) FONT_NONSPAC.add(s);
        }
        return;
    }

		F2Uelement e = new F2Uelement();
		e.fontLetter = fontLetter;
		e.unicLetter = unicLetter;

		e.priority = lineNr; //elem.size()+10;
		elem.add(e);

		F2Uelement old = fontLetter_to_F2UElement.put(e.fontLetter, e);
    /*
    char[] ca = fontLetter.toCharArray();
    Arrays.sort(ca);
    if (ca[ca.length-1] >255) {
      System.out.print(lineNr+" WARNING: all chars not in range 0-255 for " + e + " so it might not be used: ");
      ca = fontLetter.toCharArray();
      for (char c : ca) {
          System.out.print(" '"+c+"' (\\u"+Integer.toHexString(c)+")");
      }
      System.out.println();
    }
    */
		if (old != null) {
      if (e.unicLetter.equals(old.unicLetter))
          System.out.println(lineNr + " WARNING: This key comes two times: " + old + " with " + e);
      else
          System.out.println(lineNr + " WARNING: Conflicting keys. Replacing " + old + " with " + e);
    }
		// System.out.println(e);

		maxfontLetter = Math.max(maxfontLetter, fontLetter.length());
	}

	/**
	 * Check table for and fix obvious errors, like missing and totally
	 * overruled entries and
	 */
	public void checkConsistency(Font font) {
    if (font != null) {
        // Clear usage bit for one-letter mapping chars
       	for (F2Uelement e : fontLetter_to_F2UElement.values()) e.used = (e.fontLetter.length()!=1);

        for (char c = 33; c < 256*256-1; c++) if (font.canDisplay(c)) {
          F2Uelement e = fontLetter_to_F2UElement.get("" + c);
          if (e == null) {
            System.out.println("WARNING: Character missing in table: \t" + c + "\t"
                               + c + "\tChar " + c + " " + hex(c));
            //addLetter("" + c, "MISSING CHARACTER " + hex(c));
          } else e.used = true;
        }

        printUsage();
    }

		for (F2Uelement e : fontLetter_to_F2UElement.values()) {
			if (e.fontLetter.length() > 1) {
				F2Uelement eSingleLetter = fontLetter_to_F2UElement.get(e.fontLetter.substring(0, 1));
				if (eSingleLetter != null && eSingleLetter.priority > e.priority) {
					System.out.println("WARNING: Rule " + e
									+ " would never be used as it has lower priority than rule "
									+ eSingleLetter);
					e.priority = 999;
					System.out.println("         Changed to " + e	+ " to repair this");
				}
			}
		}

    //System.out.println("maxfontLetter = " + maxfontLetter);
	}

  public static String hex(char c) {
      return (int) c + "(\\u" + Integer.toHexString(c) + ")";
  }

  public static String hex(String s) {
      if (s==null) return null;
      StringBuilder r = new StringBuilder(s.length()*6);
      for (int i=0; i<s.length(); i++) {
          r.append(hex(s.charAt(i))).append(' ');
      }
      return r.toString();
  }


    /**
   * Print out table entries which have not been used. For testing putposes
   */
  public void printUsage() {
      for (F2Uelement e : fontLetter_to_F2UElement.values()) {
        if (!e.used) {
          System.out.println("This element was not used during test: " + e);
        }
        e.used = false;
      }
  }


	String NONSPAC = Devanagari.types[Character.NON_SPACING_MARK];
  HashSet<String> FONT_NONSPAC = new HashSet<String>();

  String BACKSCAN_MARKS = "";

  public void init() {
    checkConsistency(null);

    // Pre-processinhg of BACKSCAN directive: ensure a place for the char to match
    for (F2Uelement e : fontLetter_to_F2UElement.values()) {
        if ("BACKSCAN".equals(e.unicLetter)) {
            BACKSCAN_MARKS = BACKSCAN_MARKS + e.fontLetter;
            e.used = true;
        }
    }

    if (!BACKSCAN_MARKS.equals("m")) {
      new IllegalStateException("BACKSCAN_MARKS='m' for now").printStackTrace();
    }


    // Pre-processinhg of NONSPAC directive: swap noncpacing characters
    if (FONT_NONSPAC.size()==0) {
      System.out.println("Warning: FONT_NONSPAC list not found, trying to autogenerate it from mapping");

      for (F2Uelement e : fontLetter_to_F2UElement.values()) {
        if (e.unicLetter.length() == 1 && e.fontLetter.length() == 1 && NONSPAC.indexOf(e.unicLetter) != -1) {
          FONT_NONSPAC.add(e.fontLetter);
          System.out.println("added to FONT_NONSPAC = " + e + " char no " + (int) e.unicLetter.charAt(0));
        }
      }

      // It also seems that for Preeti { (R halanta) is a non-spacing mark for which duplicates needs to be removed.
      {
        F2Uelement e = fontLetter_to_F2UElement.get("{");
        FONT_NONSPAC.add(e.fontLetter);
        System.out.println("added to FONT_NONSPAC = " + e + " char no " + (int) e.unicLetter.charAt(0));
      }

      System.out.println("         Please add entry " +FONT_NONSPAC.toString().replaceAll(", ","  ")+ " NONSPAC to spreadsheet");

    }

  }



	public String toUnicode(String input) {
    if (input==null || input.length() == 0) return input;

    input = processBackscan(input);
    input = removeDuplicatesAndReorderNonspacingMarks(input);

    StringBuilder output = new StringBuilder(input.length());

		// replace all with Unicode
		int i = 0;
		while (i < input.length()) {
			F2Uelement e = null;


			for (int j = 1; j < maxfontLetter && j + i <= input.length(); j++) {
				F2Uelement e2 = fontLetter_to_F2UElement.get(input.substring(i, i + j));
				if (e2 != null) {
					if (e == null)
						e = e2;
					else if (e2.priority > e.priority)
						e = e2; // last elements in table have highest priority
				}
			}

			if (e != null) {
				// found a transscription
				output.append(e.unicLetter);
        e.used = true;
				i = i + e.fontLetter.length();
			} else {
				// none found; add original letter
				output.append(input.charAt(i));
				i++;
			}
		}

		String s = output.toString();

    // Jacob TODO: 82% of CPU time is spent here, and 63% CPU is used on compiling
    // patterns. Caching the patterns would give a *2 or more speedup



		// put the i ि after the following consonant
		String i_raswa = "ि";

		// String allConsonantsWithPunct = "([" + Devanagari.NAZALIZATIONS
		// +"]*["+ Devanagari.CONSONANTS+"])";

		String allConsonantsWithPunct = "([" + Devanagari.NAZALIZATIONS + "]*["
				+ Devanagari.CONSONANTS + "]" + "(" + Devanagari.HALANTA + "["
				+ Devanagari.CONSONANTS + "])*)";
		s = s.replaceAll(i_raswa + allConsonantsWithPunct, "$1" + i_raswa);

		// put all nazalizations after the vocal flags
		s = s.replaceAll("([" + Devanagari.NAZALIZATIONS + "]+)(["
				+ Devanagari.VOCALFLAGS + "]+)", "$2$1");

     // If there is many of a nonspacing character , then replace with just one
		for (int j = 0; j < NONSPAC.length(); j++)
			s = s.replaceAll(NONSPAC.charAt(j) + "+", "" + NONSPAC.charAt(j));

		// group = Consonant + navalization + flags
		String G = "([" + Devanagari.CONSONANTS + "])(["
				+ Devanagari.VOCALFLAGS + "])";

		// Swap to before the last consonant according to the -SWAP- marker
		// So f.eks. " हेन-SWAP-र् " gets " हेर्न "
		// So f.eks. " गने-SWAP-र् " gets " गर्ने "
		//s = s.replaceAll("([" + Devanagari.CONSONANTS + "][^"
		//		+ Devanagari.CONSONANTS + "]*)-SWAP-(.*?)-SWAP-", "$2$1");

		s = s.replaceAll("([" + Devanagari.CONSONANTS + "]"
				+"("+Devanagari.HALANTA+"["+Devanagari.CONSONANTS+"])*"
				+"[^"+Devanagari.CONSONANTS + "]*)-SWAP-(.*?)-SWAP-", "$3$1");



		// replace aa + e flags with o
		// This could also be done in the mapping file.
		s = s.replaceAll("ाे", "ो");
		s = s.replaceAll("आे", "ओ");

    s = s.replaceAll("ाै", "ौ");
    s = s.replaceAll("आै", "औ");


		return s;
	}

  /**
   * Pre-processinhg of BACKSCAN directive:
   * look for chars with BACKSCAN marks and ensure a place for the char to match
   * @param input String
   */
  private String processBackscan(String input) {
    String org_input = input;

    int i = 1;
    if (BACKSCAN_MARKS.indexOf(input.charAt(0)) != -1) {
      System.err.println("BACKSCAN " + BACKSCAN_MARKS + " impossible on first char: " + org_input);
    } else
      try {

        while (i < input.length()) {
          // TODO properly
          if (input.charAt(i) == 'm') {
            if (i <= 0 || input.charAt(i - 1) == ' ') {
              System.err.println("BACKSCAN failed on char " + i + " on text: " + org_input);
              input = org_input;
              break;
            }

            String keyToLookFor = input.substring(i - 1, i + 1);
            F2Uelement e = fontLetter_to_F2UElement.get(keyToLookFor); // TODO also 3, 4 chars
            if (e == null) {
                // XXX xxx BUG, infinite loop if
                // input = "o; cg'dltkqsf] k'/f c+z k9\g'';\ htt;://creativemmcoons.org/licenses/by-nc-sa/3.0/deed.eo"

              // it wasnt in thge mapping! We need to backscan!
              input = input.substring(0, i - 1)
                      + input.charAt(i) + input.charAt(i - 1)
                      + input.substring(i + 1);
              i--;
              continue;
            }
          }
          i++;
        }
      } catch (Exception e) {
        System.err.println("Pre-processinhg of BACKSCAN coding error for " + input);
        e.printStackTrace();
      }
    return input;
  }


  /**
   * Pre-processinhg: Remove duplicate non-spacing marks and reorder them
   * to avoid need of all combinations in mapping file.
   * Examples (Preeti): xf]]] -> xf]   ,æsn]]hsf -> ,æsn]hsf     xF'b} -> x'Fb}
   * u/+] -> u/]+
   *
   * Reordering is done so this order is followed (for Preeti):
   * 'F'->ँ char no 2305  (last)
   * '+'->ं char no 2306
   * '''->ु char no 2369
   * '"'->ू char no 2370
   * '['->ृ char no 2371
   * ']'->े char no 2375
   * '}'->ै char no 2376
   * '\'->् char no 2381  (first)
   *
   * @param input String
   * @return String
   */
  private String removeDuplicatesAndReorderNonspacingMarks(String input) {
      String orgInput = input;

      int i = 1;
      while (i < input.length()) {
          int j = i;
          while (j <= input.length() && FONT_NONSPAC.contains(input.substring(j-1, j))) j++;
          if (j>i+1) {
              // we found more than 1 nonspac char
              StringBuilder sb2 = new StringBuilder(input.substring(i-1,j-1));


              int k=1; // delete dublets
              while (k<sb2.length()) {
                char c0 = sb2.charAt(k-1);
                char c1 = sb2.charAt(k);
                if (c0 == c1) {
                  sb2.deleteCharAt(k); // delete duplicate
                } else if (fontLetter_to_F2UElement.get(""+c0).unicLetter.compareTo(fontLetter_to_F2UElement.get(""+c1).unicLetter)<0) {
                  sb2.setCharAt(k-1, c1); // swap chars
                  sb2.setCharAt(k, c0);
                  k=1; // and start over (very ineffective sorting :-)
                } else {
                  k++;
                }
              }
              //System.out.println("Ainput = " + input);
              input = input.substring(0,i-1) + sb2 + input.substring(j-1);
              //System.out.println("Binput = " + input);
              //System.out.println();
          }
          i++;
      }

      //if (!orgInput.equals(input)) System.out.println("removeDuplicateNonspacingMarks("+orgInput +" -> "+input);
      return input;
  }

    public static class Identity extends Font2UnicodeMapping {
		public Identity() {
			super("identity");
		}

    public void init() {
    }

		public String toUnicode(String input) {
			return input;
		}
	}

}
