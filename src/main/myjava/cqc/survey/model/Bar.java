package cqc.survey.model;

import java.util.List;

public class Bar
{
	private String name;
	
	private String type;
	
	private List<String> data;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

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
