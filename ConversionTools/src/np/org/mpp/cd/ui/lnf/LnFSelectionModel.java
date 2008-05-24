/*
 * @(#)LnFSelectionModel.java	2008/04/11
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui.lnf;

import javax.swing.UIManager;

/**
 * @author Abhishek
 * 
 */
public class LnFSelectionModel {

    private String lnf;

    LnFSelectionModel() {
	setLookNFeel(UIManager.getLookAndFeel().getName());
    }

    /**
     * @return the laf
     */
    public String getLookNFeel() {
	return lnf;
    }

    /**
     * @param laf
     *                the laf to set
     */
    public void setLookNFeel(String laf) {
	this.lnf = laf;
    }

}
