package crypto.decrypt;

import org.junit.Test;

import crypto.encrypt.RotationCipher;
import crypto.encrypt.SubstitutionCipher;
import crypto.util.PlainTextUtil;

public class SubstitutionDecrypterTest {
	@Test
	public void testSubstitutionDecryption()
	{
		PlainTextUtil plainTextUtil = new PlainTextUtil();
		
		String text = plainTextUtil.getLargePlainText();
		SubstitutionCipher cipher = new SubstitutionCipher();
		String encrypted = cipher.encrypt(text);
		System.out.println(text);
		System.out.println(encrypted);
		
		SubstitutionDecrypter decrypter = new SubstitutionDecrypter();
		String decryptedText = decrypter.decryptText(encrypted);
		
		
		System.out.println(decryptedText);
	}
}
