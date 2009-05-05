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
package np.esperanto.conv4.font2unicode;

import java.util.HashMap;
import java.util.HashSet;

import np.esperanto.conv4.ConversionHandler;



public class F2UConversionHandler implements ConversionHandler {

  private HashMap<String,Font2UnicodeMapping> mappings = new HashMap<String,Font2UnicodeMapping>();

  private Font2UnicodeMappingFactory f2uFactory = new Font2UnicodeMappingFactory();

  public static final Font2UnicodeMapping identity = new Font2UnicodeMapping.Identity();


  public F2UConversionHandler() {
    mappings.put("arial", identity);
    mappings.put("arial narrow", identity);
    mappings.put("helvetica", identity);
    mappings.put("palatino", identity);
    setStandardFontReplacement("Kalimati");
    //setStandardFontReplacement("ArialMT");
  }


  /**
   * Get an appropriate mapping for a given font name
   * @param font The font name, ex. "Preeti"
   * @return a Font2UnicodeMapping for that font. If no mapping could be found
   * the identity mapping will be returned
   * (thus not convertint e.g. Arial and Times New Roman)
   */
  public Font2UnicodeMapping findMapping(String font) {
    Font2UnicodeMapping mapping = mappings.get(font);

    if (mapping == null) try {
      font = font.toLowerCase().replaceAll("[0..9]", "");
      mapping = mappings.get(font);
      if (mapping == null) {
        mapping = f2uFactory.getMapping(font.toLowerCase().replaceAll("[0..9]", ""));
      }
    } catch (Exception e) {
      //e.printStackTrace();
      System.err.println(e);
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
      
      //System.err.println("fontsConverted = " + fontsConverted);
    }
    return text2;
  }

  private String standardFontReplacement = "Arial";
  
  public void setStandardFontReplacement(String f) {
    standardFontReplacement = f;
    mappings.put(f.toLowerCase(), identity);    
  }

      
      
  public String giveFontReplacement(String font) {
    if (fontsConverted.contains(font)) {
      return standardFontReplacement;
    } else {
      return font;
    }
  }

}
