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
		Alphabet alphabet = new Alphabet(Alphabet.LOWER_CASE);
		Assert.assertTrue(Arrays.equals(Alphabet.LOWER_CASE, alphabet.getCharArray()));
		alphabet.clearChar('a');
		Assert.assertEquals("-bcdefghijklmnopqrstuvwxyz", alphabet.getString());
		alphabet.clearChar('D');
		alphabet.clearChar('Q');
		alphabet.clearChar('T');
		alphabet.clearChar('g');
		alphabet.clearChar('h');
		Assert.assertTrue("-bc-ef--ijklmnop-rs-uvwxyz".equals(alphabet.getString()));
	}
	
	@Test
	public void testConvertToUpperCase()
	{
		Alphabet alphabet = new Alphabet("-bc-ef--ijklmnop-rs-uvwxyz".toCharArray());
		char[] arr = alphabet.convertToUpperCase();
		String expectedOutput = "-BC-EF--IJKLMNOP-RS-UVWXYZ";
		Assert.assertEquals(expectedOutput, alphabet.getString());
		Assert.assertEquals(expectedOutput, new String(arr));
	}

	@Test
	public void testGetMissingLetters()
	{
		char[] input = {'x', 'o', '-', 's', 'l', 'r', '-', 'i', 'd', '-', '-', '-', 'w', 'b', 'y', 
				'-', '-', 'h', 'p', 'f', 'e', '-', 't', '-', 'g', '-'};
		
		char[] expected = "acjkmnquvz".toCharArray();
		Alphabet alphabet = new Alphabet(input);
		char[] missing = alphabet.getMissingLetters();
		Assert.assertTrue(Arrays.equals(expected, missing));
	}
	
	@Test
	public void testGetMissingLettersByPosition()
	{
		char[] input = {'x', 'o', '-', 's', 'l', 'r', '-', 'i', 'd', '-', '-', '-', 'w', 'b', 'y', 
				'-', '-', 'h', 'p', 'f', 'e', '-', 't', '-', 'g', '-'};
		
		char[] expected = {'c', 'g', 'j', 'k', 'l', 'p', 'q', 'v', 'x', 'z'};
		Alphabet alphabet = new Alphabet(input);
		char[] missing = alphabet.getMissingLettersByPosition();
		Assert.assertTrue(Arrays.equals(expected, missing));
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
		Assert.assertTrue(Arrays.equals(restoreArr, charArr));
		
	}
	
	@Test
	public void testGetSize()
	{
		Alphabet alphabet = new Alphabet("mv-rz--cefBOIjkl-p-wxyns-u".toCharArray());
		Assert.assertEquals(20, alphabet.size());
	}
	
	@Test
	public void testSetChars()
	{
		Alphabet alphabet = new Alphabet("mv-rz--cefBOIjkl-p-wxyns-u".toCharArray());
		char[] missing = alphabet.getMissingLettersByPosition();
		char[] expected = "cfgqsy".toCharArray();
		Assert.assertTrue(Arrays.equals(expected, missing));
		
		alphabet.setChar('c', 'q');
		expected = "fgqsy".toCharArray();
		missing = alphabet.getMissingLettersByPosition();
		Assert.assertTrue(Arrays.equals(expected, missing));
		Assert.assertEquals("mvqrz--cefBOIjkl-p-wxyns-u", alphabet.getString());
		
		alphabet.setChar('a', 'v');
		alphabet.setChar('b', 'm');
		missing = alphabet.getMissingLettersByPosition();
		Assert.assertTrue(Arrays.equals(expected, missing));
		Assert.assertEquals("vmqrz--cefBOIjkl-p-wxyns-u", alphabet.getString());
	}
}
