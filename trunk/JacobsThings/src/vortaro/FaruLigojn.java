package vortaro;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FaruLigojn {

static String[] tst= {
//"kontraŭstaro, obstino",
//"divido de posedaĵoj al hereduloj",
"antaŭ, <text:s/>antaŭe",
//"cifero; poento",
"ŝoso, ekkreskaĵo (de planto)",};



  public static String faruLigojn(String tuto) {

    tuto = tuto.replaceAll(" <text:s/>", " ");

    if (tuto.contains("<")) {
      System.out.println("EO PROBLEMA "+Iloj.deCxapeloj(tuto));
      return tuto;
    }


    //System.out.println("lin="+ tuto);

    StringBuffer rezulto  = new StringBuffer(tuto.length());

    Matcher m = Pattern.compile("\\p{L}+").matcher(tuto);
    int pos = 0;
    while (m.find()) {
      rezulto.append( tuto.substring(pos, m.start(0)));

      String vorto = m.group(0);
      rezulto.append( vorto);
      //System.out.println("vorto="+ vorto);
      vorto = vorto.toLowerCase();

      if (vorto.length()>=4 || vorto.length()>=3 && tuto.trim().length()<5) {

      String revo = AliajVortaroj.revo.get(vorto);
      //System.out.println(vorto + " (revo) => " + revo);

      //rezulto.append("<text:a xlink:href=\"http://reta-vortaro.de/revo/art/auxtom.html\" xlink:type=\"simple\">r</text:a>");
      if (revo != null) rezulto.append("<text:a xlink:href=\""+revo+"\" xlink:type=\"simple\"> r</text:a>");

      String viki = AliajVortaroj.viki.get(vorto);
      if (viki != null) rezulto.append("<text:a xlink:href=\""+viki+"\" xlink:type=\"simple\"> v</text:a>");
      //if (viki != null) System.out.println(viki);
      }


      //System.out.println(revo + viki);

      pos = m.end(0);
    }
    rezulto.append(tuto.substring(pos));

    String s= rezulto.toString();
    //System.out.println("RES="+s);

    return s;
  }




  public static String faruNepalajnLigojn(String tuto) {

    tuto = tuto.replaceAll(" <text:s/>", " ");

    if (tuto.contains("<")) {
      System.out.println("NE PROBLEMA "+Iloj.deCxapeloj(tuto));
      return tuto;
    }
    //tuto = tuto.replaceAll("<text:s/>", " ");

    //System.out.println("lin="+ tuto);

    StringBuffer rezulto  = new StringBuffer(tuto.length());

    Matcher m = Pattern.compile("\\S+").matcher(tuto);
    int pos = 0;
    while (m.find()) {
      rezulto.append( tuto.substring(pos, m.start(0)));

      String vorto = m.group(0);
      rezulto.append( vorto);
      //System.out.println("vorto="+ vorto);
      vorto = vorto.toLowerCase();

      if (vorto.length()>=4) {

      String viki = AliajVortaroj.npviki.get(vorto);
      if (viki != null) System.out.println("vorto="+ vorto+ " " + viki);
      if (viki != null) rezulto.append("<text:a xlink:href=\""+viki+"\" xlink:type=\"simple\"> v</text:a>");
      //if (viki != null) System.out.println(viki);

      String sab = AliajVortaroj.nepalisabdakos.get(vorto);
      if (sab != null) System.out.println("vorto="+ vorto+ " " + sab);

      if (sab != null) rezulto.append("<text:a xlink:href=\""+sab+"\" xlink:type=\"simple\"> s</text:a>");

      }


      //System.out.println(revo + viki);

      pos = m.end(0);
    }
    rezulto.append(tuto.substring(pos));

    String s= rezulto.toString();
    //System.out.println("RES="+s);

    return s;
  }



  public static void main(String[] args) {

    for (String t : tst) {
      String t2= faruLigojn(t);

    }

  }
}
