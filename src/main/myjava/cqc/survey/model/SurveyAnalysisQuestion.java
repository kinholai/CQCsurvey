package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 调查分析 问题属性设置
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_analysis_question", schema = "")
@SuppressWarnings("serial")
public class SurveyAnalysisQuestion implements Serializable
{
	private String id;
	
	private String analysisId;
	
	private String questionId;
	
	private int orderNo;
	
	private String question;
	
	private int choiceNum;
	
	//权重
	private double weight;
	
	//是否计分
	private short markState;
	
	//分数
	private String mark;
	
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

	@Column(name = "analysis_id", nullable=false, length = 36)
	public String getAnalysisId()
	{
		return analysisId;
	}

	public void setAnalysisId(String analysisId)
	{
		this.analysisId = analysisId;
	}

	@Column(name = "question_id", nullable=false, length = 36)
	public String getQuestionId()
	{
		return questionId;
	}

	public void setQuestionId(String questionId)
	{
		this.questionId = questionId;
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

	@Column(name = "question", length = 50)
	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	@Column(name = "choice_num", length = 11)
	public int getChoiceNum()
	{
		return choiceNum;
	}

	public void setChoiceNum(int choiceNum)
	{
		this.choiceNum = choiceNum;
	}

	@Column(name = "weight", length = 4)
	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	@Column(name = "mark_state", length = 6)
	public short getMarkState()
	{
		return markState;
	}

	public void setMarkState(short markState)
	{
		this.markState = markState;
	}

	@Column(name = "mark", length = 30)
	public String getMark()
	{
		return mark;
	}

	public void setMark(String mark)
	{
		this.mark = mark;
	}
	
	
}
