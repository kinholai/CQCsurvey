package cqc.survey.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 调查答题信息
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_answer", schema = "")
@SuppressWarnings("serial")
public class SurveyAnswer implements Serializable
{
	/**id*/
	private java.lang.String id;
	
	/**survey_interviewee,id*/
	private java.lang.String intervieweeId;
	
	/**survey_choice,id*/
	private java.lang.String choiceId;
	
	/**主观题答案*/
	private java.lang.String textAnswer;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId()
	{
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id)
	{
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  survey_interviewee,id
	 */
	@Column(name ="INTERVIEWEE_ID",nullable=false,length=36)
	public java.lang.String getIntervieweeId()
	{
		return this.intervieweeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  survey_interviewee,id
	 */
	public void setIntervieweeId(java.lang.String intervieweeId)
	{
		this.intervieweeId = intervieweeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  survey_choice,id
	 */
	@Column(name ="CHOICE_ID",nullable=false,length=36)
	public java.lang.String getChoiceId()
	{
		return this.choiceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  survey_choice,id
	 */
	public void setChoiceId(java.lang.String choiceId)
	{
		this.choiceId = choiceId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主观题答案
	 */
	@Column(name ="TEXT_ANSWER",nullable=true,length=65535)
	public java.lang.String getTextAnswer()
	{
		return this.textAnswer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主观题答案
	 */
	public void setTextAnswer(java.lang.String textAnswer)
	{
		this.textAnswer = textAnswer;
	}
}
