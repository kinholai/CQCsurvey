<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="settings_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
  <t:datagrid name="settings" title="配置分析" checkbox="false" actionUrl="statisticController.do?analysis" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdDate" sortOrder="desc">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
   			<t:dgCol title="调查外键" field="headId" hidden="true"></t:dgCol>
            <t:dgCol title="调查名称" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="配置状态" field="settingState" width="100" replace="未配置_0, 已配置_1"></t:dgCol>
            <t:dgCol title="创建时间" field="createdDate" width="100" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
            <t:dgCol title="备注说明" field="remark" width="100"></t:dgCol>
            <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
            <t:dgFunOpt funname="operationDetail(id)" title="脚本文件管理"></t:dgFunOpt>
  </t:datagrid>
  <div id="createtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="update2('配置分析','statisticController.do?setting','settings', 1080, 520)">配置分析</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="update2('执行默认计算','statisticController.do?executeDefault1_1','settings')">执行默认计算</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="update2('执行脚本计算','statisticController.do?executePython1_1','settings')">执行脚本计算</a>
	</div>
</div>
</div>
</div>
<div data-options="region:'east',
	title:'脚本文件管理',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="operationDetailpanel"></div>
</div>	
<script type="text/javascript">

$(function() {
	var li_east = 0;
});
//脚本文件管理
function operationDetail(functionId)
{
	if(li_east == 0){
	   $('#settings_list').layout('expand','east'); 
	}
	$('#operationDetailpanel').panel("refresh", "statisticController.do?getPython&analysisHeadId=" +functionId);
}

function update2(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择一项分析');
		return;
	}
	if (rowsData.length>1) {
		tip('一次只能对一项分析进行操作');
		return;
	}
	url += '&id='+rowsData[0].id + '&headId=' + rowsData[0].headId;
	createwindow(title,url,width,height);
}
</script>