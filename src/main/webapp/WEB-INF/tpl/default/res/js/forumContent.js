var um = UM.getEditor('myEditor');

var close = false;

var getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); 
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return unescape(r[2]); return null; 
}

var sendHandler = function(){
	if(close){
		ForumBase.forumClosed();
		return;
	}
	var content = um.getContent();
	if($.isEmptyObject(content)){
		alert("请输入内容");
		return;
	}
	var parentId = getUrlParam("forum");
	var groupId = getUrlParam("group");
	var forum = {};
	forum.title = "";
	forum.content = content;
	forum.parentId = parentId;
	forum.groupId = groupId;
	serviceForum.addComment(forum,{callback:function(res){
		if(res.code == 200){
			var $main = $("#main");
			var count = parseInt($main.attr("count"));
			count += 1;
			var maxResult = parseInt($main.attr("maxResult"));
			var maxPage = Math.ceil(count / maxResult);
			window.location.href = "forumContent?forum="+parentId+"&group="+groupId+"&pidx="+maxPage;
		}else{
			ForumBase.callback(res);
		}
	},exceptionHandler : function(){
		alert("系统异常，请刷新页面重试！")
	},timeout : 3000});
}

var getReply = function(userName,date,content,fid){
	return '<div class="comment-edited">'+
		'<span>'+
			'回复'+
			'<a class="user-tooltip" href="javascript:void(0)">'+
			'	<b> '+userName+' </b>'+
			'</a>'+
			date + ' ： '+
			content+
		'</span>'+
		'<a fid="'+fid+'" href="javascript:void(0)" class="defbutton admin-color delbutton"><i class="fa fa-trash-o"></i></a>'
	'</div>';
}

var getDate = function(date){
	return "今天 " + 
	date.getHours() + ":" +
	date.getMinutes();
	
}

var loginUser = null;

var getLoginUser = function(callback){
	if(loginUser){
		if(callback)callback(loginUser);
		return;
	}
	serviceForum.getLoginUser({callback:function(res){
		if(res.code == 200){
			loginUser = res.res;
			if(callback)callback(loginUser);
		}else{
			ForumBase.callback(res);
		}
	}});
}

var delForumHandler = function(){
	if(close){
		ForumBase.forumClosed();
		return;
	}
	var $this = $(this);
	var fid = $this.attr("fid");
	var confirm = window.confirm("确认删除？");
	if(!confirm)return;
	serviceForum.delForum(fid,{callback:function(res){
		if(res.code == 200){
			var $parent = $this.parent();
			if($parent[0].className != "comment-edited"){
				$parent = $this.parents(".forum-post")
			}
			$parent.remove();
		}else{
			ForumBase.callback(res);
		}
	}});
}

var replyHandler = function(){
	if(close){
		ForumBase.forumClosed();
		return;
	}
	var $this = $(this);
	var $forumPost = $this.parents(".forum-post");
	var $textDiv = $forumPost.find(".post-text-block");
	var fid = $forumPost.attr("fid");
	var groupId = getUrlParam("group");
	
	var $replyDiv = $textDiv.find(".reply_div");
	
	if($replyDiv.length == 0){
		var replyDiv = document.createElement("DIV");
		replyDiv.className = "reply_div";
		var input = document.createElement("INPUT");
		input.className = "reply_input";
		var btn = document.createElement("A");
		btn.className = "reply_btn";
		btn.className = "newdefbutton";
		btn.innerHTML = "回复";
		
		replyDiv.appendChild(input);
		replyDiv.appendChild(btn);
		$textDiv.append(replyDiv);
	}
	
	$(btn).click(function(){
		var content = input.value;
		if($.isEmptyObject(content)){
			alert("请输入内容");
			return;
		}
		var forum = {};
		forum.title = "";
		forum.content = content;
		forum.parentId = fid;
		forum.type = 2;
		forum.groupId = groupId;
		replyDiv.parentNode.removeChild(replyDiv);
		serviceForum.addComment(forum,{callback:function(res){
			if(res.code == 200){
				var date = getDate(new Date());
				getLoginUser(function(user){
					var $replyDiv = $(getReply(user.nick,date,content,res.res));
					$textDiv.append($replyDiv);
					$replyDiv.find(".delbutton").click(delForumHandler);
				});
			}else{
				ForumBase.callback(res);
			}
		},exceptionHandler : function(){
			alert("系统异常，请刷新页面重试！")
		},timeout : 3000});
	});
}

var goodHandler = function(){
	if(close){
		ForumBase.forumClosed();
		return;
	}
	var $this = $(this);
	var $forumPost = $this.parents(".forum-post");
	var $textDiv = $forumPost.find(".post-text-block");
	var $rate = $this.parent().parent();
	var $theRate = $rate.find(".the-rate");
	var fid = $forumPost.attr("fid");
	var groupId = getUrlParam("group");
	
	console.log($rate)
	serviceForum.addGood(fid,{callback:function(res){
		if(res.code == 200){
			var oldRate = parseInt($theRate.html());
			oldRate += 1;
			$theRate.html(oldRate);
		}else{
			ForumBase.callback(res);
		}
	}});
}

var badHandler = function(){
	if(close){
		ForumBase.forumClosed();
		return;
	}
	var $this = $(this);
	var $forumPost = $this.parents(".forum-post");
	var $textDiv = $forumPost.find(".post-text-block");
	var $rate = $this.parent().parent();
	var $theRate = $rate.find(".the-rate");
	var fid = $forumPost.attr("fid");
	var groupId = getUrlParam("group");
	
	serviceForum.addBad(fid,{callback:function(res){
		if(res.code == 200){
			var oldRate = parseInt($theRate.html());
			oldRate -= 1;
			$theRate.html(oldRate);
		}else{
			ForumBase.callback(res);
		}
	}});
}

var initEvent = function(){
	$("input[name=send]").click(sendHandler);
	$(".replybtn").click(replyHandler);
	$(".fa-thumbs-up.rating-good").click(goodHandler);
	$(".fa-thumbs-down.rating-bad").click(badHandler);
	$(".delbutton").click(delForumHandler);
}

var addView = function(){
	var $main = $("#post-0");
	var fid = $main.attr("fid");
	serviceForum.addView(fid);
}

var init = function(){
	initEvent();
	addView();
	if($(".fa-unlock-alt").length > 0)close = true;
}
$(document).ready(init);
