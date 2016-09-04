<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="whitelist" title="白名单管理"
                    actionUrl="surveyController.do?whiteList&headId=${headId}" idField="id"
                    queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="调查标题" field="headId" hidden="true"></t:dgCol>
            <t:dgCol title="IP" field="ip"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="surveyController.do?delWIP&id={id}" title="删除"></t:dgDelOpt>
            <t:dgToolBar title="录入" icon="icon-add" url="surveyController.do?aouWIP&headId=${headId}" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="surveyController.do?aouWIP&headId=${headId}" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

