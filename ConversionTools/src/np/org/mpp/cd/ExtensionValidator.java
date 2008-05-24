/*
 * @(#)ExtensionValidator.java	2008/04/14
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd;

import java.io.File;

import np.org.mpp.cd.exception.InvalidExtensionException;

/**
 * Provides functionality for checking the extension of a file.
 * 
 * @author Abhishek
 * 
 */
public class ExtensionValidator {
    String[] extension;

    /**
     * 
     * initailizes the valid extensions
     * 
     * @param extension
     *                the extesnsions against which other are validated upon
     */
    public ExtensionValidator(String[] extension) {
	this.extension = extension;
    }

    /**
     * checks for the validity of the extension
     * 
     * @param file
     * @return
     * @throws InvalidExtensionException
     */
    public boolean isValidExention(String file)
	    throws InvalidExtensionException {
	File filePath = new File(file);

	String fileName = filePath.getName();

	String[] fragment = file.split("\\.");
	int count = fragment.length;
	String ext = fragment[count - 1];
	boolean status = false;

	// iteratively check for all the valid extension
	for (int i = 0; i < extension.length; i++) {
	    if (ext.equals(extension[i])) {
		status = true;
		break;
	    } else {
		status = false;
	    }
	}
	if (status) {
	    return true;
	} else {
	    String message = fileName + " has invalid extension (" + ext + ").";
	    throw new InvalidExtensionException(message, true);

	}
    }
}
