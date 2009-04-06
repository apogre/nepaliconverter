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
package np.esperanto.conv4.unused_dnd;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

/**
 * A transfer handler for file(s) and HTML We will need a HTML parser/generator.
 * 
 * Perhaps http://lobobrowser.org/cobra.jsp or
 * http://htmlparser.sourceforge.net/
 * <p>
 * Title: Nepaliconverter
 * </p>
 * 
 * <p>
 * Description: Conversion tools for Nepali and devanagari text
 * </p>
 * 
 * @author Jacob Nordfalk
 * @version 4.0
 */

public class FilesAndHtmlTransferHandler extends TransferHandler {
    public JEditorPane console;

    public DataFlavor fileDf, htmlDfByteUtf8;

    public FilesAndHtmlTransferHandler() {
	StringSelection s;
	try {
	    // fileDf = new DataFlavor("text/uri-list; class=java.lang.String");
	    // htmlDf = new DataFlavor("text/html; class=java.lang.String"); //
	    // charset=unicode;
	    fileDf = new DataFlavor("text/uri-list; class=java.lang.String");

	    // Works fine Ubuntu Linux 8.04, jdk1.6.0, from OpenOffice.org
	    // 2.4.1:
	    htmlDfByteUtf8 = new DataFlavor(
		    "text/html; class=\"[B\"; charset=UTF-8"); // charset=unicode;
	    // charset=UTF-8
	} catch (ClassNotFoundException ex) {
	    ex.printStackTrace();
	}
    }

    public static void main(String[] args) {
	FilesAndHtmlTransferHandler th = new FilesAndHtmlTransferHandler();

	// Observes the selection (copy/cut in text documents detected)
	// new
	// ClipboardObserver(Toolkit.getDefaultToolkit().getSystemClipboard(),
	// th.htmlDfByteUtf8);

	// Observes the selection (also without copy)
	// new
	// ClipboardObserver(Toolkit.getDefaultToolkit().getSystemSelection(),
	// DataFlavor.stringFlavor);
	th.console = new JEditorPane("text/plain", "drag files/html here");
	th.console.setPreferredSize(new Dimension(600, 200));
	th.console.setTransferHandler(th);
	JOptionPane.showConfirmDialog(null, new JScrollPane(th.console));
    }

