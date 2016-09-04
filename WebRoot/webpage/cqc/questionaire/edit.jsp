<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="edit" title="编辑问卷" checkbox="false" actionUrl="questionaireController.do?questionaires" idField="id" fit="true" queryMode="group" pagination="false">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="问卷标题" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="修改时间" field="updateTime" width="100" formatter="yyyy-MM-dd hh:mm"></t:dgCol>
  </t:datagrid>
  <div id="createtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑问卷','questionaireController.do?editQuestionaire','edit')">编辑问卷</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="updatebytab()">进入问卷板块管理页面</a>
	</div>
</div>
<script type="text/javascript">
function updatebytab()
{
	var rows = $("#edit").datagrid("getSelections");
		if(rows=='')
		{
			alertTip('请您先选择一份问卷。');
			return;
		}
		var id=rows[0].id;
		//var title =findThisTitle();
		document.location="questionaireController.do?blockManagement&id="+id;
		//addOneTab("TAB方式编辑", "demoController.do?aorudemo&id="+id);
		//closeThisTab(title);
	}

function closeThisTab(title){
    window.top.$('#maintabs').tabs('close',title); 
}
function findThisTitle(){
	var currTab =  window.top.$('#maintabs').tabs('getSelected'); 
	var tab = currTab.panel('options').tab;
    var title=tab.text();
    return title;
}
</script>