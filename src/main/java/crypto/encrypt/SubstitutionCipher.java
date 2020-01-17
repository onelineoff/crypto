package crypto.encrypt;

import crypto.util.RandomUtil;
import crypto.util.StringUtil;

/** Replace each letter with another.
 *  This is the classical substitution cipher.
 *  
 *  Only the characters a-z, upper and lower case
 *  are encrypted, the rest are passed through unchanged.
 *  
 *  Preserve case, so if 'a' maps to 'l', 'A' will also map to 'L'.
 *  
 *  Note that this is <b>MUCH</b> harder to solve through brute force
 *  than the Rotation Cipher, about 25! possibilities.
 *
 */
public class SubstitutionCipher implements EncryptText
{
	private RandomUtil randomUtil;
	private StringUtil stringUtil;
	
	public SubstitutionCipher()
	{
		randomUtil = new RandomUtil();
		stringUtil = new StringUtil();
	}
	
	@Override
	public String encrypt(String plainText)
	{
		// First, get the map of scrambled letters, both lower and upper case.
		char[] lowercase = stringUtil.getLowerCaseLetters();
		char[] lowerCaseScrambled = randomUtil.scramble(lowercase);
		char[] upperCaseScrambled = new String(lowerCaseScrambled).toUpperCase().toCharArray();
		
		
		char[] plainTextArr = plainText.toCharArray();
		int length = plainTextArr.length;
		char[] encryptedArr = new char[length];
		
		int lowerStartIndex = (int) 'a';
		int upperStartIndex = (int) 'A';
		
		// Perform the substitution from the plain text to the encrypted character array.
		for (int i=0; i<length; i++)
		{
			char c = plainTextArr[i];
			if (stringUtil.isLowerCase(c))
			{
				int index = (int) c - lowerStartIndex;
				c = lowerCaseScrambled[index];
			}
			else if (stringUtil.isUpperCase(c))
			{
				int index = (int) c - upperStartIndex;
				c = upperCaseScrambled[index];
			}
			// If the character isn't a letter, leave it unchanged.
			
			encryptedArr[i] = c;
		}
		
		return new String(encryptedArr);
	}
}
