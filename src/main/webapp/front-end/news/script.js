// scroll
var ruleShinker = function () {
  var rule = $(".rule-nav"),
    ruleHeight = $(".rule-nav").outerHeight(true);
  $(rule).parent().css("padding-top", ruleHeight);
  $(window).scroll(function () {
    var scrollOffset = $(window).scrollTop();
    if (scrollOffset < ruleHeight) {
      $(rule).css("height", ruleHeight - scrollOffset);
    }
    if (scrollOffset > ruleHeight - 215) {
      rule.addClass("fixme");
    } else {
      rule.removeClass("fixme");
    }
  });
};
ruleShinker();

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

// json from cotroller
$(function () {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", "/okaeri/news/listAllD", true);
	xhr.send();
	xhr.onload = function () {
	  let data = JSON.parse(this.responseText);
	  console.log(data);
	  for (let i = 0; i < data.length; i++) {
		$("#table tbody").append(
		  	"<tr><td class='time'>" +
			data[i].newsTime +
			"</td><td class='category'>" +
			data[i].newsTypeNo +
			"</td><td class='title modal-father'><a class='title_link' href='#'>" +
			data[i].newsTitle +
			"<div class='modal-returned hidden'>" +
			"<div class='modal-container'>" +
			"<button class='btn--close-modal-returned'>&times;</button>" +
			"<h2 class='modal-header'>" + data[i].newsTitle + "</h2>" +
			"<p class='modal-sub-header'>" +
			data[i].adminAcct + "<span><i class='fa fa-pencil-alt'></i>&emsp;</span>" + "<span class='modal-sub-header_time'>"
			+
			data[i].newsTime + "</span>" +
			"</p>" +
			"<div class='news-content'>" +
			"<p>" + data[i].newsContent + "</p>" +
			"</div>" +
			"</div>" +
			"</div>" +
			"</a>" +
			"</td>" +
			"</tr>"
		);
	  }
	  var table = $("#table");
	  var currentPage = 0; // 當前頁默認值為0
	  var pageSize = 8; // 每一頁顯示的數目
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
// returned cases //
document.addEventListener("click", function(e){

	e.preventDefault();

	if (e.target.classList.contains("title_link")) {
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

//ajax to logout
$("#profile_logout").on("click", function()  {
	console.log("enter");
	let url = "/okaeri/login/logout";
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send(); 
	xhr.onload = function() {
		let data = JSON.parse(this.responseText);
		console.log(data);
		 if (data == true) {
		 window.location = "/okaeri/login/login.html"
		 }
	};
});