package np.org.mpp.conv4.f2u;

import java.util.ArrayList;
import java.util.HashMap;

import np.org.mpp.conv4.utils.Devanagari;
import java.util.HashSet;
import java.util.Arrays;

public class Font2UnicodeMapping {
	String name;

	public Font2UnicodeMapping(String name) {
		this.name = name;
	}

	static class F2Uelement {
		public String fontLetter, unicLetter;
		int priority;

		public String toString() {
			return "r" + priority + ":'" + fontLetter + "'->" + unicLetter;
		}
	}

	HashMap<String, F2Uelement> f2u = new HashMap<String, F2Uelement>();
	ArrayList<F2Uelement> elem = new ArrayList<F2Uelement>();

	int maxfontLetter = 0;

	public void addLetter(String fontLetter, String unicLetter) {
		if (fontLetter.length() == 0)
			return;
		F2Uelement e = new F2Uelement();
		e.fontLetter = fontLetter;
		e.unicLetter = unicLetter;

		e.priority = elem.size();
		elem.add(e);

		F2Uelement old = f2u.put(fontLetter, e);
		if (old != null)
			System.out.println("WARNING: Duplicate keys. Replacing " + old
					+ " with " + e);

		// System.out.println(e);

		maxfontLetter = Math.max(maxfontLetter, fontLetter.length());
	}

	/**
	 * Check table for and fix obvious errors, like missing and totally
	 * overruled entries and
	 */
	private void checkConsistency() {
		for (char c = 33; c < 255; c++) {
			if (f2u.get("" + c) == null) {
				System.out.println("WARNING: Character missing in table: \t"
						+ c + "\t" + c + "\tChar " + c + " " + (int) c + " "
						+ Integer.toHexString(c));
				addLetter("" + c, "MISSING CHARACTER " + c+ " ");
			}
		}

		for (F2Uelement e : f2u.values()) {
			if (e.fontLetter.length() > 1) {
				F2Uelement eSingleLetter = f2u.get(e.fontLetter.substring(0, 1));
				if (eSingleLetter != null && eSingleLetter.priority > e.priority) {
					System.out.println("WARNING: Rule " + e
									+ " would never be used as it has lower priority than rule "
									+ eSingleLetter);
					e.priority = 999;
					System.out.println("         Changed to " + e	+ " to repair this");
				}
			}
		}
	}

	String NONSPAC = Devanagari.types[Character.NON_SPACING_MARK];
  HashSet<String> FONT_NONSPAC = new HashSet<String>();

  String BACKSCAN_MARKS = "";

  public void init() {
    checkConsistency();

    // Pre-processinhg of BACKSCAN directive: ensure a place for the char to match
    for (F2Uelement e : f2u.values()) {
        if ("BACKSCAN".equals(e.unicLetter)) {
            BACKSCAN_MARKS = BACKSCAN_MARKS + e.fontLetter;
        }

        if (e.unicLetter.length()==1 && e.fontLetter.length()==1 && NONSPAC.indexOf(e.unicLetter)!= -1) {
            FONT_NONSPAC.add(e.fontLetter);
            System.out.println("added to FONT_NONSPAC = " + e+ " char no "+(int) e.unicLetter.charAt(0));
        }
    }

    if (!BACKSCAN_MARKS.equals("m")) {
        new IllegalStateException("BACKSCAN_MARKS='m' for now").printStackTrace();
    }
  }



	public String toUnicode(String input) {
    if (input==null || input.length() == 0) return input;

    input = processBackscan(input);
    input = removeDuplicatesAndReorderNonspacingMarks(input);

    StringBuilder sb = new StringBuilder(input.length());

		// replace all with Unicode
		int i = 0;
		while (i < input.length()) {
			F2Uelement e = null;

			if (input.charAt(i) == '<') {
				// System.err.println("XXX");
			}

			for (int j = 1; j < maxfontLetter && j + i <= input.length(); j++) {
				F2Uelement e2 = f2u.get(input.substring(i, i + j));
				if (e2 != null) {
					if (e == null)
						e = e2;
					else if (e2.priority > e.priority)
						e = e2; // last elements in table have highest priority
				}
			}

			if (e != null) {
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

    // Jacob TODO: 82% of CPU time is spent here, and 63% CPU is used on compiling
    // patterns. Caching the patterns would give a *2 or more speedup



		// put the ii ि after the following consonant
		String ii = "ि";

		// String allConsonantsWithPunct = "([" + Devanagari.NAZALIZATIONS
		// +"]*["+ Devanagari.CONSONANTS+"])";

		String allConsonantsWithPunct = "([" + Devanagari.NAZALIZATIONS + "]*["
				+ Devanagari.CONSONANTS + "]" + "(" + Devanagari.HALANTA + "["
				+ Devanagari.CONSONANTS + "])*)";
		s = s.replaceAll(ii + allConsonantsWithPunct, "$1" + ii);

		// put all nazalizations after the vocal flags
		s = s.replaceAll("([" + Devanagari.NAZALIZATIONS + "]+)(["
				+ Devanagari.VOCALFLAGS + "]+)", "$2$1");


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
            F2Uelement e = f2u.get(keyToLookFor); // TODO also 3, 4 chars
            if (e == null) {
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
                } else if (f2u.get(""+c0).unicLetter.compareTo(f2u.get(""+c1).unicLetter)<0) {
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

      if (!orgInput.equals(input))
          System.out.println("removeDuplicateNonspacingMarks("+orgInput +" -> "+input);
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
