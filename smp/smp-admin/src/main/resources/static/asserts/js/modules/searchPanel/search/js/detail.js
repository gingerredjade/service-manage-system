//添加查询结果的相信文字信息
// function showMediaPlayer(url,title){
// 	var old = $.fn; 
// 	$.fn = newJquery.fn;
// 	$(".videoPanel").fadeIn();
// 	jwVideoPlayer(url);
// 	$(".videoPanel h4").text(title);
// 	$.fn = old;
// };

// function showPdfDiv(url){
// 	$("#pdfIframe").attr("src",url);
// 	//$(".pdfPanel").load(url);
// 	$(".pdfPanel").css("display","block");
// 	//var _url = $("#video-ul li").children("a").attr("url");

// };

// $("#closePdfPanel").click(function(){
// 	$(".pdfPanel").css("display","none");
// });

// $("#closePdfPanel").mouseover(function(){
// 	$(this).css("color","white");
// });

// $("#closePdfPanel").mouseout(function(){
// 	$(this).css("color","#c8c8c8");
// });


// function addAudioDetail (id, attribute) {
// 	if (id == null) {
// 		return; 
// 	}
// 	var container = $("#audio_ul");
// 	container.attr("elmid", id);
// 	$("#audio_ul").empty();
	
// 	var l1 = $("<li>");
// 	l1.appendTo(container);
	
// 	var a2 = $("<a>");
// 	a2.attr("href", "#");
// 	a2.attr("url", "modules/leftPanel/search/media/audios/1.mp3");
// 	a2.css({"text-decoration": "none", "display":"block","margin":"10px" });
// 	a2.appendTo(l1);
// 	var span1 = $("<span>").addClass("sl-icon fa fa-music MarginTop25");
// 	span1.appendTo(a2);
	
// 	var div2 = $("<div>").addClass("sl-content");
// 	div2.css({"font-family": 'Microsoft YaHei'});
// 	var h4 = $("<h4>").addClass("sl-main");
// 	h4.text("长缨战舰介绍");
// 	h4.appendTo(div2);
// 	var h5 = $("<h5>").addClass("sl-sub");
// 	h5.append("日期：2016-03-10<br>时长：00:00:10");
// 	h5.appendTo(div2);
// 	div2.appendTo(a2);
// 	if (showMediaPlayer) {
// 		$("#audio_ul li>a").on("click", function(){
// 			var _url = $("#audio_ul li").children("a").attr("url");
// 			var title = $(this).find("h4").text();
// 			showMediaPlayer(_url,title);
// 		});
// 	}
// }


// function addVideoDetail(rst) {
// 	var attribute = rst;
// 	var container = $("#video-ul");
// 	//container.attr("elmid", id);
// 	$("#video-ul").empty();
	
