package cqc.survey.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cqc.survey.model.Attribute;
import cqc.survey.model.Bar;
import cqc.survey.model.PointAnalysis;
import cqc.survey.model.SurveyAnalysisHead;
import cqc.survey.model.SurveyAnalysisQuestion;
import cqc.survey.model.SurveyAnalysisQuestionPage;
import cqc.survey.model.SurveyChoice;
import cqc.survey.model.SurveyExcel;
import cqc.survey.model.SurveyHead;
import cqc.survey.model.SurveyInterviewee;
import cqc.survey.model.SurveyQuestion;
import cqc.survey.service.SurveyService;

/**
 * 调查管理
 * @author Kinho
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/statisticController")
public class StatisticController extends BaseController
{
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SurveyService surveyService;
	
	
	@RequestMapping(params = "statistic")
	public ModelAndView statistic(HttpServletRequest request)
	{
		return new ModelAndView("cqc/statistic/statistic");
	}
	
	/**
	 * 每日提交人次总数统计
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "statistic1")
	public ModelAndView statistic1(HttpServletRequest request)
	{
		String SID = request.getParameter("SID");
		request.setAttribute("SID", SID);
		return new ModelAndView("cqc/statistic/statistic1_1");
	}
	
	@RequestMapping(params = "statistic1_2")
	public ModelAndView statistic1_2(HttpServletRequest request)
	{
		String SID = request.getParameter("SID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		request.setAttribute("SID", SID);
		request.setAttribute("date_begin", date_begin);
		request.setAttribute("date_end", date_end);
		return new ModelAndView("cqc/statistic/statistic1_2");
	}
	
	@RequestMapping(params = "getC1")
	@ResponseBody
	public Map<String, Object> getC1(HttpServletRequest request,HttpServletResponse response) throws ParseException
	{
		String SID = request.getParameter("SID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<SurveyInterviewee> siList = 
				systemService.findHql("from SurveyInterviewee where headId=? and (submitTime between ? and ?) order by submitTime asc", 
						SID, sdf.parse(date_begin), sdf.parse(date_end));
		
		List<Attribute> aList = new ArrayList<Attribute>();
		
		Attribute attribute = new Attribute();
		int num = 0;
		if(siList.size() <= 0)
			attribute.setKey("(查无数据)");
		else
		{
			String lastDate = sdf.format(siList.get(0).getSubmitTime());
			attribute.setKey(lastDate);
			for(SurveyInterviewee s : siList)
			{
				String thisDate = sdf.format(s.getSubmitTime());
				if(thisDate.equals(lastDate))
				{
					num++;
				}
				else
				{
					attribute.setValue(String.valueOf(num));
					aList.add(attribute);
					
					lastDate = thisDate;
					num = 1;
					attribute = new Attribute();
						attribute.setKey(lastDate);
				}
			}
		}
		attribute.setValue(String.valueOf(num));
		aList.add(attribute);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> xname = new ArrayList<String>();
		List<String> yvalue = new ArrayList<String>();

		for(Attribute a : aList)
		{
			xname.add(a.getKey().replace("-", "\n-"));
			yvalue.add(a.getValue());
		}
		map.put("xname", xname);
		map.put("yvalue", yvalue);
		return map;
	}
	
	@RequestMapping(params = "statistic1_3")
	public ModelAndView statistic1_3(HttpServletRequest request) throws ParseException
	{
		String SID = request.getParameter("SID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<SurveyInterviewee> siList = 
				systemService.findHql("from SurveyInterviewee where headId=? and (submitTime between ? and ?) order by submitTime asc", 
						SID, sdf.parse(date_begin), sdf.parse(date_end));
		
		List<Attribute> aList = new ArrayList<Attribute>();
		
		Attribute attribute = new Attribute();
		int num = 0;
		if(siList.size() <= 0)
			attribute.setKey("(查无数据)");
		else
		{
			String lastDate = sdf.format(siList.get(0).getSubmitTime());
			attribute.setKey(lastDate);
			for(SurveyInterviewee s : siList)
			{
				String thisDate = sdf.format(s.getSubmitTime());
				if(thisDate.equals(lastDate))
				{
					num++;
				}
				else
				{
					attribute.setValue(String.valueOf(num));
					aList.add(attribute);
					
					lastDate = thisDate;
					num = 1;
					attribute = new Attribute();
						attribute.setKey(lastDate);
				}
			}
		}
		attribute.setValue(String.valueOf(num));
		aList.add(attribute);
		
		request.setAttribute("SID", SID);
		request.setAttribute("date_begin", date_begin);
		request.setAttribute("date_end", date_end);
		request.setAttribute("aList", aList);
		return new ModelAndView("cqc/statistic/statistic1_3");
	}
	
	/**
	 * 特定问题回答情况统计
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "statistic2")
	public ModelAndView statistic2(HttpServletRequest request)
	{
		String SID = request.getParameter("SID");
		List<SurveyQuestion> sList = 
				systemService.findHql("from SurveyQuestion where headId=? order by orderNo asc", SID);
		
		request.setAttribute("SID", SID);
		request.setAttribute("sList", sList);
		return new ModelAndView("cqc/statistic/statistic2_1");
	}
	
	@RequestMapping(params = "statistic2_2")
	public ModelAndView statistic2_2(HttpServletRequest request)
	{
		String SID = request.getParameter("SID");
		String QID = request.getParameter("QID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SurveyQuestion q = systemService.getEntity(SurveyQuestion.class, QID);
		
		request.setAttribute("SID", SID);
		request.setAttribute("QID", QID);
		request.setAttribute("date_begin", date_begin);
		request.setAttribute("date_end", date_end);
		request.setAttribute("q", q);
		return new ModelAndView("cqc/statistic/statistic2_2");
	}
	
	@RequestMapping(params = "getC2")
	@ResponseBody
	public Map<String, Object> getC2(HttpServletRequest request,HttpServletResponse response) throws ParseException
	{
		String QID = request.getParameter("QID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> xname = new ArrayList<String>();
		List<Bar> yvalue = new ArrayList<Bar>();
		List<SurveyChoice> choices = 
				systemService.findByProperty(SurveyChoice.class, "questionId", QID);
		Bar b = new Bar();
		List<String> data = new ArrayList<String>();
		boolean split = false;
		if(choices.size() > 5)
			split = true;
		
		for(SurveyChoice c : choices)
		{
			String x = "";
			if(StringUtil.isNotEmpty(c.getChoice()))
				x = c.getChoice();
			else
				x = "(主观题)";
			List<String> whatever = 
					systemService.findHql("select a.id from SurveyAnswer a, SurveyInterviewee i where a.intervieweeId=i.id and a.choiceId=? and (i.submitTime between ? and ?)", 
							c.getId(), sdf.parse(date_begin), sdf.parse(date_end));
			int size = whatever.size();
			data.add(String.valueOf(size));
			if(split)
				x = x.substring(0, x.length()/2) + "\n" + x.substring(x.length()/2, x.length());
			xname.add(x);
		}
		b.setName("提交人次总数");
		b.setData(data);
		b.setType("bar");
		yvalue.add(b);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("xname", xname);
		map.put("yvalue", yvalue);
		return map;
	}
	
	@RequestMapping(params = "statistic2_3")
	public ModelAndView statistic2_3(HttpServletRequest request) throws ParseException
	{
		String SID = request.getParameter("SID");
		String QID = request.getParameter("QID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		SurveyQuestion q = systemService.getEntity(SurveyQuestion.class, QID);
		
		List<Attribute> aList = new ArrayList<Attribute>();
		List<SurveyChoice> choices = 
				systemService.findByProperty(SurveyChoice.class, "questionId", QID);
		for(SurveyChoice c : choices)
		{
			Attribute a = new Attribute();
			if(StringUtil.isNotEmpty(c.getChoice()))
				a.setKey(c.getChoice());
			else
				a.setKey("(主观题)");
			List<String> whatever = 
					systemService.findHql("select a.id from SurveyAnswer a, SurveyInterviewee i where a.intervieweeId=i.id and a.choiceId=? and (i.submitTime between ? and ?)", 
							c.getId(), sdf.parse(date_begin), sdf.parse(date_end));
			int size = whatever.size();
				a.setValue(String.valueOf(size));
			aList.add(a);
		}
		
		request.setAttribute("SID", SID);
		request.setAttribute("QID", QID);
		request.setAttribute("date_begin", date_begin);
		request.setAttribute("date_end", date_end);
		request.setAttribute("q", q);
		request.setAttribute("aList", aList);
		return new ModelAndView("cqc/statistic/statistic2_3");
	}
	
	@RequestMapping(params = "excelPage")
	public ModelAndView excelPage(HttpServletRequest request)
	{
		String SID = request.getParameter("SID");
		request.setAttribute("SID", SID);
		return new ModelAndView("cqc/statistic/excelPage");
	}
	
	@RequestMapping(params = "excelExport")
	public void excelExport(HttpServletRequest request, HttpServletResponse response)
	{
		String SID = request.getParameter("SID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try {
			// 根据浏览器进行转码，使其支持中文文件名
			String browse = BrowserUtils.checkBrowse(request);
			if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) 
			{
				response.setHeader("content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode("调查结果导出",
										"UTF-8") + ".xls");
			} 
			else 
			{
				String newtitle = new String("调查结果导出".getBytes("UTF-8"),
						"ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<SurveyExcel> excels = systemService.findHql("SELECT new cqc.survey.model.SurveyExcel(q.orderNo, c.choiceOrder, a.textAnswer) " +
			"FROM SurveyAnswer a, SurveyChoice c, SurveyQuestion q, SurveyInterviewee i " +
			"WHERE a.choiceId=c.id AND c.questionId=q.id AND a.intervieweeId=i.id " +
			"AND q.headId=? AND (i.submitTime between ? and ?) ORDER BY a.intervieweeId", SID, sdf.parse(date_begin), sdf.parse(date_end));
			
			List<SurveyQuestion> qList = systemService.findByProperty(SurveyQuestion.class, "headId", SID);
			int questionSum = qList.size();
			
			System.out.println(excels.size() + "...................");
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			
			CellStyle titleStyle = workbook.createCellStyle();
				titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
				
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			HSSFRichTextString titleText = new HSSFRichTextString("       ");
				cell.setCellValue(titleText);
			for(int i = 1; i <= questionSum; i++)
			{
				titleText = new HSSFRichTextString("Q" + i); 
				cell = row.createCell(i);
					cell.setCellValue(titleText);
					cell.setCellStyle(titleStyle);
			}
			
			int cellOrder = 0;
			int line = 0;
			for(int i = 0; i < excels.size(); i++)
			{
				SurveyExcel e = excels.get(i);
				
				if(cellOrder == 0)
				{
					line++;
					titleText = new HSSFRichTextString(String.valueOf(line));
					row = sheet.createRow(line);
					cell = row.createCell(cellOrder);
						cell.setCellValue(titleText);
						cell.setCellStyle(titleStyle);
					
					cellOrder++;
					if(cellOrder != e.getOrderNo())
					{
						titleText = new HSSFRichTextString("*");
						cell = row.createCell(cellOrder);
							cell.setCellValue(titleText);
							cell.setCellStyle(titleStyle);
						i--;
					}
					else
					{
						if(e.getChoiceOrder() == 0)
						{
							if(StringUtil.isNotEmpty(e.getTextAnswer()))
								titleText = new HSSFRichTextString(e.getTextAnswer());
							else
								titleText = new HSSFRichTextString(" ");
						}
						else
							titleText = new HSSFRichTextString(String.valueOf(e.getChoiceOrder()));
						cell = row.createCell(cellOrder);
							cell.setCellValue(titleText);
							cell.setCellStyle(titleStyle);
					}
					continue;
				}
				
				cellOrder++;
				if(cellOrder == questionSum + 1)
				{
					cellOrder = 0;
					i--;
					continue;
				}
				if(cellOrder != e.getOrderNo())
				{
					titleText = new HSSFRichTextString("*");
					cell = row.createCell(cellOrder);
						cell.setCellValue(titleText);
						cell.setCellStyle(titleStyle);
					i--;
				}
				else
				{
					if(e.getChoiceOrder() == 0)
					{
						if(StringUtil.isNotEmpty(e.getTextAnswer()))
							titleText = new HSSFRichTextString(e.getTextAnswer());
						else
							titleText = new HSSFRichTextString(" ");
					}
					else
						titleText = new HSSFRichTextString(String.valueOf(e.getChoiceOrder()));
					cell = row.createCell(cellOrder);
						cell.setCellValue(titleText);
						cell.setCellStyle(titleStyle);
				}
			}
			
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建 分析
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "create")
	public ModelAndView create(HttpServletRequest request)
	{
		return new ModelAndView("cqc/analysis/create");
	}
	
	@RequestMapping(params = "analysis")
	public void analysis(SurveyAnalysisHead sah, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(SurveyAnalysisHead.class, dataGrid);
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "addAnalysis")
	public ModelAndView addAnalysis(HttpServletRequest request)
	{
		List<SurveyHead> sList = systemService.getList(SurveyHead.class);
		request.setAttribute("sList", sList);
		return new ModelAndView("cqc/analysis/addAnalysis");
	}
	
	@RequestMapping(params = "saveAnalysis")
	@ResponseBody
	public AjaxJson saveAnalysis(HttpServletRequest request) throws ParseException
	{
		AjaxJson j = new AjaxJson();
		String headId = request.getParameter("headId");
		String remark = request.getParameter("remark");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SurveyHead s = surveyService.getEntity(SurveyHead.class, headId);
		
		SurveyAnalysisHead sah = new SurveyAnalysisHead();
			sah.setHeadId(headId);
			sah.setHead(s.getHead());
			sah.setSettingState((short) 0);
			sah.setCreatedDate(sdf.parse(sdf.format(new Date())));
			sah.setRemark(remark);
		systemService.save(sah);
			
		List<SurveyQuestion> qList = systemService.findHql("from SurveyQuestion where headId=? order by orderNo asc", headId);
		for(SurveyQuestion sq : qList)
		{
			List<SurveyChoice> sList = systemService.findByProperty(SurveyChoice.class, "questionId", sq.getId());
			SurveyAnalysisQuestion saq = new SurveyAnalysisQuestion();
				saq.setAnalysisId(sah.getId());
				saq.setQuestionId(sq.getId());
				saq.setOrderNo(sq.getOrderNo());
				saq.setQuestion(sq.getQuestion());
				saq.setChoiceNum(sList.size());
				saq.setWeight(1);
				saq.setMarkState((short)1);
			surveyService.save(saq);
		}
		
		return j;
	}
	
	/**
	 * 配置 分析
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "settings")
	public ModelAndView settings(HttpServletRequest request)
	{
		return new ModelAndView("cqc/analysis/settings");
	}
	
	@RequestMapping(params = "setting")
	public ModelAndView setting(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		
		List<SurveyAnalysisQuestion> saqList = systemService.findHql("from SurveyAnalysisQuestion where analysisId=? order by orderNo asc", id);
		request.setAttribute("saqList", saqList);
		request.setAttribute("num", saqList.size());
		
		return new ModelAndView("cqc/analysis/setting");
	}
	
	@RequestMapping(params = "saveSetting")
	@ResponseBody
	public AjaxJson saveSetting(SurveyAnalysisQuestionPage page, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		List<SurveyAnalysisQuestion> saqList = page.getSaqList();
		for(SurveyAnalysisQuestion saq : saqList)
			systemService.saveOrUpdate(saq);
		String id = saqList.get(0).getAnalysisId();
		SurveyAnalysisHead sah = surveyService.getEntity(SurveyAnalysisHead.class, id);
			sah.setSettingState((short)1);
		surveyService.saveOrUpdate(sah);
		
		j.setMsg("分析配置成功。");
		return j;
	}
	
	/**
	 * 执行 分析
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "execute")
	public ModelAndView execute(HttpServletRequest request)
	{
		return new ModelAndView("cqc/analysis/execute");
	}
	
	@RequestMapping(params = "execute1_1")
	public ModelAndView execute1_1(HttpServletRequest request)
	{
		String AID = request.getParameter("id");
		String SID = request.getParameter("headId");
		
		List<SurveyQuestion> sList = 
				systemService.findHql("from SurveyQuestion where headId=? order by orderNo asc", SID);
		
		request.setAttribute("AID", AID);
		request.setAttribute("SID", SID);
		request.setAttribute("sList", sList);
		return new ModelAndView("cqc/analysis/execute1_1");
	}
	
	@RequestMapping(params = "execute1_2")
	public ModelAndView execute1_2(HttpServletRequest request)
	{
		String SID = request.getParameter("SID");
		String AID = request.getParameter("AID");
		String QID = request.getParameter("QID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SurveyQuestion q = systemService.getEntity(SurveyQuestion.class, QID);
		
		request.setAttribute("SID", SID);
		request.setAttribute("AID", AID);
		request.setAttribute("QID", QID);
		request.setAttribute("date_begin", date_begin);
		request.setAttribute("date_end", date_end);
		request.setAttribute("q", q);
		return new ModelAndView("cqc/analysis/execute1_2");
	}
	
	@RequestMapping(params = "getC3")
	@ResponseBody
	public Map<String, Object> getC3(HttpServletRequest request) throws ParseException
	{
//		String SID = request.getParameter("SID");
		String AID = request.getParameter("AID");
		String QID = request.getParameter("QID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		SurveyQuestion sq = surveyService.getEntity(SurveyQuestion.class, QID);
		
		List<SurveyAnalysisQuestion> saqList = surveyService.findHql("from SurveyAnalysisQuestion where analysisId=? order by orderNo asc", AID);
		
		List<SurveyChoice> scList = surveyService.findByProperty(SurveyChoice.class, "questionId", QID);
		
		List<PointAnalysis> paList = systemService.findHql("SELECT new cqc.survey.model.PointAnalysis(aq.orderNo, aq.weight, c.choiceOrder, aq.markState, aq.mark) " +
				"FROM SurveyAnswer a, SurveyChoice c, SurveyAnalysisQuestion aq, SurveyInterviewee i " +
				"WHERE a.choiceId=c.id AND c.questionId=aq.questionId AND a.intervieweeId=i.id " +
				"AND aq.analysisId=? AND (i.submitTime between ? and ?) ORDER BY a.intervieweeId", AID, sdf.parse(date_begin), sdf.parse(date_end));
		
		final int questionSum = saqList.size();
		
		final int aim_q_order = sq.getOrderNo();
		
		final int cnum = scList.size();
		List<BigDecimal> times = new ArrayList<BigDecimal>();
		List<BigDecimal> points = new ArrayList<BigDecimal>();
		for(int i = 0; i <= cnum; i++) // i <= cnum, 故index直接对应choiceOrder,而当是主观题时index=0时对应了主观题
		{
			BigDecimal innit = new BigDecimal(0);
			times.add(innit);
			points.add(innit);
		}
		
		int questionOrder = 0;
		int this_choice = 0;
		boolean abandon = false;
		BigDecimal point = new BigDecimal(0);
		for(int i = 0; i < paList.size(); i++)
		{
			PointAnalysis pa = paList.get(i);
			
			questionOrder++;
			if(questionOrder == questionSum + 1)
			{
				if(abandon == false)
				{
					//算次数、总分
					BigDecimal t = times.get(this_choice);
						t = t.add(new BigDecimal(1));
					BigDecimal p = points.get(this_choice);
						p = p.add(point);
					times.set(this_choice, t);
					points.set(this_choice, p);
				}
				
				abandon = false;
				point = new BigDecimal(0);
				this_choice = 0;
				questionOrder = 0;
				i--;
				continue;
			}
			
			if(questionOrder != pa.getOrderNo())
			{
				abandon = true;
				i--;
			}
			else
			{
				if(aim_q_order == pa.getOrderNo())
				{
					this_choice = pa.getChoiceOrder();
				}
				if(pa.getMarkState() == (short) 1)
				{
					String[] marks = pa.getMark().split("；");
					int choiceNo = pa.getChoiceOrder()-1;
					double mark = Double.parseDouble(marks[choiceNo]);
					double weight = pa.getWeight();
					BigDecimal toBeAdded = new BigDecimal(mark).multiply(new BigDecimal(weight));
					point = point.add(toBeAdded);
				}
			}
		}
		
		//算平均分 封装数据
		List<String> xname = new ArrayList<String>();
		List<Bar> yvalue = new ArrayList<Bar>();
		Bar b = new Bar();
		List<String> data = new ArrayList<String>();
		
		boolean split = false;
		if(scList.size() > 5)
			split = true;
		
		int j = 0;
		for(SurveyChoice c : scList)
		{
			j++;
			String x = "";
			if(StringUtil.isNotEmpty(c.getChoice()))
				x = c.getChoice();
			else
			{
				x = "(主观题)";
				j = 0;
			}
			BigDecimal p = points.get(j);
			BigDecimal t = times.get(j);
			BigDecimal average;
			if(t.compareTo(new BigDecimal(1)) == -1) //t < 1
				average = new BigDecimal(0);
			else
				average = p.divide(t, 2, BigDecimal.ROUND_HALF_UP);
			data.add(average.toString());
			if(split)
				x = x.substring(0, x.length()/2) + "\n" + x.substring(x.length()/2, x.length());
			xname.add(x);
		}
		b.setName("特定题目回答者平均总分");
		b.setData(data);
		b.setType("bar");
		yvalue.add(b);
		
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("xname", xname);
			map.put("yvalue", yvalue);
			
		return map;
		
	}
	
	@RequestMapping(params = "execute1_3")
	public ModelAndView execute1_3(HttpServletRequest request) throws ParseException
	{
		String SID = request.getParameter("SID");
		String AID = request.getParameter("AID");
		String QID = request.getParameter("QID");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		SurveyQuestion sq = surveyService.getEntity(SurveyQuestion.class, QID);
		
		List<SurveyAnalysisQuestion> saqList = surveyService.findHql("from SurveyAnalysisQuestion where analysisId=? order by orderNo asc", AID);
		
		List<SurveyChoice> scList = surveyService.findByProperty(SurveyChoice.class, "questionId", QID);
		
		List<PointAnalysis> paList = systemService.findHql("SELECT new cqc.survey.model.PointAnalysis(aq.orderNo, aq.weight, c.choiceOrder, aq.markState, aq.mark) " +
				"FROM SurveyAnswer a, SurveyChoice c, SurveyAnalysisQuestion aq, SurveyInterviewee i " +
				"WHERE a.choiceId=c.id AND c.questionId=aq.questionId AND a.intervieweeId=i.id " +
				"AND aq.analysisId=? AND (i.submitTime between ? and ?) ORDER BY a.intervieweeId", AID, sdf.parse(date_begin), sdf.parse(date_end));
		
		final int questionSum = saqList.size();
		
		final int aim_q_order = sq.getOrderNo();
		
		final int cnum = scList.size();
		List<BigDecimal> times = new ArrayList<BigDecimal>();
		List<BigDecimal> points = new ArrayList<BigDecimal>();
		for(int i = 0; i <= cnum; i++) // i <= cnum, 故index直接对应choiceOrder,而当是主观题时index=0时对应了主观题
		{
			BigDecimal innit = new BigDecimal(0);
			times.add(innit);
			points.add(innit);
		}
		
		int questionOrder = 0;
		int this_choice = 0;
		boolean abandon = false;
		BigDecimal point = new BigDecimal(0);
		for(int i = 0; i < paList.size(); i++)
		{
			PointAnalysis pa = paList.get(i);
			
			questionOrder++;
			if(questionOrder == questionSum + 1)
			{
				if(abandon == false)
				{
					//算次数、总分
					BigDecimal t = times.get(this_choice);
						t = t.add(new BigDecimal(1));
					BigDecimal p = points.get(this_choice);
						p = p.add(point);
					times.set(this_choice, t);
					points.set(this_choice, p);
				}
				
				abandon = false;
				point = new BigDecimal(0);
				this_choice = 0;
				questionOrder = 0;
				i--;
				continue;
			}
			
			if(questionOrder != pa.getOrderNo())
			{
				abandon = true;
				i--;
			}
			else
			{
				if(aim_q_order == pa.getOrderNo())
				{
					this_choice = pa.getChoiceOrder();
				}
				if(pa.getMarkState() == (short) 1)
				{
					String[] marks = pa.getMark().split("；");
					int choiceNo = pa.getChoiceOrder()-1;
					double mark = Double.parseDouble(marks[choiceNo]);
					double weight = pa.getWeight();
					BigDecimal toBeAdded = new BigDecimal(mark).multiply(new BigDecimal(weight));
					point = point.add(toBeAdded);
				}
			}
		}
		
		List<Attribute> aList = new ArrayList<Attribute>();
		
		int j = 0;
		for(SurveyChoice c : scList)
		{
			j++;
			Attribute a = new Attribute();
			if(StringUtil.isNotEmpty(c.getChoice()))
				a.setKey(c.getChoice());
			else
				a.setKey("(主观题)");
			BigDecimal p = points.get(j);
			BigDecimal t = times.get(j);
			BigDecimal average;
			if(t.compareTo(new BigDecimal(1)) == -1) //t < 1
				average = new BigDecimal(0);
			else
				average = p.divide(t, 2, BigDecimal.ROUND_HALF_UP);
			a.setValue(average.toString());
			aList.add(a);
		}
		
		request.setAttribute("aList", aList);
		request.setAttribute("SID", SID);
		request.setAttribute("AID", AID);
		request.setAttribute("date_begin", date_begin);
		request.setAttribute("date_end", date_end);
		request.setAttribute("q", sq);
		return new ModelAndView("cqc/analysis/execute1_3");
	}
	
	
	/**
	 * 删除 分析
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delete")
	public ModelAndView delete(HttpServletRequest request)
	{
		return new ModelAndView("cqc/analysis/delete");
	}
	
	@RequestMapping(params = "delAnalysis")
	@ResponseBody
	public AjaxJson delAnalysis(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		
		try{
			systemService.executeSql("delete from survey_analysis_question where analysis_id=?", id);
			systemService.executeSql("delete from survey_analysis_head where id=?", id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			j.setMsg("删除失败：系统内部错误。");
			return j;
		}
		j.setMsg("删除成功。");
		return j;
	}
	

}
