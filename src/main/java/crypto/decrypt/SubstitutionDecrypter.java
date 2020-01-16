package crypto.decrypt;

import java.util.Arrays;
import java.util.List;

import crypto.util.FileUtil;

public class SubstitutionDecrypter extends BaseTextDecrypter implements DecryptText
{
	private FileUtil fileUtil = new FileUtil();
	
	public SubstitutionDecrypter()
	{
		super();
	}
	
	@Override
	public String decryptText(String encryptedText)
	{
		// Use frequency analysis of letters.
		// Use list of 80 most common words
		// Use most common bigrams.
		// Frequency of first letter of a word differs from overall frequency of letters.
		return null;
	}
	
	public List<String> getMostCommonCharacters() 
	{
		String[] sarr = fileUtil.getListFromResource("letters.txt").get(0).split(",");
		List<String> list = Arrays.asList(sarr);
		return list;
	}
	
	public List<String> getMostCommonWords() 
	{
		return fileUtil.getListFromResource("words.txt");
	}
	
	public List<String> getMostCommonBigrams() 
	{
		String[] sarr = fileUtil.getListFromResource("bigrams.txt").get(0).split(",");
		List<String> list = Arrays.asList(sarr);
		return list;
	}

}
