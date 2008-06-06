package np.org.mpp.conv4.utils;

public class Devanagari {

  /**
   * Lists of characters ordered by type.
   * Indexes are taken from the Character class.
   * <pre>
        UNASSIGNED                  = 0;
        OTHER_LETTER                = 5;
        NON_SPACING_MARK            = 6;
        COMBINING_SPACING_MARK      = 8;
        DECIMAL_DIGIT_NUMBER        = 9;
        OTHER_PUNCTUATION           = 24;
     </pre>
   * @see Character
   */
  public static String[] types = new String[40];

  public static final int TCONSONANTS = 31;
  public static final int TVOCALFLAGS = 32;
  public static final int TNAZALIZATIONS = 33;
  public static final int TALL = 34;


  static {
    types[TALL]="";

    karaktro(8205, "",        '‍' );    // zero-width separator to avoid combining characters

    karaktro(2305, "~",        'ँ' );    // ँ   - n-eca (naza)
    karaktro(2306, "~",        'ं' );    // ं  imoete    m-eca
    karaktro(2307, "~",        'ः' );    // ः    m-eca
    karaktro(2308, "",        'ऄ' );    // ऄ
    karaktro(2309, "a",        'अ' );    // अ
    karaktro(2310, "aa",        'आ' );    // आ
    karaktro(2311, "i",        'इ' );    // इ
    karaktro(2312, "ii",        'ई' );    // ई
    karaktro(2313, "u",        'उ' );    // उ
    karaktro(2314, "uu",        'ऊ' );    // ऊ
    karaktro(2315, "rhi",        'ऋ' );    // ऋ
    karaktro(2316, "lri",        'ऌ' );    // ऌ
    karaktro(2317, "en",        'ऍ' );    // ऍ
    karaktro(2318, "en",        'ऎ' );    // ऎ
    karaktro(2319, "e",        'ए' );    // ए
    karaktro(2320, "ai",        'ऐ' );    // ऐ
    karaktro(2321, "aan",        'ऑ' );    // ऑ
    karaktro(2322, "aan",        'ऒ' );    // ऒ
    karaktro(2323, "o",        'ओ' );    // ओ
    karaktro(2324, "au",        'औ' );    // औ
    karaktro(2325, "ka",        'क' );    // क
    karaktro(2326, "kha",        'ख' );    // ख
    karaktro(2327, "ga",        'ग' );    // ग
    karaktro(2328, "gha",        'घ' );    // घ
    karaktro(2329, "nga",        'ङ' );    // ङ
    karaktro(2330, "cha",        'च' );    // च
    karaktro(2331, "chha",        'छ' );    // छ
    karaktro(2332, "ja",        'ज' );    // ज
    karaktro(2333, "jha",        'झ' );    // झ
    karaktro(2334, "ỹa",        'ञ' );    // ञ
    karaktro(2335, "Ta",        'ट' );    // ट
    karaktro(2336, "Tha",        'ठ' );    // ठ
    karaktro(2337, "Da",        'ड' );    // ड
    karaktro(2338, "Dha",        'ढ' );    // ढ
    karaktro(2339, "ña",        'ण' );    // ण
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
    karaktro(2355, "Z",        'ळ' );    // ळ
    karaktro(2356, "Z",        'ऴ' );    // ऴ
    karaktro(2357, "va",        'व' );    // व
    karaktro(2358, "sha",        'श' );    // श
    karaktro(2359, "sha",        'ष' );    // ष
    karaktro(2360, "sa",        'स' );    // स
    karaktro(2361, "ha",        'ह' );    // ह
    karaktro(2362, "chha",        'ऺ' );    // ऺ
    karaktro(2363, "ri",        'ऻ' );    // ऻ
    karaktro(2364, "a/",        '़' );    // ़
    karaktro(2365, ".",        'ऽ' );    // ऽ
    karaktro(2366, "a",        'ा' );    // ा
    karaktro(2367, "/i",        'ि' );    // ि
    karaktro(2368, "/ii",        'ी' );    // ी
    karaktro(2369, "/u",        'ु' );    // ु
    karaktro(2370, "/uu",        'ू' );    // ू
    karaktro(2371, "/rhi",        'ृ' );    // ृ
    karaktro(2372, "/rhii",        'ॄ' );    // ॄ
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


    // konsonants are that affect the ii ि are:
    // from karaktro(2325, "ka",        'क' );    // क
    // to  karaktro(2361, "ha",        'ह' );    // ह
    // and from karaktro(2392, "ka",        'क़' );    // क़ि
    // to karaktro(2399, "ya",        'य़' );    // य़ि

    types[TCONSONANTS] = "";
    for (char c='क'; c<='ह'; c++)
      types[TCONSONANTS] += c;
    for (char c='क़'; c<='य़'; c++)
      types[TCONSONANTS] += c;


    types[TVOCALFLAGS] = "ि  ी ु ू ृ ॄ े ै ा ि ी ॉ ॊ ो ौ " .replaceAll(" ",""); // i ii u uu e ai

    types[TNAZALIZATIONS] = "ः" + types[Character.NON_SPACING_MARK].replaceAll("[्"+types[TVOCALFLAGS]+"]",""); // remove VOCALFLAGS


    System.out.println("types[COMBINING_SPACING_MARK]="+types[Character.COMBINING_SPACING_MARK]);
    System.out.println("types[NON_SPACING_MARK]="+types[Character.NON_SPACING_MARK]);
    System.out.println("types[CONSONANTS]="+types[TCONSONANTS]);
    System.out.println("types[VOCALFLAGS]="+types[TVOCALFLAGS]);
    System.out.println("types[NAZALIZATIONS]="+types[TNAZALIZATIONS]);
    System.out.println("types[ALL]="+types[TALL]);
  }

  /**
   * Zero Width Non Joiner (ZWNJ) is typically used to represent the separated
   * form of characters that normally fuse together to form a ligature.
   * @see http://madanpuraskar.org/index2.php?option=com_content&task=view&id=63&pop=1&page=0&Itemid=84
   */
  public static final String ZWNJ = "‌";
  public static final String ZWJ = "";  // XXX MISSING
  public static final String HALANTA = "्";
  public static final String CONSONANTS = types[TCONSONANTS];
  public static final String VOCALFLAGS = types[TVOCALFLAGS];
  public static final String NAZALIZATIONS = types[TNAZALIZATIONS];
  public static final String ALL = types[TALL];




  public static void main(String[] args) {
    Devanagari devanagari = new Devanagari();
  }



  private static void karaktro(int i, String rom, char c) {
    if (i != c) throw new InternalError("");
    types[TALL]=types[TALL]+c;

    int t = Character.getType(c);
    if (types[t]==null) types[t]=""+c;
    else types[t]=types[t]+c;

    //System.out.println(t+" "+c);
  }
}
