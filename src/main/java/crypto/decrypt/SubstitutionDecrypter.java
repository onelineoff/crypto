package crypto.decrypt;

import java.util.ArrayList;
import java.util.List;

import crypto.encrypt.SubstitutionCipher;
import crypto.util.CombinationUtil;
import crypto.util.DictionaryUtil;

public class SubstitutionDecrypter extends BaseTextDecrypter implements DecryptText
{
	/** These are the letters not guessed from text analysis.*/
	private char[] unknownLetterArray = {'c', 'g', 'j', 'k', 'l', 'p', 'q', 'v', 'x', 'z'}; 
	private String encryptedText;
	private List<Character> commonCharList;
	private char[] foundArr;
	private String lowerCaseText;
	private List<String> bigramList;
	private List<Character>charList;
	private List<String> wordList;
	private List<String> twoWordList;
	private List<String> threeWordList;
	private List<String> fourWordList;
	private char[] reverseKey;
	
	public SubstitutionDecrypter(String encryptedText)
	{
		super();
		this.encryptedText = encryptedText;
		commonCharList = stringUtil.getMostCommonCharacters(encryptedText, 10);
		// The character to substitute is at each position, e.g, if foundArr[3] == 'g', then g is 
		// substituted for d to decrypt.  This is meant for definite matches.
		foundArr = new char[26];
		reverseKey = new char[26];

		lowerCaseText = encryptedText.toLowerCase();
		bigramList = stringUtil.getMostCommonBigrams(lowerCaseText, 20);
		charList = stringUtil.getMostCommonCharacters(lowerCaseText, 26);
		wordList = stringUtil.getMostCommonWords(lowerCaseText, 50);
		twoWordList = stringUtil.getMostCommonWords(lowerCaseText, 2, 50);
		threeWordList = stringUtil.getMostCommonWords(lowerCaseText, 3, 50);
		fourWordList = stringUtil.getMostCommonWords(lowerCaseText, 4, 50);
	}

	@Override
	/** Decrypt text that is encrypted using the simplest form of the substitution cipher.
	 *  In this algorithm, the encrypted text has all spacing, capitalization, and punctuation
	 *  of the plaintext, and a single letter substitution is done.
	 *  
	 *  The method used here is fairly simple, and leaves a lot of room for additional robustness,
	 *  as well as increased efficiency.  The code is meant to be as simple to understand as possible.
	 *  
	 *  The following basic ideas are used:
	 *  <UL>
	 *  <LI>Some characters are more common than others.
	 *  <LI>Some characters are more or less common if they are the first letter of a word.
	 *  <LI>Some words are more common than others.
	 *  <LI>Some bigrams (two letter sequences) are more common than if letters were mixed randomly.
	 *  </UL>
	 *  The ideas above can be used to identify a subset of the encrypted text fairly easily.  Once
	 *  the number of unknown letters is reduced to a manageable size, a brute force attack can be used.
	 *  
	 *  The criteria for success is how many of the decrypted words matches a standard dictionary.  Each
	 *  time a guess of the decryption key increases the number of dictionary words found, the new key
	 *  is retained as the best guess so far.
	 *  
	 *  @return The decrypted text.
	 */
	public String decryptText()
	{
		// First, find some letters that are fairly easy to guess.
		// Once the unknown list is smaller, use brute force the guess the rest.
		findThe();
		
		boolean foundI = findAAndI();		
		if (!foundI)
			findIt();
		
		findW();
		findO();
		findF();		
		findB();
		findN();		
		findS();
		findR();
		findD();
		findY();
		findU();
		findM();

		String decryptedText = executeBruteForceSearch();
		
		return decryptedText;
	}

