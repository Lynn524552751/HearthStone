var Article = {};

Article.pageIndex = 1;

Article.maxResult = 6;

Article.loadingTO = null;

Article.searchNavType = null;

Article.pagingMaxShow = 5;

Article.fileIndex = 0;

Article.uploadingImgCount = 0;

Article.remoteServer = "http://localhost/";

Article.uploadURL = Article.remoteServer + '/upload.html';

Article.cleanFiles = function(){
	var fileGroups = $(".img_box");
	fileGroups.each(function(idx,group){
		group.parentNode.removeChild(group);
	});
}

Article.getFiles = function(){
	var files = new Array();
	var fileGroups = $(".img_box");
	fileGroups.each(function(idx,group){
		var $group = $(group);
		var fileId = $group.attr("file_id");
		if(!fileId)return;
		var fileIndex = Article.getFileIndex($group);
		var file = {};
		file.id = fileId;
		file.FIndex = fileIndex;
		files.push(file);
	});
	return files;
}

Article.initCreateEvent = function(){
	var $createBtn = $("#createBtn");
	$createBtn.click(function(){
		location.reload();
		return;
		var createBtn = document.getElementById("createBtn");
		var editBtn = document.getElementById("editBtn");
		createBtn.className = "btn btn-primary";
		editBtn.className = "btn btn-default";
		createBtn.style.color = "";
		editBtn.style.color = "#aaa";
		
		$("#article_id").val("");
		var $radios = $('input:radio[name=article_type]');
	    if($radios.is(':checked') === false) {
	        $radios.filter('[value=0]').prop('checked', true);
	    }
	    
	    var $navType = $("#navType").html("请选择栏目...").removeAttr("nid");
	    $navType.parent().removeAttr("disabled");
	    $("input[name=article_type]").removeAttr("disabled");
	    
	    $("#author").val("");
	    
	    $("#desc").val("");
	    
	    Article.cleanFiles();
	    
	    $("#title").val("");
	    editor.setValue("");
	});
}

Article.initSubmitEvent = function(){
	$("#submit-btn").click(function(){
		var articleId = $("#article_id").val();
		var type = 0;
		var $radios = $('input:radio[name=article_type]');
		$radios.each(function(){
			if(this.checked == true)type = this.value;
		});
		var files = Article.getFiles();
		var title = $("#title").val();
		var context = editor.getValue();
		var navId = $("#navType").attr("nid");
		var author = $("#author").val();
		var desc = $("#desc").val();
		
		if( wup.getUploadState() ){
			reset();
	        alertify.confirm("你当前有文件待上传，是否立即上传后再提交？", function( e ){
	        	if( e ){
	        		wup.uploader.upload();
	        		return;
	        	}
	        } );
	        return;
		}
		
		if(!navId || !title || !type || !author || !desc ){
			reset();
	        alertify.alert("请输入完整信息");
			return;
		}
		
		var article = {};
		article.AAuthorName = author;
		article.AContext = context;
		article.ADesc = desc;
		article.AEditTime = new Date();
		article.AIndexType = type;
		article.AStatus = 0;
		
		article.ATitle = title;
		article.comArticleFiles = wup.getUploadArticleFiles();
		article.id;
		article.comNav;
		
		var nav = {};
		nav.NId = navId;
		article.comNav = nav;
		
		Article.loading();
		var fnName = "saveNews";
		if($.isNumeric(articleId)){
			article.id = articleId;
			fnName = "updateNews";
			console.log( article.comNav.NId );
		}else{
			article.ATime = article.AEditTime;
		}
		serviceNews[fnName](article,{callback:function(res){
			Article.loaded();
			if ( res ){
				reset();
				alertify.alert( "保存成功!" ,  function( e ){
					if( e ){
						 location.reload();
					}
				} );
			}
		},errorHandler: function( e, re ){
			Article.loaded();
			reset();
			alertify.error("保存失败," + re.message );
		}, timeout: 3000 
		});
	});
	
}

Article.initSearchEvent = function(){
	var $searchNavType = $("#searchNavType");
	$searchNavType.change(function(){
		var nid = $searchNavType.attr("nid");
		Article.searchNavType = nid;
		Article.pageIndex = 1;
		Article.setDataList(1);
	});
}

Article.initEvent = function(){
	Article.initCreateEvent();
	Article.initSubmitEvent();
	Article.initSearchEvent();
}

