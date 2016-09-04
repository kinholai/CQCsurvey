<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="dictionaryController.do?saveItem">
    <input name="id" type="hidden" value="${Ditem.id }">
    <input name="headId" type="hidden" value="${headId}">
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 字典数据: </label>
            <input name="item" class="inputxt" value="${Ditem.item }" datatype="*" >
            <span class="Validform_checktip"></span>
        </div>
    </fieldset>
</t:formvalid>
</body>
</html>
