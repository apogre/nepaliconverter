/*
 * @(#)FileNotSelectedException.java	2008/02/23
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.exception;

import javax.swing.JOptionPane;

public class FileNotSelectedException extends Exception {

    private static final long serialVersionUID = -2762990991217516062L;
    int selectedOption;

    public FileNotSelectedException(String errorMessage) {
	selectedOption = JOptionPane.showConfirmDialog(null, errorMessage,
		"File not specified!", JOptionPane.YES_NO_OPTION);
    }

    public int getSelectedOption() {
	return selectedOption;
    }
}
