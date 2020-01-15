package crypto.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{
	public static List<String> getLines(File f)
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
	
	public List<String> getListFromResource(String fname) {
		String fullFname = getClass().getClassLoader().getResource(fname).getFile();
		return getLines(new File(fullFname));
		
	}
	
	public String getStringFromResource(String fname)
	{
		return getStringFromList(getListFromResource(fname));
	}
	
	public String getStringFromList(List<String> list) 
	{
		StringBuffer sb = new StringBuffer();
		for (String s : list)
		{
			sb.append(s);
		}
		
		return sb.toString();
	}
}