// 	if (attribute != null) {
// 		var medias = attribute.multimediaDatas;
// 		//if (medias.length > 0) {
// 			for (var i=0; i<medias.length; i++) {
// 				//var mmData = medias[i].mmData;
// 				var mmName = medias[i].mmName;
// 				//var mmType = "MP4";
// 				var mmType = medias[i].mmType;
// 				var mmSubType =medias[i].mmSubType;
// 				//var randomI = parseInt(15 * Math.random())+1;
// 				var mmURL = medias[i].mmURL;
// 				mmURL = mmURL.replace(/\\/g,"/");
// 				//var mmURL = "modules/leftPanel/search/media/videos/"+ randomI +".mp4";
// 				if (mmType == "VIDEO" ) {
// 					var l1 = $("<li>");
// 					l1.appendTo(container);
// 					var a2 = $("<a>");
// 					//a2.attr("href", "#");
// 					//a2.attr("url","/demoData/" + medi  as[i].url);
// 					//a2.attr("url", mmURL);
// 					//a2.attr("url", __serviceURL.video_+mmURL+__serviceURL._video);
// 					a2.attr("url", __serviceURL.video_+mmURL);
// 					//a2.attr("url", "http://192.168.56.157:50075/webhdfs/v1/multimedia/SEARCH/220114005002/daodan.mp4?op=OPEN&namenoderpcaddress=192.168.56.157:8020&offset=0");
// 					//a2.attr("url", "http://192.168.56.183:8082/get_hdfs_stream?path=/multimedia/bingyao/000003/jwplayer中文测试.mp4");
// 					//a2.attr("url", "http://192.168.55.30:8090/test/video/war3.mp4");
// 					//a2.attr("url", "http://192.168.56.104:9001/video/fsdata?type=video&fpath=/multimedia/bingyao/000001/haijun.mp4");
// 					//a2.attr("url","http://192.168.56.104:9000/hdfsviewer/webhdfs/v1/multimedia/bingyao/000001/haijun4.mp4?op=OPEN&namenoderpcaddress=192.168.56.140:8020&offset=0");
// 					//a2.attr("url","http://192.168.56.104:50075/webhdfs/v1/test/0.flv?op=OPEN&namenoderpcaddress=192.168.56.104:8020&offset=0");
// 					//a2.attr("url","http://192.168.56.104:50075/webhdfs/v1/test/Warcraft.mp4?op=OPEN&namenoderpcaddress=192.168.56.104:8020&offset=0");
// 					//a2.attr("url","http://192.168.55.30:8090/test/video/1.flv");
// 					//a2.attr("url","http://192.168.55.30:8090/test/video/warcraft.mp4");
// 					a2.attr("mmSubType",mmSubType);
// 					a2.css({"text-decoration": "none", "display":"block","margin":"10px","cursor": "pointer" });
// 					a2.appendTo(l1);
// 					var span1 = $("<span>").addClass("sl-icon fa fa-video-camera MarginTop25");
// 					span1.appendTo(a2);
					
// 					var div2 = $("<div>").addClass("sl-content");
// 					div2.css({"font-family": 'Microsoft YaHei'});
// 					var h4 = $("<h4>").addClass("sl-main");
// 					h4.text(mmName);
// 					h4.appendTo(div2);
// 					var h5 = $("<h5>").addClass("sl-sub");
// 					h5.append("日期：2016-03-10<br>时长：00:00:10");
// 					h5.appendTo(div2);
// 					div2.appendTo(a2);
// 				}
// 			}
// 			if (1) {
// 				$("#video-ul li>a").on("click", function(){
// 					var mmSubTypes = a2.attr("mmSubType");
// 					if(mmSubTypes == "MP4" || mmSubTypes == "mp4"){
// 						var _url = $(this).attr("url");
// 						var title = $(this).find("h4").text();
// 						showMediaPlayer(_url,title);
// 					}else {
// 						alert("不支持在线播放"+mmSubTypes+"格式的视频");
// 					}

// 				});
// 			};
			
// 		}
// 	//}
// }


