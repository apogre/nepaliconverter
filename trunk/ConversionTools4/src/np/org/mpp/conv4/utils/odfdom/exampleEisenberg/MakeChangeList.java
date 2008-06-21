package np.org.mpp.conv4.utils.odfdom.exampleEisenberg;

import java.util.Iterator;
import java.util.Vector;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.openoffice.odf.doc.element.table.OdfTable;
import org.openoffice.odf.doc.element.table.OdfTableCell;
import org.openoffice.odf.doc.element.table.OdfTableColumn;
import org.openoffice.odf.doc.element.table.OdfTableRow;
import org.openoffice.odf.doc.element.table.OdfTableHeaderRows;
import org.openoffice.odf.doc.element.text.OdfParagraph;

import org.openoffice.odf.dom.OdfNamespace;

import org.openoffice.odf.dom.style.OdfStyle;
import org.openoffice.odf.dom.style.OdfParagraphStyle;
import org.openoffice.odf.dom.style.OdfTableCellStyle;
import org.openoffice.odf.dom.style.OdfTableColumnStyle;

import org.openoffice.odf.dom.style.props.OdfStyleProperty;
//import org.openoffice.odf.dom.style.props.OdfStyleProperties;
import org.openoffice.odf.dom.style.props.OdfStylePropertiesSet;
import org.openoffice.odf.dom.style.props.OdfParagraphProperties;
import org.openoffice.odf.dom.style.props.OdfTableColumnProperties;
import org.openoffice.odf.dom.style.props.OdfTextProperties;

import org.openoffice.odf.dom.type.OdfValueType;

import org.openoffice.odf.doc.OdfDocument;
import org.openoffice.odf.doc.OdfFileDom;
import org.openoffice.odf.doc.OdfSpreadsheetDocument;
import org.openoffice.odf.dom.style.OdfStyleCollection;
import org.openoffice.odf.pkg.OdfPackage;

/*
  This command-line application takes as its arguments:
  * an input text file name
  * an output spreadsheet name

  It will then extract all the changes from the text file and
  create a spreadsheet that lists those changes.
*/
public class MakeChangeList
{
  String inputFileName;
  String outputFileName;
  XPath xpath;
  OdfFileDom content;

  /*
    This path leads to the main content, and it's used in a couple
    of places, so we define it for convenience.
  */
  private static String baseXPath = "/office:document-content/office:body/office:text/";

  Vector<ChangeInfo> changeList;

  MakeChangeList( String[ ] args )
  {
    inputFileName = args[0];
    outputFileName = args[1];
    xpath = XPathFactory.newInstance().newXPath();
    xpath.setNamespaceContext( new OdfNamespace() );
    changeList = new Vector<ChangeInfo>( );
  }

  void extractChanges( )
  {
    try
    {
      OdfDocument odfDoc = OdfDocument.load( inputFileName );
      content = odfDoc.getContent( );
      NodeList changes = (NodeList) xpath.evaluate(
        baseXPath + "text:tracked-changes/text:changed-region",
        content, XPathConstants.NODESET );
      for (int i = 0; i < changes.getLength(); i++)
      {
        processChange( changes.item( i ) );
      }
    }
    catch (XPathExpressionException e)
    {
      System.err.println( "Error evaluating XPath" );
      e.printStackTrace( System.err );
    }
    catch (Exception e)
    {
      System.err.println( "Unable to load file " + inputFileName );
      e.printStackTrace( System.err );
    }
  }

  void processChange( Node change )
  {
    String pathExpr = "";
    Element typeNode;
    Node startNode;
    String typeName;
    String author;
    String timeStamp;
    String changeContent ="";
    String idValue;

    /*
      If you find a deletion, then the deleted information
      is immediately adjacent to the <office:change-info> element.

      For format changes and insertions, you need to find the
      <text:change-start> element with the same id as the
      id of the corresponding <text:changed-region>
    */
    try
    {
      pathExpr = "text:insertion|text:deletion|text:format-change";
      typeNode = (Element) xpath.evaluate(
        pathExpr, change, XPathConstants.NODE);
      typeName = typeNode.getLocalName( );

      if (typeName.equals("deletion"))
      {
        pathExpr = "office:change-info";
        startNode = (Node) xpath.evaluate( pathExpr, typeNode, XPathConstants.NODE );
      }
      else
      {
        idValue = ((Element) change).getAttributeNS(
          OdfNamespace.TEXT.getUri(), "id");
        pathExpr = baseXPath +
          "/text:change-start[@text:change-id='" + idValue + "']";
        // System.err.println("===============================");
        // System.err.println("Looking for " + idValue );
        startNode = (Node) xpath.evaluate( pathExpr, content,
          XPathConstants.NODE );
      }

      if (startNode != null)
      {
        changeContent = getFirstChangeText( startNode );
      }

      /*
        Extract the other info from the Dublin Core elements
      */
      pathExpr = "office:change-info/dc:creator/text()";
      author = (String) xpath.evaluate( pathExpr, typeNode,
        XPathConstants.STRING);

      pathExpr = "office:change-info/dc:date/text()";
      timeStamp = (String) xpath.evaluate( pathExpr, typeNode,
        XPathConstants.STRING);

      changeList.add( new ChangeInfo(
        typeName, author, timeStamp, changeContent ) );
    }
    catch (XPathExpressionException e)
    {
      System.err.println( "Error finding " + pathExpr );
      e.printStackTrace( System.err );
    }
  }

