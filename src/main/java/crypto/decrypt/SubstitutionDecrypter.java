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
		List<Character> frequentCharList = stringUtil.getMostFrequentCharacters();
		List<String> frequentWordList = stringUtil.getMostFrequentWords();
		List<String> frequentBigramList = stringUtil.getMostFrequentBigrams();
		
		// Use frequency analysis of letters.
		// Use list of most common words
		// Use most common bigrams.
		// Frequency of first letter of a word differs from overall frequency of letters.
		
		return null;
	}
}
