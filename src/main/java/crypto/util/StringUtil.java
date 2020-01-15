package crypto.util;

public class StringUtil
{
	private static final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public static char[] getLowerCaseLetters()
	{
		return lowercase;
	}
	
	public static char[] getUpperCaseLetters()
	{
		String str = new String(lowercase);
		str = str.toUpperCase();
		return str.toCharArray();
	}
	
	public static char[] getUpperCaseArray(char[] input)
	{
		return new String(input).toUpperCase().toCharArray();
	}
}
