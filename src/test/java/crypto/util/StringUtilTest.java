package davidweiss.crypto.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest
{
	@Test
	public void testLowerCase()
	{
		char[] arr = StringUtil.getLowerCaseLetters();
		String lowerCase = new String(arr);
		Assert.assertEquals("abcdefghijklmnopqrstuvwxyz", lowerCase);
	}
	
	@Test
	public void testUpperCase()
	{
		char[] arr = StringUtil.getUpperCaseLetters();
		String upperCase = new String(arr);
		Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", upperCase);
	}
}
