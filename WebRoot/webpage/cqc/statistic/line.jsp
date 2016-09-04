<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<!DOCTYPE html>
<html style="height: 100%">
   <head>
       <meta charset="utf-8">
   </head>
   <body style="height: 100%; margin: 0">
       <div id="container" style="height: 100%"></div>
       <script type="text/javascript" src="webpage/cqc/statistic/ECharts/echarts.js"></script>
       <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
	    title: {
	        text: '折线图堆叠'
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['提交人次总数/天']
	    },
	    grid: {
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: ['周一','周二','周三','周四','周五','周六','周日']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [
	        {
	            name:'提交人次总数/天',
	            type:'line',
	            stack: '总量',
	            data:[120, 132, 101, 134, 90, 230, 210]
	        }
	    ]
	};
	getChartData(option);
    myChart.setOption(option, true);
    
function getChartData(option) {
	//通过Ajax获取数据  
	$.ajax({
		type : "post",
		async : false, //同步执行  
		url : "statisticController.do?getC2&CID=${CID }",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(data) {
			 if (data) {
				 option.legend.data[0] = data.data[0].name;
				 option.legend.data[1] = data.data[1].name;
				 option.legend.data[2] = data.data[2].name;
				 option.legend.data[3] = data.data[3].name;
				 option.legend.data[4] = data.data[4].name;
				 
				option.series[0].data[0] = data.data[0];
				option.series[0].data[1] = data.data[1];
				option.series[0].data[2] = data.data[2];
				option.series[0].data[3] = data.data[3];
				option.series[0].data[4] = data.data[4];
			} 
		}
	});
}
       </script>
   </body>
</html>