/*
 * @(#)ConversionTools.java	2008/04/15
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import np.org.mpp.cd.ui.Menu;
import np.org.mpp.cd.ui.lnf.LnF;

/**
 * The file containing the main function.
 *
 * @author Abhishek Shrestha
 *
 */
public class ConversionTools {
	public ConversionTools() {
		File file = new File("res/lnf/config.lnf");
                System.out.println(file.getAbsolutePath());
		String storedTheme = null;
		try {
			Scanner scanner = new Scanner(file);
			if (scanner.hasNextLine()) {
				storedTheme = scanner.nextLine();
			}
			System.out.println("Theme found in config.lnf: " + storedTheme);
		} catch (FileNotFoundException e) {
			/*
			 * if no file exists or no theme is stored in the file then get the
			 * defulat look n feel
			 */
			storedTheme = UIManager.getLookAndFeel().getName();
			System.out
					.println("No theme found in the config.lnf. Using the default system theme");
		}

		new LnF(storedTheme);
		Menu menu = new Menu();
		try {
			UIManager.setLookAndFeel(storedTheme);
			SwingUtilities.updateComponentTreeUI(menu);
			SwingUtilities.updateComponentTreeUI(menu);
		} catch (Exception x) {
			System.err.println("Sorry, could not install the requested theme!");
		}
		menu.setVisible(true);
	}

	public static void main(String[] args) {
		new ConversionTools();
	}
}
