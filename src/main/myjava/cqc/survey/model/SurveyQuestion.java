package cqc.survey.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 调查问题
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_question", schema = "")
@SuppressWarnings("serial")
public class SurveyQuestion implements Serializable
{
	/**id*/
	private java.lang.String id;
	
	/**survey_head,id*/
	private java.lang.String headId;
	
	/**问题序号*/
	private Integer orderNo;
	
	/**问题描述*/
	private java.lang.String question;
	
	/**权重*/
//	private java.lang.Float weight;
	
	/**转化html代码*/
	private java.lang.String html;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
//	@GeneratedValue(generator = "paymentableGenerator")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
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
	 *@return: java.lang.String  survey_head,id
	 */
	@Column(name ="HEAD_ID",nullable=true,length=36)
	public java.lang.String getHeadId()
	{
		return this.headId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  survey_head,id
	 */
	public void setHeadId(java.lang.String headId)
	{
		this.headId = headId;
	}
	@Column(name = "order_no")
	public Integer getOrderNo()
	{
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo)
	{
		this.orderNo = orderNo;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题描述
	 */
	@Column(name ="QUESTION",nullable=true,length=64)
	public java.lang.String getQuestion()
	{
		return this.question;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题描述
	 */
	public void setQuestion(java.lang.String question)
	{
		this.question = question;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  权重
	 */
//	@Column(name ="WEIGHT",nullable=true,length=22)
//	public java.lang.Float getWeight()
//	{
//		return this.weight;
//	}
//
//	/**
//	 *方法: 设置java.lang.Double
//	 *@param: java.lang.Double  权重
//	 */
//	public void setWeight(java.lang.Float weight)
//	{
//		this.weight = weight;
//	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  转化html代码
	 */
	@Column(name ="HTML",nullable=true,length=65535)
	public java.lang.String getHtml()
	{
		return this.html;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  转化html代码
	 */
	public void setHtml(java.lang.String html)
	{
		this.html = html;
	}
	
}
