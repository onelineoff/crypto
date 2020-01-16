package crypto.util;

import java.util.List;

public class StringUtil
{
	private static final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public char[] getLowerCaseLetters()
	{
		return lowercase;
	}
	
	public char[] getUpperCaseLetters()
	{
		String str = new String(lowercase);
		str = str.toUpperCase();
		return str.toCharArray();
	}
	
	public char[] getUpperCaseArray(char[] input)
	{
		return new String(input).toUpperCase().toCharArray();
	}
	
	/** Convert a list of strings to a single string.
	 *  This is done by concatenating all the strings together in order, without the eol.
	 * @param list The list of strings.
	 * @return A single concatenated string.
	 */
	public String getStringFromList(List<String> list) 
	{
		StringBuffer sb = new StringBuffer();
		for (String s : list)
		{
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	public String[] getWords(String text)
	{
		String[] words = text.split("\\W");
		return words;
	}
	
	public int getNonEmptyWordCount(String text)
	{
		String[] wordArr = getWords(text);
		
		int count = 0;
		for (String word : wordArr)
		{
			if (! isEmpty(word))
				count++;
		}
		
		return count;
	}
	
	public boolean isEmpty(String str)
	{
		boolean flag = true;
		
		if (str == null)
			flag = true;
		else if (str.length() == 0)
			flag = true;
		else
		{
			str = str.trim();
			flag = str.length() == 0;
		}
		return flag;
	}
}
