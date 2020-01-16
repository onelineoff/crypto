package crypto.decrypt;

import org.junit.Test;

import crypto.util.PlainTextUtil;
import crypto.util.StringUtil;
	
public class BaseTextDecrypterTest 
{	
	@Test
	public void testMatches()
	{
		PlainTextUtil plainTextUtil = new PlainTextUtil();
		StringUtil stringUtil = new StringUtil();
		BaseTextDecrypter baseTextDecrypter = new BaseTextDecrypter();
		
		String plainText = plainTextUtil.getLargePlainText();
		System.out.println("word count is " + stringUtil.getNonEmptyWordCount(plainText));
		System.out.println("Dictionary word count is " + 
				baseTextDecrypter.getMatchCount(plainText));
		
	}
}
