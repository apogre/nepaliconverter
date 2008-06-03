package vortaro;

import java.util.*;

public class DevAlRomana {

  public DevAlRomana() {

    /* Rigardu
    http://en.wikipedia.org/wiki/Devanagari_transliteration
    */
/*
   <char trans="ṃ">ं</char>
   <char trans="ḥ">ः </char>
*/

/*
    karaktro(2305, "~",        'ँ' );    // ँ   - n-eca (naza)
    karaktro(2306, "~",        'ं' );    // ं  imoete    m-eca
    karaktro(2307, "~",        'ः' );    // ः    m-eca
 */

    karaktro(8205, "",        '‍' );    // zero-width separator to avoid combining characters

    karaktro(2305, "",        'ँ' );    // ँ   - n-eca (naza)
    karaktro(2306, "",        'ं' );    // ं  imoete    m-eca
    karaktro(2307, "/ḥ",        'ः' );    // ः    m-eca
    karaktro(2308, "",        'ऄ' );    // ऄ
    karaktro(2309, "a",        'अ' );    // अ
    karaktro(2310, "ā",        'आ' );    // आ
    karaktro(2311, "i",        'इ' );    // इ
    karaktro(2312, "ī",        'ई' );    // ई
    karaktro(2313, "u",        'उ' );    // उ
    karaktro(2314, "ū",        'ऊ' );    // ऊ
    karaktro(2315, "ṛi",        'ऋ' );    // ऋ
    karaktro(2316, "lri",        'ऌ' );    // ऌ
    karaktro(2317, "en",        'ऍ' );    // ऍ
    karaktro(2318, "en",        'ऎ' );    // ऎ
    karaktro(2319, "e",        'ए' );    // ए
    karaktro(2320, "ai",        'ऐ' );    // ऐ
    karaktro(2321, "ān",        'ऑ' );    // ऑ
    karaktro(2322, "ān",        'ऒ' );    // ऒ
    karaktro(2323, "o",        'ओ' );    // ओ
    karaktro(2324, "au",        'औ' );    // औ
    karaktro(2325, "ka",        'क' );    // क
    karaktro(2326, "kha",        'ख' );    // ख
    karaktro(2327, "ga",        'ग' );    // ग
    karaktro(2328, "gha",        'घ' );    // घ
    karaktro(2329, "ṅa",        'ङ' );    // ङ
    karaktro(2330, "cha",        'च' );    // च
    karaktro(2331, "chha",        'छ' );    // छ
    karaktro(2332, "ja",        'ज' );    // ज
    karaktro(2333, "jha",        'झ' );    // झ
    karaktro(2334, "ỹa",        'ञ' );    // ञ
    karaktro(2335, "ṭa",        'ट' );    // ट
    karaktro(2336, "ṭha",        'ठ' );    // ठ
    karaktro(2337, "ḍa",        'ड' );    // ड
    karaktro(2338, "ḍha",        'ढ' );    // ढ
    karaktro(2339, "ṇa",        'ण' );    // ण
    karaktro(2340, "ta",        'त' );    // त
    karaktro(2341, "tha",        'थ' );    // थ
    karaktro(2342, "da",        'द' );    // द
    karaktro(2343, "dha",        'ध' );    // ध
    karaktro(2344, "na",        'न' );    // न
    karaktro(2345, "ña",        'ऩ' );    // ऩ
    karaktro(2346, "pa",        'प' );    // प
    karaktro(2347, "pha",        'फ' );    // फ
    karaktro(2348, "ba",        'ब' );    // ब
    karaktro(2349, "bha",        'भ' );    // भ
    karaktro(2350, "ma",        'म' );    // म
    karaktro(2351, "ya",        'य' );    // य
    karaktro(2352, "ra",        'र' );    // र
    karaktro(2353, "ŕa",        'ऱ' );    // ऱ
    karaktro(2354, "la",        'ल' );    // ल
//    karaktro(2355, "Z",        'ळ' );    // ळ
//    karaktro(2356, "Z",        'ऴ' );    // ऴ
    karaktro(2357, "wa",        'व' );    // व
    karaktro(2358, "śa",        'श' );    // श
    karaktro(2359, "sha",        'ष' );    // ष
    karaktro(2360, "sa",        'स' );    // स
    karaktro(2361, "ha",        'ह' );    // ह
    karaktro(2362, "chha",        'ऺ' );    // ऺ
//    karaktro(2363, "ri",        'ऻ' );    // ऻ
    karaktro(2364, "",        '़' );    // ़
    karaktro(2365, ".",        'ऽ' );    // ऽ
    karaktro(2366, "/ā",        'ा' );    // ा
    karaktro(2367, "/i",        'ि' );    // ि
    karaktro(2368, "/ī",        'ी' );    // ी
    karaktro(2369, "/u",        'ु' );    // ु
    karaktro(2370, "/ū",        'ू' );    // ू
    karaktro(2371, "/ṛi",        'ृ' );    // ृ
    karaktro(2372, "/rị̄",        'ॄ' );    // ॄ
    karaktro(2373, "~",        'ॅ' );    // ॅ
    karaktro(2374, "~",        'ॆ' );    // ॆ
    karaktro(2375, "/e",        'े' );    // े
    karaktro(2376, "/ai",        'ै' );    // ै
    karaktro(2377, "a~",        'ॉ' );    // ॉ
    karaktro(2378, "a~",        'ॊ' );    // ॊ
    karaktro(2379, "/o",        'ो' );    // ो
    karaktro(2380, "/au",        'ौ' );    // ौ
    karaktro(2381, "/",        '्' );    // ्
    karaktro(2382, "",        'ॎ' );    // ॎ
    karaktro(2383, "",        'ॏ' );    // ॏ
    karaktro(2384, "aum",        'ॐ' );    // ॐ
    karaktro(2385, "",        '॑' );    // ॑
    karaktro(2386, "",        '॒' );    // ॒
    karaktro(2387, "",        '॓' );    // ॓
    karaktro(2388, "",        '॔' );    // ॔
    karaktro(2389, "~",        'ॕ' );    // ॕ
    karaktro(2390, "",        'ॖ' );    // ॖ
    karaktro(2391, "",        'ॗ' );    // ॗ
    karaktro(2392, "ka",        'क़' );    // क़
    karaktro(2393, "kha",        'ख़' );    // ख़
    karaktro(2394, "ga",        'ग़' );    // ग़
    karaktro(2395, "ja",        'ज़' );    // ज़
    karaktro(2396, "nga",        'ड़' );    // ड़
    karaktro(2397, "Dha",        'ढ़' );    // ढ़
    karaktro(2398, "pha",        'फ़' );    // फ़
    karaktro(2399, "ya",        'य़' );    // य़
    karaktro(2400, "ri",        'ॠ' );    // ॠ
    karaktro(2401, "",        'ॡ' );    // ॡ
    karaktro(2402, "",        'ॢ' );    // ॢ
    karaktro(2403, "",        'ॣ' );    // ॣ
    karaktro(2404, ".",        '।' );    // ।
    karaktro(2405, ":|",        '॥' );    // ॥
    karaktro(2406, "0",        '०' );    // ०
    karaktro(2407, "1",        '१' );    // १
    karaktro(2408, "2",        '२' );    // २
    karaktro(2409, "3",        '३' );    // ३
    karaktro(2410, "4",        '४' );    // ४
    karaktro(2411, "5",        '५' );    // ५
    karaktro(2412, "6",        '६' );    // ६
    karaktro(2413, "7",        '७' );    // ७
    karaktro(2414, "8",        '८' );    // ८
    karaktro(2415, "9",        '९' );    // ९
    karaktro(2416, "/a~",        '॰' );    // ॰

  }

