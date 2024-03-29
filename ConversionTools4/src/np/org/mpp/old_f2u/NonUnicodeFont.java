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

import java.util.*;
import java.nio.charset.*;

public abstract class NonUnicodeFont {

  public abstract void toUnicode(StringBuffer output, String input);

  public boolean workAroundWrongEncodingSchemeInWindows = true;
  private static final Charset windows1252 = Charset.forName("windows-1252");
  private static final Charset iso_8859_1 = Charset.forName("ISO-8859-1");

  public String toUnicode(String input) {

    if (input.indexOf("´G8f")!=-1) {
      System.err.println("NU!"+input);
    }


    input = input.replaceAll("]]","]");  // Two े 's on top of each other

    // Esperanto-vortaro error corrections
    input = input.replaceAll("∆", "e"); // half jh letter not correct, so use other jh letter
    input = input.replaceAll("x “","x“");
    input = input.replaceAll("o./","of/");

    input = input.replaceAll("∆", "e"); // half jh letter not correct, so use other jh letter


    if (workAroundWrongEncodingSchemeInWindows) {
      // The following fix is needed becaurse the font converters supplied by MMS
      // does NOT work correctly. They seem to be designed to work only on text
      // saved in "windows-1252" encoding and then wrongly loaded as "ISO-8859-1".
      // See http://ascii-table.com/ansi-codes.php#note
      // To make the encoders work on correctly encoded text we have to do the same
      // encode in a "windows-1252" byte array and then decode is as if it was "ISO-8859-1":

      //System.out.println(input);

      input = iso_8859_1.decode(windows1252.encode(input)).toString();

      //System.out.println(input);
      //input.getBytes("windows-1252")


      // Further the character Ô is translated wrongly.
      // We solve this replacing "Ô" with "If" which looks similar and thus is converted
      // correctly to Unicode on all the converters
      input = input.replaceAll("Ô","If");



      // ls{dt{Joljd'b -lj_
      // ls{dt{Joljd'b -lj_
    }





    StringBuffer sb = new StringBuffer(input.length() + 5);
    toUnicode(sb, input);
    return sb.toString();
  }

  public final ArrayList problemsInLastConversion = new ArrayList();

}
