<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
<title>编辑问题</title>
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
	 				if(validate_required(providedChoice[0], "必须选择自定义字典或填写自定义选项!")==false)
	 					{
	 					providedChoice[0].focus();
	 					return false;
	 					}
	 		}
}
</script>
</head>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="questionaireController.do?saveQuestion" beforeSubmit="check()">
	<fieldset class="step">
		<input type="hidden" id="id" name="id" value="${qq.id }">
		<input type="hidden" id="orderNo" name="orderNo" value="${qq.orderNo }">
		<input type="hidden" id="blockId" name="blockId" value="${qq.blockId }">
		<input type="hidden" id="mainId" name="mainId" value="${qq.mainId }">
	<div class="form">
		<label class="Validform_label"> 问题序号: </label> 
			${qq.orderNo }
	</div>
	<div class="form">
		<label class="Validform_label"> 问题描述: </label> 
			<input name="question" class="inputxt" value="${qq.question }" style="width:600px;" placeholder="请输入2-50个字符。"> 
	</div>
	<div class="form">
		<label class="Validform_label"> 答案表示控件: </label> 
			<select name="tool" style="width:70px;">
					<option value="${qq.tool }">${qq.tool }</option>
				<c:if test="${qq.tool != '<%=Constant.INPUT_RADIO %>'}">
					<option value="<%=Constant.INPUT_RADIO %>"><%=Constant.INPUT_RADIO %></option>
				</c:if>
				<c:if test="${qq.tool != '<%=Constant.INPUT_SELECT %>'}">
					<option value="<%=Constant.INPUT_SELECT %>"><%=Constant.INPUT_SELECT %></option>
				</c:if>
				<c:if test="${qq.tool != '<%=Constant.INPUT_TEXT %>'}">
					<option value="<%=Constant.INPUT_TEXT %>"><%=Constant.INPUT_TEXT %></option>
				</c:if>
				</select>
	</div>
	<div class="form">
		<label class="Validform_label"> 选用字典: </label> 
				<select name="dictionary" style="width:90px;">
					<c:if test="${qq.dictionary == ''  ||  qq.dictionary ==null}"><option value="${qq.dictionary }"></option></c:if>
					<c:forEach items="${dictionarys }" var="ad">
						<c:if test="${qq.dictionary == ad.id }"><option value="${ad.id }">${ad.head }</option></c:if>
					</c:forEach>
					<c:if test="${qq.dictionary != ''  ||  qq.dictionary !=null}"><option value=""></option></c:if>
					<c:forEach items="${dictionarys }" var="ad">
						<c:if test="${qq.dictionary != ad.id }"><option value="${ad.id }">${ad.head }</option></c:if>
					</c:forEach>
				</select>
	</div>
	<div class="form">
		<label class="Validform_label"> 自定义选项: </label> 
			<input type="text" name="providedChoice" value="${qq.providedChoice }" placeholder="中文分号分隔，选字典不填">
	</div>
	<div class="form">
		<label class="Validform_label">单选题格式: </label> 
			<select name="format" style="width:65px;">
						<option value="${qq.format }"><c:if test="${qq.format != 0}">${qq.format }项/行</c:if></option>
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
