<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="execute" title="执行分析" checkbox="false" actionUrl="statisticController.do?analysis" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdDate" sortOrder="desc">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
   			<t:dgCol title="调查外键" field="headId" hidden="true"></t:dgCol>
            <t:dgCol title="调查名称" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="配置状态" field="settingState" width="100" replace="未配置_0, 已配置_1"></t:dgCol>
            <t:dgCol title="创建时间" field="createdDate" width="100" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
            <t:dgCol title="备注说明" field="remark" width="100"></t:dgCol>
  </t:datagrid>
  <div id="createtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="update2('特定题目平均总分分析','statisticController.do?execute1_1','execute', 1080, 520)">特定题目平均总分分析</a>
	</div>
</div>
<script type="text/javascript">
function update2(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择一项执行的分析。');
		return;
	}
	if (rowsData.length>1) {
		tip('一次只能执行一项分析。');
		return;
	}
	if(rowsData[0].settingState == '0')
		{
			tip('请先配置该项分析。');
			return;
		}
	url += '&id='+rowsData[0].id + '&headId=' + rowsData[0].headId;
	openwindow(title,url,id,width,height);
}
</script>