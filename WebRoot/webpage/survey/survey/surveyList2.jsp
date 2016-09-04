<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="surveyList2" title="录入问题" checkbox="false" actionUrl="surveyController.do?surveyList" idField="id" fit="true" queryMode="group" pagination="false" sortName="createdTime" sortOrder="desc">
   <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="问卷标题" field="head" width="100" query="false" queryMode="single"></t:dgCol>
            <t:dgCol title="创建时间" field="createdTime" formatter="yyyy-MM-dd hh:mm" width="100" query="false"></t:dgCol>
             <t:dgCol title="生命状态" field="lifeState" width="100" replace="无效_0,有效_1"></t:dgCol>
             <t:dgCol title="发布状态" field="publishState" width="100" replace="未发布_0,发布中_1"></t:dgCol>
            <t:dgToolBar title="录入问题" icon="icon-add" url="surveyController.do?addQuestions" funname="update1"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
		
 <script type="text/javascript">
 function update1(title,url, id,width,height) {
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一个需要录入问题的问卷');
			return;
		}
		if (rowsData.length>1) {
			tip('一次只能对一个问卷进行问题录入');
			return;
		}
		
		url += '&id='+rowsData[0].id;
		createwindow(title,url,1080,520);
	}
 </script>