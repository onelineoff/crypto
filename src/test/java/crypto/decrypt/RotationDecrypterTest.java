package davidweiss.crypto.decrypt;

import org.junit.Test;

import davidweiss.crypto.encrypt.RotationCipher;
import davidweiss.crypto.util.PlainTextUtil;

public class RotationDecrypterTest
{
	@Test
	public void testRotationDecryption()
	{
		String text = PlainTextUtil.getSomePlainText();
		RotationCipher cipher = new RotationCipher();
		String encrypted = cipher.encrypt(text);
		
		RotationDecrypter decrypter = new RotationDecrypter();
		String decryptedText = decrypter.decryptText(encrypted);
		
		System.out.println(text);
		System.out.println(encrypted);
		System.out.println(decryptedText);
	}
}