Article.getFileIndex = function(fileEle){
	var $groups = $(".img_box");
	for(var idx = 0 ; idx < $groups.length ; idx += 1){
		var group = $groups[idx];
		if($(fileEle).attr("id") == $(group).attr("id"))return idx;
	}
	return -1;
}

Article.uploadImage = function(fileEle){
	
	return;
	var file = fileEle.files[0];
	var reader = new FileReader();
	reader.readAsDataURL(file);
	
	reader.onloadend = function(){
		var $fileGroup = $(fileEle).parents(".file-upload-group");
		var $fileName = $fileGroup.find(".file-name");
		
		var base64 = reader.result;
		var dotIdx = base64.indexOf(",");
		//base64 = base64.substring(dotIdx + 1).replace(/\+/g,"%1").replace(/\//g,"%2").replace(/\=/g,"%3");
		//console.log(base64)
		
		var len = base64.length;
		base64 = "1";
		for(var i = 0 ; i < len; i += 1){
			base64 += "1";
		}
		
		var name = fileEle.value;
		var idx = name.indexOf("/");
		if(idx == -1)idx = name.indexOf("\\");
		if(idx != -1){
			name = name.substring(idx + 1);
		}
		
		var imgIndex = Article.getFileIndex(fileEle);
		
		Article.uploadingImgCount += 1;
//		serviceUpload.saveImage(imgIndex,name,base64,{callback:function(json){
//			Article.uploadingImgCount -= 1; 
//			var file = JSON.parse(json);
//			if(!file){
//				alert("图片 '"+name+"' 上传失败");
//				return;
//			}
//			var fullName = file.FPath + file.FName + file.FType;
//			$fileName.html(fullName).attr("file_id",file.id);
//		},exceptionHandler : function(e){
//			Article.uploadingImgCount -= 1; 
//			alert("图片 '"+name+"' 上传失败。error");
//		}})
		
		window.jsonpcallback = function(file){
			if(!file){
				alert("图片 '"+name+"' 上传失败");
				return;
			}
			var fullName = file.FPath + file.FName + file.FType;
			$fileName.html(fullName).attr("file_id",file.id);
		}
		var url = "http://192.168.1.129:8080/9combo/upload.html?base64="+base64;
		$.ajax({
			'url':url,
			'dataType':'jsonp',
			'jsonp':'jsonpcallback',
			'timeout' : 10000,
			'error' : function(e){
				if(e.status == "200" && e.statusText == "success")return;
				Article.uploadingImgCount -= 1; 
				alert("图片 '"+name+"' 上传失败。error");
			},
		});
		
		
		fileEle = $fileGroup.find("input[type=file]");
		fileEle.change(function(){
			Article.uploadImage(this);
		});
	}
	
}

Article.insertImage = function(e){
	var $this = $(this);
	var $fileName = $this.parents(".file-upload-group").find(".file-name");
	var fileId = $fileName.attr("file_id");
	var filePath = $fileName.html();
	if(!fileId){
		alert("无图片");
		return;
	}
	var imgUrl = Article.comboSite + filePath;
	wysihtml5.commands.insertImage.exec(editor.composer, "insertImage", imgUrl);
}

Article.addFiles = function(files){
	for(var i = 0 ; i < files.length ; i += 1){
		var file = files[i];
		var imgEle = $("#"+file.id);
		if(imgEle.length == 0){
			imgEle = document.createElement("DIV");
			imgEle.id = file.id;
			imgEle.className = "img_box";
			
			var img = document.createElement("IMG");
			img.src = Article.remoteServer + file.FPath + file.FName + file.FType;
			imgEle.appendChild(img);
			
			var removeDiv = document.createElement("div");
			removeDiv.innerHTML = "删除";
			imgEle.appendChild(removeDiv);
			
			$(removeDiv).click(function(){
				$(this).parents(".img_box").remove();
			});
			
			var titleGroup = $("#author-form-group")[0];
			var form = $("form")[0];
			form.insertBefore(imgEle,titleGroup);
			
			imgEle = $(imgEle);
		}
		imgEle.attr("file_id",file.id)
	}
}

Article.setFiles = function(files){
	Article.cleanFiles();
	Article.addFiles(files)
}

Article.getNavMenuTmpl = function(nav){
	return $('<li><a href="javascript:void(0)" nid="'+nav.NId+'">'+nav.NName+'</a></li>');
}

