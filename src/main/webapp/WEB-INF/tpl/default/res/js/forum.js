var Forum = {};

Forum.groups = null;
Forum.editGroupId = null;
Forum.forums = null;
Forum.groupType = null;

Forum.callback = function(res){
	if(res.code == 200){
		window.wxc.xcConfirm("操作成功", window.wxc.xcConfirm.typeEnum.success);
	}else if(res.code == 10001){
		window.open("/login");
	}else if(res.code == 10002){
		window.wxc.xcConfirm("无权操作", window.wxc.xcConfirm.typeEnum.error);
	}else if(res.code == 20001){
		window.wxc.xcConfirm("不能重复操作", window.wxc.xcConfirm.typeEnum.error);
	}else if(res.code == 20002){
		window.wxc.xcConfirm("有依赖数据，不能删除", window.wxc.xcConfirm.typeEnum.error);
	}else{
		window.wxc.xcConfirm("未知错误", window.wxc.xcConfirm.typeEnum.error);
	}
}

Forum.getForumGroupTmpl = function(forumGroup){
	return '<div class="message hand"'+
		'onclick="javascript:Forum.editForumGroup('+forumGroup.id+');">'+
		'<div class="img-container">'+
		'	<i class="forum-icon ">'+
		'		<i class="fa '+forumGroup.icon+'"></i>'+
		'	</i>'+
		'</div>'+
		'<article>'+
		'	<h4 class="no-margin">'+forumGroup.name+'</h4>'+
		'	<p class="no-margin">'+forumGroup.desc+'</p>'+
		'</article>'+
		'<a href="javascript:Forum.delForumGroup('+forumGroup.id+')" style="right: 15px;position: absolute;top: 16px;">'+
		'<i class="fa fa-trash-o fa-2x"></i>'+
		'</a>'+
	'</div>';
}

Forum.updateForum = function(){
	var $this = $(this);
	var fid = $this.attr("fid");
	var value = $this.val();
	var forum = {};
	forum.id = fid;
	if(this.className == "forum-type"){
		forum.type = value;
	}else{
		forum.canReply = value == 1 ? true : false;
	}
	serviceForum.updateForum(forum,{callback:function(res){
		if(res.code != 200)Forum.callback(res);
	}});
}

Forum.delForumGroup = function(groupId){
	var txt = "确认删除该分组吗？";
	var option = {
		title: "提醒",
		btn: parseInt("0011",2),
		onOk: function(){
			serviceForum.delForumGroup(groupId,{callback:function(res){
				if(res.code == 200){
					Forum.setForumGroup(Forum.groupType);
				}
				Forum.callback(res);
			}});
		}
	}
	window.wxc.xcConfirm(txt, "custom", option);
}

Forum.delForum = function(forumId,ele){
	var txt = "确认删除该帖子吗？";
	var $this = $(ele);
	var option = {
		title: "提醒",
		btn: parseInt("0011",2),
		onOk: function(){
			var forum = {};
			forum.id = forumId;
			serviceForum.delForum(forum,{callback:function(res){
				if(res.code == 200){
					$this.parents(".message").remove();
				}
				Forum.callback(res);
			}});
		}
	}
	window.wxc.xcConfirm(txt, "custom", option);
}

