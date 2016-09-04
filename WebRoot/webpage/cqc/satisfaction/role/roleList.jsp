<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div id="roles_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="roles" title="角色管理" actionUrl="satisfactionController.do?rolesList"
                    idField="id" treegrid="false" pagination="false" onLoadSuccess="loadSuccess">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="角色名称" field="roleName" width="100"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt url="satisfactionController.do?delRole&id={id}" title="删除"></t:dgDelOpt>
            <t:dgFunOpt funname="queryTypeValue(id,roleName)" title="角色用户管理"></t:dgFunOpt>
            <t:dgToolBar title="角色录入" icon="icon-add" url="satisfactionController.do?aouRole" funname="add"></t:dgToolBar>
            <t:dgToolBar title="角色编辑" icon="icon-edit" url="satisfactionController.do?aouRole" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<div data-options="region:'east',
	title:'mytitle',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
</div>

<script type="text/javascript">
    $(function() {
        var li_east = 0;
    });

    function queryTypeValue(typegroupid, typegroupname){
        var title = typegroupname;
        if(li_east == 0){
            $('#roles_list').layout('expand','east');
        }
        $('#roles_list').layout('panel','east').panel('setTitle', title);
        $('#userListpanel').panel("refresh", "satisfactionController.do?users&id=" + typegroupid);
    }
    function loadSuccess() {
        $('#roles_list').layout('panel','east').panel('setTitle', "");
        $('#roles_list').layout('collapse','east');
        $('#userListpanel').empty();
    }
</script>
