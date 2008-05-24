// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Layout.java

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintStream;
import javax.swing.*;


public class Layout extends JFrame
{

    public Layout()
    {
	flagdir = false;
	initComponents();
    }

    private void initComponents()
    {
	jLabel1 = new JLabel();
	txtInputname = new JTextField();
	jLabel3 = new JLabel();
	jLabel4 = new JLabel();
	comInputfont = new JComboBox(inputfont);
	inencoding = new JComboBox(inputencoding);
	jLabel6 = new JLabel();
	outencoding = new JComboBox(outputencoding);
	btnBrowse = new JButton();
	btnRun = new JButton();
	btnCancel = new JButton();
	jLabel7 = new JLabel();
	jLabel8 = new JLabel();
	jLabel5 = new JLabel();
	progress = new JLabel();
	getContentPane().setLayout(new GridBagLayout());
	setDefaultCloseOperation(3);
	setTitle("Font Converter");
	setFocusCycleRoot(false);
	setLocationByPlatform(true);
	setName("Font Converter");
	jLabel1.setText("Input File name(txt) :");
	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.fill = 1;
	gridBagConstraints.ipadx = 15;
	gridBagConstraints.anchor = 17;
	getContentPane().add(jLabel1, gridBagConstraints);
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.fill = 1;
	gridBagConstraints.ipadx = 113;
	gridBagConstraints.anchor = 17;
	getContentPane().add(txtInputname, gridBagConstraints);
	jLabel3.setText("Input Font:");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.ipadx = 26;
	gridBagConstraints.anchor = 17;
	getContentPane().add(jLabel3, gridBagConstraints);
	jLabel4.setText("Input Encoding:");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.ipadx = 35;
	gridBagConstraints.anchor = 17;
	getContentPane().add(jLabel4, gridBagConstraints);
	comInputfont.setSelectedIndex(0);
	comInputfont.addItemListener(new ItemListener() {

	    public void itemStateChanged(ItemEvent evt)
	    {
		infont(evt);
	    }

	});
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.fill = 2;
	gridBagConstraints.ipadx = 69;
	gridBagConstraints.anchor = 17;
	getContentPane().add(comInputfont, gridBagConstraints);
	inencoding.setSelectedIndex(0);
	inencoding.addItemListener(new ItemListener() {

	    public void itemStateChanged(ItemEvent evt)
	    {
		inencod(evt);
	    }

	});
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.fill = 2;
	gridBagConstraints.ipadx = 68;
	getContentPane().add(inencoding, gridBagConstraints);
	jLabel6.setText("Output Encoding:");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 7;
	gridBagConstraints.ipadx = 21;
	gridBagConstraints.anchor = 17;
	getContentPane().add(jLabel6, gridBagConstraints);
	outencoding.setSelectedIndex(0);
	outencoding.addItemListener(new ItemListener() {

	    public void itemStateChanged(ItemEvent evt)
	    {
		outencod(evt);
	    }

	});
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 7;
	gridBagConstraints.fill = 1;
	gridBagConstraints.ipadx = 69;
	getContentPane().add(outencoding, gridBagConstraints);
	btnBrowse.setText("Browse");
	btnBrowse.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent evt)
	    {
		btnBrowseActionPerformed(evt);
	    }

	});
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.fill = 1;
	getContentPane().add(btnBrowse, gridBagConstraints);
	btnRun.setText("Run");
	btnRun.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent evt)
	    {
		btnRunActionPerformed(evt);
	    }

	});
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 6;
	gridBagConstraints.fill = 1;
	gridBagConstraints.ipadx = 39;
	getContentPane().add(btnRun, gridBagConstraints);
	btnCancel.setText("Cancel");
	btnCancel.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent evt)
	    {
		btnCancelActionPerformed(evt);
	    }

	});
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 5;
	gridBagConstraints.fill = 1;
	gridBagConstraints.ipadx = 25;
	getContentPane().add(btnCancel, gridBagConstraints);
	jLabel7.setText("     ");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 8;
	gridBagConstraints.fill = 1;
	getContentPane().add(jLabel7, gridBagConstraints);
	jLabel8.setText("      ");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.fill = 1;
	getContentPane().add(jLabel8, gridBagConstraints);
	jLabel5.setText("      ");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 3;
	gridBagConstraints.gridy = 2;
	getContentPane().add(jLabel5, gridBagConstraints);
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 9;
	gridBagConstraints.gridwidth = 2;
	gridBagConstraints.fill = 1;
	getContentPane().add(progress, gridBagConstraints);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	setBounds((screenSize.width - 600) / 2, (screenSize.height - 262) / 2, 500, 180);
	setResizable(false);
    }

    private void btnSaveActionPerformed(ActionEvent evt)
    {
	code = 2;
	openFile();
    }

    private void outencod(ItemEvent evt)
    {
	chng3 = outencoding.getSelectedIndex();
    }

    private void inencod(ItemEvent evt)
    {
	chng2 = inencoding.getSelectedIndex();
    }

    private void infont(ItemEvent evt)
    {
	chng1 = comInputfont.getSelectedIndex();
    }

    private void btnRunActionPerformed(ActionEvent evt)
    {
	if(txtInputname.getText().equals(""))
	    JOptionPane.showMessageDialog(null, "Fill all fields!");
	else
	if(comInputfont.getSelectedItem().toString().equals("Kantipur/ Nepal"))
	{
	    JOptionPane.showMessageDialog(null, "The Process has been Completed");
	} else
	if(comInputfont.getSelectedItem().toString().equals("Preeti"))
	{
	    JOptionPane.showMessageDialog(null, "The Process has been Completed");
	} else
	if(comInputfont.getSelectedItem().toString().equals("Jaga Himali"))
	{
	    JOptionPane.showMessageDialog(null, "The Process has been Completed");
	} else
	if(comInputfont.getSelectedItem().toString().equals("Kanchan"))
	{
	    JOptionPane.showMessageDialog(null, "The Process has been Completed");
	} else
	if(comInputfont.getSelectedItem().toString().equals("Himali"))
	{
	    JOptionPane.showMessageDialog(null, "The Process has been Completed");
	} else
	{
	    System.out.println("no file selected");
	}
    }

    public JLabel getProgress()
    {
	return progress;
    }

    private void btnBrowseActionPerformed(ActionEvent evt)
    {
	code = 1;
	openFile();
    }

    private void openFile()
    {
	if(code == 1)
	{
	    fileChooser1 = new JFileChooser();
	    fileChooser1.setFileSelectionMode(2);
	    int result1 = fileChooser1.showOpenDialog(this);
	    if(result1 == 1)
		return;
	    fileName1 = fileChooser1.getSelectedFile();
	    if(fileName1.isDirectory())
	    {
		filename1 = fileName1.getPath();
		System.out.println(filename1);
		flagdir = true;
	    } else
	    {
		filename1 = fileName1.getAbsolutePath();
		flagdir = false;
	    }
	    if(filename1.equals(""))
		JOptionPane.showMessageDialog(this, "Invalid File Name", "Invalid File Name", 0);
	    else
		txtInputname.setText(filename1);
	    System.out.println(filename1);
	}
    }

    public String getFile()
    {
	return filename1;
    }

    public String getInputfont()
    {
	return inputfont[chng1];
    }

    public String getInputencoding()
    {
	return inputencoding[chng2];
    }

    public String getOutputfile()
    {
	return filename2;
    }

    public String getOutputencoding()
    {
	return outputencoding[chng3];
    }

    public JButton getRun()
    {
	return btnRun;
    }

    private void btnCancelActionPerformed(ActionEvent evt)
    {
	System.exit(0);
    }

    public static void main(String args[])
    {
	EventQueue.invokeLater(new Runnable() {

	    public void run()
	    {
		(new Layout()).setVisible(true);
	    }

	});
    }

    private String inputfont[] = {
	"Preeti", "Kantipur/ Nepal", "Jaga Himali", "Kanchan", "Himali"
    };
    private String inputencoding[] = {
	"ISO-8859-1", "ISO-8859-15"
    };
    private String outputencoding[] = {
	"UTF8", "UTF16"
    };
    public File fileName1;
    public File fileName2;
    public boolean flagdir;
    private JFileChooser fileChooser1;
    private static String filename1;
    private static String filename2;
    private static int code;
    private static int chng1;
    private static int chng2;
    private static int chng3;
    private JButton btnBrowse;
    private JButton btnCancel;
    private JButton btnRun;
    public static JComboBox comInputfont;
    public static JComboBox inencoding;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    public static JComboBox outencoding;
    private JLabel progress;
    private JTextField txtInputname;






}
