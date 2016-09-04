package cqc.survey.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 字典表内容
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_dictionary_item", schema = "")
@SuppressWarnings("serial")
public class DictionaryItem implements Serializable
{
	/**id*/
	private java.lang.String id;
	
	/**survey_dictionary_head,id*/
	private java.lang.String headId;
	
	/**字典内容*/
	private java.lang.String item;
	
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
	 *@return: java.lang.String  survey_dictionary_head,id
	 */
	@Column(name ="HEAD_ID",nullable=false,length=36)
	public java.lang.String getHeadId()
	{
		return this.headId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  survey_dictionary_head,id
	 */
	public void setHeadId(java.lang.String headId)
	{
		this.headId = headId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  字典内容
	 */
	@Column(name ="ITEM",nullable=true,length=16)
	public java.lang.String getItem()
	{
		return this.item;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  字典内容
	 */
	public void setItem(java.lang.String item)
	{
		this.item = item;
	}
}
