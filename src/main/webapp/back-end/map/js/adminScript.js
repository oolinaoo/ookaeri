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

//json from servlet listall
$(function () {
	let url =
	  "/okaeri/rule/listAll";
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (let i = 0; i < data.length; i++) {
		$("table tbody").append(
				"<tr><td class='id' contenteditable='false'>" + 
				data[i].ruleNo +
				"</td><td class='title' contenteditable='false'>" +
				data[i].ruleTitle +
				"</td><td class='content' contenteditable='false'>" + 
				data[i].ruleContent +
				"</td><td class='time' contenteditable='false'>" +
				data[i].rulePosttime +
				"</td><td class='admin' contenteditable='false'>" +
				data[i].adminAcct +
				"</td><td class='finalActionsCol'><i class='fa fa-minus-circle' aria-hidden='true'></i> <i class='fa fa-edit' aria-hidden='true'></i> </td></tr>"
		);
	  }
	}
});

//edit Buttons
$(document).ready(function() {

  $("table").on("click", ".fa-minus-circle", function() {
    if (prompt("確認刪除，請輸入yes") == "yes") {
      $(this).closest('tr').remove();
    } else {}
  });

  $("table").on("click", ".fa-edit, .fa-save", function() {
    let thisRow = $(this).parent().siblings();
    let editOn = $(this).hasClass("editMode");

    $('td:last-child').attr('contenteditable', 'false');
    $('td:last-child').css('background-color', 'transparent');

    if (editOn == false) {
      $(thisRow).attr('contenteditable', 'true');
      $('td:eq(0)').attr('contenteditable', 'false');
      $('td:eq(4)').attr('contenteditable', 'false');
      $(thisRow).css('background-color', '#EBECF0');
      $(this).removeClass("fa-edit");
      $(this).addClass("fa-save editMode");
    } else if (editOn == true) {
      $(thisRow).attr('contenteditable', 'false');
      $(thisRow).css('background-color', 'transparent');
      $(this).removeClass("fa-save editMode");
      $(this).addClass("fa-edit");
      $(function() {
    		const ruleId = $(this).find(".id").html();
    		const title = $(this).find(".title").html();
    		const content = $(this).find(".content").html();
    		const time = $(this).find(".time").html();
    		const admin = $(".menu span span span").html();
    		let form_data = {
    			"ruleNo" : ruleId,
    			"ruleTitle" : title,
    			"ruleContent" : content,
    			"rulePosttime" : time,
    			"adminAcct" : admin
    		};
    		let xhr = new XMLHttpRequest();
    		xhr.onreadystatechange = function() {
    			if (this.readyState == 4 && this.status == 200) {
    				console.log(xhr);
    			}
    		};
    		xhr.open("POST", "/okaeri/rule/update"); //post 告知後端
    		xhr.setRequestHeader("Content-type", "application/json"); //告訴後端是用 JSON 格式
    		let data = JSON.stringify(form_data); //將物件資料轉成字串
    		console.log(data);
    		xhr.send(data); //送出字串
    	})
    }
  });
});

//// LightBox
//// returned cases //
//const modalReturned = document.querySelector(".modal-returned");
//const overlayReturned = document.querySelector(".overlay-returned");
//const btnCloseModalReturned = document.querySelector(
//  ".btn--close-modal-returned"
//);
//const btnsOpenModalReturned = document.querySelectorAll(".newPost-button");
//
//// returned cases //
//const openModalReturned = function (e) {
//  e.preventDefault();
//  modalReturned.classList.remove("hidden");
//  overlayReturned.classList.remove("hidden");
//};
//
//const closeModalReturned = function () {
//  modalReturned.classList.add("hidden");
//  overlayReturned.classList.add("hidden");
//};
//
//btnsOpenModalReturned.forEach((btn) =>
//  btn.addEventListener("click", openModalReturned)
//);
//btnCloseModalReturned.addEventListener("click", closeModalReturned);
//overlayReturned.addEventListener("click", closeModalReturned);
//
//document.addEventListener("keydown", function (e) {
//  if (e.key === "Escape" && !modalReturned.classList.contains("hidden")) {
//    closeModalReturned();
//  }
//});

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