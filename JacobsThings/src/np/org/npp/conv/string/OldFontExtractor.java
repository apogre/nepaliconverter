package np.org.npp.conv.string;


import java.util.*;
import java.io.*;

public class OldFontExtractor {

  static final char START = 33;
  static final char SLUT = 255;


  static NonUnicodeFont nu = new Preeti();

  static HashMap<String,String> map = new HashMap<String,String>();

  static int maxprint = 500;

  static PrintStream out;

  public static void rec(String ord, int n) {
    n=n-1;
    for (char c=33; c<255; c++) {
      if (c == 'l') continue; // ि
      if (ord.length()==0) {
        if (c == 109) continue; // ्क
        if (c >= 128 && c<=159) continue;
        if (c == 165 ) continue; //
        if (c == 171 ) continue; //
        if (c == 176 ) continue; //
        if (c == 212 ) continue; //
        if (c == 216 ) continue; //
      }

      if (n > 0) {
        rec(ord + c, n);
      }
      else {
        String is = ord+c;
        String os = nu.toUnicode("  "+is+"  ").trim();

        if (is.equals("m!")) {
          System.err.println("NU");
        }

        if (!is.equals(os) && nu.problemsInLastConversion.size()==0)
        {
          boolean kombi = false;
          for (int i=1;i<is.length(); i++) {
            String i1 =is.substring(0,i);
            String i2 =is.substring(i);
            String p1 = map.get(i1);
            String p2 = map.get(i2);
            if (os.equals(p1+p2)) kombi = true;
            if (os.equals(i1+p2)) kombi = true;
            if (os.equals(p1+i2)) kombi = true;
          }

          //Character.
          if (!kombi) {
            out.println(Integer.toHexString(c) + " "+ (int)c+" \t "+unicStr(is) + " \t "+is +" \t "+is + " \t" + os+ " \t " + splitStr(os)+ " \t " + unicStr(os));
            //out.println("<tr><td>"+is + "</td><td>" + os+ "</td></tr>");
            //System.out.println("'"+is + "' -> '" + os+ "'");
            //if (maxprint--<0) System.exit(0);
            map.put(is, os);
            Properties p;
          }
        }
      }
    }
  }

  public static String unicStr(String s) {
    StringBuffer outBuffer = new StringBuffer();

    for (int i=0; i<s.length(); i++) {
      char aChar = s.charAt(i);
      outBuffer.append(' ');
      outBuffer.append('\\');
      outBuffer.append('u');
      outBuffer.append(Integer.toHexString( (aChar >> 12) & 0xF));
      outBuffer.append(Integer.toHexString( (aChar >> 8) & 0xF));
      outBuffer.append(Integer.toHexString( (aChar >> 4) & 0xF));
      outBuffer.append(Integer.toHexString(aChar & 0xF));
    }
    return outBuffer.toString();

  }

  public static String splitStr(String s) {
    StringBuffer outBuffer = new StringBuffer();

    for (int i=0; i<s.length(); i++) {
      char aChar = s.charAt(i);
      outBuffer.append(' ');
      outBuffer.append(aChar);
    }
    return outBuffer.toString();

  }



  public static void main(String[] args) throws FileNotFoundException {
    out = new PrintStream("preeti.tsv");
    //out.print("<html><body><table>");
    rec("",1);
    rec("",2);
    //rec("",3);
    //out.print("</table></html>");
    out.close();
/*

    for (char i1=33; i1<255; i1++) {
      for (char i2=33; i2<255; i2++) {
        for (char i3=33; i3<255; i3++) {
          for (char i4=33; i4<255; i4++) {
            String is = "  "+i1+i2+i3+i4+"  ";
            String os = nu.toUnicode(is);
            if (!is.equals(os) && nu.problemsInLastConversion.size()==0) System.out.println((char)i4+": "+is +" -> " +os);
          }
          System.exit(0);
        }
      }
    }
*/
  }
}
