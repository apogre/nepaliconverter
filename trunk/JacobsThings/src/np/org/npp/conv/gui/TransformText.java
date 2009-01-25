package np.org.npp.conv.gui;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import np.org.npp.conv.gui.*;
import np.org.mpp.old.old_f2u.*;

public class TransformText
{


    public static void main(String[] args) throws IOException {
	konvertuDeHtml();
    }


    public static String laes(File fil) throws IOException
    {
      FileChannel fc = new FileInputStream(fil).getChannel();
      MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fil.length());
      CharBuffer cb = Charset.forName("ISO-8859-1").decode(bb);
      //CharBuffer cb = Charset.forName("UTF-16").decode(bb);
      return new String(cb.array());
    }

    public static void konvertuDeHtml() throws IOException {

	String tuto = laes(new File("vorto02txt.txt"));
	PrintWriter pw = new PrintWriter(new FileWriter("rezulto.html"));

	//Font2Unicode font2Unicode = new Font2Unicode();

	try {

	    //System.out.println(tuto);
	    String[] lin = tuto.split("\n");
	    for (int linioNro = 0; linioNro <lin.length; linioNro++) { // 83 lin.length lin.length

              String l = lin[linioNro];
              int n = l.indexOf("\t");
              if (n==-1) n=l.lastIndexOf("   ");
              if (n==-1) n=l.lastIndexOf("  ");
              if (n==-1) n=l.lastIndexOf(" ");
              if (n==-1) n=l.length()-1;
		String ne = l.substring(0,n);
		String eo = l.substring(n);

                System.out.println(ne + " ** " + eo);

                String ne2 = ne; //font2Unicode.toUnicode("Kantipur", ne);


		pw.println("<tr><td>"+ne + "</td><td>" +ne2+ "</td><td>" + eo+ "</td></tr>");
                System.out.println("<tr><td>"+ne + "</td><td>" +ne2+ "</td><td>" + eo+ "</td></tr>");
		//if (l.contains("tajxafenestro")) break;
	    }
	} finally {
	    pw.close();
	}
	System.out.println("==============================\nnetraktitaj: "+netraktitaj);


    }






    static String netraktitaj = "";



}
