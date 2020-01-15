package crypto.util;

import java.io.File;
import java.util.List;

import org.junit.Assert;

import org.junit.Test;

public class FileUtilTest
{
	@Test
	public void testReadFile()
	{
		
		File f = new File( DictionaryUtil.getDictionaryFileName());
		List<String> lines = FileUtil.getLines(f);
		Assert.assertNotNull(lines);
		Assert.assertTrue(lines.size() > 0);
		System.out.println("Found " + lines.size() + " lines");
	}
	
	@Test
	public void testGetAsResource() 
	{
		FileUtil fileUtil = new FileUtil();
		
		fileUtil.getListFromResource("letters.txt");
	}
}
