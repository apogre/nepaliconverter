/*
 * @(#)BrowseButton.java	2008/02/23
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui.button;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import np.org.mpp.cd.exception.InvalidExtensionException;

public class BrowseButton extends JButton {
	private static final long serialVersionUID = 484431608851593984L;

	private String validFilePath = null;

	private String outputFilePath = null;

	private final String VALID_EXTENSION;

	private final String SUFFIX;

	public BrowseButton(String name, String extension, String suffix) {
		setText(name);
		VALID_EXTENSION = extension;
		SUFFIX = suffix;
	}

	public void showBroseDialog() throws InvalidExtensionException {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*."
				+ VALID_EXTENSION.toString(), VALID_EXTENSION.toString());
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(getParent());
		String file = "";
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// get the name of the file(with extension)
			file = fileChooser.getSelectedFile().getName();
			String parentPath = fileChooser.getSelectedFile().getParent();
			validFilePath = fileChooser.getSelectedFile().getPath();

			String[] fragment = file.split("\\.");
			int count = fragment.length;
			String fileName = fragment[0];
			String ext = fragment[count - 1];

			if (ext.equals(VALID_EXTENSION)) {
				outputFilePath = parentPath + "/" + fileName + SUFFIX + "."
						+ VALID_EXTENSION;
			} else {
				outputFilePath = "";
				validFilePath = "";
				StringBuffer errorMessage = new StringBuffer(
						"."
								+ ext
								+ " extension is not supported.\nPlease select a file with ."
								+ VALID_EXTENSION
								+ " extension.\nShow browse dialog?");
				throw new InvalidExtensionException(errorMessage.toString(),
						false);
			}
		}
	}

	/**
	 * Returns the valid file path obtained through the browse dialog if
	 * <b>isValidFile</b> is set true else throws {@link NullPointerException}.
	 * 
	 * @return a string representation of a valid file path.
	 * @throws NullPointerException
	 *             if <b>isValidFile</b> is false.
	 */
	public String getValidFilePath() {
		return validFilePath;
	}

	/**
	 * @return the outputFilePath
	 */
	public String getOutputFilePath() {
		return outputFilePath;
	}
}
