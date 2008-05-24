/*
 * @(#)ExitButton.java	2006/02/23
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui.button;

import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * @author Abhishek
 * 
 */
public class ExitButton extends JButton {

    private static final long serialVersionUID = -8186821570948194653L;

    /**
     * The name of the application to be exited from.
     */
    String applicationName;
    JDialog parentFrame;

    public ExitButton(String applicationName, JDialog parentFrame) {
	setText("Exit");
	this.applicationName = applicationName;
	this.parentFrame = parentFrame;
    }

    public void closeApplication() {
	System.out.println("Exiting from " + applicationName);
	parentFrame.setVisible(false);
    }
}
