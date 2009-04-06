package vortaro;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;

public class Iloj {


public static void main(String[] args) throws IOException {
	String l = alCxapeloj("cxgxhxjxsxux");
	for (int i=0; i<l.length(); i++) {
		char c = l.charAt(i);
		System.out.println(Integer.toHexString(c)+ " " + (int) c + " " + c);
	}
}

	public static String deCxapeloj(String teksto) {
			teksto = teksto.replaceAll("ĉ", "cx");
			teksto = teksto.replaceAll("ĝ", "gx");
			teksto = teksto.replaceAll("ĥ", "hx");
			teksto = teksto.replaceAll("ĵ", "jx");
			teksto = teksto.replaceAll("ŝ", "sx");
			teksto = teksto.replaceAll("ŭ", "ux");
			teksto = teksto.replaceAll("Ĉ", "Cx");
			teksto = teksto.replaceAll("Ĝ", "Gx");
			teksto = teksto.replaceAll("Ĥ", "Hx");
			teksto = teksto.replaceAll("Ĵ", "Jx");
			teksto = teksto.replaceAll("Ŝ", "Sx");
			teksto = teksto.replaceAll("Ŭ", "Ux");
			return teksto;
	}


	public static String alCxapeloj(String teksto) {
			teksto = teksto.replaceAll("cx", "ĉ");
			teksto = teksto.replaceAll("gx", "ĝ");
			teksto = teksto.replaceAll("hx", "ĥ");
			teksto = teksto.replaceAll("jx", "ĵ");
			teksto = teksto.replaceAll("sx", "ŝ");
			teksto = teksto.replaceAll("ux", "ŭ");
			teksto = teksto.replaceAll("Cx", "Ĉ");
			teksto = teksto.replaceAll("Gx", "Ĝ");
			teksto = teksto.replaceAll("Hx", "Ĥ");
			teksto = teksto.replaceAll("Jx", "Ĵ");
			teksto = teksto.replaceAll("Sx", "Ŝ");
			teksto = teksto.replaceAll("Ux", "Ŭ");
			return teksto;
	}



	public static Collator usCollator = Collator.getInstance(Locale.US);

	public static Comparator eocomparator = new Comparator<String>() {
    int xx;
			public int compare(String o1, String o2) {
        o1 = foriguLigojnKajSpacojn(o1);
        o2 = foriguLigojnKajSpacojn(o2);
//        if (xx++<500) System.err.println(o1+" ------- "+o2);
        if (o1.startsWith("(")) o1 = o1.substring(o1.indexOf(")")+1);
        if (o2.startsWith("(")) o2 = o2.substring(o2.indexOf(")")+1);
        while (o1.length()>0 && !Character.isLetter(o1.charAt(0))) o1 = o1.substring(1);
        while (o2.length()>0 && !Character.isLetter(o2.charAt(0))) o2 = o2.substring(1);
//        if (xx++<500) System.err.println(o1+" ------- "+o2);

		return usCollator.compare(deCxapeloj(o1).toLowerCase(), deCxapeloj(o2).toLowerCase());
			}
	};


	public static String legu(File fil) throws IOException
	{
		FileChannel fc = new FileInputStream(fil).getChannel();
		MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fil.length());
		//CharBuffer cb = Charset.forName("ISO-8859-1").decode(bb);
		CharBuffer cb = Charset.forName("UTF-8").decode(bb);
		return new String(cb.array());
	}


	public static String deDevAlRomana(String dev)
	{
		for (int i=0; i<dev.length(); i++) {
			char d = dev.charAt(i);

		}
		return dev;
	}

  public static String foriguLigojnKajSpacojn(String esp) {
    //dev = dev.replaceAll("<text:a [^>]*> *\\w</text:a>",""); // forigu cxiuj unuliterajn ligojn
    //dev = dev.replaceAll("<text:span text:style-name=\"\\w\">(\\s)</text:span>","$1"); // forigu tipojn kiu nur kosideras spacojn
    esp = esp.replaceAll("\\s?<text:a [^>]*>\\s*\\w</text:a>", ""); // forigu cxiuj unuliterajn ligojn kaj evt spaco antauxe
    esp = esp.replaceAll("<text:span text:style-name=\"\\w\">(\\s)*</text:span>", "$1"); // forigu tipojn kiu nur kosideras spacojn
    esp = esp.replaceAll("<text:soft-page-break/>", "");
    return esp;
  }

}
