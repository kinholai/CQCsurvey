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
 
 function validate_form(thisform)
 {
 	with (thisform)
   {
 		var num = "${num}";
 		for(var i=0; i < num; i++)
 			{
 			 	var weight = document.getElementsByName("saqList["+i +"].weight");
 			 	if (validate_required(weight[0], "必须填写权重!")==false)
 		     	{	weight[0].focus();
 		     		return false;
 		     	}
 			 	if (validate_required2(weight[0], "权重必须大于0!")==false)
 		     	{	weight[0].focus();
 		     		return false;
 		     	}
 			 	
 			 	var markState = document.getElementsByName("saqList["+i +"].markState");
 			 	var mark = document.getElementsByName("saqList["+i +"].mark");
 			 	if(markState[0].value == 1)
 			 		{
 			 			var choiceNum = document.getElementsByName("saqList["+i +"].choiceNum");
 			 			if (validate_required(mark[0], "必须填写分值，并以中文引号隔开!")==false)
 		 		     	{	mark[0].focus();
 		 		     		return false;
 		 		     	}
 			 			var cnum = (mark[0].value.split('；')).length;
 			 			if(cnum != choiceNum[0].value)
 			 				{
 			 					tip("选项分值与选项个数不对应！");
 			 					mark[0].focus();
 	 		 		     		return false;
 			 				}
 			 		}
 			}
   }
 }
 </script>
</head>
<body>
<t:formvalid formid="formobj" layout="table" dialog="true" action="statisticController.do?saveSetting" beforeSubmit="validate_form(this)">
<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table" style="width:1060px;">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">问题</td>
		<td align="center" bgcolor="#EEEEEE">选项数量</td>
		<td align="center" bgcolor="#EEEEEE">权重</td>
		<td align="center" bgcolor="#EEEEEE">是否计分</td>
		<td align="center" bgcolor="#EEEEEE">选项分值</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tbody id="add_jeecgOrderProduct_table">
			<c:forEach items="${saqList}" var="poVal" varStatus="stuts">
			<tr>
			<td>${poVal.orderNo }<input type="hidden" name="saqList[${stuts.index }].orderNo" value="${poVal.orderNo }"></td>	
			
			<td><input type="text" name="saqList[${stuts.index }].question" value="${poVal.question }" style="width:550px;" readOnly="readonly"></td>
			
			<td><input type="text" style="width:35px;" name="saqList[${stuts.index }].choiceNum" value="${poVal.choiceNum }" readOnly="readonly"></td>
			
			<td><input type="text" style="width:35px;" name="saqList[${stuts.index }].weight" value="${poVal.weight }"></td>
			
			<td align="left">
				<c:if test="${poVal.choiceNum<=1 }">
					<select name="saqList[${stuts.index }].markState" style="width:100px;">
						<option value="0">主观题不计分</option>
					</select>
				</c:if>
				<c:if test="${poVal.choiceNum>1 }">
					<select name="saqList[${stuts.index }].markState" style="width:100px;">
						<c:if test="${poVal.markState==1 }">
							<option value="1">计分</option>
							<option value="0">不计分</option>
						</c:if>
						<c:if test="${poVal.markState==0 }">
							<option value="0">不计分</option>
							<option value="1">计分</option>
						</c:if>
					</select>
				</c:if>
			</td>
			
			<td align="left">
				<input type="text" name="saqList[${stuts.index }].mark" value="${poVal.mark }"style="width:240px;" placeholder="中文分号分隔，选“不计分”不填">
			</td>
			
			<td><input type="hidden" name="saqList[${stuts.index }].analysisId" value="${poVal.analysisId }"></td>
			<td><input type="hidden" name="saqList[${stuts.index }].questionId" value="${poVal.questionId }"></td>
			<td><input type="hidden" name="saqList[${stuts.index }].id" value="${poVal.id }"></td>
		</tr>						
		</c:forEach>	
	</tbody>
	</table>
</t:formvalid>
</body>
</html>
