package com.ninnana.t24tm;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class T24tm_expj4
{
	public static long times = 0;//遍历错误计数
	static Operator f1 = new Operator(">>", 2, true, Operator.PRECEDENCE_ADDITION - 3)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] >> (int) doubles[1];
		}
	};//运算符定义
	static Operator f2 = new Operator("<<", 2, true, Operator.PRECEDENCE_ADDITION - 3)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] << (int) doubles[1];
		}
	};//运算符定义
	static Operator f3 = new Operator("^", 2, true, Operator.PRECEDENCE_ADDITION - 5)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] ^ (int) doubles[1];
		}
	};//运算符定义
	static Operator f4 = new Operator("&", 2, true, Operator.PRECEDENCE_ADDITION - 4)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] & (int) doubles[1];
		}
	};//运算符定义
	static Operator f5 = new Operator("|", 2, true, Operator.PRECEDENCE_ADDITION - 6)
	{
		@Override
		public double apply(double... doubles)
		{
			return (int) doubles[0] | (int) doubles[1];
		}
	};//运算符定义
	static List<List<Integer>> listList;//数组全排列后
	static String[] bracket;//括号
	static String[] operator;//运算符
	static String[] minus;//数字的正负
	static String result;//独立结果
	static List<String> resultList = new ArrayList<>();//结果List
	
	public static List<String> getExper(int[] numbers)
	{
		Expression expr;
		listList = permuteUnique(numbers);//数组全排列
		for (int i = 0; i <= 0b111111; i++)//遍历括号map
		{
			for (int j = 0; j <= 728; j++)//遍历运算符map 实际是9进制888
			{
				for (List<Integer> integers : listList)
				{
					for (int k = 0; k < 0b1111; k++)//遍历数字的正负map
					{
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
											expr = new ExpressionBuilder(result).operator(f1).operator(f2).operator(f3)
													.operator(f4)
													.operator(f5).build();
											if (expr != null)
												if (expr.validate().isValid())
													if (expr.evaluate() == 24)//直接计算并判断返回值是否为24
													{
														resultList.add(result);//符合的表达式
														continue;
													}
											times++;//错误计次
										} catch (Exception ignored)//无视无效表达式与无效答案
										{
										}
									}
					}
					
				}
			}
			
		}
		return resultList.size() > 0 ? resultList : null;
	}
	static StringBuilder bracket_map= new StringBuilder();
	public static String[] getBracket(int map)
	{
		String[] result = {"", "", "", "", "", ""};
		String[] resultmap = {"(", "(", ")", "(", ")", ")"};
		
		
		bracket_map.append(Integer.toBinaryString(map));
		
		int c;
		if (bracket_map.length() < 6)
		{
			c = 6 - bracket_map.length();
			for (int i = 0; i < c; i++)
			{
				bracket_map.insert(0, '0');
			}
		}
		for (int i = 0; i < result.length; i++)
		{
			if (bracket_map.charAt(i) == '1')
			{
				result[i] = resultmap[i];
			} else
			{
				result[i] = "";
			}
		}
		bracket_map.setLength(0);
		return result;
	}
	static StringBuilder operator_map = new StringBuilder();
	public static String[] getOperator(int map)
	{
		final String[] OPERATOR_MAP = {"+", "-", "*", "/", ">>", "<<", "&", "|", "^"};
		String[] result = {"", "", ""};
		
		
		operator_map.append(Integer.toString(map, 9));
		
		int c;
		if (operator_map.length() < 3)
		{
			c = 3 - operator_map.length();
			for (int i = 0; i < c; i++)
			{
				operator_map.insert(0, '0');
			}
		}
		for (int i = 0; i < result.length; i++)
		{
			result[i] = OPERATOR_MAP[Integer.parseInt(String.valueOf(operator_map.charAt(i)))];
		}
		operator_map.setLength(0);
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
	static StringBuilder minus_map= new StringBuilder();
	public static String[] getMinusSign(int map)
	{
		String[] s = {"", "", "", ""};
		
		
		minus_map.append(Integer.toBinaryString(map));
		
		int c;
		if (minus_map.length() < 4)
		{
			c = 4 - minus_map.length();
			for (int i = 0; i < c; i++)
			{
				minus_map.insert(0, '0');
			}
		}
		
		for (int i = 0; i < s.length; i++)
		{
			if (minus_map.charAt(i) == '1') s[i] = "-";
			else s[i] = "";
		}
		minus_map.setLength(0);
		return s;
		
		
	}
}
