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

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.io.*;
import np.esperanto.conv4.translit.NepaliTransliterationJacob;
import np.esperanto.conv4.ui.ConversionToolsFrame;

public class ConversionTools {

    public static void main(String[] args) throws IOException {
	  if (args.length == 0) {
		// start the GUI

		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			try {
			    UIManager.setLookAndFeel(UIManager
				    .getSystemLookAndFeelClassName());
			} catch (Exception exception) {
			    exception.printStackTrace();
			}

                new ConversionToolsFrame().setVisible(true);
		    }
		});
	  } else {
		// CLI interface
	    String arg1 = args[0].toLowerCase();
	    if (arg1.startsWith("-tr")) {
		  // Transliteration mode

		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		  NepaliTransliterationJacob tr = new NepaliTransliterationJacob("res/transliteration/NepaliALA-LC.xml", true);
		  String lin = null;
		  while ( (lin = br.readLine()) != null) {
			String outLin = tr.convertText(null, lin);
			System.out.println(outLin);
		  }

	    }
	  }
    }
}
