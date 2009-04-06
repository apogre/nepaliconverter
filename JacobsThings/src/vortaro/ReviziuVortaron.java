package vortaro;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;
import javax.xml.parsers.*;

import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import np.esperanto.conv4.translit.*;
import java.awt.*;
import np.esperanto.vortaro.Devanagari;

public class ReviziuVortaron {

	String dosiero = "versio205";
	public static boolean faruAnkauxInternajnLigojn = false;

	public static void main(String[] args) throws Exception {
		Toolkit.getDefaultToolkit().beep();

		ReviziuVortaron r;
		r = new ReviziuVortaron();
		r.faruLigojn = false;
		r.enmetuRomanizigon = true;
    r.ordigu = false;
    //r.akceptuProponojn = false;
		//r.reviziu();

		r = new ReviziuVortaron();
		r.faruEoNeDirekto = true;
    r.akceptuProponojn = true;
    r.tenuNepalanVortklason = false;
    r.faruLigojn = false;
    r.transliterumoEnKrampoj = true;
    r.metuOblikvanStrekonCxeRadiko = true;
    r.disiguNepalajVortojKunOblikvaStreko = true;
    r.aldonuAlApertium = true;
		//r.akceptuProponojn = true;
		r.reviziu();
    /*

		r = new ReviziuVortaron();
		r.akceptuProponojn = true;
		r.faruEoNeDirekto = true;
		r.reviziu();
   */
/* */
	}

	boolean faruLigojn = true;
	boolean enmetuRomanizigon = true;
  boolean tenuNepalanVortklason = true;
	boolean ordigu = false;
	boolean ordiguLauxRom = false;
	boolean faruEoNeDirekto = false;
	boolean nurProblemaj = false;
	boolean akceptuProponojn = false;
	boolean startuOoo = false;
  boolean transliterumoEnKrampoj = false;
  boolean metuOblikvanStrekonCxeRadiko = false;
  boolean disiguNepalajVortojKunOblikvaStreko = false;
  boolean aldonuAlApertium = false;

	public void reviziu() throws Exception {
    String dosierNomo = "/home/j/esperanto/nepala vortaro/" + dosiero + ".odt";
    System.out.println("Legas "+dosierNomo);
		ZipFile zif = new ZipFile(dosierNomo);

		String kromnomo = "";

		if (faruEoNeDirekto) {
			ordigu = true;
		}

		if (ordigu) {
			kromnomo = kromnomo + "-ordigita";
			if (ordiguLauxRom)
				kromnomo = kromnomo + "-romane";
      if (faruEoNeDirekto) {
        kromnomo = kromnomo + "-esperanto-nepali";
      }
		}

		if (nurProblemaj) {
			kromnomo = kromnomo + "-problemaj";
		}

		//if (akceptuProponojn) kromnomo = kromnomo + "-proponojAkceptitaj";



		if (kromnomo.startsWith("-"))
			kromnomo = kromnomo.substring(1);

		if (kromnomo.length() == 0)
			kromnomo = "reviziita";

		String of = "/home/j/esperanto/nepala vortaro/" + dosiero + "-" + kromnomo + ".odt";
		ZipOutputStream zof = new ZipOutputStream(new FileOutputStream(of));

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

		System.out.println("Skribis " + of);
		zof.close();

		try {
			if (startuOoo) {
				Runtime.getRuntime().exec("soffice ", new String[] { of });
			}

			Toolkit.getDefaultToolkit().beep();
			//LudiSonon.playAudioFile( "/home/Hent/minisebran_src/Esperanto/numbers/"+numero++ +".wav" );

		} catch (Exception ex) {
			ex.printStackTrace();
		}


	}

	static int numero = 1;


	static Collator hindiCollator = Collator.getInstance(new Locale("ne","np"));

	/* laux opinio de N   la kombino ज्  ञ estu LASTA

	tiuj cxi estu lastaj
jỹaat	ज्ञात s (वि)	scia r, XXXald sciata
jỹaan	ज्ञान s (ना)	scio r v, eduko r v
jỹaanii	ज्ञानी s (वि)	scianto
jỹaanendriy	ज्ञानेन्द्रिय (ना)	sentumo r v XXXforpr konsciilo


	*/

