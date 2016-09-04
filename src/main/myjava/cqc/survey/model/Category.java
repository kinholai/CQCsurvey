package cqc.survey.model;

import java.util.List;

public class Category
{
	private String type;
	
	private List<String> data;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<String> getData()
	{
		return data;
	}

	public void setData(List<String> data)
	{
		this.data = data;
	}
}
