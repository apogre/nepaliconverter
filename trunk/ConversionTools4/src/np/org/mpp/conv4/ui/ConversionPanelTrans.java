package np.org.mpp.conv4.ui;

import np.org.mpp.conv4.ui.dnd.ClipboardObserver;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionListener;
import np.org.mpp.conv4.translit.NepaliTransliterationJacob;
import java.util.regex.Pattern;

public class ConversionPanelTrans extends ConversionPanel {

    NepaliTransliterationJacob transliterator = new NepaliTransliterationJacob();

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


    String convertClipboardHtml(String html) {

        // TODO: WE NEED A HTML PARSER to just transliterate the text (and f.eks. not transluiterate devanagari URLS)
        // Anyway, just transliterating it all will work on most HTML:
        String conv = transliterator.convertText(null, html);


        appendLog("============================");
        appendLog(stripHtml(html));
        appendLog("Was converted to: ");
        appendLog(stripHtml(conv));
        return conv;
    }

    private String stripHtml(String html) {
//        new Pattern
        html = Pattern.compile("<.*?>", Pattern.DOTALL).matcher(html).replaceAll("");
        html = html.replaceAll("\n"," ").replaceAll("\\s+"," ");
        return html;
    }


    public void confgureGuiForThisPanel(ToolBar toolBar, MenuBar menuBar) {
        toolBar.cmbFont.setVisible(false);
        //toolBar.cmbTrans.setVisible(false);
    }




}
