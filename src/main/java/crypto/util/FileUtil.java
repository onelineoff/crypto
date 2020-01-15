package davidweiss.crypto.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{
	public static List<String> getLines(File f)
	{
		ArrayList<String> lines = new ArrayList<String>();
		FileInputStream fis = null;
		try
		{
			if (f.exists())
			{
				fis = new FileInputStream(f);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				
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
		finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		
		return lines;
	}
}
