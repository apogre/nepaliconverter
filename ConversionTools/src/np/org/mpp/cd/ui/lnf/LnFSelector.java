/*
 * @(#)LnFSelector.java	2008/04/11
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.ui.lnf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import np.org.mpp.cd.ui.DialogFrame;
import np.org.mpp.cd.ui.Menu;

/**
 * @author Abhishek
 * 
 */
public class LnFSelector extends DialogFrame implements ActionListener {
	private static final long serialVersionUID = 8856487134759341224L;

	private JButton btnOK;
	private JButton btnCancel;
	static LnFSelectionModel model;
	static LnFSelectionPanel lnfPanel;
	Menu menu;

	public LnFSelector(LnFSelectionModel model, Menu menu, String icon) {
		super(menu, "Select Theme", false, 210, 235, icon);
		this.menu = menu;
		LnFSelector.model = model;

		lnfPanel = new LnFSelectionPanel(model);
		lnfPanel.setBounds(0, 5, 200, 170);
		add(lnfPanel);

		btnOK = new JButton("OK");
		btnCancel = new JButton("Cancel");

		btnOK.addActionListener(this);
		btnCancel.addActionListener(this);

		btnOK.setBounds(10, 180, 85, 20);
		btnCancel.setBounds(105, 180, 85, 20);

		add(btnOK);
		add(btnCancel);
		try {
			UIManager.setLookAndFeel(model.getLookNFeel());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception x) {
			System.err.println("Sorry, could not install the requested theme!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOK) {
			int index = LnFSelectionPanel.lnfPanel.getOptionIndex();
			model.setLookNFeel(LnF.laf[index].getClassName());
			try {
				UIManager.setLookAndFeel(model.getLookNFeel());
				SwingUtilities.updateComponentTreeUI(this);
				SwingUtilities.updateComponentTreeUI(menu);
				this.repaint();
			} catch (Exception x) {
				System.err
						.println("Sorry, could not install the requested theme!");
			}
			storeTheme();
			this.setVisible(false);
		} else if (e.getSource() == btnCancel) {
			System.out.println("... exiting");
			this.setVisible(false);
		}
	}

	public void storeTheme() {
		String directory = ".res/lnf/";
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
		boolean doNotAppend = false;
		try {
			FileOutputStream outStream = new FileOutputStream(directory
					+ "config.lnf", doNotAppend);
			String lnf = model.getLookNFeel();
			try {
				outStream.write(lnf.getBytes(), 0, lnf.length());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
