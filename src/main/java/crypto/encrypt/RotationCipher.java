package crypto.encrypt;

import crypto.util.RandomUtil;
import crypto.util.StringUtil;

/** This class uses the Rotation Cipher, of which the Caesar Cipher is one example.
 *  The value to rotate by is selected randomly.
 *  @see https://en.wikipedia.org/wiki/Caesar_cipher
 */
public class RotationCipher implements EncryptText
{
	private RandomUtil randomUtil;
	private StringUtil stringUtil;
	
	public RotationCipher()
	{
		randomUtil = new RandomUtil();
		stringUtil = new StringUtil();
	}
	
	@Override
	/** Produce encypted text with a random rotation value between 1 and 25.
	 *  The encrypted characters will have the same case as the plaintext characters.
	 *  Characters which are not letters are not encrypted.
	 *  
	 *  @param plainText The text to be encrypted.
	 */
	public String encrypt(String plainText)
	{
		int offset = randomUtil.getInt(26);
		
		return encrypt(plainText, offset);
	}

	/** Produced encrypted text with the specified rotation value.
	 *  The encrypted characters will have the same case as the plaintext characters.
	 *  Characters which are not letters are not encrypted.
	 * 
	 * @param plainText The text to be encrypted.
	 * @param offset The rotation value.
	 * @return The encrypted text.
	 */
	public String encrypt(String plainText, int offset)
	{
		if (plainText == null)
			return plainText;
		else if (plainText.length() < 2)
			return plainText;
		
		// First, get the map of rotated letters, both lower and upper case.
		char[] lowercase = stringUtil.getLowerCaseLetters();
		
		// This produces the rotation map.
		char[] lowerCaseRotated = rotate(lowercase, offset);
		
		// Don't call rotate() again, or the lower case and upper case characters
		// will be rotated by a different amount.
		char[] upperCaseRotated = stringUtil.getUpperCaseArray(lowerCaseRotated);
		
		int length = plainText.length();
		char[] input = plainText.toCharArray();
		char[] output = new char[length];
		
		int lowerStartIndex = (int) 'a';
		int upperStartIndex = (int) 'A';
		
		// Process each character, encypting lower case letters as lower case,
		// upper case letters as upper case,
		// and the remaining characters unchanged.
		for (int i=0; i<length; i++)
		{
			char c = input[i];
			if (Character.isLowerCase(c))
			{
				int index = (int) c - lowerStartIndex;
				c = lowerCaseRotated[index];
			}
			else if (Character.isUpperCase(c))
			{
				int index = (int) c - upperStartIndex;
				c = upperCaseRotated[index];
			}
			else // Not a letter, leave unchanged.
			{
				output[i] = c;
			}
		}
		
		return new String(output);
	}
	
	/** Rotate the characters by the specified amount.
	 * 
	 * @param input The characters to be rotated.
	 * @param offset The amount of the rotation.
	 * @return The character array rotated by the specified amount.
	 */	
	protected char[] rotate(char[] input, int offset)
	{
		// Make sure the input at least two characters. 
		if (input == null || input.length < 2)
			return input;
		
		int length = input.length;
		char[] output = new char[length];
		
		// Make sure you understand how the modulus (%) operator works.
		for (int i=0; i<length; i++)
		{
			int rotatedIndex = (i + offset) % length;
			output[i] = input[rotatedIndex];
		}
		
		return output;
	}
	
	/** Rotate the characters by a random amount.
	 * 
	 * @param input The characters to be rotated.
	 * @return The rotated characters.
	 */
	protected char[] rotate(char[] input)
	{
		if ((input == null) || (input.length < 2))
			return input;
		
		int amount = randomUtil.getInt(input.length);
		
		return rotate(input, amount);	
	}
}
