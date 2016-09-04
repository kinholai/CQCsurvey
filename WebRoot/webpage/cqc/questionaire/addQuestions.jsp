<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page language="java" import="cqc.survey.model.Constant"%>
<!DOCTYPE html>
<html>
<head>
<title>录入全部问题</title>
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

 function validate_required1(field,alerttxt)
 {
 with (field)
   {
   if (value.length >50)
     {
 	   tip(alerttxt);
 	   return false;
 	   }
   else 
 	   return true;
   }
 }
 
 function validate_required2(field,alerttxt)
 {
 with (field)
   {
   if (value < 0)
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
 
 function validate_form(thisform)
 {
 	with (thisform)
   {
 		var num = "${num}";
 		for(var i=0; i < num; i++)
 			{
 				var questions = document.getElementsByName("qqList["+i +"].question");
 			 	if (validate_required(questions[0], "必须填写问卷问题!")==false)
 		     	{	questions[0].focus();
 		     		return false;
 		     	}
 			 	if (validate_required1(questions[0], "问题字数不能超过50!")==false)
 		     	{	questions[0].focus();
 		     		return false;
 		     	}
 			 	
 			 	/*var weight = document.getElementsByName("qqList["+i +"].weight");
 			 	if (validate_required(weight[0], "必须填写权重!")==false)
 		     	{	weight[0].focus();
 		     		return false;
 		     	}
 			 	if (validate_required2(weight[0], "权重必须大于0!")==false)
 		     	{	weight[0].focus();
 		     		return false;
 		     	}*/
 			 	
 			 	var tool = document.getElementsByName("qqList["+i +"].tool");
 			 	var dictionary = document.getElementsByName("qqList["+i +"].dictionary");
 			 	var providedChoice = document.getElementsByName("qqList["+i +"].providedChoice");
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
   }
 }
 </script>
</head>
<body>
<t:formvalid formid="formobj" layout="table" dialog="true" action="questionaireController.do?saveQuestions" beforeSubmit="validate_form(this)">
<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table" style="width:1060px;">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">问题(自添序号)</td>
		<td align="center" bgcolor="#EEEEEE">答案表示控件</td>
		<td align="center" bgcolor="#EEEEEE">选用字典</td>
		<td align="center" bgcolor="#EEEEEE">自定义选项</td>
		<td align="center" bgcolor="#EEEEEE">单选题格式</td>
		<td></td>
		<td></td>
	</tr>
	<tbody id="add_jeecgOrderProduct_table">
			<c:forEach items="${qqList}" var="poVal" varStatus="stuts">
			<tr>
			<td>${stuts.index+1 }<input type="hidden" name="qqList[${stuts.index }].orderNo" value="${stuts.index+1 }"></td>	
			
			<td><input type="text" name="qqList[${stuts.index }].question" value="${poVal.question }" style="width:600px;"></td>
			
			<td align="left">
				<select name="qqList[${stuts.index }].tool" style="width:70px;">
					<option value="<%=Constant.INPUT_RADIO %>"><%=Constant.INPUT_RADIO %></option>
					<option value="<%=Constant.INPUT_SELECT %>"><%=Constant.INPUT_SELECT %></option>
					<option value="<%=Constant.INPUT_TEXT %>"><%=Constant.INPUT_TEXT %></option>
				</select>
			</td>
			
			<td align="left">
				<select name="qqList[${stuts.index }].dictionary" style="width:90px;">
						<option value=""></option>
					<c:forEach items="${dictionarys }" var="ad">
						<option value="${ad.id }">${ad.head }</option>
					</c:forEach>
				</select>
			</td>
			
			<td><input type="text" name="qqList[${stuts.index }].providedChoice" value="${poVal.providedChoice }" placeholder="中文分号分隔，选字典不填"></td>
		
			<td align="left">
				<select name="qqList[${stuts.index }].format" style="width:65px;">
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
			</td>
		
			<td><input type="hidden" name="qqList[${stuts.index }].blockId" value="${blockId }"></td>
			<td><input type="hidden" name="qqList[${stuts.index }].mainId" value="${mainId }"></td>
		</tr>						
		</c:forEach>	
	</tbody>
	</table>
</t:formvalid>
</body>
</html>