Forum.getForumTmpl = function(forum){
	var check1 = "";
	var check2 = "";
	var check3 = "";
	var check4 = "";
	var check5 = "";
	var check6 = "";
	var check7 = "";
	var checked = "checked = \"checked\"";
	if(forum.type == 0)check1 = checked;
	else if(forum.type == 9)check2 = checked;
	else if(forum.type == 5)check3 = checked;
	else if(forum.type == 2)check4 = checked;
	else if(forum.type == -1)check5 = checked;
	
	if(forum.canReply)check6 = checked;
	else check7 = checked;
	var html =  '<div class="message hand" style="min-height:40px">'+
		'	<a class="middle" style="font-size: 20px" href="../forumContent?forum='+forum.id+'&group='+Forum.editGroupId+'" target="_blank"> '+
		'	<i class="fa fa-comments-o"></i>'+
		'	<span> '+
		'		'+forum.title+''+
		'	</span>'+
		'</a>'+
		'<div style="width:30px;margin: 10px" class="row pull-right col-sm-1">'+
		'<a class="del-forum" href="javascript:void(0)" onclick="Forum.delForum('+forum.id+',this)">'+
		'<i class="fa fa-trash-o fa-2x"></i>'+
		'</a>'+
		'</div>'+
		'<div class="row pull-right col-sm-6">'+
		'	<label class="checkbox-inline"> <input type="radio"'+
		'		fid="'+forum.id+'" class="forum-type" name="forum-type-'+forum.id+'" '+check1+' value="0" /> 默认'+
		'	</label> <label class="checkbox-inline"> <input type="radio"'+
		'		fid="'+forum.id+'" class="forum-type" name="forum-type-'+forum.id+'" '+check2+' value="9" /> 置顶'+
		'	</label> <label class="checkbox-inline"> <input type="radio"'+
		'		fid="'+forum.id+'" class="forum-type" name="forum-type-'+forum.id+'" '+check3+' value="5" /> 加精'+
		'	</label> <label class="checkbox-inline"> <input type="radio"'+
		'		fid="'+forum.id+'" class="forum-type" name="forum-type-'+forum.id+'" '+check4+' value="2" /> 关闭'+
		'	</label> <label class="checkbox-inline"> <input type="radio"'+
		'		fid="'+forum.id+'" class="forum-type" name="forum-type-'+forum.id+'" '+check5+' value="-1" /> 隐藏'+
		'	</label>'+
		'</div>'+
		'<div class="row pull-right col-sm-6">'+
		'	<label class="checkbox-inline"> <input type="radio"'+
		'		fid="'+forum.id+'" name="reply-role-'+forum.id+'" value="1" '+check6+' /> 可以回帖'+
		'	</label> '+
		'	<label class="checkbox-inline"> <input type="radio"'+
		'		fid="'+forum.id+'" name="reply-role-'+forum.id+'" value="0" '+check7+' /> 不可回帖'+
		'	</label> '+
		'</div>'+
		'</div>'
		
	var $html = $(html);
	return html;
}

Forum.getGroup = function(groupId){
	for(var i = 0 ; i < Forum.groups.length; i += 1){
		var group = Forum.groups[i];
		if(group.id == groupId)return group;
	}
	return null;
}

Forum.getRadioValue = function(name){
	var types = $("input[name="+name+"]");
	for(var i = 0 ; i < types.length ; i += 1){
		var $type = $(types[i]);
		if($type.prop("checked") == true)return $type.val();
	}
}

Forum.setRadioValue = function(name,value){
	$("input[name="+name+"]").each(
		function(o){
			var $this = $(this);
		    if($this.val() == value){
		    	$this.prop("checked","checked")
		    }	
		}
	)
}

Forum.setIcon = function(icon){
	icon = "fa " + icon;
	$(".forum-icon-select").removeClass("checked").each(function(){
		var $icon = $(this);
		var inner = $icon.find("i")[0];
		if(inner.className == icon)$icon.addClass("checked");
	});
}

Forum.getIcon = function(){
	var $icons = $(".forum-icon-select");
	for(var i = 0 ; i < $icons.length ; i += 1){
		var $icon = $($icons[i]);
		if($icon.hasClass("checked")){
			var inner = $icon.find("i")[0];
			var className = inner.className;
			return className.split(" ")[1];
		}
	}
	return null;
}

Forum.setForumList = function(groupId,pageIdx){
	if(!pageIdx)pageIdx = 1;
	serviceForum.getForumList(groupId,pageIdx,{callback:function(res){
		if(res.code == 200){
			var $forumList = $(".forum-list").empty();
			var list = res.res;
			for(var i = 0 ; i < list.length ; i += 1){
				var forum = list[i];
				var forumTmpl = Forum.getForumTmpl(forum);
				$forumList.append(forumTmpl);
			}
			var $radios = $forumList.find("input");
			$radios.click(Forum.updateForum)
		}else{
			Forum.callback(res);
		}
	}});
}

Forum.editForumGroup = function(groupId){
	var group = Forum.getGroup(groupId);
	var $title = $("#ed-title");
	var $desc = $("#ed-desc");
	var canCreate = group.canCreate ? 1 : 0;
	$title.val(group.name);
	$desc.val(group.desc);
	Forum.setRadioValue("ed-type",group.type);
	Forum.setRadioValue("create-role-type",canCreate);
	Forum.setIcon(group.icon);
	Forum.setForumList(groupId);
	Forum.editGroupId = groupId;
	$("#updateInfo").html("编辑ID ： "+groupId);
	$("#ed-update").hide();
}

