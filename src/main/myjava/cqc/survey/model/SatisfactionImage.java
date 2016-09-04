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
@Table(name = "satisfaction_image", schema = "")
@SuppressWarnings("serial")
public class SatisfactionImage implements Serializable
{
	private String id;
	
	private String title;
	
	private String image;
	
	private String description;
	
	private short tag;
	
	private String tagId;
	
	private short prority;
	
	private Date createTime;

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

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
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

	@Column(name = "description", nullable = true, length = 255)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "tag", nullable = false)
	public short getTag()
	{
		return tag;
	}

	public void setTag(short tag)
	{
		this.tag = tag;
	}

	@Column(name = "tag_id", nullable = false, length = 36)
	public String getTagId()
	{
		return tagId;
	}

	public void setTagId(String tagId)
	{
		this.tagId = tagId;
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

	@Column(name = "create_time")
	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	
}
