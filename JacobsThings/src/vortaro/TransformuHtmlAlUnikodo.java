package vortaro;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import np.org.mpp.old.old_f2u.*;

public class TransformuHtmlAlUnikodo extends Iloj
{


    public static void main(String[] args) throws IOException {
/*
	String teksto = læs(new File("/home/j/esperanto/nepala vortaro/vorto02.txt")).substring(0,300);
	System.out.println(teksto);
	teksto = kantipurAlUnikodo(teksto);
	System.out.println(teksto);
*/

	konvertuDeHtml();
    }


    public static class Paro {
	public String ne;
	public String eo;
    }


    public static void konvertuDeHtml() throws IOException {
	final boolean html = true;
	final boolean unikodo = true;
	final boolean ooo = false;

	String tuto = legu(new File("vorto03j2.html"));
	PrintWriter pw = new PrintWriter(new FileWriter("rezulto."+(html?"html":"txt")));

	if (html) {
	    pw.println(
	    "<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=UTF-8\" />"
	    +"<title>rezulto "+(new Date())+"</title>"
	    //+"<style>\n"+".eo { lang:eo; }"+(unikodo?".ne { lang:ne; }" : ".ne { lang:ne; font-family:Kantipur }")+"\n</style>"
	    +"<STYLE TYPE=\"text/css\">\n<!--\nTD P { margin-bottom: 0.03cm }\n-->\n</STYLE>"
	    +"</head>"
	    +"<body><div><h1>Nepala-Esperanta</h1><table border='0'  CELLPADDING='2' CELLSPACING='2'>"
	    );
	}


	ArrayList vortaro = new ArrayList();




	TreeMap eo_ne = new TreeMap(eocomparator);
	//Font2Unicode font2Unicode = new Font2Unicode();

	try {

	    //System.out.println(tuto);
	    String[] lin = tuto.split("\n");
	    int linio = 0;
	    for (int linioNro = 0; linioNro <lin.length; linioNro++) { // 83 lin.length lin.length
		//if (lin[i].indexOf(skilletegn) > -1)  throw new IllegalStateException("Skilletegn i input: " + lin[i]);
		String l = lin[linioNro].trim();
		l = l.replaceAll("<p.*?>","");
		l = l.replaceAll("</p>","").trim();
		if (!l.startsWith("<span")) {
		    continue;
		}
		//System.out.println(l);
		l = l.replaceAll("&nbsp;"," ").trim();
		//l = l.replaceAll("&ldquo;", "").trim();   c“ -lg_ 
		l = l.replaceAll("&ldquo;", "“").trim();
		//l = l.replaceAll("&ldquo;", new String("“".getBytes("ISO-8859-1"),"UTF-8")).trim();

		/*
		<span style="font-family:'Kantipur'">lsTnL  -gf_    </span>
		<span style="font-family:'Arial Narrow'">bolilo</span>
		*/

		Pattern findAfsnit = Pattern.compile("<span style=\".*?font-family:'(.*?)'.*?\">(.*?)</span>", Pattern.DOTALL);
		Matcher m = findAfsnit.matcher(l);

		String ne = "";
		String eo = "";
		int i = 0;

		while (m.find()) {
		    String tipo = m.group(1);
		    String teksto = m.group(2).trim();
		    System.out.println(tipo+ ":" + teksto);

		    /*
		    if (tipo.equals("Preeti")) {
		      System.err.println("xxx");
		    } */


		    try {
		      //if (unikodo) teksto = font2Unicode.toUnicode(tipo, teksto);
		    } catch (Exception e) {
		      e.printStackTrace();
		      System.out.println(teksto);
		      System.out.println(l);
		      System.exit(0);
		    }


		    // Konvertér tegnsæt!
		    if (tipo.equals("Kantipur") || tipo.equals("Preeti")) {
		    } else if (tipo.contains("Arial")) {
			teksto = alCxapeloj(teksto);
		    } else {
			netraktitaj = netraktitaj + linio + ":  tipo: " +
				      tipo + teksto + "\n" + l + "\n";
		    }


		    if (tipo.equals("Arial Greek")) tipo = "Kantipur"; // hmmm
		    if (tipo.equals("Preeti")) tipo = "Kantipur"; // de ser ens ud
		    if (tipo.equals("Shangrila Hybrid")) tipo = "Kantipur"; // tomt afsnit
		    if (tipo.contains("Arial Narrow")) tipo = "Arial"; // de ser ens ud


		    //teksto = teksto.trim();
		    if (teksto.length()==0) continue;


		    i++;
		    if (tipo.equals("Kantipur")) i--;

		    boolean maldekstra = i==0;
		    /*
		    String lingvo = tipo.equals("Kantipur")?"ne":"eo";
		    if (html) {
			if (ooo) {
			  if (unikodo)
			    teksto = "<span lang=\""+lingvo+"\">"+teksto+"</span>";
			  else
			    teksto = "<span lang=\""+lingvo+"\" style=\"font-family:'"+tipo+"'\">"+teksto+"</span>";
			}
			else teksto = "<span class=\""+lingvo+"\">"+teksto+"</span>";
		    }

		    if (maldekstra) ne =  (ne + " " + teksto).trim();
		    else eo = (eo + " " + teksto).trim();
		    */
		   if (maldekstra) ne =  ne + teksto;
		   else eo = eo + teksto;

		}


		linio++;


		System.out.println("ne=" + ne);
		System.out.println("eo=" + eo);

		/*
		if (ne.length()==0 || eo.length()==0) {
		    netraktitaj = netraktitaj + linio + ": " + ne + eo + "\n" + l + "\n";
		    continue;
		}
		*/

		//String l = skilletegn + ne + skilletegn + "," + skilletegn + eo + skilletegn;
		//System.out.flush();
		//System.out.println("=================");
		if (html) pw.println("<tr><td>"+ne + "</td><td>" + eo+ "</td></tr>");
		else pw.println(ne + "\t" + eo);

		String glNe = (String) eo_ne.get(eo);
		if (glNe != null) ne = glNe + ", " + ne;
		eo_ne.put(eo,ne);
		Paro p = new Paro();
		p.eo = eo;
		p.ne = ne;
		vortaro.add(p);
		//if (l.contains("tajxafenestro")) break;
	    }
	} finally {
	    if (html) pw.println("</table></div></body></html>");
	    pw.close();
	}
	System.out.println("==============================\nnetraktitaj: "+netraktitaj);

	pw = new PrintWriter(new FileWriter("eo_ne."+(html?"html":"txt")));
	if (html) {
	    pw.println(
	    "<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=UTF-8\" />"
	    +"<title>Esperanta-Nepala</title></head>"
	    +"<body><div><h1>Esperanta-Nepala</h1><table border='0'>"
	    );
	}

	for (Iterator iter = eo_ne.keySet().iterator(); iter.hasNext(); ) {
	    String eo = (String) iter.next();
	    String ne = (String) eo_ne.get(eo);

	    if (html) pw.println("<tr><td>"+eo + "</td><td>" + ne+ "</td></tr>");
	    else pw.println(ne + "\t" + eo);
	}
	if (html) pw.println("</table></div></body></html>");
	pw.close();
    }



