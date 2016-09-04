package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "satisfaction_report", schema = "")
@SuppressWarnings("serial")
public class SatisfactionReport implements Serializable
{
	private String id;
	
	private String title;
	
	private String description;
	
	private String content;
	
	private String attachment;
	
	private String createTime;
	
	private short prority;
	
	private String image;
	
	private String typeId;
	
	private String userId;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "increment")
	@Column(name = "ID", nullable = false, length = 20)
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

	@Column(name = "description", nullable = true, length = 255)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "content", nullable = true)
	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@Column(name = "attachment", nullable = true, length = 255)
	public String getAttachment()
	{
		return attachment;
	}

	public void setAttachment(String attachment)
	{
		this.attachment = attachment;
	}

	@Column(name = "create_time")
	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
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

	@Column(name = "user_id", nullable = false, length = 36)
	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	
}
