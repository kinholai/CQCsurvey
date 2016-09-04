<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="foresee" title="预览问卷" checkbox="false" actionUrl="questionaireController.do?questionaires" idField="id" fit="true" queryMode="group" pagination="false">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="问卷标题" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="修改时间" field="updateTime" width="100" formatter="yyyy-MM-dd hh:mm"></t:dgCol>
  </t:datagrid>
  <div id="foreseetb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="updatebytab1()">预览调查</a>
  	</div>
 </div>
 <script type="text/javascript">
 function updatebytab1()
 {
 	var rows = $("#foresee").datagrid("getSelections");
 		if(rows=='')
 		{
 			alertTip('请您先选择一项问卷（如果没有，请您先录入）。');
 			return;
 		}
 		var id=rows[0].id;
 		document.location="questionaireController.do?goForesee&id="+id;
 	}
 </script>