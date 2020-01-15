package crypto.encrypt;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import crypto.util.PlainTextUtil;

public class RotationCipherTest
{
	@Test
	public void testEncrypt()
	{
		String text = PlainTextUtil.getSomePlainText();
		RotationCipher cipher = new RotationCipher();
		String encryptedText = cipher.encrypt(text);
		
		System.out.println(text);
		System.out.println(encryptedText);
		
		Assert.assertNotNull(encryptedText);
		Assert.assertEquals(text.length(), encryptedText.length());
		
		int length = text.length();
		
		int rotation = 0;
		boolean first = true;
		
		for (int i=0; i<length; i++)
		{
			char plainChar = text.charAt(i);
			char encryptedChar = encryptedText.charAt(i);
			
			if (Character.isLetter(plainChar))
			{
				if (first)
				{ 
					rotation = diff(plainChar, encryptedChar);
					Assert.assertTrue(rotation > 0);
					first = false;
				}
				else
				{
					int currRotation = diff(plainChar, encryptedChar);
					Assert.assertEquals(rotation, currRotation);
				}
			}
			else
				Assert.assertEquals(plainChar, encryptedChar);
			
		}
		
		System.out.println("rotation is " + rotation);
	}
	
	private int diff (char c1, char c2)
	{
		int diff = c2 - c1;
		if (diff < 0)
		{
			diff += 26;
		}
		
		return diff;
	}
	
	@Test
	public void testRotation()
	{
		Assert.assertTrue(testIsRotated(null));
		Assert.assertTrue(testIsRotated(new char[0]));
		Assert.assertTrue(testIsRotated("abcdefg".toCharArray()));
		Assert.assertTrue(testIsRotated("ABCDEFG".toCharArray()));
		Assert.assertTrue(testIsRotated("1234!@#$".toCharArray()));
		
		Assert.assertFalse(testIsRotated("a".toCharArray()));
		Assert.assertFalse(testIsRotated("aaa".toCharArray()));
		
	}
	
	private boolean testIsRotated(char[] input)
	{
		char[] output = RotationCipher.rotate(input);
		if (input == null)
			return (output == null);
		else if (input.length == 0)
			return (output.length == 0);
		else
		{
			if (input.length != output.length)
				return false;
			
			if (Arrays.equals(input, output))
				return false;
			
			// This is the amount by which the characters are rotated.
			for (int i=1; i<input.length; i++)
			{
				boolean flag = true;
				for (int j=0; j<input.length; j++)
				{
					int rotatedIndex = (i + j) % input.length;
					char c1 = input[j];
					char c2 = output[rotatedIndex];
					if (c1 != c2)
					{
						flag = false;
						break;
					}
				}
				if (flag)
					return true;
			}
			
			// We tried all the rotation amounts, but none of them worked.
			return false;
		}
	}
}
