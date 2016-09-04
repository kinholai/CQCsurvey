package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "satisfaction_type", schema = "")
@SuppressWarnings("serial")
public class SatisfactionType implements Serializable
{
	public String id;
	
	public String typeName;
	
	public short category;

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

	@Column(name = "type_name", nullable = false, length = 16)
	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	@Column(name = "category", nullable = false)
	public short getCategory()
	{
		return category;
	}

	public void setCategory(short category)
	{
		this.category = category;
	}
	
	
}
