package crypto.decrypt;

import crypto.dto.Alphabet;

public class RotationDecrypter extends BaseTextDecrypter implements DecryptText
{
	private String encryptedText;
	private Alphabet alphabet;
	private char[] lowercase;
	private char[] uppercase;
	
	public RotationDecrypter(String encryptedText) 
	{
		super();
		this.encryptedText = encryptedText;
		alphabet = new Alphabet();
		lowercase = Alphabet.LOWER_CASE;
		uppercase = Alphabet.UPPER_CASE;
	}
		
	@Override
	public String decryptText()
	{
		String plainText = encryptedText;
		int matches = getMatchCount(plainText);
		
		for (int i=1; i<26; i++)
		{
			String rotatedText = rotate(encryptedText, i);
			int count = getMatchCount(rotatedText);
			if (count > matches)
			{
				matches = count;
				plainText = rotatedText;
			}
		}
		
		return plainText;
	}
	
	private String rotate(String text, int rotation)
	{
		char[] charArray = text.toCharArray();
		int lowerStartIndex = (int) 'a';
		int upperStartIndex = (int) 'A';
				
		char[] rotatedLowerCase = new char[26];
		char[] rotatedUpperCase = new char[26];
		
		for (int i=0; i<26; i++)
		{
			int index = (i + rotation) % 26;
			rotatedLowerCase[index] = lowercase[i];
			rotatedUpperCase[index] = uppercase[i];
		}
		
		for (int i=0; i< charArray.length; i++)
		{
			char c = charArray[i];
			if (Character.isLowerCase(c))
			{
				int index = c - lowerStartIndex;
				charArray[i] = rotatedLowerCase[index];
			}
			else if (Character.isUpperCase(c))
			{
				int index = c - upperStartIndex;
				charArray[i] = rotatedUpperCase[index];
			}
		}
		
		return new String(charArray);
	}
	

}