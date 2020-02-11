package crypto.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest
{		
	@Test
	public void testGetWords()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		String plainText = plainTextUtils.getMorePlainText();
		String[] wordArr = stringUtil.getWords(plainText);
		Assert.assertEquals(41, wordArr.length);
		for (String word : wordArr)
		{
			if (word.trim().length() == 0)
				Assert.fail();
		}
	}
	
	@Test
	public void testGetWordsByLength()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		String plainText = plainTextUtils.getSomePlainText();
		for (int i=1; i<=8; i++) 
		{
			String[] wordArr = stringUtil.getWords(plainText, i);
			Assert.assertTrue(wordArr.length > 0);
			Assert.assertTrue(testWordsByLength(wordArr, i));
		}

		plainText = plainTextUtils.getMorePlainText();
		for (int i=1; i<=8; i++) 
		{	
			String[] wordArr = stringUtil.getWords(plainText, i);
			// There are no 4 letter words in this text sample.
			if (i == 4)
				Assert.assertFalse(wordArr.length > 0);
			else
			{
				Assert.assertTrue(wordArr.length > 0);
				Assert.assertTrue(testWordsByLength(wordArr, i));
			}
		}
	}
	
	/** Utility testing method for testing that all words in the array are the correct length.
	 * 
	 * @param wordArr The array to be tested.
	 * @param len The length of the words that's expected
	 * @return true if all the words are of the expected length, or the array is empty.
	 * 
	 */
	private boolean testWordsByLength(String[] wordArr, int len)
	{
		boolean flag = true;
		for (String word : wordArr)
		{
			if (word.length() != len)
				flag = false;
		}
		
		return flag;
	}
	
	@Test
	public void testGetWordCount()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		String plainText = plainTextUtils.getLargePlainText();
		int count = stringUtil.getWordCount(plainText);
		
		System.out.println("Word count from large plain text is " + count);
		Assert.assertEquals(1168, count);
	}
	
	@Test
	public void testGetMostCommonCharacters() 
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		int charNum = 10;
		String plainText = plainTextUtils.getLargePlainText();
		List<Character> charList = stringUtil.getMostCommonCharacters(plainText, charNum);
		Assert.assertNotNull(charList);
		Assert.assertEquals(charNum, charList.size());
		List<Character> commonCharacters = stringUtil.getMostFrequentCharacters();
		int count = 0;
		for (Character c : charList)
		{
			if (commonCharacters.contains(c))
				count++;
		}
		
		for (Character c : charList)
		{
			System.out.print(c);
			System.out.print(" ");
		}
		System.out.println();
		
		System.out.println("Most popular characters in text that are in top letters list is " + count);
		Assert.assertTrue(count >= charNum * 0.7f);
	}
	
	@Test
	public void testGetMostCommonWords()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		String plainText = plainTextUtils.getLargePlainText();
		plainText = plainText.toLowerCase();
		List<String> wordList = stringUtil.getMostCommonWords(plainText, 40);
		Assert.assertNotNull(wordList);
		Assert.assertEquals(40, wordList.size());
		
		List<String> commonWordList = stringUtil.getMostFrequentWords();
		int count = 0;
		
		for (String word : wordList)
		{
			System.out.println(word);
			if (commonWordList.contains(word))
				count++;
		}
		
		System.out.println("Number of popular words that are most popular words is " + count);
		Assert.assertTrue(count >= 25);
	}
	
	@Test
	public void testGetMostCommonTwoLetterWords()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		int total = 10;
		String plainText = plainTextUtils.getLargePlainText();
		List<String> wordList = stringUtil.getMostCommonWords(plainText, 2, total);
		Assert.assertNotNull(wordList);
		Assert.assertEquals(total, wordList.size());
		
		List<String> commonWordList = stringUtil.getMostFrequentWords();
		int count = 0;
		
		for (String word : wordList)
		{
			System.out.println(word);
			if (commonWordList.contains(word))
				count++;
		}
		
		System.out.println("Number of two letter popular words that are most popular words is " + count);
		Assert.assertTrue(count >= 8);
	}
	
	@Test
	public void testGetMostCommonBigrams()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		String plainText = plainTextUtils.getLargePlainText();
		
		List<String> bigrams = stringUtil.getMostFrequentBigrams();
		Assert.assertNotNull(bigrams);
		Assert.assertEquals(21, bigrams.size());
		
		// Get the most common bigrams from the text sample, and see
		// how many are in the most common bigrams in English in general.
		List<String> bigramList = stringUtil.getMostCommonBigrams(plainText, 20);
		Assert.assertNotNull(bigramList);
		Assert.assertEquals(20, bigramList.size());
		
		int count = 0;
		System.out.println("Most common bigrams in text are:");
		for (String bigram : bigramList)
		{
			if (bigrams.contains(bigram))
				count++;
			
			System.out.print(bigram);
			System.out.print(" ");
		}
		System.out.println();
		System.out.println("Number of popular bigrams in the popular list are " + count);		
	}
	
	@Test
	public void testGetMostLikelyFirstCharacters()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		List<Character> likelyList = stringUtil.getMostLikelyFirstCharacters();
		
		String plainText = plainTextUtils.getLargePlainText();
		List<Character> mostCommonList = stringUtil.getMostCommonFirstCharacters(plainText, 10);
		Assert.assertNotNull(mostCommonList);
		Assert.assertEquals(26, mostCommonList.size());
		
		int count = 0;
		for (Character c : mostCommonList)
		{
			if (likelyList.contains(c))
				count++;
			
			System.out.print(c);
			System.out.print(" ");
		}
		System.out.println();
		System.out.println("Of 10 most common characters in text, list contained " + count);
		Assert.assertTrue(count >= 5);
	}
	
	@Test
	public void testGetLeastLikelyFirstCharacters()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		List<Character> unlikelyList = stringUtil.getLeastLikelyFirstCharacters();
		
		String plainText = plainTextUtils.getLargePlainText();
		List<Character> leastCommonList = stringUtil.getLeastCommonFirstCharacters(plainText, 10);
		Assert.assertNotNull(leastCommonList);
		Assert.assertEquals(10, leastCommonList.size());
		
		int count = 0;
		for (Character c : leastCommonList)
		{
			if (unlikelyList.contains(c))
				count++;
			
			System.out.print(c);
			System.out.print(" ");
		}
		System.out.println();
		System.out.println("Of 10 least common characters in text, list contained " + count);
		Assert.assertTrue(count >= 3);
	}
	
	@Test
	public void testSortByValueDescending()
	{
		StringUtil stringUtil = new StringUtil();
		
		Map<String, Integer> frequencyMap = new HashMap<>();
		frequencyMap.put("bat", 1);
		frequencyMap.put("hello", 4);
		frequencyMap.put("the", 10);
		frequencyMap.put("a", 12);
		frequencyMap.put("to", 7);
		frequencyMap.put("porcupine", 1);
		frequencyMap.put("friend", 2);
		frequencyMap.put("house", 3);
		
		Map<String, Integer> sortedMap = stringUtil.sortByValueDescending(frequencyMap);
		Set<String> sortedSet = sortedMap.keySet();
		for (String word : sortedSet)
		{
			System.out.println(word);
		}
	}
	
	@Test
	public void testReverseChars()
	{
		StringUtil stringUtil = new StringUtil();
		
		char[] arr = {'s', 'k', 'h', 'q', 'i', 'j', 'c', 'f', 'm', 'n', 'o', 'p',
				'a', 'w', 'l', 'r', 'g', 'd', 'x', 'y', 'z', 'b', 't', 'u', 'v', 'e'};
		char[] reverseArr = new char[26];
		stringUtil.reverseChars(arr, reverseArr);
		System.out.println(new String(reverseArr));
		
		char[] restoreArr = new char[26];
		stringUtil.reverseChars(reverseArr, restoreArr);
		System.out.println(new String(restoreArr));
	}
}