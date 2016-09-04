<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<!DOCTYPE html>
<html style="height: 100%">
   <head>
       <meta charset="utf-8">
   </head>
   <body style="height: 100%; margin: 0">
   <a href="statisticController.do?statistic1&SID=${SID }" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回重新查询</a>
       <div id="container" style="height: 100%"></div>
       <script type="text/javascript" src="webpage/cqc/statistic/ECharts/echarts.js"></script>
       <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
	    title: {
	        text: "${date_begin}" + '(0时)至' + "${date_end}" + '(0时) 提交人次总数统计'
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
	        data: []
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [
	        {
	            name:'提交人次总数/天',
	            type:'line',
	            stack: '总量',
	            data:[]
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
		url : "statisticController.do?getC1&SID=${SID }",
		data : {
			date_begin : "${date_begin}",
			date_end : "${date_end}"
		},
		dataType : "json", //返回数据形式为json  
		success : function(data) {
			 if (data) {
				 option.xAxis.data = data.xname;
				 option.series[0].data = data.yvalue;
			} 
		}
	});
}
       </script>
   </body>
</html>