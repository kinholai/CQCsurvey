<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="blacklist" title="脚本文件管理"
                    actionUrl="statisticController.do?pythonList&analysisHeadId=${analysisHeadId}" idField="id"
                    queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="文件名称" field="attachmenttitle" hidden="false"></t:dgCol>
            <t:dgCol title="备注说明" field="remark"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="statisticController.do?delPython&id={id}" title="删除"></t:dgDelOpt>
            <t:dgToolBar title="上传" icon="icon-add" url="statisticController.do?addPython&analysisHeadId=${analysisHeadId}" funname="add"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

