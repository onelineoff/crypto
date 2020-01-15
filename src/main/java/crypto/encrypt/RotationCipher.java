package davidweiss.crypto.encrypt;

import davidweiss.crypto.util.RandomUtil;
import davidweiss.crypto.util.StringUtil;

public class RotationCipher implements EncryptText
{

	@Override
	public String encrypt(String plainText)
	{
		int offset = 0;
		while (offset == 0)
			offset = RandomUtil.getInt(26);
		
		return encrypt(plainText, offset);
	}

	protected String encrypt(String plainText, int offset)
	{
		if (plainText == null)
			return plainText;
		else if (plainText.length() < 2)
			return plainText;
		
		// First, get the map of rotated letters, both lower and upper case.
		char[] lowercase = StringUtil.getLowerCaseLetters();
		char[] lowerCaseRotated = rotate(lowercase, offset);
		char[] upperCaseRotated = StringUtil.getUpperCaseArray(lowerCaseRotated);
		
		int length = plainText.length();
		char[] input = plainText.toCharArray();
		char[] output = new char[length];
		
		int lowerStartIndex = (int) 'a';
		int upperStartIndex = (int) 'A';
		
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
			// If the character isn't a letter, leave it unchanged.

			output[i] = c;
		}
		
		return new String(output);
	}
	
	protected static char[] rotate(char[] input, int offset)
	{
		// Make sure the input at least two characters. 
		if (input == null)
			return input;
		else if (input.length < 2)
			return input;
		
		int length = input.length;
		char[] output = new char[length];
		
		for (int i=0; i<length; i++)
		{
			int rotatedIndex = (i + offset) % length;
			output[i] = input[rotatedIndex];
		}
		
		return output;
	}
	
	protected static char[] rotate(char[] input)
	{
		if ((input == null) || (input.length == 0))
			return input;
		
		int amount = 0;
		while (amount == 0)
		{
			amount = RandomUtil.getInt(input.length);
		}
		
		return rotate(input, amount);	
	}
}
