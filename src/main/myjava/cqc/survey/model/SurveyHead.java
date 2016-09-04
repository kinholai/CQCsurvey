package cqc.survey.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 调查信息
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_head", schema = "")
@SuppressWarnings("serial")
public class SurveyHead implements Serializable
{
	/**id*/
	private java.lang.String id;
	
	/**问卷标题*/
	private java.lang.String head;
	
	private String descriptionHtml;
	
	/**调查创建时间*/
	private java.util.Date createdTime;
	
	/**调查发布状态*/
	private short publishState;
	
	/**调查发布时间*/
	private java.util.Date publishTime;
	
	/**调查限制状态*/
	private short limitState;
	
	private int limitNum;
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
	 *@return: java.lang.String  问卷标题
	 */
	@Column(name ="HEAD",nullable=true,length=50)
	public java.lang.String getHead()
	{
		return this.head;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问卷标题
	 */
	public void setHead(java.lang.String head)
	{
		this.head = head;
	}
	
	@Column(name ="DESCRIPTION_HTML",nullable=true,length=512)
	public String getDescriptionHtml()
	{
		return descriptionHtml;
	}

	public void setDescriptionHtml(String descriptionHtml)
	{
		this.descriptionHtml = descriptionHtml;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  问卷创建时间
	 */
	@Column(name ="CREATED_TIME",nullable=true)
	public java.util.Date getCreatedTime()
	{
		return this.createdTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  问卷创建时间
	 */
	public void setCreatedTime(java.util.Date createdTime)
	{
		this.createdTime = createdTime;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  发布状态
	 */
	@Column(name ="PUBLISH_STATE",nullable=true,length=6)
	public Short getPublishState()
	{
		return this.publishState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  发布状态
	 */
	public void setPublishState(Short publishState)
	{
		this.publishState = publishState;
	}
	
	@Column(name ="PUBLISH_TIME",nullable=true)
	public java.util.Date getPublishTime()
	{
		return publishTime;
	}

	public void setPublishTime(java.util.Date publishTime)
	{
		this.publishTime = publishTime;
	}

	@Column(name ="LIMIT_STATE",nullable=true,length=6)
	public short getLimitState()
	{
		return limitState;
	}

	public void setLimitState(short limitState)
	{
		this.limitState = limitState;
	}

	@Column(name ="LIMIT_NUM",nullable=true)
	public int getLimitNum()
	{
		return limitNum;
	}

	public void setLimitNum(int limitNum)
	{
		this.limitNum = limitNum;
	}

}
