package vortaro;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.net.*;

public class AliajVortaroj {

  static boolean liguAlLokajKopioj = false;

  static HashMap<String,String> revo  = new HashMap<String,String>(40000);
  static HashMap<String,String> viki  = new HashMap<String,String>(40000);
  static HashMap<String,String> npviki  = new HashMap<String,String>(40000);
  static HashMap<String,String> nepalisabdakos  = new HashMap<String,String>(40000);

  static File aliaj_vortaroj = new File("../aliaj_vortaroj/");

  static {
    try {
      aliaj_vortaroj = aliaj_vortaroj.getCanonicalFile().getAbsoluteFile();
      ekkk();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void ekkk() throws IOException {


    Set devLit = new HashSet();
    BufferedReader br;
    String linio;

    System.out.println("Legas vortlistojn de "+aliaj_vortaroj);

    for (int dosiero=0; dosiero<2; dosiero++) try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(aliaj_vortaroj+"/www.nepalisabdakos.com/"+(dosiero==0?"root_words.html":"derived_words.html"))));
      while ( (linio=br.readLine()) != null) {
        int i1 = linio.indexOf("<td>");
        int i2 = linio.indexOf("</td>");
        if (i1==-1) continue;
        if (i2==-1) continue;
        String vorto = linio.substring(i1+4,i2).trim();
        //String adreso = "http://nepalisabdakos.com/index.php?srch_word="+vorto+"&dictionary=VF";
        String adreso = "http://nepalisabdakos.com/index.php?srch_word="+vorto+"&amp;dictionary=VF";

        nepalisabdakos.put(vorto, adreso);
        //System.out.println(vorto+" -> "+adreso);
      }
      br.close();

      System.out.println("newiki-latest-all-titles-in-ns0.gz legita");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }






    try {
      br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(aliaj_vortaroj+"/newiki-latest-all-titles-in-ns0.gz"))));
      while ( (linio=br.readLine()) != null) {
	for (int i = 0; i<linio.length(); i++) devLit.add(""+linio.charAt(i));

	if (linio.contains("_")) continue;
	if (linio.contains(" ")) continue;
	if (linio.contains("(")) continue;
	//linio=linio.replaceAll("[^\\p{L}]","-");
	//if (linio.contains("-")) continue;
	String vorto = linio.toLowerCase();
	//linio = URLEncoder.encode(linio, "UTF-8");
	//if (linio.contains("%")) continue;
	String adreso = "http://ne.wikipedia.org/wiki/"+linio;

	//if (liguAlLokajKopioj && linio.length()>2) {
	//  adreso = "file://"+revo_kaj_vikipedio+"/vikipedio/ne/"+linio.charAt(0)+'/'+linio.charAt(1)+'/'+linio.charAt(2)+'/'+linio+".html";
	//}

	npviki.put(vorto.toLowerCase(), adreso);
	//System.out.println(vorto+" -> "+adreso);
	//if (viki.size()>44000) break;
      }
      br.close();

      System.out.println("newiki-latest-all-titles-in-ns0.gz legita");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }





    /*
	 ArrayList al = new ArrayList(new TreeSet(devLit));
	 for (int i=0; i<al.size(); i++) {
	   String s = (String) al.get(i);
	   System.out.println("'"+s+"'  "+((int)s.charAt(0)));
	 }
	 System.exit(0);



	// grep 'drv mrk=' *.xml | gzip -9c > revo-vortoj.txt.gz
	BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("../revo_kaj_vikipedio/revo-vortoj.txt.gz"))));
	String linio;
	while ( (linio=br.readLine()) != null) {
	  String pagxo = linio.split("\\.")[0];
	  String ankro = linio.split("\\\"")[1];
	  String[] p = ankro.split("\\.");
	  String vorto = Iloj.alCxapeloj(p[1].replaceFirst("0",p[0]).toLowerCase());
	  String adreso = "http://reta-vortaro.de/revo/art/"+pagxo+".html#"+ankro;

	  revo.put(vorto, adreso);
	  System.out.println(vorto+" -> "+adreso);
	}
	br.close();
    */


    // wget -O revo-vortoj.txt "http://www.reta-vortaro.de/cgi-bin/sercxu.pl?sercxata=%%&kadroj=0&formato=txt"
    br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(aliaj_vortaroj+"revo-vortoj.txt.gz"))));
    //br = new BufferedReader(new InputStreamReader(new FileInputStream("../revo_kaj_vikipedio/revo-vortoj.txt")));
    //br = new BufferedReader( new InputStreamReader(Runtime.getRuntime().exec("zcat ../revo_kaj_vikipedio/revo-vortoj.txt.gz").getInputStream()));
    while ( (linio=br.readLine()) != null) {
      String vorto = linio.split(",")[0].toLowerCase();


      String adreso = "http://reta-vortaro.de"+linio.split(" ")[1];
      // LOKA VERSIO
      if (liguAlLokajKopioj) {
	adreso = "file://"+aliaj_vortaroj + linio.split(" ")[1];
      }

      //System.out.println(linio + " //////" + vorto+ " xXXX " + adreso);



      revo.put(vorto, adreso);
      //System.out.println(vorto+" -> "+adreso);
      //if (revo.size()>100) break;
    }
    br.close();
    System.out.println("revo-vortoj.txt.gz legita");

    br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(aliaj_vortaroj+"eowiki-latest-all-titles-in-ns0.gz"))));
    while ( (linio=br.readLine()) != null) {
      if (linio.contains("_")) continue;
      if (linio.contains("(")) continue;
      linio=linio.replaceAll("[^\\p{L}]","-");
      if (linio.contains("-")) continue;
      String vorto = linio.toLowerCase();
      //linio = URLEncoder.encode(linio, "UTF-8");
      //if (linio.contains("%")) continue;
      String adreso = "http://eo.wikipedia.org/wiki/"+linio;


      if (liguAlLokajKopioj && linio.length()>2) {
	//adreso = "file://"+revo_kaj_vikipedio+"/vikipedio/eo/"+linio.charAt(0)+'/'+linio.charAt(1)+'/'+linio.charAt(2)+'/'+linio+".html";
	adreso = "http://localhost:8000/article/"+linio;
      }


      viki.put(vorto.toLowerCase(), adreso);
      //System.out.println(vorto+" -> "+adreso);
      //if (viki.size()>44000) break;
    }
    br.close();
    System.out.println("eowiki-latest-all-titles-in-ns0.gz legita");



  }

  public static void main(String[] args) throws IOException {
    AliajVortaroj aliajvortaroj = new AliajVortaroj();

  }
}
