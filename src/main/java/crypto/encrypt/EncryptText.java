package crypto.encrypt;

/** Classes which implement this interface should convert plain text to an encrypted String representation. */
public interface EncryptText 
{
	public String encrypt(String plainText);
}
