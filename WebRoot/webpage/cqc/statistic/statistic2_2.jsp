<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<!DOCTYPE html>
<html style="height: 100%">
   <head>
       <meta charset="utf-8">
   </head>
   <body style="height: 100%; margin: 0">
   <a href="statisticController.do?statistic2&SID=${SID }" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回重新查询</a>
       <div id="container" style="height: 100%"></div>
       <script type="text/javascript" src="webpage/cqc/statistic/ECharts/echarts.js"></script>
       <script type="text/javascript">
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
		title: {
	        text: "${date_begin}" + '(0时)至' + "${date_end}" + '(0时)\n特定问题(' + "${q.question}" +  ')统计'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:['提交人次总数']
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
	
	/*
	option = {
    title: {
        text: 'ECharts 入门示例'
    },
    tooltip: {},
    legend: {
        data:['销量']
    },
    xAxis: {
        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
    }]
};
	*/
	getChartData(option);
    myChart.setOption(option, true);
    
function getChartData(option) {
	//通过Ajax获取数据  
	$.ajax({
		type : "post",
		async : false, //同步执行  
		url : "statisticController.do?getC2",
		data : {
			QID : "${QID}",
			date_begin : "${date_begin}",
			date_end : "${date_end}"
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