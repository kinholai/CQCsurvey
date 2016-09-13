package cqc.survey.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.web.system.pojo.base.TSAttachment;

@Entity
@Table(name = "survey_analysis_python")
@SuppressWarnings("serial")
public class SurveyAnalysisPython extends TSAttachment implements Serializable
{
	private String analysisHeadId;
	
	private String remark;
	
	private Date createTime;

	@Column(name ="ANALYSIS_HEAD_ID",nullable=false,length=36)
	public String getAnalysisHeadId()
	{
		return analysisHeadId;
	}

	public void setAnalysisHeadId(String analysisHeadId)
	{
		this.analysisHeadId = analysisHeadId;
	}

	@Column(name ="REMARK",nullable=true,length=36)
	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Column(name ="CREATE_TIME",nullable=false)
	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	
}
