package crypto.util;

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
}