Forum.setForumGroup = function(type){
	Forum.groupType = type;
	serviceForum.getForumGroupList(type,{callback:function(res){
		if(res.code == 200){
			var $forumGroupDiv = $(".forum-group-list").empty();
			var list = res.res;
			Forum.groups = list;
			for(var i = 0 ; i < list.length ; i += 1){
				var temp = Forum.getForumGroupTmpl(list[i]);
				$forumGroupDiv.append(temp);
			}
		}else{
			Forum.callback(res);
		}
	}});
}

Forum.selectGroup = function(type,val){
	$(".nav-name").html(val);
	Forum.setForumGroup(type);
}

Forum.cleanGroupInfo = function(){
	var $title = $("#ed-title");
	var $desc = $("#ed-desc");
	$title.val("");
	$desc.val("");
	$(".forum-list").empty()
	Forum.editGroupId = null;
	$("#updateInfo").empty();
	$("#ed-update").hide();
}

Forum.saveGroup = function(){
	var $title = $("#ed-title");
	var $desc = $("#ed-desc");
	var title = $title.val();
	var desc = $desc.val();
	var type = Forum.getRadioValue("ed-type");
	var canCreate = Forum.getRadioValue("create-role-type") == "1" ? true : false;
	var icon = Forum.getIcon();
	var id = Forum.editGroupId;
	
	if(title == null || desc == null || type == null || icon == null || canCreate == null){
		window.wxc.xcConfirm("请输入完整信息", window.wxc.xcConfirm.typeEnum.warning);
		return;
	}
	$("#ed-update").hide();
	var group = {};
	group.name = title;
	group.desc = desc;
	group.type = type;
	group.icon = icon;
	group.id = id;
	group.canCreate = canCreate;
	var callback = function(res){
		Forum.setForumGroup(Forum.groupType);
		Forum.callback(res);
	}
		
	if(!id)serviceForum.saveForumGroup(group,{callback:callback});
	else serviceForum.updateForumGroup(group,{callback:callback});
}

Forum.reset = function(){
	window.location.reload();
}

Forum.gotoFirstPage = function(){
	if(Forum.editGroupId == null)return;
	var $page = $(".nav-page.tip");
	$page.html(1);
	Forum.setForumList(Forum.editGroupId,1);
}

Forum.gotoProvPage = function(){
	if(Forum.editGroupId == null)return;
	var $page = $(".nav-page.tip");
	var pidx = parseInt($page.html());
	pidx -= 1;
	if(pidx < 1)pidx = 1;
	$page.html(pidx);
	Forum.setForumList(Forum.editGroupId,pidx);
}

Forum.gotoNextPage = function(){
	if(Forum.editGroupId == null)return;
	var $page = $(".nav-page.tip");
	var pidx = parseInt($page.html());
	pidx += 1;
	$page.html(pidx);
	Forum.setForumList(Forum.editGroupId,pidx);
}

Forum.showSaveBtn = function(){
	$("#ed-update").show();
}

Forum.initEvent = function(){
	var $selectList = $(".forum-icon-select");
	$selectList.click(function(){
		$selectList.removeClass("checked");
		$(this).addClass("checked");
	});
	
	$("#ed-save").click(Forum.cleanGroupInfo);
	$("#ed-update").click(Forum.saveGroup);
	$("#ed-reset").click(Forum.reset);
	$(".nav-first").click(Forum.gotoFirstPage);
	$(".nav-prov").click(Forum.gotoProvPage);
	$(".nav-next").click(Forum.gotoNextPage);

	$("#ed-title").keydown(Forum.showSaveBtn);
	$("#ed-desc").keydown(Forum.showSaveBtn);
	$(".forum-icon-select").click(Forum.showSaveBtn);
	$("input[name=ed-type]").click(Forum.showSaveBtn);
	$("input[name=create-role-type]").click(Forum.showSaveBtn);
}

Forum.init = function(){
	Forum.initEvent();
	Forum.setForumGroup(null);
}

$(document).ready(Forum.init);