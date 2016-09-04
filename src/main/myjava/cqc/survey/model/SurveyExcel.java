package cqc.survey.model;

public class SurveyExcel
{
	private int orderNo;
	
	private int choiceOrder;
	
	private String textAnswer;

	public SurveyExcel(int orderNo, int choiceOrder, String textAnswer)
	{
		this.orderNo = orderNo;
		this.choiceOrder = choiceOrder;
		this.textAnswer = textAnswer;
	}
	
	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	public int getChoiceOrder()
	{
		return choiceOrder;
	}

	public void setChoiceOrder(int choiceOrder)
	{
		this.choiceOrder = choiceOrder;
	}

	public java.lang.String getTextAnswer()
	{
		return textAnswer;
	}

	public void setTextAnswer(java.lang.String textAnswer)
	{
		this.textAnswer = textAnswer;
	}
	
	
}