Article.initForms = function(){
	var $searchNavUL = $("#searchNavUL");
	var allData = {};
	allData.NId = "";
	allData.NName = "全部";
	var $allTmpl = Article.getNavMenuTmpl(allData);
	$searchNavUL.append($allTmpl);
	$allTmpl.click(function(){
		var $this = $(this);
		var $a = $this.find("a");
		var $dropdownName = $a.parents(".dropdown").find(".dropdown-name");
		$dropdownName.attr("nid",$a.attr("nid")).html($a.html());
		if($dropdownName[0].id == "searchNavType")$dropdownName.trigger("change");
	});
	
	serviceNews.getNormalNav({callback:function(res){
		if(res.code == 0){
			serviceNews.getSpecialNav({callback:function(res){
				if(res.code == 0){
					var otherId = "";
					var data = res.resultMap.data;
					$.each(data,function(idx,nav){
						otherId += nav.NId + ",";
					});
					if(otherId != ""){
						otherId = otherId.substring(0,otherId.length - 1);
						var otherData = {};
						otherData.NId = otherId;
						otherData.NName = "其他";
						var $otherTmpl = Article.getNavMenuTmpl(otherData);
						$searchNavUL.append($otherTmpl);
						$otherTmpl.click(function(){
							var $this = $(this);
							var $a = $this.find("a");
							var $dropdownName = $a.parents(".dropdown").find(".dropdown-name");
							$dropdownName.attr("nid",$a.attr("nid")).html($a.html());
							if($dropdownName[0].id == "searchNavType")$dropdownName.trigger("change");
						});
					}
				}else{
					throw new Error("load normal nav error");
				}
			},execptionHandler : function(e){
				console.log(e)
			}});
			
			
			var data = res.resultMap.data;
			var $ul = $(".nav_menu");
			var $navType = $("#navType");
			$.each(data,function(idx,nav){
				$ul.each(function(idx2,ul){
					var $tmpl = Article.getNavMenuTmpl(nav);
					$(ul).append($tmpl);
					$tmpl.click(function(){
						var $this = $(this);
						var $a = $this.find("a");
						var $dropdownName = $a.parents(".dropdown").find(".dropdown-name");
						$dropdownName.attr("nid",$a.attr("nid")).html($a.html());
						if($dropdownName[0].id == "searchNavType")$dropdownName.trigger("change");
						else{
							var type = 5;
							if(nav.NType == 1)type = 7;
							var $radios = $('input:radio[name=article_type]');
							$radios.filter('[value='+type+']').prop('checked', true);
						}
					});
				});
			});
		}else{
			throw new Error("load normal nav error");
		}
	},execptionHandler : function(e){
		console.log(e)
	}});
}

Article.getInsertVideoEle = function(){
	return '<li data-toggle="modal" data-target="#insertVideoWin">'+
			'<a class="btn btn- btn-sm btn-default" tabindex="-1" href="javascript:;" unselectable="on">'+
			'<i class="glyphicon glyphicon-facetime-video"></i>'+
			'</a>'+
		'</li>';
}

