package crypto.decrypt;

import java.util.List;

import crypto.util.FileUtil;

public class SubstitutionDecrypter extends BaseTextDecrypter implements DecryptText
{
	public SubstitutionDecrypter()
	{
		super();
	}
	
	@Override
	public String decryptText(String encryptedText)
	{
		List<Character> frequentCharList = stringUtil.getMostFrequentCharacters();
		List<String> frequentWordList = stringUtil.getMostFrequentWords();
		List<String> frequentBigramList = stringUtil.getMostFrequentBigrams();
		List<Character> frequentFirstCharList = stringUtil.getMostLikelyFirstCharacters();
		List<Character> infrequentFirstCharList = stringUtil.getLeastLikelyFirstCharacters();
		
		// Use frequency analysis of letters.
		// Use list of most common words
		// Use most common bigrams.
		// Frequency of first letter of a word differs from overall frequency of letters.
		
		// The character to substitute is at each position, e.g, if foundArr[3] == 'g', then g is 
		// substituted for d to decrypt.  This is meant for definite matches.
		Character[] foundArr = new Character[26];
				
		// This is the same structure as foundArr, but is the current decrypt key to be tested.
		Character[] currArr = new Character[26];
		
		String lowerCaseText = encryptedText.toLowerCase();
		String[] wordArray = stringUtil.getWords(lowerCaseText);
		List<String> bigramList = stringUtil.getMostCommonBigrams(lowerCaseText, 20);
		List<Character>charList = stringUtil.getMostCommonCharacters(lowerCaseText, 26);
		List<String> wordList = stringUtil.getMostCommonWords(lowerCaseText, 50);
		
		// Sequence of steps
		// 1. th should be the most popular bigram, and t should be a very popular letter.
		String th = bigramList.get(0);
		System.out.println("th is " + th);
		
		Character t = th.charAt(0);
		Character h = th.charAt(1);
		boolean match = false;
		for (int i=0; i<10; i++)
		{
			if (Character.compare(t, charList.get(i)) == 0)
				match = true;
		}
		
		if (!match)
			throw new RuntimeException("Did not find th bigram at the top.");
		
		// 2. Identify the word "the".  Contains bigram from step 1, e most popular letter, than t, then h.
		for (int i=0; i<5; i++)
		{
			String word = wordList.get(i);
			if ((word.length() == 3) && word.startsWith(th))
			{
				foundArr['t' - 'a'] = t;
				foundArr['h' - 'a'] = h;
				Character e = word.charAt(2);
				match = false;
				for (int j=0; j<3; j++)
				{
					if (Character.compare(t, charList.get(j)) == 0)
						match = true;
				}
				if (!match)
					throw new RuntimeException("Did not find the at the top of the word list.");
				
				foundArr['e' - 'a'] = e;
			}
		}
		// 3. Find the letter a.  Very popular, and contained in lots of popular words.
		boolean foundA = false;
		boolean foundI = false;
		
		for (String word : wordList)
		{
			if (!foundA && word.length() == 1)
			{
				foundArr[0] = word.charAt(0);
				foundA = true;
				System.out.println("a is " + word.charAt(0));
			}			
			else if (foundA && word.length() == 1)
			{
				foundArr['i' - 'a'] = word.charAt(0);
				System.out.println("i is " + word.charAt(0));
				foundI = true;
				break;
			}
		}
		// 4. Find the letter i. Look for the word "it".
		if (!foundI)
		{
			for (String word : wordList)
			{
				// Two letter word ending in t is either it or at.
				// Looking for it.
				if ((word.length() == 2) && 
						(Character.compare(word.charAt(0), foundArr[0]) != 0) &&
						(Character.compare(word.charAt(1), t) == 0))
				{
					foundArr['i' - 'a'] = word.charAt(0);
					System.out.println("i db2 is " + word.charAt(0));
				}
			}
		}
		// 5. Find the letter s.  Look for the words "as" and "is".
		
		// 6. Find the letter w.  Look for the words "was" and "with".
		for (String word : wordList)
		{
			if (word.length() == 4 && word.endsWith(th))
			{
				Character c = word.charAt(1);
				if (Character.compare(c, foundArr['i' - 'a']) == 0)
				{
					Character w = word.charAt(0);
					foundArr['w' - 'a'] = w;
					System.out.println("w is " + foundArr['w' - 'a']);
					break;
				}					
			}
		}
		
		// 7. Of most popular letters, still need to find o,r,n.  
		// Search for "to", "not", "in", ""no", "on", "are", "where", "other".
		// 8. Find the letter f. Look for the word "of", "for"
		// 9. Find the letter b.  Look for the word "be".
		// 10. Find the letter d. Look for the word "and".
		// 11. Look for the word "you" and find the letters y and u.
		// 12. Brute force.  There are 11 unidentified letters.  Search all possible remaining combinations.
		
		
		return null;
	}
	
	
}
