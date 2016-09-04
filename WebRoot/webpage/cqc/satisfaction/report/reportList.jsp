<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="reportList" title="报告发布管理" actionUrl="satisfactionController.do?reportList" idField="id" fit="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<%--<t:dgCol title="图片" field="image" imageSize="90,50"></t:dgCol> --%>
	<t:dgCol title="标题" field="title"></t:dgCol>
	<t:dgCol title="分类" field="typeId" dictionary="satisfaction_type, id, type_name"></t:dgCol>
	<t:dgCol title="发布时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
	<t:dgCol title="企业用户" field="userId" dictionary="satisfaction_user, id, username"></t:dgCol>
	<t:dgCol title="是否置顶" field="prority" replace="否_0,是_1"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgFunOpt funname="preview(id)" title="预览"></t:dgFunOpt>
	<t:dgDelOpt title="删除" url="satisfactionController.do?delReport&id={id}" />
	<t:dgToolBar title="录入" icon="icon-add" url="satisfactionController.do?aouReport" width="1080" height="720" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="satisfactionController.do?aouReport" width="1080" height="720" funname="update"></t:dgToolBar>
	
</t:datagrid> 

<script type="text/javascript">
			function preview(id) {
				var url = 'satisfactionController.do?preview&id=' + id;
				createwindow('预览', url, 1080, 720);
			}
		</script>
		
	</div>
</div>