package np.org.npp.conv.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.*;
import javax.swing.table.*;
import java.awt.Font;
import np.org.mpp.old.old_f2u.*;

public class AskWhichEncoderToUsePanel2 extends JPanel {
  private BorderLayout borderLayout1 = new BorderLayout();
  private JEditorPane jEditorPane1 = new JEditorPane();

  JCheckBox dontAskAgain = new JCheckBox();
  JTable fontList = new JTable();
  String[] fontNames;
  /**
   * True if all possible conversions happens to give the same unicode anyway
   */
  boolean allGivesSameUnicode = true;

  public void set(Map fontNameToFontClass, String font, String teksto, String err)
  {
    err = "<html><body><p>"+err+"</p>"
	+"<p><span style=\"font-family:'"+font+"';font-size: 200%;\">"+teksto+"</span></p>"
	+"<p>Please select which converter to use or what to do:</p></body></html>";
    System.err.println(err);
    jEditorPane1.setContentType("text/html");
    jEditorPane1.setText(err);

    fontNames = (String[]) fontNameToFontClass.keySet().toArray(new String[0]);
    String[] c = new String[] {"Unicode", "Original", "Font name"};
    String[][] d = new String[fontNames.length][3];


    String unic = null;
    allGivesSameUnicode = true;
    for (int i=0; i<fontNames.length; i++) {
      String f = (String) fontNames[i];
      //NonUnicodeFont nonUnicodeFont2 = (NonUnicodeFont) fontNameToFontClass.get(f);

      //if (nonUnicodeFont2 instanceof DontConvert) {
	//d[i][0] .addElement("<html><body>"+teksto+"  &nbsp;&nbsp;(no conversion)</body></html>");

      //} else
      {

	StringBuffer sb = new StringBuffer(teksto.length() + 5);
	//nonUnicodeFont2.toUnicode(sb, teksto);
	String unic2 = sb.toString();


	if (unic==null) unic = unic2;
	else if (!unic2.equals(unic)) allGivesSameUnicode = false;

	d[i][0]=unic2;
	d[i][1]="<html><body><span style=\"font-family:'"+f+"';xxfont-size: 150%;\">"+teksto+"</span></body></html>";
	d[i][2]=f;
	//m.addElement(("<html><body>"+sb+"   from  "+f+":  <span style=\"font-family:'"+f+"';xxfont-size: 150%;\">"+teksto+"</span></body></html>").replaceAll(" ","&nbsp;"));
      }
    }

    TableModel tm = new DefaultTableModel(d,c) {
      public boolean isCellEditable(int rowIndex, int columnIndex) {
	return true;//columnIndex == 0;
      }
    };
    fontList.setModel(tm);
    fontList.validate();
  }

  public AskWhichEncoderToUsePanel2() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    dontAskAgain.setText("Dont\' ask about this particulat font again");
    jEditorPane1.setText("jEditorPane1");
    fontList.setBackground(UIManager.getColor("Menu.background"));
    fontList.setFont(new java.awt.Font("Dialog", Font.PLAIN, 24));
    fontList.setRowHeight(30);
    fontList.setRowMargin(10);
    fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.add(dontAskAgain, java.awt.BorderLayout.SOUTH);
    this.add(fontList, java.awt.BorderLayout.CENTER);
    this.add(jEditorPane1, java.awt.BorderLayout.NORTH);
  }
}
