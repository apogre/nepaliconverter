/*
 * Copyright (C) 2009  Nepala Esperanto-Asocio, http://www.esperanto.org.np/
 * Author: Jacob Nordfalk
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */
package np.esperanto.conv4.test;

import java.util.Arrays;
import java.util.HashSet;
import np.esperanto.conv4.ConversionHandler;
import java.util.HashMap;
import java.io.File;
import java.util.*;

public class LoggingConversionHandler implements ConversionHandler {
  public static void main(String[] args) {
    LoggingConversionHandler loggingconversionhandler = new LoggingConversionHandler();
  }


  HashMap<String, TreeSet<String>> fontwords = new HashMap<String, TreeSet<String>>();
  HashSet<String> fonts = new HashSet<String>();
  HashSet<String> lookForWords = new HashSet<String>(Arrays.asList(new String[] {
	//  "\\m", "\\mPd\\", "j|mflGtsf/Laf6", "m", "m\\", "", "", "",
  }));
  public File actualFile;

  /**
   * Convert text.
   * This migt be to Unicode, from Unicode or a transliteration
   * @param text The text to be converted
   * @param font The font the text is encoded in (if any)
   * @return The converted text
   */
  public String convertText(String font, String text) {
    if (text.length() == 0)
	return text;

    font = font.replaceAll("'", "").replaceAll("1", "");

    font = font.replaceAll("(.*),(.*)","$1");
    //if (font.equals("Times New Roman, Times New Roman")) font = "Times New Roman";
    //if (font.equals("Times New Roman, Times New Roman")) font = "Times New Roman";



    //System.out.println("convertText("+font+", '"+text+"')");
    fonts.add(font);

    if (text.indexOf("Ldfg") != -1) {
	;
    }

    for (String word : text.split("[ \\n\\t]")) {
	word = word.trim();
	if (word.length() <= 1)
	  continue;
	if (word.length() < 40) {
	  TreeSet<String> words = fontwords.get(font);
	  if (words == null) {
	    System.out.println("Created words for font " + font);

	    words = new TreeSet<String>();
	    try {
		//TestF2U.readTextFile(words, "test/words_" + font + ".txt");
	    } catch (Exception ex) {
	    }
	    fontwords.put(font, words);
	  }

	  words.add(word);
	}
	if (lookForWords.contains(word)) {
	  System.err.println(word + " found in " + actualFile + ": " + text);
	  //System.err.println(word+" found: "+text);
	}
    }

    return text;
  }

  /**
   * Specify which font a given font should be replaced with.
   * NOTE: This method must only be called AFTER calls to convertText(),
   * @param font The original font. Ex. "Preeti"
   * @return The replacement font. Ex. "Arial".
   * If no replacement of this font should be made the original font should be returned.
   */
  public String giveFontReplacement(String font) {
    return font;
  }

}


/*



 */
