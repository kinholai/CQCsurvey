<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>调查编辑</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="satisfactionController.do?saveSurvey">
	<input id="id" name="id" type="hidden" value="${survey.id}">
	<table style="width: 870px; height: 500px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 标题: </label></td>
			<td class="value"><input name="title" class="inputxt" value="${survey.title }"></td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 分类: </label></td>
			<td class="value">
				<c:if test="${empty survey.typeId }">
                	<select name="typeId">
                		<c:forEach var="type"  items="${typeList }">
                			<option value="${type.id }">${type.typeName }</option>
                		</c:forEach>
                	</select>
                </c:if>
                <c:if test="${!empty survey.typeId }">
                	<select name="typeId">
	                	<c:forEach items="${typeList }" var="type">
	                		<c:if test="${type.id == survey.typeId }"><option value="${type.id }">${type.typeName }</option></c:if>
	                	 </c:forEach>
	                	 <c:forEach var="type" items="${typeList }">
	                		<c:if test="${type.id != survey.typeId }"><option value="${type.id }">${type.typeName }</option></c:if>
	                	 </c:forEach>
                	 </select>
                </c:if>
			</td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 是否置顶: </label></td>
			<td class="value">
				<select name="prority">
					<c:if test="${empty survey.prority }">
						<option value="0">否</option>
						<option value="1">是</option>
					</c:if>
					<c:if test="${!empty survey.prority && survey.prority==0 }">
						<option value="0">否</option>
						<option value="1">是</option>
					</c:if>
					<c:if test="${!empty survey.prority && survey.prority==1 }">
						<option value="1">是</option>
						<option value="0">否</option>
					</c:if>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 发布状态: </label></td>
			<td class="value">
				<select name="state">
					<c:if test="${empty survey.state }">
						<option value="1">正在发布</option>
						<option value="0">不发布</option>
					</c:if>
					<c:if test="${!empty survey.state && survey.state==0 }">
						<option value="0">不发布</option>
						<option value="1">正在发布</option>
					</c:if>
					<c:if test="${!empty survey.state && survey.state==1 }">
						<option value="1">正在发布</option>
						<option value="0">不发布</option>
					</c:if>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 选用调查: </label></td>
			<td class="value">
				<c:if test="${empty survey.surveyId }">
                	<select name="surveyId">
                		<c:forEach var="user"  items="${surveyList }">
                			<option value="${user.id }">${user.head }</option>
                		</c:forEach>
                	</select>
                </c:if>
                <c:if test="${!empty survey.surveyId }">
                	<select name="surveyId">
	                	<c:forEach items="${surveyList }" var="user">
	                		<c:if test="${user.id == survey.surveyId }"><option value="${user.id }">${user.head }</option></c:if>
	                	 </c:forEach>
	                	 <c:forEach var="user" items="${surveyList }">
	                		<c:if test="${user.id != survey.surveyId }"><option value="${user.id }">${user.head }</option></c:if>
	                	 </c:forEach>
                	 </select>
                </c:if>
			</td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 简要说明: </label></td>
			<td class="value"><textarea name="description" cols="100" rows="4" placeholder="不能超过255个字符">${survey.description }</textarea></td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 图片: </label></td>
			<td class="value"><t:ckfinder name="image" uploadType="Images" value="${survey.image}" width="100" height="60" buttonClass="ui-button" buttonValue="上传图片"></t:ckfinder></td>
		</tr>
	</table>
</t:formvalid>
</body>