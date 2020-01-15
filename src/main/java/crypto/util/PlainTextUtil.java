package crypto.util;

public class PlainTextUtil
{
	private FileUtil fileUtil = new FileUtil();
	
	public String getSomePlainText()
	{
		return fileUtil.getStringFromResource("plaintext1.txt");
	}
	
	public String getMorePlainText() 
	{
		return fileUtil.getStringFromResource("plaintext2.txt");
	}
	
	public String getLargePlainText() 
	{
		return fileUtil.getStringFromResource("plaintext_long.txt");
	}
}
