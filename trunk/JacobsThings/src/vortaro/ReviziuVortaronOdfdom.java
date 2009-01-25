package vortaro;

import java.util.*;

import org.openoffice.odf.doc.*;
import org.openoffice.odf.dom.element.*;
import org.openoffice.odf.dom.style.*;
import org.w3c.dom.*;
import org.openoffice.odf.doc.element.style.*;

public class ReviziuVortaronOdfdom {

  String dosiero = "versiotest";
  //public static boolean faruAnkauxInternajnLigojn = true;

  public static void main(String[] args) throws Exception {
    //OdfDocument odfDocument = OdfDocument.load("versiotest2.odt");
    //odfDocument.save("versiotest2-reviziita.odt");
    //System.exit(0);



    ReviziuVortaronOdfdom r;

    r = new ReviziuVortaronOdfdom();
    //r.enmetuRomanizigon = false;
    //r.nurMppRomDiff = true;
    //r.faruLigojn = false;
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
  boolean nurMppRomDiff = false;


  public void reviziu() throws Exception {



    OdfDocument odfDocument = OdfDocument.loadDocument("/home/j/esperanto/nepala vortaro/" + dosiero + ".odt");


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



    convertStylesAndContent(odfDocument);


    odfDocument.save("/home/j/esperanto/nepala vortaro/" + dosiero + "-" + kromnomo + ".odt");

    System.out.println("Skribis " + kromnomo);
  }

  //static Collator hindiCollator = Collator.getInstance(new Locale("hi"));
  //static Collator anglaCollator = Iloj.usCollator; //.getInstance(new Locale("en"));

  static boolean DEBUG = true;


  private void convertStylesAndContent(OdfDocument odfDocument) throws Exception {

    //Document content = odfDocument.getContent();
    OdfFileDom content = odfDocument.getContentDom();

    NodeList lst = content.getElementsByTagName("text:p");

    for (int i = 0; i < lst.getLength(); i++) {
      Node n = lst.item(i);
      if (!(n instanceof OdfStylableElement)) continue;
      OdfStylableElement linio = (OdfStylableElement) n;

/* XXXXXXXXXX
      OdfStyle sty =  linio.getSgetStyle(odfDocument,linio.getStyleName());
      while (sty != null && !"Vortaro".equals(sty.getName())) sty = getStyle(odfDocument,sty.getParentName());
      if (sty == null) continue;

      if (DEBUG) System.out.println("A "+linio);
      //if (DEBUG) System.out.println("node = " + linio.getTextContent());

      String[] teksto = new String[] { "", "", "", "" };
      int tab = 0;
      n = linio.getFirstChild();
      while (n!=null) {
        //if (DEBUG) System.out.println("- " + n+ " "+n.getClass());
        String nn = n.getNodeName();
        if ("text:tab".equals(nn)) {
          tab++;
          if (tab==teksto.length) break;
        }
        else if ("#text".equals(nn)) teksto[tab] = teksto[tab]+ n.getNodeValue();
        else if ("text:a".equals(nn));
        else {
          System.out.println("NEKONATA" + n);
          if (DEBUG) System.out.println("  " + n.getNodeValue());
          if (DEBUG) System.out.println("    " + n.getBaseURI());
          if (DEBUG) System.out.println("    " + n.getLocalName());
          if (DEBUG) System.out.println("    " + n.getNodeType());
          if (DEBUG) System.out.println("    " + n.getPrefix());
        }
        //if (n instanceof OdfStylableElement) continue;
        //linio.removeChild(n);
        n = n.getNextSibling();
      }
      System.out.println("teksto[]=" + Arrays.asList(teksto));

      if (tab<2) {
        System.out.println("PROBLEMA " + linio);
        continue;
      }

      while (linio.hasChildNodes()) linio.removeChild(linio.getLastChild());
      linio.appendChild(content.createTextNode(teksto[0]));
      linio.appendChild(new org.openoffice.odf.doc.element.text.OdfTab(content));
      linio.appendChild(content.createTextNode(teksto[1]));
      linio.appendChild(new org.openoffice.odf.doc.element.text.OdfTab(content));
      linio.appendChild(content.createTextNode(teksto[2]));
      if (DEBUG) System.out.println("B "+linio);
 */
    }


    ArrayList ne_eo = new ArrayList();
    ArrayList eo_ne = new ArrayList();

//     "<text:p text:style-name=\"Vortaro\">अकाल (ना)<text:tab/>sekego, senpluveco</text:p>"
//    Matcher m = Pattern.compile("(<text:p text:style-name=\"(\\w+)\">(.*?)</text:p>)").matcher(tuto);
    /*

      String linio = m.group(2);
      //System.out.println("0: "+m.group(0));

      if (linio.contains("krura")) {
        ;//System.err.println("XXX6 "+linio);
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
        //System.err.println("XXX5 "+esp);
      }

      //System.out.println(rom + " / " + dev + " / " + esp + "   / "+m.start(2)+" " +m.end(2));
      if (faruLigojn)
        esp = FaruLigojn.faruLigojn(esp);

      dev = foriguLigojnKajSpacojn(dev);


      //rom = devAlRomana.alRomana(dev);
      //rom = rom.replaceAll("\\(.*?\\)","").trim();
      String devSenPara = dev.replaceAll("\\(.*?\\)", "").trim();

      rom = devAlRomana.alRomana(devSenPara).trim();
      String mpprom = null;
      try {
        mpprom = mppdevAlRomana.convertText(null, devSenPara).trim();
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println(rom+dev+esp);
      }
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
          if (!nurMppRomDiff) rezulto.append(mpprom + "<text:tab/>" + dev + "<text:tab/>" + esp);
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
    */
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
