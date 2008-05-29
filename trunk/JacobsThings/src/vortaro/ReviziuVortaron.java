package vortaro;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;
import javax.xml.parsers.*;

import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import java.text.*;
import np.org.mpp.unicode.sprites.*;

public class ReviziuVortaron {

  String dosiero = "versio62";
  public static boolean faruAnkauxInternajnLigojn = true;

  public static void main(String[] args) throws Exception {

    ReviziuVortaron r;

    r = new ReviziuVortaron();
    r.nurMppRomDiff = true;
    r.faruLigojn = false;
    r.reviziu();

    /*
        r = new ReviziuVortaron();
        // r.enmetuRomanizigon = false;
        r.nurProblemaj = true;
        r.reviziu();

        r = new ReviziuVortaron();
        r.ordigu = r.ordiguLauxRom = true;
        //r.reviziu();

        r = new ReviziuVortaron();
        r.faruEoNeDirekto = true;
        //r.reviziu();
     */
  }

  boolean faruLigojn = true;
  boolean enmetuRomanizigon = true;
  boolean ordigu = false;
  boolean ordiguLauxRom = false;
  boolean faruEoNeDirekto = false;
  boolean nurProblemaj = false;
  boolean nurMppRomDiff = true;

  public void reviziu() throws Exception {

    ZipFile zif = new ZipFile("/home/j/esperanto/nepala vortaro/" + dosiero + ".odt");

    String kromnomo = "";

    if (faruEoNeDirekto) {
      kromnomo = kromnomo + "-eo_ne";
      ordigu = true;
    }

    if (ordigu) {
      kromnomo = kromnomo + "-ordigita";
      if (ordiguLauxRom)
        kromnomo = kromnomo + "LauxRom";
    }

    if (nurProblemaj) {
      kromnomo = kromnomo + "-problemaj";
    }

    if (kromnomo.startsWith("-"))
      kromnomo = kromnomo.substring(1);

    if (kromnomo.length() == 0)
      kromnomo = "reviziita";

    ZipOutputStream zof = new ZipOutputStream(new FileOutputStream("/home/j/esperanto/nepala vortaro/" + dosiero + "-" + kromnomo + ".odt"));

    Document content = null;
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

    System.out.println("Legas " + dosiero);

    for (Enumeration zee = zif.entries(); zee.hasMoreElements(); ) {
      ZipEntry ze = (ZipEntry) zee.nextElement();
      InputStream is = zif.getInputStream(ze);
      if (ze.getName().equals("content.xml")) {
        content = db.parse(is);
        String res = convertStylesAndContent(content);
        zof.putNextEntry(new ZipEntry("content.xml"));
        //OutputFormat format = new OutputFormat(content);
        //format.setIndenting(true); //format.setLineSeparator("\n");
        //new XMLSerializer(zof, format).serialize(content);
        zof.write(res.getBytes("UTF-8"));
        zof.closeEntry();
        content = null;
      } else {
        ZipEntry ze2 = new ZipEntry(ze.getName());
        zof.putNextEntry(ze2);
        byte[] b = new byte[1];
        while (is.read(b) >= 0) {
          zof.write(b);
        }
        zof.closeEntry();
      }
    }

    System.out.println("Skribis " + kromnomo);

    zof.close();
  }

  static Collator hindiCollator = Collator.getInstance(new Locale("hi"));
  static Collator anglaCollator = Iloj.usCollator; //.getInstance(new Locale("en"));

