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
<t:formvalid formid="formobj" layout="div" dialog="true" action="statisticController.do?saveAnalysis">
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 选择调查: </label>
            	<select name="headId" style="width:400px;">
            		<c:forEach  items="${sList }" var="s">
            			<option value="${s.id }">${s.head }</option>
            		</c:forEach>
            	</select>
            <span class="Validform_checktip"></span>
        </div>
        
        <div class="form">
            <label class="Validform_label"> 备注说明: </label>
            	<input name="remark" value="">
            <span class="Validform_checktip"></span>
        </div>
    </fieldset>
    提示：点击确定后可能需等待稍长时间才有响应。
</t:formvalid>
</body>
</html>
