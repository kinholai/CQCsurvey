<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问卷调查</title>
<style type="text/css">
table {
	border-collapse: collapse;
	width: 100%;
}

table td,table th {
	padding: 1px;
	border: 1px solid #ddd;
	padding: 10px 10px;
	text-align: left;
}

table th {
	background-color: #eee;
}

table  th {
	font-weight: 500;
	margin-top: 10px;
	margin-bottom: 10px;
}

body {
	display: flex;
	flex-flow: column;
	font-size: 18px;
}

ol {
	height: 100%;
}

div#header {
	background-color: #99bbbb;
	text-align: center;
	align: center;
}

div#foot {
	background-color: #99bbbb;
	text-align: center;
	align: center;
}

div#last {
	text-align: center;
	padding: 30px;
}

.test {
	border-bottom: 1px solid #ddd;
	padding: 10px 10px;
	text-align: left;
}

.login-btn {
	width: 170px;
	height: 30px;
	border: none;
	background: #056daa;
	color: #ffffff;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	margin-right: 50px;
}

.container {
	background-color: white;
	width: 60%;
	height: auto;
	margin: auto;
}

.row {
	border: 1px solid #ddd;
	padding: 10px 10px;
	text-align: left;
}

.target {
	width: 90%;
	height: auto;
	border: 1px solid #ddd;
	text-align: left;
	padding: 5%;
}

.label {
	margin: 20px;
	padding-left: 20px;
	display: inline;
}

.suit{
    margin:30px auto ;
}
</style>

</head>
<body>
	<h1  class = "suit">${message }</h1>
	<div id=foot>@中国质量认证中心</div>
</body>
</html>