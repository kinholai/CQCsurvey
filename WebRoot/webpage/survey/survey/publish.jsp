<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>问卷发布状态</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="surveyController.do?savePublish">
	<fieldset class="step">
	<input type="hidden" name="id" id="id" value="${sHead.id }">
	<div class="form">
	<label class="Validform_label"> 问卷标题: </label> 
	${sHead.head }</div>
	<div class="form">
	<label class="Validform_label"> 发布状态: </label> 
	<select name="publishState" id="publishState">
		<c:if test="${sHead.publishState == 0 }">
			<option value="0">未发布</option>
			<option value="1">发布中</option>
		</c:if>
		<c:if test="${sHead.publishState == 1 }">
			<option value="1">发布中</option>
			<option value="0">未发布</option>
		</c:if>
	</select>
	</div>
	</fieldset>
</t:formvalid>
</body>
</html>
