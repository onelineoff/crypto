package davidweiss.crypto.util;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class DictionaryUtilTest 
{
	@Test
	public void testDictionary()
	{
		Set<String> words = DictionaryUtil.getWords();
		Assert.assertNotNull(words);
		Assert.assertTrue(words.size() > 10000);
		
		System.out.println("Found " + words.size() + " words");
	}
}
