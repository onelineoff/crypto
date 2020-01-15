package davidweiss.crypto.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomUtil
{
	private static SecureRandom sr;
	
	static
	{
		sr = new SecureRandom();
	}
	
	/** Take the array of characters, and randomly rearrange them.
	 * 
	 * @param input
	 * @return The same characters, but in a random order.
	 */
	public static char[] scramble(char[] input)
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
	
	
	
	public static int getInt(int upperBound)
	{
		return sr.nextInt(upperBound);
	}
}
