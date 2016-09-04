package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问卷板块信息
 * @author Kinho
 *
 */
@Entity
@Table(name = "questionaire_block", schema = "")
@SuppressWarnings("serial")
public class QuestionaireBlock implements Serializable
{
	private String id;
	
	private String blockOrder;
	
	private String blockName;
	
	private String nameFont;
	
	private String nameSize;
	
	private String blockDescription;
	
	private String descriptionFont;
	
	private String descriptionSize;
	
	private int primaryNum;
	
	private short questionState;
	
	private String mainId;

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

	@Column(name ="BLOCK_ORDER",nullable=true,length=16)
	public String getBlockOrder()
	{
		return blockOrder;
	}

	public void setBlockOrder(String blockOrder)
	{
		this.blockOrder = blockOrder;
	}

	@Column(name ="BLOCK_NAME",nullable=true,length=50)
	public String getBlockName()
	{
		return blockName;
	}

	public void setBlockName(String blockName)
	{
		this.blockName = blockName;
	}

	@Column(name ="NAME_FONT",nullable=true,length=10)
	public String getNameFont()
	{
		return nameFont;
	}

	public void setNameFont(String nameFont)
	{
		this.nameFont = nameFont;
	}

	@Column(name ="NAME_SIZE",nullable=true,length=8)
	public String getNameSize()
	{
		return nameSize;
	}

	public void setNameSize(String nameSize)
	{
		this.nameSize = nameSize;
	}

	@Column(name ="BLOCK_DESCRIPTION",nullable=true,length=256)
	public String getBlockDescription()
	{
		return blockDescription;
	}

	public void setBlockDescription(String blockDescription)
	{
		this.blockDescription = blockDescription;
	}

	@Column(name ="DESCRIPTION_FONT",nullable=true,length=10)
	public String getDescriptionFont()
	{
		return descriptionFont;
	}

	public void setDescriptionFont(String descriptionFont)
	{
		this.descriptionFont = descriptionFont;
	}

	@Column(name ="DESCRIPTION_SIZE",nullable=true,length=8)
	public String getDescriptionSize()
	{
		return descriptionSize;
	}

	public void setDescriptionSize(String descriptionSize)
	{
		this.descriptionSize = descriptionSize;
	}

	@Column(name ="PRIMARY_NUM",nullable=true)
	public int getPrimaryNum()
	{
		return primaryNum;
	}

	public void setPrimaryNum(int primaryNum)
	{
		this.primaryNum = primaryNum;
	}

	@Column(name ="QUESTION_STATE",nullable=true)
	public short getQuestionState()
	{
		return questionState;
	}

	public void setQuestionState(short questionState)
	{
		this.questionState = questionState;
	}

	@Column(name ="MAIN_ID",nullable=false,length=36)
	public String getMainId()
	{
		return mainId;
	}

	public void setMainId(String mainId)
	{
		this.mainId = mainId;
	}
	
	
}
