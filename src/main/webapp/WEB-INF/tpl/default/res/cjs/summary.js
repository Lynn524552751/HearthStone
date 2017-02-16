$(document).ready(function(){
	initPage();
});

function initPage(){
	console.log("summary init");
  	//
	jsReset();
	//
	getSummary();
	//
}

//Alertify JS
function jsReset () {
	alertify.set({
		labels: {
            ok: "确定",
            cancel: "取消"
        },
      	delay: 5000,
      	buttonReverse: false,
      	buttonFocus: "ok"
	});
}
//
function getSummary () {
	SummaryCtrl.loadSummary(  { callback: function( res ){		
		console.log(res);
		setTotal(res.data.total);	
		setPlayed(res.data.played);
		setAverage(res.data.played);
		setWins(res.data.wins)
	}, errorHandler: function( e, re ){
		alertify.error("加载失败！：SummaryCtrl.getSummary");
	}, timeout: 3000 } );			
}
//
function setTotal (data){
	var games = data.winsSum + data.lossesSum;
	
	$("#runs").text(data.count);
	$("#totalGames").text(games+" ("+data.winsSum+"-"+data.lossesSum+")");
	$("#averageWins").text(Digit.round(data.winsSum/data.count,2));
	$("#winRate").text(Digit.round((data.winsSum/games)*100,2)+"%");
	$("#goldAverage").text(Digit.round(data.goldSum/data.count,2));
	$("#dustTotal").text(data.dustSum);
}
//
function setPlayed (data){
	var legendData = [];
	var seriesData = [];
	for(var i=0;i<data.length;i++){
		var name = getOccupationName(data[i].name);
		legendData[i] = name
		
		seriesData[i] = {};
		seriesData[i].value = parseInt(data[i].count);
		seriesData[i].name = name;
	}
	
	var myChart = echarts.init(document.getElementById("playedEchart"));
	option = {
		    title : {
		        text: '竞技场职业选择',
		        subtext: '纯属虚构',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: legendData
		    },
		    series : [
		        {
		            name: '职业',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data: seriesData,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ],
		    color : ['#ff7d0a', '#abd473', '#69ccf0',
		             '#f58cba', '#dddddd', '#fff569', 
		             '#0070de', '#9482c9', '#c79c6e']
		};
	myChart.setOption(option);
}
//
function setAverage (data){
	var legendData = [];
	var seriesData = [];
	for(var i=0;i<data.length;i++){
		var name = getOccupationName(data[i].name);
		legendData[i] = name;
		
		seriesData[i] = {};
		seriesData[i].value = Digit.round(data[i].winsSum/data[i].count,2);
		seriesData[i].name = name;
	}
	
	var myChart = echarts.init(document.getElementById("averageEchart"));
	option = {

		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : legendData,
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		              {
		                  name:'场均',
		                  type:'bar',
		                  barWidth: '60%',
		                  data:seriesData,
		                  itemStyle: {
		                      normal: {
		                          color: function(params) {
		                              // build a color map as your need.
		                              var colorList = ['#ff7d0a', '#abd473', '#69ccf0',
		                          		             '#f58cba', '#dddddd', '#fff569',
		                        		             '#0070de', '#9482c9', '#c79c6e'];
		                              return colorList[params.dataIndex]
		                          }
		                      }
		                  }
		              }
		          ],
		    color : ['#ff7d0a', '#abd473', '#69ccf0',
		             '#f58cba', '#dddddd', '#fff569',
		             '#0070de', '#9482c9', '#c79c6e']
		};
	myChart.setOption(option);
}
//
function setWins (data){
	var legendData = [];
	var seriesData = [];
	for(var i=0;i<data.length;i++){
		legendData[i] = i+"胜";
		
		seriesData[i] = {};
		seriesData[i].value = parseInt(data[i].count);
		seriesData[i].name = name;
	}
	
	var myChart = echarts.init(document.getElementById("winsEchart"));
	option = {

		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : legendData,
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		              {
		                  name:'次数',
		                  type:'bar',
		                  barWidth: '60%',
		                  data:seriesData
		              }
		          ],
			color: ['#3398DB'],
		};
	myChart.setOption(option);
}
//
function getOccupationName (name){
	switch(name)
	{
	case "hunter":
		return "猎人"
		break;
	case "mage":
		return "法师"
		break;
	case "paladin":
		return "圣骑士"
		break;
	case "preist":
		return "牧师"
		break;
	case "rouge":
		return "盗贼"
		break;
	case "warlock":
		return "术士"
		break;
	case "druid":
		return "德鲁伊"
		break;
	case "shaman":
		return "萨满"
		break;
	case "warrior":
		return "战士"
		break;
	default:
		return ""
		break;
	}
}
//
var Digit = {};
Digit.round = function(digit, length) {
    length = length ? parseInt(length) : 0;
    if (length <= 0) return Math.round(digit);
    digit = Math.round(digit * Math.pow(10, length)) / Math.pow(10, length);
    return digit;
};