  /*
    Find the first text of a change (insertion or format change)
    This is non-trivial, as you could have something like this:
    old stuff <text:change-start></text:p>
    <text:p>New stuff<text:change-end> old stuff

    the <text:change-start>'s next sibling won't work, because it has no
    next sibling. Instead, I have to get the first text node that follows
    in the file. This could be an all whitespace node, so I skip over those.
    (Note that this could give a wrong result, as the insertion or format change
    could be that whitespace node. So technically, this method returns the first
    nearest text node to where the change was.
  */
  String getFirstChangeText( Node startNode )
  {
    String result = null;
    String pathExpr = "following::text()[1]";

    try {
      startNode = (Node) xpath.evaluate( pathExpr, startNode, XPathConstants.NODE);
      while (startNode != null && result == null)
      {
        result = startNode.getNodeValue();
        if (result.matches( "^\\s*$"))
        {
          result = null;
        }

        if (result == null)
        {
          startNode = (Node) xpath.evaluate( pathExpr, startNode, XPathConstants.NODE);
        }
      }
    }
    catch (XPathExpressionException e)
    {
      System.err.println( "Error finding " + pathExpr );
      e.printStackTrace( System.err );
    }

    return result;
  }

  void createSpreadsheet( )
  {
    Iterator<ChangeInfo> iter = changeList.iterator();
    ChangeInfo item;
    try
    {
      OdfSpreadsheetDocument outDoc = new OdfSpreadsheetDocument( );
      OdfStyleCollection automaticStyles;

      OdfStyleProperty property;
      OdfStyle style;
      OdfTableColumnStyle colStyle;

      Node spreadsheet;
      Node body;
      Element autoStyleNode;
      Element dateStyle;

      OdfTable theTable;
      OdfTableHeaderRows tableHeaderRows;
      OdfTableRow row;
      OdfTableColumn column;
      OdfTableCell cell;
      OdfParagraph para;

      content = outDoc.getContent( );
      automaticStyles = outDoc.getAutomaticStyles( );

      /* Create a number:date style; not available from ODFDOM yet */
      dateStyle = createDateStyle( );

      /*
        Create a bold-centered style
      */
      style = new OdfTableCellStyle( "boldCenter", automaticStyles );
      style.setProperty( OdfTextProperties.FontWeight, "bold");
      style.setProperty( OdfParagraphProperties.TextAlign, "center" );

      /* A style to refer to the number:date style */
      style = new OdfTableCellStyle( "dateCell", automaticStyles );
      //style.setDataStyleName( "dateStyle" );

      /* Default style for other columns */
      colStyle = new OdfTableColumnStyle( "colstyle", automaticStyles );
      colStyle.setProperty( OdfTableColumnProperties.UseOptimalColumnWidth,
        "true" );

      /*
        The empty files shipped with ODFDOM don't contain the
        <office:spreadsheet> element due to a bug in OOo2.4
      */
      spreadsheet = content.createElementNS(OdfNamespace.OFFICE.getUri(),
        "spreadsheet");

      /* The table will go into the <office:body> */
      body = content.getElementsByTagNameNS(OdfNamespace.OFFICE.getUri(),
        "body").item(0);

      /* And our newly created styles will be children of the
        <office:automatic-styles> element */
      autoStyleNode = (Element)
        content.getElementsByTagNameNS(OdfNamespace.OFFICE.getUri(),
        "automatic-styles").item(0);

      autoStyleNode.appendChild( dateStyle );
      automaticStyles.appendToNode( autoStyleNode );

      theTable = new OdfTable( content );

      /* The first <table:table-column> is populated with dates */
      column = theTable.addTableColumn( colStyle );
      column.setDefaultCellStyleName( "dateCell" );

      /* The other three columns are just regular columns */
      theTable.addTableColumn( 3, colStyle );

      /* Create the header row(s) */
      tableHeaderRows = new OdfTableHeaderRows( content );
      row = new OdfTableRow( content );

      row.appendChild( constructStringCell( "Date", "boldCenter" ) );
      row.appendChild( constructStringCell( "Type", "boldCenter" ) );
      row.appendChild( constructStringCell( "Author", "boldCenter" ) );
      row.appendChild( constructStringCell( "Content", "boldCenter" ) );
      tableHeaderRows.appendChild( row );
      theTable.appendChild( tableHeaderRows );

      /*	At last! Put the changes in as new (non-header) rows
        and add each row to the table */
      while (iter.hasNext())
      {
        row = new OdfTableRow( content );
        item = iter.next();
        row.appendChild( constructDateCell( item.getTimeStamp() ) );
        row.appendChild( constructStringCell( item.getType() ) );
        row.appendChild( constructStringCell( item.getAuthor() ) );
        row.appendChild( constructStringCell( item.getChangeContent() ) );
        theTable.appendChild( row );
      }

      // finally, make the table a child of the <office:spreadsheet>
      spreadsheet.appendChild( theTable );

      // which is a child of <office:body>
      body.appendChild( spreadsheet );


      // and write the resulting file.
      // THIS WILL CALL odsDoc1.getContent() WHICH WILL DELETE THE STYLES!
      //outDoc.save( outputFileName );

      // Instead do it 'by hand' without calling getContent()
      OdfPackage mPackage = outDoc.getOdfPackage();
      mPackage.insert(content, "content.xml");
      mPackage.save(outputFileName);


    }
    catch (Exception e)
    {
      System.err.println("Unable to create output " + outputFileName );
      e.printStackTrace( System.err );
    }

  }

