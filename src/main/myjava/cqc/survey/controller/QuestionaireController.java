package cqc.survey.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cqc.survey.model.Constant;
import cqc.survey.model.DictionaryHead;
import cqc.survey.model.DictionaryItem;
import cqc.survey.model.QuestionaireBlock;
import cqc.survey.model.QuestionaireMain;
import cqc.survey.model.QuestionaireQuestion;
import cqc.survey.model.QuestionaireQuestionPage;
import cqc.survey.model.SurveyHead;
import cqc.survey.service.SurveyService;

/**
 * 问卷管理
 * @author Kinho
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/questionaireController")
public class QuestionaireController extends BaseController
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
	 * 常规页面跳转
	 * @param request
	 * turn:
	 * create - 创建问卷 
	 * 	addQuestionaire - 创建问卷 弹窗
	 * edit - 编辑问卷
	 * foresee - 预览问卷
	 * delete - 删除问卷
	 * @return
	 */
	@RequestMapping(params = "questionaire")
	public ModelAndView surveyHead(HttpServletRequest request)
	{
		String turn = request.getParameter("turn");
		return new ModelAndView("cqc/questionaire/" + turn + "");
	}
	
	/**
	 * 返回questionaire_main数据
	 * @param qm
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "questionaires")
	public void questionaires(QuestionaireMain qm, HttpServletRequest request,
				HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(QuestionaireMain.class, dataGrid);
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 保存问卷
	 * @param qm
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(params = "saveHead")
	@ResponseBody
	public AjaxJson saveHead(QuestionaireMain qm, HttpServletRequest request) throws ParseException
	{
		AjaxJson j = new AjaxJson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
		qm.setUpdateTime(sdf.parse(sdf.format(new Date())));
		surveyService.save(qm);
		return j;
	}
	
	/**
	 * 进入板块管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "blockManagement")
	public ModelAndView blockManagement(HttpServletRequest request)
	{
		String qmid = request.getParameter("id");
		return new ModelAndView("cqc/questionaire/blockManagement").addObject("qmid", qmid);
	}
	
	/**
	 * 返回questionaire_block数据
	 * @param qb
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "blocks")
	public void blocks(QuestionaireBlock qb, HttpServletRequest request,
				HttpServletResponse response, DataGrid dataGrid)
	{
		String qmid = request.getParameter("qmid");
		CriteriaQuery cq = new CriteriaQuery(QuestionaireBlock.class, dataGrid);
			cq.eq("mainId", qmid);
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 新建板块 弹窗页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addBlock")
	public ModelAndView addBlock(HttpServletRequest request)
	{
		String qmid = request.getParameter("qmid");
		int size = 0;
		if(StringUtil.isNotEmpty(qmid))
		{
			List<QuestionaireBlock> qbs = systemService.findByProperty(QuestionaireBlock.class, "mainId", qmid);
			size = qbs.size() + 1;
		}
		else
			return new ModelAndView("cqc/errorPage").addObject("message", "很抱歉，系统内部出现错误。");
		ModelAndView md = new ModelAndView("cqc/questionaire/addBlock");
			md.addObject("qmid", qmid);
			md.addObject("blockOrder", size);
		return md;
	}
	
	/**
	 * 保存板块信息
	 * @param qb
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveBlock")
	@ResponseBody
	public AjaxJson saveBlock(QuestionaireBlock qb, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
			qb.setQuestionState(Constant.UNUSING_STATE);
		if(qb.getPrimaryNum() == 0)
			qb.setQuestionState(Constant.USING_STATE);
		surveyService.save(qb);
		return j;
	}
	
	/**
	 * 编辑版块 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "editBlock")
	public ModelAndView editBlock(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		QuestionaireBlock qb = systemService.getEntity(QuestionaireBlock.class, id);
		return new ModelAndView("cqc/questionaire/editBlock").addObject("qb", qb);
	}
	
	/**
	 * 保存 编辑板块
	 * @param qb
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping(params = "saveBlock2")
	@ResponseBody
	public AjaxJson saveBlock2(QuestionaireBlock qb, HttpServletRequest request,
				HttpServletResponse response, DataGrid dataGrid)
	{
		AjaxJson j = new AjaxJson();
		surveyService.saveOrUpdate(qb);
		return j;
	}
	
	/**
	 * 录入全部问题 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addQuestions")
	public ModelAndView addQuestions(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		QuestionaireBlock qb = surveyService.getEntity(QuestionaireBlock.class, id);
		int num = qb.getPrimaryNum();
		String blockId = qb.getId();
		String mainId = qb.getMainId();
		List<QuestionaireQuestion> qqList = new ArrayList<QuestionaireQuestion>();
		for(int i = 0; i < num; i++)
			qqList.add(new QuestionaireQuestion());
		List<DictionaryHead> dictionarys = surveyService.getList(DictionaryHead.class);
		request.setAttribute("num", num);
		request.setAttribute("qqList", qqList);
		request.setAttribute("dictionarys", dictionarys);
		request.setAttribute("blockId", blockId);
		request.setAttribute("mainId", mainId);
		return new ModelAndView("cqc/questionaire/addQuestions");
	}
	
	/**
	 * 保存全部问题
	 * @param qqList
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveQuestions")
	@ResponseBody
	public AjaxJson saveQuestions(QuestionaireQuestionPage qqp, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		List<QuestionaireQuestion> qqList = qqp.getQqList();
		String bid = qqList.get(0).getBlockId();
		for(QuestionaireQuestion qq : qqList)
			surveyService.save(qq);
		QuestionaireBlock qb = systemService.getEntity(QuestionaireBlock.class, bid);
			qb.setQuestionState(Constant.USING_STATE);
		systemService.saveOrUpdate(qb);
		return j;
	}
	
	/**
	 * 问题管理 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "questionManagement")
	public ModelAndView questionManagement(HttpServletRequest request)
	{
		String bid = request.getParameter("id");
		return new ModelAndView("cqc/questionaire/questionManagement").addObject("bid", bid);
	}
	
	/**
	 * 返回 板块问题数据
	 * @param qq
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "questions")
	public void questions(QuestionaireQuestion qq, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid)
	{
		String bid = request.getParameter("bid");
		CriteriaQuery cq = new CriteriaQuery(QuestionaireQuestion.class, dataGrid);
			cq.eq("blockId", bid);
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 补充录入问题 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "anotherQuestion")
	public ModelAndView anotherQuestion(HttpServletRequest request)
	{
		String bid = request.getParameter("bid");
		if(StringUtil.isNotEmpty(bid))
		{
			QuestionaireBlock qb = surveyService.getEntity(QuestionaireBlock.class, bid);
			int num = qb.getPrimaryNum() + 1;
			List<DictionaryHead> dictionarys = systemService.getList(DictionaryHead.class);
			request.setAttribute("num", num);
			request.setAttribute("dictionarys", dictionarys);
			request.setAttribute("mainId", qb.getMainId());
			request.setAttribute("blockId", qb.getId());
		}
		else
			return new ModelAndView("cqc/errorPage").addObject("message", "很抱歉，系统内部出现错误。");
		return new ModelAndView("cqc/questionaire/anotherQuestion");
	}
	
	/**
	 * 保存 补充录入问题
	 * @param qq
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveAnotherQuestion")
	@ResponseBody
	public AjaxJson saveAnotherQuestion(QuestionaireQuestion qq, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		
		surveyService.save(qq);
		
		QuestionaireBlock qb = surveyService.getEntity(QuestionaireBlock.class, qq.getBlockId());
		int num = qb.getPrimaryNum() + 1;
			qb.setPrimaryNum(num);
		systemService.saveOrUpdate(qb);
		return j;
	}
	
	/**
	 * 编辑问题 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "editQuestion")
	public ModelAndView editQuestion(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id))
		{
			QuestionaireQuestion qq = surveyService.getEntity(QuestionaireQuestion.class, id);
			List<DictionaryHead> dictionarys = systemService.getList(DictionaryHead.class);
			request.setAttribute("dictionarys", dictionarys);
			request.setAttribute("qq", qq);
		}
		else
			return new ModelAndView("cqc/errorPage").addObject("message", "很抱歉，系统内部出现错误。");
		return new ModelAndView("cqc/questionaire/editQuestion");
	}
	
	/**
	 * 保存编辑问题
	 * @param qq
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveQuestion")
	@ResponseBody
	public AjaxJson saveQuestion(QuestionaireQuestion qq, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		surveyService.saveOrUpdate(qq);
		return j;
	}
	
	/**
	 * 编辑问卷 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "editQuestionaire")
	public ModelAndView editQuestionaire(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id))
		{
			QuestionaireMain qm = surveyService.getEntity(QuestionaireMain.class, id);
			request.setAttribute("qm", qm);
		}
		else
			return new ModelAndView("cqc/errorPage").addObject("message", "很抱歉，系统内部出现错误。");
		return new ModelAndView("cqc/questionaire/editQuestionaire");
	}
	
	/**
	 * 保存编辑问卷
	 * @param qm
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(params = "saveEditQuestionaire")
	@ResponseBody
	public AjaxJson saveEditQuestionaire(QuestionaireMain qm, HttpServletRequest request) throws ParseException
	{
		AjaxJson j = new AjaxJson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		qm.setUpdateTime(sdf.parse(sdf.format(new Date())));
		surveyService.saveOrUpdate(qm);
		return j;
	}
	
	/**
	 * 删除问题
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delQuestion")
	@ResponseBody
	public AjaxJson delQuestion(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String blockId = request.getParameter("blockId");
		String orderNo = request.getParameter("orderNo");
		systemService.executeSql("delete from questionaire_question where id=?", id);
		List<QuestionaireQuestion> qqList = systemService.findHql("from QuestionaireQuestion where orderNo>? and blockId=?", 
												Integer.parseInt(orderNo), blockId);
		for(QuestionaireQuestion qq : qqList)
		{
			int no = qq.getOrderNo() - 1;
			qq.setOrderNo(no);
			systemService.saveOrUpdate(qq);
		}
		QuestionaireBlock qb = surveyService.getEntity(QuestionaireBlock.class, blockId);
		int num = qb.getPrimaryNum() - 1;
			qb.setPrimaryNum(num);
		surveyService.saveOrUpdate(qb);
		return j;
	}
	
	/**
	 * 删除问卷
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delQuestionaire")
	@ResponseBody
	public AjaxJson delQuestionaire(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		
		try{
			systemService.executeSql("delete from questionaire_question where main_id=?", id);
			systemService.executeSql("delete from questionaire_block where main_id=?", id);
			systemService.executeSql("delete from questionaire_main where id=?", id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			j.setMsg("删除失败：系统内部错误。");
			return j;
		}
		j.setMsg("删除问卷成功。");
		return j;
	}
	
	@RequestMapping(params = "delBlock")
	@ResponseBody
	public AjaxJson delBlock(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		
		try{
			systemService.executeSql("delete from questionaire_question where block_id=?", id);
			systemService.executeSql("delete from questionaire_block where id=?", id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			j.setMsg("删除失败：系统内部错误。");
			return j;
		}
		j.setMsg("删除板块成功。");
		return j;
	}
	
	/**
	 * 预览问卷
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goForesee")
	public ModelAndView goForesee(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		
		String head = "";
		String descriptionHtml = "";
		List<String> htmlList = new ArrayList<String>();
		
		QuestionaireMain qm = systemService.getEntity(QuestionaireMain.class, id);
		head = qm.getHead();
		
		if(StringUtil.isNotEmpty(qm.getDescription()))
			descriptionHtml = "<div class=\"test\">" + qm.getDescription() + "</div>";
		else 
			descriptionHtml = "<div class=\"test\"></div>";
		
		List<QuestionaireBlock> qbList = systemService.findHql("from QuestionaireBlock where mainId=? order by blockOrder asc", id);
		if(qbList.size() <= 0)
			return new ModelAndView("cqc/survey/surveyResult").addObject("message", "预览失败：问卷没有板块！");
		int questionOrder = 0;
		String description = null;
		for(QuestionaireBlock qb : qbList)
		{
			if(qb.getQuestionState() == Constant.UNUSING_STATE) //有板块没有全部录入问题
				return new ModelAndView("cqc/survey/surveyResult").addObject("message", "预览失败：问卷的" + qb.getBlockOrder() + "没有录入问题！");
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
					int numPerLine = qq.getFormat();
					
					StringBuffer html = new StringBuffer();
					if(i == 0)
					{
						questionOrder++;
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
	  							html.append("<input type=\"radio\" name=\"answerList[" + (questionOrder - 1) + "].choiceId\" value=\"\">" + di.getItem());
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
	  							html.append("<input type=\"radio\" name=\"answerList[" + (questionOrder - 1) + "].choiceId\" value=\"\">" + choice);
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
	  						List<DictionaryItem> dItems = systemService.findByProperty(DictionaryItem.class, "headId", qq.getDictionary());
	  						for(DictionaryItem di : dItems)
	  							html.append("<option value=\"\">" + di.getItem() + "</option>");
	  					}
	  					else if(StringUtil.isNotEmpty(qq.getProvidedChoice()))
	  					{
	  						for(String choice : qq.getProvidedChoice().split("；"))
	  							html.append("<option value=\"\">" + choice + "</option>");
	  					}
	  					html.append("</select>");
	  				}
					
	  				else if(qq.getTool().equals(Constant.INPUT_TEXT))
	  				{
	  					html.append("<input type=\"hidden\" name=\"answerList[" + (questionOrder - 1) + "].choiceId\" value=\"\">");
	  					html.append("<input type=\"text\" name=\"answerList[" + (questionOrder - 1) + "].textAnswer\" value=\"\" style=\"width: 500px\">");
	  				}
	    				
	  				html.append("</div>");
					
	  					htmlList.add(html.toString());
					i++;
				}
			}
		}		
		request.setAttribute("head", head);
		request.setAttribute("descriptionHtml", descriptionHtml);
		request.setAttribute("htmlList", htmlList);
		return new ModelAndView("cqc/questionaire/goForesee");
	}
}
