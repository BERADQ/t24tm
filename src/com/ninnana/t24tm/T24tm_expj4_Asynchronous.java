package com.ninnana.t24tm;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.operator.Operator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class T24tm_expj4_Asynchronous
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
	static List<String> resultList = new ArrayList<>();//结果List
	
	@Deprecated
	public static List<String> getExper(int[] numbers)
	{
		
		ExecutorService exec = Executors.newFixedThreadPool(12);
		Queue<Future<ResultObject>> futures = new LinkedList<>();//算数结果队列
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
						Expr expre = new Expr();
						expre.setIntegers(integers);
						expre.setBracket(bracket);
						expre.setMinus(minus);
						expre.setOperator(operator);
						futures.offer(expre.setAsync(exec));
						
					}
					
				}
			}
			
		}
		exec.shutdown();//停止增加任务但已有任务继续处理
		try
		{
			System.out.println(exec.awaitTermination(100, TimeUnit.SECONDS));
		} catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
		System.out.println(futures.size());
		while (!futures.isEmpty())//当任意为空时停止，一般来说两者的Size是相同的
		{
			try
			{
				ResultObject OBJ = futures.poll().get();//弹出一个结果以供判断
				if (OBJ.getValue().get() == 24)//等待结果返回判断是否为24
				{
					resultList.add(OBJ.getFormula());//符合条件的加入到结果list中
					continue;
				}
				times++;//错误计数
			} catch (Exception ignored)//无视无效结果抛出的异常
			{
			}
		}
		return resultList.size() > 0 ? resultList : null;
	}
	
	public static String[] getBracket(int map)
	{
		String[] result = {"", "", "", "", "", ""};
		String[] resultmap = {"(", "(", ")", "(", ")", ")"};
		StringBuilder stringmap = new StringBuilder(Integer.toBinaryString(map));
		int c;
		if (stringmap.length() < 6)
		{
			c = 6 - stringmap.length();
			for (int i = 0; i < c; i++)
			{
				stringmap.insert(0, '0');
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
		StringBuilder stringmap = new StringBuilder(Integer.toString(map, 9));
		int c;
		if (stringmap.length() < 3)
		{
			c = 3 - stringmap.length();
			for (int i = 0; i < c; i++)
			{
				stringmap.insert(0, '0');
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
		StringBuilder stringmap = new StringBuilder(Integer.toBinaryString(map));
		int c;
		if (stringmap.length() < 4)
		{
			c = 4 - stringmap.length();
			for (int i = 0; i < c; i++)
			{
				stringmap.insert(0, '0');
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
