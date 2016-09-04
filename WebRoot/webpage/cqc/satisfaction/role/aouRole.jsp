<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>角色</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="satisfactionController.do?saveRole">
	<fieldset class="step">
	<input type="hidden" name="id" id="id" value="${role.id }">
	<div class="form">
	<label class="Validform_label"> 角色名称: </label> 
	<input name="roleName" class="inputxt" value="${role.roleName }" datatype="s2-20"> <span class="Validform_checktip">请输入2到20个字符</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
