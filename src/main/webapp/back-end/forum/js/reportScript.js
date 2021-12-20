// page
function changCss(obj) {
  var arr = document.getElementsByTagName("a");
  for (var i = 0; i < arr.length; i++) {
    if (obj == arr[i]) {
      // 當前頁樣式
      obj.style.backgroundColor = "#B5495B";
      obj.style.color = "#ffffff";
    } else {
      arr[i].style.color = "";
      arr[i].style.backgroundColor = "";
    }
  }
}

// ARTICLE article ARTICLE article ARTICLE article ARTICLE article
// json from controller
$(function () {
let dataUrl = "/okaeri/forumReport/artJoinAll";
let xhr = new XMLHttpRequest();
xhr.open("GET", dataUrl, true);
xhr.send();
xhr.onload = function () {
  let data = JSON.parse(this.responseText);
  console.log(data);
  $("table tbody").append(
	      "<tr><td class='id'>" +
	        data[0].forReptNo +
	        "</td><td class='artId'>" +
	        data[0].forArtNo +
	        "</td><td class='msgId'>" +
	        data[0].forMsgNoRept +
	        "</td><td class='reason'>" +
	        data[0].forReptContent +
	        "</td><td class='mem'>" +
	        data[0].memAcct +
	        "</td><td class='admin'>" +
	        data[0].adminAcct +
	        "</td><td class='state'>" +
	        data[0].forReptState +
	        "</td><td class='modal-father '><a class='whole' href='#'>檢舉原因" +
	        "<div class='modal-returned hidden'>" +
	        "<div class='modal-container'>" +
	        	"<h2>檢舉原因</h2>" +
	        	"<button class='btn--close-modal-returned'>&times;</button>" +
	        	"<div class='report_content'>" +
	        	data[0].forReptContent +
	        	"<div style='text-align: right; font-size: 1em'>" +
            	data[0].memAcctRept +
            	"</div>" +
	            "</div>" +
	            "<br /><br /><br />" +
	            "<h3>" + data[i].forArtTitle + "</h3>" +
	        	"<div class='article_content article_message'>" +
	        	"<div>發文者：" + data[i].memAcct + "</div>" +
	            data[0].forArtContent +
	            "<div style='text-align: right; font-size: 0.8em'>" + 
	            data[0].forArtPosttime +
	            "</div>" +
	            "</div>" +
	            "<h3 class='reptrept1'>留言</h3>" +
	            "<div class='article_message'>" +
	            "<div>留言編號：" + data[i].forMsgNo + "</div>" +
	        	"<div>留言者：" + data[0].memAcctMsg + "</div>" +
	            data[0].forMsgContent +
	            "<div style='text-align: right; font-size: 0.8em'>" + 
	            data[0].forMsgPosttime +
	            "</div>" +
	            "</div>" +
	        "</div>" +
	        "</div>"+
	        "</a></td></tr>"
  );
  for (let i = 1; i < data.length; i++) {

	  if (data[i-1].forReptNo != data[i].forReptNo) {
		  if (data[i].forMsgNoRept == null) {
		  $("table tbody").append(
			      "<tr><td class='id'>" +
			        data[i].forReptNo +
			        "</td><td class='artId'>" +
			        data[i].forArtNo +
			        "</td><td class='msgId'>-" +
			        "</td><td class='reason'>" +
			        data[i].forReptContent +
			        "</td><td class='mem'>" +
			        data[i].memAcct +
			        "</td><td class='admin'>" +
			        data[i].adminAcct +
			        "</td><td class='state'>" +
			        data[i].forReptState +
			        "</td><td class='modal-father '><a class='whole' href='#'>檢舉原因" +
			        "<div class='modal-returned hidden'>" +
			        "<div class='modal-container'>" +
			        	"<h2>檢舉原因</h2>" +
			        	"<button class='btn--close-modal-returned'>&times;</button>" +
			        	"<div class='report_content'>" +
			        	data[i].forReptContent +
			        	"<div style='text-align: right; font-size: 1em'>" +
		            	data[i].memAcctRept +
		            	"</div>" +
			            "</div>" +
			            "<br /><br /><br />" +
			            "<h3>" + data[i].forArtTitle + "</h3>" +
			        	"<div class='article_content article_message'>" +
			        	"<div>發文者：" + data[i].memAcct + "</div>" +
			            data[i].forArtContent +
			            "<div style='text-align: right; font-size: 0.8em'>" + 
			            data[i].forArtPosttime +
			            "</div>" +
			            "</div>" +
			            "<h3 class='reptrept" + data[i].forReptNo + "'>留言</h3>" +
			            "<div class='article_message'>" +
			            "<div>留言編號：" + data[i].forMsgNo + "</div>" +
			        	"<div>留言者：" + data[i].memAcctMsg + "</div>" +
			            data[i].forMsgContent +
			            "<div style='text-align: right; font-size: 0.8em'>" + 
			            data[i].forMsgPosttime +
			            "</div>" +
			            "</div>" +
			        "</div>" +
			        "</div>"+
			        "</a></td></tr>"
		    );
		  } else {
			  $("table tbody").append(
				      "<tr><td class='id'>" +
				        data[i].forReptNo +
				        "</td><td class='artId'>" +
				        data[i].forArtNo +
				        "</td><td class='msgId'>" +
				        data[i].forMsgNoRept +
				        "</td><td class='reason'>" +
				        data[i].forReptContent +
				        "</td><td class='mem'>" +
				        data[i].memAcct +
				        "</td><td class='admin'>" +
				        data[i].adminAcct +
				        "</td><td class='state'>" +
				        data[i].forReptState +
				        "</td><td class='modal-father '><a class='whole' href='#'>檢舉原因" +
				        "<div class='modal-returned hidden'>" +
				        "<div class='modal-container'>" +
				        	"<h2>檢舉原因</h2>" +
				        	"<button class='btn--close-modal-returned'>&times;</button>" +
				        	"<div class='report_content'>" +
				        	data[i].forReptContent +
				        	"<div style='text-align: right; font-size: 1em'>" +
			            	data[i].memAcctRept +
			            	"</div>" +
				            "</div>" +
				            "<br /><br /><br />" +
				            "<h3>" + data[i].forArtTitle + "</h3>" +
				        	"<div class='article_content article_message'>" +
				        	"<div>發文者：" + data[i].memAcct + "</div>" +
				            data[i].forArtContent +
				            "<div style='text-align: right; font-size: 0.8em'>" + 
				            data[i].forArtPosttime +
				            "</div>" +
				            "</div>" +
				            "<h3 class='reptrept" + data[i].forReptNo + "'>留言</h3>" +
				            "<div class='article_message'>" +
				            "<div>留言編號：" + data[i].forMsgNo + "</div>" +
				        	"<div>留言者：" + data[i].memAcctMsg + "</div>" +
				            data[i].forMsgContent +
				            "<div style='text-align: right; font-size: 0.8em'>" + 
				            data[i].forMsgPosttime +
				            "</div>" +
				            "</div>" +
				        "</div>" +
				        "</div>"+
				        "</a></td></tr>"
			  );
		  }
	  } else {
		  $(".reptrept" + data[i].forReptNo).after( 
				  "<div class='article_message'>" +
				  	"<div>留言編號：" + data[i].forMsgNo + "</div>" +
		        	"<div>留言者：" + data[i].memAcctMsg + "</div>" +
		            data[i].forMsgContent +
		            "<div style='text-align: right; font-size: 0.8em'>" + 
		            data[i].forMsgPosttime +
		            "</div>" +
		            "</div>"
		  );
	  }
  }
  var table = $("table");
  var currentPage = 0; // 當前頁默認值為0
  var pageSize = 10; // 每一頁顯示的數目
  table.bind("paging", function () {
    table
      .find("tbody tr")
      .hide()
      .slice(currentPage * pageSize, (currentPage + 1) * pageSize)
      .show();
  });
  var sumRows = table.find("tbody tr").length;
  var sumPages = Math.ceil(sumRows / pageSize); // 總頁數

  var pager = $('<div class="page"></div>'); // 新建div，放入a標簽,顯示底部分頁碼
  for (var pageIndex = 0; pageIndex < sumPages; pageIndex++) {
    $(
      '<a href="#" id="pageStyle" onclick="changCss(this)"><span>' +
        (pageIndex + 1) +
        "</span></a>"
    )
      .bind("click", { newPage: pageIndex }, function (event) {
        currentPage = event.data["newPage"];
        table.trigger("paging");
        // 觸發分頁函數
      })
      .appendTo(pager);
    pager.append(" ");
  }
  pager.insertAfter(table);
  table.trigger("paging");

  // 默認第一頁的a標簽效果
  var pagess = $("#pageStyle");
  pagess[0].style.backgroundColor = "#B5495B";
  pagess[0].style.color = "#ffffff";
};
});

// LightBox
// returned cases
// document.addEventListener("click", function(e){
$("table").click(function(e){

	e.preventDefault();

	if (e.target.classList.contains("whole")) {
		console.log("1");
		let parent = e.target.closest(".modal-father");
		let modal = parent.querySelector(".modal-returned");
		let overlay = document.querySelector(".overlay-returned");
		let btnCloseModal = parent.querySelector(
			".btn--close-modal-returned"
		);
		
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