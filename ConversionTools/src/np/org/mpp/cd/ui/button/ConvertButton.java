/*
 * @(#)ConvertButton.java	2.100 2008/02/23
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */

package np.org.mpp.cd.ui.button;

import javax.swing.JButton;

import np.org.mpp.cd.exception.FileNotSelectedException;
import np.org.mpp.cd.u2f.UnicodeToFontGUI;

/**
 * An implementation of a button used for conversion.
 * 
 * @author Abhishek Shrestha
 * 
 */
public class ConvertButton extends JButton {
    private static final long serialVersionUID = 5817105319931956393L;

    /**
     * Creates a button set with the specified text.
     * 
     * @param text
     *                The text on the button.
     */
    public ConvertButton(String text) {
	setText(text);
    }

    /**
     * Starts the conversion as specified by the <b>isReadyToConvert.</b>
     * 
     * @param isReadyToConvert
     *                conversion is proceeded if set true<br>
     *                else {@link FileNotSelectedException} is thrown.
     * @throws FileNotSelectedException
     *                 is thrown if <b> isReadyToConvert </b> is set to false.
     * 
     */
    public void startConversion(boolean isReadyToConvert)
	    throws FileNotSelectedException {
	if (isReadyToConvert) {
	    // call the convert function; and prompt the user
	    System.out.println("Ready for conversion!");
	    return;
	} else {
	    String errorMessage = new String(
		    "No file has been selected for conversion.\nPlease select a file with ."
			    + UnicodeToFontGUI.VALID_EXTENSION
			    + " extension.\nShow browse dialog?");
	    throw new FileNotSelectedException(errorMessage);
	}
    }
}
