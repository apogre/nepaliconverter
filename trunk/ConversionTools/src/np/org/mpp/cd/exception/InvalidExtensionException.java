/*
 * @(#)InvalidExtensionException.java	2008/02/23
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.exception;

import javax.swing.JOptionPane;

/**
 * @author Abhishek Shrestha
 * 
 */
public class InvalidExtensionException extends Exception {
	private static final long serialVersionUID = -2072189481108408805L;

	int selectedOption;

	public InvalidExtensionException() {
		JOptionPane
				.showMessageDialog(
						null,
						"Some of the files selected have invalid extension.\nPlease select a file with .txt extension.",
						"Invalid extension!", JOptionPane.YES_NO_OPTION);
	}

	public InvalidExtensionException(String errorMessage, boolean isMessage) {
		if (isMessage) {
			JOptionPane.showMessageDialog(null, errorMessage,
					"Invalid extension!", JOptionPane.ERROR_MESSAGE);
		} else {
			selectedOption = JOptionPane.showConfirmDialog(null, errorMessage,
					"Invalid extension!", JOptionPane.YES_NO_OPTION);
		}
	}

	public int getSelectedOption() {
		return selectedOption;
	}
}
