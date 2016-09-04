<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
<title>编辑问题</title>
<t:base type="jquery,easyui,tools"></t:base>

<script type="text/javascript">
//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select', this).each(function(){
			var $this = $(this), name = $this.attr('name'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
		});
	});
}

 $(document).ready(function(){
		$(".datagrid-toolbar").parent().css("width","auto");
		//将表格的表头固定
	    $("#jeecgOrderProduct_table").createhftable({
	    	height:'auto',
			width:'auto',
			fixFooter:false
			});
});
 </script>
</head>
<body style="overflow-y: auto" scroll="no">
<t:formvalid formid="formobj" layout="table" dialog="true" action="surveyController.do?saveQuestions">
<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table" style="width:1060px;">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">问题描述</td>
		<td align="center" bgcolor="#EEEEEE">权重</td>
		<td align="center" bgcolor="#EEEEEE">答案表示控件</td>
		<td align="center" bgcolor="#EEEEEE">选用字典</td>
		<td align="center" bgcolor="#EEEEEE">自定义选项</td>
		<td align="center" bgcolor="#EEEEEE">单选题格式</td>
		<td></td>
	</tr>
	<tbody id="add_jeecgOrderProduct_table">
		<tr>
		<td></td> 
		<td>个人信息类/非问卷调查主体问题：</td> 
		<td></td> 
		<td></td> 
		<td></td>
		<td> </td>
		<td> </td>
		<td> </td>
		</tr>
			<c:forEach items="${extras}" var="poVal" varStatus="stuts">
			<tr>
			<td>${stuts.index+1 }<input type="hidden" name="extras[${stuts.index }].orderNo" value="${poVal.orderNo }"></td>	
			
			<td><input type="text" name="extras[${stuts.index }].question" value="${poVal.question }" style="width:600px;"></td>
			
			<td><input type="text" style="width:35px;" name="extras[${stuts.index }].weight" value="${poVal.weight}"></td>
			
			<td align="left">
				<select name="extras[${stuts.index }].tool" style="width:70px;">
					<option value="${poVal.tool }">${poVal.tool }</option>
					<c:if test="${poVal.tool != '<%=Constant.INPUT_RADIO %>'}">
					<option value="<%=Constant.INPUT_RADIO %>"><%=Constant.INPUT_RADIO %></option>
					</c:if>
					<c:if test="${poVal.tool != '<%=Constant.INPUT_SELECT %>'}">
					<option value="<%=Constant.INPUT_SELECT %>"><%=Constant.INPUT_SELECT %></option>
					</c:if>
					<c:if test="${poVal.tool != '<%=Constant.INPUT_TEXT %>'}">
					<option value="<%=Constant.INPUT_TEXT %>"><%=Constant.INPUT_TEXT %></option>
					</c:if>
				</select>
			</td>
			
			<td align="left">
				<select name="extras[${stuts.index }].dictionary" style="width:90px;">
					<c:if test="${poVal.dictionary == ''  ||  poVal.dictionary ==null}"><option value="${poVal.dictionary }"></option></c:if>
					<c:forEach items="${dictionarys }" var="ad">
						<c:if test="${poVal.dictionary == ad.id }"><option value="${ad.id }">${ad.head }</option></c:if>
					</c:forEach>
					<c:forEach items="${dictionarys }" var="ad">
						<c:if test="${poVal.dictionary != ad.id }"><option value="${ad.id }">${ad.head }</option></c:if>
					</c:forEach>
				</select>
			</td>
			
			<td><input type="text" name="extras[${stuts.index }].providedChoice" value="${poVal.providedChoice }" placeholder="中文分号分隔，选字典不填"></td>
		
			<td align="left">
				<select name="extras[${stuts.index }].format" style="width:65px;">
						<option value="${poVal.format }"><c:if test="${poVal.format != 0}">${poVal.format }项/行</c:if></option>
						<option value="1">1项/行</option>
						<option value="2">2项/行</option>
						<option value="3">3项/行</option>
						<option value="4">4项/行</option>
						<option value="5">5项/行</option>
						<option value="6">6项/行</option>
						<option value="7">7项/行</option>
						<option value="8">8项/行</option>
				</select>
			</td>
		
			<td><input type="hidden" name="extras[${stuts.index }].headId" value="${poVal.headId }"></td>
			<td><input type="hidden" name="extras[${stuts.index }].id" value="${poVal.id }"></td>
		</tr>						
		</c:forEach>	
		
		<tr>
		<td></td> 
		<td>问卷调查主体问题：</td> 
		<td></td> 
		<td></td> 
		<td></td>
		<td> </td>
		<td> </td>
		<td> </td>
		</tr>
			<c:forEach items="${normals}" var="poVal" varStatus="stuts">
			<tr>
			<td>${stuts.index+1 }<input type="hidden" name="normals[${stuts.index }].orderNo" value="${poVal.orderNo }"></td>	
			
			<td><input type="text" name="normals[${stuts.index }].question" value="${poVal.question }" style="width:600px;"></td>
			
			<td><input type="text" style="width:35px;" name="normals[${stuts.index }].weight" value="${poVal.weight}"></td>
			
			<td align="left">
				<select name="normals[${stuts.index }].tool" style="width:70px;">
					<option value="${poVal.tool }">${poVal.tool }</option>
					<c:if test="${poVal.tool != '<%=Constant.INPUT_RADIO %>'}">
					<option value="<%=Constant.INPUT_RADIO %>"><%=Constant.INPUT_RADIO %></option>
					</c:if>
					<c:if test="${poVal.tool != '<%=Constant.INPUT_SELECT %>'}">
					<option value="<%=Constant.INPUT_SELECT %>"><%=Constant.INPUT_SELECT %></option>
					</c:if>
					<c:if test="${poVal.tool != '<%=Constant.INPUT_TEXT %>'}">
					<option value="<%=Constant.INPUT_TEXT %>"><%=Constant.INPUT_TEXT %></option>
					</c:if>
				</select>
			</td>
			
			<td align="left">
				<select name="normals[${stuts.index }].dictionary" style="width:90px;">
					<c:if test="${poVal.dictionary == '' ||  poVal.dictionary ==null}"><option value="${poVal.dictionary }"></option></c:if>
					<c:forEach items="${dictionarys }" var="ad">
						<c:if test="${poVal.dictionary == ad.id }"><option value="${ad.id }">${ad.head }</option></c:if>
					</c:forEach>
					<c:forEach items="${dictionarys }" var="ad">
						<c:if test="${poVal.dictionary != ad.id }"><option value="${ad.id }">${ad.head }</option></c:if>
					</c:forEach>
				</select>
			</td>
			
			<td><input type="text" name="normals[${stuts.index }].providedChoice" value="${poVal.providedChoice }" placeholder="中文分号分隔，选字典不填"></td>
		
			<td align="left">
				<select name="normals[${stuts.index }].format" style="width:65px;">
						<option value="${poVal.format }"><c:if test="${poVal.format != 0}">${poVal.format }项/行</c:if></option>
						<option value="1">1项/行</option>
						<option value="2">2项/行</option>
						<option value="3">3项/行</option>
						<option value="4">4项/行</option>
						<option value="5">5项/行</option>
						<option value="6">6项/行</option>
						<option value="7">7项/行</option>
						<option value="8">8项/行</option>
				</select>
			</td>
		
			<td><input type="hidden" name="normals[${stuts.index }].headId" value="${poVal.headId }"></td>
			<td><input type="hidden" name="normals[${stuts.index }].id" value="${poVal.id }"></td>
		</tr>						
		</c:forEach>
							
	</tbody>
	</table>
</t:formvalid>
</body>
</html>
