package crypto.decrypt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import crypto.encrypt.SubstitutionCipher;
import crypto.util.CombinationUtil;
import crypto.util.DictionaryUtil;

public class SubstitutionDecrypter extends BaseTextDecrypter implements DecryptText
{
	/** These are the letters not guessed from text analysis.*/
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
	private char[] bestKey;
	
	public SubstitutionDecrypter(String encryptedText)
	{
		super();
		this.encryptedText = encryptedText;
		commonCharList = stringUtil.getMostCommonCharacters(encryptedText, 10);		
		
		// The character to substitute is at each position, e.g, if foundArr[3] == 'g', then g is 
		// substituted for d to decrypt.  This is meant for definite matches.
		foundArr = new char[26];
		for (int i=0; i<26; i++)
			foundArr[i]= '-';
		reverseKey = new char[26];
		bestKey = new char[26];

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
		findLetters();

		String decryptedText = executeBruteForceSearch(foundArr);
		
		return decryptedText;
	}
	
	public String smartDecryptText()
	{
		findLetters();

		String decryptedText = executeSmarterBruteForceSearch(foundArr);
		
		return decryptedText;
	}

	/** Try to find the values of certain letters through analysis.
	 *  Each of these methods are dependent on the ones before them, 
	 *  so they should be called through this method.
	 *  @return A character array of length 26 where the first is the value of a, the second the value for b, etc.
	 */
	public char[] findLetters()
	{
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
		
		return foundArr;
	}
	
	/** Execute a faster brute force search.
	 * 
	 *  The idea is based on the fact that brute force search is proportional to
	 *  n!.  So, break up the 10 unknown letters into two groups, the more common
	 *  of these are c, g, k, l, p, and v.  The least common are j, q, x, and z.
	 *  10! is about 3.6 million, while 6! + 4! is 720 + 24.  Run two brute force tests,
	 *  and this should complete over 5000 times faster.
	 *   
	 * @param knownLetters An array which contains the known letters.
	 * @return The decrypted string.
	 */
	protected String executeSmarterBruteForceSearch(char[] knownLetters) {
		System.out.println("knownLetters is       " + new String(knownLetters));
		char[] unknownLetterArray = Arrays.copyOf(knownLetters, 26);
		List<Character> commonList = stringUtil.getMostCommonCharacters(encryptedText, 26);	
		stringUtil.setChar('j', commonList.get(25), unknownLetterArray);
		stringUtil.setChar('q', commonList.get(24), unknownLetterArray);
		stringUtil.setChar('x', commonList.get(23), unknownLetterArray);
		stringUtil.setChar('z', commonList.get(22), unknownLetterArray);
		System.out.println("unknownLetterArray is " + new String(unknownLetterArray));
		
		String partialDecrypt = executeBruteForceSearch(unknownLetterArray);
		System.out.println("partial Decrypt is " + partialDecrypt);
		
		char c = bestKey['c' - 'a'];
		stringUtil.setChar('c', c, unknownLetterArray);
		
		c = bestKey['g' - 'a'];
		stringUtil.setChar('g', c, unknownLetterArray);
		
		c = bestKey['k' - 'a'];
		stringUtil.setChar('k', c, unknownLetterArray);
		
		c = bestKey['l' - 'a'];
		stringUtil.setChar('l', c, unknownLetterArray);
		
		c = bestKey['p' - 'a'];
		stringUtil.setChar('p', c, unknownLetterArray);
		
		c = bestKey['v' - 'a'];
		stringUtil.setChar('v', c, unknownLetterArray);
		
		stringUtil.clearChar('j', unknownLetterArray);
		stringUtil.clearChar('q', unknownLetterArray);
		stringUtil.clearChar('x', unknownLetterArray);
		stringUtil.clearChar('z', unknownLetterArray);
		
		return executeBruteForceSearch(unknownLetterArray);
	}
	
