package crypto.decrypt;

import java.io.FileNotFoundException;
import java.util.Set;

import crypto.util.DictionaryUtil;
import crypto.util.StringUtil;

/** Useful base class for text decrypters.*/
public class BaseTextDecrypter {
	protected Set<String> dictionaryWords = null;
	protected StringUtil stringUtil;
	protected DictionaryUtil dictionaryUtil;
	
	public BaseTextDecrypter()
	{
		stringUtil = new StringUtil();
		dictionaryUtil = new DictionaryUtil();
	}
	
	/** Return the number of words in the text that are in the dictionary.
	 * 
	 * @param text The text to be processed.
	 * @return The number of dictionary words.
	 */
	protected int getMatchCount(String text)
	{
		if (dictionaryWords == null)
			try {
				dictionaryWords = dictionaryUtil.getWords();
			} catch (FileNotFoundException e) {
				System.out.println("Can't get dictionary, just returning 0 matches.");
				return 0;
			}
		
		int count = 0;
		
		String[] words = stringUtil.getWords(text);
		for (String word : words)
		{
			word = word.toLowerCase();
			if (dictionaryWords.contains(word))
				count++;
		}
		
		return count;		
	}
}
