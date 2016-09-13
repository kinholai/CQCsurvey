<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!DOCTYPE html>
<html>
<head>
<title>特定题目回答者平均总分统计</title>
</head>
<body>

<a href="statisticController.do?execute2_1&id=${AID }&headId=${SID }" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回重新查询</a>
<p><font size="3" face="arial" color="red">特定问题提交情况统计：<br>${q.question }</font></p>
<table border="1px\" cellpadding="5\" cellspacing="0">

<tr>
<td>选项</td><td>平均总分</td>
</tr>

<c:forEach items="${aList}" var="sa"><tr><td>${sa.key }</td><td>${sa.value }</td></tr></c:forEach>

</table>

</body>
</html>