    public static String netraktitaj = "";



}

/* Problemoj - kontrolu



 8710याउकिरी (ना)	cikado
 8710याउ (ना)	musko
 8710याउरे (ना)	speco de popolkanto
 8710यापुल्लो (वि)	malfresxa
 8730 याल (ना)	fenestro
 8730 यालखाना (ना)	malliberejo
 8730 याली (ना)	cimbalo


 द212 (वि)	sperta, kapabla
 द212िण (ना)	sudo
 द212िण अफ्रिका(ना)	suda Afriko
 द212िण अमेरिका(ना)	suda Ameriko
 द212िणा(ना)	donacxo, honoario

 द207तर (ना)	oficejo

 धर्मनिरपे212 (वि)	sekulara

 न172न (संयो)	nek -nek




 ७नतसद्धा (ना)	odoro, estimo
 ७नतसम (ना)	laboro
 ७नतसमजीवी	profesia, taborista
 ७नतसमिक (ना)	laboristo
 ७नतसवण (ना)	auxdado
 ७नतसाध्द(ना)	prejxo al animoj de mortintoj
 ७नतसी(ना)	posedajxo, beleco; sinjoro
 ७नतसीखण्ड (ना)	santalo
 ७नतसीमान् (ना)	sinjoro
 ७नतसीलङ्का (ना)	Srilanko
 ७नतसेणी (ना)	rango, grado
 ७नतसेय (ना)	reputacio
 ७नतसेष्ठ (वि)	bonega, prefekta
 ७नतसोत (वि)	akvo-fonto
 ७नतसोता (ना)	auxskultanto
 श्वास (ना)	respiro



*/
