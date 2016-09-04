<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<h1  class = "suit">没有问卷输出</h1>

	<div id=header>
		<h1>
			<font color="red"> 
			</font>
		</h1>
	</div>

	<div class="container">
		<form name="surveydata" action="AnswerServlet" method="post">
			<div>
				<table>
					<tr>
						<th>您所居住的城市</th>
						<td><select style="width: 300px" name="getcity">
									<option value=""></option>
								</select>
						</td>
					</tr>
					<tr>
						<th>您的年龄</th>
						<td><input type="radio" name="age" value="nianling1">18-24
							<input type="radio" name="age" value="nianling2">25-29 <input
							type="radio" name="age" value="nianling3">30-34 <input
							type="radio" name="age" value="nianling4">35-39 <input
							type="radio" name="age" value="nianling5">40-44<br /> <input
							type="radio" name="age" value="nianling6">45-49 <input
							type="radio" name="age" value="nianling7">50-54 <input
							type="radio" name="age" value="nianling8">55-59 <input
							type="radio" name="age" value="nianling9">60-64 <input
							type="radio" name="age" value="nianling10">65以上</td>
					</tr>
					<tr>
						<th>性别</th>
						<td>
						<input type="radio" name="sex" value="male">男
						 <input type="radio" name="sex" value="female">女
						 </td>
					</tr>
					<tr>
						<th>您的教育情况</th>
						<td><input type="radio" name="edu" value="xueli1">小学
							<input type="radio" name="edu" value="xueli2">初中/技校 <input
							type="radio" name="edu" value="xueli3">高中/中专 <input
							type="radio" name="edu" value="xueli4">大专 <input
							type="radio" name="edu" value="xueli5">大学 <input
							type="radio" name="edu" value="xueli6">研究生以上</td>
					</tr>
					<tr>
						<th>您的职业</th>
						<td><input type="radio" name="job" value="zhiye1">党政机关人员
							<input type="radio" name="job" value="zhiye2">事业单位人员 <input
							type="radio" name="job" value="zhiye3">专业技术人员<br /> <input
							type="radio" name="job" value="zhiye4">国有企业人员 <input
							type="radio" name="job" value="zhiye5">私营企业人员 <input
							type="radio" name="job" value="zhiye6">外资/合资企业人员<br /> <input
							type="radio" name="job" value="zhiye7">学生 <input
							type="radio" name="job" value="zhiye8">农民 <input
							type="radio" name="job" value="zhiye9">离职退休人员 <input
							type="radio" name="job" value="zhiye10">无业</td>
					</tr>
					<tr>
						<th>评价的产品</th>
						<td><input type="radio" name="Product" value="chapin1">粮油
							<input type="radio" name="Product" value="chapin2">肉制品 <input
							type="radio" name="Product" value="chapin3">乳制品 <input
							type="radio" name="Product" value="chapin4">洗漱用品 <input
							type="radio" name="Product" value="chapin5">服装鞋袜 <input
							type="radio" name="Product" value="chapin6">纺织用品<br /> <input
							type="radio" name="Product" value="chapin7">药品 <input
							type="radio" name="Product" value="chapin8">汽车 <input
							type="radio" name="Product" value="chapin9">冰箱 <input
							type="radio" name="Product" value="chapin10">空调 <input
							type="radio" name="Product" value="chapin11 ">热水器 <input
							type="radio" name="Product" value="chapin12">电视机<br /> <input
							type="radio" name="Product" value="chapin13">厨房用品 <input
							type="radio" name="Product" value="chapin14">手机 <input
							type="radio" name="Product" value="chapin15">电脑及配件<br />
							<input type="radio" name="Product" value="chapin16">其他</td>
					</tr>
					<tr>
						<th>您所使用该产品的品牌</th>
						<td><input type="text" name="brand" value="brand"></td>
					</tr>
				</table>
			</div>

			<div class="row">
					<div class="test">
						
							<input type="radio" value="1">非常满意<br />
							<input type="radio" value="2">满意<br />
							<input type="radio" value="3">一般<br />
							<input type="radio" value="4">不满意<br />
							<input type="radio" value="5">非常不满意<br />
						
					</div>
			</div>

			<div class="target">
				<p class="p">针对如何提高产品的质量水平，您有什么建议？</p>
				<p class="p">请从以下建议中选择其中您觉得最重要的三个：</p>
				<br />

				<div id="inputsParent">
					<div class="label">
						<input name="target" type="checkbox" value="" onclick="check_count(this)" checked="checked" /><label></label>
					</div>
					<div class="label">
						<input name="target" type="checkbox" value="" onclick="check_count(this)" /><label></label>
					</div>
					<br />
					<br />
				</div>
			</div>

			<div id="last">
				<input type="submit" class="login-btn" value="提交">
			</div>

		</form>

	</div>


	<div id=foot>@中国质量认证中心</div>
</body>
</html>