package crypto.decrypt;

import org.junit.Test;

import crypto.dto.Alphabet;
import crypto.encrypt.SubstitutionCipher;
import crypto.util.PlainTextUtil;

public class SubstitutionDecrypterTest {
	@Test
	public void testSubstitutionDecryption()
	{
		long t1 = System.currentTimeMillis();
		PlainTextUtil plainTextUtil = new PlainTextUtil();
		
		String text = plainTextUtil.getLargePlainText();
		SubstitutionCipher cipher = new SubstitutionCipher();
		String encrypted = cipher.encrypt(text);
		System.out.println(text);
		System.out.println(encrypted);
		
		SubstitutionDecrypter decrypter = new SubstitutionDecrypter(encrypted);
		String decryptedText = decrypter.decryptText();
		
		
		System.out.println(decryptedText);
		long t2 = System.currentTimeMillis();
		long seconds = (t2 - t1) / 1000;
		long minutes = seconds / 60;
		System.out.println("Total decryption time for substitution cipher is " + 
		minutes + ":" + (seconds % 60));
	}

	@Test
	public void testSmarterBruteForceSearch()
	{
		long t1 = System.currentTimeMillis();
		PlainTextUtil plainTextUtil = new PlainTextUtil();
		
		String text = plainTextUtil.getLargePlainText();
		SubstitutionCipher cipher = new SubstitutionCipher();
		String encrypted = cipher.encrypt(text);
		System.out.println(text);
		System.out.println(encrypted);
		
		SubstitutionDecrypter decrypter = new SubstitutionDecrypter(encrypted);
		String decryptedText = decrypter.smartDecryptText();
		
		
		System.out.println(decryptedText);
		long t2 = System.currentTimeMillis();
		System.out.println("Smart decryption time for substitution cipher is " + 
		(t2 - t1) + " msec");
	}
	
	@Test
	public void testFindLetters()
	{
		PlainTextUtil plainTextUtil = new PlainTextUtil();
		
		String text = plainTextUtil.getLargePlainText();
		SubstitutionCipher cipher = new SubstitutionCipher();
		String encrypted = cipher.encrypt(text);
		System.out.println(text);
		System.out.println(encrypted);
		
		SubstitutionDecrypter decrypter = new SubstitutionDecrypter(encrypted);
		Alphabet key = decrypter.findLetters();
		System.out.println(key.getString());
	}
}
