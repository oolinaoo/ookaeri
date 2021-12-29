// sidebar dropdown
var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
  this.classList.toggle("active");
  var dropdownContent = this.nextElementSibling;
  if (dropdownContent.style.display === "block") {
  dropdownContent.style.display = "none";
  } else {
  dropdownContent.style.display = "block";
  }
  });
}

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

//json from servlet listallA
$(function init() {
	var url =
	  "/okaeri/news/listAllA";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (var i = 0; i < data.length; i++) {
		$("table tbody").append(
				"<tr><td class='id'>" + 
				data[i].newsNo +
				"</td><td class='type' contenteditable='false'>" +
				data[i].newsTypeNo +
				"</td><td class='title' contenteditable='false'>" +
				data[i].newsTitle +
				"</td><td class='content' contenteditable='false'>" +
				data[i].newsContent +
				"</td><td class='time' contenteditable='false'>" +
				data[i].newsTime +
				"</td><td class='admin'>" +
				data[i].adminAcct +
				"</td><td class='state' contenteditable='false'>" +
				`${data[i].newsStateNo == 0 ? "上架" : "下架"}` +
				"</td><td class='finalActionsCol'><i class='fa fa-minus-circle' aria-hidden='true'></i> <i class='fa fa-edit' aria-hidden='true'></i> </td></tr>"
		);
	  }
	  var table = $("table");
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

//edit Buttons
$(document).ready(function() {

  $("table").on("click", ".fa-minus-circle", function() {
    if (prompt("Are You Sure You Want to Delete this Row? Type 'yes' to Confirm this") == "yes") {
      $(this).closest('tr').remove();
    } else {}
  });

  $("table").on("click", ".fa-edit, .fa-save", function() {
    let thisRow = $(this).parent().siblings();
    let row = $(this).parent().parent();
    let editOn = $(this).hasClass("editMode");

    $('td:last-child').attr('contenteditable', 'false');
    $('td:last-child').css('background-color', 'transparent');

    if (editOn == false) {
      $(thisRow).attr('contenteditable', 'true');
      $(row).find('td:eq(0)').attr('contenteditable', 'false');
      select1 = 
    	  `
    	  <div class="inputStyle editTypeOnly">
            <select id="editType" required style="width: inherit;"> 
              <option value="" disabled>公告類別</option>
              <option value="會議">會議</option>
              <option value="公告" selected>公告</option>
              <option value="其他">其他</option>
            </select>
          </div>
    	  `;
      $(row).find('td:eq(1)').html(select1);
      $(row).find('td:eq(5)').attr('contenteditable', 'false');
      $(row).find('td:eq(5)').html($('.menu span span span').html());
      select2 =
    	  `
    	  <div class="inputStyle editStateOnly">
            <select id="editState" required style="width: inherit;"> 
              <option value="" disabled>狀態</option>
              <option value="0" selected>上架</option>
              <option value="1">下架</option>
            </select>
          </div>
    	  `;
      $(row).find('td:eq(6)').html(select2);
      $(thisRow).css('background-color', '#EBECF0');
      $(this).removeClass("fa-edit");
      $(this).addClass("fa-save editMode");
    } else if (editOn == true) {
      $(thisRow).attr('contenteditable', 'false');
      $(thisRow).css('background-color', 'transparent');
      $(row).find("td:eq(1)").html($("#editType").val());
      $(row).find("td:eq(6)").html(`${$("#editState").val() == 0 ? "上架" : "下架"}`);
      $(this).removeClass("fa-save editMode");
      $(this).addClass("fa-edit");
      $(function() {
    		const newsId = $(row).find(".id").html();
    		const type = $(row).find(".type").html();
    		const title = $(row).find(".title").html();
    		const content = $(row).find(".content").html();
    		const time = $(row).find(".time").html();
    		const admin = $('.menu span span span').html();
    		const state = `${$(row).find(".state").html() == "上架" ? "0" : "1"}`;
    		let form_data = {
    			"newsNo" : newsId,
    			"newsTypeNo" : type,
    			"newsTitle" : title,
    			"newsContent" : content,
    			"newsTime" : time,
    			"adminAcct" : admin,
    			"newsStateNo" : state
    		};
    		let xhr = new XMLHttpRequest();
    		xhr.onreadystatechange = function() {
    			if (this.readyState == 4 && this.status == 200) {
    				console.log(xhr);
    			}
    		};
    		xhr.open("POST", "/okaeri/news/update"); //post 告知後端
    		xhr.setRequestHeader("Content-type", "application/json"); //告訴後端是用 JSON 格式
    		let data = JSON.stringify(form_data); //將物件資料轉成字串
    		console.log(data);
    		xhr.send(data); //送出字串
    	})
    }
  });

});

// LightBox
// returned cases //
const modalReturned = document.querySelector(".modal-returned");
const overlayReturned = document.querySelector(".overlay-returned");
const btnCloseModalReturned = document.querySelector(
  ".btn--close-modal-returned"
);
const btnsOpenModalReturned = document.querySelectorAll(".newPost-button");

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

btnsOpenModalReturned.forEach((btn) =>
  btn.addEventListener("click", openModalReturned)
);
btnCloseModalReturned.addEventListener("click", closeModalReturned);
overlayReturned.addEventListener("click", closeModalReturned);

document.addEventListener("keydown", function (e) {
  if (e.key === "Escape" && !modalReturned.classList.contains("hidden")) {
    closeModalReturned();
  }
});

//Search Filter
(function (document) {
	"use strict";

	// 建立 LightTableFilter
	var LightTableFilter = (function (Arr) {
	  var _input;

	  // 資料輸入事件處理函數
	  function _onInputEvent(e) {
		_input = e.target;
		var tables = document.getElementsByClassName(
		  _input.getAttribute("data-table")
		);
		Arr.forEach.call(tables, function (table) {
		  Arr.forEach.call(table.tBodies, function (tbody) {
			Arr.forEach.call(tbody.rows, _filter);
		  });
		});
	  }

	  // 資料篩選函數，顯示包含關鍵字的列，其餘隱藏
	  function _filter(row) {
		var text = row.textContent.toLowerCase(),
		  val = _input.value.toLowerCase();
		row.style.display = text.indexOf(val) === -1 ? "none" : "table-row";
	  }

	  return {
		// 初始化函數
		init: function () {
		  var inputs =
			document.getElementsByClassName("light-table-filter");
		  Arr.forEach.call(inputs, function (input) {
			input.oninput = _onInputEvent;
		  });
		},
	  };
	})(Array.prototype);

	// 網頁載入完成後，啟動 LightTableFilter
	document.addEventListener("readystatechange", function () {
	  if (document.readyState === "complete") {
		LightTableFilter.init();
	  }
	});
  })(document);