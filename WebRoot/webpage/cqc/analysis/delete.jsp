<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="delete" title="删除分析" checkbox="false" actionUrl="statisticController.do?analysis" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdDate" sortOrder="desc">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
   			<t:dgCol title="调查外键" field="headId" hidden="true"></t:dgCol>
            <t:dgCol title="调查名称" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="配置状态" field="settingState" width="100" replace="未配置_0, 已配置_1"></t:dgCol>
            <t:dgCol title="创建时间" field="createdDate" width="100" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
            <t:dgCol title="备注说明" field="remark" width="100"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="statisticController.do?delAnalysis&id={id}" title="删除"></t:dgDelOpt>
  </t:datagrid>
  <div id="createtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
	</div>
</div>
<script type="text/javascript">
function update2(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择一项执行的分析');
		return;
	}
	if (rowsData.length>1) {
		tip('一次只能执行一项分析');
		return;
	}
	url += '&id='+rowsData[0].id;
	createwindow(title,url,width,height);
}
</script>