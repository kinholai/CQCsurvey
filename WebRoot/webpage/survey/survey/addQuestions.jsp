<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
<title>录入问题</title>
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

 function validate_form(thisform)
 {
 	with (thisform)
   {
 		var extraNum = "${sHead.extraSum}";
 		for(var i=0; i < extraNum; i++)
 			{
 				var questions = document.getElementsByName("extras["+i +"].question");
 			 	if (validate_required(questions[0], "必须填写问卷问题!")==false)
 		     	{	questions[0].focus();
 		     		return false;
 		     	}
 			}
 		var normalNum = "${sHead.normalSum}";
 		for(var i=0; i < normalNum; i++)
 			{
 				var questions = document.getElementsByName("normals["+i +"].question");
			 	if (validate_required(questions[0], "必须填写问卷问题!")==false)
		     	{	questions[0].focus();
		     		return false;
		     	}
 			}
   }
 }
 </script>
</head>
<body style="overflow-y: auto" scroll="no">
<t:formvalid formid="formobj" layout="table" dialog="true" action="surveyController.do?saveQuestions" beforeSubmit="validate_form(this)">
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
		</tr>
			<c:forEach items="${extras}" var="poVal" varStatus="stuts">
			<tr>
			<td>${stuts.index+1 }<input type="hidden" name="extras[${stuts.index }].orderNo" value="${stuts.index+1 }"></td>	
			
			<td><input type="text" name="extras[${stuts.index }].question" value="${poVal.question }" style="width:600px;"></td>
			
			<td><input type="text" style="width:35px;" name="extras[${stuts.index }].weight" value="${poVal.weight}"></td>
			
			<td align="left">
				<select name="extras[${stuts.index }].tool" style="width:70px;">
					<option value="<%=Constant.INPUT_RADIO %>"><%=Constant.INPUT_RADIO %></option>
					<option value="<%=Constant.INPUT_SELECT %>"><%=Constant.INPUT_SELECT %></option>
					<option value="<%=Constant.INPUT_TEXT %>"><%=Constant.INPUT_TEXT %></option>
				</select>
			</td>
			
			<td align="left">
				<select name="extras[${stuts.index }].dictionary" style="width:90px;">
						<option value=""></option>
					<c:forEach items="${dictionarys }" var="ad">
						<option value="${ad.id }">${ad.head }</option>
					</c:forEach>
				</select>
			</td>
			
			<td><input type="text" name="extras[${stuts.index }].providedChoice" value="${poVal.providedChoice }" placeholder="中文分号分隔，选字典不填"></td>
		
			<td align="left">
				<select name="extras[${stuts.index }].format" style="width:65px;">
						<option value="0"></option>
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
		
			<td><input type="hidden" name="extras[${stuts.index }].headId" value="${headId }"></td>
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
		</tr>
			<c:forEach items="${normals}" var="poVal" varStatus="stuts">
			<tr>
			<td>${stuts.index+1 }<input type="hidden" name="normals[${stuts.index }].orderNo" value="${stuts.index+1 }"></td>	
			
			<td><input type="text" name="normals[${stuts.index }].question" value="${poVal.question }" style="width:600px;"></td>
			
			<td><input type="text" style="width:35px;" name="normals[${stuts.index }].weight" value="${poVal.weight}"></td>
			
			<td align="left">
				<select name="normals[${stuts.index }].tool" style="width:70px;">
					<option value="<%=Constant.INPUT_RADIO %>"><%=Constant.INPUT_RADIO %></option>
					<option value="<%=Constant.INPUT_SELECT %>"><%=Constant.INPUT_SELECT %></option>
					<option value="<%=Constant.INPUT_TEXT %>"><%=Constant.INPUT_TEXT %></option>
				</select>
			</td>
			
			<td align="left">
				<select name="normals[${stuts.index }].dictionary" style="width:90px;">
						<option value=""></option>
					<c:forEach items="${dictionarys }" var="ad">
						<option value="${ad.id }">${ad.head }</option>
					</c:forEach>
				</select>
			</td>
			
			<td><input type="text" name="normals[${stuts.index }].providedChoice" value="${poVal.providedChoice }" placeholder="中文分号分隔，选字典不填"></td>
		
			<td align="left">
				<select name="normals[${stuts.index }].format" style="width:65px;">
						<option value="0"></option>
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
		
			<td><input type="hidden" name="normals[${stuts.index }].headId" value="${headId }"></td>
		</tr>						
		</c:forEach>
							
	</tbody>
	</table>
</t:formvalid>
</body>
</html>