Article.afterWYSILoad = function(e){
	var $insertVideoEle = $(Article.getInsertVideoEle());
	var $wysiToobar = $(".wysihtml5-toolbar");
	$wysiToobar.append($insertVideoEle);
	
	$("#insertVideoBtn").click(function(){
		var videoHTML = $("#videoHTML").val();
		if(!videoHTML || videoHTML == "")return;
		var node = document.createElement("span");
		node.innerHTML = videoHTML;
		editor.focus();
		editor.composer.selection.insertNode(node);
	});
	
}
/*
Article.initUploadBtn = function(){
	$("#remote_upload").uploadify({  
        'swf'       : '/' + appName + '/admini/res/js/uploadify/uploadify.swf',  
        'uploader'         : Article.uploadURL,  
        'height'		 : 30,
        'width'			 : 120,
        'fileDesc' : '选择图片',  
        'fileExt' : '*.jpg;*.png;*.gif',
        'onDialogOpen' : function() {//当选择文件对话框打开时触发  
        	console.log( 'Open!');  
        },  
        'onSelect' : function(file) {//当每个文件添加至队列后触发  
        	console.log( 'id: ' + file.id  
                    + ' - 索引: ' + file.index  
                    + ' - 文件名: ' + file.name  
                    + ' - 文件大小: ' + file.size  
                    + ' - 类型: ' + file.type  
                    + ' - 创建日期: ' + file.creationdate  
                    + ' - 修改日期: ' + file.modificationdate  
                    + ' - 文件状态: ' + file.filestatus);  
        },  
        'onSelectError' : function(file,errorCode,errorMsg) {//当文件选定发生错误时触发  
        	console.log( 'id: ' + file.id  
                + ' - 索引: ' + file.index  
                + ' - 文件名: ' + file.name  
                + ' - 文件大小: ' + file.size  
                + ' - 类型: ' + file.type  
                + ' - 创建日期: ' + file.creationdate  
                + ' - 修改日期: ' + file.modificationdate  
                + ' - 文件状态: ' + file.filestatus  
                + ' - 错误代码: ' + errorCode  
                + ' - 错误信息: ' + errorMsg);  
        },  
        'onDialogClose' : function(swfuploadifyQueue) {//当文件选择对话框关闭时触发  
            if( swfuploadifyQueue.filesErrored > 0 ){  
            	console.log( '添加至队列时有'  
                    +swfuploadifyQueue.filesErrored  
                    +'个文件发生错误n'  
                    +'错误信息:'  
                    +swfuploadifyQueue.errorMsg  
                    +'n选定的文件数:'  
                    +swfuploadifyQueue.filesSelected  
                    +'n成功添加至队列的文件数:'  
                    +swfuploadifyQueue.filesQueued  
                    +'n队列中的总文件数量:'  
                    +swfuploadifyQueue.queueLength);  
            }  
        },  
        'onQueueComplete' : function(stats) {//当队列中的所有文件全部完成上传时触发  
        	console.log( '成功上传的文件数: ' + stats.successful_uploads  
                + ' - 上传出错的文件数: ' + stats.upload_errors  
                + ' - 取消上传的文件数: ' + stats.upload_cancelled  
                + ' - 出错的文件数' + stats.queue_errors);  
        },  
        'onUploadComplete' : function(file,swfuploadifyQueue) {//队列中的每个文件上传完成时触发一次  
        	console.log( 'id: ' + file.id  
                + ' - 索引: ' + file.index  
                + ' - 文件名: ' + file.name  
                + ' - 文件大小: ' + file.size  
                + ' - 类型: ' + file.type  
                + ' - 创建日期: ' + file.creationdate  
                + ' - 修改日期: ' + file.modificationdate  
                + ' - 文件状态: ' + file.filestatus);  
        },  
        'onUploadError' : function(file,errorCode,errorMsg,errorString,swfuploadifyQueue) {//上传文件出错是触发（每个出错文件触发一次）  
        	console.log( 'id: ' + file.id  
                + ' - 索引: ' + file.index  
                + ' - 文件名: ' + file.name  
                + ' - 文件大小: ' + file.size  
                + ' - 类型: ' + file.type  
                + ' - 创建日期: ' + file.creationdate  
                + ' - 修改日期: ' + file.modificationdate  
                + ' - 文件状态: ' + file.filestatus  
                + ' - 错误代码: ' + errorCode  
                + ' - 错误描述: ' + errorMsg  
                + ' - 简要错误描述: ' + errorString);  
        },  
        'onUploadProgress' : function(file,fileBytesLoaded,fileTotalBytes,queueBytesLoaded,swfuploadifyQueueUploadSize) {//上传进度发生变更时触发  
            console.log( 'id: ' + file.id  
                + ' - 索引: ' + file.index  
                + ' - 文件名: ' + file.name  
                + ' - 文件大小: ' + file.size  
                + ' - 类型: ' + file.type  
                + ' - 创建日期: ' + file.creationdate  
                + ' - 修改日期: ' + file.modificationdate  
                + ' - 文件状态: ' + file.filestatus  
                + ' - 当前文件已上传: ' + fileBytesLoaded  
                + ' - 当前文件大小: ' + fileTotalBytes  
                + ' - 队列已上传: ' + queueBytesLoaded  
                + ' - 队列大小: ' + swfuploadifyQueueUploadSize);
            //Article.addFiles(file,fileBytesLoaded,fileTotalBytes,queueBytesLoaded,swfuploadifyQueueUploadSize);
        },  
        'onUploadStart': function(file) {//上传开始时触发（每个文件触发一次）  
        	console.log( 'id: ' + file.id  
                + ' - 索引: ' + file.index  
                + ' - 文件名: ' + file.name  
                + ' - 文件大小: ' + file.size  
                + ' - 类型: ' + file.type  
                + ' - 创建日期: ' + file.creationdate  
                + ' - 修改日期: ' + file.modificationdate  
                + ' - 文件状态: ' + file.filestatus );  
        },  
        'onUploadSuccess' : function(file,data,response) {//上传完成时触发（每个文件触发一次）  
        	console.log( 'id: ' + file.id  
                + ' - 索引: ' + file.index  
                + ' - 文件名: ' + file.name  
                + ' - 文件大小: ' + file.size  
                + ' - 类型: ' + file.type  
                + ' - 创建日期: ' + file.creationdate  
                + ' - 修改日期: ' + file.modificationdate  
                + ' - 文件状态: ' + file.filestatus  
                + ' - 服务器端消息: ' + data  
                + ' - 是否上传成功: ' + response);
        	data = data.replace(/&quot;/g,"'");
        	eval("var files = " + data);
        	console.log(files)
        	Article.addFiles(files);
        }  
	});
}
*/
Article.init = function(){
	$('.textarea').wysihtml5({
		"html" : true,
		events : {
			load : Article.afterWYSILoad
		}
	});
	Article.setDataList(Article.pageIndex);
	Article.initEvent();
	Article.initForms();
	
	$($(".file-upload-group")[0]).remove();
	
	//Article.initUploadBtn();
}

