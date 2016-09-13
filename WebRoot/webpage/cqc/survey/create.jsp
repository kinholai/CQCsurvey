<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="create_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
  <t:datagrid name="create" title="创建调查" checkbox="false" actionUrl="surveyController.do?surveyList" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdTime" sortOrder="desc">
   <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="调查标题" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="创建时间" field="createdTime" formatter="yyyy-MM-dd hh:mm" width="100" query="false"></t:dgCol>
             <t:dgCol title="发布状态" field="publishState" width="100" replace="未发布_0,发布中_1"></t:dgCol>
             <t:dgCol title="发布时间" field="publishTime" formatter="yyyy-MM-dd hh:mm" width="100" query="false"></t:dgCol>
             <t:dgCol title="限制状态" field="limitState" width="150" replace="同一IP不限提交次数_0,同一IP限提交x次_1,仅白名单IP不限提交次数_2,黑名单IP限提交x次而非黑名单IP不限提交次数_3,仅白名单IP不限提交次数且黑名单IP限提交x次_4"></t:dgCol>
             <t:dgCol field="limitNum" title="受限对象的限值次数x"></t:dgCol>
             <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
		        <%--   update-start--Author:anchao  Date:20130415 for：按钮权限控制--%>
		        <t:dgFunOpt funname="operationDetail(id)" title="黑名单管理"></t:dgFunOpt>
		        <t:dgFunOpt funname="operationData(id)" title="白名单管理"></t:dgFunOpt>
  </t:datagrid>
<div id="createtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('创建调查','surveyController.do?addSurvey','create')">创建调查</a>
	</div>
</div>	
</div>
</div>


<div data-options="region:'east',
	title:'黑白名单管理',
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
	//白名单管理
	function  operationData(fucntionId){
		if(li_east == 0){
		   $('#create_list').layout('expand','east'); 
		}
		$('#operationDetailpanel').panel("refresh", "surveyController.do?whitelist&headId=" +fucntionId);
	}
	//黑名单管理
	function operationDetail(functionId)
	{
		if(li_east == 0){
		   $('#create_list').layout('expand','east'); 
		}
		$('#operationDetailpanel').panel("refresh", "surveyController.do?blacklist&headId=" +functionId);
	}
 
 </script>