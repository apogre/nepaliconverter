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
package np.org.mpp.old_f2u;

import java.util.HashSet;
import np.esperanto.conv4.ConversionHandler;



public class Old_F2UConversionHandler implements ConversionHandler {

  MppFont2Unicode f2u = new MppFont2Unicode();

  public Old_F2UConversionHandler() {
      f2u.interactive = false;
      f2u.silent = true;
      f2u.justStripSpaces = true;
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
