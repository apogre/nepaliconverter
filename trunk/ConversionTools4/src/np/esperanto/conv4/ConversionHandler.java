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

package np.esperanto.conv4;

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
