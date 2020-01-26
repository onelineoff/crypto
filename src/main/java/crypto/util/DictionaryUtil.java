package crypto.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Various utilities for accessing the dictionary.
 */
public class DictionaryUtil
{
	// TODO Move to a configuration file, and read from there.
	// This is Linux specific, and will need to be different for other operating systems.
	private static final String dictionaryFileName = "/usr/share/dict/words";
	
	private FileUtil fileUtil;
	private StringUtil stringUtil;
	private Set<String> wordSet;
	
	public DictionaryUtil()
	{
		fileUtil = new FileUtil();
		stringUtil = new StringUtil();
		wordSet = null;
	}
	
	/** Get a set of all the words in the dictionary.
	 * 
	 * @return The set of words
	 * @throws FileNotFoundException Thrown if the dictionary file is not found.
	 */
	public Set<String> getWords() throws FileNotFoundException
	{
		if (wordSet != null)
			return wordSet;
		
		File f = new File(dictionaryFileName);
		if (!f.exists())
		{
			String err = "The dictionary file " + dictionaryFileName +
				" does not exist on this computer";
			System.out.println(err);
			
			throw new FileNotFoundException(err);
		}
		
		List<String> lines = fileUtil.getLines(f);
		HashSet<String> set = new HashSet<String>();
		
		if (lines != null)
			set.addAll(lines);
		
		wordSet = set;
		return set;
	}
	
	public int getWordCount(String text)
	{
		int count = 0;
		try {
			getWords();
			
			String[] potentialWords = stringUtil.getWords(text);
			for (String potentialWord : potentialWords)
			{
				if (wordSet.contains(potentialWord))
					count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public String getDictionaryFileName()
	{
		return dictionaryFileName;
	}
}
