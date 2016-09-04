<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
 <t:datagrid name="foresee" title="预览调查" checkbox="false" actionUrl="surveyController.do?surveyList" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdTime" sortOrder="desc">
   <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="调查标题" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="创建时间" field="createdTime" formatter="yyyy-MM-dd hh:mm" width="100" query="false"></t:dgCol>
             <t:dgCol title="发布状态" field="publishState" width="100" replace="未发布_0,发布中_1"></t:dgCol>
             <t:dgCol title="发布时间" field="publishTime" formatter="yyyy-MM-dd hh:mm" width="100" query="false"></t:dgCol>
             <t:dgCol title="限制状态" field="limitState" width="150" replace="同一IP不限提交次数_0,同一IP限提交x次_1,仅白名单IP不限提交次数_2,黑名单IP限提交x次而非黑名单IP不限提交次数_3,仅白名单IP不限提交次数且黑名单IP限提交x次_4"></t:dgCol>
  			<t:dgCol field="limitNum" title="受限对象的限值次数x"></t:dgCol>
  </t:datagrid>
  <div id="foreseetb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="updatebytab1()">预览调查</a>
  	</div>
 </div>
  </div>
 </div>
		
 <script type="text/javascript">
 function updatebytab1()
 {
 	var rows = $("#foresee").datagrid("getSelections");
 		if(rows=='')
 		{
 			alertTip('请您先选择一项调查（如果没有，请您先录入）。');
 			return;
 		}
 		var id=rows[0].id;
 		document.location="surveyController.do?goForesee&id="+id;
 	}
 </script>