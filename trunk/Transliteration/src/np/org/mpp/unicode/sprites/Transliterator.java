package np.org.mpp.unicode.sprites;

/**
 * An interface to be implemented by all classes performing the transliteration
 *
 * @see NepaliTransliteration
 *
 * @author Abhishek
 *
 */
public interface Transliterator {

	public String transliterate(String unicodeString);
}
