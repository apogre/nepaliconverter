/*
 * @(#)LnFSelectionPanel.java	2008/04/11
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui.lnf;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import np.org.mpp.cd.ui.RadioPanel;

/**
 * @author Abhishek
 * 
 */
public class LnFSelectionPanel extends JPanel {

    private static final long serialVersionUID = -739505877917064869L;

    // stores the Look & Feel available in the system
    LnFSelectionModel model;
    String lnf;
    static RadioPanel lnfPanel;

    public LnFSelectionPanel(LnFSelectionModel model) {
	this.model = model;
	lnf = model.getLookNFeel();
	// FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
	setLayout(null);

	JLabel lblRequestEnglish = new JLabel(
		"Please select a theme from below:");
	lblRequestEnglish.setBounds(5, 5, 230, 25);
	add(lblRequestEnglish);
	lnfPanel = new RadioPanel(LnF.getThemes());
	add(lnfPanel);
	lnfPanel.setBounds(5, 30, 190, 140);
	lnfPanel.setBorder(new TitledBorder("Look&Feel"));
    }
}
