/*
 * @(#)LnF.java	2008/04/11
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui.lnf;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * @author Abhishek
 * 
 */
public class LnF {
	public static final LookAndFeelInfo[] laf = UIManager
			.getInstalledLookAndFeels();
	public static String[] theme = new String[laf.length];;
	public static LnFSelectionModel lnfModel = new LnFSelectionModel();

	public LnF(String lnf) {
		lnfModel.setLookNFeel(lnf);
	}

	public static String[] getThemes() {
		for (int i = 0; i < laf.length; i++) {
			theme[i] = new String(laf[i].getName());
		}
		return theme;
	}
}
