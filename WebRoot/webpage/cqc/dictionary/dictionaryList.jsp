<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div id="dictionary_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="typeGridTree" title="字典信息" actionUrl="dictionaryController.do?dictionaryList"
                    idField="id" treegrid="false" pagination="false" onLoadSuccess="loadSuccess">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="字典名称" field="head" width="100"></t:dgCol>
            <t:dgCol title="字典描述" field="description" width="100"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt url="dictionaryController.do?deldictionary&id={id}" title="删除"></t:dgDelOpt>
            <t:dgFunOpt funname="queryTypeValue(id,head)" title="字典内容管理"></t:dgFunOpt>
            <t:dgToolBar title="字典录入" icon="icon-add" url="dictionaryController.do?addDictionary" funname="add"></t:dgToolBar>
            <t:dgToolBar title="字典编辑" icon="icon-edit" url="dictionaryController.do?editDictionary" funname="update"></t:dgToolBar>
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
            $('#dictionary_list').layout('expand','east');
        }
        $('#dictionary_list').layout('panel','east').panel('setTitle', title);
        $('#userListpanel').panel("refresh", "dictionaryController.do?details&id=" + typegroupid);
    }
    function loadSuccess() {
        $('#dictionary_list').layout('panel','east').panel('setTitle', "");
        $('#dictionary_list').layout('collapse','east');
        $('#userListpanel').empty();
    }
</script>
