package davidweiss.crypto.encrypt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import davidweiss.crypto.util.PlainTextUtil;

public class SubstitutionCipherTest
{
	@Test
	public void testEncrypt()
	{
		String input = PlainTextUtil.getSomePlainText();
		
		SubstitutionCipher sc = new SubstitutionCipher();
		String output = sc.encrypt(input);
		
		System.out.println(input);
		System.out.println(output);
		
		Assert.assertEquals(input.length(), output.length());
		
		// Check all lower and upper case letters.
		char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for (char c : letters)
		{
			List<Integer> list = findPositionsOfLetter(input, c);
			
			// The character doesn't occur in the input, ignore it.
			if (list.size() == 0)
				continue;
			
			char encryptedChar = output.charAt(list.get(0));
			List<Integer> encryptedList = findPositionsOfLetter(output, encryptedChar);
			Assert.assertTrue(Arrays.equals(list.toArray(), encryptedList.toArray()));
			System.out.println(c + " " + encryptedChar);
		}
		
		char[] nonLetters = " ().,".toCharArray();
		for (char c : nonLetters)
		{
			List<Integer> list = findPositionsOfLetter(input, c);
			List<Integer> encryptedList = findPositionsOfLetter(output, c);
			Assert.assertTrue(Arrays.equals(list.toArray(), encryptedList.toArray()));
		}
	}
	
	/** Get all of the positions in the string where the character occurs.
	 * 
	 * @param str The string being searched
	 * @param c The character being searched for
	 * @return A list of all the positions where the character occurs.
	 */
	private List<Integer> findPositionsOfLetter(String str, char c)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		int length = str.length();
		for (int i=0; i<length; i++)
		{
			char curr = str.charAt(i);
			if (curr == c)
				list.add(i);
		}
		return list;
	}
}
