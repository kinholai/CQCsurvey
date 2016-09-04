package cqc.survey.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 调查选项
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_choice", schema = "")
@SuppressWarnings("serial")
public class SurveyChoice implements Serializable
{
	/**id*/
	private java.lang.String id;
	
	/**survey_question,id*/
	private java.lang.String questionId;
	
	/**选项*/
	private java.lang.String choice;
	
	/**选项序号*/
	private int choiceOrder;
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
	 *@return: java.lang.String  survey_question,id
	 */
	@Column(name ="QUESTION_ID",nullable=true,length=36)
	public java.lang.String getQuestionId()
	{
		return this.questionId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  survey_question,id
	 */
	public void setQuestionId(java.lang.String questionId)
	{
		this.questionId = questionId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项
	 */
	@Column(name ="CHOICE",nullable=true,length=20)
	public java.lang.String getChoice()
	{
		return this.choice;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项
	 */
	public void setChoice(java.lang.String choice)
	{
		this.choice = choice;
	}

	@Column(name ="CHOICE_ORDER",nullable=true)
	public int getChoiceOrder()
	{
		return choiceOrder;
	}

	public void setChoiceOrder(int choiceOrder)
	{
		this.choiceOrder = choiceOrder;
	}

}
