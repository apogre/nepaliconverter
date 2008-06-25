package np.org.mpp.conv4.utils.jopendocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

//import org.jopendocument.dom.spreadsheet.*;

public class JOpenDocumentSpreadsheetReader {

    /*

  public static boolean DEBUG = false;


  public JOpenDocumentSpreadsheetReader() {
  }

  public static ArrayList<ArrayList<String>> read(String fileName) throws Exception {
    return read(fileName, 0, 0);
  }

  public static ArrayList<ArrayList<String>> read(String fileName, int colFrom, int colTo) throws Exception {

    if (!new File(fileName).exists()) throw new FileNotFoundException(fileName);

    File file = new File(fileName);
    final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);
    // Change strings.
    sheet.ensureColumnCount(colTo);
    int cols = sheet.getColumnCount();
    if (colTo==0) colTo = cols;
    int rows = sheet.getRowCount();


    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    //ArrayList<String> modelRow = new ArrayList<String>();
    for (int r = 0; r<rows; r++) {
        ArrayList<String> row = new ArrayList<String>();
        for (int c=colFrom; c<colTo; c++) {
            MutableCell ce = sheet.getCellAt(c,r);
            Object v = ce.getValue();
            if (v instanceof Float) {
                System.out.println(v);
                v = "" + (((Float) v).intValue());
            }
            if (! (v instanceof String)) {
                System.out.println("v = " + v + " " +v.getClass());
            }
            if (v.equals("¥")) {
                System.err.println("XXX");
            }
            row.add( v.toString());
        }
        System.out.println(row);
        res.add(row);
    }

    return res;
  }

  public static void main(String[] args) throws Exception {
    JOpenDocumentSpreadsheetReader jopendocumentspreadsheetreader = new JOpenDocumentSpreadsheetReader();
    //OdtSpreadsheetReader odtreader = new OdtSpreadsheetReader();
    //odtreader.read("test/resources/table.odt");
    //odtreader.read("res/preeti_unicode.ods");
    //read("res/preeti_unicode.ods");
    System.out.println(read("res/preeti_unicode.ods"));
  }

*/
}
