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

//MESSAGE message MESSAGE message MESSAGE message MESSAGE message
//json from controller
$(function () {
let dataUrl = "/okaeri/forumMessage/msgJoinArtJoinType";
let xhr = new XMLHttpRequest();
xhr.open("GET", dataUrl, true);
xhr.send();
xhr.onload = function () {
  let data = JSON.parse(this.responseText);
  console.log(data);
  for (let i = 0; i < data.length; i++) {
		  $("table tbody").append(
			      "<tr><td class='id'>" +
			        data[i].forMsgNo +
			        "</td><td class='content'>" +
			        data[i].forMsgContent +
			        "</td><td class='artId'>" +
			        data[i].forArtNo +
			        "</td><td class='artTitle'>" +
			        data[i].forArtTitle +
			        "</td><td class='posttime'>" +
			        data[i].forMsgPosttime +
			        "</td><td class='mem'>" +
			        data[i].memAcctMsg +
			        "</td><td class='state'>" +
			        data[i].forMsgState +
			        "</td><td class='modal-father'><a class='whole' href='#'>文章內容" +
			        "<div class='modal-returned hidden'>" +
			        "<div class='modal-container dataArray" + i + "'>" +
			        	"<h2>" + data[i].forArtTitle + "</h2>" +
			        	"<button class='btn--close-modal-returned'>&times;</button>" +
			        	"<div class='article_content'>" +
			            data[i].forArtContent +
			            	"<div style='text-align: right; font-size: 1em'>" +
			            	data[i].memAcct +
			            	"</div>" +
			            "</div>" +
			            "<br /><br /><br />" +
			            "<h3>留言</h3>" +			            
			        "</div>" +
			        "</div>"+
			        "</a></td></tr>"
		    );
		  for (j = 0; j < data.length; j++) {
			  if (data[j].forArtNo == data[i].forArtNo) {
				  $(".dataArray" + i + " h3").after(
						  "<div class='article_message'>" +
							"<div>" + data[j].memAcctMsg + "</div>" +
						  data[j].forMsgContent +
						  "<div style='text-align: right; font-size: 0.8em'>" + 
						  data[j].forMsgPosttime +
						  "</div>" +
						"</div>"
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