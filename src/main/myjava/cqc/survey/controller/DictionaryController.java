package cqc.survey.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cqc.survey.model.DictionaryHead;
import cqc.survey.model.DictionaryItem;

/**
 * 字典管理
 * @author Kinho
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/dictionaryController")
public class DictionaryController extends BaseController
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
	
	
	/***********************字典信息***************************/
	/**
	 * 字典管理 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "dictionary")
	public ModelAndView typeGroupList(HttpServletRequest request) 
	{
		return new ModelAndView("cqc/dictionary/dictionaryList");
	}
	
	/**
	 * 字典管理页面 请求数据
	 * @param head
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "dictionaryList")
	public void dictionaryList(DictionaryHead head, HttpServletRequest request,
				HttpServletResponse response, DataGrid dataGrid)
	{
		CriteriaQuery cq = new CriteriaQuery(DictionaryHead.class, dataGrid);
		cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 添加字典 页面跳转
	 * @param head
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addDictionary")
	public ModelAndView addDictionary(DictionaryHead head, HttpServletRequest request)
	{
		return new ModelAndView("cqc/dictionary/addDictionary");
	}
	
	/**
	 * 编辑字典 页面跳转
	 * @param head
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "editDictionary")
	public ModelAndView editDictionary(DictionaryHead head, HttpServletRequest request)
	{
		if(StringUtil.isNotEmpty(head.getId()))
		{
			head = systemService.getEntity(DictionaryHead.class, head.getId());
			request.setAttribute("Dhead", head);
		}
		return new ModelAndView("cqc/dictionary/editDictionary");
	}
	
	/**
	 * 保存字典
	 * @param Dhead
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveDictionary")
	@ResponseBody
	public AjaxJson saveDictionary(DictionaryHead Dhead, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		systemService.saveOrUpdate(Dhead);
		
		message = "操作成功。";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 删除字典及其字典数据
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "deldictionary")
	@ResponseBody
	public AjaxJson deldictionary(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		systemService.executeSql("delete from survey_dictionary_item where head_id=?", id);
		systemService.executeSql("delete from survey_dictionary_head where id=?", id);
		message = "字典删除成功";
		j.setMsg(message);
		return j;
	}
	
	
	/***********************字典内容***************************/
	/**
	 * 字典内容管理 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "details")
	public ModelAndView details(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		request.setAttribute("headId", id);
		return new ModelAndView("cqc/dictionary/itemList");
	}
	
	/**
	 * 字典内容管理页面 请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "itemList")
	public void itemList(HttpServletRequest request,
				HttpServletResponse response, DataGrid dataGrid)
	{
		String headId = request.getParameter("headId");
		CriteriaQuery cq = new CriteriaQuery(DictionaryItem.class, dataGrid);
			cq.eq("headId", headId);
			cq.add();
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 录入或编辑 字典数据
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "aouItem")
	public ModelAndView updateItem(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		String headId = request.getParameter("headId");
		if(StringUtil.isNotEmpty(id))
		{
			DictionaryItem item = systemService.getEntity(DictionaryItem.class, id);
			request.setAttribute("Ditem", item);
		}
		return new ModelAndView("cqc/dictionary/aouItem").addObject("headId", headId);
	}
	
	/**
	 * 检验字典数据是否重复
	 * 暂时用不到 前端问题
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkItem")
	@ResponseBody
	public ValidForm checkItem(HttpServletRequest request)
	{
		ValidForm v = new ValidForm();
		String headId = oConvertUtils.getString(request.getParameter("headId"));
		String item = oConvertUtils.getString(request.getParameter("item"));
		StringBuilder hql = new StringBuilder("FROM ").append(DictionaryItem.class.getName()).append(" AS entity WHERE 1=1 ");
		hql.append(" AND entity.headId =  '").append(headId).append("'");
		hql.append(" AND entity.item =  '").append(item).append("'");
		List<Object> types = systemService.findByQueryString(hql.toString());
		if(types.size() > 0)
		{
			v.setInfo("类型已存在");
			v.setStatus("n");
		}
		return v;
	}
	
	/**
	 * 保存字典数据
	 * @param Ditem
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveItem")
	@ResponseBody
	public AjaxJson saveItem(DictionaryItem Ditem, HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		if(StringUtil.isNotEmpty(Ditem.getId()))
			systemService.saveOrUpdate(Ditem);
		else
			systemService.save(Ditem);
		message = "保存成功";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 删除字典数据
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delItem")
	@ResponseBody
	public AjaxJson delItem(HttpServletRequest request)
	{
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		systemService.executeSql("delete from survey_dictionary_item where id=?", id);
		message = "字典数据删除成功";
		j.setMsg(message);
		return j;
	}
}
