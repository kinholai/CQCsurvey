<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="surveyController.do?saveBIP">
    <input name="id" type="hidden" value="${sr.id }">
    <input name="headId" type="hidden" value="${headId}">
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label"> IP地址: </label>
            <input name="ip" class="inputxt" value="${sr.ip }" datatype="*" >
            <span class="Validform_checktip"></span>
        </div>
    </fieldset>
</t:formvalid>
</body>
</html>
