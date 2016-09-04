<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>首页图文编辑</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
		
	$(function() {
		if($('#tag').val()=='1'){
			$('#tag1').show();
			$('#tag2').hide();
		}else{
			$('#tag2').show();
			$('#tag1').hide();
		}
		
		$('#tag').change(function(){
			if($(this).val()=='1'){
				$('#tag1').show();
				$('#tag2').hide();
			}else{
				$('#tag2').show();
				$('#tag1').hide();
			}
		});
	});
	
	function validate_required(field,alerttxt)
	{
	with (field)
	  {
	  if (value.length >255 || value.length < 1)
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
		var description = document.getElementsByName("description");
		if(validate_required(description[0], "简要说明的字数范围为1-255!")==false)
		{
				description[0].focus();
				return false;
		}
	}
</script>

<link rel="stylesheet" type="text/css" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="satisfactionController.do?saveImage" beforeSubmit="check()">
<fieldset class="step">
	<input id="id" name="id" type="hidden" value="${image.id}">
		<div class="form">
			<label class="Validform_label"> 图片: </label>
			<t:ckfinder name="image" uploadType="Images" value="${image.image}" width="100" height="60" buttonClass="ui-button" buttonValue="上传图片"></t:ckfinder></td>
		</div>
		
		<div class="form">
			<label class="Validform_label"> 推介类型: </label>
				<select name="tag" id="tag">
					<c:if test="${empty image.tag }">
						<option value="1">正在发布</option>
						<option value="2">调查报告</option>
					</c:if>
					<c:if test="${!empty image.tag && image.tag==1 }">
						<option value="1">正在发布</option>
						<option value="2">调查报告</option>
					</c:if>
					<c:if test="${!empty image.tag && image.tag==2 }">
						<option value="2">调查报告</option>
						<option value="1">正在发布</option>
					</c:if>
				</select>
		</div>
		
		<div class="form" id="tag1">
			<label class="Validform_label"> 推介调查: </label>
				<c:if test="${empty image.tagId }">
                	<select name="tagId1">
                		<c:forEach var="survey"  items="${surveyList }">
                			<option value="${survey.id }">${survey.title }</option>
                		</c:forEach>
                	</select>
                </c:if>
                <c:if test="${!empty image.tagId }">
                	<select name="tagId1">
	                	<c:forEach items="${surveyList }" var="survey">
	                		<c:if test="${survey.id == image.tagId }"><option value="${survey.id }">${survey.title }</option></c:if>
	                	 </c:forEach>
	                	 <c:forEach var="survey" items="${surveyList }">
	                		<c:if test="${survey.id != image.tagId }"><option value="${survey.id }">${survey.title }</option></c:if>
	                	 </c:forEach>
                	 </select>
                </c:if>
		</div>
		
		<div class="form" id="tag2">
			<label class="Validform_label"> 推介报告: </label>
				<c:if test="${empty image.tagId }">
                	<select name="tagId2">
                		<c:forEach var="report"  items="${reportList }">
                			<option value="${report.id }">${report.title }</option>
                		</c:forEach>
                	</select>
                </c:if>
                <c:if test="${!empty image.tagId }">
                	<select name="tagId2">
	                	<c:forEach items="${reportList }" var="report">
	                		<c:if test="${report.id == image.tagId }"><option value="${report.id }">${report.title }</option></c:if>
	                	 </c:forEach>
	                	 <c:forEach var="report" items="${reportList }">
	                		<c:if test="${report.id != image.tagId }"><option value="${report.id }">${report.title }</option></c:if>
	                	 </c:forEach>
                	 </select>
                </c:if>
		</div>
		
		<div class="form">
			<label class="Validform_label"> 简要说明: </label>
			<textarea name="description" cols="100" rows="4" placeholder="不能超过255个字符">${image.description }</textarea>
		</div>
		
		
		<div class="form">
			<label class="Validform_label"> 是否置顶: </label>
				<select name="prority">
					<c:if test="${empty image.prority }">
						<option value="0">否</option>
						<option value="1">是</option>
					</c:if>
					<c:if test="${!empty image.prority && image.prority==0 }">
						<option value="0">否</option>
						<option value="1">是</option>
					</c:if>
					<c:if test="${!empty image.prority && image.prority==1 }">
						<option value="1">是</option>
						<option value="0">否</option>
					</c:if>
				</select>
		</div>
		
		
</fieldset>
</t:formvalid>
</body>