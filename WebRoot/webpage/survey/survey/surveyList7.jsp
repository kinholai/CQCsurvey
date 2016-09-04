<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="surveyList7" title="删除问卷" checkbox="false" actionUrl="surveyController.do?surveyList" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdTime" sortOrder="desc">
   <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="问卷标题" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="创建时间" field="createdTime" formatter="yyyy-MM-dd hh:mm" width="100" query="false"></t:dgCol>
             <t:dgCol title="生命状态" field="lifeState" width="100" replace="无效_0,有效_1"></t:dgCol>
             <t:dgCol title="发布状态" field="publishState" width="100" replace="未发布_0,发布中_1"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt url="surveyController.do?delSurvey&id={id}" title="删除"></t:dgDelOpt>
  </t:datagrid>
  </div>
 </div>
