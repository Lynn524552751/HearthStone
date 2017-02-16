$(document).ready(function(){
	initPage();
});

function initPage(){
	console.log("live init");
	$(".heading").text("直播");
	getLiveHosts();
}

function getLiveHosts(  ){
		LiveCtrl.getLiveHosts( { callback: function( res ){		
			console.log(res);
			if( res.ok ){
				setLiveHostTpl(res.data);
			}else{
				alertify.error("加载失败！");
			}
		}, errorHandler: function( e, re ){
			console.log(e);
			console.log(re);
		}, timeout: 100000 } );
	}

function setLiveHostTpl(data){
	//$("li.list-group-item").css("padding","10px");
	//$("li.list-group-item").css("border","0px");
	var html = "";
	var item = "";
	$.each( data , function( i,  val ){
		item += '<div class="col-sm-3 col-md-3">'
			+'<a href="'+val.url+'" target="_blank" class="thumbnail " id="host-'+i+'">'
			+'<img data-src="" alt="500x200" src="'+val.img+'" style="width: 500px; height: 200px;">'
			+'<div class="caption">'			
			+'<p>'+val.title+'</p>'
			+'<li class="list-group-item" style="padding:0px;border:0px">'
			+val.name+'<span class="tools badge">'+val.number+'</span>'
			+'</li>'
			+'</div>'
			+'</a>'
			+'</div>';
		if((i+1)%4 == 0){
			html += '<div class="row">'
				+item
				+"</div>";
			item = "";
		}
	});

		
	$("#liveList").html(html);
}
