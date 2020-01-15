package crypto.decrypt;

import org.junit.Test;

import crypto.encrypt.RotationCipher;
import crypto.util.PlainTextUtil;

public class RotationDecrypterTest
{
	private PlainTextUtil plainTextUtil = new PlainTextUtil();
	
	@Test
	public void testRotationDecryption()
	{
		String text = plainTextUtil.getSomePlainText();
		RotationCipher cipher = new RotationCipher();
		String encrypted = cipher.encrypt(text);
		
		RotationDecrypter decrypter = new RotationDecrypter();
		String decryptedText = decrypter.decryptText(encrypted);
		
		System.out.println(text);
		System.out.println(encrypted);
		System.out.println(decryptedText);
	}
}
