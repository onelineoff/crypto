package crypto.util;

/** Utility class for getting plain text samples.
 *  The text is read from a resource file.  Modify the contents
 *  of those files to get different text.
 *
 */
public class PlainTextUtil
{
	private FileUtil fileUtil = new FileUtil();
	
	/** Return some plain text.
	 * 
	 * @return Some plain text.
	 */
	public String getSomePlainText()
	{
		return fileUtil.getStringFromResource("plaintext1.txt");
	}
	
	/** Return a different sample of plain text.
	 * 
	 * @return Some plain text.
	 */
	public String getMorePlainText() 
	{
		return fileUtil.getStringFromResource("plaintext2.txt");
	}
	
	/** Return a larger sample of plain text.
	 * 
	 * @return Some plain text.
	 */
	public String getLargePlainText() 
	{
		return fileUtil.getStringFromResource("plaintext_long.txt");
	}
}
