package crypto.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Store information related to the alphabet, with various utility methods.
  * The encrypt/decrypt methods need to work on character arrays, or String 
  * representation of the alphabet.  This class encapsulates that functionality
  * in one place.
  * 
  * A big assumption is that this class only deals with English, or at least,
  * only text with characters in a-z, A-Z.
 */
public class Alphabet {
	public static final String LOWER_CASE_STRING = "abcdefghijklmnopqrstuvwxyz";
	public static final char[] LOWER_CASE = LOWER_CASE_STRING.toCharArray();
	
	public static final String UPPER_CASE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final char[] UPPER_CASE = UPPER_CASE_STRING.toCharArray();
	
	/** Use this character for unknown/missing elements in an array or String.*/
	public static final char SENTINEL = '-';
	
	private static final char[] emptyCharArray = 
		{'-','-','-','-','-','-','-','-','-','-','-','-','-',
				'-','-','-','-','-','-','-','-','-','-','-','-','-'};

	private char[] letters;
	private char[] reverseLetters;

	/** Create a new, empty Alphabet object.
	 * 
	 */
	public Alphabet()
	{
		letters = Arrays.copyOf(emptyCharArray, 26);
		reverseLetters = new char[26];
	}
	
	/** Create a new Alphabet object based on the input.
	 *  The index of the input array is the position of the letter in the alphabet,
	 *  so arr[0] is the letter that represents 'a', arr[9] represents 'j', and
	 *  arr[25] represents 'z'.
	 *  
	 *  If a partial array of characters is passed in, then the characters that are
	 *  not set should be initialized to the sentinel character.
	 * 
	 * @param arr An array of characters, or partial characters.
	 */
	public Alphabet(char[] arr)
	{
		if (arr.length == 26)
		{
			letters = Arrays.copyOf(arr, 26);
			reverseLetters = new char[26];
		}
		else
		{
			throw new RuntimeException("Input array must be 26 characters in length");
		}
	}
	
	/** Get the character array represented by this object.
	 * 
	 * @return The char[26] array with the letters from 'a' to 'z'.
	 */
	public char[] getCharArray()
	{
		return letters;
	}
	
	/** Return the letters are an upper case array.
	 * 
	 * @return An upper case array.
	 */
	public char[] convertToUpperCase()
	{
		letters = new String(letters).toUpperCase().toCharArray();
		return letters;
	}
	
	/** Clear a character in the alphabet.
	 * 
	 * @param c The position of the character to be cleared, 'a' - 'z'.
	 */
	public void clearChar(char c)
	{
		if (Character.isUpperCase(c))
			c = Character.toLowerCase(c);
		
		letters[c - 'a'] = SENTINEL;
	}
	
	/** Clear a character in the alphabet.
	 * 
	 * @param position The position of the character to be cleared, 0 - 25 inclusive.
	 */
	public void clearChar(int position)
	{
		letters[position] = SENTINEL;
	}
	
	/** Set a character in the alphabet.
	 * 
	 * @param positionChar The position of the character to be set.
	 * @param insertChar The character to be set.
	 */
	public void setChar(char positionChar, char insertChar)
	{
		if (Character.isUpperCase(positionChar))
			positionChar = Character.toLowerCase(positionChar);	
		
		letters[positionChar - 'a'] = insertChar;
	}
	
	/** Set a character in the alphabet.
	 * 
	 * @param position The position of the character to be set, 0 to 25 inclusive.
	 * @param insertChar The character to be set.
	 */
	public void setChar(int position, char insertChar)
	{
		letters[position] = insertChar;
	}
	
	/** Return all the lower case letters not in the input array.
	 * 
	 * @param knownLetters The known letters.
	 * @return An array of lower case letters not in the input array.
	 */
	public char[] getMissingLetters(char[] knownLetters)
	{
		int retLength = 26 - knownLetters.length;
		char[] retArr = new char[retLength];
		
		List<Character> missingLetters = new ArrayList<>();
		char[] lowercase = LOWER_CASE;
		for (Character c : lowercase)
		{
			missingLetters.add(c);
		}
		
		for (Character c : knownLetters)
		{
			if (Character.isUpperCase(c))
				c = Character.toLowerCase(c);
			
			if ((c >= 'a' && c<= 'z'))
			{
				missingLetters.remove(c);
			}
		}
		
		// Can't use missingLetters.toArray(char[])
		for (int i=0; i<missingLetters.size(); i++)
		{
			retArr[i] = missingLetters.get(i);
		}
		
		return retArr;
	}
	
	/** Return an array with the inverse mapping.
	 *  The input array is a mapping from plain text to substituted text.
	 *  So, for example, for the mapping a to g, b to d, and c to l,
	 *  in the return array, the mapping would be g to a, d to b, l to c
	 *  @return the reversed character array.
	 */
	public char[] getReverseChars()
	{
		for (int index=0; index<26; index++)
		{
			int position = letters[index] - 'a';
			reverseLetters[position] = LOWER_CASE[index];
		}
		
		return reverseLetters;
	}
}
