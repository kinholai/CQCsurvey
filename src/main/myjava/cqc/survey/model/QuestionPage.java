package cqc.survey.model;

import java.util.List;

public class QuestionPage
{
	List<SurveyQuestion> extras;
	
	List<SurveyQuestion> normals;

	public List<SurveyQuestion> getExtras()
	{
		return extras;
	}

	public void setExtras(List<SurveyQuestion> extras)
	{
		this.extras = extras;
	}

	public List<SurveyQuestion> getNormals()
	{
		return normals;
	}

	public void setNormals(List<SurveyQuestion> normals)
	{
		this.normals = normals;
	}
	
	
}
