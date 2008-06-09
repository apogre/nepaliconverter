/*
 * @(#)FileNotSelectedException.java	2008/02/23
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.exception;

public class FileNotSelectedException extends Exception {

    private static final long serialVersionUID = -2762990991217516062L;
    int selectedOption;

    public FileNotSelectedException(String errorMessage) {
	System.err.println("File not specified!" + errorMessage);
    }

    public int getSelectedOption() {
	return selectedOption;
    }
}
