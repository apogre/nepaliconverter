/*
 * @(#)UnicodifyGUI.java	2006/02/07
 * Copyright � 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.u2f;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import np.org.mpp.cd.exception.FileNotSelectedException;
import np.org.mpp.cd.exception.InvalidExtensionException;
import np.org.mpp.cd.u2f.map.Preeti;
import np.org.mpp.cd.ui.DnDTextBox;
import np.org.mpp.cd.ui.button.BrowseButton;
import np.org.mpp.cd.ui.button.ConvertButton;
import np.org.mpp.cd.ui.button.ExitButton;

/**
 * This class {@link UnicodeToFontGUI} has all the GUI elements of the Unicodify
 * application.
 * 
 * @author Abhishek Shrestha
 */
public class UnicodeToFontGUI extends JPanel implements ActionListener,
	ItemListener {
    private static final long serialVersionUID = 8840769449824024166L;
    public static final String VALID_EXTENSION = "txt";
    private final String SUFFIX = "_font";
    private boolean isReadyToConvert = false;

    JDialog parentFrame;
    private JPanel background;

    private JLabel inputFile;
    private JLabel inputEncoding;
    private JLabel outputFont;

    private DnDTextBox inputFileField;

    public static BrowseButton browse;
    public static ConvertButton convert;
    private ExitButton exit;

    private JComboBox inputEncodingChooser;
    private JComboBox outputFontChooser;

    public static JProgressBar progressBar;
    static final int PB_MINIMUM = 0;
    static final int PB_MAXIMUM = 100;

    int fontIndex;
    int inputEncodingIndex;
    int outputFontIndex;
    /*
     * TODO: try to think of better way of storing these information regarding
     * the fonts, encodings. It could be XML or a simple text file so that for
     * future extension the user simply needs to add to the external file and
     * not the source file.
     */

    private String[] encodingsInput = { "UTF-8", "UTF-16" };
    private String[] fontOutput = { "Preeti" };

    public UnicodeToFontGUI(JDialog frame) {
	setLayout(null);
	parentFrame = frame;
	init();
	addComponents();
	addListeners();
    }

    private void init() {
	background = new JPanel();
	background.setBorder(new TitledBorder(""));
	background.setLayout(null);

	inputFile = new JLabel("Input file   [txt]:");
	inputEncoding = new JLabel("Input Encoding:");
	outputFont = new JLabel("Output Font:");

	inputFileField = new DnDTextBox();
	inputEncodingChooser = new JComboBox(encodingsInput);
	outputFontChooser = new JComboBox(fontOutput);

	progressBar = new JProgressBar();
	progressBar.setMinimum(PB_MINIMUM);
	progressBar.setMaximum(PB_MAXIMUM);
	progressBar.setStringPainted(true);

	browse = new BrowseButton("...", VALID_EXTENSION, SUFFIX);
	convert = new ConvertButton("Convert");
	exit = new ExitButton("Unicodifier", parentFrame);
    }

    private void addComponents() {
	background.setBounds(5, 7, 512, 95);
	add(background);

	inputFile.setBounds(5, 5, 100, 20);
	background.add(inputFile);
	inputFileField.setBounds(105, 5, 375, 21);
	background.add(inputFileField);

	inputEncoding.setBounds(5, 35, 100, 20);
	background.add(inputEncoding);
	inputEncodingChooser.setBounds(105, 35, 115, 20);
	background.add(inputEncodingChooser);

	outputFont.setBounds(225, 35, 110, 20);
	background.add(outputFont);
	outputFontChooser.setBounds(300, 35, 115, 20);
	background.add(outputFontChooser);

	browse.setBounds(485, 6, 20, 18);
	background.add(browse);
	convert.setBounds(420, 35, 85, 20);
	background.add(convert);
	exit.setBounds(420, 65, 85, 20);

	background.add(exit);

	progressBar.setBounds(5, 65, 410, 20);
	background.add(progressBar);
    }

    private void addListeners() {
	exit.addActionListener(this);
	browse.addActionListener(this);
	convert.addActionListener(this);

	inputEncodingChooser.addItemListener(this);
	outputFontChooser.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
	JComboBox selectedCombo = (JComboBox) event.getSource();
	if (selectedCombo == inputEncodingChooser) {
	    inputEncodingIndex = inputEncodingChooser.getSelectedIndex();
	} else if (selectedCombo == outputFontChooser) {
	    outputFontIndex = outputFontChooser.getSelectedIndex();
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	JButton source = (JButton) e.getSource();
	if (source == browse) {
	    invokeBrowseDialog();
	} else if (source == exit) {
	    exit.closeApplication();
	} else if (source == convert) {
	    try {
		String inputEncoding = encodingsInput[inputEncodingIndex];
		String[] inputFile = inputFileField.getFiles();
		// find the total size of the files
		int totalSize = 0;
		int[] initProgress = new int[inputFile.length];
		initProgress[0] = 0;

		for (int i = 0; i < inputFile.length; i++) {
		    File file = new File(inputFile[i]);
		    try {
			InputStream reader = new BufferedInputStream(
				new DataInputStream(new FileInputStream(file)));
			System.out.println("file size: " + file.length());
			byte[] data = new byte[(int) file.length()];
			reader.read(data);
			String input = new String(data, inputEncoding);
			StringBuffer inputBuffer = new StringBuffer(input);
			totalSize += inputBuffer.length();
			if (i < inputFile.length - 1) {
			    initProgress[i + 1] = totalSize;
			}
		    } catch (FileNotFoundException e1) {

		    } catch (IOException e1) {

		    }
		}

		float unitBlock = totalSize / 100f;
		for (int i = 0; i < inputFile.length; i++) {
		    String[] args = { inputFile[i], inputEncoding,
			    "" + unitBlock, "" + initProgress[i] };
		    isReadyToConvert = true;
		    convert.startConversion(isReadyToConvert);
		    switch (fontIndex) {
		    case 0:
			new Preeti(args);
			break;
		    default:
			break;
		    }
		}
		progressBar.setValue(0);
	    } catch (FileNotSelectedException f) {
		if (f.getSelectedOption() == JOptionPane.YES_OPTION) {
		    invokeBrowseDialog();
		} else {
		    return;
		}
	    }
	}
    }

    public void invokeBrowseDialog() {
	try {
	    browse.showBroseDialog();
	    String validFilePath = browse.getValidFilePath();
	    Object[] val = { validFilePath };
	    inputFileField.setFile(val);
	    isReadyToConvert = true;
	} catch (InvalidExtensionException i) {
	    isReadyToConvert = false;
	    if (i.getSelectedOption() == JOptionPane.YES_OPTION) {
		invokeBrowseDialog();
	    } else {
		return;
	    }
	}
    }
}
