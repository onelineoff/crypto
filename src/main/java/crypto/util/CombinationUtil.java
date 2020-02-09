package crypto.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinationUtil {

	public List<String> getCombinations(char[] arr)
	{
		Character[] charArr = new Character[arr.length];
		for (int i=0; i< arr.length; i++)
		{
			charArr[i] = arr[i];
		}
			
		return getCombinations(charArr);
	}
	
	public List<String> getCombinations(Character[] arr) 
	{
		List<String> list = new ArrayList<>();
		if (arr.length < 4)
		{
			System.out.println("Error, length < 4 not supported");
			
		}
		else
		{
			int len = arr.length;
			for (int i=0; i<len; i++)
			{
				char firstChar = arr[i];
				char[] newArr = new char[len - 1];
				for (int j=0, insert=0; j<len; j++)
				{
					if (i != j)
					{
						newArr[insert] = arr[j];
						insert++;
					}
				}
				
				List<String> substrings = getCombinations(newArr, firstChar);
				list.addAll(substrings);
			}
		}
		
		return list;
	}
	
	public List<String> getCombinations(Character[] arr, int len)
	{		
		List<String> list = getCombinations(arr);
		if (arr.length == len)
			return list;

		Set<String> comboSet = new HashSet<>();

		for (String str : list) 
		{
			if (str.length() > len)
			{
				str = str.substring(0, len);
				comboSet.add(str);
			}
		}
		
		List<String> retList = new ArrayList<>();
		retList.addAll(comboSet);
		
		return retList;		
	}

	protected List<String> getCombinations(char[] arr, char leadChar) 
	{
		List<String> list = new ArrayList<>();
		if (arr.length == 3)
		{
			char[] newArr = new char[4];
			newArr[0] = leadChar;
			newArr[1] = arr[0];
			newArr[2] = arr[1];
			newArr[3] = arr[2];
			list.add(new String(newArr));
			
			newArr[1] = arr[0];
			newArr[2] = arr[2];
			newArr[3] = arr[1];
			list.add(new String(newArr));
			
			newArr[1] = arr[1];
			newArr[2] = arr[0];
			newArr[3] = arr[2];
			list.add(new String(newArr));
			
			newArr[1] = arr[1];
			newArr[2] = arr[2];
			newArr[3] = arr[0];
			list.add(new String(newArr));
			
			newArr[1] = arr[2];
			newArr[2] = arr[0];
			newArr[3] = arr[1];
			list.add(new String(newArr));
			
			newArr[1] = arr[2];
			newArr[2] = arr[1];
			newArr[3] = arr[0];
			list.add(new String(newArr));
		}
		else 
		{
			int len = arr.length;
			for (int i=0; i<len; i++)
			{
				char firstChar = arr[i];
				char[] newArr = new char[len - 1];
				for (int j=0, insert=0; j<len; j++)
				{
					if (i != j)
					{
						newArr[insert] = arr[j];
						insert++;
					}
				}
				
				List<String> substrings = getCombinations(newArr, firstChar);
				List<String> fullList = new ArrayList<>();
				for (String str : substrings)
				{
					fullList.add(new String(leadChar + str));
				}
					
				list.addAll(fullList);
			}
		}
		return list;
	}
}
