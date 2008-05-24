/*
 * @(#)UnicodifyGUI.java	2008/02/07
 * Copyright © 2007 Madan Puraskar Pustakalaya
 * www.mpp.org.np
 */
package np.org.mpp.cd.f2u;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import np.org.mpp.cd.exception.FileNotSelectedException;
import np.org.mpp.cd.exception.InvalidExtensionException;
import np.org.mpp.cd.f2u.map.Himali;
import np.org.mpp.cd.f2u.map.JagaHimali;
import np.org.mpp.cd.f2u.map.Kanchan;
import np.org.mpp.cd.f2u.map.Kantipur;
import np.org.mpp.cd.f2u.map.Preeti;
import np.org.mpp.cd.ui.DnDTextBox;
import np.org.mpp.cd.ui.Frame;
import np.org.mpp.cd.ui.button.BrowseButton;
import np.org.mpp.cd.ui.button.ConvertButton;
import np.org.mpp.cd.ui.button.ExitButton;

/**
 * This class {@link FontToUnicodeGUI} has all the GUI elements of the Unicodify
 * application.
 * 
 * @author Abhishek Shrestha
 */
public class FontToUnicodeGUI extends JPanel implements ActionListener,
	ItemListener {
    private static final long serialVersionUID = 8840769449824024166L;
    public static final String VALID_EXTENSION = "txt";
    private final String SUFFIX = "_font";
    private boolean isReadyToConvert = false;

    Frame parentFrame;
    private JPanel background;

    private JLabel inputFile;
    private JLabel inputFont;
    private JLabel inputEncoding;
    private JLabel outputEncoding;

    private DnDTextBox inputFileField;

    public static BrowseButton browse;
    public static ConvertButton convert;
    private ExitButton exit;

    private JComboBox inputFontChooser;
    private JComboBox inputEncodingChooser;
    private JComboBox outputEncodingChooser;

    public static JProgressBar progressBar;
    static final int PB_MINIMUM = 0;
    static final int PB_MAXIMUM = 100;

    int fontIndex;
    int inputEncodingIndex;
    int outputEncodingIndex;
    /*
     * TODO: try to think of better way of storing these information regarding
     * the fonts, encodings. It could be XML or a simple text file so that for
     * future extension the user simply needs to add to the external file and
     * not the source file.
     */
    private String[] fonts = { "Preeti", "Kantipur", "Jaga Himal", "Kanchan",
	    "Himali" };
    private String[] encodingsInput = { "ISO-8859-1", "ISO-8859-15" };
    private String[] encodingsOutput = { "UTF-8", "UTF-16" };

    public FontToUnicodeGUI(Frame frame) {
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
	inputFont = new JLabel("Input Font:");
	inputEncoding = new JLabel("Input Encoding:");
	outputEncoding = new JLabel("Output Encoding:");

	inputFileField = new DnDTextBox();
	inputFontChooser = new JComboBox(fonts);
	inputEncodingChooser = new JComboBox(encodingsInput);
	outputEncodingChooser = new JComboBox(encodingsOutput);

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

	outputEncoding.setBounds(225, 35, 110, 20);
	background.add(outputEncoding);
	outputEncodingChooser.setBounds(335, 35, 75, 20);
	background.add(outputEncodingChooser);

	inputFont.setBounds(5, 65, 100, 20);
	background.add(inputFont);
	inputFontChooser.setBounds(105, 65, 115, 20);
	background.add(inputFontChooser);

	browse.setBounds(485, 6, 20, 18);
	background.add(browse);
	convert.setBounds(420, 35, 85, 20);
	background.add(convert);
	exit.setBounds(420, 65, 85, 20);

	background.add(exit);

	progressBar.setBounds(225, 65, 190, 20);
	background.add(progressBar);
    }

    private void addListeners() {
	exit.addActionListener(this);
	browse.addActionListener(this);
	convert.addActionListener(this);

	inputFontChooser.addItemListener(this);
	inputEncodingChooser.addItemListener(this);
	outputEncodingChooser.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
	JComboBox selectedCombo = (JComboBox) event.getSource();
	if (selectedCombo == inputFontChooser
		&& event.getStateChange() == ItemEvent.SELECTED) {
	    fontIndex = inputFontChooser.getSelectedIndex();
	} else if (selectedCombo == inputEncodingChooser) {
	    inputEncodingIndex = inputEncodingChooser.getSelectedIndex();
	} else if (selectedCombo == outputEncodingChooser) {
	    outputEncodingIndex = outputEncodingChooser.getSelectedIndex();
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
		String outputEncoding = encodingsOutput[outputEncodingIndex];
		String[] inputFile = inputFileField.getFiles();
		// find the total size of the files
		int totalSize = 0;

		int[] initProgress = new int[inputFile.length];
		initProgress[0] = 0;
		progressBar.setValue(0);
		System.out.println("files: " + inputFile.length);
		for (int i = 0; i < inputFile.length; i++) {
		    File file = new File(inputFile[i]);
		    try {
			InputStream reader = new BufferedInputStream(
				new DataInputStream(new FileInputStream(file)));
			byte[] data = new byte[(int) file.length()];
			int readByte = reader.read(data);
			totalSize += readByte;
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
			    outputEncoding, "" + unitBlock,
			    "" + initProgress[i] };
		    isReadyToConvert = true;
		    convert.startConversion(isReadyToConvert);

		    switch (fontIndex) {
		    case 0:
			new Preeti(args);
			break;
		    case 1:
			new Kantipur(args);
			break;
		    case 2:
			new JagaHimali(args);
			break;
		    case 3:
			new Kanchan(args);
			break;
		    case 4:
			new Himali(args);
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
