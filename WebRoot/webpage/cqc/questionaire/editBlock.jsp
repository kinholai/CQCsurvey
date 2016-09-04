<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>编辑板块</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
function validate_required1(field,alerttxt)
{
with (field)
  {
  if (value<= 0 || value >18)
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
  if (value<= 0)
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
  if (value.length >50)
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
	var nameSize = document.getElementsByName("nameSize");
	var descriptionSize = document.getElementsByName("descriptionSize");
	var blockName = document.getElementsByName("blockName");
	
	 	if (validate_required3(blockName[0], "板块名称不能超过50个字符!")==false)
  		{	
	 		blockName[0].focus();
  			return false;
 		}
	if(validate_required1(nameSize[0], "字体大小的范围必须为1-18!")==false)
		{
			nameSize[0].focus();
  			return false;
		}
	if(validate_required1(descriptionSize[0], "字体大小的范围必须为1-18!")==false)
	{
			descriptionSize[0].focus();
			return false;
	}
}
</script>
</head>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="questionaireController.do?saveBlock2" beforeSubmit="check()">
	<fieldset class="step">
		<input type="hidden" id="id" name="id" value="${qb.id }">
		<input type="hidden" id="blockOrder" name="blockOrder" value="${qb.blockOrder }">
		<input type="hidden" id="primaryNum" name="primaryNum" value="${qb.primaryNum }">
		<input type="hidden" id="questionState" name="questionState" value="${qb.questionState }">
		<input type="hidden" id="mainId" name="mainId" value="${qb.mainId }">
	<div class="form">
		<label class="Validform_label"> 板块序号: </label> 
			${qb.blockOrder }
	</div>
	<div class="form">
		<label class="Validform_label"> 板块名称: </label> 
			<input name="blockName" class="inputxt" value="${qb.blockName }" placeholder="不填则不在问卷上显示。"> 
	</div>
	<div class="form">
		<label class="Validform_label"> 板块名称字体: </label> 
			<select name="nameFont">
				<option value="${qb.nameFont }">原来的字体</option>
				<option value="SimSun">宋体</option>
				<option value="SimHei">黑体</option>
				<option value="FangSong">仿宋</option>
				<option value="KaiTi">楷体</option>
				<option value="Microsoft YaHei">微软雅黑</option>
				<option value="Microsoft JhengHei">微软正黑体</option>
				<option value="NSimSun">新宋体</option>
				<option value="PMingLiU">新细明体</option>
				<option value="MingLiU">细明体</option>
				<option value="DFKai-SB">标楷体</option>
			</select>
	</div>
	<div class="form">
		<label class="Validform_label"> 板块名称字体大小: </label> 
			<input name="nameSize" class="inputxt" value="${qb.nameSize }" datatype="n"> 
	</div>
	<div class="form">
		<label class="Validform_label"> 板块解释性文字: </label> 
			<input name="blockDescription" class="inputxt" value="${qb.blockDescription }">
	</div>
	<div class="form">
		<label class="Validform_label"> 板块解释性文字字体: </label> 
			<select name="descriptionFont">
				<option value="${qb.descriptionFont }">原来的字体</option>
				<option value="SimSun">宋体</option>
				<option value="SimHei">黑体</option>
				<option value="FangSong">仿宋</option>
				<option value="KaiTi">楷体</option>
				<option value="Microsoft YaHei">微软雅黑</option>
				<option value="Microsoft JhengHei">微软正黑体</option>
				<option value="NSimSun">新宋体</option>
				<option value="PMingLiU">新细明体</option>
				<option value="MingLiU">细明体</option>
				<option value="DFKai-SB">标楷体</option>
			</select>
	</div>
	<div class="form">
		<label class="Validform_label"> 板块解释性文字字体大小: </label> 
			<input name="descriptionSize" class="inputxt" value="${qb.descriptionSize }" datatype="n">
	</div>
	</fieldset>
</t:formvalid>
</body>
</html>
