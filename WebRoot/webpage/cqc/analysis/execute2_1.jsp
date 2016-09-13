<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title></title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  function submit1(URL){
	var QID = document.getElementsByName("QID");
	
	if(QID[0].value.length<1)
	{
		tip("请先选择查询问题！");
		QID[0].focus();
		return false;
	}
	
	statistic1.action=URL;
	statistic1.method="post";
	statistic1.submit();
}
  </script>
 </head>
 <body>
  <form id="statistic1">
  		<input type="hidden" name="SID" value="${SID }">
  		<input type="hidden" name="AID" value="${AID }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" align="center">
				<tr>
					<td align="right">
						<label class="Validform_label">
							查询问题:
						</label>
					</td>
					<td class="value">
					     	 <select id="QID" name="QID">
						     	 <c:if test="${fn:length(sList)  <= 0 }">
						     	 <option value="">---暂无数据---</option>
						     	 </c:if>
						     	 <c:if test="${fn:length(sList)  > 0 }">
					                <option value=""></option>
					                    <c:forEach items="${sList}" var="s">
					                        <option value="${s.id }">${s.question}</option>
					                    </c:forEach>
					             </c:if>
				            </select>
					</td>
				</tr>
		<tr>
		<td><br><br><br></td><td><br><br><br></td>
		</tr>
				
				<tr>
				<td align="right"><input type="button" id="sub" value="以柱状图显示结果"
				onclick="submit1('statisticController.do?execute2_2');"></td>
				<td align="center"><input type="button" id="sub" value="以数据表格显示结果"
				onclick="submit1('statisticController.do?execute2_3');"></td>
				</tr>
			</table>
		</form>
		<br/><br/><br/>
		 <center>提示：当数据量较大时，点击按钮后可能需等待稍长时间才有响应，请您耐心等待。</center>
 </body>
