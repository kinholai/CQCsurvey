<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="itemList" title="字典内容"
                    actionUrl="dictionaryController.do?itemList&headId=${headId}" idField="id"
                    queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="字典" field="head_id" hidden="true"></t:dgCol>
            <t:dgCol title="字典数据" field="item"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="dictionaryController.do?delItem&id={id}" title="删除"></t:dgDelOpt>
            <t:dgToolBar title="字典数据录入" icon="icon-add" url="dictionaryController.do?aouItem&headId=${headId}" funname="add"></t:dgToolBar>
            <t:dgToolBar title="字典数据编辑" icon="icon-edit" url="dictionaryController.do?aouItem&headId=${headId}" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script>
    function addType(title,addurl,gname,width,height) {
        alert($("#id").val());
        add(title,addurl,gname,width,height);
    }
</script>

