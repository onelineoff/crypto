package crypto.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/** A utility class for performing random operations on text.*/
public class RandomUtil
{
	private SecureRandom sr;
	
	public RandomUtil()
	{
		sr = new SecureRandom();
	}
	
	/** Take the array of characters, and randomly rearrange them.
	 * 
	 * @param input The character array to scramble.
	 * @return The same characters, but in a random order.
	 */
	public char[] scramble(char[] input)
	{
		// Make sure the input contains something
		if (input == null)
			return input;
		else if (input.length == 0)
			return input;
		
		// Add the characters to a list.
		List<Character> charList = new ArrayList<Character>();
		for (char c : input)
		{
			charList.add(c);
		}
		
		int length = input.length;
		
		char[] outputArr = new char[length];
		
		// Randomly remove the characters from the list one at a time.
		// Put these random characters into the output array in order.
		for (int i=0; i<length; i++)
		{
			int numberLeft = length - i;
			int index = sr.nextInt(numberLeft);
			char c = charList.remove(index);
			outputArr[i] = c;
		}
		
		return outputArr;
	}
	
	/** Get an integer between 1 and one less than the upper bound.
	 * 
	 * @param upperBound One higher than the maximum integer to be returned.
	 * @return A random integer between 1 and (upperBound - 1).
	 */
	public int getInt(int upperBound)
	{
		if (upperBound <= 2)
			return 1;
		
		int val = 0;
		
		// The nextInt() call for SecureRandom does not allow a lower bound to
		// be specified, so just call until a number other than 0 is returned.
		while (val == 0)
			val = sr.nextInt(upperBound);
		
		return val;
	}
}
