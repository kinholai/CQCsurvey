<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:datagrid name="questionManagement" title="问题管理" checkbox="false" actionUrl="questionaireController.do?questions&bid=${bid }" idField="id" fit="true" queryMode="group" pagination="false" sortName="orderNo" sortOrder="asc">
   			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="所属问卷标题" field="mainId" width="120" dictionary="questionaire_main, id, head"></t:dgCol>
            <t:dgCol title="板块序号" field="blockId" width="100" dictionary="questionaire_block, id, block_order"></t:dgCol>
             <t:dgCol title="问题序号" field="orderNo" width="40"></t:dgCol>
             <t:dgCol title="问题" field="question" width="120"></t:dgCol>
             <t:dgCol title="控件" field="tool" width="100"></t:dgCol>
             <t:dgCol title="格式(项/行)" field="format" width="100"></t:dgCol>
             <t:dgCol title="common.operation" field="opt"></t:dgCol>
             <t:dgDelOpt url="questionaireController.do?delQuestion&id={id}&blockId={blockId}&orderNo={orderNo}" title="删除"></t:dgDelOpt>
  </t:datagrid>
  <div id="questionManagementtb" style="padding: 5px; height: 25px">
	<div style="float: left;">
		<a href="javascript:history.go(-1)" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add1('补充录入问题','questionaireController.do?anotherQuestion','questionManagement')">补充录入问题</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑问题','questionaireController.do?editQuestion','questionManagement')">编辑问题</a>
	</div>
</div>
<script type="text/javascript">
function add1(title,url, id,width,height) {
	gridname=id;
	url += '&bid=' + "${bid }";
	createwindow(title,url,780,520);
}

function update1(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择一个问题');
		return;
	}
	if (rowsData.length>1) {
		tip('一次只能对一个问卷进行问题录入');
		return;
	}
	
	url += '&id='+rowsData[0].id;
	createwindow(title,url,780,520);
}

function updatebytab()
{
	var rows = $("#questionManagement").datagrid("getSelections");
		if(rows=='')
		{
			alertTip('请您先选择一个板块。');
			return;
		}
		var id=rows[0].id;
		var questionState = rows[0].questionState;
		if(questionState == 0)
		{
			alertTip('请您先录入板块的所有问题。');
			return;
		}
		//var title =findThisTitle();
		document.location="questionaireController.do?questionManagement&id="+id;
		//addOneTab("TAB方式编辑", "demoController.do?aorudemo&id="+id);
		//closeThisTab(title);
	}

function closeThisTab(title){
    window.top.$('#maintabs').tabs('close',title); 
}
function findThisTitle(){
	var currTab =  window.top.$('#maintabs').tabs('getSelected'); 
	var tab = currTab.panel('options').tab;
    var title=tab.text();
    return title;
}
</script>