	static Collator anglaCollator = Iloj.usCollator; //.getInstance(new Locale("en"));

  private String convertStylesAndContent(Document content) throws Exception {
		//final Map<String,OpenDocumentConverter.Style> styleNameToNode = OpenDocumentConverter.Style.findStyleNodes(content);

		OutputFormat format = new OutputFormat(content);
		format.setIndenting(false); format.setLineSeparator("\n");
		StringWriter sw = new StringWriter();
		new XMLSerializer(sw, format).serialize(content);

		String tuto = sw.toString();

		//DevAlRomana devAlRomana = new DevAlRomana();
		NepaliTransliterationJacob mppdevAlRomana = new NepaliTransliterationJacob();

		StringBuffer rezulto = new StringBuffer(3*tuto.length()/2);
		int pos = 0;

		ArrayList<String[]> ne_eo = new ArrayList<String[]>();
		HashSet ne_aro = new HashSet();
		ArrayList<String[]> eo_ne = new ArrayList<String[]>(); //new TreeMap(Iloj.eocomparator);

//     "<text:p text:style-name=\"Vortaro\">अकाल (ना)<text:tab/>sekego, senpluveco</text:p>"
//    Matcher m = Pattern.compile("(<text:p text:style-name=\"(\\w+)\">(.*?)</text:p>)").matcher(tuto);

		//Matcher m = Pattern.compile("(<text:p text:style-name=\"Vortaro\">(.*?)</text:p>)").matcher(tuto);

		Matcher m = Pattern.compile("(<text:[hp][^>]*?text:style-name=\"Vortaro[4]*\"[^>]*>(.*?)</text:[ph]>)").matcher(tuto);
		//Matcher m = Pattern.compile("(<text:h text:style-name=\"Vortaro4\" text:outline-level=\"4\">(.*?)</text:h>)").matcher(tuto);
		//                            <text:h text:style-name="Vortaro4" text:outline-level="4"><text:tab/>ए</text:h>

		int traktis = 0;

    String linio, esp, dev; linio =esp= dev= null;
    int x = 0;

    while (m.find()) try {
			linio = m.group(2);
/*
			if (m.group(1).contains("Vortaro4")) {
				System.err.println("XXX Vortaro4");
			}



			System.out.println("0: "+m.group(0));
			System.out.println("1: "+m.group(1));

			System.exit(0);
*/
			//System.out.println(linio);
			boolean header = m.group(1).startsWith("<text:h ");

			if (header) { linio = linio + "<text:tab/> "; }

			if (linio.endsWith("<text:tab/>"))
				linio = linio + " ";
			String[] kol = (linio + " ").split("<text:tab/>");
			if (kol.length < 2) {
				System.out.println("NE TRAKTIS: " + linio);
				continue;
			}


			esp = kol[kol.length - 1].trim();
			//if (esp.endsWith(" ")) esp = esp.substring(0,esp.length()-1);
			dev = kol[kol.length - 2].trim();

			String rom = "ROM" + dev + "MOR";

			if (header) {
        if (faruEoNeDirekto) esp = dev;
        else esp = "HEADER";
			}

			esp = Iloj.foriguLigojnKajSpacojn(esp);

			if (akceptuProponojn) {
				esp = akceptuProponojn(esp);
			}

			//System.out.println(rom + " / " + dev + " / " + esp + "   / "+m.start(2)+" " +m.end(2));
			if (faruLigojn)
				esp = FaruLigojn.faruLigojn(esp);

      String devx = dev;
			dev = Iloj.foriguLigojnKajSpacojn(dev);
      //System.out.println(devx + "  ->  "+dev);
      //System.out.println(dev);

			if (!header && dev.trim().length()>0) traktis++;

			String devSenVortklaso = dev.replaceAll("\\(.*?\\)", "").trim();

      String neVortklaso = devSenVortklaso.equals(dev)? "" : dev.substring(dev.indexOf('(')+1, dev.indexOf(')'));



      int n = esp.indexOf(' ');
      if (n==-1) n = esp.length();
      String eoVortklaso = esp.substring(n-1, n);
      if (";.j".indexOf(eoVortklaso)>-1) {
        n--;
        eoVortklaso = esp.substring(n-1, n);
      }
      if ("ieoa".indexOf(eoVortklaso)==-1) {
        n = esp.length();
        eoVortklaso = esp.substring(n-1, n);
      }




      if (!tenuNepalanVortklason) dev = devSenVortklaso;
      else if (!dev.matches(".*\\(.*\\).*")) {
       if (dev.length()>1) System.out.println("Mankas vortklaso: "+dev+"   "+esp+" ...."+eoVortklaso+" ..?");
      } else {
        //System.out.println(dev.charAt('(') + "  "+ dev.charAt(')'));
        boolean malgxusta = false;
        if (neVortklaso.equals("ना")) malgxusta = "iea".indexOf(eoVortklaso)!=-1;
        else if (neVortklaso.equals("वि")) malgxusta = "ieo".indexOf(eoVortklaso)!=-1;
        else if (neVortklaso.equals("क्रिवि")) malgxusta = "ioa".indexOf(eoVortklaso)!=-1;
        else if (neVortklaso.equals("क्रियो")) malgxusta = "ioa".indexOf(eoVortklaso)!=-1;
        else if (neVortklaso.equals("अक्रि")) malgxusta = "eoa".indexOf(eoVortklaso)!=-1;
        else if (neVortklaso.equals("सक्रि")) malgxusta = "eoa".indexOf(eoVortklaso)!=-1;
        else if (neVortklaso.equals("प्रे.क्रि")) ;
        else if (neVortklaso.equals("नायो"));
        else if (neVortklaso.equals("सर्व"));
        else if (neVortklaso.equals("विबो"));
        else if (neVortklaso.equals("विभ"));
        else if (neVortklaso.equals("प्रत्य"));
        else if (neVortklaso.equals("सं"));
        else if (neVortklaso.equals("अव्य"));
        else if (neVortklaso.equals("नि"));
        else System.out.println("NEKONATA VORTKLASO por "+dev+"   "+esp);

        //if (malgxusta)
        //  System.out.println("KONFLIKTAJ Vortklasoj "+eoVortklaso+" kaj "+neVortklaso+" "+dev+"   "+esp);

      }
      if (esp.matches(".*\\(["+Devanagari.ALL+"]+\\).*")) System.out.println("Ekstra nagaria vortklaso en esp parto: "+rom+" "+dev+"   "+esp);

			//System.out.println(rom + " / " + dev + " / " + esp + "   / "+m.start(2)+" " +m.end(2));

			boolean forjetuLinion = !header && nurProblemaj && (linio.toLowerCase().indexOf("xx") == -1 || esp.trim().length()==0);

			if (!forjetuLinion) {

        String[] devSenParaArr;
        if (disiguNepalajVortojKunOblikvaStreko) {
          devSenParaArr = devSenVortklaso.split(" *"+"/ *");
          //if (devSenParaArr.length>=2) System.out.println(devSenVortklaso + " devSenParaArr = " + devSenParaArr.length);
        } else {
          devSenParaArr = new String[] { devSenVortklaso };
        }

        for (String devSenPara : devSenParaArr) {
          if (enmetuRomanizigon)
            rom = mppdevAlRomana.convertText(null, devSenPara).trim();

          String devSenParaSenLigoj = devSenPara;

          if (faruLigojn)
            devSenPara = FaruLigojn.faruNepalajnLigojn(devSenPara);

          dev = devSenPara + (!tenuNepalanVortklason || devSenVortklaso.isEmpty()? "": " ("+neVortklaso+")");


          String[] tuplo = new String[] {rom, dev, esp};
          ne_eo.add(tuplo);

          if (ne_aro.add(dev)==false && tenuNepalanVortklason) {
            System.out.println("AVERTO: La vorto "+dev+" aperas plurfoje! "+esp);
          }


          /*        *
                  for (String eo : esp.split("[,;]")) {
                    String[] tuploEo = new String[] {rom, dev, eo.trim()};
                    eo_ne.add(tuploEo);
                  }
          */
          int i=0,j=-1;
          boolean parentezo = false;
          while (++j<esp.length()) {
            char ch = esp.charAt(j);
            if (ch=='(') parentezo = true;
            if (ch==')') parentezo = false;
            if (!parentezo && (ch==';' || ch==',')) {
              String[] tuploEo = new String[] {rom, dev, esp.substring(i,j).trim()};
              //if (++x<100) System.out.println(esp.substring(i,j).trim());
              eo_ne.add(tuploEo);
              if (aldonuAlApertium && !header) AldonuAlApertium.inst().aldonu(
                  devSenParaSenLigoj, neVortklaso, rom, esp.substring(i,j).trim());
              i=j+1;
            }
          }
          String[] tuploEo = new String[] {rom, dev, esp.substring(i,j).trim()};
          eo_ne.add(tuploEo);
          if (aldonuAlApertium && !header) AldonuAlApertium.inst().aldonu(
              devSenParaSenLigoj, neVortklaso, rom, esp.substring(i,j).trim());
          //if (++x<100) System.out.println(orgEoxxx + " -> \n"+ partoj+"\n"+Arrays.asList(esp.split("[,;]")));
          /**/

        }
			}

			if (!ordigu && !forjetuLinion) {
				if (rom.contains("/")) {
					//System.err.println("XXX");
				}
				rezulto.append(tuto.substring(pos, m.start(2)));
				if (enmetuRomanizigon) {
					rezulto.append(rom + "<text:tab/>" + dev + "<text:tab/>" + esp);
					//else rezulto.append(mpprom + "<text:tab/>" + rom + "<text:tab/>" + dev + "<text:tab/>" + esp);

				} else
					rezulto.append("<text:tab/>" + dev + "<text:tab/>" + esp);
					//rezulto.append(dev + "<text:tab/>" + esp);
				pos = m.end(2);
			} else {
				rezulto.append(tuto.substring(pos, m.start(1)));
				pos = m.end(1);
			}
    } catch (Exception e) {
      System.err.println(e + " por linio "+linio);
      System.err.println(e + esp + "  "+dev);
      e.printStackTrace();
      System.exit(-1);
    }
    //System.exit(0);

    if (aldonuAlApertium) AldonuAlApertium.inst().finalize();

		if (ordigu) {

			if (!faruEoNeDirekto) {

				Collections.sort(ne_eo, new Comparator() {
          private String angl(String s) {
            return s.toLowerCase().replace("v", "b");
          }

          private String nep(String s) {
            //return s.replaceAll(Devanagari.HALANTA,"ऄ");
            //return s.replaceAll(Devanagari.HALANTA,"ह"); // halanta estu la lasta litero
            return s.replaceAll(Devanagari.HALANTA,"९"); // halanta estu la lasta litero
            //return s;
          }

					public int compare(Object o1, Object o2) {
						String[] t1 = (String[]) o1;
						String[] t2 = (String[]) o2;
						if (ordiguLauxRom)
							return anglaCollator.compare(angl(t1[0]), angl(t2[0]));
						//else return t1[1].compareTo(t2[1]);
						else {
              //return t1[1].compareTo(t2[1]);
              return hindiCollator.compare(nep(t1[1]), nep(t2[1]));
            }
					}
				});

				for (String[] t : ne_eo) {
					boolean header = t[2].equals("HEADER");

					if (header) {

						rezulto.append("<text:h text:outline-level=\"4\" text:style-name=\"Vortaro4\">");
						if (enmetuRomanizigon)
							rezulto.append("<text:tab/>" + t[1] + "<text:tab/>" + t[0]);
						else
							rezulto.append(t[1]);
						rezulto.append("</text:h>");

					} else {

						rezulto.append("<text:p text:style-name=\"Vortaro\">");
						if (enmetuRomanizigon)
							rezulto.append(t[0] + "<text:tab/>" + t[1] + "<text:tab/>" + t[2]);
						else
							rezulto.append(t[1] + "<text:tab/>" + t[2]);
						rezulto.append("</text:p>");
					}

				}

			} else {

				Collections.sort(eo_ne, new Comparator() {
					public int compare(Object o1, Object o2) {
						String[] t1 = (String[]) o1;
						String[] t2 = (String[]) o2;
						return Iloj.eocomparator.compare(t1[2], t2[2]);
					}
				});

        Iterator<String[]> it = eo_ne.iterator();
        String[] lasta = it.next();
        //int x2=0;
        while (it.hasNext()) {
          String[] t = it.next();
          //if (x2++<100) System.out.println(lasta[2]+".equalsIgnoreCase("+t[2]);
          if (lasta[2].equalsIgnoreCase(t[2])) {
            //if (x2++<100) System.out.println("Kunmetas "+t[2]+" "+lasta[0] + ", "+t[0]+"  -  "+lasta[1] + ", "+t[1]);
            if (transliterumoEnKrampoj) {
              lasta[1] += ", " + t[1] + " [" + t[0]  + "]";
              lasta[0] = "";
            } else {
              lasta[0] += ", " + t[0];
              lasta[1] += ", " + t[1];
            }
            it.remove();
          } else {
            lasta = t;
            if (transliterumoEnKrampoj) {
              lasta[1] = t[1] + " [" + t[0]  + "]";
              lasta[0] = "";
            }
          }
        }

        //int x3=0;
        for (String[] t : eo_ne) {
          // se finigxas per a, e, i aux o, oni devas enmeti oblikvan strekon /
          if (metuOblikvanStrekonCxeRadiko && t[2].length()>1 && "aeio".contains(t[2].substring(t[2].length()-1))) {
            //if (x3++<100) System.out.println(t[2]);

            t[2] = t[2].substring(0, t[2].length()-1) + "/" + t[2].substring(t[2].length()-1);
            //if (x3++<100) System.out.println(t[2]);
          }

          boolean header = t[2].equals("HEADER") || t[2].equals(t[1]);
               if (header) {

                 rezulto.append("<text:h text:outline-level=\"4\" text:style-name=\"Vortaro4\">");
                   rezulto.append(t[1]);
                 rezulto.append("</text:h>");

               } else {

                 rezulto.append("<text:p text:style-name=\"Vortaro\">");
                 if (enmetuRomanizigon)
                   rezulto.append(t[2] + "<text:tab/>" + t[1] + "<text:tab/>" + t[0]);
                 else
                   rezulto.append(t[2] + "<text:tab/>" + t[1]);
                 rezulto.append("</text:p>");
               }

				}

			}

		}

		rezulto.append(tuto.substring(pos));

		String s = rezulto.toString();
		//System.out.println(s);

		System.out.println("Traktis "+traktis+" vortojn");
		return s;
	}

	private String akceptuProponojn(String esp) {
		String orgEsp = esp;
		int i = 0;
		esp = esp.replaceAll("XXXald"," ");
		while ( (i=esp.indexOf("XXX")) >= 0) {
			esp = esp.substring(0,i).trim();
		}
    while ( (i=esp.indexOf("YYY")) >= 0) {
      esp = esp.substring(0,i)+esp.substring(i+3);
    }
		if (!esp.equals(orgEsp)) {
			esp = esp.replaceAll(" ;",";").trim();
			esp = esp.replaceAll(" ,",",");
			esp = esp.replaceAll(", ,",",");
			esp = esp.replaceAll("[;,] [;,]",";");
			esp = esp.replaceAll(" +"," ").trim();
			if (esp.endsWith(",")) esp = esp.substring(0,esp.length()-1).trim();
			if (esp.endsWith(";")) esp = esp.substring(0,esp.length()-1).trim();
			//System.out.println(orgEsp);
			//System.out.println(esp);
		}
		return esp;
	}


}
