package cqc.survey.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "satisfaction_survey", schema = "")
@SuppressWarnings("serial")
public class SatisfactionSurvey implements Serializable
{
	private String id;
	
	private String title;

	private String description;
	
	private Date createTime;
	
	private short prority;
	
	private short state;
	
	private String image;
	
	private String typeId;
	
	private String surveyId;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@Column(name = "description", nullable = true, length = 255)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Column(name = "create_time")
	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	@Column(name = "prority", nullable = false)
	public short getPrority()
	{
		return prority;
	}

	public void setPrority(short prority)
	{
		this.prority = prority;
	}

	@Column(name = "state", nullable = false)
	public short getState()
	{
		return state;
	}

	public void setState(short state)
	{
		this.state = state;
	}

	@Column(name = "image", nullable = false, length = 255)
	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	@Column(name = "type_id", nullable = false, length = 36)
	public String getTypeId()
	{
		return typeId;
	}

	public void setTypeId(String typeId)
	{
		this.typeId = typeId;
	}

	@Column(name = "survey_id", nullable = false, length = 36)
	public String getSurveyId()
	{
		return surveyId;
	}

	public void setSurveyId(String surveyId)
	{
		this.surveyId = surveyId;
	}
}
