package crypto.decrypt;

import java.util.List;

import crypto.dto.Alphabet;
import crypto.encrypt.SubstitutionCipher;
import crypto.util.CombinationUtil;
import crypto.util.DictionaryUtil;

/** Attempt to decrypt text encrypted using the substitution cipher.
 *  There are a number of assumptions that have been made here.
 * <UL>
 * <LI>A single character substitution was used.
 * <LI>The plaintext primarily consists of English words.
 * <LI>There are spaces between each word.
 * <LI>Most of the words would appear in an English language dictionary.
 * </UL>
 * 
 * This code is of no practical use, and is meant as a demonstration of simple 
 * cryptographic techniques.
 *
 */
public class SubstitutionDecrypter extends BaseTextDecrypter implements DecryptText
{
	private String encryptedText;
	/** An ordered list of the most common characters in the encrypted text, in descending order.*/
	private List<Character> commonCharList;
	/** This contains the part of the decryption key that has been found so far.*/
	private Alphabet foundArr;
	/** Most common bigrams, in decreasing order.*/
	private List<String> bigramList;
	private List<Character>charList;
	private List<String> wordList;
	private List<String> twoWordList;
	private List<String> threeWordList;
	private List<String> fourWordList;
	private Alphabet bestKey;
		
	public SubstitutionDecrypter(String encryptedText)
	{
		super();
		this.encryptedText = encryptedText;
		commonCharList = stringUtil.getMostCommonCharacters(encryptedText, 10);		
		
		foundArr = new Alphabet();
		
		bestKey = new Alphabet();

		String lowerCaseText = encryptedText.toLowerCase();
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
	
	/** Execute decryption using a more efficient technique than brute force.
	 *  Find the 16 letters that can be searched for easily.  Then call the
	 *  more efficient decryption algorithm.
	 *  
	 * @return The decrypted plaintext.
	 */
	public String smartDecryptText()
	{
		findLetters();

		String decryptedText = executeSmarterBruteForceSearch(foundArr);
		
		return decryptedText;
	}

	/** Try to find the values of certain letters through analysis.
	 *  Each of these methods are dependent on the ones before them, 
	 *  so they should be called through this method.
	 *  
	 *  @return A character array of length 26 where the first letter is the value of a, the second the value for b, etc.
	 */
	public Alphabet findLetters()
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
	protected String executeSmarterBruteForceSearch(Alphabet knownLetters) {
		System.out.println("knownLetters is       " + knownLetters.getString());
		Alphabet unknownLetters = new Alphabet(knownLetters);
		List<Character> commonList = stringUtil.getMostCommonCharacters(encryptedText, 26);	
		unknownLetters.setChar('j', commonList.get(25));
		unknownLetters.setChar('q', commonList.get(24));
		unknownLetters.setChar('x', commonList.get(23));
		unknownLetters.setChar('z', commonList.get(22));
		
		String partialDecrypt = executeBruteForceSearch(unknownLetters);
		System.out.println("partial Decrypt is " + partialDecrypt);
		
		char c = bestKey.getChar('c');
		unknownLetters.setChar('c', c);
		
		c = bestKey.getChar('g');
		unknownLetters.setChar('g', c);
		
		c = bestKey.getChar('k');
		unknownLetters.setChar('k', c);
		
		c = bestKey.getChar('l');
		unknownLetters.setChar('l', c);
		
		c = bestKey.getChar('p');
		unknownLetters.setChar('p', c);
		
		c = bestKey.getChar('v');
		unknownLetters.setChar('v', c);
		
		unknownLetters.clearChar('j');
		unknownLetters.clearChar('q');
		unknownLetters.clearChar('x');
		unknownLetters.clearChar('z');
		
		return executeBruteForceSearch(unknownLetters);
	}
	
	/** Execute a brute force search to examine all possibilities of letters which are still unknown.
	 *  Since the brute force algorithm run time complexity is n!, where n is the number of unknown
	 *  letters, you need to verify manually that the unknown letter size is not too large.
	 *  
	 *  On my home computer, 10 unknown letter takes about 8.5 minutes to guess.
	 *  
	 *  @param knownLetters An array of the letters already known.
	 * @return The decrypted text.
	 */
	protected String executeBruteForceSearch(Alphabet knownLetters) {
		
		System.out.println("knownLetters size is " + knownLetters.size());
		
		char[] missingArray = knownLetters.getMissingLetters();
		System.out.println("missing letters are " + new String(missingArray));
		
		char[] missingArrayByPosition = knownLetters.getMissingLettersByPosition();
		System.out.println("missing letters by position are " + 
				new String(missingArrayByPosition));

		CombinationUtil combinationUtil = new CombinationUtil();
		List<String> combinationList = combinationUtil.getCombinations(missingArray);
		String decryptedText = "";
		int bestCount = 0;
		Alphabet currKey = new Alphabet(knownLetters);
		
		DictionaryUtil dictionaryUtil = new DictionaryUtil();
		
		Alphabet reverseKey = new Alphabet();
		int guessCount = 0;
		for (String currGuess : combinationList)
		{			
			if (guessCount % 500000 == 0)
			{
				System.out.println("Total guesses tested is " + guessCount);
				System.out.println("currGuess is " + currGuess + 
						" currKey is " + currKey.getString());
			}
			guessCount++;			
				
			String potentialDecrypt = decrypt(encryptedText, currGuess, 
					currKey, missingArrayByPosition, reverseKey);
			int currCount = dictionaryUtil.getWordCount(potentialDecrypt);
			
			if (currCount > bestCount)
			{
				bestCount = currCount;
				decryptedText = potentialDecrypt;
				bestKey = new Alphabet(currKey);
				System.out.println(decryptedText);
				System.out.println("Decrypting, current word count is " + bestCount);
				System.out.println("currKey is " + currKey.getString());
				System.out.println("reverseKey is " + reverseKey.getString());				
			}
		}
		
		return decryptedText;
	}

	/** Find the letter s.
	 *  Look for the word as.
	 */
	protected void findS() {
		char t = foundArr.getChar('t');
		
		for (String word: twoWordList)
		{
			char c = word.charAt(0);
			if (foundArr.isMatch(0, c))
			{
				char s = word.charAt(1);
				if ((s != t) && (s != foundArr.getChar('n')))
				{
					foundArr.setChar('s', s);
					System.out.println("s is " + s);
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
						
			if (foundArr.isMatch('o', c1) && foundArr.isMatch('t', c2))
			{
				foundArr.setChar('n', c0);
				System.out.println("n is " + c0);
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
			if (foundArr.isMatch('e', c))
			{
				Character b = word.charAt(0);
				if (commonCharList.contains(b))
				{
					System.out.println("Searching for b, found " + b);
					continue;
				}
				
				foundArr.setChar('b', b);
				System.out.println("b is " + b);
				break;
			}
		}		
	}

	/** Find the letter f.  The word of is very common, but f is relatively infrequent.*/
	protected void findF() {
		for (String word: twoWordList)
		{
			char c = word.charAt(0);
			if (foundArr.isMatch('o', c))
			{
				Character f = word.charAt(1);
				if (commonCharList.contains(f))
				{
					System.out.println("Searching for f, found " + f);
					continue;
				}
				
				foundArr.setChar('f', f);
				System.out.println("f is " + f);
				break;
			}
		}
		
	}

	/** Find the letter o by looking for the word to.*/
	protected void findO() 
	{
		for (String word: twoWordList)
		{
			char c = word.charAt(0);
			if (foundArr.isMatch('t',c))
			{
				Character o = word.charAt(1);
				foundArr.setChar('o', o);
				System.out.println("o is " + o);
				break;
			}
		}		
	}

	/** Find the letter m.
	 *  Look for the word them.
	 */
	protected void findM() {
		for (String word: fourWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char c2 = word.charAt(2);
			char m = word.charAt(3);

			// The word starts with "the".  
			// Other likely possibilities are the words "they" and "then".
			// The letters n and y are already known, so rule those out.
			if (foundArr.isMatch('t', c0) && 
					foundArr.isMatch('h', c1) &&
					foundArr.isMatch('e', c2) &&
					!foundArr.isMatch('y', m) && 
					!foundArr.isMatch('n', m))
			{
				foundArr.setChar('m', m);
				System.out.println("m is " + m);
				break;
			}
		}		
	}

	/** Find the letter u.
	 *  Look for the word but.
	 */
	protected void findU() {
		for (String word: threeWordList)
		{
			char c0 = word.charAt(0);
			char u = word.charAt(1);
			char c2 = word.charAt(2);

			if (foundArr.isMatch('b', c0) && foundArr.isMatch('t', c2))
			{
				foundArr.setChar('u', u);
				System.out.println("u is " + u);
				break;
			}
		}
	}

	/** Find the letter y.
	 *  Look for the word by.
	 */
	protected void findY() 
	{
		for (String word: twoWordList)
		{
			char b = word.charAt(0);
			if (foundArr.isMatch('b', b))
			{
				Character y = word.charAt(1);
				if (! commonCharList.contains(y))
				{
					foundArr.setChar('y', y);
					System.out.println("y is " + y);
					break;
				}
			}
		}		
	}
	
	/** Find the letter d.
	 *  Look for the word and.
	 */
	protected void findD() {
		for (String word: threeWordList)
		{
			char c0 = word.charAt(0);
			char c1 = word.charAt(1);
			char d = word.charAt(2);
			
			if (foundArr.isMatch('a', c0) && foundArr.isMatch('n', c1))
			{
				foundArr.setChar('d', d);
				System.out.println("d is " + d);
				break;
			}
		}
		
	}
	
	/** Look for the word with, and set w.*/
	protected void findW() {
		String th = bigramList.get(0);
		for (String word : fourWordList)
		{
			if (word.endsWith(th))
			{
				Character c = word.charAt(1);
				if (foundArr.isMatch('i', c))
				{
					Character w = word.charAt(0);
					foundArr.setChar('w', w);
					System.out.println("w is " + w);
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
			char r = word.charAt(1);
			char c2 = word.charAt(2);
			
			if (foundArr.isMatch('a', c0) && foundArr.isMatch('e', c2))
			{
				foundArr.setChar('r', r);
				System.out.println("r is " + r);
				break;
			}
		}
		
	}
	
	/** Set i by finding the word it.
	 *  Make sure it's not the word at.
	 */
	protected void findIt() {
		for (String word : twoWordList)
		{
			// Two letter word ending in t is either it or at.
			// Looking for it.
			char i = word.charAt(0);
			char c1 = word.charAt(1);
			if (!foundArr.isMatch('a', i) && foundArr.isMatch('t', c1))
			{
				System.out.println("Two letter word is " + word);
				foundArr.setChar('i', i);
				System.out.println("i from multi-letter word is " + i);
				break;
			}
		}
	}

	protected boolean findAAndI() {
		boolean foundA = false;
		boolean foundI = false;
		
		for (String word : wordList)
		{
			char c0 = word.charAt(0);
			
			if (!foundA && word.length() == 1)
			{
				foundArr.setChar('a', c0);
				foundA = true;
				System.out.println("a is " + c0);
			}			
			else if (foundA && word.length() == 1)
			{
				foundArr.setChar('i', c0);
				System.out.println("i is " + c0);
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
		
		char t = th.charAt(0);
		char h = th.charAt(1);
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
				foundArr.setChar('t', t);
				foundArr.setChar('h', h);
				Character e = word.charAt(2);
				match = false;
				for (int j=0; j<5; j++)
				{
					if (Character.compare(e, charList.get(j)) == 0)
						match = true;
				}
				if (!match)
					throw new RuntimeException("Did not find the at the top of the word list.");
				
				foundArr.setChar('e', e);
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
	 * @param currKey This contains all the known letters.  It is updated from currGuess.
	 * @param missingArr The position of the characters that are still to be determined.
	 * @param reverseKey The reversed key, i.e., the key that will decrypt using the substitution cipher.	
	 *  
	 * @return The decrypted text.
	 */
	protected String decrypt(String encryptedText, String currGuess, 
			Alphabet currKey, char[] missingArr, Alphabet reverseKey)
	{
		// For each of the unknown letters, put the current guess into it,
		// leaving the letters that are already known.
		for (int i=0; i< missingArr.length; i++)
		{
			currKey.setChar(missingArr[i], currGuess.charAt(i));
		}

		reverseKey.set(currKey.getReverseChars());

		SubstitutionCipher substitutionCipher = new SubstitutionCipher();
		String potentialDecrypt = substitutionCipher.encrypt(
				encryptedText, reverseKey.getCharArray());
		
		return potentialDecrypt;
	}
	
}
