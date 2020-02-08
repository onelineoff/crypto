package crypto.dto;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class AlphabetTest {
	@Test
	public void testLowerCase()
	{		
		char[] arr = Alphabet.LOWER_CASE;
		String lowerCase = new String(arr);
		Assert.assertEquals("abcdefghijklmnopqrstuvwxyz", lowerCase);
	}
	
	@Test
	public void testUpperCase()
	{		
		char[] arr = Alphabet.UPPER_CASE;
		String upperCase = new String(arr);
		Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", upperCase);
	}
	
	@Test
	public void testClearChar()
	{
		
	}
	
	@Test
	public void testConvertToUpperCase()
	{
		
	}

	@Test
	public void testGetMissingLetters()
	{
		
	}
	
	@Test
	public void testGetReverseChars()
	{
		char[] arr = {'s', 'k', 'h', 'q', 'i', 'j', 'c', 'f', 'm', 'n', 'o', 'p',
				'a', 'w', 'l', 'r', 'g', 'd', 'x', 'y', 'z', 'b', 't', 'u', 'v', 'e'};
		
		Alphabet alphabet = new Alphabet(arr);
		char[] charArr = alphabet.getCharArray();
		char[] reverseArr = alphabet.getReverseChars();
		System.out.println(new String(charArr));
		System.out.println(new String(reverseArr));
		
		Alphabet reverseAlphabet = new Alphabet(reverseArr);
		char[] restoreArr = reverseAlphabet.getReverseChars();
		System.out.println(new String(restoreArr));
		Assert.assertTrue(Arrays.equals(restoreArr, charArr));
	}
	
	@Test
	public void testSetChars()
	{
		
	}
}
