package cqc.survey.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 字典表标题
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_dictionary_head", schema = "")
@SuppressWarnings("serial")
public class DictionaryHead implements Serializable
{
	/**id*/
	private java.lang.String id;
	
	/**字典表名*/
	private java.lang.String head;
	
	/**功能描述*/
	private java.lang.String description;
	
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
	 *@return: java.lang.String  字典表名
	 */
	@Column(name ="HEAD",nullable=true,length=20)
	public java.lang.String getHead()
	{
		return this.head;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  字典表名
	 */
	public void setHead(java.lang.String head)
	{
		this.head = head;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  功能描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=50)
	public java.lang.String getDescription()
	{
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  功能描述
	 */
	public void setDescription(java.lang.String description)
	{
		this.description = description;
	}
}
