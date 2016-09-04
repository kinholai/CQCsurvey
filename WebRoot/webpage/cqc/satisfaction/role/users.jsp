<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="roleUsers" title="角色用户"
                    actionUrl="satisfactionController.do?usersList&roleId=${roleId}" idField="id"
                    queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="用户名" field="username"></t:dgCol>
            <t:dgCol title="企业名称" field="enterpriseName"></t:dgCol>
           <%-- <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="dictionaryController.do?delItem&id={id}" title="删除"></t:dgDelOpt> --%>
            <t:dgToolBar title="角色用户录入" icon="icon-add" url="satisfactionController.do?aouUser&roleId=${roleId}" funname="add" height="560"></t:dgToolBar>
            <t:dgToolBar title="角色用户编辑" icon="icon-edit" url="satisfactionController.do?aouUser&roleId=${roleId}" funname="update" height="560"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script>
    function addType(title,addurl,gname,width,height) {
        alert($("#id").val());
        add(title,addurl,gname,width,height);
    }
</script>

