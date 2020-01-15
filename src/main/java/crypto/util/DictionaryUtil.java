package davidweiss.crypto.util;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DictionaryUtil
{
	private static final String dictionaryFileName = "/usr/share/dict/words";
	
	public static Set<String> getWords()
	{
		File f = new File(dictionaryFileName);
		List<String> lines = FileUtil.getLines(f);
		HashSet<String> set = new HashSet<String>();
		
		if (lines != null)
			set.addAll(lines);
		
		return set;
	}
	
	public static String getDictionaryFileName()
	{
		return dictionaryFileName;
	}
}
