package com.ninnana.t24tm;

import java.util.concurrent.Future;

public class ResultObject
{
	
	public String getFormula()
	{
		return formula;
	}
	
	public void setFormula(String formula)
	{
		this.formula = formula;
	}
	
	public Future<Double> getValue()
	{
		return value;
	}
	
	public void setValue(Future<Double> value)
	{
		this.value = value;
	}
	
	private String formula = null;
	private Future<Double> value = null;
}
