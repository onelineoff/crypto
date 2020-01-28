package crypto.decrypt;

import java.util.List;

import crypto.encrypt.SubstitutionCipher;
import crypto.util.CombinationUtil;
import crypto.util.DictionaryUtil;

public class SubstitutionDecrypter extends BaseTextDecrypter implements DecryptText
{
	public SubstitutionDecrypter()
	{
		super();
	}
	
	@Override
	public String decryptText(String encryptedText)
	{
		List<Character> commonCharList = stringUtil.getMostCommonCharacters(encryptedText, 10);
		
		// Use frequency analysis of letters.
		// Use list of most common words
		// Use most common bigrams.
		// Frequency of first letter of a word differs from overall frequency of letters.
		
		// The character to substitute is at each position, e.g, if foundArr[3] == 'g', then g is 
		// substituted for d to decrypt.  This is meant for definite matches.
		char[] foundArr = new char[26];
		
		String lowerCaseText = encryptedText.toLowerCase();
		List<String> bigramList = stringUtil.getMostCommonBigrams(lowerCaseText, 20);
		List<Character>charList = stringUtil.getMostCommonCharacters(lowerCaseText, 26);
		List<String> wordList = stringUtil.getMostCommonWords(lowerCaseText, 50);
		List<String> twoWordList = stringUtil.getMostCommonWords(lowerCaseText, 2, 50);
		List<String> threeWordList = stringUtil.getMostCommonWords(lowerCaseText, 3, 50);
		List<String> fourWordList = stringUtil.getMostCommonWords(lowerCaseText, 4, 50);
		
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
			String word = threeWordList.get(i);
			if (word.startsWith(th))
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
				System.out.println("e is " + e);
			}
		}
		// 3. Look for the single letter words "a" and "I".
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
		// 4. Find the letter i, if not already found above. Look for the word "it".
		if (!foundI)
		{
			for (String word : twoWordList)
			{
				// Two letter word ending in t is either it or at.
				// Looking for it.
				if ((Character.compare(word.charAt(0), foundArr[0]) != 0) &&
						(Character.compare(word.charAt(1), t) == 0))
				{
					foundArr['i' - 'a'] = word.charAt(0);
					System.out.println("i from multi-letter word is " + word.charAt(0));
				}
			}
		}
		
		// 5. Find the letter w.  Look for the word "with".
		for (String word : fourWordList)
		{
			if (word.endsWith(th))
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
		
		// 6. Find the letter o.  Look for the word "to"
		for (String word: twoWordList)
		{
			char c = word.charAt(0);
			if (Character.compare(c, foundArr['t' - 'a']) == 0)
			{
				Character o = word.charAt(1);
				foundArr['o' - 'a'] = o;
				System.out.println("o is " + foundArr['o' - 'a']);
				break;
			}
		}
		
		// 7. Find the letter f.  Look for the word "of"
		// The word "of" is very common, but the letter f is relatively infrequent.
		for (String word: twoWordList)
		{
			char c = word.charAt(0);
			if (Character.compare(c, foundArr['o' - 'a']) == 0)
			{
				Character f = word.charAt(1);
				if (commonCharList.contains(f))
				{
					continue;
				}
				
				foundArr['f' - 'a'] = f;
				System.out.println("f is " + foundArr['f' - 'a']);
				break;
			}
		}
		
		// 8. Find the letter b.  Look for the word "be".
		// Similarly to the letter f, the word "be" is very frequent, 
		// but the letter b is not.
		for (String word: twoWordList)
		{
			char c = word.charAt(1);
			if (Character.compare(c, foundArr['e' - 'a']) == 0)
			{
				Character b = word.charAt(0);
				if (commonCharList.contains(b))
				{
					continue;
				}
				
				foundArr['b' - 'a'] = b;
				System.out.println("b is " + foundArr['b' - 'a']);
				break;
			}
		}
		
		// 9. Find the letter n.  Look for the word "not".
		for (String word: threeWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			
			char o = foundArr['o' - 'a'];
			
			if (Character.compare(c1, o) == 0 && Character.compare(c2, t) == 0)
			{
				Character n = c0;
				foundArr['n' - 'a'] = n;
				System.out.println("n is " + foundArr['n' - 'a']);
				break;
			}
		}
		
		// 9. Find the letter s.  Look for the word "as".
		for (String word: twoWordList)
		{
			char c = word.charAt(0);
			if (Character.compare(c, foundArr[0]) == 0)
			{
				Character s = word.charAt(1);
				if (Character.compare(s, t) != 0 &&
						Character.compare(s, foundArr['n' - 'a']) != 0	)
				{
					foundArr['s' - 'a'] = s;
					System.out.println("s is " + foundArr['s' - 'a']);
					break;
				}

			}
		}
		
		// 7. Find the letter r.  Look for the words "are", "where", "other".  
		for (String word: threeWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			
			char a = foundArr[0];
			
			if (Character.compare(c0, a) == 0 && 
					Character.compare(c2, foundArr['e' - 'a']) == 0)
			{
				Character r = c1;
				foundArr['r' - 'a'] = r;
				System.out.println("r is " + foundArr['r' - 'a']);
				break;
			}
		}
		
		// 10. Find the letter d. Look for the word "and".
		for (String word: threeWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			
			char a = foundArr[0];
			
			if (Character.compare(c0, a) == 0 && 
					Character.compare(c1, foundArr['n' - 'a']) == 0)
			{
				Character d = c2;
				foundArr['d' - 'a'] = d;
				System.out.println("d is " + foundArr['d' - 'a']);
				break;
			}
		}
			
		// 11. Find the letter y.  Look for the word "by".
		for (String word: twoWordList)
		{
			char b = word.charAt(0);
			if (Character.compare(b, foundArr['b' - 'a']) == 0)
			{
				Character y = word.charAt(1);
				if (! commonCharList.contains(y))
				{
					foundArr['y' - 'a'] = y;
					System.out.println("y is " + foundArr['y' - 'a']);
					break;
				}
			}
		}
		
		// 12. Find the letter u. Look for the words "about", or "but".
		for (String word: threeWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			
			char b = foundArr['b' - 'a'];
			
			if (Character.compare(c0, b) == 0 && 
					Character.compare(c2, t) == 0)
			{
				Character u = c1;
				foundArr['u' - 'a'] = u;
				System.out.println("u is " + foundArr['u' - 'a']);
				break;
			}
		}
		
		// 13. Find the letter m.  Look for the word "them".
		for (String word: fourWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			char c3 = word.charAt(3);
			
			// Word start with "the".  Rule out "they" and "then".
			if (Character.compare(c0, t) == 0 && 
					Character.compare(c1, foundArr['h' - 'a']) == 0 &&
					Character.compare(c2, foundArr['e' - 'a']) == 0)
			{
				Character m = c3;
				if (Character.compare(m, foundArr['y' - 'a']) != 0 &&
						Character.compare(m, foundArr['n' - 'a']) != 0)
				{
					foundArr['m' - 'a'] = m;
					System.out.println("m is " + foundArr['m' - 'a']);
					break;
				}
			}
		}		
		
		// 14. Brute force.  There are 10 unidentified letters.  Search all possible remaining combinations.
		// This is the same structure as foundArr, but is the current decrypt key to be tested.
		char[] unknownLetterArray = {'c', 'g', 'j', 'k', 'l', 'p', 'q', 'v', 'x', 'z'};
		CombinationUtil combinationUtil = new CombinationUtil();
		List<String> combinationList = combinationUtil.getCombinations(unknownLetterArray);
		String decryptedText = "";
		int bestCount = 0;
		char[] currKey = new char[26];
		
		// These 16 letters are known, and only need to be set once.
		// Insert the 10 guesses into this array.
		setChar('a', getChar('a', foundArr), currKey);
		setChar('b', getChar('b', foundArr), currKey);
		setChar('d', getChar('d', foundArr), currKey);
		setChar('e', getChar('e', foundArr), currKey);
		setChar('f', getChar('f', foundArr), currKey);
		setChar('h', getChar('h', foundArr), currKey);
		setChar('i', getChar('i', foundArr), currKey);
		setChar('m', getChar('m', foundArr), currKey);
		setChar('n', getChar('n', foundArr), currKey);
		setChar('o', getChar('o', foundArr), currKey);
		setChar('r', getChar('r', foundArr), currKey);
		setChar('s', getChar('s', foundArr), currKey);
		setChar('t', getChar('t', foundArr), currKey);
		setChar('u', getChar('u', foundArr), currKey);
		setChar('w', getChar('w', foundArr), currKey);
		setChar('y', getChar('y', foundArr), currKey);
		
		DictionaryUtil dictionaryUtil = new DictionaryUtil();
		SubstitutionCipher substitutionCipher = new SubstitutionCipher();
		
		int guessCount = 0;
		for (String currGuess : combinationList)
		{
			guessCount++;
			if (guessCount % 100000 == 0)
			{
				System.out.println("Total guesses tested is " + guessCount);
			}
			for (int i=0; i< unknownLetterArray.length; i++)
			{
				setChar(currGuess.charAt(i), unknownLetterArray[i], currKey);
			}
			
			String potentialDecrypt = substitutionCipher.encrypt(
					encryptedText, currKey);
			int currCount = dictionaryUtil.getWordCount(potentialDecrypt);
			if (currCount > bestCount)
			{
				bestCount = currCount;
				decryptedText = potentialDecrypt;
				System.out.println(decryptedText);
				System.out.println("Decrypting, current word count is " + bestCount);
			}
		}
		
		return decryptedText;
	}
	
	public char getChar(char c, char[] arrMap)
	{
		return arrMap[c - 'a'];
	}
	
	public void setChar(char insertChar, char positionChar, char[] arrMap)
	{
		arrMap[positionChar - 'a'] = insertChar;
	}
	
	
}
