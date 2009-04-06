/*
 * Copyright (C) 2009  Nepala Esperanto-Asocio, http://www.esperanto.org.np/
 * Author: Jacob Nordfalk
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */
package np.esperanto.conv4.odfdom;

import java.io.*;

import org.openoffice.odf.doc.*;
import org.openoffice.odf.doc.element.table.*;
import org.openoffice.odf.dom.*;
import org.w3c.dom.*;
import java.util.ArrayList;
import org.openoffice.odf.doc.element.office.OdfSpreadsheet;

public class SpreadsheetReader {

  public static boolean DEBUG = false;


  public SpreadsheetReader() {
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

    OdfDocument odfdoc = OdfDocument.loadDocument(fileName);


    NodeList lst;
    if (colFrom==colTo) {
      lst = odfdoc.getContentDom().getElementsByTagNameNS(OdfNamespace.TABLE.getUri(), "table");
      OdfTable t = (OdfTable) lst.item(0);
      if (DEBUG) System.out.println("t.getTableColumnCount() = " + t.getTableColumnCount());

      colFrom = 0;
      colTo = t.getTableColumnCount();
    }

    ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
    //ArrayList<String> modelRow = new ArrayList<String>();

    ArrayList<String> row = null;


//    lst = odfdoc.getContentCached().getElementsByTagNameNS(OdfNamespace.TABLE.getUri(), "table-cell");
    lst = odfdoc.getContentDom().getElementsByTagNameNS(OdfNamespace.TABLE.getUri(), "table-cell");
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
          for (int j=colFrom; j<=colTo; j++) row.add(null);
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
        if (colIndex>=colFrom && colIndex<=colTo) row.add(colIndex-colFrom, txt);
      }
    }
    if (row != null) res.add(row);
    return res;
  }
}
