package crypto.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Utility methods specifically to support encryption and decryption of text.
 */
public class StringUtil
{
	private static final String lowercaseString = "abcdefghijklmnopqrstuvwxyz";
	private static final char[] lowercase = lowercaseString.toCharArray();
	
	private static final String uppercaseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final char[] uppercase = uppercaseString.toCharArray();
	
	private FileUtil fileUtil;
	
	public StringUtil()
	{
		fileUtil = new FileUtil();
	}
	
	public char[] getLowerCaseLetters()
	{
		return lowercase;
	}
	
	public char[] getUpperCaseLetters()
	{
		return uppercase;
	}
	
	public String getLowerCaseString()
	{
		return lowercaseString;
	}
	
	public char[] getUpperCaseArray(char[] input)
	{
		return new String(input).toUpperCase().toCharArray();
	}
	
	/** Convert a list of strings to a single string.
	 *  This is done by concatenating all the strings together in order, without the eol.
	 * @param list The list of strings.
	 * @return A single concatenated string.
	 */
	public String getStringFromList(List<String> list) 
	{
		StringBuilder sb = new StringBuilder();
		for (String s : list)
		{
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	/** Convert the text into an array of words.
	 *  Words consist of one or more alphanumeric characters.
	 *  If there are multiple non-word characters between two words,
	 *  such as a period followed by two spaces, the words before and
	 *  after these three non-word characters are returned, but the 
	 *  period and two spaces are thrown out.
	 * @param text The input text
	 * @return An array of words.
	 */
	public String[] getWords(String text)
	{
		String[] words = text.split("\\W+");
		return words;
	}
	
	/** Get the words of a specified length.
	 * @param text The input text
	 * @param length The length of the words to be returned.
	 * @return All words in the input text of the specified length.
	 */
	public String[] getWords(String text, int length)
	{
		String[] arr = getWords(text);
		List<String> list = new ArrayList<>();
		for (String word : arr) 
		{
			if (word.length() == length)
			{
				list.add(word);
			}
		}
		
		String[] retArr = new String[list.size()];
		list.toArray(retArr);
		return retArr;
	}
	
	/** Return the number of words in the text.
	 * 
	 * @param text The text to be examined.
	 * @return The number of words.
	 */
	public int getWordCount(String text)
	{
		return getWords(text).length;		
	}
	
	/** Return a list of the most frequent characters, in descending order.
	 *  These are the most frequent characters in the English language.
	 *  @see <a href="https://www.rd.com/culture/common-letters-english-language/">Most frequent letters</a>
	 * 
	 * @return An ordered list of the most frequent characters, in descending order.
	 */
	public List<Character> getMostFrequentCharacters() 
	{
		String[] sarr = fileUtil.getListFromResource("letters.txt").get(0).split(",");
		List<Character> list = new ArrayList<>();
		for (String s : sarr)
		{
			list.add(s.charAt(0));
		}
		
		return list;
	}
	
	/** The most frequent words in English, in descending order.
	 *  @see <a href="https://www.rypeapp.com/most-common-english-words/">Most common English words</a>
	 * @return An ordered list of the most frequent words in English, in descending order.
	 */
	public List<String> getMostFrequentWords() 
	{
		return fileUtil.getListFromResource("words.txt");
	}
	
	/** The most frequent two letter combinations in the English language.
	 *  Letters such as th, he, in, and er tend to occur together much
	 *  more frequently than would be expected if the occurrence of the bigrams
	 *  was the same as the popularity of letter 1 multipled by that of letter 2.
	 *  In fact, e, a, and r are the three most popular letters in English, but
	 *  bigrams with two of these letters are only 4th, 6th, and 20th most common.
	 *  @see <a href="https://blogs.sas.com/content/iml/2014/09/26/bigrams.html">Bigrams</a>
	 * @return The list of bigrams, in decreasing order.
	 */
	public List<String> getMostFrequentBigrams() 
	{
		String[] sarr = fileUtil.getListFromResource("bigrams.txt").get(0).split(",");
		List<String> list = Arrays.asList(sarr);
		return list;
	}

	/** Find the most common characters in the given text.
	 * The text is converted to all lower case before it's analyzed.
	 * 
	 * @param str The string to be analyzed
	 * @param total The number of results to be returned.
	 * @return A list of the most common characters, in descending order.
	 */
	public List<Character> getMostCommonCharacters(String str, int total) 
	{
		List<Character> list = new ArrayList<>();
		Map<Character, Integer> charCountMap = new HashMap<>();
		
		// Initialize character counts to 0.
		for (Character c : lowercase)
		{
			charCountMap.put(c, 0);
		}
		
		str = str.toLowerCase();
		for (int i=0; i<str.length(); i++)
		{
			Character c = str.charAt(i);
			// The character is either a lower case letter, or a non-word character.
			if (isLowerCase(c))
			{
				int count = charCountMap.get(c);
				count++;
				charCountMap.put(c, count);
			}
		}
		
		Map<Character, Integer> sortedMap = sortByValueDescending(charCountMap);
		Set<Character> charSet = sortedMap.keySet();
		list.addAll(charSet);
		if (list.size() > total)
			list = list.subList(0, total);

		return list;
		
	}
	
	/** Sort the map by the value, in descending order.
	 *  This method used both generics, and functional programming techniques.
	 *  Note that maps with both characters and string as their keys call this.
	 * 
	 * @param inputMap The map to be sorted
	 * @param <K> The key type, either String or Character.
	 * @return The sorted map
	 */
	public <K> Map<K, Integer> sortByValueDescending(Map<K, Integer> inputMap)
	{
		LinkedHashMap<K, Integer> sortedMap = new LinkedHashMap<>();
		
		inputMap.entrySet()
	    .stream()
	    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
	    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		
		return sortedMap;
	}
	
	/** Return true if the character is a lower case letter.
	 *  This works because of the way Java defines characters.
	 *  See the ASCII and Unicode standards for more details.
	 *  
	 * @param c The character to be tested.
	 * @return true if the character is a lower case letter.
	 */
	public boolean isLowerCase(Character c)
	{
		if (c == null)
			return false;
		
		return (c >= 'a' && c<= 'z');
	}
	
	/** Return true if the character is an upper case letter.
	 *  This works because of the way Java defines characters.
	 *  See the ASCII and Unicode standards for more details.
	 *  
	 * @param c The character to be tested.
	 * @return true if the character is an upper case letter.
	 */
	public boolean isUpperCase(Character c)
	{
		if (c == null)
			return false;
		
		return (c >= 'A' && c<= 'Z');
	}
	
	/** Get the most common words in the text of a given length.
	 * 
	 * @param str The text to be analyzed
	 * @param length The length of words to be used.
	 * @param total The number of words to be returned.
	 * @return An ordered list in descending order with the results.
	 */
	public List<String> getMostCommonWords(String str, int length, int total) {
		String[] wordArr = getWords(str, length);
		return getMostCommonWords(str, wordArr, total);
	}
	
	/** Get the most common words in the text of a given length.
	 * 
	 * @param str The text to be analyzed
	 * @param total The total number of words to be returned.
	 * @return An ordered list in descending order with the results.
	 */
	public List<String> getMostCommonWords(String str, int total)
	{
		String[] wordArr = getWords(str);
		return getMostCommonWords(str, wordArr, total);
	}
	
	/** Return the most common words in the text that are in the specified list.
	 * 
	 * @param str The text to be analyzed
	 * @param wordArr The words that are to be searched for in the text.
	 * @param total The total number of words to be returned.
	 * @return An ordered list in descending order from the array of words passed in.
	 */
	public List<String> getMostCommonWords(String str, String[] wordArr, int total)
	{
		str = str.toLowerCase();
		Map<String, Integer> wordCount = new HashMap<>();
		for (String word : wordArr)
		{
			Integer count = wordCount.get(word);
			if (count == null)
			{
				count = 0;
			}
			count++;
			wordCount.put(word, count);
		}
		
		Map<String, Integer> sortedMap = sortByValueDescending(wordCount);
		
		List<String> list = new ArrayList<>();
		Set<String> wordSet = sortedMap.keySet();
		list.addAll(wordSet);
		
		if (list.size() > total)
		{
			list = list.subList(0, total);
		}

		return list;
	}
	
	/** Get the most common bigrams in the input text.
	 * 
	 * @param str The text to be analyzed.
	 * @param total The number of bigrams to return.
	 * @return An ordered list of the most common bigrams in the text.
	 */
	public List<String> getMostCommonBigrams(String str, int total)
	{
		str = str.toLowerCase();
		String[] wordArr = getWords(str);
		Map<String, Integer> bigramCount = new HashMap<>();
		
		for (String word : wordArr)
		{
			int length = word.length();
			if (length < 2)
				continue;
			
			int last = length - 2;
			
			for (int i=0; i<= last; i++)
			{
				String bigram = word.substring(i, i+2);
				Integer count = bigramCount.get(bigram);
				if (count == null)
					count = 0;
				count++;
				bigramCount.put(bigram, count);
			}
		}
		
		List<String> list = new ArrayList<>();
		Map<String, Integer> sortedMap = sortByValueDescending(bigramCount);
		
		Set<String> bigramSet = sortedMap.keySet();
		list.addAll(bigramSet);
		if (list.size() > total)
		{
			list = list.subList(0, total);
		}
		
		return list;
	}
	
	public List<Character> getMostLikelyFirstCharacters()
	{
		String[] sarr = fileUtil.getListFromResource("first_letter_more.txt").get(0).split(",");
		List<Character> list = new ArrayList<>();
		for (String s : sarr)
		{
			list.add(s.charAt(0));
		}
		
		return list;
	}
	
	public List<Character> getLeastLikelyFirstCharacters()
	{
		String[] sarr = fileUtil.getListFromResource("first_letter_less.txt").get(0).split(",");
		List<Character> list = new ArrayList<>();
		for (String s : sarr)
		{
			list.add(s.charAt(0));
		}
		
		return list;
	}
	
	/** Find letters that occur more often as the first letter of a word than in general.
	 *  In English, some letters are more likely to be the first letter of a word than
	 *  their occurrence in the text in general.
	 * @param str The text to be analyzed.
	 * @param total The number of entries to be returned.
	 * @return An ordered list of the characters more likely to be the first letter of a word.
	 */
	public List<Character> getMostCommonFirstCharacters(String str, int total)
	{
		str = str.toLowerCase();
		String[] wordArr = getWords(str);
		StringBuilder sb = new StringBuilder();
		for (String word : wordArr)
		{
			if (word.length() == 0)
				continue;
			
			sb.append(word.charAt(0));
		}
		
		String firstLetters = sb.toString();
		
		Map<Character, Integer> ratioMap = new HashMap<>();
		
		for (char c : lowercase)
		{
			float percent = getPercentOfCharacterInString(c, str);
			float firstPercent = getPercentOfCharacterInString(c, firstLetters);
			float ratio = 0.0f;
			if (percent > 0.01f) 
				ratio = firstPercent / percent;
			
			Integer val = Math.round(ratio * 100);
			ratioMap.put(c, val);
		}
		
		Map<Character,Integer> sortedMap = sortByValueDescending(ratioMap);
		List<Character> commonList = new ArrayList<>();
		Set<Character> charSet = sortedMap.keySet();
		commonList.addAll(charSet);
		
		return commonList;
	}
	
	/** Find letters that occur less often as the first letter of a word than in general.
	 *  In English, some letters are less likely to be the first letter of a word than
	 *  their occurrence in the text in general.
	 * @param str The text to be analyzed.
	 * @param total The number of entries to be returned.
	 * @return An ordered list of the characters less likely to be the first letter of a word.
	 */
	public List<Character> getLeastCommonFirstCharacters(String str, int total)
	{
		List<Character> list = getMostCommonFirstCharacters(str, 26);
		List<Character> leastCommonList = new ArrayList<>();
		for (int i=25; i>25 - total; i--)
			leastCommonList.add(list.get(i));
		
		return leastCommonList;
	}
	
	public float getPercentOfCharacterInString(char c, String str)
	{
		int count = 0;
		for (int i=0; i<str.length(); i++)
		{
			if (Character.compare(c, str.charAt(i)) == 0)
			{
				count++;
			}
		}
		
		return (float) count / (float) str.length();
	}
	
	public char getChar(char c, char[] arrMap)
	{
		return arrMap[c - 'a'];
	}
	
	public void setChar(char positionChar, char insertChar, char[] arrMap)
	{
			setChar(positionChar - 'a', insertChar, arrMap);
	}
	
	public void clearChar(char positionChar, char[] arrMap)
	{
		arrMap[positionChar - 'a'] = '-';
	}
	
	public void setChar(char positionChar, int insertChar, char[] arrMap)
	{
		setChar(positionChar - 'a', lowercase[insertChar], arrMap);
	}
	public void setChar(int index, char insertChar, char[] arrMap)
	{
		if (Character.isLowerCase(insertChar))
			arrMap[index] = insertChar;
	}
	
	/** Return an array with the inverse mapping.
	 *  The input array is a mapping from plain text to substituted text.
	 *  So, for example, for the mapping a to g, b to d, and c to l,
	 *  in the return array, the mapping would be g to a, d to b, l to c
	 * @param charArr The mapping array, where charArr[0] is a, charArr[1] is b, etc.
	 * @param reverseArr The array which maps from the substituted text to plain text.
	 */
	public void reverseChars(char[] charArr, char[] reverseArr)
	{
		int index = 0;
		for (char c : charArr)
		{
			setChar(c,  index, reverseArr);
			index++;
		}
	}
}
