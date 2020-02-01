package crypto.decrypt;

import org.junit.Test;

import crypto.encrypt.RotationCipher;
import crypto.util.PlainTextUtil;

public class RotationDecrypterTest
{	
	@Test
	public void testRotationDecryption()
	{
		PlainTextUtil plainTextUtil = new PlainTextUtil();
		
		String text = plainTextUtil.getSomePlainText();
		RotationCipher cipher = new RotationCipher();
		String encrypted = cipher.encrypt(text);
		
		RotationDecrypter decrypter = new RotationDecrypter(encrypted);
		String decryptedText = decrypter.decryptText();
		
		System.out.println(text);
		System.out.println(encrypted);
		System.out.println(decryptedText);
	}
	
	@Test
	public void testKidsMessage()
	{

		String text = "Your message, should you choose to accept it, is to decrypt this text";
		RotationCipher cipher = new RotationCipher();
		String encrypted = cipher.encrypt(text);
		
		RotationDecrypter decrypter = new RotationDecrypter(encrypted);
		String decryptedText = decrypter.decryptText();
		
		System.out.println(text);
		System.out.println(encrypted);
		System.out.println(decryptedText);
	}
}
