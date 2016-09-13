package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "survey_analysis_result", schema = "")
@SuppressWarnings("serial")
public class SurveyAnalysisResult implements Serializable
{
	private String id;
	
	private String intervieweeId;
	
	private String analysisHeadId;
	
	private int orderNo;
	
	private int choiceOrder;
	
	private double result;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name ="INTERVIEWEE_ID",nullable=false,length=36)
	public String getIntervieweeId()
	{
		return intervieweeId;
	}

	public void setIntervieweeId(String intervieweeId)
	{
		this.intervieweeId = intervieweeId;
	}

	@Column(name ="ANALYSIS_HEAD_ID",nullable=false,length=36)
	public String getAnalysisHeadId()
	{
		return analysisHeadId;
	}

	public void setAnalysisHeadId(String analysisHeadId)
	{
		this.analysisHeadId = analysisHeadId;
	}

	@Column(name = "order_no", length = 6)
	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	@Column(name ="CHOICE_ORDER",nullable=false,length=36)
	public int getChoiceOrder()
	{
		return choiceOrder;
	}

	public void setChoiceOrder(int choiceOrder)
	{
		this.choiceOrder = choiceOrder;
	}

	@Column(name ="RESULT",nullable=false,length=10)
	public double getResult()
	{
		return result;
	}

	public void setResult(double result)
	{
		this.result = result;
	}
	
	
}
