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

//ARTICLE article ARTICLE article ARTICLE article ARTICLE article
//json from controller
$(function () {
let dataUrl = "/okaeri/forumArticle/artJoinTypeJoinMsg";
let xhr = new XMLHttpRequest();
xhr.open("GET", dataUrl, true);
xhr.send();
xhr.onload = function () {
  let data = JSON.parse(this.responseText);
  console.log(data);
  for (let i = 0; i < data.length; i++) {
	if (data[i].forArtEdittime !== null) {
	    $("table tbody").append(
	      "<tr><td class='id'>" +
	        data[i].forArtNo +
	        "</td><td class='type'>" +
	        data[i].type[0].forType +
	        "</td><td class='title'>" +
	        data[i].forArtTitle +
	        "</td><td class='content'>" +
	        data[i].forArtContent +
	        "</td><td class='posttime'>" +
	        data[i].forArtPosttime +
	        "</td><td class='edittime'>" +
	        data[i].forArtEdittime +
	        "</td><td class='mem'>" +
	        data[i].memAcct +
	        "</td><td class='state'>" +
	        data[i].artStateNo +
	        "</td><td class='modal-father'><a class='whole' href='#'>留言內容" +
	        "<div class='modal-returned hidden'>" +
	        "<div class='modal-container dataArray" + i + "'>" +
	        	"<h2>" + data[i].forArtTitle + "</h2>" +
	        	"<button class='btn--close-modal-returned'>&times;</button>" +
	        	"<div class='article_content'>" +
	            data[i].forArtContent +
	            	"<div style='text-align: right; font-size: 1em'>" +
	            	data[i].memAcct +
	            	"<br>" +
	            	"&emsp;發布時間：" + data[i].forArtPosttime +
	            	"&emsp;編輯時間：" + data[i].forArtEdittime +
	            	"</div>" +
	            "</div>" +
	            "<br /><br /><br />" +
	            "<h3>留言</h3>" +
	        "</div>" +
	        "</div>"+
	        "</a></td></tr>"
	    );
	} else {
		 $("table tbody").append(
			      "<tr><td class='id'>" +
			        data[i].forArtNo +
			        "</td><td class='type'>" +
			        data[i].type[0].forType +
			        "</td><td class='title'>" +
			        data[i].forArtTitle +
			        "</td><td class='content'>" +
			        data[i].forArtContent +
			        "</td><td class='posttime'>" +
			        data[i].forArtPosttime +
			        "</td><td class='edittime'>-" +
			        "</td><td class='mem'>" +
			        data[i].memAcct +
			        "<td class='state'>" +
			        data[i].artStateNo +
			        "</td><td class='modal-father'><a class='whole' href='#'>留言內容" +
			        "<div class='modal-returned hidden'>" +
			        "<div class='modal-container dataArray" + i + "'>" +
			        	"<h2>" + data[i].forArtTitle + "</h2>" +
			        	"<button class='btn--close-modal-returned'>&times;</button>" +
			        	"<div class='article_content'>" +
			            data[i].forArtContent +
			            	"<div style='text-align: right; font-size: 1em'>" +
			            	data[i].memAcct +
			            	"<br>" +
			            	"&emsp;發布時間：" + data[i].forArtPosttime +
			            	"&emsp;編輯時間：無 " + 
			            	"</div>" +
			            "</div>" +
			            "<br /><br /><br />" +
			            "<h3>留言</h3>" +
			        "</div>" +
			        "</div>"+
			        "</a></td></tr>"
			    );		
	}
    for (let j = 0; j < data[i].message.length; j++) {
    	console.log(data[0].message.length);
    	if (data[i].message[j].forMsgContent !== null) {
	    	$(".dataArray" + i + " h3").after(
		    	"<div class='article_message'>" +
			    	"<div>" + data[i].message[j].memAcct + "</div>" +
			        data[i].message[j].forMsgContent +
			        "<div style='text-align: right; font-size: 0.8em'>" + 
			        data[i].message[j].forMsgPosttime +
			        "</div>" +
			     "</div>"
		    	);
    	} else {
    		$(".dataArray" + i + " h3").after(
    		    "<div class='article_message' style='text-align: center;' >無</div>"
    		   	);
    	}
    }
  }

  var table = $("table");
  var currentPage = 0; // 當前頁默認值為0
  var pageSize = 15; // 每一頁顯示的數目
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

//LightBox
//returned cases
//document.addEventListener("click", function(e){
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