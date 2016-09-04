<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="blockManagement" title="板块管理" checkbox="false" actionUrl="questionaireController.do?blocks&qmid=${qmid }" idField="id" fit="true" queryMode="group" pagination="false" onLoadSuccess="tipsMsg">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="所属问卷标题" field="mainId" width="120" dictionary="questionaire_main, id, head"></t:dgCol>
            <t:dgCol title="板块序号" field="blockOrder" width="100"></t:dgCol>
            <t:dgCol title="板块名称" field="blockName" width="100"></t:dgCol>
            <t:dgCol title="问题录入状态" field="questionState" width="100" replace="未录入_0,已录入_1"></t:dgCol>
            <t:dgCol title="问题数量" field="primaryNum" width="100"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="questionaireController.do?delBlock&id={id}" title="删除"></t:dgDelOpt>
  </t:datagrid>
  <div id="blockManagementtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="javascript:history.go(-1)" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('新建板块','questionaireController.do?addBlock&qmid=${qmid }','blockManagement')">新建板块</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑板块','questionaireController.do?editBlock','blockManagement')">编辑板块</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="update1('录入全部问题','questionaireController.do?addQuestions','blockManagement')">录入问题</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="updatebytab()">进入问题管理页面</a>
	</div>
</div>
<script type="text/javascript">
function tipsMsg()
{
	var row = $('#blockManagement').datagrid('getData');
	if(row.total == 0)
		alertTip('该问卷暂时没有板块内容，请您先新建板块。');
}

function update1(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择一个板块');
		return;
	}
	if (rowsData.length>1) {
		tip('一次只能对一个板块进行问题录入');
		return;
	}
	if(rowsData[0].primaryNum == 0 && rowsData[0].questionState != 0)
		{
		tip("该板块已定义为空板块。");
		return;
		}
	if(rowsData[0].questionState != 0)
	{
		tip("该板块的问题已全部录入，若要补充录入问题，请您到“问题管理”。");
		return;
	}	
	url += '&id='+rowsData[0].id;
	createwindow(title,url,1080,520);
}

function updatebytab()
{
	var rows = $("#blockManagement").datagrid("getSelections");
		if(rows=='')
		{
			alertTip('请您先选择一个板块。');
			return;
		}
		var id=rows[0].id;
		var questionState = rows[0].questionState;
		var primaryNum = rows[0].primaryNum;
		if(primaryNum == 0 && questionState != 0)
		{
			tip("该板块已定义为空板块。");
			return;
		}
		if(questionState == 0)
		{
			alertTip('请您先录入板块的所有问题。');
			return;
		}
		//var title =findThisTitle();
		document.location="questionaireController.do?questionManagement&id="+id;
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