package crypto.util;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest
{
	@Test
	public void testLowerCase()
	{
		StringUtil stringUtil = new StringUtil();
		
		char[] arr = stringUtil.getLowerCaseLetters();
		String lowerCase = new String(arr);
		Assert.assertEquals("abcdefghijklmnopqrstuvwxyz", lowerCase);
	}
	
	@Test
	public void testUpperCase()
	{
		StringUtil stringUtil = new StringUtil();
		
		char[] arr = stringUtil.getUpperCaseLetters();
		String upperCase = new String(arr);
		Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", upperCase);
	}
	
	@Test
	public void testGetWords()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		String plainText = plainTextUtils.getLargePlainText();
		int count = stringUtil.getNonEmptyWordCount(plainText);
		
		System.out.println("Word count from large plain text is " + count);
		Assert.assertEquals(1168, count);
	}
	
	@Test
	public void testGetMostCommonCharacters() 
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		String plainText = plainTextUtils.getLargePlainText();
		List<Character> charList = stringUtil.getMostCommonCharacters(plainText, 10);
		Assert.assertNotNull(charList);
		Assert.assertEquals(10, charList.size());
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
		Assert.assertTrue(count >= 7);
	}
	
	@Test
	public void testGetMostCommonWords()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		String plainText = plainTextUtils.getLargePlainText();
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
	public void testGetMostCommonBigrams()
	{
		PlainTextUtil plainTextUtils = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		
		String plainText = plainTextUtils.getLargePlainText();
		
		List<String> bigrams = stringUtil.getMostFrequentBigrams();
		
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
		Assert.assertEquals(10, mostCommonList.size());
		
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
}