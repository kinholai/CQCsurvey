<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分类</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="satisfactionController.do?saveType">
	<fieldset class="step">
	<input type="hidden" name="id" id="id" value="${type.id }">
	<input type="hidden" name="category" id="category" value="${category }">
	<div class="form">
	<label class="Validform_label"> 分类名称: </label> 
	<input name="typeName" class="inputxt" value="${type.typeName }" datatype="s2-16"> <span class="Validform_checktip">请输入2到16个字符</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
