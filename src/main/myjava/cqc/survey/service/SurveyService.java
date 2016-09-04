package cqc.survey.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.service.CommonService;

public interface SurveyService extends CommonService
{
	public HttpServletResponse downloadFile(HttpServletRequest request, 
			HttpServletResponse response, String fileRealname);
}