function addTextDetail(rst) {
	var attribute = rst;
	$("#tab-info").empty();
	var a1 = $("<p>");
	a1.appendTo($("#tab-info"));
	a1.css({"font-family": "'Microsoft YaHei', sans-serif"});
	if (attribute != null) {
		var phoneNumber = attribute.attrData[4];
		var zipCode = attribute.attrData[3];
		var address = attribute.attrData[5];
		if(phoneNumber=="" || phoneNumber=="null") phoneNumber=parseInt(100000000 * Math.random());;
		if(zipCode=="" || zipCode=="null" || zipCode==null) zipCode=parseInt(1000000 * Math.random());;
		if(address=="" || address=="null" || address==null) address="无详细地址";
		var items = '<div style="margin-left: 40px;margin-top: 15px">' +
						'<strong style="font-size: 14px;margin-left: -25px">相关信息:</strong>&nbsp;&nbsp;<br><br>' +
						'<span><b>电话号码：</b>'+ phoneNumber +'</span><br>' +
						'<span><b>邮编：</b>'+ zipCode +'</span><br>' +
						'<span><b>地址：</b>'+ address +'</span><br>'+
					'</div>';;

		//"typename","flm","qym","sxm","jyjcdlxxbm","szgjmc","szdmc","dlfw","zbtd","jianshu","cjsj","cjdw"
		var itemsBY = '<div style="margin-left: 12px;">' +
						'<strong style="font-size: 14px;margin-left: -10px;">相关信息:</strong>&nbsp;&nbsp;<br><br>' +
						'<span"><b>兵要数据类型名称：</b>'+ attribute.attrData[6] +'</span><br>' +
						'<span"><b>分类码：</b>'+ attribute.attrData[7] +'</span><br>' +
						'<span"><b>区域码：</b>'+ attribute.attrData[8] +'</span><br>' +
						'<span"><b>顺序码：</b>'+ attribute.attrData[9] +'</span><br>' +
						'<span"><b>军用基础地理信息编码：</b>'+ attribute.attrData[10] +'</span><br>' +
						'<span"><b>所在国家（地区）名称：</b>'+ attribute.attrData[11] +'</span><br>' +
						'<span"><b>所在地名称：</b>'+ attribute.attrData[12] +'</span><br>' +
						'<span"><b>地理范围：</b>'+ attribute.attrData[13] +'</span><br>' +
						'<span"><b>植被特点：</b>'+ attribute.attrData[14] +'</span><br>' +
						'<span"><b>采集时间：</b>'+ attribute.attrData[16] +'</span><br>' +
						'<span"><b>采集单位：</b>'+ attribute.attrData[17] +'</span><br>' +
						'<span"><b>简述：</b>'+ attribute.attrData[15] +'</span><br>'+
				'</div>';
		if(attribute.attrData[18]=="by"){
			a1.append (itemsBY);
		}else {
			a1.append (items);
		}
	}
}


// function addPicDetail(rst) {
// 	//if (id == null) {
// 	//	return;
// 	//}
// 	var attribute = rst;
// 	var container = $("#_slider");
// 	var ii=0;
// 	$("#_slider").empty();
// 	//if (attribute != null) {
// 		var medias = attribute.multimediaDatas;
// 		//if (medias.length > 0) {
// 			for (var i=0; i<medias.length; i++) {
// 				//var mmData = medias[i].mmData;
// 				var mmName = medias[i].mmName;
// 				//var mmSubType ="jpg";
// 				var mmType =medias[i].mmType;
// 				var mmSubType =medias[i].mmSubType;
// 				//var mmType = medias[i].mmType;//"MMDATATYPE_PIC"
// 				var randomI = parseInt(85 * Math.random())+1;
// 				//var mmURL = "modules/leftPanel/search/media/pics/"+ randomI +".jpg";
// 				var mmURL = __serviceURL.pics+medias[i].mmURL;
// 				if (mmType == "PIC") {
// 					ii+=1;
// 					var li1 = $("<li>");
// 					li1.appendTo(container);
// 					var em1 = $("<em>");
// 					em1.appendTo(li1);
// 					var img = $("<img>");
// 					img.appendTo(em1);
// 					img.attr("src",mmURL);

// 					var a2 = $("<a>");
// 					a2.attr("href",mmURL);
// 					//a2.attr("title", mmData);
// 					a2.attr("data-rel", "fancybox-button");
// 					a2.attr("data-fancybox-group", "gallery");
// 					a2.addClass("fancybox-button fancybox");
// 					if(ii%2){
// 						var i2 = $("<i>").addClass("fa fa-search-plus icon-hover icon-hover-1");
// 					}
// 					else{
// 						var i2 = $("<i>").addClass("fa fa-search-plus icon-hover icon-hover-2");
// 					}
// 					i2.appendTo(a2);
// 					a2.appendTo(em1);
// 					var a3 = $("<a>").addClass("bxslider-block");
// 					a3.css({"font-family": "'Microsoft YaHei', sans-serif"});
// 					//a3.append("<span>相关图片"+ii+"</span>");
// 					a3.append("<span>"+mmName.split(".")[0]+"</span>");
// 					a3.appendTo(li1);
// 				}
// 			}
// 			window.setTimeout(function() {
// 				var old = $.fn; 
// 				$.fn = newJquery.fn;
// 				$('.fancybox').fancybox();
// 				$.fn = old;
// 			}, 1000);


// 		//}
// 	//}

// }