	/** Execute a brute force search to examine all possibilities of letters which are still unknown.
	 *  Since the brute force algorithm run time complexity is n!, where n is the number of unknown
	 *  letters, you need to verify manually that the unknown letter size is not too large.
	 *  
	 *  On my home computer, 10 unknown letter takes about 8.5 minutes to guess.
	 *  
	 * @return The decrypted text.
	 */
	protected String executeBruteForceSearch(char[] knownLetters) {
		
		System.out.println("knownLetters size is " + knownLetters.length);
		System.out.println(new String(knownLetters));
		
		// First, put all missing letters into the list.
		List<Character> missingLetters = new ArrayList<>();
		char[] lowercase = stringUtil.getLowerCaseLetters();
		for (Character c : lowercase)
		{
			missingLetters.add(c);
		}
		
		// Remove all letters already found, so that missing Letters contains all unknown letters.
		// The letters being removed are the encrypted ones, not the plaintext ones.
		for (Character c : knownLetters)
		{
			if (stringUtil.isLowerCase(c))
			{
				missingLetters.remove(c);
			}
		}
		
		System.out.println("missingLetters size is " + missingLetters.size());
		Character[] missingArr = new Character[missingLetters.size()];
		missingLetters.toArray(missingArr);
		CombinationUtil combinationUtil = new CombinationUtil();
		List<String> combinationList = combinationUtil.getCombinations(missingArr);
		String decryptedText = "";
		int bestCount = 0;
		char[] currKey = new char[26];
		for (int i=0; i<26; i++)
			currKey[i] = '-';
		
		// The known letters only need to be set once.
		// Insert the remaining guesses into this array later on.
		stringUtil.setChar('a', stringUtil.getChar('a', knownLetters), currKey);
		stringUtil.setChar('b', stringUtil.getChar('b', knownLetters), currKey);
		stringUtil.setChar('c', stringUtil.getChar('c', knownLetters), currKey);
		stringUtil.setChar('d', stringUtil.getChar('d', knownLetters), currKey);
		stringUtil.setChar('e', stringUtil.getChar('e', knownLetters), currKey);
		stringUtil.setChar('f', stringUtil.getChar('f', knownLetters), currKey);
		stringUtil.setChar('g', stringUtil.getChar('g', knownLetters), currKey);
		stringUtil.setChar('h', stringUtil.getChar('h', knownLetters), currKey);
		stringUtil.setChar('i', stringUtil.getChar('i', knownLetters), currKey);
		stringUtil.setChar('j', stringUtil.getChar('j', knownLetters), currKey);
		stringUtil.setChar('k', stringUtil.getChar('k', knownLetters), currKey);
		stringUtil.setChar('l', stringUtil.getChar('l', knownLetters), currKey);
		stringUtil.setChar('m', stringUtil.getChar('m', knownLetters), currKey);
		stringUtil.setChar('n', stringUtil.getChar('n', knownLetters), currKey);
		stringUtil.setChar('o', stringUtil.getChar('o', knownLetters), currKey);
		stringUtil.setChar('p', stringUtil.getChar('p', knownLetters), currKey);
		stringUtil.setChar('q', stringUtil.getChar('q', knownLetters), currKey);
		stringUtil.setChar('r', stringUtil.getChar('r', knownLetters), currKey);
		stringUtil.setChar('s', stringUtil.getChar('s', knownLetters), currKey);
		stringUtil.setChar('t', stringUtil.getChar('t', knownLetters), currKey);
		stringUtil.setChar('u', stringUtil.getChar('u', knownLetters), currKey);
		stringUtil.setChar('v', stringUtil.getChar('v', knownLetters), currKey);
		stringUtil.setChar('w', stringUtil.getChar('w', knownLetters), currKey);
		stringUtil.setChar('x', stringUtil.getChar('x', knownLetters), currKey);
		stringUtil.setChar('y', stringUtil.getChar('y', knownLetters), currKey);
		stringUtil.setChar('z', stringUtil.getChar('z', knownLetters), currKey);
		System.out.println("currKey is " + new String(currKey));
		
		DictionaryUtil dictionaryUtil = new DictionaryUtil();
		Character[] missingPlainTextCharArr = new Character[missingArr.length];
		char[] lowercaseArr = stringUtil.getLowerCaseLetters();
		int index = 0;
		for (int i=0; i<26; i++)
		{
			if (!stringUtil.isLowerCase(currKey[i]))
			{
				missingPlainTextCharArr[index] = lowercaseArr[i];
				index++;
			}
		}
		
		int guessCount = 0;
		for (String currGuess : combinationList)
		{			
			if (guessCount % 500000 == 0)
			{
				System.out.println("Total guesses tested is " + guessCount);
				System.out.println("currGuess is " + currGuess + " currKey is " + new String(currKey));
			}
			guessCount++;			
				
			String potentialDecrypt = decrypt(encryptedText, currGuess, 
					currKey, missingPlainTextCharArr);
			int currCount = dictionaryUtil.getWordCount(potentialDecrypt);
			if (currCount > bestCount)
			{
				bestCount = currCount;
				decryptedText = potentialDecrypt;
				bestKey = Arrays.copyOf(currKey, 26);
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
	 * That is, if a to d, b to y, and c to m, then the reverseKey would be
	 * d to a, y to b, and m to c. 
	 * 
	 * @param encryptedText The encrypted text.
	 * @param currGuess This is a guess for the letters that are not yet known.
	 * @param currKey This contains all the known letters.  It is updated from currGuess.	 * 
	 * @return The decrypted text.
	 */
	protected String decrypt(String encryptedText, String currGuess, 
			char[] currKey, Character[] missingArr)
	{
		// For each of the unknown letters, put the current guess into it,
		// leaving the letters that are already known.
		for (int i=0; i< missingArr.length; i++)
		{
			stringUtil.setChar(missingArr[i], currGuess.charAt(i), currKey);
		}

		stringUtil.reverseChars(currKey, reverseKey);

		SubstitutionCipher substitutionCipher = new SubstitutionCipher();
		String potentialDecrypt = substitutionCipher.encrypt(
				encryptedText, reverseKey);
		
		return potentialDecrypt;
	}
	
}
