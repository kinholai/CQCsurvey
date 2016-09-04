package cqc.survey.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问卷主要信息
 * @author Kinho
 *
 */
@Entity
@Table(name = "questionaire_main", schema = "")
@SuppressWarnings("serial")
public class QuestionaireMain implements Serializable
{
	private String id;
	
	private String head;

	private String description;
	
	private Date updateTime;
	
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

	@Column(name ="HEAD",nullable=true,length=50)
	public String getHead()
	{
		return head;
	}

	public void setHead(String head)
	{
		this.head = head;
	}

	@Column(name ="DESCRIPTION",nullable=true,length=256)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name ="UPDATE_TIME",nullable=true)
	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	
	
}