  static public HashMap m = new HashMap();

  private void karaktro(int numero, String romana, char karaktro) {
    if (karaktro != numero) throw new IllegalStateException(""+((int)karaktro +"!="+ numero));
    //if (romana.length()==0) romana="Z"+numero;
    String k = ""+((char) numero);
    m.put(k, romana);
  }

  public String alRomana(String dev) {

    if (dev.equals("चमेना गृह")) {
      System.err.println("XXX3");
    }

    StringBuffer rom = new StringBuffer(dev.length()*3);
    for (int i=0; i<dev.length(); i++) {
      String d = dev.substring(i,i+1);
      String k = (String) m.get(d);
      if (k!=null) rom.append(k); else rom.append(d);
    }

    String r = rom.toString();

    // enmetu spacojn komence kaj fine por pli bone povi sercxi (ni forigos ilin denove)
    r = " " + r + " ";


    r=r.replaceAll("a/","");

    //r=r.replaceAll("a~","ã");
    r=r.replaceAll("a~","a");
    r=r.replaceAll("u~","ũ");
    r=r.replaceAll("i~","i");

    // forigu a fine de vortoj (sed ne aa)
    r=r.replaceAll("([^a])a ","$1 ");

    // kelkaj vortoj bezonas la lastan a por esti prononceblaj
    // v.d. http://en.wikipedia.org/wiki/Devanagari_transliteration#Pronunciation_of_the_final_.22a.22

    String[] tenuAVortoj = new String [] {
      "aba", "kaba", "jaba", "taba",
      "para", "tara", "gara", "tala", "tira", "chha", "jiiva", "daiva",
      "dhruva",
      "pidha",
      "pachaasa",
      "chaudha",
    };

    for (String s : tenuAVortoj) {
      s = s.substring(0,s.length()-1); // forpr a
      String r0 = r;
      r=r.replaceAll(" "+s+" ", " "+s+"a ");
      if (!r0.equals(r)) {
        System.err.println(dev + " "+r+" " + r0);
      }
    }

    String[] tenuAFinajxoj = new String [] {
        "mb",
        "mbh",
        "chchh",
        "nd",
        "rdh", "bdh", "ãdh", "ndh", "ddh", "ruddh", "gdh",
        "gg",
        "th", "mh",
        "gk", "kk",
        "ll",
        "rm", "sm",
        "rn", "hn", "jn", "tn", "mn", "nn", "gn", "hñ",
        "shp", "śp",
        "tt", "nt", "pt", "st", "kht",
        "shT", "shTh",
	"tr", "jr", "jr", "hr", "dr",
        "rsh", "ksh", "ãsh",
	"ry", "hy", "jy", "ty", "by", "iy",  "aay",
	"rv", "hv", "jv", "tv", "kv",
    };

    //System.out.println(new TreeSet(Arrays.asList(tenuAFinajxoj)));

    for (String s : tenuAFinajxoj) {
      r=r.replaceAll(s+" ", s+"a ");
     }


    /*  tenu A cxe: pokhta  praphulla  preta   banda  balla
    */
    String[] esploriAFinajxoj = new String [] {

    };

    /*
    tamen ne tenu la A cxe:
    pleTapharma -> pleTapharm
    */


    for (String s : esploriAFinajxoj) {
      String r2 = r.replaceAll(s+" ", s+"a ");
      if (!r2.equals(r)) {
	System.out.println("CXU "+s+" havu a?\t"+r+"  \t =>   "+r2+"  \t ?" );
      }
    }


/*
 gxustaj:
 phuT
 phuTpaatha
 phuTbal
 phuTaalnu


 briiphakes estu briiphkes
 bhakabhak estu bhakbhak

*/



    r=r.replaceAll("/","");

    // forprenu spacojn komence kaj fine denove
    if (r.endsWith(" ")) r=r.substring(0,r.length()-1);
    if (r.startsWith(" ")) r=r.substring(1);

    //System.out.println("'"+dev+"' -> '"+r+"'");
    return r;
  }



  public static void main(String[] args) {
    DevAlRomana devalromana = new DevAlRomana();



    for (int i = 2305; i < 2416; i++) {
      //System.out.println(i+"\t'" + ((char) i) +"'\t" + Character.valueOf((char)i));
      System.out.println("\tkaraktro("+i+", \"\",        '" + ((char) i) +"' );    // " + Character.valueOf((char)i));
    }


    devalromana.alRomana("टक्कर");
    devalromana.alRomana("टन्की");
    devalromana.alRomana("टुपी");
    devalromana.alRomana("टेपरिकर्डर");
    devalromana.alRomana("टेलिफोन");
    devalromana.alRomana("अँ");
    devalromana.alRomana("अङ्गुर");
    devalromana.alRomana("अङ्ग्रेजी");
  }
}
