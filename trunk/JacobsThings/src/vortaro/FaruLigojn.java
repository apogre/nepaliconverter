package vortaro;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import np.esperanto.vortaro.Devanagari;

public class FaruLigojn {

static String[] tst= {
//"kontraŭstaro, obstino",
//"divido de posedaĵoj al hereduloj",
"antaŭ, <text:s/>antaŭe",
//"cifero; poento",
"ŝoso, ekkreskaĵo (de planto)",};

	public static boolean nurEksteraj = true;


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

      //if (vorto.length()>=4 || vorto.length()>=3 && tuto.trim().length()<5) {
			if (vorto.length()>=3) {

				String revo = sercxuVortaron(vorto, AliajVortaroj.revoext);
				if (revo != null) {
					if (nurEksteraj)
						rezulto.append("<text:a xlink:href=\"" + revo + "\" xlink:type=\"simple\"> r</text:a>");
					else
						rezulto.append("<text:a xlink:href=\"" + sercxuVortaron(vorto, AliajVortaroj.revoint) + "\" xlink:type=\"simple\"> r</text:a>");
				}

				//System.out.println(vorto + " (revo) => " + revo);

				//rezulto.append("<text:a xlink:href=\"http://reta-vortaro.de/revo/art/auxtom.html\" xlink:type=\"simple\">r</text:a>");


				String viki = AliajVortaroj.vikiext.get(vorto);
				if (viki != null) {
					rezulto.append("<text:a xlink:href=\"" + viki + "\" xlink:type=\"simple\"> v</text:a>");
					//rezulto.append("<text:a xlink:href=\"" + AliajVortaroj.vikiint.get(vorto) + "\" xlink:type=\"simple\">i</text:a>");
					//System.out.println(viki);
				}
			}


			//System.out.println(revo + viki);

			pos = m.end(0);
		}
		rezulto.append(tuto.substring(pos));

		String s= rezulto.toString();
		//System.out.println("RES="+s);

		return s;
	}

	public static String sercxuVortaron(String vorto, HashMap<String, String> vortaro) {
		String rezulto = vortaro.get(vorto);
		if (rezulto == null && vorto.endsWith("n")) {
			vorto = vorto.substring(0,vorto.length()-1); // fprprenu 'n'
			rezulto = vortaro.get(vorto);
		}

		if (rezulto == null && vorto.endsWith("j")) {
			vorto = vorto.substring(0,vorto.length()-1); // fprprenu 'j'
			rezulto = vortaro.get(vorto);
		}

		if (rezulto == null && vorto.endsWith("e")) {
			vorto = vorto.substring(0,vorto.length()-1)+"a"; // provu kun 'a'
			rezulto = vortaro.get(vorto);
		}

		if (rezulto == null && vorto.endsWith("i")) {
			vorto = vorto.substring(0,vorto.length()-1)+"a"; // provu kun 'a'
			rezulto = vortaro.get(vorto);
		}

		if (rezulto == null && vorto.endsWith("a")) {
			vorto = vorto.substring(0,vorto.length()-1)+"o"; // provu kun 'o'
			rezulto = vortaro.get(vorto);
		}

		if (rezulto == null && vorto.endsWith("o")) {
			vorto = vorto.substring(0,vorto.length()-1)+"a"; // provu kun 'o'
			rezulto = vortaro.get(vorto);
		}

		if (rezulto == null && vorto.endsWith("a")) {
			vorto = vorto.substring(0,vorto.length()-1)+"i"; // provu kun 'o'
			rezulto = vortaro.get(vorto);
		}

		if (rezulto == null && vorto.endsWith("eci")) {
			vorto = vorto.substring(0,vorto.length()-3)+"o"; // provu kun 'o'
			rezulto = sercxuVortaron(vorto, vortaro);
		}

		if (rezulto == null && vorto.endsWith("aĵi")) {
			vorto = vorto.substring(0,vorto.length()-3)+"o"; // provu kun 'o'
			rezulto = sercxuVortaron(vorto, vortaro);
		}


		return rezulto;
	}




	public static String faruNepalajnLigojn(String tuto) {

		tuto = tuto.replaceAll(" <text:s/>", " ");

		if (tuto.contains("<")) {
			System.out.println("NE PROBLEMA "+Iloj.deCxapeloj(tuto));
			return tuto;
		}
		//tuto = tuto.replaceAll("<text:s/>", " ");
/*
    if (tuto.startsWith("शु")) {
      System.out.println("lin="+ tuto);
      tuto = tuto.replaceAll(Devanagari.ZWJ,"").replaceAll(Devanagari.ZWNJ,"");
      System.out.println("lin2="+ tuto);
    }
*/

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

        String vortoSenZWJ = vorto.replaceAll(Devanagari.ZWJ,"").replaceAll(Devanagari.ZWNJ,"");

			String viki = AliajVortaroj.npviki.get(vorto);
      if (viki==null) viki = AliajVortaroj.npviki.get(vortoSenZWJ);

			if (viki != null) {
				rezulto.append("<text:a xlink:href=\""+viki+"\" xlink:type=\"simple\"> v</text:a>");
				//System.out.println("vorto=" + vorto + " " + viki);
			}
			//if (viki != null) System.out.println(viki);

			String sab = AliajVortaroj.nepalisabdakos.get(vorto);
      if (sab==null) sab = AliajVortaroj.nepalisabdakos.get(vortoSenZWJ);
			//if (sab != null) System.out.println("vorto="+ vorto+ " " + sab);
/*
      if (tuto.startsWith("शु")) {
        System.out.println("vorto="+ vorto+"  -> "+sab);
        //  शुद्ध s kaj  शुध्द

      }
*/

			if (sab != null) {
				rezulto.append("<text:a xlink:href=\"" + sab + "\" xlink:type=\"simple\"> s</text:a>");
			}

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