	/** Execute a brute force search to examine all possibilities of letters which are still unknown.
	 *  Since the brute force algorithm run time complexity is n!, where n is the number of unknown
	 *  letters, you need to verify manually that the unknown letter size is not too large.
	 *  
	 *  On my home computer, 10 unknown letter takes about 8.5 minutes to guess.
	 *  
	 * @return The decrypted text.
	 */
	private String executeBruteForceSearch() {
		
		// First, put all missing letters into the list.
		List<Character> missingLetters = new ArrayList<>();
		char[] lowercase = stringUtil.getLowerCaseLetters();
		for (Character c : lowercase)
		{
			missingLetters.add(c);
		}
		
		// Remove all letters already found, so that missingLetters contains all unknown letters.
		// The letters being removed are the encrypted ones, not the plaintext ones.
		for (Character c : foundArr)
		{
			if (stringUtil.isLowerCase(c))
			{
				missingLetters.remove(c);
			}
		}
		
		Character[] missingArr = new Character[missingLetters.size()];
		missingLetters.toArray(missingArr);
		CombinationUtil combinationUtil = new CombinationUtil();
		List<String> combinationList = combinationUtil.getCombinations(missingArr);
		String decryptedText = "";
		int bestCount = 0;
		char[] currKey = new char[26];
		
		// These 16 letters are known, and only need to be set once.
		// Insert the 10 guesses into this array.
		stringUtil.setChar('a', stringUtil.getChar('a', foundArr), currKey);
		stringUtil.setChar('b', stringUtil.getChar('b', foundArr), currKey);
		stringUtil.setChar('d', stringUtil.getChar('d', foundArr), currKey);
		stringUtil.setChar('e', stringUtil.getChar('e', foundArr), currKey);
		stringUtil.setChar('f', stringUtil.getChar('f', foundArr), currKey);
		stringUtil.setChar('h', stringUtil.getChar('h', foundArr), currKey);
		stringUtil.setChar('i', stringUtil.getChar('i', foundArr), currKey);
		stringUtil.setChar('m', stringUtil.getChar('m', foundArr), currKey);
		stringUtil.setChar('n', stringUtil.getChar('n', foundArr), currKey);
		stringUtil.setChar('o', stringUtil.getChar('o', foundArr), currKey);
		stringUtil.setChar('r', stringUtil.getChar('r', foundArr), currKey);
		stringUtil.setChar('s', stringUtil.getChar('s', foundArr), currKey);
		stringUtil.setChar('t', stringUtil.getChar('t', foundArr), currKey);
		stringUtil.setChar('u', stringUtil.getChar('u', foundArr), currKey);
		stringUtil.setChar('w', stringUtil.getChar('w', foundArr), currKey);
		stringUtil.setChar('y', stringUtil.getChar('y', foundArr), currKey);
		
		DictionaryUtil dictionaryUtil = new DictionaryUtil();
		
		int guessCount = 0;
		for (String currGuess : combinationList)
		{
			
			guessCount++;
			if (guessCount % 500000 == 0)
			{
				System.out.println("Total guesses tested is " + guessCount);
			}
			
			String potentialDecrypt = decrypt(encryptedText, currGuess, 
					currKey);
			int currCount = dictionaryUtil.getWordCount(potentialDecrypt);
			if (currCount > bestCount)
			{
				bestCount = currCount;
				decryptedText = potentialDecrypt;
				System.out.println(decryptedText);
				System.out.println("Decrypting, current word count is " + bestCount);
				System.out.println("reverseKey is " + new String(reverseKey));
				
			}
		}
		
		return decryptedText;
	}

	/** Find the letter s.
	 *  Look for the word as.
	 */
	protected void findS() {
		Character t = stringUtil.getChar('t', foundArr);
		
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
	}

	/** Find the letter n.
	 *  Look for the word not.
	 */
	protected void findN() {
		for (String word: threeWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			
			char o = stringUtil.getChar('o', foundArr);
			char t = stringUtil.getChar('t', foundArr);
			
			if (Character.compare(c1, o) == 0 && Character.compare(c2, t) == 0)
			{
				Character n = c0;
				foundArr['n' - 'a'] = n;
				System.out.println("n is " + foundArr['n' - 'a']);
				break;
			}
		}
		
	}

