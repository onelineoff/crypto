package crypto.util;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class RandomUtilTest
{

	@Test
	public void testScramble()
	{
		Assert.assertTrue(testIsScrambled(null));
		Assert.assertTrue(testIsScrambled(new char[0]));
		Assert.assertTrue(testIsScrambled("abcdefg".toCharArray()));
		Assert.assertTrue(testIsScrambled("ABCDEFG".toCharArray()));
		Assert.assertTrue(testIsScrambled("1234!@#$".toCharArray()));
		
		Assert.assertFalse(testIsScrambled("a".toCharArray()));
		Assert.assertFalse(testIsScrambled("aaa".toCharArray()));
		
	}
	
	/** Determine if the scramble() method works correctly.
	 *  The following criteria are used:
	 *  If input is null, then output should be null too.
	 *  If input is an empty array, then output should be an empty array too.
	 *  
	 *  Otherwise, the scramble is correct if the following are all true:
	 *  1. The two arrays are the same length.
	 *  2. Each character in the input is in the output.
	 *  3. The two arrays are not in the same order.
	 *  
	 * @param input The characters to be scrambled.
	 * @return true if the conditions are met, or false.
	 */
	private boolean testIsScrambled(char[] input)
	{
		RandomUtil randomUtil = new RandomUtil();
		
		char[] output = randomUtil.scramble(input);
		
		if (input == null)
			return (output == null);
		else if (input.length == 0)
			return (output.length == 0);
		else
		{
			if (input.length != output.length)
				return false;
			
			for (int i=0; i<input.length; i++)
			{
				char c = input[i];
				boolean flag = false;
				for (int j=0; j<output.length; j++)
				{
					if (output[j] == c)
					{
						flag = true;
						break;
					}
				}
				
				if (flag == false)
					return false;
			}
			
			// All the above test will pass if you don't scramble the array at all.
			return (Arrays.equals(input, output) == false);
		}
	}
	
	
}
