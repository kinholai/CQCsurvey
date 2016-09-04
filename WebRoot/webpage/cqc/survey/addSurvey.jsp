<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
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
<t:formvalid formid="formobj" layout="div" dialog="true" action="surveyController.do?saveSurvey" beforeSubmit="validate_form(this)">
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 选择问卷: </label>
            	<select name="headId" style="width:400px;">
            		<c:forEach  items="${qmList }" var="qm">
            			<option value="${qm.id }">${qm.head }</option>
            		</c:forEach>
            	</select>
            <span class="Validform_checktip"></span>
        </div>
        <div class="form">
            <label class="Validform_label"> 设置限制状态: </label>
            	<select name="limitState" style="width:500px;">
            			<option value="<%=Constant.UNLIMITED_STATE_VALUE%>"><%=Constant.UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.SINGLE_IP_STATE_VALUE%>"><%=Constant.SINGLE_IP_STATE_NAME%></option>
            			<option value="<%=Constant.WHITELIST_UNLIMITED_STATE_VALUE%>"><%=Constant.WHITELIST_UNLIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.BLACKLIST_LIMITED_STATE_VALUE%>"><%=Constant.BLACKLIST_LIMITED_STATE_NAME%></option>
            			<option value="<%=Constant.MIXED_STATE_VALUE%>"><%=Constant.MIXED_STATE_NAME%></option>
            	</select>
            <span class="Validform_checktip"></span>
        </div>
        <div class="form">
            <label class="Validform_label"> 受限对象的限值次数x: </label>
            	<input name="limitNum" value="1">
            <span class="Validform_checktip"></span>
        </div>
    </fieldset>
    提示：点击确定后可能需等待稍长时间才有响应。
</t:formvalid>
</body>
</html>
