<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div id="users_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="users" title="用户管理" actionUrl="satisfactionController.do?usersList"
                    idField="id" treegrid="false" pagination="true" fitColumns="true">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="用户名" field="username"></t:dgCol>
            <t:dgCol title="真实姓名" field="realname"></t:dgCol>
            <t:dgCol title="手机号码" field="mobile"></t:dgCol>
            <t:dgCol title="邮箱地址" field="email"></t:dgCol>
            <t:dgCol title="企业名称" field="enterpriseName"></t:dgCol>
            <t:dgCol title="企业地址" field="enterpriseAddress"></t:dgCol>
            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt url="satisfactionController.do?delRole&id={id}" title="删除"></t:dgDelOpt> --%>
            <t:dgToolBar title="用户录入" icon="icon-add" url="satisfactionController.do?aouUser" funname="add" height="560"></t:dgToolBar>
            <t:dgToolBar title="用户编辑" icon="icon-edit" url="satisfactionController.do?aouUser" funname="update" height="560"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">

</script>