  /*
    Create a <number:date-style> element that has subelements for
    year MON day

    The numberURI variable saves us having to call OdfNamespace.NUMBER.getUri
    repeatedly.

    Ideally, this should be a generic function that splits up a format string
    and creates the correct style, but I am in a burning hurry and will just
    do the particular style I want.

    It's in this code because ODFDOM doesn't support the <number:...>
    elements yet.
  */
  Element createDateStyle( )
  {
    String numberURI = OdfNamespace.NUMBER.getUri();
    Element result = content.createElementNS( numberURI, "number:date-style");
    Element subElement;
    Element textElement;

    result.setAttributeNS( OdfNamespace.STYLE.getUri(), "style:name", "dateStyle" );

    subElement = content.createElementNS( numberURI, "number:year");
    subElement.setAttributeNS( numberURI, "number:style", "long" );
    result.appendChild( subElement );
    textElement = content.createElementNS( numberURI, "number:text" );
    textElement.appendChild( content.createTextNode( " " ) );
    result.appendChild( textElement );

    subElement = content.createElementNS( numberURI, "number:month");
    subElement.setAttributeNS( numberURI, "number:textual", "true" );
    result.appendChild( subElement );
    textElement = content.createElementNS( numberURI, "number:text" );
    textElement.appendChild( content.createTextNode( " " ) );
    result.appendChild( textElement );

    subElement = content.createElementNS( OdfNamespace.NUMBER.getUri(), "number:day");
    result.appendChild( subElement );

    return result;
  }

  /*
    A convenience function to create a table cell with a string
    content and no style name.
  */
  OdfTableCell constructStringCell( String s )
  {
    return constructStringCell( s, null );
  }

  /*
    Construct a cell with text String s inside it
    and the given cell style.
  */
  OdfTableCell constructStringCell( String s, String cellStyleName  )
  {
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
  }

  /*
    Construct a date cell with a given date and time
  */
  OdfTableCell constructDateCell( String dateTime ) throws Exception
  {
    OdfTableCell cell = new OdfTableCell( content );
    cell.setDateValue( DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime) );
    cell.setValueType( OdfValueType.DATE );
    return cell;
  }

  void process( )
  {
    extractChanges( );
    createSpreadsheet( );
    System.runFinalization();
  }

  public static void main( String[] args )
  {
    MakeChangeList app;

    if (args.length == 2)
    {
      app = new MakeChangeList( args );
      app.process( );
    }
    else
    {
      System.err.println("Usage: java MakeChangeList inputfile.odt outputfile.ods" );

      app = new MakeChangeList( new String[] { "test/changedfile.odt", "tmp/changedfileSS.ods"} );
      app.process( );

    }
  }
}
