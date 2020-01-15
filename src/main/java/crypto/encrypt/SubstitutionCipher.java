package davidweiss.crypto.encrypt;

import davidweiss.crypto.util.RandomUtil;
import davidweiss.crypto.util.StringUtil;

/** Replace each letter with another.
 *  This is the classical substitution cipher.
 *  
 *  Only the characters a-z, upper and lower case
 *  are encrypted, the rest are passed through unchanged.
 *  
 *  Preserve case, so if 'a' maps to 'l', 'A' will also map to 'L'.
 *  
 * @author dweiss
 *
 */
public class SubstitutionCipher implements EncryptText
{

	@Override
	public String encrypt(String plainText)
	{
		// First, get the map of scrambled letters, both lower and upper case.
		char[] lowercase = StringUtil.getLowerCaseLetters();
		char[] lowerCaseScrambled = RandomUtil.scramble(lowercase);
		char[] upperCaseScrambled = new String(lowerCaseScrambled).toUpperCase().toCharArray();
		
		
		char[] plainTextArr = plainText.toCharArray();
		int length = plainTextArr.length;
		char[] encryptedArr = new char[length];
		
		int lowerStartIndex = (int) 'a';
		int upperStartIndex = (int) 'A';
		
		for (int i=0; i<length; i++)
		{
			char c = plainTextArr[i];
			if (Character.isLowerCase(c))
			{
				int index = (int) c - lowerStartIndex;
				c = lowerCaseScrambled[index];
			}
			else if (Character.isUpperCase(c))
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
