// 藉由網址傳值，取得forArtNo
function GetRequest(){
   let url = window.location.search; //獲取url中"?"後的字串
   let theRequest = new Object();
   if (url.indexOf("?") != -1) {
      let str = url.substr(1);
      strs = str.split("&");
      for(let i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}

//呼叫方式
let Request = new Object();
Request = GetRequest();
let artNo = Request['forArtNo'];
console.log(artNo);

//Ajax forArtNo
$(function () {
		let url =
		  "/okaeri/forumArticle/artNoJoinMsg";
		let art_data = {
			"forArtNo" : artNo
		}
		let xhr = new XMLHttpRequest();
		xhr.open("POST", url);
		xhr.setRequestHeader("Content-type", "application/json"); //告訴後端是用 JSON 格式
		let art = JSON.stringify(art_data); //將物件資料轉成字串
		console.log(art);
		xhr.send(art); //送出字串
		xhr.onload = function () {
		  let data = JSON.parse(this.responseText);
		  console.log(data);
		  $(".forum-row-box").append(
		            "<div class='forum-title'>" +
		              "<h3>" +
		                data[0].forArtTitle +
		              "</h3>" +
		              "<p>" +
		              	data[0].memAcctArt +
		              "</p>" +	
		            "</div>" +
		            "<div class='forum-content'>" +
		              "<p>" +
		                data[0].forArtContent +
		              "</p>" +
		            "</div>" +
		            "<hr />" +
		            "<div class='forum-icon'>" +
		              "<span><i class='fa fa-clock'></i>" +
		              	data[0].forArtPosttime +
		              "</span>&emsp;" +
		              "<span><i class='fa fa-eye'></i>" + 
		              	data[0].forArtView +
		              "</span>&emsp;" +
		              "<a href='#'><i class='fa fa-times'></i>檢舉</a>" +
		            "</div>" 
		  	);
		  for (let i = 0; i < data[0].message.length; i++) {
				  $(".refresh-comment").after(
					 "<div class='comment-wrap comment-message'>" +
						"<div class='comment-block'>" +
			              "<p class='comment-text'>" +
			                data[0].message[i].forMsgContent +
			              "</p>" +
			              "<div class='bottom-comment'>" +
							"<div class='comment-date'>" +
								"<span>" + 
								data[0].message[i].memAcctMsg +
								"</span>&emsp;" +
								data[0].message[i].forMsgPosttime +
							"</div>" +
							"<ul class='comment-actions'>" +
								"<li class='complain'>Report</li>" +
								"<li class='reply'>Reply</li>" +
							"</ul>" +
						"</div>" +
			            "</div>" +
			         "</div>" 
			);
		  }
		};
	});