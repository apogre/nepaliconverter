package np.org.mpp.old.old_f2u;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * This panel permits the user select between different encoders for a particular piece of text
 * @author Jacob Nordfalk
 * @version 1.0
 */
public class AskWhichEncoderToUsePanel extends JPanel
{
  private JEditorPane jEditorPane1 = new JEditorPane();

  JCheckBox dontAskAgain = new JCheckBox();
  JList fontList = new JList();
  String[] fontNames;
  String[] unicValues;
  /**
   * True if all possible conversions happens to give the same unicode anyway
   */
  boolean allGivesSameUnicode = true;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel1 = new JLabel();

  public void set(Map fontNameToFontClass, String font, String teksto, String err)
  {
    err = "<html><body><p>"+err+"</p>"
	+"<p><span style=\"font-family:'"+font+"';font-size: 200%;\">"+teksto+"</span></p>"
	+"<p>Please select which converter to use or what to do:</p></body></html>";
    System.err.println(err);
    jEditorPane1.setContentType("text/html");
    jEditorPane1.setText(err);


    DefaultListModel m = new DefaultListModel();
    fontNames = (String[]) fontNameToFontClass.keySet().toArray(new String[0]);
    unicValues = new String[fontNames.length];
    String unicAll = null;
    allGivesSameUnicode = true;
    for (int i=0; i<fontNames.length; i++) {
      String f = (String) fontNames[i];
      NonUnicodeFont nonUnicodeFont2 = (NonUnicodeFont) fontNameToFontClass.get(f);

      String unic = nonUnicodeFont2.toUnicode(teksto);
      unicValues[i] = unic;

      if (nonUnicodeFont2 instanceof DontConvert) {
	m.addElement("<html><body>"+teksto+"  &nbsp;&nbsp;(no conversion)</body></html>");

      } else {

	if (unicAll==null) unicAll = unic;
	else if (!unic.equals(unicAll)) allGivesSameUnicode = false;
	if (nonUnicodeFont2.problemsInLastConversion.size()>0) allGivesSameUnicode = false;

	m.addElement(("<html><body><span style=\"font-size: 150%;\">"+unic+"</span> &nbsp;&nbsp; from &nbsp;&nbsp; "+f+": <span style=\"font-family:'"+f+"';font-size: 150%;\">"+teksto+"</span></body></html>"));
      }
    }
    fontList.setModel(m);

    fontList.setSelectedIndex(Math.max(0,Arrays.binarySearch(fontNames, font)));
  }

  public AskWhichEncoderToUsePanel() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    dontAskAgain.setText("Dont\' ask this particular question again");
    jEditorPane1.setText("jEditorPane1");
    fontList.setBackground(UIManager.getColor("Menu.background"));
    fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fontList.addListSelectionListener(new
	AskWhichEncoderToUsePanel_fontList_listSelectionAdapter(this));
    jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
    jTextField1.setText("jTextField1");
    jLabel1.setText("<html><body>Perhaps<br/>correct<br/>it here</body></html>");
    this.add(fontList, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
					      , GridBagConstraints.CENTER,
					      GridBagConstraints.BOTH,
					      new Insets(0, 0, 0, 0), 0, 0));
    this.add(jEditorPane1, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
						  , GridBagConstraints.CENTER,
						  GridBagConstraints.BOTH,
						  new Insets(0, 0, 0, 0), 0, 0));
    this.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
					     , GridBagConstraints.CENTER,
					     GridBagConstraints.NONE,
					     new Insets(0, 0, 0, 0), 0, 0));
    this.add(jTextField1, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
						 , GridBagConstraints.WEST,
						 GridBagConstraints.BOTH,
						 new Insets(0, 0, 0, 0), 0, 10));
    this.add(dontAskAgain, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
						  , GridBagConstraints.WEST,
						  GridBagConstraints.NONE,
						  new Insets(0, 0, 0, 0), 0, 0));
  }

  public void fontList_valueChanged(ListSelectionEvent e) {
    jTextField1.setText(unicValues[fontList.getSelectedIndex()]);
  }
}

class AskWhichEncoderToUsePanel_fontList_listSelectionAdapter
    implements ListSelectionListener {
  private AskWhichEncoderToUsePanel adaptee;
  AskWhichEncoderToUsePanel_fontList_listSelectionAdapter(
      AskWhichEncoderToUsePanel adaptee) {
    this.adaptee = adaptee;
  }

  public void valueChanged(ListSelectionEvent e) {
    adaptee.fontList_valueChanged(e);
  }
}
