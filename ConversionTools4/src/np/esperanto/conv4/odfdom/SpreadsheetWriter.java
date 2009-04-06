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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.Element;
import org.openoffice.odf.doc.OdfFileDom;
import org.openoffice.odf.doc.OdfSpreadsheetDocument;
import org.openoffice.odf.doc.element.table.*;
import org.openoffice.odf.doc.element.text.OdfParagraph;
import org.openoffice.odf.dom.OdfNamespace;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.openoffice.odf.pkg.OdfPackage;
import org.w3c.dom.Document;
import java.io.OutputStream;
import org.openoffice.odf.dom.type.table.OdfValueType;

/**
 * A class for writing ODS spread sheets.
 * License: Whatever (public domain), no attribution required
 * @author Jacob Nordfalk
 */
public class SpreadsheetWriter {

    /*
      Construct a cell with text String s inside it
      and the given cell style.
    */
    private static OdfTableCell constructStringCell( String s, String cellStyleName, OdfFileDom content)
    {/*
      OdfTableCell cell = new OdfTableCell( content );
      cell.setStringValue( s );
      cell.setValueType( OdfValueType.STRING );
      OdfParagraph para = new OdfParagraph( content );
      para.appendChild( content.createTextNode( s ) );
      if (cellStyleName != null)
      {
        cell.setStyleName( cellStyleName );
      }
      cell.appendChild( para );
      return cell;
      */
      return null;
    }


  /**
   * Writes a two-dimensional ArrayList of String to a file as a SpreadSheet.
   * All cells will be in standard formatting
   * @param outFile File name
   * @param data ArrayList of data rows. Each row is an ArrayList of strings.
   * Rows does not have to be of equal length
   */
  public static void write(String outFile, ArrayList<ArrayList<String>> data, String font2) throws Exception {
    /*
    OdfSpreadsheetDocument odsDoc1 = new OdfSpreadsheetDocument();


    OdfFileDom doc = odsDoc1.getContent();

    // find the first table in the sheet
    NodeList lst = doc.getElementsByTagNameNS(
        OdfTable.ELEMENT_NAME.getUri(),
        OdfTable.ELEMENT_NAME.getLocalName());
    OdfTable table = (OdfTable) lst.item(0);

    // print out the XML
    //System.out.println(doc.toString().replaceAll("<","\n1<"));

    // remove first empty row of table. TODO: more rebust method
    table.removeChild(table.getFirstChild().getNextSibling());



      //Create a bold-centered style
    OdfStyleCollection automaticStyles = odsDoc1.getAutomaticStyles( );

    // newly created styles are children of <office:automatic-styles> element
    Node autoStyleNode =
      doc.getElementsByTagNameNS(OdfNamespace.OFFICE.getUri(), "automatic-styles").item(0);

    OdfTableCellStyle boldCenter = new OdfTableCellStyle( "boldCenter", automaticStyles );
    boldCenter.setProperty( OdfTextProperties.FontWeight, "bold");
    boldCenter.setProperty( OdfParagraphProperties.TextAlign, "center" );
    boldCenter.appendToNode(autoStyleNode);


    
    //  Create a style with a different font
    
    OdfTableCellStyle specFont2 = new OdfTableCellStyle( "specfont", automaticStyles );
    specFont2.setProperty( OdfTextProperties.FontName, font2);
    //preeti.setProperty( OdfParagraphProperties.TextAlign, "center" );
    specFont2.appendToNode(autoStyleNode);


    //System.out.println("autoStyleNode.getNamespaceURI() = " + autoStyleNode.getNamespaceURI());

    //System.out.println(doc.toString().replaceAll("<","\n1<"));

    // We also need a <office:font-face-decls>
    // <style:font-face style:name="Preeti" svg:font-family="Preeti" style:font-pitch="variable"/>
    Node fontFaceDecls =
      doc.getElementsByTagNameNS(OdfNamespace.OFFICE.getUri(), "font-face-decls").item(0);

    //Element ff = fontFaceDecls.getOwnerDocument().createElement("style:font-face");
    String ns = fontFaceDecls.getFirstChild().getNamespaceURI();
    // ns = urn:oasis:names:tc:opendocument:xmlns:style:1.0

    //System.out.println("ns = " + ns);
    Document od = fontFaceDecls.getOwnerDocument();
    Element ff = fontFaceDecls.getOwnerDocument().createElementNS(ns, "style:font-face");
    ff.setAttribute("style:name",font2);
    //ff.setAttribute("svg:font-family","Preeti");
    org.w3c.dom.Attr a = od.createAttributeNS("urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0","svg:font-family");
    a.setValue(font2);
    ff.setAttributeNode(a);
    ff.setAttribute("style:font-pitch", "variable");

    fontFaceDecls.appendChild(ff);
    //System.out.println("fontFaceDecls = " + fontFaceDecls);

    //System.out.println(doc.toString().replaceAll("<","\n2<"));

    //System.exit(0);



    int n = 0;

    for (ArrayList<String> ral : data) {
      OdfTableRow tr = (OdfTableRow) doc.createOdfElement(OdfTableRow.class);
      table.appendChild(tr);

      int c = 0;
      for (String cellText : ral) {
          if (c==1)
              tr.appendChild(constructStringCell( cellText, "specfont", doc ));
          else
              tr.appendChild(constructStringCell( cellText, null, doc ));
        c++;
      }


      if (++n % 100 == 0) {
        if (n % 10000 == 0)
          System.out.print("\n" + n);
        else
          System.out.print(".");
      }
    }

    if (n > 10000) {
      System.out.println("\nWriting to " + outFile);
    }


    // THIS WILL CALL odsDoc1.getContent() WHICH WILL DELETE MY STYLES!
    //odsDoc1.save(outFile);

    // Instead do it 'by hand' without calling getContent()
    OdfPackage mPackage = odsDoc1.getOdfPackage();

    // quick fix to set zoom to 234%:
    String sett = new String(mPackage.getBytes(mPackage.STREAMNAME_SETTINGS));
    String sett2 = sett.replaceAll(
        "<config:config-item config:name=\"ZoomValue\" config:type=\"int\">100</config:config-item>",
        "<config:config-item config:name=\"ZoomValue\" config:type=\"int\">234</config:config-item>" );
    if (sett2.equals(sett)) System.err.println("Failed to set zoom factor.");
    OutputStream os = mPackage.insert(mPackage.STREAMNAME_SETTINGS);
    os.write(sett2.getBytes());
    os.close();

    mPackage.insert(doc, "content.xml");
    mPackage.save(outFile);

    if (n > 10000) {
      System.out.println("Writing finished");
    }
*/
  }


  /**
   * Writes a two-dimensional array of String to a file as a SpreadSheet.
   * All cells will be in standard formatting
   * @param outFile File name
   * @param data array of data rows. Each row is an array of strings.
   * Rows does not have to be of equal length
   */
  public static void write(String outFile, String[][] data) throws Exception {
    ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
    for (String[] r : data) {
      ArrayList<String> ral = new ArrayList<String>();
      ral.addAll(Arrays.asList(r));
      al.add(ral);
    }

    write(outFile, al, "Kalimati");
  }


  public static void main(String[] args) throws Exception {
    SpreadsheetWriter ssw = new SpreadsheetWriter();
    new File("tmp").mkdirs();
    String[][] data = new String[][] { {"A1", "A2"}, {"A1", "A2"}, {"A1", "A2"}, {"A1", "A2"}, {"B1", "B2"}
    };
    ssw.write("tmp/slet.ods", data);
  }
}

