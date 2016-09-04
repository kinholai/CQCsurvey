package cqc.survey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问卷板块问题
 * @author Kinho
 *
 */
@Entity
@Table(name = "questionaire_question", schema = "")
@SuppressWarnings("serial")
public class QuestionaireQuestion implements Serializable
{
	private String id;
	
	private int orderNo;
	
	private String question;
	
//	private float weight;
	
	private String providedChoice;
	
	private String dictionary;
	
	private String tool;
	
	private short format;
	
	private String blockId;
	
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

	@Column(name ="ORDER_NO",nullable=true,length=11)
	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	@Column(name ="QUESTION",nullable=true,length=50)
	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

//	@Column(name ="WEIGHT",nullable=true)
//	public float getWeight()
//	{
//		return weight;
//	}
//
//	public void setWeight(float weight)
//	{
//		this.weight = weight;
//	}

	@Column(name ="PROVIDED_CHOICE",nullable=true,length=128)
	public String getProvidedChoice()
	{
		return providedChoice;
	}

	public void setProvidedChoice(String providedChoice)
	{
		this.providedChoice = providedChoice;
	}

	@Column(name ="DICTIONARY",nullable=true,length=36)
	public String getDictionary()
	{
		return dictionary;
	}

	public void setDictionary(String dictionary)
	{
		this.dictionary = dictionary;
	}

	@Column(name ="TOOL",nullable=true,length=5)
	public String getTool()
	{
		return tool;
	}

	public void setTool(String tool)
	{
		this.tool = tool;
	}

	@Column(name ="FORMAT",nullable=true,length=6)
	public short getFormat()
	{
		return format;
	}

	public void setFormat(short format)
	{
		this.format = format;
	}

	@Column(name ="BLOCK_ID",nullable=false,length=36)
	public String getBlockId()
	{
		return blockId;
	}

	public void setBlockId(String blockId)
	{
		this.blockId = blockId;
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
