<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>编辑问卷</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
 function validate_required1(field,alerttxt)
 {
 with (field)
   {
   if (value.length >50 || value.length < 5)
     {
 	   tip(alerttxt);
 	   return false;
 	   }
   else 
 	   return true;
   }
 }
 
 function validate_form(thisform)
 {
 	with (thisform)
   {
 		var head = document.getElementsByName("head");
 			 	if (validate_required1(head[0], "请输入5-50个字符!")==false)
 		     	{	head[0].focus();
 		     		return false;
 		     	}
   }
 }
 </script>
</head>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="questionaireController.do?saveEditQuestionaire">
	<fieldset class="step">
	<input type="hidden" name="id" value="${qm.id }">
	<div class="form">
	<label class="Validform_label"> 问卷标题: </label> 
		<input name="head" class="inputxt" value="${qm.head }">
	</div>
	<div class="form">
		<label class="Validform_label"> 说明性文字: </label> 
			<input name="description" class="inputxt" value="${qm.description }">
	</div>
	</fieldset>
</t:formvalid>
</body>
</html>
