/*
 * @(#)NonUniodeFont.java	2008/04/22
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.f2u.map;

import java.io.File;

import np.org.mpp.cd.f2u.FontToUnicodeGUI;

/**
 * to be extended by all the non Unicode Fonts like Preeti, Jaga Himali,
 * Kantipur, Kanchan
 * 
 * @author Abhishek Shrestha
 * 
 */
public class NonUniodeFont {
    private String inputFile;
    private String inputEncoding;
    private String outputEncoding;
    private float unitBlock;
    private int initialProgress;
    private boolean isConversionComplete;

    private final String VALID_EXTENSION = "txt";
    private final String SUFFIX = "_unicode";

    public NonUniodeFont(String[] args) {
	setInputFile(args[0]);
	setInputEncoding(args[1]);
	setOutputEncoding(args[2]);
	setUnitBlock(Float.parseFloat(args[3]));
	setInitialProgress(Integer.parseInt(args[4]));
	setConversionComplete(false);
    }

    File getFile() {
	return (new File(getInputFile()));
    }

    String getInputFile() {
	return inputFile;
    }

    String getOutputFile() {
	// seperating fileName and extension
	String[] fragment = getInputFile().split("\\.");
	// the whole path without the extension need for appendint the suffix
	String fileName = fragment[0];

	String outputFile = fileName + SUFFIX + "." + VALID_EXTENSION;
	return outputFile;
    }

    private void setInputFile(String inputFile) {
	this.inputFile = inputFile;
    }

    /**
     * returns only the file name.
     * <p>
     * Example inputFile = "usr/home/test.pdf" getFileName() returns just
     * test.pdf
     * 
     * @return
     */
    String getFileName() {
	File file = new File(getInputFile());
	return file.getName();
    }

    String getInputEncoding() {
	return inputEncoding;
    }

    private void setInputEncoding(String inputEncoding) {
	this.inputEncoding = inputEncoding;
    }

    String getOutputEncoding() {
	return outputEncoding;
    }

    private void setOutputEncoding(String outputEncoding) {
	this.outputEncoding = outputEncoding;
    }

    float getUnitBlock() {
	return unitBlock;
    }

    private void setUnitBlock(float unitBlock) {
	this.unitBlock = unitBlock;
    }

    int getInitialProgress() {
	return initialProgress;
    }

    private void setInitialProgress(int initialProgress) {
	this.initialProgress = initialProgress;
    }

    boolean isConversionComplete() {
	return isConversionComplete;
    }

    void setConversionComplete(boolean isConversionComplete) {
	this.isConversionComplete = isConversionComplete;
    }

    void updateProgressBar(float progressStatus) {
	int progress = (int) progressStatus;
	if (!isConversionComplete()) {
	    FontToUnicodeGUI.progressBar.setValue(progress);
	    FontToUnicodeGUI.progressBar.updateUI();
	    FontToUnicodeGUI.progressBar.repaint();
	}
    }
}
