<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
<title>限制状态</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<script type="text/javascript">
 function validate_required1(field,alerttxt)
 {
 with (field)
   {
   if (value == null || value < 1)
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
 		var head = document.getElementsByName("limitNum");
 			 	if (validate_required1(head[0], "限制次数不能小于1!")==false)
 		     	{	head[0].focus();
 		     		return false;
 		     	}
   }
 }
 </script>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="surveyController.do?saveLimit" beforeSubmit="validate_form(this)">
	<fieldset class="step">
	<input type="hidden" name="id" id="id" value="${sHead.id }">
	<div class="form">
	<label class="Validform_label"> 调查标题: </label> 
	${sHead.head }</div>
	<div class="form">
	<label class="Validform_label"> 限制状态: </label> 
	<select name="limitState" id="limitState" style="width:500px;">
		<c:if test="${sHead.limitState == 0 }">
						<option value="<%=Constant.UNLIMITED_STATE_VALUE%>"><%=Constant.UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.SINGLE_IP_STATE_VALUE%>"><%=Constant.SINGLE_IP_STATE_NAME%></option>
            			<option value="<%=Constant.WHITELIST_UNLIMITED_STATE_VALUE%>"><%=Constant.WHITELIST_UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.BLACKLIST_LIMITED_STATE_VALUE%>"><%=Constant.BLACKLIST_LIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.MIXED_STATE_VALUE%>"><%=Constant.MIXED_STATE_NAME%></option>
		</c:if>
		<c:if test="${sHead.limitState == 1 }">
						<option value="<%=Constant.SINGLE_IP_STATE_VALUE%>"><%=Constant.SINGLE_IP_STATE_NAME%></option>
						<option value="<%=Constant.UNLIMITED_STATE_VALUE%>"><%=Constant.UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.WHITELIST_UNLIMITED_STATE_VALUE%>"><%=Constant.WHITELIST_UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.BLACKLIST_LIMITED_STATE_VALUE%>"><%=Constant.BLACKLIST_LIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.MIXED_STATE_VALUE%>"><%=Constant.MIXED_STATE_NAME%></option>
		</c:if>
		<c:if test="${sHead.limitState == 2 }">
						<option value="<%=Constant.WHITELIST_UNLIMITED_STATE_VALUE%>"><%=Constant.WHITELIST_UNLIMITED_STATE_NAME%></option>
						<option value="<%=Constant.UNLIMITED_STATE_VALUE%>"><%=Constant.UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.SINGLE_IP_STATE_VALUE%>"><%=Constant.SINGLE_IP_STATE_NAME%></option>
            			<option value="<%=Constant.BLACKLIST_LIMITED_STATE_VALUE%>"><%=Constant.BLACKLIST_LIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.MIXED_STATE_VALUE%>"><%=Constant.MIXED_STATE_NAME%></option>
		</c:if>
		<c:if test="${sHead.limitState == 3 }">
						<option value="<%=Constant.BLACKLIST_LIMITED_STATE_VALUE%>"><%=Constant.BLACKLIST_LIMITED_STATE_NAME%></option>
						<option value="<%=Constant.UNLIMITED_STATE_VALUE%>"><%=Constant.UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.SINGLE_IP_STATE_VALUE%>"><%=Constant.SINGLE_IP_STATE_NAME%></option>
            			<option value="<%=Constant.WHITELIST_UNLIMITED_STATE_VALUE%>"><%=Constant.WHITELIST_UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.MIXED_STATE_VALUE%>"><%=Constant.MIXED_STATE_NAME%></option>
		</c:if>
		<c:if test="${sHead.limitState == 4 }">
						<option value="<%=Constant.MIXED_STATE_VALUE%>"><%=Constant.MIXED_STATE_NAME%></option>
						<option value="<%=Constant.UNLIMITED_STATE_VALUE%>"><%=Constant.UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.SINGLE_IP_STATE_VALUE%>"><%=Constant.SINGLE_IP_STATE_NAME%></option>
            			<option value="<%=Constant.WHITELIST_UNLIMITED_STATE_VALUE%>"><%=Constant.WHITELIST_UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.BLACKLIST_LIMITED_STATE_VALUE%>"><%=Constant.BLACKLIST_LIMITED_STATE_NAME%></option>
		</c:if>
	</select>
	</div>
	<div class="form">
            <label class="Validform_label"> 受限对象的限值次数x: </label>
            	<input name="limitNum" value="${sHead.limitNum }">
            <span class="Validform_checktip"></span>
        </div>
	</fieldset>
</t:formvalid>
</body>
</html>