  private String convertStylesAndContent(Document content) throws Exception {
    //final Map<String,OpenDocumentConverter.Style> styleNameToNode = OpenDocumentConverter.Style.findStyleNodes(content);

    OutputFormat format = new OutputFormat(content);
    format.setIndenting(false); format.setLineSeparator("\n");
    StringWriter sw = new StringWriter();
    new XMLSerializer(sw, format).serialize(content);

    String tuto = sw.toString();

    DevAlRomana devAlRomana = new DevAlRomana();
    Transliterator mppdevAlRomana = new NepaliTransliterationJacob();

    StringBuffer rezulto = new StringBuffer(3*tuto.length()/2);
    int pos = 0;

    ArrayList ne_eo = new ArrayList();
    ArrayList eo_ne = new ArrayList(); //new TreeMap(Iloj.eocomparator);

//     "<text:p text:style-name=\"Vortaro\">अकाल (ना)<text:tab/>sekego, senpluveco</text:p>"
//    Matcher m = Pattern.compile("(<text:p text:style-name=\"(\\w+)\">(.*?)</text:p>)").matcher(tuto);
    Matcher m = Pattern.compile("(<text:p text:style-name=\"Vortaro\">(.*?)</text:p>)").matcher(tuto);
    while (m.find()) {
      //if (!m.group(2).equals("Vortaro")) continue;


      String linio = m.group(2);
      //System.out.println("0: "+m.group(0));

      if (linio.contains("krura")) {
        System.err.println("XXX6 "+linio);
      }


      //System.out.println(linio);
      if (linio.endsWith("<text:tab/>"))
        linio = linio + " ";
      String[] kol = (linio + " ").split("<text:tab/>");
      if (kol.length < 2) {
        System.out.println("NE TRAKTIS: " + linio);
        continue;
      }

      String esp = kol[kol.length - 1].trim();
      //if (esp.endsWith(" ")) esp = esp.substring(0,esp.length()-1);
      String dev = kol[kol.length - 2].trim();
      String rom = "ROM" + dev + "MOR";

      esp = foriguLigojnKajSpacojn(esp);

      if (esp.startsWith("krur")) {
        System.err.println("XXX5 "+esp);
      }

      //System.out.println(rom + " / " + dev + " / " + esp + "   / "+m.start(2)+" " +m.end(2));
      if (faruLigojn)
        esp = FaruLigojn.faruLigojn(esp);

      dev = foriguLigojnKajSpacojn(dev);


      //rom = devAlRomana.alRomana(dev);
      //rom = rom.replaceAll("\\(.*?\\)","").trim();
      String devSenPara = dev.replaceAll("\\(.*?\\)", "").trim();

      rom = devAlRomana.alRomana(devSenPara).trim();
      String mpprom =  mppdevAlRomana.transliterate(devSenPara).trim();
      //System.out.println(rom + " / " + dev + " / " + esp + "   / "+m.start(2)+" " +m.end(2));
      if (faruLigojn)
        dev = FaruLigojn.faruNepalajnLigojn(dev);

      boolean forjetuLinion = nurProblemaj && linio.toLowerCase().indexOf("xx") == -1;

      if (!forjetuLinion) {
        String[] tuplo = new String[] {rom, dev, esp};
        ne_eo.add(tuplo);

        for (String eo : esp.split(",;")) {
          String[] tuploEo = new String[] {rom, dev, eo};
          eo_ne.add(tuploEo);
        }
      }

      if (!ordigu && !forjetuLinion && (!nurMppRomDiff || !rom.equals(mpprom.replaceAll("/","")))) {
        if (rom.contains("/")) {
          //System.err.println("XXX");
        }
        rezulto.append(tuto.substring(pos, m.start(2)));
        if (enmetuRomanizigon) {
          if (!nurMppRomDiff) rezulto.append(rom + "<text:tab/>" + dev + "<text:tab/>" + esp);
          else rezulto.append(mpprom + "<text:tab/>" + rom + "<text:tab/>" + dev + "<text:tab/>" + esp);

        } else
          rezulto.append(dev + "<text:tab/>" + esp);
        pos = m.end(2);
      } else {
        rezulto.append(tuto.substring(pos, m.start(1)));
        pos = m.end(1);
      }
    }

    if (ordigu) {

      if (!faruEoNeDirekto) {

        Collections.sort(ne_eo, new Comparator() {
          public int compare(Object o1, Object o2) {
            String[] t1 = (String[]) o1;
            String[] t2 = (String[]) o2;
            if (ordiguLauxRom)
              return anglaCollator.compare(t1[0].toLowerCase().replace("v", "b"), t2[0].toLowerCase().replace("v", "b"));
            //else return t1[1].compareTo(t2[1]);
            else
              return hindiCollator.compare(t1[1], t2[1]);

          }
        });

        for (Object o : ne_eo) {
          String[] t = (String[]) o;
          rezulto.append("<text:p text:style-name=\"Vortaro\">");
          if (enmetuRomanizigon)
            rezulto.append(t[0] + "<text:tab/>" + t[1] + "<text:tab/>" + t[2]);
          else
            rezulto.append(t[1] + "<text:tab/>" + t[2]);
          rezulto.append("</text:p>");
        }

      } else {

        Collections.sort(eo_ne, new Comparator() {
          public int compare(Object o1, Object o2) {
            String[] t1 = (String[]) o1;
            String[] t2 = (String[]) o2;
            return Iloj.eocomparator.compare(t1[2], t2[2]);
          }
        });

        for (Object o : eo_ne) {
          String[] t = (String[]) o;
          rezulto.append("<text:p text:style-name=\"Vortaro\">");
          if (enmetuRomanizigon)
            rezulto.append(t[2] + "<text:tab/>" + t[1] + "<text:tab/>" + t[0]);
          else
            rezulto.append(t[2] + "<text:tab/>" + t[1]);
          rezulto.append("</text:p>");
        }

      }

    }

    rezulto.append(tuto.substring(pos));

    String s = rezulto.toString();
    //System.out.println(s);
    return s;
  }

  private static String foriguLigojnKajSpacojn(String esp) {
    //dev = dev.replaceAll("<text:a [^>]*> *\\w</text:a>",""); // forigu cxiuj unuliterajn ligojn
    //dev = dev.replaceAll("<text:span text:style-name=\"\\w\">(\\s)</text:span>","$1"); // forigu tipojn kiu nur kosideras spacojn
    esp = esp.replaceAll("\\s?<text:a [^>]*>\\s*\\w</text:a>", ""); // forigu cxiuj unuliterajn ligojn kaj evt spaco antauxe
    esp = esp.replaceAll("<text:span text:style-name=\"\\w\">(\\s)*</text:span>", "$1"); // forigu tipojn kiu nur kosideras spacojn
    esp = esp.replaceAll("<text:soft-page-break/>", "");
    return esp;
  }

}
