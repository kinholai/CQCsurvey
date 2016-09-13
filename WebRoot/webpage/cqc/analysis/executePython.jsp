<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<script type="text/javascript">


 </script>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="statisticController.do?executePython1_2">
    <fieldset class="step">
    <input type="hidden" name="analysisHeadId" value="${analysisHeadId }">
        <div class="form">
            <label class="Validform_label"> 选择脚本文件: </label>
            	<select name="pythonId" style="width:400px;">
            		<c:forEach  items="${pythons }" var="s">
            			<option value="${s.id }">${s.attachmenttitle }</option>
            		</c:forEach>
            	</select>
            <span class="Validform_checktip"></span>
        </div>
    </fieldset>
</t:formvalid>
</body>
</html>
