/*
 * @(#)DnDTextBox.java	2008/02/12
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.TransferHandler;

import np.org.mpp.cd.ExtensionValidator;
import np.org.mpp.cd.exception.InvalidExtensionException;

public class DnDTextBox extends JScrollPane {
    private static final long serialVersionUID = -1120006674586479543L;
    static JList fileList;

    public DnDTextBox() {
	super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	fileList = new JList();

	this.getViewport().setView(fileList);
	this.setTransferHandler(new FileTransferHandler()); // see below
    }

    public String[] getFiles() {
	ListModel model = fileList.getModel();
	String[] files = new String[model.getSize()];

	for (int i = 0; i < model.getSize(); i++) {
	    files[i] = (String) model.getElementAt(i);
	}
	return files;
    }

    public void setFile(Object[] obj) {
	fileList.removeAll();
	fileList.setListData(obj);
    }
}

/**
 * Non-public class to handle filename drops
 */
class FileTransferHandler extends TransferHandler {
    private static final long serialVersionUID = -5228266412248668237L;

    public boolean canImport(JComponent arg0, DataFlavor[] arg1) {
	for (int i = 0; i < arg1.length; i++) {
	    DataFlavor flavor = arg1[i];
	    // for windows system
	    if (flavor.equals(DataFlavor.javaFileListFlavor)) {
		return true;
	    } else if (flavor.equals(DataFlavor.stringFlavor)) {
		// for linux system
		return true;
	    }
	}
	// Didn't find any that match, so:
	return false;
    }

    /**
     * Do the actual import.
     */
    public boolean importData(JComponent comp, Transferable t) {
	String[] ext = { "txt" };
	ExtensionValidator validator = new ExtensionValidator(ext);
	DataFlavor[] flavors = t.getTransferDataFlavors();
	for (int i = 0; i < flavors.length; i++) {
	    DataFlavor flavor = flavors[i];
	    try {
		if (flavor.equals(DataFlavor.javaFileListFlavor)) {

		    List<Object> l = (List) t
			    .getTransferData(DataFlavor.javaFileListFlavor);
		    Iterator<Object> iter = l.iterator();

		    // keeps the list of files with valid
		    // extension
		    Vector<String> list = new Vector<String>(0);

		    while (iter.hasNext()) {
			File file = (File) iter.next();
			System.out.println("Analyzing " + ": "
				+ file.getCanonicalPath());
			try {
			    if (validator.isValidExention(file
				    .getCanonicalPath())) {
				System.out.println("valid ext:"
					+ file.getCanonicalPath());
				list.addElement(file.getCanonicalPath());
			    }
			} catch (InvalidExtensionException e) {
			    System.out.println("Invalid extension");
			}
		    }
		    // set the files on the list
		    System.out.println("list size: " + list.size());
		    DnDTextBox.fileList.setListData(list);

		    return true;
		} else if (flavor.equals(DataFlavor.stringFlavor)) {
		    String fileOrURL = (String) t.getTransferData(flavor);
		    try {
			URL url = new URL(fileOrURL);
			String strUrl = url.toString();
			// strip off the "file:" from
			// the string
			strUrl = strUrl.substring(5, strUrl.length());
			Object[] lst = { strUrl };
			DnDTextBox.fileList.setListData(lst);

			return true;
		    } catch (MalformedURLException ex) {
			return false;
		    }
		}
	    } catch (IOException ex) {
		System.err.println("IOError getting data: " + ex);
	    } catch (UnsupportedFlavorException e) {
		System.err.println("Unsupported Flavor: " + e);
	    }
	}
	Toolkit.getDefaultToolkit().beep();
	return false;
    }
}
