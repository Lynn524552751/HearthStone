$(document).ready(function(){
	console.log("record init");
	//
	alertJS();
	//
  	Record._action = action;
  	Record._id = parseInt(dataId);
	//
	switch(action)
	{
	case "add":
		Record.add.init();
		break;
	case "edit":
		Record.edit.init();
		break;
	case "view":
		Record.view.init();
		break;
	case "list":
		Record.list.init();
		break;
	default:
		Record.list.init();
	}
});

var Record = {
		_action : null,
		_id : null,
		_data : [],
		_view : {
			occupation : $("#edit-occupation"),
			wins : $("#edit-wins"),
			losses : $("#edit-losses"),
			gold : $("#edit-gold"),
			dust : $("#edit-dust"),
			time : $("#edit-time")
		},
		goHref : function(action , id){
			var url = window.location.href;
			var index = url.indexOf("?");
			if( index > 0 ){ url = url.substring( 0,index );}
			isNaN(id) ? id = "" : id = "&id=" + id;
			url = url + "?" + action + id;
			window.location.href = url;
		},
		setData : function( data ){
			if(data == null){
				Record._view.occupation.val(-1);
				Record._view.wins.val(-1);
				Record._view.losses.val(-1);
				Record._view.gold.val("");
				Record._view.dust.val("");
				Record._view.time.val(new Date().Format("yyyy-MM-dd"));
			}else{
				Record._view.occupation.val(data.occupation);
				Record._view.wins.val(data.wins);
				Record._view.losses.val(data.losses);
				Record._view.gold.val(data.gold);
				Record._view.dust.val(data.dust);
				Record._view.time.val(new Date(data.time).Format("yyyy-MM-dd"));
			}
		},
		checkData : function( data ){
			if (data.occupation == -1 ){
				alertify.alert("职业未填写！");
				return false;
			}
			if( data.wins == -1 ){
				alertify.alert("胜场未填写！");
				return false;
			}
			if( data.losses == -1 ){
				alertify.alert("败场未填写！");
				return false;
			}
			if( data.gold == null || data.gold == "" ){
				alertify.alert("金币未填写！");
				return false;
			}
			if( data.dust== null || data.dust == "" ){
				alertify.alert("粉尘未填写！");
				return false;
			}
			if( data.time== null || data.time == "" ){
				alertify.alert("时间未填写！");
				return false;
			}
			//
			if( (data.losses < 3 && data.wins < 12) || (data.losses == 3 && data.wins == 12) ){
				alertify.alert("败场格式不正确！");
				return false;
			}
			if( isNaN(data.gold) ){
				alertify.alert("金币格式不正确！");
				return false;
			}
			if( isNaN(data.dust) ){
				alertify.alert("粉尘格式不正确！");
				return false;
			}
			return true;
		},
		checkList : function( ids ){
			if(ids.length == 0){
				alertify.alert("请选择一条数据");
				return false;
			}
			return true;
		}
}
//竞技场数据 添加页面
Record.add = {
		init : function(){
			console.log("add init");
			//
		  	var dateoption = {
		  			minView: "month", 
		  			language: "zh-CN",  
		  			format: "yyyy-mm-dd",
		  			autoclose: true,
		  	        todayBtn: true,
		  	};
		  	Record._view.time.datetimepicker( dateoption );
		  	Record._view.time.val(new Date().Format("yyyy-MM-dd"));	  		
		},
		saveBtnClick : function(){
			var data = {};
			data.occupation = Record._view.occupation.val();
			data.wins= Record._view.wins.val();
			data.losses = Record._view.losses.val();
			data.gold = Record._view.gold.val();
			data.dust = Record._view.dust.val();
			data.time = Date.parse( Record._view.time.val() );
			//console.log(data);
			//
			if(Record.checkRecordData( data )){
				Record.add.add( data );
			}
		},
		reloadBtnClick : function(){
			Record.setData(null);
		},
		add : function( data ){
			alertify.confirm( "确认添加此记录" , function( e ){
				if( e ){
					console.log(data);
					RecordCtrl.save( data , { callback: function( res ){		
						console.log(res);
						if( res.ok ){
							Record.setData(null);
							alertify.success("操作成功！");
						}else{
							alertify.error("操作失败！");
						}
					}, errorHandler: function( e, re ){
						console.log(e);
						console.log(re);
					}, timeout: 3000 } );
				}
			});	
		} 
},
//竞技场数据 编辑页面
Record.edit = {
		init : function(){
			console.log("edit init : " + Record._id);
		  	//
			var dateoption = {
		  			minView: "month", 
		  			language: "zh-CN",  
		  			format: "yyyy-mm-dd",
		  			autoclose: true,
		  	        todayBtn: true,
		  	};
		  	Record._view.time.datetimepicker( dateoption );
			Record.edit.loadData( Record._id );	
		},
		saveBtnClick : function(){
			var data = Record._data;
			data.occupation = Record._view.occupation.val();
			data.wins= Record._view.wins.val();
			data.losses = Record._view.losses.val();
			data.gold = Record._view.gold.val();
			data.dust = Record._view.dust.val();
			data.time = Date.parse( Record._view.time.val() );
			//console.log(data);
			//
			if(Record.checkData( data )){
				Record.edit.update( data );
			}
		},
		reloadBtnClick : function(){
			Record.setData(Record._data);
		},
		loadData : function( id ){	
			RecordCtrl.loadData( id , { callback: function( res ){					
				if( res.ok ){
					Record._data = res.data;
					Record.setData(res.data);
				}else{
					alertify.error("加载数据失败！");
				}
			}, errorHandler: function( e, re ){
				console.log(e);
				console.log(re);
			}, timeout: 3000 } );
		},
		update : function( data ){
			alertify.confirm( "确认修改此记录" , function( e ){
				if( e ){
					RecordCtrl.save( data , { callback: function( res ){		
						if( res.ok ){
							Record._data = res.data;
							Record.setData(Record._data);
							alertify.success("操作成功！");
						}else{
							alertify.error("操作失败！");
						}
					}, errorHandler: function( e, re ){
						console.log(e);
						console.log(re);
					}, timeout: 3000 } );
				}
			});	
		} 
}
//竞技场数据 查看页面
Record.view = {
		init : function(){
			console.log("view init : " + Record._id);
			Record.view.loadData( Record._id );	
		},
		loadData : function( id ){	
			RecordCtrl.loadData( id , { callback: function( res ){				
				if( res.ok ){
					Record._data = res.data;
					Record.setData(res.data);
				}else{
					alertify.error("加载数据失败！");
				}
			}, errorHandler: function( e, re ){
				console.log(e);
				console.log(re);
			}, timeout: 3000 } );
		},
		editBtnClick : function(){
			Record.goHref("edit" , Record._id );
		},
	
}
//竞技场数据 列表页面
Record.list = {
		init : function(){
			console.log("list init : " );
			//Record.view.loadData( Record._id );
			Record.list.initTable();
		},
		initTable : function( ){
			console.log("table init : " );
			 //先销毁表格  
	        $('#table').bootstrapTable('destroy');  
	        $('#table').bootstrapTable({
	        	classes : "table table-bordered table-hover table-striped fixed-table-container",//加载的样式
	            url: 'record/loadList',         //请求后台的URL（*）
	            method: 'get',                      //请求方式（*）
	            toolbar: '#toolbar',                //工具按钮用哪个容器     
	            striped: true, //是否显示行间隔色
	            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	            pagination: true, //是否显示分页（*）
	            sortable: true, //是否启用排序
	            sortOrder: "desc", //排序方式	            
	            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
	            pageNumber:1, //初始化加载第一页，默认第一页
	            pageSize: 10, //每页的记录行数（*）
	            pageList: [10, 25, 50], //可供选择的每页的行数（*）
	            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	            strictSearch: true,//设置为 true启用 全匹配搜索，否则为模糊搜索
	            showColumns: true, //是否显示所有的列
	            showRefresh: true, //是否显示刷新按钮
	            minimumCountColumns: 2, //最少允许的列数
	            clickToSelect: true, //是否启用点击选中行
	            singleSelect: true,//复选框只能选择一条记录
	            //height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	            uniqueId: "id", //每一行的唯一标识，一般为主键列
	            showToggle:false, //是否显示详细视图和列表视图的切换按钮
	            cardView: false, //是否显示详细视图
	            detailView: false, //是否显示父子表
	            queryParams: function queryParams (params) {
	                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	                        limit: params.limit,   //页面大小
	                        offset: params.offset,  //页码
	                        order: params.order, //排位命令（desc，asc）
	                        sort: params.sort, //排序列名
	                        occupation : $("#edit-occupation").val(),
	            			wins : $("#edit-wins").val(),
//	                        sdate: $("#stratTime").val(),
//	                        edate: $("#endTime").val(),
//	                        sellerid: $("#sellerid").val(),
//	                        orderid: $("#orderid").val(),
//	                        CardNumber: $("#CardNumber").val(),
//	                        maxrows: params.limit,
//	                        pageindex:params.pageNumber,
//	                        portid: $("#portid").val(),
//	                        CardNumber: $("#CardNumber").val(),
//	                        tradetype:$('input:radio[name="tradetype"]:checked').val(),
//	                        success:$('input:radio[name="success"]:checked').val(),
	                    };
	                    return temp;
	                },//传递参数（*）
	            columns: [{
	            	checkbox: true
	            	},{
	                field: 'id',
	                title: '序号',  
	            }, {
	                field: 'occupation',
	                title: '职业',
	                formatter:function(value,row,index){                    
	                       return getOccupationName( row.occupation );  
	                   } 
	            }, {
	                field: 'wins',
	                title: '战绩',
	                //valign:"middle",
	                //align:"center",
	                formatter:function(value,row,index){                    
	                    return row.wins + "-" + row.losses;
	                } 
	            }, {
	                field: 'time',
	                title: '时间',
	                sortable:true,
	                formatter:function(value,row,index){                    
	                    return new Date(row.time).Format("yyyy-MM-dd");  
	                } 
	            },],
	  //      	onLoadSuccess:function(data){
	            //通过对data判断
//	                var rows = data["rows"];
//	                $.each( rows ,function( index , row ){
//	                	//row["occupation"] = getOccupationName( row["occupation"] );
//	                	row["wins"] = row["wins"] + "-" + row["losses"];
//	                	row["time"] = new Date(row["time"]).Format("yyyy-MM-dd");
//	                })
//	                $("#table").bootstrapTable("load",data);
//	        	},
	        });
		},
		addBtnClick : function(){
			Record.goHref("add" , null);
		},
		viewBtnClick : function(){
			var ids = $.map($('#table').bootstrapTable('getSelections'), function (row){
				return row.id
			});
			if(Record.checkList(ids)) Record.goHref("view" , ids );
		},
		editBtnClick : function(){
			var ids = $.map($('#table').bootstrapTable('getSelections'), function (row){
				return row.id
			});
			if(Record.checkList(ids)) Record.goHref("edit" , ids );
		},
		delBtnClick : function(){
			var ids = $.map($('#table').bootstrapTable('getSelections'), function (row){
				return row.id
			});
			if(Record.checkList(ids)) Record.list.del(ids);
		},
		searchBtnClick : function(){
				Record.list.initTable();
		},
		del : function( id ){
			console.log(id);		 
			alertify.confirm( "确认删除此记录" , function( e ){
				if( e ){
					RecordCtrl.del( id , { callback: function( res ){		
						if( res.ok ){
							//removeByUniqueId
							$('tr[data-uniqueid='+id+']').remove();
							alertify.success("操作成功！");
						}else{
							alertify.error("操作失败！");
						}
					}, errorHandler: function( e, re ){
						console.log(e);
						console.log(re);
					}, timeout: 3000 } );
				}
			});	
		}	
}
//Alertify JS
function alertJS () {
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




