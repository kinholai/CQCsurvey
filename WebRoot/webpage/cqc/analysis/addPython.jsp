<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文件上传</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
		
	function uploadFile(data)
	{
  		if(!data.success)
  			tip('请不要留空任何信息。');
  		else 
  			{
  				if($(".uploadify-queue-item").length>0){
  	  				upload();
  	  			}else{
  	  				tip('请先上传文件。');
  	  			//frameElement.api.opener.reloadTable();
  	  			//frameElement.api.close();
  	  			}
  			}
  			
  	}
	
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" refresh="true" action="statisticController.do?setObject" callback="@Override uploadFile">
<input id="analysisHeadId" name="analysisHeadId" type="hidden" value="${analysisHeadId }">

<fieldset class="step">
	<div class="form">
        <label class="Validform_label"> 备注说明: </label>
        <input name="remark" class="inputxt" value="">
    </div>
    
    <div class="form">
		<t:upload name="fiels" buttonText="上传脚本文件" uploader="statisticController.do?savePython" extend="*.py;" id="file_upload" formId="formobj" multi="false"></t:upload>
	</div>
	<div class="form" id="filediv" style="height: 50px">注意：不用等待进度条，直接点击”确定“即可。</div> 
    
</fieldset>
</t:formvalid> 
</body>
</html>