    @Override
    public boolean canImport(JComponent arg0, DataFlavor[] arg1) {

	// public boolean canImport(TransferHandler.TransferSupport support) {

	return true;
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
	StringBuffer status = new StringBuffer();
	logAllDataFlavors(t, status);

	System.out.println(status);

	try {

	    if (t.isDataFlavorSupported(fileDf)) {
		String s = (String) t.getTransferData(fileDf);
		System.out.println("s = " + s);
		console.setText("FILES\n" + s);
		return true;
	    }

	    if (t.isDataFlavorSupported(htmlDfByteUtf8)) {
		// status.append("HRML"+htmlDf);
		String s = getHtml(t);
		console.setText("HTML\n" + s);
		return true;
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	/*
	 * if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
	 * status.append("FILE LIST!"); }
	 * 
	 * if (t.isDataFlavorSupported(DataFlavor.getTextPlainUnicodeFlavor())) {
	 * status.append("TEXT! getTextPlainUnicodeFlavor()"); }
	 * 
	 * if
	 * (t.isDataFlavorSupported(DataFlavor.selectBestTextFlavor(t.getTransferDataFlavors()))) {
	 * status.append("TEXT! selectBestTextFlavor()"); DataFlavor df =
	 * DataFlavor.selectBestTextFlavor(t.getTransferDataFlavors());
	 * //t.getTransferData(df); }
	 */

	if (console != null)
	    console.setText(status.toString());
	return true;
    }

    public String getHtml(Object transferData) throws IOException,
	    UnsupportedFlavorException {
	byte[] b = (byte[]) transferData;
	String s = new String(b, "UTF-8");
	System.out.println("s = " + s);

	StringBuffer sb = new StringBuffer(s.length());
	for (int i = 0; i < s.length(); i++) {
	    char ch = s.charAt(i);
	    // System.out.println((int) ch +" "+ ch);
	    if (ch != 0)
		sb.append(ch);
	}
	return s.toString();
    }

    public String getHtml(Transferable t) throws IOException,
	    UnsupportedFlavorException {
	return getHtml(t.getTransferData(htmlDfByteUtf8));
    }

    public static void logAllDataFlavors(Transferable t, StringBuffer log) {
	// support.getDataFlavors()

	DataFlavor[] fl = t.getTransferDataFlavors();
	// DataFlavor[] flx = support.getDataFlavors();
	// System.err.println("t.getTransferDataFlavors()=" + flx.length);
	System.err.println("support.getDataFlavors()=" + fl.length);

	for (int i = 0; i < fl.length; i++) {
	    DataFlavor f = fl[i];

	    log.append("f[" + i + "]=" + f.getMimeType() + "\n");

	    try {
		System.err.println();
		System.err.println(" f[" + i + "]=" + fl[i].toString() + " "
			+ fl[i].getHumanPresentableName());
		System.err.println("  " + t.getTransferData(fl[i]));
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    /*
	     * if (f.getHumanPresentableName().equals("text/uri-list")) { try {
	     * List l = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
	     * status.append("ITS A FILE LIST: " + l); } catch (Exception ex) {
	     * //ex.printStackTrace(); } }
	     */

	    if (f.getRepresentationClass() == InputStream.class) {
		try {
		    InputStream l = (InputStream) t.getTransferData(f);
		    new File("clipboardData").mkdirs();
		    FileOutputStream fos = new FileOutputStream(
			    "clipboardData/clipboard"
				    + i
				    + "_"
				    + f.getHumanPresentableName().replaceAll(
					    "/", ""));
		    int j;
		    while ((j = l.read()) != -1) {
			fos.write(j);
			System.err.print((char) j);
		    }
		    System.err.println();
		    fos.close();
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	    }

	}

	for (int i = 0; i < fl.length; i++) {
	    DataFlavor f = fl[i];

	    /*
	     * if (f.getHumanPresentableName().equals("text/html") &&
	     * f.getRepresentationClass() == String.class) { try { Object l =
	     * t.getTransferData(f); status.append("ITS A TEXT: " + l); break; }
	     * catch (Exception ex) { ex.printStackTrace(); } }
	     */
	}
    }

}
/*
 * TRANSFER HANDLER LOGS
 * 
 * Ubuntu Linux 8.04, jdk1.6.0, from OpenOffice.org 2.4.1:
 * f[0]=application/x-openoffice-link; windows_formatname=Link;
 * class=java.io.InputStream 
 * f[1]=application/x-openoffice-objectdescriptor-xml;
 * windows_formatname="Star Object Descriptor (XML)"; class=java.io.InputStream
 * f[2]=application/x-openoffice-embed-source-xml; windows_formatname="Star
 * Embed Source (XML)"; class=java.io.InputStream 
 * f[3]=text/html;class=java.io.Reader; charset=Unicode 
 * f[4]=text/html; class=java.lang.String; charset=Unicode 
 * f[5]=text/html; class=java.nio.CharBuffer; charset=Unicode
 * f[6]=text/html; class="[C"; charset=Unicode 
 * f[7]=text/html; class=java.io.InputStream; charset=UTF-16 
 * f[8]=text/html; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[9]=text/html; class="[B"; charset=UTF-16 
 * f[10]=text/html; class=java.io.InputStream; charset=UTF-8
 * f[11]=text/html; class=java.nio.ByteBuffer; charset=UTF-8 
 * f[12]=text/html; class="[B"; charset=UTF-8 
 * f[13]=text/html; class=java.io.InputStream; charset=UTF-16BE 
 * f[14]=text/html; class=java.nio.ByteBuffer; charset=UTF-16BE
 * f[15]=text/html; class="[B"; charset=UTF-16BE 
 * f[16]=text/html; class=java.io.InputStream; charset=UTF-16LE 
 * f[17]=text/html; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[18]=text/html; class="[B"; charset=UTF-16LE 
 * f[19]=text/html; class=java.io.InputStream; charset=ISO-8859-1 
 * f[20]=text/html; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[21]=text/html; class="[B"; charset=ISO-8859-1
 * f[22]=text/html; class=java.io.InputStream; charset=US-ASCII 
 * f[23]=text/html; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[24]=text/html; class="[B"; charset=US-ASCII 
 * f[25]=text/richtext; class=java.io.Reader; charset=Unicode
 * f[26]=text/richtext; class=java.lang.String; charset=Unicode
 * f[27]=text/richtext; class=java.nio.CharBuffer; charset=Unicode
 * f[28]=text/richtext; class="[C"; charset=Unicode 
 * f[29]=text/richtext; class=java.io.InputStream; charset=UTF-16 
 * f[30]=text/richtext; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[31]=text/richtext; class="[B"; charset=UTF-16 
 * f[32]=text/richtext; class=java.io.InputStream; charset=UTF-8
 * f[33]=text/richtext; class=java.nio.ByteBuffer; charset=UTF-8
 * f[34]=text/richtext; class="[B"; charset=UTF-8 
 * f[35]=text/richtext; class=java.io.InputStream; charset=UTF-16BE 
 * f[36]=text/richtext; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[37]=text/richtext; class="[B"; charset=UTF-16BE 
 * f[38]=text/richtext; class=java.io.InputStream; charset=UTF-16LE 
 * f[39]=text/richtext; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[40]=text/richtext; class="[B"; charset=UTF-16LE
 * f[41]=text/richtext; class=java.io.InputStream; charset=ISO-8859-1
 * f[42]=text/richtext; class=java.nio.ByteBuffer; charset=ISO-8859-1
 * f[43]=text/richtext; class="[B"; charset=ISO-8859-1 
 * f[44]=text/richtext; class=java.io.InputStream; charset=US-ASCII 
 * f[45]=text/richtext; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[46]=text/richtext; class="[B"; charset=US-ASCII 
 * f[47]=application/x-java-serialized-object; class=java.lang.String 
 * f[48]=text/plain; class=java.io.Reader; charset=Unicode 
 * f[49]=text/plain; class=java.lang.String; charset=Unicode
 * f[50]=text/plain; class=java.nio.CharBuffer; charset=Unicode
 * f[51]=text/plain; class="[C"; charset=Unicode 
 * f[52]=text/plain; class=java.io.InputStream; charset=unicode 
 * f[53]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[54]=text/plain; class="[B"; charset=UTF-16 
 * f[55]=text/plain; class=java.io.InputStream; charset=UTF-8
 * f[56]=text/plain; class=java.nio.ByteBuffer; charset=UTF-8 
 * f[57]=text/plain; class="[B"; charset=UTF-8 
 * f[58]=text/plain; class=java.io.InputStream; charset=UTF-16BE 
 * f[59]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[60]=text/plain; class="[B"; charset=UTF-16BE
 * f[61]=text/plain; class=java.io.InputStream; charset=UTF-16LE
 * f[62]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16LE
 * f[63]=text/plain; class="[B"; charset=UTF-16LE 
 * f[64]=text/plain; class=java.io.InputStream; charset=ISO-8859-1 
 * f[65]=text/plain; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[66]=text/plain; class="[B"; charset=ISO-8859-1 
 * f[67]=text/plain; class=java.io.InputStream; charset=US-ASCII 
 * f[68]=text/plain; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[69]=text/plain; class="[B"; charset=US-ASCII
 * 
 * 
 * Ubuntu Linux 8.04, jdk1.6.0, from Gnome file manager (one dir, one file or
 * several files/dirs gives same result). æøå OK
 * f[0]=application/x-java-serialized-object; class=java.lang.String
 * f[1]=x-special/gnome-copied-files; class=java.io.InputStream 
 * f[2]=text/plain; class=java.io.Reader; charset=Unicode 
 * f[3]=text/plain; class=java.lang.String; charset=Unicode 
 * f[4]=text/plain; class=java.nio.CharBuffer; charset=Unicode 
 * f[5]=text/plain; class="[C"; charset=Unicode 
 * f[6]=text/plain; class=java.io.InputStream; charset=unicode
 * f[7]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[8]=text/plain; class="[B"; charset=UTF-16 
 * f[9]=text/plain; class=java.io.InputStream; charset=UTF-8 
 * f[10]=text/plain; class=java.nio.ByteBuffer; charset=UTF-8
 * f[11]=text/plain; class="[B"; charset=UTF-8 
 * f[12]=text/plain; class=java.io.InputStream; charset=UTF-16BE 
 * f[13]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[14]=text/plain; class="[B"; charset=UTF-16BE 
 * f[15]=text/plain; class=java.o.InputStream; charset=UTF-16LE 
 * f[16]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[17]=text/plain; class="[B"; charset=UTF-16LE
 * f[18]=text/plain; class=java.io.InputStream; charset=ISO-8859-1
 * f[19]=text/plain; class=java.nio.ByteBuffer; charset=ISO-8859-1
 * f[20]=text/plain; class="[B"; charset=ISO-8859-1 
 * f[21]=text/plain; class=java.io.InputStream; charset=US-ASCII 
 * f[22]=text/plain; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[23]=text/plain; class="[B"; charset=US-ASCII
 * 
 * 
 * Ubuntu Linux 8.04, jdk1.6.0, from Mozilla 3.0. THE HTML OPTIONS SEEMS BROKEN!
 * f[0]=text/html; class=java.io.Reader; charset=Unicode 
 * f[1]=text/html; class=java.lang.String; charset=Unicode 
 * f[2]=text/html; class=java.nio.CharBuffer; charset=Unicode 
 * f[3]=text/html; class="[C"; charset=Unicode 
 * f[4]=text/html; class=java.io.InputStream; charset=UTF-16
 * f[5]=text/html; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[6]=text/html; class="[B"; charset=UTF-16 
 * f[7]=text/html; class=java.io.InputStream; charset=UTF-8 
 * f[8]=text/html; class=java.nio.ByteBuffer; charset=UTF-8
 * f[9]=text/html; class="[B"; charset=UTF-8 
 * f[10]=text/html; class=java.io.InputStream; charset=UTF-16BE 
 * f[11]=text/html; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[12]=text/html; class="[B"; charset=UTF-16BE 
 * f[13]=text/html; class=java.io.InputStream; charset=UTF-16LE
 * f[14]=text/html; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[15]=text/html; class="[B"; charset=UTF-16LE 
 * f[16]=text/html; class=java.io.InputStream; charset=ISO-8859-1 
 * f[17]=text/html; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[18]=text/html; class="[B"; charset=ISO-8859-1
 * f[19]=text/html; class=java.io.InputStream; charset=US-ASCII 
 * f[20]=text/html; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[21]=text/html; class="[B"; charset=US-ASCII 
 * f[22]=application/x-java-serialized-object; class=java.lang.String 
 * f[23]=text/plain; class=java.io.Reader; charset=Unicode 
 * f[24]=text/plain; class=java.lang.String; charset=Unicode
 * f[25]=text/plain; class=java.nio.CharBuffer; charset=Unicode
 * f[26]=text/plain; class="[C"; charset=Unicode 
 * f[27]=text/plain; class=java.io.InputStream; charset=unicode 
 * f[28]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[29]=text/plain; class="[B"; charset=UTF-16 
 * f[30]=text/plain; class=java.io.InputStream; charset=UTF-8
 * f[31]=text/plain; class=java.nio.ByteBuffer; charset=UTF-8 
 * f[32]=text/plain; class="[B"; charset=UTF-8 
 * f[33]=text/plain; class=java.io.InputStream; charset=UTF-16BE 
 * f[34]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[35]=text/plain; class="[B"; charset=UTF-16BE
 * f[36]=text/plain; class=java.io.InputStream; charset=UTF-16LE
 * f[37]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16LE
 * f[38]=text/plain; class="[B"; charset=UTF-16LE
 * f[39]=text/plain; class=java.io.InputStream; charset=ISO-8859-1 
 * f[40]=text/plain; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[41]=text/plain; class="[B"; charset=ISO-8859-1 
 * f[42]=text/plain; class=java.io.InputStream; charset=US-ASCII 
 * f[43]=text/plain; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[44]=text/plain; class="[B"; charset=US-ASCII
 * f[45]=text/x-moz-url-priv; class=java.io.InputStream
 * f[46]=text/_moz_htmlinfo; class=java.io.InputStream
 * f[47]=text/_moz_htmlcontext; class=java.io.InputStream
 * f[48]=text/x-moz-url-priv; class=java.nio.ByteBuffer
 * f[49]=text/_moz_htmlinfo; class=java.nio.ByteBuffer
 * f[50]=text/_moz_htmlcontext; class=java.nio.ByteBuffer
 * f[51]=text/x-moz-url-priv; class="[B" f[52]=text/_moz_htmlinfo; class="[B"
 * f[53]=text/_moz_htmlcontext; class="[B"
 * 
 * Microsoft Windows XP SP3, jdk1.6.6, from OpenOffice.org 2.4.0
 * f[0]=application/x-java-text-encoding; class="[B" 
 * f[1]=text/html; class=java.io.Reader; charset=Unicode 
 * f[2]=text/html; class=java.lang.String; charset=Unicode 
 * f[3]=text/html; class=java.nio.CharBuffer; charset=Unicode
 * f[4]=text/html; class="[C"; charset=Unicode 
 * f[5]=text/html; class=java.io.InputStream; charset=UTF-16 
 * f[6]=text/html; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[7]=text/html; class="[B"; charset=UTF-16 
 * f[8]=text/html; class=java.io.InputStream; charset=UTF-8
 * f[9]=text/html; class=java.nio.ByteBuffer; charset=UTF-8 
 * f[10]=text/html; class="[B"; charset=UTF-8 
 * f[11]=text/html; class=java.io.InputStream; charset=UTF-16BE 
 * f[12]=text/html; class=java.nio.ByteBuffer; charset=UTF-16BE
 * f[13]=text/html; class="[B"; charset=UTF-16BE 
 * f[14]=text/html; class=java.io.InputStream; charset=UTF-16LE 
 * f[15]=text/html; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[16]=text/html; class="[B"; charset=UTF-16LE 
 * f[17]=text/html; class=java.io.InputStream; charset=ISO-8859-1 
 * f[18]=text/html; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[19]=text/html; class="[B"; charset=ISO-8859-1
 * f[20]=text/html; class=java.io.InputStream; charset=windows-1252
 * f[21]=text/html; class=java.nio.ByteBuffer; charset=windows-1252
 * f[22]=text/html; class="[B"; charset=windows-1252 
 * f[23]=text/html; class=java.io.InputStream; charset=US-ASCII 
 * f[24]=text/html; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[25]=text/html; class="[B"; charset=US-ASCII 
 * f[26]=text/rtf; class=java.io.InputStream 
 * f[27]=text/rtf; class=java.nio.ByteBuffer 
 * f[28]=text/rtf; class="[B"
 * f[29]=application/x-java-serialized-object; class=java.lang.String
 * f[30]=text/plain; class=java.io.Reader; charset=Unicode 
 * f[31]=text/plain; class=java.lang.String; charset=Unicode 
 * f[32]=text/plain; class=java.nio.CharBuffer; charset=Unicode 
 * f[33]=text/plain; class="[C"; charset=Unicode 
 * f[34]=text/plain; class=java.io.InputStream; charset=unicode
 * f[35]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[36]=text/plain; class="[B"; charset=UTF-16 
 * f[37]=text/plain; class=java.io.InputStream; charset=UTF-8 
 * f[38]=text/plain; class=java.nio.ByteBuffer; charset=UTF-8
 * f[39]=text/plain; class="[B"; charset=UTF-8 
 * f[40]=text/plain; class=java.io.InputStream; charset=UTF-16BE 
 * f[41]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[42]=text/plain; class="[B"; charset=UTF-16BE 
 * f[43]=text/plain; class=java.io.InputStream; charset=UTF-16LE 
 * f[44]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[45]=text/plain; class="[B"; charset=UTF-16LE
 * f[46]=text/plain; class=java.io.InputStream; charset=ISO-8859-1
 * f[47]=text/plain; class=java.nio.ByteBuffer; charset=ISO-8859-1
 * f[48]=text/plain; class="[B"; charset=ISO-8859-1 
 * f[49]=text/plain; class=java.io.InputStream; charset=windows-1252 
 * f[50]=text/plain; class=java.nio.ByteBuffer; charset=windows-1252 
 * f[51]=text/plain; class="[B"; charset=windows-1252 
 * f[52]=text/plain; class=java.io.InputStream; charset=US-ASCII 
 * f[53]=text/plain; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[54]=text/plain; class="[B"; charset=US-ASCII
 * 
 * Microsoft Windows XP SP3, jdk1.6.6, from Mozilla 3.0
 * f[0]=application/x-java-text-encoding; class="[B" 
 * f[1]=text/html; class=java.io.Reader; charset=Unicode 
 * f[2]=text/html; class=java.lang.String; charset=Unicode 
 * f[3]=text/html; class=java.nio.CharBuffer; charset=Unicode
 * f[4]=text/html; class="[C"; charset=Unicode 
 * f[5]=text/html; class=java.io.InputStream; charset=UTF-16 
 * f[6]=text/html; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[7]=text/html; class="[B"; charset=UTF-16 
 * f[8]=text/html; class=java.io.InputStream; charset=UTF-8
 * f[9]=text/html; class=java.nio.ByteBuffer; charset=UTF-8 
 * f[10]=text/html; class="[B"; charset=UTF-8 
 * f[11]=text/html; class=java.io.InputStream; charset=UTF-16BE 
 * f[12]=text/html; class=java.nio.ByteBuffer; charset=UTF-16BE
 * f[13]=text/html; class="[B"; charset=UTF-16BE 
 * f[14]=text/html; class=java.io.InputStream; charset=UTF-16LE 
 * f[15]=text/html; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[16]=text/html; class="[B"; charset=UTF-16LE 
 * f[17]=text/html; class=java.io.InputStream; charset=ISO-8859-1 
 * f[18]=text/html; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[19]=text/html; class="[B"; charset=ISO-8859-1
 * f[20]=text/html; class=java.io.InputStream; charset=windows-1252
 * f[21]=text/html; class=java.nio.ByteBuffer; charset=windows-1252
 * f[22]=text/html; class="[B"; charset=windows-1252 
 * f[23]=text/html; class=java.io.InputStream; charset=US-ASCII 
 * f[24]=text/html; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[25]=text/html; class="[B"; charset=US-ASCII 
 * f[26]=application/x-java-serialized-object; class=java.lang.String 
 * f[27]=text/plain; class=java.io.Reader; charset=Unicode 
 * f[28]=text/plain; class=java.lang.String; charset=Unicode
 * f[29]=text/plain; class=java.nio.CharBuffer; charset=Unicode
 * f[30]=text/plain; class="[C"; charset=Unicode 
 * f[31]=text/plain; class=java.io.InputStream; charset=unicode 
 * f[32]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[33]=text/plain; class="[B"; charset=UTF-16 
 * f[34]=text/plain; class=java.io.InputStream; charset=UTF-8
 * f[35]=text/plain; class=java.nio.ByteBuffer; charset=UTF-8 
 * f[36]=text/plain; class="[B"; charset=UTF-8 
 * f[37]=text/plain; class=java.io.InputStream; charset=UTF-16BE 
 * f[38]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[39]=text/plain; class="[B"; charset=UTF-16BE
 * f[40]=text/plain; class=java.io.InputStream; charset=UTF-16LE
 * f[41]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16LE
 * f[42]=text/plain; class="[B"; charset=UTF-16LE 
 * f[43]=text/plain; class=java.io.InputStream; charset=ISO-8859-1 
 * f[44]=text/plain; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[45]=text/plain; class="[B"; charset=ISO-8859-1 
 * f[46]=text/plain; class=java.io.InputStream; charset=windows-1252 
 * f[47]=text/plain; class=java.nio.ByteBuffer; charset=windows-1252 
 * f[48]=text/plain; class="[B"; charset=windows-1252
 * f[49]=text/plain; class=java.io.InputStream; charset=US-ASCII
 * f[50]=text/plain; class=java.nio.ByteBuffer; charset=US-ASCII
 * f[51]=text/plain; class="[B"; charset=US-ASCII
 * 
 * 
 * Microsoft Windows XP SP3, jdk1.6.6, from Microsoft Word 2003
 * f[0]=application/x-java-text-encoding; class="[B" 
 * f[1]=text/html; class=java.io.Reader; charset=Unicode 
 * f[2]=text/html; class=java.lang.String; charset=Unicode 
 * f[3]=text/html; class=java.nio.CharBuffer; charset=Unicode
 * f[4]=text/html; class="[C"; charset=Unicode 
 * f[5]=text/html; class=java.io.InputStream; charset=UTF-16 
 * f[6]=text/html; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[7]=text/html; class="[B"; charset=UTF-16 
 * f[8]=text/html; class=java.io.InputStream; charset=UTF-8
 * f[9]=text/html; class=java.nio.ByteBuffer; charset=UTF-8 
 * f[10]=text/html; class="[B"; charset=UTF-8 
 * f[11]=text/html; class=java.io.InputStream; charset=UTF-16BE 
 * f[12]=text/html; class=java.nio.ByteBuffer; charset=UTF-16BE
 * f[13]=text/html; class="[B"; charset=UTF-16BE 
 * f[14]=text/html; class=java.io.InputStream; charset=UTF-16LE 
 * f[15]=text/html; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[16]=text/html; class="[B"; charset=UTF-16LE 
 * f[17]=text/html; class=java.io.InputStream; charset=ISO-8859-1 
 * f[18]=text/html; class=java.nio.ByteBuffer; charset=ISO-8859-1 
 * f[19]=text/html; class="[B"; charset=ISO-8859-1
 * f[20]=text/html; class=java.io.InputStream; charset=windows-1252
 * f[21]=text/html; class=java.nio.ByteBuffer; charset=windows-1252
 * f[22]=text/html; class="[B"; charset=windows-1252 
 * f[23]=text/html; class=java.io.InputStream; charset=US-ASCII 
 * f[24]=text/html; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[25]=text/html; class="[B"; charset=US-ASCII 
 * f[26]=text/rtf; class=java.io.InputStream 
 * f[27]=text/rtf; class=java.nio.ByteBuffer 
 * f[28]=text/rtf; class="[B"
 * f[29]=application/x-java-serialized-object; class=java.lang.String
 * f[30]=image/x-java-image; class=java.awt.Image 
 * f[31]=text/plain; class=java.io.Reader; charset=Unicode 
 * f[32]=text/plain; class=java.lang.String; charset=Unicode 
 * f[33]=text/plain; class=java.nio.CharBuffer; charset=Unicode 
 * f[34]=text/plain; class="[C"; charset=Unicode 
 * f[35]=text/plain; class=java.io.InputStream; charset=unicode
 * f[36]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[37]=text/plain; class="[B"; charset=UTF-16 
 * f[38]=text/plain; class=java.io.InputStream; charset=UTF-8 
 * f[39]=text/plain; class=java.nio.ByteBuffer; charset=UTF-8
 * f[40]=text/plain; class="[B"; charset=UTF-8 
 * f[41]=text/plain; class=java.io.InputStream; charset=UTF-16BE 
 * f[42]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[43]=text/plain; class="[B"; charset=UTF-16BE 
 * f[44]=text/plain; class=java.io.InputStream; charset=UTF-16LE 
 * f[45]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[46]=text/plain; class="[B"; charset=UTF-16LE
 * f[47]=text/plain; class=java.io.InputStream; charset=ISO-8859-1
 * f[48]=text/plain; class=java.nio.ByteBuffer; charset=ISO-8859-1
 * f[49]=text/plain; class="[B"; charset=ISO-8859-1 
 * f[50]=text/plain; class=java.io.InputStream; charset=windows-1252 
 * f[51]=text/plain; class=java.nio.ByteBuffer; charset=windows-1252 
 * f[52]=text/plain; class="[B"; charset=windows-1252 
 * f[53]=text/plain; class=java.io.InputStream; charset=US-ASCII 
 * f[54]=text/plain; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[55]=text/plain; class="[B"; charset=US-ASCII
 * 
 * Microsoft Windows XP SP3, jdk1.6.6, from Notepad
 * f[0]=application/x-java-text-encoding; class="[B"
 * f[1]=application/x-java-serialized-object; class=java.lang.String
 * f[2]=text/plain; class=java.io.Reader; charset=Unicode 
 * f[3]=text/plain; class=java.lang.String; charset=Unicode 
 * f[4]=text/plain; class=java.nio.CharBuffer; charset=Unicode 
 * f[5]=text/plain; class="[C"; charset=Unicode 
 * f[6]=text/plain; class=java.io.InputStream; charset=unicode
 * f[7]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16 
 * f[8]=text/plain; class="[B"; charset=UTF-16 
 * f[9]=text/plain; class=java.io.InputStream; charset=UTF-8 
 * f[10]=text/plain; class=java.nio.ByteBuffer; charset=UTF-8
 * f[11]=text/plain; class="[B"; charset=UTF-8 
 * f[12]=text/plain; class=java.io.InputStream; charset=UTF-16BE 
 * f[13]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16BE 
 * f[14]=text/plain; class="[B"; charset=UTF-16BE 
 * f[15]=text/plain; class=java.io.InputStream; charset=UTF-16LE 
 * f[16]=text/plain; class=java.nio.ByteBuffer; charset=UTF-16LE 
 * f[17]=text/plain; class="[B"; charset=UTF-16LE
 * f[18]=text/plain; class=java.io.InputStream; charset=ISO-8859-1
 * f[19]=text/plain; class=java.nio.ByteBuffer; charset=ISO-8859-1
 * f[20]=text/plain; class="[B"; charset=ISO-8859-1 
 * f[21]=text/plain; class=java.io.InputStream; charset=windows-1252 
 * f[22]=text/plain; class=java.nio.ByteBuffer; charset=windows-1252 
 * f[23]=text/plain; class="[B"; charset=windows-1252 
 * f[24]=text/plain; class=java.io.InputStream; charset=US-ASCII 
 * f[25]=text/plain; class=java.nio.ByteBuffer; charset=US-ASCII 
 * f[26]=text/plain; class="[B"; charset=US-ASCII
 */
