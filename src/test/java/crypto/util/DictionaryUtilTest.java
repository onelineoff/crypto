package crypto.util;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class DictionaryUtilTest 
{
	@Test
	public void testDictionary()
	{
		DictionaryUtil dictionaryUtil = new DictionaryUtil();
		
		try {
			Set<String> words = dictionaryUtil.getWords();
			Assert.assertNotNull(words);
			Assert.assertTrue(words.size() > 10000);
			
			System.out.println("Found " + words.size() + " words");
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			Assert.fail();
		}
	}
}
