<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="imageList" title="首页图片管理(默认显示最近3张，置顶除外)" actionUrl="satisfactionController.do?imageList" idField="id" fit="true" sortName="createTime" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<%--<t:dgCol title="图片" field="image" imageSize="90,50"></t:dgCol> --%>
	<t:dgCol title="标题" field="title"></t:dgCol>
	<t:dgCol title="分类" field="tag" replace="正在发布_1,调查报告_2"></t:dgCol>
	<t:dgCol title="是否置顶" field="prority" replace="否_0,是_1"></t:dgCol>
	<t:dgCol title="发布时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt title="删除" url="satisfactionController.do?delImage&id={id}" />
	<t:dgToolBar title="录入" icon="icon-add" url="satisfactionController.do?aouImage" width="720" height="300" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="satisfactionController.do?aouImage" width="720" height="300" funname="update"></t:dgToolBar>
	
</t:datagrid> 

<script type="text/javascript">
			function preview(id) {
				var url = 'satisfactionController.do?preview&id=' + id;
				createwindow('预览', url, 1080, 720);
			}
		</script>
		
	</div>
</div>