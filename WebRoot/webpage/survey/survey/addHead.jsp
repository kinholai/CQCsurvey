<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>创建问卷</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="surveyController.do?saveHead">
	<fieldset class="step">
	<div class="form">
	<label class="Validform_label"> 问卷标题: </label> 
	<input name="head" class="inputxt" value="" datatype="s5-50"> <span class="Validform_checktip">请至少输入5个字符</span></div>
	<div class="form">
	<label class="Validform_label"> 个人信息类/非问卷调查类问题总数: </label> 
	<input name="extraSum" class="inputxt" value="" datatype="n"><span class="Validform_checktip">请输入正整数</span></div>
	<div class="form">
	<label class="Validform_label"> 问卷调查类问题总数: </label> 
	<input name="normalSum" class="inputxt" value="" datatype="n"><span class="Validform_checktip">请输入正整数</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