	/** Find the letter b.  
	 * Look for the word "be".  Similarly to the letter f, the word "be" is very frequent,
	 * but the letter b is not.
	 */
	protected void findB() {
		for (String word: twoWordList)
		{
			char c = word.charAt(1);
			if (Character.compare(c, foundArr['e' - 'a']) == 0)
			{
				Character b = word.charAt(0);
				if (commonCharList.contains(b))
				{
					System.out.println("Searching for b, found " + b);
					continue;
				}
				
				foundArr['b' - 'a'] = b;
				System.out.println("b is " + foundArr['b' - 'a']);
				break;
			}
		}
		
	}

	/** Find the letter f.  The word of is very common, but f is relatively infrequent.*/
	protected void findF() {
		for (String word: twoWordList)
		{
			char c = word.charAt(0);
			if (Character.compare(c, foundArr['o' - 'a']) == 0)
			{
				Character f = word.charAt(1);
				if (commonCharList.contains(f))
				{
					System.out.println("Searching for f, found " + f);
					continue;
				}
				
				foundArr['f' - 'a'] = f;
				System.out.println("f is " + foundArr['f' - 'a']);
				break;
			}
		}
		
	}

	/** Find the letter o by looking for the word to.
	 * 
	 */
	protected void findO() {
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
		
		
	}

	/** Find the letter m.
	 *  Look for the word them.
	 */
	protected void findM() {
		Character t = stringUtil.getChar('t', foundArr);
		for (String word: fourWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			char c3 = word.charAt(3);
			
			// The word starts with "the".  
			// Other likely possibilities are the words "they" and "then".
			// The letters n and y are already known, so rule those out.
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
		
	}
	
	/** Find the letter u.
	 *  Look for the word but.
	 */
	protected void findU() {
		char t = stringUtil.getChar('t', foundArr);
		
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
		
		
	}
	
	/** Find the letter y.
	 *  Look for the word by.
	 */
	protected void findY() {
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
		
		
	}
	
	/** Find the letter d.
	 *  Look for the word are.
	 */
	protected void findD() {
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
		
	}
	protected void findW() {
		String th = bigramList.get(0);
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
	}

	/** Find the letter r.
	 *  Look for the word are.
	 */
	protected void findR() { 
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
		
	}
	protected void findIt() {
		for (String word : twoWordList)
		{
			// Two letter word ending in t is either it or at.
			// Looking for it.
			char t = stringUtil.getChar('t', foundArr);
			if ((Character.compare(word.charAt(0), foundArr[0]) != 0) &&
					(Character.compare(word.charAt(1), t) == 0))
			{
				foundArr['i' - 'a'] = word.charAt(0);
				System.out.println("i from multi-letter word is " + word.charAt(0));
			}
		}
	}

	protected boolean findAAndI() {
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
		
		return foundI;
	}

	protected void findThe() {
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
				for (int j=0; j<5; j++)
				{
					if (Character.compare(e, charList.get(j)) == 0)
						match = true;
				}
				if (!match)
					throw new RuntimeException("Did not find the at the top of the word list.");
				
				foundArr['e' - 'a'] = e;
				System.out.println("e is " + e);
			}
		}
		
	}

	/** Decrypt the encrypted text, based on the input parameters.
	 * The basic idea here is that performing the substitution cipher twice will
	 * decrypt the text if the key mapping is reversed.
	 * That is, if a -> d, b -> y, and c-> m, then the reverseKey would be
	 * d -> a, y -> b, and m -> c. 
	 * 
	 * @param encryptedText The encrypted text.
	 * @param currGuess This is a guess for the letters that are not yet known.
	 * @param currKey This contains all the known letters.  It is updated from currGuess.	 * 
	 * @return The decrypted text.
	 */
	protected String decrypt(String encryptedText, String currGuess, 
			char[] currKey)
	{
		// For each of the unknown letters, put the current guess into it,
		// leaving the letters that are already known.
		for (int i=0; i< unknownLetterArray.length; i++)
		{
			stringUtil.setChar(unknownLetterArray[i], currGuess.charAt(i), currKey);
		}

		stringUtil.reverseChars(currKey, reverseKey);

		SubstitutionCipher substitutionCipher = new SubstitutionCipher();
		String potentialDecrypt = substitutionCipher.encrypt(
				encryptedText, reverseKey);
		
		return potentialDecrypt;
	}
	
}
