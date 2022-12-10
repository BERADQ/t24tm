package com.ninnana.t24tm;

import java.util.Arrays;
import java.util.List;

/*
import static com.ninnana.t24tm.T24tm_expj4_Asynchronous.getExper;
import static com.ninnana.t24tm.T24tm_expj4_Asynchronous.times;
*/



import static com.ninnana.t24tm.T24tm_expj4.getExper;
import static com.ninnana.t24tm.T24tm_expj4.times;


public class Demo
{
	
	
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		int[] a = {13, 13, 13, 1};
		System.out.println("计算: " + Arrays.toString(a));
		List<String> result = getExper(a);
		System.out.println(((result != null) ? ("结果=" + Arrays.toString(result.toArray())) : ("无解")));
		System.out.print(((result != null) ? ("总数: " + result.size() + "\n") : ("")));
		System.out.println("试错: " + times + "times");
		times = 0;
		long endTime = System.currentTimeMillis();
		System.out.println("时长: " + (endTime - startTime) + "ms");//时长: 2101ms 总数: 426
	}
}
