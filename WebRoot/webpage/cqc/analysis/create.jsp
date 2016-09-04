<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="create" title="创建分析" checkbox="false" actionUrl="statisticController.do?analysis" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdDate" sortOrder="desc">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
   			<t:dgCol title="调查外键" field="headId" hidden="true"></t:dgCol>
            <t:dgCol title="调查名称" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="配置状态" field="settingState" width="100" replace="未配置_0, 已配置_1"></t:dgCol>
            <t:dgCol title="创建时间" field="createdDate" width="100" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
            <t:dgCol title="备注说明" field="remark" width="100"></t:dgCol>
  </t:datagrid>
  <div id="createtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('创建分析','statisticController.do?addAnalysis','create')">创建分析</a>
	</div>
</div>
<script type="text/javascript">

</script>