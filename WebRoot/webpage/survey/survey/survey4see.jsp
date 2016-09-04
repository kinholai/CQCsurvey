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

 <script type="text/javascript">
	var p_tag, inputs, selectInputs = [];
	onload = function() {
		p_tag = document.getElementById("inputsParent");
		inputs = p_tag.getElementsByTagName("input");
		for (var i = 0; i < inputs.length; i++) {
			if (inputs[i].checked == true)
				selectInputs.push(inputs[i]);
		}
	//	document.title = selectInputs.length;
	}

	function check_count(th) {
		var i = n = 0;
		if (th.checked == true) {
			selectInputs.push(th);
			if (selectInputs.length > 3) {
				selectInputs[0].checked = false;
				selectInputs.shift();
			}
		} else {
			if (selectInputs.length > 3) {
				for (var i = 0; i < selectInputs.length; i++) {
					if (th == selectInputs[i])
						selectInputs.splice(i, 1);
				}
			} else {
				th.checked = true;
				return false;
			}
		}
	}
</script>

</head>
<body>
	<c:if test="${sHead.lifeState == 0 }">
		<h1  class = "suit">问卷无效或问卷未开放，请联系管理员。</h1>
	</c:if>
	<c:if test="${sHead.lifeState != 0 }">
		<div id=header>
			<h1>
				<font color="red"> ${sHead.head }</font>
			</h1>
		</div>

	<div class="container">
		<form name="surveydata" action="" method="post">
			<div>
				<table>
				
					<c:forEach items="${extras}" var="poVal">
						${poVal.html }
					</c:forEach>
					
				</table>
			</div>

			<div class="row">
				
					<c:forEach items="${normals}" var="poVal">
						${poVal.html }
					</c:forEach>
					
			</div>

<!-- 
			<div id="last">
				<input type="submit" class="login-btn" value="提交">
			</div>
 -->
		</form>

	</div>

	</c:if>
	<div id=foot>@中国质量认证中心</div>
</body>
</html>