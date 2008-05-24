/*
 * @(#)UnicodifyMain.java	2006/02/07
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui;

import javax.swing.JFrame;

import np.org.mpp.cd.f2u.FontToUnicodeGUI;
import np.org.mpp.cd.u2f.UnicodeToFontGUI;

/**
 * It contains the main class and instantiates the {@link JFrame} to include all
 * the necessary panels.
 * 
 * @author Abhishek
 * 
 */
public class Frame extends DialogFrame {

    private static final long serialVersionUID = 41795019483578491L;

    private static final int WIDTH = 525;
    private static final int HEIGHT = 135;

    public Frame(Menu owner, String title, String icon) {
	super(owner, title, false, 528, 137, icon);
	getRootPane().setDefaultButton(
		np.org.mpp.cd.u2f.UnicodeToFontGUI.convert);
    }

    public void createAndShowGUI(boolean isFontToUnicode) {
	// Create and set up the window.
	if (isFontToUnicode) {
	    FontToUnicodeGUI f2u = new FontToUnicodeGUI(this);
	    f2u.setBounds(0, 0, WIDTH, HEIGHT);
	    add(f2u);
	} else {
	    UnicodeToFontGUI u2f = new UnicodeToFontGUI(this);
	    u2f.setBounds(0, 0, WIDTH, HEIGHT);
	    add(u2f);
	}

	super.createAndShowGUI();
    }
}
