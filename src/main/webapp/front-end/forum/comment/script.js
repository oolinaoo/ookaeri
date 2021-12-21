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
		              	data[0].memAcct +
		              "</p>" +	
		            "</div>" +
		            "<div class='forum-content'>" +
		              "<p>" +
		                data[0].forArtContent +
		              "</p>" +
		            "</div>" +
		            "<hr />" +
		            "<div class='forum-icon modal-father'>" +
		              "<span><i class='fa fa-clock'></i>" +
		              	data[0].forArtPosttime +
		              "</span>&emsp;" +
		              "<span><i class='fa fa-eye'></i>" + 
		              	data[0].forArtView +
		              "</span>&emsp;" +
		              "<a class='report-button' href='#'><i class='fa fa-times'></i>檢舉" +
		              "<div class='modal-returned hidden'>" +
				        "<div class='modal-container'>" +
			        	"<div class='hidden-span1'>" +
			        	"<span hidden>" + artNo + "</span>" +
			        	"</div>" +
			        	"<button class='btn--close-modal-returned'>&times;</button>" +
			        	"<div class='inputStyle'>" +
			        	"<p>檢舉原因</p><br>" +
			        	"<input type='text' id='report-reason' required />" +
			        	"</div>" +
			        	"<div class='save-button'>" +
			        	"<input type='submit' value='檢舉' class='save' onclick='reportA(this)' />" +
			        	"</div>" +
			        "</div>" +
			        "</div>"+
		              "</a>" +
		            "</div>" 
		  	);
		  for (let i = 0; i < data[0].message.length; i++) {
			  if (data[0].message[i].forMsgContent != null) {
				  $(".refresh-comment").append(
					 "<div class='comment-wrap comment-message'>" +
						"<div class='comment-block'>" +
			              "<p class='comment-text'>" +
			                data[0].message[i].forMsgContent +
			              "</p>" +
			              "<div class='bottom-comment'>" +
							"<div class='comment-date'>" +
								"<span>" + 
								data[0].message[i].memAcct +
								"</span>&emsp;" +
								data[0].message[i].forMsgPosttime +
							"</div>" +
							"</div>" +
						"<div id='father' class='modal-father'>" +
						"<a class='report-button' href='#'>檢舉" +
						"<div class='modal-returned hidden'>" +
				        "<div class='modal-container'>" +
				        	"<div class='hidden-span1'>" +
				        	"<span hidden>" + artNo + "</span>" +
				        	"</div>" +
				        	"<div class='hidden-span2'>" +
				        	"<span hidden>" + data[0].message[i].forMsgNo + "</span>" +
				        	"</div>" +
				        	"<button class='btn--close-modal-returned'>&times;</button>" +
				        	"<div class='inputStyle'>" +
				        	"<p>檢舉原因</p><br>" +
				        	"<input type='text' id='report-reason' required />" +
				        	"</div>" +
				        	"<div class='save-button'>" +
				        	"<input type='submit' value='檢舉' class='save' onclick='report(this)' />" +
				        	"</div>" +
				        "</div>" +
				        "</div>"+
						"</a>" +
						"</div>" +
			            "</div>" +
			         "</div>" 
				  );
			  };
		  }
		};
	});

//LightBox
//document.addEventListener("click", function(e){
$("section").click(function(e){

	e.preventDefault();

	if (e.target.classList.contains("report-button")) {
		let parent = e.target.closest(".modal-father");
		let modal = parent.querySelector(".modal-returned");
		let overlay = document.querySelector(".overlay-returned");
		let btnCloseModal = parent.querySelector(".btn--close-modal-returned");
		
		modal.classList.remove("hidden");
		overlay.classList.remove("hidden");
		
		const closeModal = function () {
			modal.classList.add("hidden");
			overlay.classList.add("hidden");
		};
		btnCloseModal.addEventListener("click", closeModal);
		overlay.addEventListener("click", closeModal);

		document.addEventListener("keydown", function (e) {
		if (e.key === "Escape" && !modal.classList.contains("hidden")) {
			closeModal();
		}
		});
	} 
});

//report 彈窗
function report(obj) {
    if (prompt("確定要檢舉嗎？確定請輸入yes") == "yes") {
      let art = $(obj).parent().siblings(".hidden-span1").find("span").html();
      let msg = $(obj).parent().siblings(".hidden-span2").find("span").html();
      let reason = $(obj).parent().siblings(".inputStyle").find("input").val();
      let mem = "gina3";
      console.log(art);
      console.log(msg);
      console.log(reason);
      $(function() {
			let report_data = {
				"forArtNo" : art,
				"forMsgNo" : msg,
				"forReptContent" : reason,
				"memAcct" : mem
			};
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
  			if (this.readyState == 4 && this.status == 200) {
  				console.log(xhr);
  			}
  		};
			xhr.open("POST", "/okaeri/forumReport/add"); //post 告知後端
			xhr.setRequestHeader("Content-type", "application/json"); //告訴後端是用 JSON 格式
			var data = JSON.stringify(report_data); //將物件資料轉成字串
			console.log(data);
			xhr.send(data); //送出字串
		});
      alert ("檢舉成功");
      window.location = window.location.href;
    }
}

//article report 
function reportA(obj) {
    if (prompt("確定要檢舉嗎？確定請輸入yes") == "yes") {
      let art = $(obj).parent().siblings(".hidden-span1").find("span").html();
      let reason = $(obj).parent().siblings(".inputStyle").find("input").val();
      let mem = "gina3";
      console.log(art);
      console.log(reason);
      $(function() {
			let report_data = {
				"forArtNo" : art,
				"forReptContent" : reason,
				"memAcct" : mem
			};
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
  			if (this.readyState == 4 && this.status == 200) {
  				console.log(xhr);
  			}
  		};
			xhr.open("POST", "/okaeri/forumReport/add"); //post 告知後端
			xhr.setRequestHeader("Content-type", "application/json"); //告訴後端是用 JSON 格式
			var data = JSON.stringify(report_data); //將物件資料轉成字串
			console.log(data);
			xhr.send(data); //送出字串
		});
      alert ("檢舉成功");
      window.location = window.location.href;
    }
}