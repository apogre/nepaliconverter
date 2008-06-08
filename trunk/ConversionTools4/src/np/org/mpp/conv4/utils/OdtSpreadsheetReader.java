package np.org.mpp.conv4.utils;

import java.io.*;

import org.openoffice.odf.doc.*;
import org.openoffice.odf.doc.element.table.*;
import org.openoffice.odf.dom.*;
import org.w3c.dom.*;
import java.util.ArrayList;

public class OdtSpreadsheetReader {

  public static boolean DEBUG = false;


  public OdtSpreadsheetReader() {
  }

  public static void main(String[] args) throws Exception {
    //OdtSpreadsheetReader odtreader = new OdtSpreadsheetReader();
    //odtreader.read("test/resources/table.odt");
    //odtreader.read("res/preeti_unicode.ods");
    read("res/preeti_unicode.ods");
    //System.out.println(read("res/preeti_unicode.ods"));
  }

  public static ArrayList<ArrayList<String>> read(String fileName) throws Exception {
    return read(fileName, 0, 0);
  }

  public static ArrayList<ArrayList<String>> read(String fileName, int colFrom, int colTo) throws Exception {
    if (!new File(fileName).exists()) throw new FileNotFoundException(fileName);

    OdfDocument odfdoc = new OdfDocument(fileName);

    NodeList lst;
    if (colFrom==colTo) {
      lst = odfdoc.getContentCached().getElementsByTagNameNS(OdfNamespace.TABLE.getUri(), "table");
      OdfTable t = (OdfTable) lst.item(0);
      if (DEBUG) System.out.println("t.getTableColumnCount() = " + t.getTableColumnCount());

      colFrom = 0;
      colTo = t.getTableColumnCount();
    }

    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    //ArrayList<String> modelRow = new ArrayList<String>();

    ArrayList<String> row = null;


    lst = odfdoc.getContentCached().getElementsByTagNameNS(OdfNamespace.TABLE.getUri(), "table-cell");
    int colIndex = Integer.MAX_VALUE;
    int rowIndex = -1;
    for (int i = 0; i < lst.getLength(); i++) {
      Node node = lst.item(i);
      if (DEBUG) System.out.println("node = " + node);

      if (node instanceof OdfTableCell) {
        OdfTableCell td = (OdfTableCell) node;

        if (colIndex >= td.getColumnIndex()) {
          rowIndex++;
          if (row != null) res.add(row);
          row = new ArrayList<String>();
          for (int j=colFrom; j<colTo; j++) row.add(null);
        }
        colIndex = td.getColumnIndex();

        String txt = "";
        node = td.getFirstChild();
        while (node != null) {
            //System.out.println(node.getNodeName());
            if (node.getNodeName().equals("text:p")) txt = txt + node.getTextContent();
            //System.out.println(node+ " '" +txt+"'");
            node = node.getNextSibling();
        }

        //String txt = td.getTextContent().trim();
        if (DEBUG) System.out.println("td = " + rowIndex + " " + colIndex + "  " + txt);
        row.set(colIndex, txt);
      }
    }
    if (row != null) res.add(row);
    return res;
  }
}
