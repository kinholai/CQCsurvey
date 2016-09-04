<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="satisfactionController.do?saveUser">
	<input id="id" name="id" type="hidden" value="${user.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="15%" nowrap>
                <label class="Validform_label">  用户名: </label>
            </td>
			<td class="value" width="85%">
                <c:if test="${user.id!=null }"> ${user.username } <input id="userame" name="username" type="hidden" value="${user.username }" /></c:if>
                <c:if test="${user.id==null }">
                    <input id="userame" class="inputxt" name="username" validType="satisfaction_user,username,id" value="${user.username }" datatype="s2-10" />
                    <span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to20"/></span>
                </c:if>
            </td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> 真实姓名: </label></td>
			<td class="value" width="10%">
                <input id="realname" class="inputxt" name="realname" value="${user.realname }" datatype="s2-10">
                <span class="Validform_checktip"><t:mutiLang langKey="fill.realname"/></span>
            </td>
		</tr>
		<c:if test="${user.id==null }">
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.password"/>: </label></td>
				<td class="value">
                    <input type="password" class="inputxt" value="" name="password" plugin="passwordStrength" datatype="*6-18" errormsg="" />
                    <span class="passwordStrength" style="display: none;">
                        <span><t:mutiLang langKey="common.weak"/></span>
                        <span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    </span>
                    <span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
                </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.repeat.password"/>: </label></td>
				<td class="value">
                    <input id="repassword" class="inputxt" type="password" value="${user.password}" recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！">
                    <span class="Validform_checktip"><t:mutiLang langKey="common.repeat.password"/></span>
                </td>
			</tr>
		</c:if>
		<tr>
			<td align="right"><label class="Validform_label"> 角色名称: </label></td>
			<td class="value" nowrap>
                <c:if test="${empty roleId }">
                	<select name="roleId">
                		<c:forEach var="role "  items="${roleList }">
                			<option value="${role.id }">${role.roleName }</option>
                		</c:forEach>
                	</select>
                </c:if>
                <c:if test="${!empty roleId }">
                	<select name="roleId">
	                	<c:forEach items="${roleList }" var="role">
	                		<c:if test="${role.id == roleId }"><option value="${role.id }">${role.roleName }</option></c:if>
	                	 </c:forEach>
	                	 <c:forEach var="role" items="${roleList }">
	                		<c:if test="${role.id != roleId }"><option value="${role.id }">${role.roleName }</option></c:if>
	                	 </c:forEach>
                	 </select>
                </c:if>
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right" nowrap><label class="Validform_label">  <t:mutiLang langKey="common.phone"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="mobile" value="${user.mobile}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.tel"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="office" value="${user.office}" datatype="n" errormsg="办公室电话不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="email" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> 企业名称: </label></td>
			<td class="value" width="10%">
                <input id="enterpriseName" class="inputxt" name="enterpriseName" value="${user.enterpriseName }" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> 企业地址: </label></td>
			<td class="value" width="10%">
                <input id="enterpriseAddress" class="inputxt" name="enterpriseAddress" value="${user.enterpriseAddress }" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		
	</table>
</t:formvalid>
<%--update-end--Author:zhangguoming  Date:20140825 for：格式化页面代码 并 添加组织机构combobox多选框--%>
</body>