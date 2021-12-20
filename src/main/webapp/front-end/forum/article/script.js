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
//json from controller
$(function () {
	let url =
	  "/okaeri/forumArticle/listAll";
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send();
	xhr.onload = function () {
	  let data = JSON.parse(this.responseText);
	  console.log(data);
	  for (let i = 0; i < data.length; i++) {
		$(".forum-page").append(
				"<div class='forum-row' >" +
		          "<div class='forum-row-box' >" +
		            "<div class='forum-title'>" +
		              "<h3>" +
		                "<a href='../comment/comment.html?forArtNo=" + data[i].forArtNo + "' target='_blank'>" +
		                data[i].forArtTitle + "</a>" +
		              "</h3>" +
		            "</div>" +
		            "<div class='forum-content'>" +
		              "<p>" +
		                data[i].forArtContent +
		              "</p>" +
		            "</div>" +
		            "<hr />" +
		            "<div class='forum-icon'>" +
		              "<span><i class='fa fa-clock'></i>" +
		              	data[i].forArtPosttime +
		              "</span>&emsp;" +
		              "<span><i class='fa fa-eye'></i>" + 
		              	data[i].forArtView +
		              "</span>&emsp;" +
		              "<a href='../comment/comment.html?forArtNo=" + data[i].forArtNo + "' target='_blank'>" +
		              "<i class='fa fa-comment-dots'></i>留言</a>&emsp;" +
		            "</div>" +
		          "</div>" +
		        "</div>"
		);
	  }
	  var table = $(".forum-page");
	  var currentPage = 0; // 當前頁默認值為0
	  var pageSize = 5; // 每一頁顯示的數目
	  table.bind("paging", function () {
		table
		  .find(".forum-row")
		  .hide()
		  .slice(currentPage * pageSize, (currentPage + 1) * pageSize)
		  .show();
	  });
	  var sumRows = table.find(".forum-row").length;
	  var sumPages = Math.ceil(sumRows / pageSize); // 總頁數

	  var pager = $('<div class="page" id="clear"></div>'); // 新建div，放入a標簽,顯示底部分頁碼
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
// returned cases //
const modalReturned = document.querySelector(".modal-returned");
const overlayReturned = document.querySelector(".overlay-returned");
const btnCloseModalReturned = document.querySelector(".btn--close-modal-returned");
const btnsOpenModalReturned = document.querySelectorAll(".forum-button");

// returned cases //
const openModalReturned = function (e) {
  e.preventDefault();
  modalReturned.classList.remove("hidden");
  overlayReturned.classList.remove("hidden");
};

const closeModalReturned = function () {
  modalReturned.classList.add("hidden");
  overlayReturned.classList.add("hidden");
};

btnsOpenModalReturned.forEach(btn =>
  btn.addEventListener("click", openModalReturned)
);
btnCloseModalReturned.addEventListener("click", closeModalReturned);
overlayReturned.addEventListener("click", closeModalReturned);

document.addEventListener("keydown", function (e) {
  if (e.key === "Escape" && !modalReturned.classList.contains("hidden")) {
    closeModalReturned();
  }
});

//New Post
if(typeof(Storage) !== "undefined") {

$('.editor').keypress(function(){
  $(this).find('.saved').detach();
});
  $('.editor').html(localStorage.getItem("wysiwyg")) ;
  
  $('button[data-func="save"]').click(function(){
    $content = $('.editor').html();
    localStorage.setItem("wysiwyg", $content);
    $('.editor').append('<span class="saved"><i class="fa fa-check"></i></span>').fadeIn(function(){
      $(this).find('.saved').fadeOut(500);
    });
  });
  
  $('button[data-func="clear"]').click(function(){
    $('.editor').html('');
    localStorage.removeItem("wysiwyg");
  });
} 

  