<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="delete" title="删除问卷" checkbox="false" actionUrl="questionaireController.do?questionaires" idField="id" fit="true" queryMode="group" pagination="false">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="问卷标题" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="修改时间" field="updateTime" width="100" formatter="yyyy-MM-dd hh:mm"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="questionaireController.do?delQuestionaire&id={id}" title="删除"></t:dgDelOpt>
  </t:datagrid>
