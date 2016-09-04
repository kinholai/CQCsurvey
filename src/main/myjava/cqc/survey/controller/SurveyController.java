package cqc.survey.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cqc.survey.model.AnswerPage;
import cqc.survey.model.Constant;
import cqc.survey.model.DictionaryItem;
import cqc.survey.model.QuestionaireBlock;
import cqc.survey.model.QuestionaireMain;
import cqc.survey.model.QuestionaireQuestion;
import cqc.survey.model.SurveyAdmit;
import cqc.survey.model.SurveyAnalysisHead;
import cqc.survey.model.SurveyAnswer;
import cqc.survey.model.SurveyChoice;
import cqc.survey.model.SurveyHead;
import cqc.survey.model.SurveyInterviewee;
import cqc.survey.model.SurveyQuestion;
import cqc.survey.model.SurveyReject;
import cqc.survey.service.SurveyService;
import cqc.survey.utils.IpUtil;
import cqc.survey.utils.RandomUtil;

/**
 * 调查管理
 * @author Kinho
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/surveyController")
public class SurveyController extends BaseController
{
	private String message;

	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message = message;
	}
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SurveyService surveyService;
	
	/**
	 * 菜单页面跳转
	 * turn：
	 * 	create - 创建调查
	 * 	foresee - 预览调查
	 * 	publish - 发布调查
	 * 	delete - 删除调查
	 * 	analyse - 分析调查
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "survey")
	public ModelAndView survey(HttpServletRequest request)
	{
		String turn = request.getParameter("turn");
		return new ModelAndView("cqc/survey/" + turn + "");
	}
	
	/**
	 * 返回 调查 数据
	 * @param sHead
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "surveyList")
	public void surveyList(SurveyHead sHead, HttpServletRequest request,
				HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(SurveyHead.class, dataGrid);
		cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 创建调查 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addSurvey")
	public ModelAndView addSurvey(HttpServletRequest request)
	{
		List<QuestionaireMain> qmList = systemService.getList(QuestionaireMain.class);
		if(qmList.size() <= 0)
			return new ModelAndView("cqc/errorPage").addObject("message", "请您先在“问卷管理”中创建问卷。");
		request.setAttribute("qmList", qmList);
		return new ModelAndView("cqc/survey/addSurvey");
	}
	
	/**
	 * 保存 创建调查
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(params = "saveSurvey")
	@ResponseBody
	public AjaxJson saveSurvey(HttpServletRequest request) throws ParseException
	{
		AjaxJson j = new AjaxJson();
		String headId = request.getParameter("headId");
		String limitState = request.getParameter("limitState");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SurveyHead sh = new SurveyHead();
		QuestionaireMain qm = systemService.getEntity(QuestionaireMain.class, headId);
			sh.setHead(qm.getHead());
			if(StringUtil.isNotEmpty(qm.getDescription()))
				sh.setDescriptionHtml("<div class=\"test\">" + qm.getDescription() + "</div>");
			else 
				sh.setDescriptionHtml("<div class=\"test\"></div>");
			sh.setCreatedTime(sdf.parse(sdf.format(new Date())));
			sh.setPublishState(Constant.UNUSING_STATE);//未发布
			sh.setLimitState(Short.parseShort(limitState));
		surveyService.save(sh);
		
		List<QuestionaireBlock> qbList = systemService.findHql("from QuestionaireBlock where mainId=? order by blockOrder asc", headId);
		if(qbList.size() <= 0)
		{
			systemService.delete(sh);
			j.setMsg("调查创建失败：问卷没有板块！");
			return j;
		}
		int questionOrder = 0;
		String description = null;
		for(QuestionaireBlock qb : qbList)
		{
			if(qb.getQuestionState() == Constant.UNUSING_STATE) //有板块没有全部录入问题
			{
				systemService.executeSql("delete from survey_choice where question_id in (select id from survey_question where head_id=?)", sh.getId());
				systemService.executeSql("delete from survey_question where head_id=?", sh.getId());
				systemService.delete(sh);
				j.setMsg("调查创建失败：问卷的" + qb.getBlockOrder() + "没有录入问题！");
				return j;
			}
			
			if(qb.getPrimaryNum() == 0)
			{
				StringBuffer sb = new StringBuffer();
					if(StringUtil.isNotEmpty(qb.getBlockName()))
					{
						sb.append("<div class=\"test\"><font size=\"" + qb.getNameSize() + "\" face=\"" + qb.getNameFont() + "\">");
						sb.append(qb.getBlockName());
						sb.append("</font></div>");
					}
					if(StringUtil.isNotEmpty(qb.getBlockDescription()))
					{
						sb.append("<div class=\"test\"><font size=\"" + qb.getDescriptionSize() + "\" face=\"" + qb.getDescriptionFont() + "\">");
						sb.append(qb.getBlockDescription());
						sb.append("</font></div>");
					}
				if(StringUtil.isNotEmpty(description))
					description = description + sb.toString();
				else
					description = sb.toString();
			}
			else
			{
				String blockId = qb.getId();
				List<QuestionaireQuestion> qqList = systemService.findHql("from QuestionaireQuestion where blockId=? order by orderNo asc", blockId);
				int i = 0; 
				for(QuestionaireQuestion qq : qqList)
				{
					questionOrder++;
					SurveyQuestion sq = new SurveyQuestion();
						sq.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
						sq.setHeadId(sh.getId());
						sq.setOrderNo(questionOrder);
						sq.setQuestion(qq.getQuestion());
//						sq.setWeight(qq.getWeight());
					int numPerLine = qq.getFormat();
					
					StringBuffer html = new StringBuffer();
					if(i == 0)
					{
						if(StringUtil.isNotEmpty(description))
						{
							html.append(description);
							description = null;
						}
						if(StringUtil.isNotEmpty(qb.getBlockName()))
						{
							html.append("<div class=\"test\"><font size=\"" + qb.getNameSize() + "\" face=\"" + qb.getNameFont() + "\">");
							html.append(qb.getBlockName());
							html.append("</font></div>");
						}
						if(StringUtil.isNotEmpty(qb.getBlockDescription()))
						{
							html.append("<div class=\"test\"><font size=\"" + qb.getDescriptionSize() + "\" face=\"" + qb.getDescriptionFont() + "\">");
							html.append(qb.getBlockDescription());
							html.append("</font></div>");
						}
					}
					html.append("<div class=\"test\">" + qq.getQuestion() + "<br>");
					if(qq.getTool().equals(Constant.INPUT_RADIO)) //单选框
	  				{
	  					if(StringUtil.isNotEmpty(qq.getDictionary()))
	  					{
	  						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", qq.getDictionary());
	  						int k = 0;
	  						for(DictionaryItem di : dItems)
	  						{
	  							k++;
	  							//保存选项
	  							SurveyChoice sc = new SurveyChoice();
	  								sc.setQuestionId(sq.getId());
	  								sc.setChoice(di.getItem());
	  								sc.setChoiceOrder(k);
	  							surveyService.save(sc);	
	  								
	  							html.append("<input type=\"radio\" name=\"answerList[" + (questionOrder - 1) + "].choiceId\" value=\""
	  									+ sc.getId() + "\">" + sc.getChoice());
	  							if(numPerLine != 0)
	  								if(k % numPerLine == 0)
	  									html.append("<br />");
	  						}
	  					}
	  					else if(StringUtil.isNotEmpty(qq.getProvidedChoice()))
	  					{
	  						int k = 0;
	  						for(String choice : qq.getProvidedChoice().split("；"))
	  						{
	  							k++;
	  							//保存选项
	  							SurveyChoice sc = new SurveyChoice();
	  								sc.setQuestionId(sq.getId());
	  								sc.setChoice(choice);
	  								sc.setChoiceOrder(k);
	  							surveyService.save(sc);	
	  							
	  							html.append("<input type=\"radio\" name=\"answerList[" + (questionOrder - 1) + "].choiceId\" value=\""
	  									+ sc.getId() + "\">" + sc.getChoice());
	  							if(numPerLine != 0)
	  								if(k % numPerLine == 0)
	  									html.append("<br />");
	  						}
	  					}
	  				}
	  				
	  				else if(qq.getTool().equals(Constant.INPUT_SELECT))
	  				{
	  					html.append("<select style=\"width: 300px\" name=\"answerList[" + (questionOrder - 1) + "].choiceId\">");
	  					html.append("<option value=\"\"></option>");
	  					if(StringUtil.isNotEmpty(qq.getDictionary()))
	  					{
	  						int k = 0;
	  						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", qq.getDictionary());
	  						for(DictionaryItem di : dItems)
	  						{
	  							k++;
	  							//保存选项
	  							SurveyChoice sc = new SurveyChoice();
	  								sc.setQuestionId(sq.getId());
	  								sc.setChoice(di.getItem());
	  								sc.setChoiceOrder(k);
	  							surveyService.save(sc);	
	  								
	  							html.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
	  						}
	  					}
	  					else if(StringUtil.isNotEmpty(qq.getProvidedChoice()))
	  					{
	  						int k = 0;
	  						for(String choice : qq.getProvidedChoice().split("；"))
	  						{
	  							k++;
	  							//保存选项
	  							SurveyChoice sc = new SurveyChoice();
	  								sc.setQuestionId(sq.getId());
	  								sc.setChoice(choice);
	  								sc.setChoiceOrder(k);
	  							surveyService.save(sc);	
	  							
	  							html.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
	  						}
	  					}
	  					html.append("</select>");
	  				}
					
	  				else if(qq.getTool().equals(Constant.INPUT_TEXT))
	  				{
	  					SurveyChoice sc = new SurveyChoice();
	  						sc.setQuestionId(sq.getId());
	  						sc.setChoice(null);
	  						sc.setChoiceOrder(0);
	  					surveyService.save(sc);	
	  						
	  					html.append("<input type=\"hidden\" name=\"answerList[" + (questionOrder - 1) + "].choiceId\" value=\""
	  							+ sc.getId() + "\">");
	  					html.append("<input type=\"text\" name=\"answerList[" + (questionOrder - 1) + "].textAnswer\" value=\"\" style=\"width: 500px\">");
	  				}
	    				
	  				html.append("</div>");
					
	  					sq.setHtml(html.toString());
	  				surveyService.save(sq);
	  					
					i++;
				}
			}
		}
		
		return j;
	}
	
	/**
	 * 黑名单管理 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "blacklist")
	public ModelAndView blacklsit(HttpServletRequest request)
	{
		String headId = request.getParameter("headId");
		request.setAttribute("headId", headId);
		return new ModelAndView("cqc/survey/blacklist");
	}
	
	/**
	 * 返回 黑名单 数据
	 * @param sr
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "blackList")
	public void blackList(SurveyReject sr, HttpServletRequest request,
				HttpServletResponse response, DataGrid dataGrid)
	{
		String headId = request.getParameter("headId");
		CriteriaQuery cq = new CriteriaQuery(SurveyReject.class, dataGrid);
			cq.eq("headId", headId);
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 录入或编辑 黑名单 弹窗
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "aouBIP")
	public ModelAndView aouBIP(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		String headId = request.getParameter("headId");
		if(StringUtil.isNotEmpty(id))
		{
			SurveyReject sr = systemService.getEntity(SurveyReject.class, id);
			request.setAttribute("sr", sr);
		}
		return new ModelAndView("cqc/survey/aouBIP").addObject("headId", headId);
	}
	
	/**
	 * 保存黑名单录入
	 * @param sr
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveBIP")
	@ResponseBody
	public AjaxJson saveBIP(SurveyReject sr, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		if(systemService.findHql("from SurveyAdmit where headId=? and ip=?", sr.getHeadId(), sr.getIp()).size() > 0)
		{
			j.setMsg("添加失败：该ip地址已进入白名单。");
			return j;
		}
		if(StringUtil.isNotEmpty(sr.getId()))
			systemService.saveOrUpdate(sr);
		else
			systemService.save(sr);
		return j;
	}
	
	/**
	 * 白名单管理 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "whitelist")
	public ModelAndView whitelist(HttpServletRequest request)
	{
		String headId = request.getParameter("headId");
		request.setAttribute("headId", headId);
		return new ModelAndView("cqc/survey/whitelist");
	}
	
	/**
	 * 返回 白名单 数据
	 * @param sa
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "whiteList")
	public void whiteList(SurveyAdmit sa, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid)
	{
		String headId = request.getParameter("headId");
		CriteriaQuery cq = new CriteriaQuery(SurveyAdmit.class, dataGrid);
			cq.eq("headId", headId);
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 录入或编辑 白名单 弹窗
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "aouWIP")
	public ModelAndView aouWIP(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		String headId = request.getParameter("headId");
		if(StringUtil.isNotEmpty(id))
		{
			SurveyAdmit sa = systemService.getEntity(SurveyAdmit.class, id);
			request.setAttribute("sa", sa);
		}
		return new ModelAndView("cqc/survey/aouWIP").addObject("headId", headId);
	}
	
	/**
	 * 保存白名单录入
	 * @param sr
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveWIP")
	@ResponseBody
	public AjaxJson saveWIP(SurveyAdmit sa, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		if(systemService.findHql("from SurveyReject where headId=? and ip=?", sa.getHeadId(), sa.getIp()).size() > 0)
		{
			j.setMsg("添加失败：该ip地址已进入黑名单。");
			return j;
		}
		if(StringUtil.isNotEmpty(sa.getId()))
			systemService.saveOrUpdate(sa);
		else
			systemService.save(sa);
		return j;
	}
	
	/**
	 * 预览调查 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goForesee")
	public ModelAndView goForesee(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		SurveyHead sh = surveyService.getEntity(SurveyHead.class, id);
		List<SurveyQuestion> sqList = 
				systemService.findHql("from SurveyQuestion where headId=? order by orderNo asc", id);
		request.setAttribute("sHead", sh);
		request.setAttribute("sqList", sqList);
		return new ModelAndView("cqc/survey/goForesee");
	}
	
  	/**
 	 * 访客参与调查 页面跳转
 	 * @param request
 	 * @return
 	 */
 	@RequestMapping(params = "doSurvey")
 	public ModelAndView doSurvey(HttpServletRequest request)
 	{
 		String id = request.getParameter("id");
 		if(id == null)
 			return new ModelAndView("cqc/survey/surveyResult").addObject("message", "访问失败：地址无效。");
 		if(id.equals("2c909c8f5387a5ed0153bc5799ac0e72"))
			id = "2c909c8f5403df07015404243cb5004e";
 		SurveyHead sh = surveyService.getEntity(SurveyHead.class, id);
		List<SurveyQuestion> sqList = 
				systemService.findHql("from SurveyQuestion where headId=? order by orderNo asc", id);
		request.setAttribute("sHead", sh);
		request.setAttribute("sqList", sqList);
		request.setAttribute("num", sqList.size());
 		return new ModelAndView("cqc/survey/doSurvey");
 	}
 	
	/**
	 * 保存调查答案
	 * @param aPage
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(params = "saveAnswers")
	public ModelAndView saveAnswers(AnswerPage aPage, HttpServletRequest request) throws ParseException
	{
		String headId = request.getParameter("sHeadId");
		String num = request.getParameter("num");
		String ip = IpUtil.getIpAddr(request);
		List<SurveyAnswer> answerList = aPage.getAnswerList();
		if(answerList != null)
		{
			if(answerList.size() < Integer.parseInt(num))
				return new ModelAndView("cqc/survey/surveyResult").addObject("message", "提交失败：请后退并完成所有题目再提交。");
			boolean wrong = false;
			for(SurveyAnswer a : answerList)
			{
				if(a.getChoiceId() == null || a.getChoiceId() == "")
				{
					wrong = true;
					break;
				}
			}
			if(wrong)
				return new ModelAndView("cqc/survey/surveyResult").addObject("message", "提交失败：请后退并完成所有题目再提交。");
		}
		else
			return new ModelAndView("cqc/survey/surveyResult").addObject("message", "提交失败：请后退并完成所有题目再提交。");
		
		SurveyHead sh = surveyService.getEntity(SurveyHead.class, headId);
		int limitNum = sh.getLimitNum();
		List<SurveyInterviewee> siList = 
				surveyService.findHql("from SurveyInterviewee where headId=? and ip=?", headId, ip);
		if(sh.getLimitState() == Constant.SINGLE_IP_STATE_VALUE) //同一IP限提交limitNum次
		{
			if(siList.size() >= limitNum)
				return new ModelAndView("cqc/survey/surveyResult").addObject("message", "抱歉，您已经提交过了，不允许重复提交。");
		}
		else if(sh.getLimitState() == Constant.WHITELIST_UNLIMITED_STATE_VALUE) //仅白名单IP不限提交次数
		{
			if(siList.size() >= limitNum && 
					surveyService.findHql("from SurveyAdmit where headId=? and ip=?", headId, ip).size() <= 0)
				return new ModelAndView("cqc/survey/surveyResult").addObject("message", "抱歉，您已经提交过了，不允许重复提交。");
		}
		else if(sh.getLimitState() == Constant.BLACKLIST_LIMITED_STATE_VALUE) //黑名单IP限提交limitNum次，非黑名单IP不限提交次数
		{
			if(siList.size() >= limitNum && 
					surveyService.findHql("from SurveyReject where headId=? and ip=?", headId, ip).size() > 0)
				return new ModelAndView("cqc/survey/surveyResult").addObject("message", "抱歉，您已经提交过了，不允许重复提交。");
		}
		else if(sh.getLimitState() == Constant.MIXED_STATE_VALUE) //仅白名单IP不限提交次数，且黑名单IP限提交limitNum次
		{
			if(siList.size() >= limitNum)
			{
				if(surveyService.findHql("from SurveyAdmit where headId=? and ip=?", headId, ip).size() <= 0)
					return new ModelAndView("cqc/survey/surveyResult").addObject("message", "抱歉，您已经提交过了，不允许重复提交。");
				if(surveyService.findHql("from SurveyReject where headId=? and ip=?", headId, ip).size() > 0)
					return new ModelAndView("cqc/survey/surveyResult").addObject("message", "抱歉，您已经提交过了，不允许重复提交。");
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SurveyInterviewee interviewee = new SurveyInterviewee();
			interviewee.setHeadId(headId);
			interviewee.setIp(ip);
			interviewee.setSubmitTime(sdf.parse(sdf.format(new Date())));
			interviewee.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
		
		try{
			for(SurveyAnswer answer : answerList)
			{
				answer.setIntervieweeId(interviewee.getId());
				surveyService.save(answer);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			message = "问卷提交失败：系统内部错误。";
			return new ModelAndView("cqc/survey/surveyResult").addObject("message", message);
		}
		
		String randomCode = RandomUtil.getCharAndNumr(8);
		while(true)
		{
			if(systemService.findHql("from SurveyInterviewee where code=? and headId=?", randomCode, headId).size() > 0)
				randomCode = RandomUtil.getCharAndNumr(8);
			else
				break;
		}
		interviewee.setCode(randomCode);
		surveyService.save(interviewee);
		message = "问卷提交成功：感谢您的参与，祝您生活愉快！" + "本次问卷调查的随机码是" + randomCode + "。";
		return new ModelAndView("cqc/survey/surveyResult").addObject("message", message);
	}
	
	/**
	 * 修改发布状态 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "changePublish")
	public ModelAndView changePublish(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		SurveyHead sh = surveyService.getEntity(SurveyHead.class, id);
		request.setAttribute("sHead", sh);
		return new ModelAndView("cqc/survey/changePublish");
	}
	
	/**
	 * 保存修改发布状态
	 * @param sh
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "savePublish")
	@ResponseBody
	public AjaxJson savePublish(SurveyHead sHead, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		try{
			SurveyHead sh = surveyService.getEntity(SurveyHead.class, sHead.getId());
			if(sh.getPublishState() == sHead.getPublishState())
			{
				j.setMsg("发布状态未改动。");
				return j;
			}
			if(sh.getPublishState() == Constant.UNUSING_STATE)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sh.setPublishTime(sdf.parse(sdf.format(new Date())));
			}
				sh.setPublishState(sHead.getPublishState());
			surveyService.saveOrUpdate(sh);
			if(sh.getPublishState() == Constant.USING_STATE)
				j.setMsg("发布成功。");
			else
				j.setMsg("取消发布成功。");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			j.setMsg("操作失败：系统内部错误。");
		}
		return j;
	}
	
	/**
	 * 修改限制状态 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "changeLimit")
	public ModelAndView changeLimit(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		SurveyHead sh = surveyService.getEntity(SurveyHead.class, id);
		request.setAttribute("sHead", sh);
		return new ModelAndView("cqc/survey/changeLimit");
	}
	
	/**
	 * 保存 修改限制状态
	 * @param sHead
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveLimit")
	@ResponseBody
	public AjaxJson saveLimit(SurveyHead sHead, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		try{
			SurveyHead sh = surveyService.getEntity(SurveyHead.class, sHead.getId());
//			if(sh.getLimitState() == sHead.getLimitState())
//			{
//				j.setMsg("限制状态未改动。");
//				return j;
//			}
				sh.setLimitState(sHead.getLimitState());
				sh.setLimitNum(sHead.getLimitNum());
			surveyService.saveOrUpdate(sh);
			j.setMsg("修改成功。");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			j.setMsg("操作失败：系统内部错误。");
		}
		return j;
	}
	
	/**
	 * 获取问卷地址
	 * @param modelMap
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getAddress")
	public ModelAndView getAddress(ModelMap modelMap, @RequestParam String id, 
									HttpServletRequest request)
	{
		if(id.equals("2c909c8f5387a5ed0153bc5799ac0e72"))
			id = "2c909c8f5403df07015404243cb5004e";
		String url = "surveyController.do?doSurvey&id=" + id;
		modelMap.put("url", url);
		return new ModelAndView("cqc/survey/getLink");
	}
	
	/**
	 * 删除调查
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delSurvey")
	@ResponseBody
	public AjaxJson delSurvey(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		if(systemService.findByProperty(SurveyAnalysisHead.class, "headId", id).size() > 0)
		{
			j.setMsg("删除失败：请先删除该调查在分析管理中的所有分析。");
			return j;
		}
		
		try{
			systemService.executeSql("delete from survey_answer where interviewee_id in (select id from survey_interviewee where head_id=?)", id);
			systemService.executeSql("delete from survey_choice where question_id in (select id from survey_question where head_id=?)", id);
			systemService.executeSql("delete from survey_interviewee where head_id=?", id);
			systemService.executeSql("delete from survey_question where head_id=?", id);
			systemService.executeSql("delete from survey_admit where head_id=?", id);
			systemService.executeSql("delete from survey_reject where head_id=?", id);
			systemService.executeSql("delete from survey_head where id=?", id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			j.setMsg("删除失败：系统内部错误。");
			return j;
		}
		j.setMsg("删除调查成功。");
		return j;
	}
	
	
//	/**********************第1版***********************/
//	/**
//	 * 问卷设置 页面跳转
//	 * @param request
//	 * turn:
//	 * surveyList1 创建问卷
//	 * surveyList2 录入问题
//	 * surveyList3 编辑问题
//	 * surveyList4 预览问卷
//	 * surveyList5 发布或取消发布
//	 * surveyList6 获取文件地址
//	 * surveyList7 删除问卷
//	 * @return
//	 */
//	@RequestMapping(params = "surveyHead")
//	public ModelAndView surveyHead(HttpServletRequest request)
//	{
//		String turn = request.getParameter("turn");
//		return new ModelAndView("survey/survey/" + turn + "");
//	}
//	
//	/**
//	 * 问卷设置页面 请求数据
//	 * @param sHead
//	 * @param request
//	 * @param response
//	 * @param dataGrid
//	 */
//	@RequestMapping(params = "surveyList")
//	public void surveyList(SurveyHead sHead, HttpServletRequest request,
//				HttpServletResponse response, DataGrid dataGrid)
//	{
//		CriteriaQuery cq = new CriteriaQuery(SurveyHead.class, dataGrid);
//		cq.add();
//		systemService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
//	}
//	
//	/**
//	 * 创建问卷
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "addHead")
//	public ModelAndView addHead(HttpServletRequest request)
//	{
//		return new ModelAndView("survey/survey/addHead");
//	}
//	
//	/**
//	 * 保存问卷
//	 * @param sHead
//	 * @return
//	 * @throws ParseException 
//	 */
//	@RequestMapping(params = "saveHead")
//	@ResponseBody
//	public AjaxJson saveHead(SurveyHead sHead, HttpServletRequest request) throws ParseException
//	{
//		AjaxJson j = new AjaxJson();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date now = sdf.parse(sdf.format(new Date()));
//		
//		sHead.setCreatedTime(now);
//		sHead.setLifeState(Constant.UNUSING_STATE);
//		sHead.setPublishState(Constant.UNUSING_STATE);
//		surveyService.save(sHead);
//		
//		return j;
//	}
//	
//	/**
//	 * 问题录入
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "addQuestions")
//	public ModelAndView addQuestions(HttpServletRequest request)
//	{
//		String id = request.getParameter("id");
//		if(StringUtil.isNotEmpty(id))
//		{
//			SurveyHead sHead = surveyService.getEntity(SurveyHead.class, id);
//			if(sHead.getLifeState() == Constant.USING_STATE)
//				return new ModelAndView("survey/survey/reject").addObject("message", "该问卷已录入过问题，不能继续录入！");
//			int extraSum = sHead.getExtraSum();
//			int normalSum = sHead.getNormalSum();
//			List<SurveyQuestion> extras = new ArrayList<SurveyQuestion>();
//			List<SurveyQuestion> normals = new ArrayList<SurveyQuestion>();
//			for(int i = 0; i < extraSum; i++)
//				extras.add(new SurveyQuestion());
//			for(int i = 0; i < normalSum; i++)
//				normals.add(new SurveyQuestion());
//			List<DictionaryHead> dictionarys = surveyService.getList(DictionaryHead.class);
//			request.setAttribute("headId", id);
//			request.setAttribute("sHead", sHead);
//			request.setAttribute("extras", extras);
//			request.setAttribute("normals", normals);
//			request.setAttribute("dictionarys", dictionarys);
//		}
//		return new ModelAndView("survey/survey/addQuestions");
//	}
//	
//	/**
//	 * 保存问题及其选项
//	 * 生成html代码
//	 * @param qPage
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "saveQuestions")
//	@ResponseBody
//	public AjaxJson saveQuestions(QuestionPage qPage, HttpServletRequest request)
//	{
//		AjaxJson j = new AjaxJson();
//		List<SurveyQuestion> extras = qPage.getExtras();
//		List<SurveyQuestion> normals = qPage.getNormals();
//		String headId = "";
//		if(extras.size() > 0)
//			headId = extras.get(0).getHeadId();
//		else if(normals.size() > 0)
//			headId = normals.get(0).getHeadId();
//		try{
//			systemService.executeSql("delete from survey_choice where head_id=?", headId);
//			for(SurveyQuestion extra : extras) 
//			{
//				if(!StringUtil.isNotEmpty(extra.getId()))
//					extra.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
//				int numPerLine = extra.getFormat();
//				StringBuffer sb = new StringBuffer();
//				sb.append("<tr><th>" + extra.getQuestion() + "</th>");
//				sb.append("<td>");
//				
//				//<input type="radio" name="age" value="nianling1">18-24
//				if(extra.getTool().equals(Constant.INPUT_RADIO)) //单选框
//				{
//					if(StringUtil.isNotEmpty(extra.getDictionary()))
//					{
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", extra.getDictionary());
//						int i = 0;
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<input type=\"radio\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//					else if(StringUtil.isNotEmpty(extra.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : extra.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<input type=\"radio\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//				}
//				
//				// <select style="width: 300px" name="getcity"><option value=""></option></select>
//				else if(extra.getTool().equals(Constant.INPUT_SELECT))
//				{
//					sb.append("<select style=\"width: 300px\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\">");
//					sb.append("<option value=\"\"></option>");
//					if(StringUtil.isNotEmpty(extra.getDictionary()))
//					{
//						int i = 0;
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", extra.getDictionary());
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					else if(StringUtil.isNotEmpty(extra.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : extra.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					sb.append("</select>");
//				}
////				else if(extra.getTool().equals(Constant.INPUT_CHECKBOX))
////				{
////					if(StringUtil.isNotEmpty(extra.getDictionary()))
////					{
////						
////					}
////					else if(StringUtil.isNotEmpty(extra.getProvidedChoice()))
////					{
////						
////					}
////				}
//				else if(extra.getTool().equals(Constant.INPUT_TEXT))
//				{
//					SurveyChoice sc = new SurveyChoice();
//						sc.setHeadId(extra.getHeadId());
//						sc.setQuestionId(extra.getId());
//						sc.setChoice(null);
//						sc.setChoiceOrder(0);
//					surveyService.save(sc);	
//						
//					sb.append("<input type=\"hidden\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//							+ sc.getId() + "\">");
//					sb.append("<input type=\"text\" name=\"answerList[" + (extra.getOrderNo()-1) + "].textAnswer\" value=\"\" style=\"width: 500px\">");
//				}
//				else if(extra.getTool().equals(Constant.INPUT_TEXTAREA))
//				{
//					SurveyChoice sc = new SurveyChoice();
//						sc.setHeadId(extra.getHeadId());
//						sc.setQuestionId(extra.getId());
//						sc.setChoice(null);
//						sc.setChoiceOrder(0);
//					surveyService.save(sc);	
//						
//					sb.append("<input type=\"hidden\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//							+ sc.getId() + "\">");
//					sb.append("<input type=\"textarea\" name=\"answerList[" + (extra.getOrderNo()-1) + "].textAnswer\" value=\"\" cols=\"70\" rows=\"5\">");
//				}
//				sb.append("</td></tr>");
//				
//				extra.setNormalOrNot((short)0);
//				extra.setHtml(sb.toString());
//				systemService.saveOrUpdate(extra);
//			}
//			
//			int before = extras.size();
//			for(SurveyQuestion normal : normals)
//			{
//				if(!StringUtil.isNotEmpty(normal.getId()))
//					normal.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
//				int numPerLine = normal.getFormat();
//				StringBuffer sb = new StringBuffer();
//				sb.append("<div class=\"test\">" + normal.getQuestion() + "<br>");
//				
//				if(normal.getTool().equals(Constant.INPUT_RADIO)) //单选框
//				{
//					if(StringUtil.isNotEmpty(normal.getDictionary()))
//					{
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", normal.getDictionary());
//						int i = 0;
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<input type=\"radio\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//					else if(StringUtil.isNotEmpty(normal.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : normal.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<input type=\"radio\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//				}
//				
//				else if(normal.getTool().equals(Constant.INPUT_SELECT))
//				{
//					sb.append("<select style=\"width: 300px\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\">");
//					sb.append("<option value=\"\"></option>");
//					if(StringUtil.isNotEmpty(normal.getDictionary()))
//					{
//						int i = 0;
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", normal.getDictionary());
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					else if(StringUtil.isNotEmpty(normal.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : normal.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					sb.append("</select>");
//				}
////				else if(extra.getTool().equals(Constant.INPUT_CHECKBOX))
////				{
////					if(StringUtil.isNotEmpty(extra.getDictionary()))
////					{
////						
////					}
////					else if(StringUtil.isNotEmpty(extra.getProvidedChoice()))
////					{
////						
////					}
////				}
//				else if(normal.getTool().equals(Constant.INPUT_TEXT))
//				{
//					SurveyChoice sc = new SurveyChoice();
//						sc.setHeadId(normal.getHeadId());
//						sc.setQuestionId(normal.getId());
//						sc.setChoice(null);
//						sc.setChoiceOrder(0);
//					surveyService.save(sc);	
//						
//					sb.append("<input type=\"hidden\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
//							+ sc.getId() + "\">");
//					sb.append("<input type=\"text\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].textAnswer\" value=\"\" style=\"width: 500px\">");
//				}
////				else if(normal.getTool().equals(Constant.INPUT_TEXTAREA))
////				{
////					SurveyChoice sc = new SurveyChoice();
////						sc.setHeadId(normal.getHeadId());
////						sc.setQuestionId(normal.getId());
////						sc.setChoice(null);
////					surveyService.save(sc);	
////						
////					sb.append("<input type=\"hidden\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
////							+ sc.getId() + "\">");
////					sb.append("<input type=\"textarea\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].textAnswer\" value=\"\" cols=\"70\" rows=\"5\">");
////				}
//				sb.append("</div>");
//				
//				normal.setNormalOrNot((short)1);
//				normal.setHtml(sb.toString());
//				systemService.saveOrUpdate(normal);
//			}
//			
//			if(StringUtil.isNotEmpty(headId))
//			{
//				SurveyHead sHead = surveyService.getEntity(SurveyHead.class, headId);
//					sHead.setLifeState(Constant.USING_STATE);
//				surveyService.saveOrUpdate(sHead);
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			j.setMsg("提交失败：系统错误。");
//			return j;
//		}
//		message = "问题提交成功。";
//		j.setMsg(message);
//		return j;
//	}
//	
//	/**
//	 * 问题编辑
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "updateQuestions")
//	public ModelAndView updateQuestions(HttpServletRequest request)
//	{
//		String id = request.getParameter("id");
//		if(StringUtil.isNotEmpty(id))
//		{
//			SurveyHead sHead = surveyService.getEntity(SurveyHead.class, id);
//			if(sHead.getLifeState() == Constant.UNUSING_STATE ||
//					sHead.getPublishState() == Constant.USING_STATE)
//				return new ModelAndView("survey/survey/reject").addObject("message", "问卷无效或正在发布中，目前无法编辑。");
//			List<SurveyQuestion> extras = 
//					systemService.findHql("from SurveyQuestion where headId='"
//							+ id + "' and normalOrNot=0 order by orderNo asc");
//			List<SurveyQuestion> normals = 
//					systemService.findHql("from SurveyQuestion where headId='"
//							+ id + "' and normalOrNot=1 order by orderNo asc");
//			List<DictionaryHead> dictionarys = surveyService.getList(DictionaryHead.class);
//			request.setAttribute("sHead", sHead);
//			request.setAttribute("extras", extras);
//			request.setAttribute("normals", normals);
//			request.setAttribute("dictionarys", dictionarys);
//		}
//		return new ModelAndView("survey/survey/editQuestions");
//	}
//	
//	@RequestMapping(params = "editQuestions")
//	public AjaxJson editQuestions(QuestionPage qPage, HttpServletRequest request)
//	{
//		AjaxJson j = new AjaxJson();
//		
//		List<SurveyQuestion> extras = qPage.getExtras();
//		List<SurveyQuestion> normals = qPage.getNormals();
//		String headId = "";
//		if(extras.size() > 0)
//			headId = extras.get(0).getHeadId();
//		else if(normals.size() > 0)
//			headId = normals.get(0).getHeadId();
//		try{
//			systemService.executeSql("delete from survey_choice where head_id=?", headId);
//			for(SurveyQuestion extra : extras) 
//			{
//				extra.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
//				int numPerLine = extra.getFormat();
//				StringBuffer sb = new StringBuffer();
//				sb.append("<tr><th>" + extra.getQuestion() + "</th>");
//				sb.append("<td>");
//				
//				if(extra.getTool().equals(Constant.INPUT_RADIO)) //单选框
//				{
//					if(StringUtil.isNotEmpty(extra.getDictionary()))
//					{
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", extra.getDictionary());
//						int i = 0;
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<input type=\"radio\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//					else if(StringUtil.isNotEmpty(extra.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : extra.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<input type=\"radio\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//				}
//				
//				else if(extra.getTool().equals(Constant.INPUT_SELECT))
//				{
//					sb.append("<select style=\"width: 300px\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\">");
//					sb.append("<option value=\"\"></option>");
//					if(StringUtil.isNotEmpty(extra.getDictionary()))
//					{
//						int i = 0;
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", extra.getDictionary());
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					else if(StringUtil.isNotEmpty(extra.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : extra.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(extra.getHeadId());
//								sc.setQuestionId(extra.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					sb.append("</select>");
//				}
//				else if(extra.getTool().equals(Constant.INPUT_TEXT))
//				{
//					SurveyChoice sc = new SurveyChoice();
//						sc.setHeadId(extra.getHeadId());
//						sc.setQuestionId(extra.getId());
//						sc.setChoice(null);
//						sc.setChoiceOrder(0);
//					surveyService.save(sc);	
//						
//					sb.append("<input type=\"hidden\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//							+ sc.getId() + "\">");
//					sb.append("<input type=\"text\" name=\"answerList[" + (extra.getOrderNo()-1) + "].textAnswer\" value=\"\" style=\"width: 500px\">");
//				}
//				else if(extra.getTool().equals(Constant.INPUT_TEXTAREA))
//				{
//					SurveyChoice sc = new SurveyChoice();
//						sc.setHeadId(extra.getHeadId());
//						sc.setQuestionId(extra.getId());
//						sc.setChoice(null);
//						sc.setChoiceOrder(0);
//					surveyService.save(sc);	
//						
//					sb.append("<input type=\"hidden\" name=\"answerList[" + (extra.getOrderNo()-1) + "].choiceId\" value=\""
//							+ sc.getId() + "\">");
//					sb.append("<input type=\"textarea\" name=\"answerList[" + (extra.getOrderNo()-1) + "].textAnswer\" value=\"\" cols=\"70\" rows=\"5\">");
//				}
//				sb.append("</td></tr>");
//				
//				extra.setNormalOrNot((short)0);
//				extra.setHtml(sb.toString());
//				systemService.save(extra);
//			}
//			
//			int before = extras.size();
//			for(SurveyQuestion normal : normals)
//			{
//				normal.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
//				int numPerLine = normal.getFormat();
//				StringBuffer sb = new StringBuffer();
//				sb.append("<div class=\"test\">" + normal.getQuestion() + "<br>");
//				
//				if(normal.getTool().equals(Constant.INPUT_RADIO)) //单选框
//				{
//					if(StringUtil.isNotEmpty(normal.getDictionary()))
//					{
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", normal.getDictionary());
//						int i = 0;
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<input type=\"radio\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//					else if(StringUtil.isNotEmpty(normal.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : normal.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<input type=\"radio\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
//									+ sc.getId() + "\">" + sc.getChoice());
//							if(numPerLine != 0)
//								if(i % numPerLine == 0)
//									sb.append("<br />");
//						}
//					}
//				}
//				
//				else if(normal.getTool().equals(Constant.INPUT_SELECT))
//				{
//					sb.append("<select style=\"width: 300px\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\">");
//					sb.append("<option value=\"\"></option>");
//					if(StringUtil.isNotEmpty(normal.getDictionary()))
//					{
//						int i = 0;
//						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", normal.getDictionary());
//						for(DictionaryItem di : dItems)
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(di.getItem());
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//								
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					else if(StringUtil.isNotEmpty(normal.getProvidedChoice()))
//					{
//						int i = 0;
//						for(String choice : normal.getProvidedChoice().split("；"))
//						{
//							i++;
//							//保存选项
//							SurveyChoice sc = new SurveyChoice();
//								sc.setHeadId(normal.getHeadId());
//								sc.setQuestionId(normal.getId());
//								sc.setChoice(choice);
//								sc.setChoiceOrder(i);
//							surveyService.save(sc);	
//							
//							sb.append("<option value=\"" + sc.getId() + "\">" + sc.getChoice() + "</option>");
//						}
//					}
//					sb.append("</select>");
//				}
//				else if(normal.getTool().equals(Constant.INPUT_TEXT))
//				{
//					SurveyChoice sc = new SurveyChoice();
//						sc.setHeadId(normal.getHeadId());
//						sc.setQuestionId(normal.getId());
//						sc.setChoice(null);
//						sc.setChoiceOrder(0);
//					surveyService.save(sc);	
//						
//					sb.append("<input type=\"hidden\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
//							+ sc.getId() + "\">");
//					sb.append("<input type=\"text\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].textAnswer\" value=\"\" style=\"width: 500px\">");
//				}
//				else if(normal.getTool().equals(Constant.INPUT_TEXTAREA))
//				{
//					SurveyChoice sc = new SurveyChoice();
//						sc.setHeadId(normal.getHeadId());
//						sc.setQuestionId(normal.getId());
//						sc.setChoice(null);
//						sc.setChoiceOrder(0);
//					surveyService.save(sc);	
//						
//					sb.append("<input type=\"hidden\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].choiceId\" value=\""
//							+ sc.getId() + "\">");
//					sb.append("<input type=\"textarea\" name=\"answerList[" + (normal.getOrderNo()-1+before) + "].textAnswer\" value=\"\" cols=\"70\" rows=\"5\">");
//				}
//				sb.append("</div>");
//				
//				normal.setNormalOrNot((short)1);
//				normal.setHtml(sb.toString());
//				systemService.saveOrUpdate(normal);
//			}
//			
//			if(StringUtil.isNotEmpty(headId))
//			{
//				SurveyHead sHead = surveyService.getEntity(SurveyHead.class, headId);
//					sHead.setLifeState(Constant.USING_STATE);
//				surveyService.saveOrUpdate(sHead);
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			j.setMsg("提交失败：系统错误。");
//			return j;
//		}
//		message = "问题提交成功。";
//		j.setMsg(message);
//		
//		return j;
//	}
//	
//	/**
//	 * 预览
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "foresee")
//	public ModelAndView foresee(HttpServletRequest request)
//	{
//		String id = request.getParameter("id");
//		if(StringUtil.isNotEmpty(id))
//		{
//			SurveyHead sHead = surveyService.getEntity(SurveyHead.class, id);
//			List<SurveyQuestion> extras = 
//					systemService.findHql("from SurveyQuestion where headId='"
//							+ id + "' and normalOrNot=0 order by orderNo asc");
//			List<SurveyQuestion> normals = 
//					systemService.findHql("from SurveyQuestion where headId='"
//							+ id + "' and normalOrNot=1 order by orderNo asc");
//			request.setAttribute("sHead", sHead);
//			request.setAttribute("extras", extras);
//			request.setAttribute("normals", normals);
//		}
//		return new ModelAndView("survey/survey/survey4see");
//	}
//	
//	/**
//	 * 发布/取消发布 页面跳转
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "publish")
//	public ModelAndView publish(HttpServletRequest request)
//	{
//		String id = request.getParameter("id");
//		if(StringUtil.isNotEmpty(id))
//		{
//			SurveyHead sHead = surveyService.getEntity(SurveyHead.class, id);
//			request.setAttribute("sHead", sHead);
//		}
//		return new ModelAndView("survey/survey/publish");
//	}
//	
//	/**
//	 * 保存发布状态
//	 * @param sHead
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "savePublish")
//	@ResponseBody
//	public AjaxJson savePublish(SurveyHead sHead, HttpServletRequest request)
//	{
//		AjaxJson j = new AjaxJson();
//		try{
//			SurveyHead sh = surveyService.getEntity(SurveyHead.class, sHead.getId());
//			if(sh.getPublishState() == sHead.getPublishState())
//			{
//				j.setMsg("发布状态未改动。");
//				return j;
//			}
//			if(sh.getPublishState() == Constant.UNUSING_STATE)
//			{
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				sh.setPublishTime(sdf.parse(sdf.format(new Date())));
//			}
//				sh.setPublishState(sHead.getPublishState());
//			surveyService.saveOrUpdate(sh);
//			if(sh.getPublishState() == Constant.USING_STATE)
//				j.setMsg("发布成功。");
//			else
//				j.setMsg("取消发布成功。");
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			j.setMsg("操作失败：系统内部错误。");
//		}
//		return j;
//	}
//	
//	/**
//	 * 获取问卷地址
//	 * @param modelMap
//	 * @param id
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "getAddress")
//	public ModelAndView popmenulink(ModelMap modelMap, @RequestParam String id, 
//									HttpServletRequest request)
//	{
//		String url = "surveyController.do?doSurvey&id=" + id;
//		modelMap.put("url", url);
//		return new ModelAndView("survey/survey/getLink");
//	}
//	
//	/**
//	 * 问卷 页面跳转
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "doSurvey")
//	public ModelAndView doSurvey(HttpServletRequest request)
//	{
//		String id = request.getParameter("id");
//		if(StringUtil.isNotEmpty(id))
//		{
//			SurveyHead sHead = surveyService.getEntity(SurveyHead.class, id);
//			List<SurveyQuestion> extras = 
//					systemService.findHql("from SurveyQuestion where headId='"
//							+ id + "' and normalOrNot=0 order by orderNo asc");
//			List<SurveyQuestion> normals = 
//					systemService.findHql("from SurveyQuestion where headId='"
//							+ id + "' and normalOrNot=1 order by orderNo asc");
//			request.setAttribute("sHead", sHead);
//			request.setAttribute("extras", extras);
//			request.setAttribute("normals", normals);
//		}
//		return new ModelAndView("survey/survey/doSurvey");
//	}
//	
//	/**
//	 * 保存问卷答案
//	 * @param aPage
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params = "saveAnswers")
//	public ModelAndView saveAnswers(AnswerPage aPage, HttpServletRequest request)
//	{
//		String headId = request.getParameter("sHeadId");
//		String ip = IpUtil.getIpAddr(request);
//		List<SurveyAnswer> answerList = aPage.getAnswerList();
//		
//		//验证ip是否重复提交
//		if(systemService.findHql("from SurveyInterviewee where headId='" + headId + "' and ip='" + ip + "'").size() > 0)
//			return new ModelAndView("survey/survey/surveyResult").addObject("message", "抱歉，您已经提交过一次了，不允许重复提交。");
//		
//		SurveyInterviewee interviewee = new SurveyInterviewee();
//			interviewee.setHeadId(headId);
//			interviewee.setIp(ip);
//			interviewee.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
//		
//		try{
//			for(SurveyAnswer answer : answerList)
//			{
//				answer.setIntervieweeId(interviewee.getId());
//				surveyService.save(answer);
//			}	
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			message = "问卷提交失败：系统内部错误。";
//			return new ModelAndView("survey/survey/surveyResult").addObject("message", message);
//		}
//		surveyService.save(interviewee);
//		message = "问卷提交成功：感谢您的参与，祝您生活愉快！";
//		return new ModelAndView("survey/survey/surveyResult").addObject("message", message);
//	}
//
//	@RequestMapping(params = "delSurvey")
//	@ResponseBody
//	public AjaxJson delSurvey(HttpServletRequest request)
//	{
//		AjaxJson j = new AjaxJson();
//		String id = request.getParameter("id");
//		
//		try{
//			systemService.executeSql("delete from survey_answer where choice_id in (select id from survey_choice where head_id=?)", id);
//			systemService.executeSql("delete from survey_choice where head_id=?", id);
//			systemService.executeSql("delete from survey_interviewee where head_id=?", id);
//			systemService.executeSql("delete from survey_question where head_id=?", id);
//			systemService.executeSql("delete from survey_head where id=?", id);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			j.setMsg("删除失败：系统内部错误。");
//			return j;
//		}
//		j.setMsg("删除问卷成功。");
//		return j;
//	}
}