Article.loading = function(){
	Article.loadingTO = setTimeout(function(){
		var $loading = $("#loading_mask");
		if($loading.length > 0)return;
		var loading = document.createElement("DIV");
		loading.id = "loading_mask";
		loading.className = "loading-mask";
		var loadingImg = document.createElement("IMG");
		loadingImg.src = "/" + appName + "/admini/res/img/loading-blue.gif";
		loadingImg.className = "loading-img";
		loading.appendChild(loadingImg);
		document.body.appendChild(loading);
	},300);
}

Article.loaded = function(){
	var $loading = $("#loading_mask");
	if(Article.loadingTO)clearTimeout(Article.loadingTO);
	if($loading.length == 0)return;
	$loading.remove();
}

Article.reloadList = function(){
	Article.setDataList(Article.pageIndex);
}

Article.setDataList = function(pageIdx){
	var query = {};
	query.firstResult = (pageIdx-1) * Article.maxResult;
	query.maxResult = Article.maxResult;
	query.orderColumn = "";
	query.order = "";
	if(Article.searchNavType)query.arg0 = Article.searchNavType;
	Article.loading();
	serviceNews.getNewsList(query,{callback:function(res){
		Article.loaded();
		if(res.status == 0){
			Article.setList(res);
			Article.setPaging(res,pageIdx);
			Article.setDataEvent();
			Article.setPagingEvent();
		}else{
			alert(res.message)
		}
	},exceptionHandler : function(e){
		Article.loaded();
		console.log(e);
	}});
}

Article.getTmpl = function(art,active){
	var activeClass = "";
	if(active)activeClass = "active";
	return '<a href="javascript:void(0);" class="list-group-item '+activeClass+'" art="'+art.id+'" artType="'+art.comNav.NType+'">'+
		      '<h4 class="list-group-item-heading">'+art.ATitle+'</h4>'+
		      '<p class="list-group-item-text">['+art.AEditTime.toLocaleString()+']</p>'+
		      '<p class="list-group-item-text" style="position: absolute; right: 10px;bottom: 10px">'+art.comNav.NName+'</p>'+
		      '<i class="glyphicon glyphicon-trash" style="position: absolute; right: 10px; top: 10px; font-size: 16px;"></i>'+
		    '</a>';
}

Article.setList = function(res){
	var inner = $("#article_list").empty();
	var data = res.resultMap.data;
	for(var i = 0 ; i < data.length ; i += 1){
		var art = data[i];
		var active = false;
		if(i == 0)active = true;
		var tmpl = Article.getTmpl(art,active);
		inner.append(tmpl);
	}
}

Article.getPreviousPagingTmpl = function(){
	return '<li>'+
			'<a aria-label="Previous" href="javascript:void(0)"><span aria-hidden="true">«</span></a>'+
		'</li>';
}

Article.getPagingTmpl = function(idx,active){
	var className = "";
	if(active)className = "active";
	return '<li class="'+className+'">'+
			'<a href="javascript:void(0)">'+idx+'</a>'+
		'</li>';
}

Article.getNextPagingTmpl = function(){
	return '<li>'+
			'<a aria-label="Next" href="javascript:void(0)"><span aria-hidden="true">»</span></a>'+
		'</li>';
}

