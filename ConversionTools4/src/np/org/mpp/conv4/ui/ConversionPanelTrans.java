package np.org.mpp.conv4.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import np.org.mpp.conv4.translit.NepaliTransliterationAbishek;
import np.org.mpp.conv4.translit.NepaliTransliterationJacob;
import np.org.mpp.conv4.utils.ConversionHandler;
import np.org.mpp.ui.WidgetFactory;
import javax.swing.JFileChooser;

public class ConversionPanelTrans extends ConversionPanel {

    ConversionHandler transliterator; // initialization delayed to later = new NepaliTransliterationJacob();


    public ConversionPanelTrans() {
        /*
        // Observes the selection (also without copy)
        new ClipboardObserver(Toolkit.getDefaultToolkit().getSystemSelection(), DataFlavor.stringFlavor).addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
              if (isVisible())
                  console.setText("text marked: " + ae.getActionCommand() + "\n" + ae.getSource());
          }
        });
        */
        appendLog("this is the transliterator");

    }


    private final String[] trans = { "ALA-LC", "ALA-LC w exceptions", "ALA-LC Abisheks code", "MS-Nepal", "Esperanto dict", "(select file)" };
    private int currentTransliteratorIndex = -2;

    private void getRightTransliterator() {
      int i = cmbTrans.getSelectedIndex();
      if (currentTransliteratorIndex != i) {
          currentTransliteratorIndex = i;

          switch (i) {
          case 0:
              transliterator = new NepaliTransliterationJacob("res/transliteration/NepaliALA-LC.xml", false);
              break;
          case 1:
              transliterator = new NepaliTransliterationJacob("res/transliteration/NepaliALA-LC.xml", true);
              break;
          case 2:
              transliterator = new NepaliTransliterationAbishek();
              break;
          case 3:
              transliterator = new NepaliTransliterationJacob("res/transliteration/NepaliJacob.xml", true);
              break;
          case 4:
              transliterator = new NepaliTransliterationJacob("res/transliteration/NepaliJacobVortaro.xml", true);
              break;
          case 5:
              JFileChooser browse = new JFileChooser();
              browse.setDialogTitle("Choose an XML transliteration file");
              if (browse.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                transliterator = new NepaliTransliterationJacob(browse.getSelectedFile().getPath(), true);
              }
              break;

          }
      }
    }

    private String JFileChooser() {
        return "";
    }


    String convertClipboardHtml(String html) {
        getRightTransliterator();
        //ToolBar.getInstance().list();

        // TODO: WE NEED A HTML PARSER to just transliterate the text (and f.eks. not transluiterate devanagari URLS)
        // Anyway, just transliterating it all will work on most HTML:
        String conv = transliterator.convertText(null, html);
        appendLog("============================");
        appendLog(stripHtml(html));
        if (html.equals(conv)) {
          appendLog("Was NOT converted (nothing to convert)");
          conv = html;
        } else {
          appendLog("Was converted to: ");
          appendLog(stripHtml(conv));
        }
        return conv;
    }

    private String stripHtml(String html) {
//        new Pattern
        html = Pattern.compile("<.*?>", Pattern.DOTALL).matcher(html).replaceAll("");
        html = html.replaceAll("\n"," ").replaceAll("\\s+"," ");
        return html;
    }


    JComboBox cmbTrans = null; // Reference to instance from toolbar

    public void confgureGui(MainFrame mainFrame) {

        if (cmbTrans == null) {
          // delayed first time initialization
          cmbTrans = mainFrame.toolBar.cmbTrans;
          cmbTrans.setModel(new DefaultComboBoxModel(trans));
          cmbTrans.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  appendLog("Transliterator selected "+cmbTrans.getSelectedItem());
              }
          });
        }

        mainFrame.toolBar.cmbFont.setVisible(false);
    }

}
