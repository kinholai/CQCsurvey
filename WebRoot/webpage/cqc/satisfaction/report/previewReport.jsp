<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>报告全文预览</title>
<style type="text/css">
a {
	color: Black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: none;
}
</style>
</head>
<body>
<div style="width: 980px; margin-left: auto; margin-right: auto;">${report.content}</div>
<!-- 底部 -->
<div region="south" border="false" style="height: 25px; overflow: hidden;">
</div>
</body>