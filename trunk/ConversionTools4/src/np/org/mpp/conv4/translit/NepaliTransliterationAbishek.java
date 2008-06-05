package np.org.mpp.conv4.translit;

import java.util.*;

import np.org.mpp.conv4.utils.*;
import np.org.mpp.conv4.utils.xml.*;

public class NepaliTransliterationAbishek implements ConversionHandler {

  HashMap<String, String> hashMap;

	public NepaliTransliterationAbishek() {
    SAXParser parser = new SAXParser("res/transliteration/NepaliALA-LC.xml");
    hashMap = parser.getHashMap();
	}


  public String giveFontReplacement(String font) { return font; }

  public String convertText(String font, String data) {
    StringBuffer transData = new StringBuffer();

		String ch = "";
		String nextCh = "";

		// extract each word
		String[] totSentence = data.toString().split(" ");

		String word = "";
		int wordLen = 0;
		int j = 0;

		for (int i = 0; i < totSentence.length; i++) {
			// get the word
			word = totSentence[i];
			wordLen = word.length();
			// reinitialize j for each new word
			j = 0;
			try {
				while (j < wordLen) {
					ch = word.substring(j, j + 1);
					/*
					 * check if last char. If last char then it is desirable to
					 * omit a from the string thus add the Zero width joiner
					 */
					if (j == wordLen - 1) {
						if (!ch.equals("ः") && !ch.equals("ृ")) {
							ch += "्";
						}
					} else {
						// get the next character in sequence

						nextCh = word.substring(j + 1, j + 2);

						if (isHalfCharacter(nextCh)) {
							ch += nextCh;
							j++;
						} else if (isCombination(nextCh)) {
							// if combination append the zero width joiner
							String trnsChr = hashMap.get(ch + "्");
							if (trnsChr!=null) {
								transData.append(trnsChr);
							}
							ch = nextCh;
							j++;
						}
					}
					String transChr = hashMap.get(ch);
					if (transChr != null) {
						transData.append(transChr);
					}
					j++;
				}
			} catch (StringIndexOutOfBoundsException s) {
        s.printStackTrace();
			}
			transData.append(" ");
		}
		return transData.toString();
	}

	private boolean isHalfCharacter(String nextCh) {
		return (nextCh.equals("्") ? true : false);
	}

	private boolean isCombination(String nextCh) {
		if (nextCh.equals("ा") || nextCh.equals("ि") || nextCh.equals("ी")
				|| nextCh.equals("ु") || nextCh.equals("ू")
				|| nextCh.equals("े") || nextCh.equals("ो")
				|| nextCh.equals("ै")) {
			// System.out.println("half char found");
			return true;
		} else {
			return false;
		}
	}
}
