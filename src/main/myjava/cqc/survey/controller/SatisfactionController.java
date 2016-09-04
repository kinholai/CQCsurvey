package cqc.survey.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.servlet.URLDecoder;

import org.jeecgframework.core.annotation.Ehcache;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cqc.survey.model.SatisfactionImage;
import cqc.survey.model.SatisfactionReport;
import cqc.survey.model.SatisfactionRole;
import cqc.survey.model.SatisfactionSurvey;
import cqc.survey.model.SatisfactionType;
import cqc.survey.model.SatisfactionUser;
import cqc.survey.model.SurveyHead;
import cqc.survey.service.SurveyService;

@Scope("prototype")
@Controller
@RequestMapping("/satisfactionController")
public class SatisfactionController extends BaseController
{
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping(params = "roleManagement")
	public ModelAndView roleManagement(HttpServletRequest request)
	{
		return new ModelAndView("cqc/satisfaction/role/roleList");
	}
	
	@RequestMapping(params = "rolesList")
	public void roleList(SatisfactionRole role, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(SatisfactionRole.class, dataGrid);
		cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "aouRole")
	public ModelAndView aouRole(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id))
		{
			SatisfactionRole role = systemService.getEntity(SatisfactionRole.class, id);
			request.setAttribute("role", role);
		}
		return new ModelAndView("cqc/satisfaction/role/aouRole");
	}
	
	@RequestMapping(params = "saveRole")
	@ResponseBody
	public AjaxJson saveRole(SatisfactionRole role, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		if(StringUtil.isNotEmpty(role.getId()))
			systemService.saveOrUpdate(role);
		else
			systemService.save(role);
		j.setMsg("保存角色成功。");
		return j;
	}
	
	@RequestMapping(params = "delRole")
	@ResponseBody
	public AjaxJson delRole(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		List<SatisfactionUser> users = systemService.findByProperty(SatisfactionUser.class, "roleId", id);
		if(users.size() > 0)
		{
			j.setMsg("无法删除该角色：有用户正在使用。");
			return j;
		}
		systemService.executeSql("delete from satisfaction_role where id=?", id);
		j.setMsg("删除角色成功。");
		return j;
	}
	
	@RequestMapping(params = "users")
	public ModelAndView users(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		request.setAttribute("roleId", id);
		return new ModelAndView("cqc/satisfaction/role/users");
	}
	
	@RequestMapping(params = "usersList")
	public void usersList(SatisfactionUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		String roleId = request.getParameter("roleId");
		CriteriaQuery cq = new CriteriaQuery(SatisfactionUser.class, dataGrid);
		if(StringUtil.isNotEmpty(roleId))
			cq.eq("roleId", roleId);
		cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "userManagement")
	public ModelAndView userManagement(HttpServletRequest request)
	{
		return new ModelAndView("cqc/satisfaction/user/userList");
	}
	
	@RequestMapping(params = "aouUser")
	public ModelAndView aouUser(SatisfactionUser user, HttpServletRequest request)
	{
		String roleId = request.getParameter("roleId");
		if(StringUtil.isNotEmpty(user.getId()))
		{
			user = systemService.getEntity(SatisfactionUser.class, user.getId());
			request.setAttribute("user", user);
			roleId = user.getRoleId();
		}
		List<SatisfactionRole> roleList = systemService.getList(SatisfactionRole.class);
		request.setAttribute("roleList", roleList);
		request.setAttribute("roleId", roleId);
		return new ModelAndView("cqc/satisfaction/user/user");
	}
	
	@RequestMapping(params = "saveUser")
	@ResponseBody
	public AjaxJson saveUser(SatisfactionUser user, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		if(StringUtil.isEmpty(user.getId()))
		{
			String password = user.getPassword();
			String encryptPassword = PasswordUtil.encrypt(user.getUsername(), password, PasswordUtil.getStaticSalt());
			user.setPassword(encryptPassword);
			systemService.save(user);
		}
		else
		{
			SatisfactionUser old_user = systemService.getEntity(SatisfactionUser.class, user.getId());
				old_user.setEmail(user.getEmail());
				old_user.setEnterpriseAddress(user.getEnterpriseAddress());
				old_user.setEnterpriseName(user.getEnterpriseName());
				old_user.setMobile(user.getMobile());
				old_user.setOffice(user.getOffice());
				old_user.setRealname(user.getRealname());
				old_user.setRoleId(user.getRoleId());
				old_user.setUsername(user.getUsername());
			systemService.saveOrUpdate(old_user);
		}
		j.setMsg("用户信息保存成功");
		return j;
	}
	
	@RequestMapping(params = "type1Management")
	public ModelAndView type1Management(HttpServletRequest request)
	{
		request.setAttribute("category", (short)1);
		return new ModelAndView("cqc/satisfaction/type/type1List");
	}
	
	@RequestMapping(params = "type2Management")
	public ModelAndView type2Management(HttpServletRequest request)
	{
		request.setAttribute("category", (short)2);
		return new ModelAndView("cqc/satisfaction/type/type2List");
	}
	
	@RequestMapping(params = "typeList")
	public void typeList(SatisfactionType type, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		String category = request.getParameter("category");
		CriteriaQuery cq = new CriteriaQuery(SatisfactionType.class, dataGrid);
			cq.eq("category", Short.valueOf(category));
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "aouType")
	public ModelAndView aouType(SatisfactionType type, HttpServletRequest request)
	{
		String category = request.getParameter("category");
		if(StringUtil.isNotEmpty(type.getId()))
		{
			type = systemService.getEntity(SatisfactionType.class, type.getId());
			request.setAttribute("type", type);
		}
		request.setAttribute("category", category);
		return new ModelAndView("cqc/satisfaction/type/aouType");
	}
	
	@RequestMapping(params = "saveType")
	@ResponseBody
	public AjaxJson saveType(SatisfactionType type, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		if(StringUtil.isNotEmpty(type.getId()))
			systemService.saveOrUpdate(type);
		else
			systemService.save(type);
		j.setMsg("保存分类成功。");
		return j;
	}
	
	@RequestMapping(params = "delType")
	@ResponseBody
	public AjaxJson delType(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		String category = request.getParameter("category");
		AjaxJson j = new AjaxJson();
		if(category.equals("1"))
		{
			List<SatisfactionSurvey> surveyList = systemService.findByProperty(SatisfactionSurvey.class, "typeId", id);
			if(surveyList.size() > 1)
			{
				j.setMsg("无法删除：有调查正在引用。");
				return j;
			}
			else
				systemService.executeSql("delete from satisfaction_type where id=?", id);
		}
		else if(category.equals("2"))
		{
			List<SatisfactionReport> reportList = systemService.findByProperty(SatisfactionReport.class, "typeId", id);
			if(reportList.size() > 1)
			{
				j.setMsg("无法删除：有报告正在引用。");
				return j;
			}
			else
				systemService.executeSql("delete from satisfaction_type where id=?", id);
		}
		j.setMsg("删除成功");
		return j;
	}
	
	@RequestMapping(params = "reportsManagement")
	public ModelAndView reportManagement(HttpServletRequest request)
	{
		request.getSession().setAttribute("CKFinder_UserRole", "admin");
		return new ModelAndView("cqc/satisfaction/report/reportList");
	}
	
	@RequestMapping(params = "reportList")
	public void reportList(SatisfactionReport report, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(SatisfactionReport.class, dataGrid);
		cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "aouReport")
	public ModelAndView aouReport(SatisfactionReport report, HttpServletRequest request)
	{
		if(StringUtil.isNotEmpty(report.getId()))
		{
			report = systemService.getEntity(SatisfactionReport.class, report.getId());
			request.setAttribute("report", report);
		}
		List<SatisfactionType> typeList = systemService.findByProperty(SatisfactionType.class, "category", (short)2);
		List<SatisfactionUser> userList = systemService.getList(SatisfactionUser.class);
		request.setAttribute("typeList", typeList);
		request.setAttribute("userList", userList);
		return new ModelAndView("cqc/satisfaction/report/aouReport");
	}
	
	@RequestMapping(params = "saveReport")
	@ResponseBody
	public AjaxJson saveReport(SatisfactionReport report, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		report.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(StringUtil.isNotEmpty(report.getId()))
			systemService.saveOrUpdate(report);
		else
			systemService.save(report);
		j.setMsg("报告保存成功");
		return j;
	}
	
	@RequestMapping(params = "preview")
	public ModelAndView preview(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		SatisfactionReport report = systemService.getEntity(SatisfactionReport.class, id);
		request.setAttribute("report", report);
		return new ModelAndView("cqc/satisfaction/report/previewReport");
	}
	
	@RequestMapping(params = "delReport")
	@ResponseBody
	public AjaxJson delReport(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		systemService.executeSql("delete from satisfaction_report where id=?", id);
		j.setMsg("删除报告成功。");
		return j;
	}
	
	@RequestMapping(params = "surveyManagement")
	public ModelAndView surveyManagement(HttpServletRequest request)
	{
		request.getSession().setAttribute("CKFinder_UserRole", "admin");
		return new ModelAndView("cqc/satisfaction/survey/surveyList");
	}
	
	@RequestMapping(params = "surveyList")
	public void surveyList(SatisfactionSurvey survey, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(SatisfactionSurvey.class, dataGrid);
		cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "aouSurvey")
	public ModelAndView aouSurvey(SatisfactionSurvey survey, HttpServletRequest request)
	{
		if(StringUtil.isNotEmpty(survey.getId()))
		{
			survey = systemService.getEntity(SatisfactionSurvey.class, survey.getId());
			request.setAttribute("survey", survey);
		}
		List<SatisfactionType> typeList = systemService.findByProperty(SatisfactionType.class, "category", (short)1);
		List<SurveyHead> surveyList = systemService.getList(SurveyHead.class);
		request.setAttribute("typeList", typeList);
		request.setAttribute("surveyList", surveyList);
		return new ModelAndView("cqc/satisfaction/survey/aouSurvey");
	}
	
	@RequestMapping(params = "saveSurvey")
	@ResponseBody
	public AjaxJson saveSurvey(SatisfactionSurvey survey, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		SurveyHead sh = surveyService.getEntity(SurveyHead.class, survey.getSurveyId());
		survey.setCreateTime(sh.getCreatedTime());
		if(StringUtil.isNotEmpty(survey.getId()))
			systemService.saveOrUpdate(survey);
		else
			systemService.save(survey);
		j.setMsg("调查保存成功");
		return j;
	}
	
	@RequestMapping(params = "delSurvey")
	@ResponseBody
	public AjaxJson delSurvey(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		systemService.executeSql("delete from satisfaction_survey where id=?", id);
		j.setMsg("删除调查成功。");
		return j;
	}
	
	@RequestMapping(params = "download")
	public void download(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id))
		{
			SatisfactionReport report = surveyService.getEntity(SatisfactionReport.class, id);
			request.setAttribute("report", report);
			
			String attachment = report.getAttachment();
				attachment = URLDecoder.decode(attachment);
				attachment = URLDecoder.decode(attachment);
			String directionName = "/upload/files";
			String filename = attachment.substring(attachment.lastIndexOf("/"), attachment.length());
			String name = directionName + filename;
			
			String ctxPath = request.getSession().getServletContext().getRealPath("/");
			if(ctxPath.endsWith("/") || ctxPath.endsWith("\\"))
			{}
			else
				ctxPath += "/";
			String fileRealname = ctxPath + name;
			
			surveyService.downloadFile(request, response, fileRealname);
		}
	}
	
	@RequestMapping(params = "imageManagement")
	public ModelAndView imageManagement(HttpServletRequest request, HttpServletResponse reponse)
	{
		request.getSession().setAttribute("CKFinder_UserRole", "admin");
		return new ModelAndView("cqc/satisfaction/image/imageList");
	}
	
	@RequestMapping(params = "imageList")
	public void imageList(SatisfactionImage image, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(SatisfactionImage.class, dataGrid);
		cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "aouImage")
	public ModelAndView aouImage(SatisfactionImage image, HttpServletRequest request, HttpServletResponse response)
	{
		if(StringUtil.isNotEmpty(image.getId()))
		{
			image = systemService.getEntity(SatisfactionImage.class, image.getId());
			request.setAttribute("image", image);
		}
		List<SatisfactionReport> reportList = systemService.getList(SatisfactionReport.class);
		List<SatisfactionSurvey> surveyList = systemService.findByProperty(SatisfactionSurvey.class, "state", (short)1);
		request.setAttribute("reportList", reportList);
		request.setAttribute("surveyList", surveyList);
		return new ModelAndView("cqc/satisfaction/image/aouImage");
	}
	
	@RequestMapping(params = "saveImage")
	@ResponseBody
	public AjaxJson saveImage(SatisfactionImage image, HttpServletRequest request, HttpServletResponse response) throws ParseException
	{
		AjaxJson j = new AjaxJson();
		if(image.getTag() == (short)1)
		{
			String tagId1 = request.getParameter("tagId1");
			image.setTagId(tagId1);
			SatisfactionSurvey survey = systemService.getEntity(SatisfactionSurvey.class, image.getTagId());
			image.setTitle(survey.getTitle());
		}
		else
		{
			String tagId2 = request.getParameter("tagId2");
			image.setTagId(tagId2);
			SatisfactionReport report = systemService.getEntity(SatisfactionReport.class, image.getTagId());
			image.setTitle(report.getTitle());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
		image.setCreateTime(sdf.parse(sdf.format(new Date())));
		surveyService.save(image);
		return j;
	}
	
	@RequestMapping(params = "delImage")
	@ResponseBody
	public AjaxJson delImage(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		List<SatisfactionImage> images = systemService.getList(SatisfactionImage.class);
		if(images.size() <= 3)
			j.setMsg("无法删除：剩余图片数量不能少于3。");
		else
		{
			String id = request.getParameter("id");
			systemService.executeSql("delete from satisfaction_image where id=?", id);
			j.setMsg("删除图片成功。");
		}
		return j;
	}
}
