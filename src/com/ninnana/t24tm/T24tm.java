package com.ninnana.t24tm;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class T24tm
{
	
	public static long times = 0;
	
	static Operator f1 = new Operator(">>", 2, true, Operator.PRECEDENCE_ADDITION - 3)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] >> (int) doubles[1];
		}
	};
	static Operator f2 = new Operator("<<", 2, true, Operator.PRECEDENCE_ADDITION - 3)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] << (int) doubles[1];
		}
	};
	static Operator f3 = new Operator("^", 2, true, Operator.PRECEDENCE_ADDITION - 5)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] ^ (int) doubles[1];
		}
	};
	static Operator f4 = new Operator("&", 2, true, Operator.PRECEDENCE_ADDITION - 4)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] & (int) doubles[1];
		}
	};
	static Operator f5 = new Operator("|", 2, true, Operator.PRECEDENCE_ADDITION - 6)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] | (int) doubles[1];
		}
	};

	static List<List<Integer>> listList;
	static String[] bracket;
	static String[] operator;
	static String[] minus;
	static String result;
/*
	static List<Integer> integers;

	
	static int i;
	static int j;
	static int k;
*/


	
	public static String getExper(int[] numbers)
	{
		
		//T24tm.listList = permuteUnique(numbers);
		listList = permuteUnique(numbers);
		//T1 te = new T1();
		for (int i = 0; i <= 0b111111; i++)
		{
			//T24tm.i = i;
			for (int j = 0; j <= 819; j++)
			{
				//T24tm.j = j;
				for (List<Integer> integers : listList)
				{
					//T24tm.integers = integers;
					for (int k = 0; k < 0b1111; k++)
					{
						//T24tm.k = k;
						bracket = getBracket(i);
						operator = getOperator(j);
						minus = getMinusSign(k);
						int i1 =
								(Objects.equals(bracket[0], "") ? 1 : 0) + (Objects.equals(bracket[1], "") ? 1 : 0) +
										(Objects.equals(bracket[3], "") ? 1 : 0);
						int i2 =
								(Objects.equals(bracket[2], "") ? 1 : 0) + (Objects.equals(bracket[4], "") ? 1 : 0) +
										(Objects.equals(bracket[5], "") ? 1 : 0);
						
						if (i1 == i2)
							if (!(Objects.equals(operator[0], minus[1]) & Objects.equals(bracket[1], "")))
								if (!(Objects.equals(operator[1], minus[2]) & Objects.equals(bracket[3], "")))
									if (!Objects.equals(operator[2], minus[3]))
									{
										result =
												bracket[0] + minus[0] + integers.get(0) + operator[0] +
														bracket[1] + minus[1] + integers.get(1) + bracket[2] +
														operator[1] +
														bracket[3] + minus[2] + integers.get(2) + bracket[4] +
														operator[2] +
														minus[3] + integers.get(3) + bracket[5];
										
										try
										{
											Expression expr =
													new ExpressionBuilder(result).operator(f1).operator(f2).operator(f3)
															.operator(f4)
															.operator(f5).build();
											if (expr.validate().isValid())
												if (expr.evaluate() == 24)
													System.out.println(result);
											times++;
										} catch (Exception ignored)
										{
										}
									}
						//Thread thread = new Thread(te);
						//thread.start();
					}
					
				}
			}
			
		}
		return null;
	}
	
	public static String[] getBracket(int map)
	{
		String[] result = {"", "", "", "", "", ""};
		String[] resultmap = {"(", "(", ")", "(", ")", ")"};
		String stringmap = Integer.toBinaryString(map);
		int c;
		if (stringmap.length() < 6)
		{
			c = 6 - stringmap.length();
			for (int i = 0; i < c; i++)
			{
				stringmap = '0' + stringmap;
			}
		}
		for (int i = 0; i < result.length; i++)
		{
			if (stringmap.charAt(i) == '1')
			{
				result[i] = resultmap[i];
			} else
			{
				result[i] = "";
			}
		}
		
		return result;
	}
	
	public static String[] getOperator(int map)
	{
		final String[] OPERATOR_MAP = {"+", "-", "*", "/", ">>", "<<", "&", "|", "^"};
		String[] result = {"", "", ""};
		String stringmap = Integer.toString(map, 9);
		int c;
		if (stringmap.length() < 3)
		{
			c = 3 - stringmap.length();
			for (int i = 0; i < c; i++)
			{
				stringmap = '0' + stringmap;
			}
		}
		for (int i = 0; i < result.length; i++)
		{
			result[i] = OPERATOR_MAP[Integer.parseInt(String.valueOf(stringmap.charAt(i)))];
		}
		return result;
	}
	
	
	static boolean[] vis;
	
	public static List<List<Integer>> permuteUnique(int[] nums)
	{
		List<List<Integer>> ans = new ArrayList<>();
		List<Integer> perm = new ArrayList<>();
		vis = new boolean[nums.length];
		Arrays.sort(nums);
		backtrack(nums, ans, 0, perm);
		return ans;
	}
	
	public static void backtrack(int[] nums, List<List<Integer>> ans, int idx, List<Integer> perm)
	{
		if (idx == nums.length)
		{
			ans.add(new ArrayList<>(perm));
			return;
		}
		for (int i = 0; i < nums.length; ++i)
		{
			if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i - 1]))
			{
				continue;
			}
			perm.add(nums[i]);
			vis[i] = true;
			backtrack(nums, ans, idx + 1, perm);
			vis[i] = false;
			perm.remove(idx);
		}
	}
	
	
	public static String[] getMinusSign(int map)
	{
		String[] s = {"", "", "", ""};
		String stringmap = Integer.toBinaryString(map);
		int c;
		if (stringmap.length() < 4)
		{
			c = 4 - stringmap.length();
			for (int i = 0; i < c; i++)
			{
				stringmap = '0' + stringmap;
			}
		}
		
		for (int i = 0; i < s.length; i++)
		{
			if (stringmap.charAt(i) == '1') s[i] = "-";
			else s[i] = "";
		}
		
		return s;
	}
	
	
}
