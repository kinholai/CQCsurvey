package cqc.survey.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.service.CommonService;

import cqc.survey.model.SurveyAnalysisPython;

public interface SurveyService extends CommonService
{
	public HttpServletResponse downloadFile(HttpServletRequest request, 
			HttpServletResponse response, String fileRealname);
	
	public void deletePython(SurveyAnalysisPython sap);
	
	public <T> List<T> limit(String hql, int first, int max);
}