Article.setPaging = function(res,idx){
	var pagination = $(".pagination").empty();
	var data = res.resultMap.data;
	var count = res.resultMap.count;
	var maxPage = Math.ceil(count / Article.maxResult);
	
	var beginIdx = Math.floor(idx - Article.pagingMaxShow / 2);
	var showCount = beginIdx + Article.pagingMaxShow - 1;
	if(beginIdx < 1){
		beginIdx = 1;
		showCount = Article.pagingMaxShow;
	}
	
	showCount = maxPage < showCount ? maxPage : showCount;
	pagination.attr("count",count);
	
	var previousPagingTmpl = Article.getPreviousPagingTmpl();
	pagination.append(previousPagingTmpl);
	
	for(var i = 1 ; i <= showCount ; i += 1){
		var active = false;
		if(idx == i)active = true;
		pagination.append(Article.getPagingTmpl(i,active));
	}
	pagination.append(Article.getNextPagingTmpl());
}

Article.isNormalType = function(type){
	if(type == 1 || type == 2)return true;
	return false;
}

Article.deleteArtEvent = function($this,artId,e){
	var $H4 = $this.parent().find("h4");
	if ( e && e.stopPropagation ) e.stopPropagation();
    else window.event.cancelBubble = true;
	reset();
	alertify.confirm( "是否删除新闻 '"+$H4.html()+"'" ,  function( e ){
		if( e ){
			Article.loading();
			serviceNews.deleteNews(artId,{callback:function(res){
				Article.loaded();
				if(res.code == 0){
					reset();
					alertify.success( "删除成功!");
					$this.parents(".list-group-item").remove();
					Article.reloadList();
				}else{
					reset();
					alertify.error( "删除失败!");
				}
			},exceptionHandler : function(e){
				Article.loaded();
				reset();
				alertify.error( "删除失败!",+ e);
			}})
		}
	} );
}

Article.setDataEvent = function(){
	var items = $(".list-group-item");
	items.each(function(idx,item){
		var $item = $(item);
		var type = $item.attr("artType");
		var artId = $item.attr("art");
		$item.click(function(){
			items.removeClass("active");
			var $this = $(this);
			$this.addClass("active");
			var artId = $this.attr("art");
			Article.editArticle(artId,type);
		});
		var $i = $item.find("i");
		if(!Article.isNormalType(type)){
			$i.hide();
		}else{
			$i.click(function(e){
				var $this = $(this);
				Article.deleteArtEvent($this,artId,e);
			});
		}
		
	});
}

Article.setPagingEvent = function(){
	var count = $(".pagination").attr("count");
	var maxPage = Math.ceil(count / Article.maxResult);
	var as = $(".pagination a");
	as.each(function(idx,a){
		var $a = $(a);
		$a.click(function(){
			var $this = $(this);
			var label = $this.attr("aria-label");
			if(label == "Previous"){
				Article.pageIndex = 1
			}else if(label == "Next"){
				Article.pageIndex = maxPage;
			}else{
				var idx = parseInt($this.html());
				Article.pageIndex = idx;
			}
			Article.setDataList(Article.pageIndex);
		});
	});
}

Article.editArticle = function(artId,artNavType){
	Article.loading();
	serviceNews.getNews(artId,{callback:function(res){
		Article.loaded();
		var data = res.resultMap.data;
		var nav = data.comNav;
		var createBtn = document.getElementById("createBtn");
		var editBtn = document.getElementById("editBtn");
		createBtn.className = "btn btn-default";
		editBtn.className = "btn btn-primary";
		createBtn.style.color = "#aaa";
		editBtn.style.color = "";
		
		var navBtn = $("#navType").attr("nid",nav.NId).html(nav.NName).parents("button");
		if(!Article.isNormalType(artNavType)){
			navBtn.prop("disabled",true);
			$("input[name=article_type]").prop("disabled",true);
		}else {
			navBtn.removeAttr("disabled");
			$("input[name=article_type]").removeAttr("disabled");
		}
		
		$("#article_id").val(data.id);
		var type = data.AIndexType;
		if(!type)type = 0 ;
		var $radios = $('input:radio[name=article_type]');
        $radios.filter('[value='+type+']').prop('checked', true);
        
        $("#author").val(data.AAuthorName);
        $("#desc").val( data.ADesc );
	    var files = data.comArticleFiles;
	    wup.fileEvent.addAll( files );
	    //Article.setFiles(files);
	    
	    var title = data.ATitle;
	    $("#title").val(title);
	    var context = data.AContext;
	    editor.setValue(context);
		
	},exceptionHandler : function(e){
		Article.loaded();
		console.log(e);
	}});
}
