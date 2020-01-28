package crypto.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil
{
	private static final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
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
	
	public String[] getWords(String text)
	{
		String[] words = text.split("\\W");
		return words;
	}
	
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
	
	public int getNonEmptyWordCount(String text)
	{
		String[] wordArr = getWords(text);
		
		int count = 0;
		for (String word : wordArr)
		{
			if (! isEmpty(word))
				count++;
		}
		
		return count;
	}
	
	public boolean isEmpty(String str)
	{
		boolean flag = true;
		
		if (str == null)
			flag = true;
		else if (str.length() == 0)
			flag = true;
		else
		{
			str = str.trim();
			flag = str.length() == 0;
		}
		return flag;
	}
	
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
	
	public List<String> getMostFrequentWords() 
	{
		return fileUtil.getListFromResource("words.txt");
	}
	
	public List<String> getMostFrequentBigrams() 
	{
		String[] sarr = fileUtil.getListFromResource("bigrams.txt").get(0).split(",");
		List<String> list = Arrays.asList(sarr);
		return list;
	}

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
			if (isLowerCase(c))
			{
				int count = charCountMap.get(c);
				count++;
				charCountMap.put(c, count);
			}
		}
		
		Map<Character, Integer> sortedMap = sortByValueDescending(charCountMap);
		Set<Character> charSet = sortedMap.keySet();
		Iterator<Character> it = charSet.iterator();
		for (int i=0; i<total; i++) 
		{
			list.add(it.next());
		}
		return list;
		
	}
	
	/** Sort the map by the value, in descending order.
	 * 
	 * @param inputMap The map to be sorted
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
	
	public boolean isLowerCase(Character c)
	{
		return (c >= 'a' && c<= 'z');
	}
	
	public boolean isUpperCase(Character c)
	{
		return (c >= 'A' && c<= 'Z');
	}
	
	public List<String> getMostCommonWords(String str, int length, int total) {
		String[] wordArr = getWords(str, length);
		return getMostCommonWords(str, wordArr, total);
	}
	
	public List<String> getMostCommonWords(String str, int total)
	{
		String[] wordArr = getWords(str);
		return getMostCommonWords(str, wordArr, total);
	}
	
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
		Iterator<String> it = wordSet.iterator();
		for (int i=0; i<total; i++) 
		{	
			if (it.hasNext())
			{
				String word = it.next();
				while (isEmpty(word))
					word = it.next();	

				list.add(word);
			}
			else
				break;
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
		Iterator<String> it = bigramSet.iterator();
		for (int i=0; i<total; i++) 
		{
			String bigram = it.next();	
			list.add(bigram);
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
		Iterator<Character> it = charSet.iterator();
		for (int i=0; i<total; i++) 
		{
			commonList.add(it.next());
		}
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
}
