<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div id="roles_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="type1List" title="调查分类管理" actionUrl="satisfactionController.do?typeList&category=${category }"
                    idField="id" treegrid="false" pagination="false">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="分类名称" field="typeName" width="100"></t:dgCol>
            <t:dgCol title="对象" field="category" hidden="true"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt url="satisfactionController.do?delType&id={id}&category={category}" title="删除"></t:dgDelOpt>
            <t:dgToolBar title="调查分类录入" icon="icon-add" url="satisfactionController.do?aouType&category=${category }" funname="add"></t:dgToolBar>
            <t:dgToolBar title="调查分类编辑" icon="icon-edit" url="satisfactionController.do?aouType&category=${category }" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>


<script type="text/javascript">

</script>
