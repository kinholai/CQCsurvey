package cqc.survey.model;

public class PointAnalysis
{
	private int orderNo;
	
	private double weight;
	
	private int choiceOrder;
	
	private short markState;
	
	private String mark;

	private String intervieweeId;
	
	public PointAnalysis(int orderNo, double weight, int choiceOrder,
			short markState, String mark)
	{
		super();
		this.orderNo = orderNo;
		this.weight = weight;
		this.choiceOrder = choiceOrder;
		this.markState = markState;
		this.mark = mark;
	}
	
	public PointAnalysis(int orderNo, double weight, int choiceOrder,
			short markState, String mark, String intervieweeId)
	{
		super();
		this.orderNo = orderNo;
		this.weight = weight;
		this.choiceOrder = choiceOrder;
		this.markState = markState;
		this.mark = mark;
		this.intervieweeId = intervieweeId;
	}

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	public int getChoiceOrder()
	{
		return choiceOrder;
	}

	public void setChoiceOrder(int choiceOrder)
	{
		this.choiceOrder = choiceOrder;
	}

	public short getMarkState()
	{
		return markState;
	}

	public void setMarkState(short markState)
	{
		this.markState = markState;
	}

	public String getMark()
	{
		return mark;
	}

	public void setMark(String mark)
	{
		this.mark = mark;
	}

	public String getIntervieweeId()
	{
		return intervieweeId;
	}

	public void setIntervieweeId(String intervieweeId)
	{
		this.intervieweeId = intervieweeId;
	}
	
	
}
