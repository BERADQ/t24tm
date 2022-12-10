package com.ninnana.t24tm;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.ninnana.t24tm.T24tm_expj4_Asynchronous.*;

public class Expr
{
	private List<Integer> integers;//数组全排列后
	private String[] bracket;//括号
	private String[] operator;//运算符
	private String[] minus;//数字的正负
	
	private Expression expr;
	
	public void setIntegers(List<Integer> integers)
	{
		this.integers = integers;
	}
	
	public void setBracket(String[] bracket)
	{
		this.bracket = bracket;
	}
	
	public void setOperator(String[] operator)
	{
		this.operator = operator;
	}
	
	public void setMinus(String[] minus)
	{
		this.minus = minus;
	}
	
	public Future<ResultObject> setAsync (ExecutorService exec){
		return exec.submit(new Callable<ResultObject>(){
			
			@Override
			public ResultObject call() throws Exception
			{
				return constructAndCalculate(exec);
			}
		});
	}
	public ResultObject constructAndCalculate(ExecutorService exec){
		
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
						String result =
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
							if (expr != null){
								ResultObject OBJ = new ResultObject();
								OBJ.setValue(expr.evaluateAsync(exec));
								OBJ.setFormula(result);
								return OBJ;
							}
							
						} catch (Exception ignored)//忽略无效表达式
						{
						}
						
						
					}
		return null;
	}
	
	
}
