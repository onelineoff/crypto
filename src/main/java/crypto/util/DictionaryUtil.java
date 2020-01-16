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
	
	public DictionaryUtil()
	{
		fileUtil = new FileUtil();
	}
	
	/** Get a set of all the words in the dictionary.
	 * 
	 * @return The set of words
	 * @throws FileNotFoundException Thrown if the dictionary file is not found.
	 */
	public Set<String> getWords() throws FileNotFoundException
	{
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
		
		return set;
	}
	
	public String getDictionaryFileName()
	{
		return dictionaryFileName;
	}
}
