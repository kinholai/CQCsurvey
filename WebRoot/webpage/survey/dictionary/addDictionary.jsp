<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>字典编辑</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="dictionaryController.do?saveDictionary">
	<fieldset class="step">
	<div class="form">
	<label class="Validform_label"> 字典名称: </label> 
	<input name="head" class="inputxt" validType="survey_dictionary_head,head,id" value="" datatype="s2-20"> <span class="Validform_checktip">请输入2到20个字符</span></div>
	<div class="form">
	<label class="Validform_label"> 字典描述: </label> 
	<input name="description" class="inputxt" value=""></div>
	</fieldset>
</t:formvalid>
</body>
</html>
