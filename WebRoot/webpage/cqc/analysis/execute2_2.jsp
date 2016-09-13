<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<!DOCTYPE html>
<html style="height: 100%">
   <head>
       <meta charset="utf-8">
   </head>
   <body style="height: 100%; margin: 0">
   <a href="statisticController.do?execute2_1&id=${AID }&headId=${SID }" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回重新查询</a>
       <div id="container" style="height: 100%"></div>
       <script type="text/javascript" src="webpage/cqc/statistic/ECharts/echarts.js"></script>
       <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
		title: {
	        text: '特定问题(' + "${q.question}" +  ')统计'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:['特定题目回答者平均总分']
	    },
	    grid: {
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis : {
	           // type : 'category',
	            data : [] // xname
	        }
	    ,
	    yAxis : {
	            type : 'value'
	        },
	    series : []
	};
	getChartData(option);
    myChart.setOption(option, true);
    
function getChartData(option) {
	//通过Ajax获取数据  
	$.ajax({
		type : "post",
		async : false, //同步执行  
		url : "statisticController.do?getC4",
		data : {
			AID : "${AID}",
			QID : "${QID}"
		},
		dataType : "json", //返回数据形式为json  
		success : function(data) {
			 if (data) {
				 
				option.xAxis.data = data.xname;
				 option.series = data.yvalue;
			} 
		}
	});
}
       </script>
   </body>
</html>