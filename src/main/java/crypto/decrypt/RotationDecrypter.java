package crypto.decrypt;

import java.util.Set;

import crypto.util.DictionaryUtil;
import crypto.util.StringUtil;


public class RotationDecrypter implements DecryptText
{
	private Set<String> dictionaryWords = null;
	
	@Override
	public String decryptText(String encryptedText)
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
		
		char[] lowerCase = StringUtil.getLowerCaseLetters();
		char[] upperCase = StringUtil.getUpperCaseLetters();
		
		char[] rotatedLowerCase = new char[26];
		char[] rotatedUpperCase = new char[26];
		
		for (int i=0; i<26; i++)
		{
			int index = (i + rotation) % 26;
			rotatedLowerCase[index] = lowerCase[i];
			rotatedUpperCase[index] = upperCase[i];
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
	
	private int getMatchCount(String text)
	{
		if (dictionaryWords == null)
			dictionaryWords = DictionaryUtil.getWords();
		
		int count = 0;
		
		String[] words = getWords(text);
		for (String word : words)
		{
			word = word.toLowerCase();
			if (dictionaryWords.contains(word))
				count++;
		}
		
		return count;
		
	}
	
	private String[] getWords(String text)
	{
		String[] words = text.split(" ");
		return words;
	}

}
