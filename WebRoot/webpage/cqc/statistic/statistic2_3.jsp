<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!DOCTYPE html>
<html>
<head>
<title>特定问题回答情况统计</title>
</head>
<body>

<a href="statisticController.do?statistic2&SID=${SID }" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回重新查询</a>
<p><font size="3" face="arial" color="red">${date_begin}(0时)至${date_end}(0时)特定问题提交情况统计：<br>${q.question }</font></p>
<table border="1px\" cellpadding="5\" cellspacing="0">

<tr>
<td>选项</td><td>提交人次总数</td>
</tr>

<c:forEach items="${aList}" var="sa"><tr><td>${sa.key }</td><td>${sa.value }</td></tr></c:forEach>

</table>

</body>
</html>