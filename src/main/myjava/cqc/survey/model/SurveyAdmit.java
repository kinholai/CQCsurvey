package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 白名单
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_admit", schema = "")
@SuppressWarnings("serial")
public class SurveyAdmit implements Serializable
{
	/**id*/
	private java.lang.String id;
	
	/**survey_head,id*/
	private java.lang.String headId;
	
	/**IP地址*/
	private String ip;
	
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
	 *@return: java.lang.String  survey_head,id
	 */
	@Column(name ="HEAD_ID",nullable=false,length=36)
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
	
	@Column(name ="IP",nullable=false,length=32)
	public java.lang.String getIp()
	{
		return this.ip;
	}

	public void setIp(java.lang.String ip)
	{
		this.ip = ip;
	}
}
