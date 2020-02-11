package crypto.util;

/** Utility class for getting plain text samples.
 *  The text is read from a resource file.  Modify the contents
 *  of those files to get different text.
 *
 */
public class PlainTextUtil
{
	private FileUtil fileUtil;
	private StringUtil stringUtil;
	
	public PlainTextUtil()
	{
		fileUtil = new FileUtil();
		stringUtil = new StringUtil();
	}
	
	/** Return some plain text.
	 * 
	 * @return Some plain text.
	 */
	public String getSomePlainText()
	{
		return stringUtil.getStringFromList(fileUtil.getListFromResource("plaintext1.txt"));
	}
	
	/** Return a different sample of plain text.
	 * 
	 * @return Some plain text.
	 */
	public String getMorePlainText() 
	{
		return stringUtil.getStringFromList(fileUtil.getListFromResource("plaintext2.txt"));
	}
	
	/** Return a larger sample of plain text.
	 * 
	 * @return Some plain text.
	 */
	public String getLargePlainText() 
	{
		return stringUtil.getStringFromList(fileUtil.getListFromResource("plaintext_long.txt"));
	}
}
