<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>报告编辑</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="satisfactionController.do?saveReport">
	<input id="id" name="id" type="hidden" value="${report.id}">
	<table style="width: 980px; height: 500px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 标题: </label></td>
			<td class="value"><input name="title" class="inputxt" value="${report.title }"></td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 分类: </label></td>
			<td class="value">
				<c:if test="${empty report.typeId }">
                	<select name="typeId">
                		<c:forEach var="type"  items="${typeList }">
                			<option value="${type.id }">${type.typeName }</option>
                		</c:forEach>
                	</select>
                </c:if>
                <c:if test="${!empty report.typeId }">
                	<select name="typeId">
	                	<c:forEach items="${typeList }" var="type">
	                		<c:if test="${type.id == report.typeId }"><option value="${type.id }">${type.typeName }</option></c:if>
	                	 </c:forEach>
	                	 <c:forEach var="type" items="${typeList }">
	                		<c:if test="${type.id != report.typeId }"><option value="${type.id }">${type.typeName }</option></c:if>
	                	 </c:forEach>
                	 </select>
                </c:if>
			</td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 是否置顶: </label></td>
			<td class="value">
				<select name="prority">
					<c:if test="${empty report.prority }">
						<option value="0">否</option>
						<option value="1">是</option>
					</c:if>
					<c:if test="${!empty report.prority && report.prority==0 }">
						<option value="0">否</option>
						<option value="1">是</option>
					</c:if>
					<c:if test="${!empty report.prority && report.prority==1 }">
						<option value="1">是</option>
						<option value="0">否</option>
					</c:if>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 企业用户: </label></td>
			<td class="value">
				<c:if test="${empty report.userId }">
                	<select name="userId">
                		<c:forEach var="user"  items="${userList }">
                			<option value="${user.id }">${user.username }</option>
                		</c:forEach>
                	</select>
                </c:if>
                <c:if test="${!empty report.userId }">
                	<select name="userId">
	                	<c:forEach items="${userList }" var="user">
	                		<c:if test="${user.id == report.userId }"><option value="${user.id }">${user.username }</option></c:if>
	                	 </c:forEach>
	                	 <c:forEach var="user" items="${userList }">
	                		<c:if test="${user.id != report.userId }"><option value="${user.id }">${user.username }</option></c:if>
	                	 </c:forEach>
                	 </select>
                </c:if>
			</td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 简要说明: </label></td>
			<td class="value"><textarea name="description" cols="100" rows="4" placeholder="不能超过255个字符">${report.description }</textarea></td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 报告全文内容: </label></td>
			<td class="value"><t:ckeditor name="content" isfinder="true" value="${report.content}" type="width:880,height:350"></t:ckeditor></td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 图片: </label></td>
			<td class="value"><t:ckfinder name="image" uploadType="Images" value="${report.image}" width="100" height="60" buttonClass="ui-button" buttonValue="上传图片"></t:ckfinder></td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 附件: </label></td>
			<td class="value"><t:ckfinder name="attachment" uploadType="Files" value="${report.attachment}" buttonClass="ui-button" buttonValue="上传附件"></t:ckfinder></td>
		</tr>
	</table>
</t:formvalid>
</body>