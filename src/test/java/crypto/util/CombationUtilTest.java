package crypto.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CombationUtilTest {

	@Test
	public void testCombinations()
	{
		CombinationUtil combinationUtil = new CombinationUtil();
		char[] testArr = {'a', 'b', 'c', 'd'};
		List<String> retList = combinationUtil.getCombinations(testArr);
		for (String str : retList)
		{
			System.out.println(str);
		}
		Assert.assertNotNull(retList);
		Assert.assertEquals(24, retList.size());
	}
	
	@Test
	public void testFiveCombinations()
	{
		CombinationUtil combinationUtil = new CombinationUtil();
		char[] testArr = {'a', 'b', 'c', 'd', 'e'};
		List<String> retList = combinationUtil.getCombinations(testArr);
		for (String str : retList)
		{
			Assert.assertEquals(5,  str.length());
		}
		Assert.assertNotNull(retList);
		Assert.assertEquals(120, retList.size());
	}
	
	@Test
	public void testSixCombinations()
	{
		CombinationUtil combinationUtil = new CombinationUtil();
		char[] testArr = {'a', 'b', 'c', 'd', 'e', 'f'};
		List<String> retList = combinationUtil.getCombinations(testArr);
		for (String str : retList)
		{
			Assert.assertEquals(6,  str.length());
		}
		Assert.assertNotNull(retList);
		Assert.assertEquals(720, retList.size());
	}
	
	@Test
	public void testEightCombinations()
	{
		CombinationUtil combinationUtil = new CombinationUtil();
		char[] testArr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		List<String> retList = combinationUtil.getCombinations(testArr);
		for (String str : retList)
		{
			Assert.assertEquals(8,  str.length());
		}
		Assert.assertNotNull(retList);
		Assert.assertEquals(40320, retList.size());
	}
	
	@Test
	public void testTenCombinations()
	{
		CombinationUtil combinationUtil = new CombinationUtil();
		char[] testArr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
		List<String> retList = combinationUtil.getCombinations(testArr);
		for (String str : retList)
		{
			Assert.assertEquals(10,  str.length());
		}
		Assert.assertNotNull(retList);
		Assert.assertEquals(3628800, retList.size());
	}
}
