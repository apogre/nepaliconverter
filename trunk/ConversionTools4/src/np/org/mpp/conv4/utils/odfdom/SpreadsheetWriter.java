package np.org.mpp.conv4.utils.odfdom;

import java.io.File;

import org.openoffice.odf.doc.OdfFileDom;
import org.openoffice.odf.doc.OdfSpreadsheetDocument;
import org.openoffice.odf.doc.element.table.*;
import org.openoffice.odf.doc.element.text.OdfParagraph;
import org.openoffice.odf.dom.OdfNamespace;
import org.openoffice.odf.dom.style.*;
import org.openoffice.odf.dom.style.props.*;
import org.w3c.dom.NodeList;
import java.util.*;

public class SpreadsheetWriter {

  public SpreadsheetWriter() {

  }

  public void write(String outFile, String[][] data) throws Exception {
      ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
      for (String[] r : data) {
          ArrayList<String> ral = new ArrayList<String>();
          ral.addAll(Arrays.asList(r));
          al.add(ral);
      }

      write(outFile, al);
  }

  // Collection<Collection<String>>
  public void write(String outFile, ArrayList<ArrayList<String>> data) throws Exception {
    OdfSpreadsheetDocument odsDoc1 = new OdfSpreadsheetDocument();
    //odsDoc1.save(outFile);

    OdfFileDom doc = odsDoc1.getContent();

    // find the first table
    NodeList lst = doc.getElementsByTagNameNS(
            OdfTable.ELEMENT_NAME.getUri(),
            OdfTable.ELEMENT_NAME.getLocalName());
    OdfTable table = (OdfTable) lst.item(0);

    //System.out.println(doc.toString().replaceAll("<","\n1<"));

    table.removeChild(table.getFirstChild().getNextSibling()); // remove first empty row
    //table.removeChild(table.getFirstChild()); // remove first empty row

    //System.out.println(doc.toString().replaceAll("<","\n2<"));

    /*
    OdfStyle style1 = new OdfStyle("style1", OdfStyleFamily.TableCell);
    style1.setProperty(OdfTextProperties.FontName, "Preeti");

    OdfTextStyle ts1 = new OdfTextStyle("text1");
    ts1.setProperty(ts1.FontName, "Helvetica");

    OdfStyle style2 = new OdfStyle("style2", OdfStyleFamily.getByName("text"));
    style2.setProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.FO, "font-weight", "bold");

    OdfStyle style3 = new OdfStyle("style3", OdfStyleFamily.Text);
    style3.setProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.FO, "font-weight", "bold");
    style3.setProperty(OdfStylePropertiesSet.TextProperties, OdfNamespace.FO, "font-size", "15pt");

    odsDoc1.getDocumentStyles().addStyle(style1);
    odsDoc1.getDocumentStyles().addStyle(style2);
    odsDoc1.getDocumentStyles().addStyle(style3);
    odsDoc1.getDocumentStyles().addStyle(ts1);

    OdfTableRow tr = (OdfTableRow) table.appendChild(
            doc.createOdfElement(OdfTableRow.class));
    OdfTableCell td1 = (OdfTableCell) tr.appendChild(
            doc.createOdfElement(OdfTableCell.class));
    OdfParagraph p1 = doc.createOdfElement(OdfParagraph.class);
    p1.setStyleName(ts1.getName());
    p1.appendChild(doc.createTextNode("content 1"));
    td1.appendChild(p1);

    OdfTableCell td2 = (OdfTableCell) tr.appendChild(
            doc.createOdfElement(OdfTableCell.class));
    OdfParagraph p2 = doc.createOdfElement(OdfParagraph.class);
    p2.setStyleName(style1.getName());
    p2.appendChild(doc.createTextNode("cell 2"));
    td2.appendChild(p2);

    OdfTableCell td3 = (OdfTableCell) tr.appendChild(
            doc.createOdfElement(OdfTableCell.class));
    OdfParagraph p3 = doc.createOdfElement(OdfParagraph.class);
    p2.setStyleName(style3.getName());
    p3.appendChild(doc.createTextNode("table cell content 3"));
    td3.appendChild(p3);

    //p0.getParentNode().insertBefore(table, p0);

    OdfStyle ts = table.getAutomaticStyle();
    ts.setProperty(OdfTableProperties.Width, "12cm");
    ts.setProperty(OdfTableProperties.Align, "left");

    OdfStyle cs1 = td1.getTableColumn().getAutomaticStyle();
    cs1.setProperty(OdfTableColumnProperties.ColumnWidth, "2cm");
    td1.getTableColumn().setStyleName(style1.getName());

    OdfStyle cs2 = td2.getTableColumn().getAutomaticStyle();
    cs2.setProperty(OdfTableColumnProperties.ColumnWidth, "4cm");
    cs2.setProperty(OdfTextProperties.FontName, "Preeti");

    OdfStyle cs3 = td3.getTableColumn().getAutomaticStyle();
    cs3.setProperty(OdfTableColumnProperties.ColumnWidth, "6cm");
    */

   int n=0;

   for (ArrayList<String> ral : data) {

       OdfTableRow tr = (OdfTableRow) table.appendChild(
               doc.createOdfElement(OdfTableRow.class));
       for (String cellText : ral) {

           OdfTableCell td1 = (OdfTableCell) tr.appendChild(
                   doc.createOdfElement(OdfTableCell.class));

           OdfParagraph p1 = doc.createOdfElement(OdfParagraph.class);

           p1.appendChild(doc.createTextNode(cellText));
           td1.appendChild(p1);

       }
       if (++n % 100 == 0) {
           if (n % 10000 == 0) System.out.print("\n"+n);
           else System.out.print(".");
       }
   }

   if (n > 10000) {
       System.out.println("Writing to "+outFile);
   }

    doc.getOdfDocument().save(outFile);

    //System.out.println(doc.toString().replaceAll("<","\n3<"));

  }

  public static void main(String[] args) throws Exception {
    SpreadsheetWriter ssw = new SpreadsheetWriter();
    new File("tmp").mkdirs();
    String[][] data = new String[][] { { "A1", "A2" }, { "A1", "A2" }, { "A1", "A2" }, { "A1", "A2" }, { "A1", "A2" }, { "B1", "B2" } };
    ssw.write("tmp/slet.ods", data );
  }
}
