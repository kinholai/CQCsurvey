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
 * 调查分析
 * @author Kinho
 *
 */
@Entity
@Table(name = "survey_analysis_head", schema = "")
@SuppressWarnings("serial")
public class SurveyAnalysisHead implements Serializable
{
	private String id;
	
	private String headId;
	
	private String head;
	
	//配置状态
	private short settingState;
	
	private Date createdDate;
	
	//备注说明
	private String remark;

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

	@Column(name ="HEAD_ID",nullable=false,length=36)
	public String getHeadId()
	{
		return headId;
	}

	public void setHeadId(String headId)
	{
		this.headId = headId;
	}

	@Column(name = "HEAD", length = 50)
	public String getHead()
	{
		return head;
	}

	public void setHead(String head)
	{
		this.head = head;
	}

	@Column(name = "setting_state", length = 6)
	public short getSettingState()
	{
		return settingState;
	}

	public void setSettingState(short settingState)
	{
		this.settingState = settingState;
	}

	@Column(name = "created_date")
	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	@Column(name = "remark", length = 60)
	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	
}
