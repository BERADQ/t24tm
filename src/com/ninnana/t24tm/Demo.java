package com.ninnana.t24tm;

import java.util.Arrays;

import static com.ninnana.t24tm.T24tm.getExper;
import static com.ninnana.t24tm.T24tm.times;

public class Demo
{
	
	
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		
		int[] a = {10, 10, 10, 10};
		
		System.out.println("开始计算:"+ Arrays.toString(a));
		
		System.out.println("结果:" + getExper(a));
		
		System.out.println("试错:" + times + "times");
		
		times = 0;
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("时长:" + (endTime - startTime) + "ms");

		/*Expression expr =
				new ExpressionBuilder("10|-10-10^(-10)").operator(f1).operator(f2).operator(f3).operator(f4)
						.operator(f5).build();
		System.out.println(expr.evaluate());
		System.out.println(10|-10-10^(-10));*/
		
	}
}
