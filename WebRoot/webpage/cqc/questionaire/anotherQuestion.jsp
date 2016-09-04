<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
<title>补充录入问题</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
function validate_required(field,alerttxt)
{
with (field)
  {
  if (value==null || value=="")
    {
	   tip(alerttxt);
	   return false;
	   }
  else 
	   return true;
  }
}
function validate_required1(field,alerttxt)
{
with (field)
  {
  if (value.length >50 || value.length < 2)
    {
	   tip(alerttxt);
	   return false;
	   }
  else 
	   return true;
  }
}

function validate_required3(field,alerttxt)
{
with (field)
  {
  if (value.length >128)
    {
	   tip(alerttxt);
	   return false;
	   }
  else 
	   return true;
  }
}

function check()
{
		var tool = document.getElementsByName("tool");
	 	var dictionary = document.getElementsByName("dictionary");
	 	var providedChoice = document.getElementsByName("providedChoice");
	 	var question = document.getElementsByName("question");
		 	if (validate_required1(question[0], "请输入2-50个字符!")==false)
	     	{	question[0].focus();
	     		return false;
	     	}
	 	if(tool[0].value == '<%=Constant.INPUT_RADIO %>' || tool[0].value == '<%=Constant.INPUT_SELECT %>')
	 		{
	 			if(dictionary[0].value == "" || dictionary[0].value == null)
	 				{
	 				if(validate_required(providedChoice[0], "必须选择自定义字典或填写自定义选项!")==false)
	 					{
	 					providedChoice[0].focus();
	 					return false;
	 					}
	 				if(validate_required3(providedChoice[0], "自定义选项不应超过128个字符!")==false)
	 					{
	 					providedChoice[0].focus();
	 					return false;
	 					}
	 				}
	 		}
}
</script>
</head>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="questionaireController.do?saveAnotherQuestion" beforeSubmit="check()">
	<fieldset class="step">
		<input type="hidden" id="orderNo" name="orderNo" value="${num }">
		<input type="hidden" id="blockId" name="blockId" value="${blockId }">
		<input type="hidden" id="mainId" name="mainId" value="${mainId }">
	<div class="form">
		<label class="Validform_label"> 问题序号: </label> 
			${num }
	</div>
	<div class="form">
		<label class="Validform_label"> 问题描述: </label> 
			<input name="question" class="inputxt" value="" style="width:600px;"  placeholder="请输入2-50个字符。"> 
	</div>
	<div class="form">
		<label class="Validform_label"> 答案表示控件: </label> 
			<select name="tool" style="width:70px;">
					<option value="<%=Constant.INPUT_RADIO %>"><%=Constant.INPUT_RADIO %></option>
					<option value="<%=Constant.INPUT_SELECT %>"><%=Constant.INPUT_SELECT %></option>
					<option value="<%=Constant.INPUT_TEXT %>"><%=Constant.INPUT_TEXT %></option>
			</select>
	</div>
	<div class="form">
		<label class="Validform_label"> 选用字典: </label> 
			<select name="dictionary" style="width:90px;">
						<option value=""></option>
					<c:forEach items="${dictionarys }" var="ad">
						<option value="${ad.id }">${ad.head }</option>
					</c:forEach>
			</select>
	</div>
	<div class="form">
		<label class="Validform_label"> 自定义选项: </label> 
			<input type="text" name="providedChoice" value="" placeholder="中文分号分隔，选字典不填">
	</div>
	<div class="form">
		<label class="Validform_label">单选题格式: </label> 
			<select name="format" style="width:65px;">
						<option value="0">无</option>
						<option value="1">1项/行</option>
						<option value="2">2项/行</option>
						<option value="3">3项/行</option>
						<option value="4">4项/行</option>
						<option value="5">5项/行</option>
						<option value="6">6项/行</option>
						<option value="7">7项/行</option>
						<option value="8">8项/行</option>
				</select>
	</div>
	</fieldset>
</t:formvalid>
</body>
</html>
