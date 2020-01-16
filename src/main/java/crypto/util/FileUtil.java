package crypto.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/** Assorted methods for accessing files.
 *  Usually, this would not be explicitly coded, but a third party library,
 *  such as Apache Commons, would be used instead.
 *
 */
public class FileUtil
{
	private StringUtil stringUtil;
	
	public FileUtil() 
	{
		stringUtil = new StringUtil();
	}
	
	/** Return the contents of the text file as a list of lines.
	 * 
	 * @param f The text file to be read.
	 * @return A list of lines.
	 */
	public List<String> getLines(File f)
	{
		ArrayList<String> lines = new ArrayList<String>();
		
		try (FileInputStream fis = new FileInputStream(f);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
			)
		{
			if (f.exists())
			{	
				while (true)
				{
					String line = br.readLine();
					if (line == null)
						break;
					else
						lines.add(line);
				}
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return lines;
	}
	
	/** Get the list of lines from a resource file.
	 *  Generally, this file resides in src/main/resources, or src/test/resources.
	 * @param fname The file to be read.
	 * @return A list of lines.
	 */
	public List<String> getListFromResource(String fname) {
		String fullFname = getClass().getClassLoader().getResource(fname).getFile();
		return getLines(new File(fullFname));
		
	}
	
	/** Get the contents of a resource file as a single string.
	 * 
	 * @param fname The resource file to be read.
	 * @return The contents of the file as a single concatenated string.
	 */
	public String getStringFromResource(String fname)
	{
		return stringUtil.getStringFromList(getListFromResource(fname));
	}
	
}
