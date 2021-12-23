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

//json from servlet
$(function () {
	var dataUrl =
	  "/okaeri/payment/listAllPayment";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl, true);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (var i = 0; i < data.length; i++) {
		if(data[i].payWay == null ){
			data[i].payWay = "未繳交";
		}  
		if(data[i].payDate == null ){
			data[i].payDate = "";
		}
		if(data[i].payState == 0 ){
			data[i].payState = "已繳費";
		}else if(data[i].payState == 1){
			data[i].payState = "未繳費";
		} 
		$("table tbody").append(
				"<tr><td class='payNo' contenteditable='false'>" + data[i].payNo +"</td>"
				+"<td class='memAcct' contenteditable='false'>" + data[i].memAcct +"</td>"
				+"<td class='addrNo' contenteditable='false'>" + data[i].addrNo +"</td>"
				+"<td class='payDate' contenteditable='false'>" + data[i].payDate +"</td>" 
				+"<td class='payDeadline' contenteditable='false'>" + data[i].payDeadline +"</td>" 
				+"<td class='payAmount' contenteditable='false'>" + data[i].payAmount + "</td>"
				+"<td class='payRecentCall' contenteditable='false'>" + data[i].payRecentCall +"</td>"
				+"<td class='payPeriod' contenteditable='false'>" + data[i].payPeriod +"</td>"
				+"<td class='payWay' contenteditable='false'>" + data[i].payWay +"</td>"
				+"<td class='adminAcct' contenteditable='false'>" + data[i].adminAcct +"</td>"
				+"<td class='payState' contenteditable='false'>" + data[i].payState +"</td>"
				+"<td class='finalActionsCol'><i class='fa fa-minus-circle' aria-hidden='true'></i> <i class='fa fa-edit' aria-hidden='true'></i> </td>" 
				+"</tr>"
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
//ajax 寫法
//$.ajax({
//	url:"http://localhost:8080/okaeri-news/NewsControllerServlet",
//	type:"GET",
//	data:{"command": "LIST"},
//	dataType:"json",
//	success: function(data) {
//		console.log(data);
//	}
//error: 
//});

//edit Buttons
$(document).ready(function() {

  $("table").on("click", ".fa-minus-circle", function() {
    if (prompt("Are You Sure You Want to Delete this Row? Type 'yes' to Confirm this") == "yes") {
      $(this).closest('tr').remove();
    } else {}
  });

  $("table").on("click", ".fa-edit, .fa-save", function() {
    var thisRow = $(this).parent().siblings();
    var editOn = $(this).hasClass("editMode");

    $('td:last-child').attr('contenteditable', 'false');
    $('td:last-child').css('background-color', 'transparent');

    if (editOn == false) {
      $(thisRow).attr('contenteditable', 'true');
      $(thisRow).css('background-color', '#EBECF0');
      $(this).removeClass("fa-edit");
      $(this).addClass("fa-save editMode");
    } else if (editOn == true) {
      $(thisRow).attr('contenteditable', 'false');
      $(thisRow).css('background-color', 'transparent');
      $(this).removeClass("fa-save editMode");
      $(this).addClass("fa-edit");
    }
  });

  $('th', this).mouseout(function() {
    $(this).attr("contenteditable", "false");
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
//按新增顯示視窗
$(function () {
    $(".newPost-button").on("click", function () {
      $("div.hidden_form").css("display", "block");
      $("div.hidden_form").fadeIn;
    });
    $(".btn_confirm").on("click", function () {
    	 console.log("進入函式");
    	 let r = confirm("確定新增？");
    	 if (r) {
    		 $("div.error_block").remove();
    		//將燈箱輸入的值放入陣列中 //住戶帳號、地址編號、繳費期限、繳費金額、繳費期數、收費者、繳費狀態
    	        let row_list = new Array(6);

    	        for (var i = 0; i <= 5; i++) {
    	            let item = $("#paymentform").find("div").eq(i).children("input").val();
    	            row_list[i] = item.trim();
    	            console.log("row_list[i]");
    	            console.log("i");
    	        }
    	        
    	      //正規表達式驗證
    	        let n = 0;
    	        let html_list = "";
    	        for (var i = 0; i <= 5; i++) {
    	            if(i == 0){
    	                let re = /^[(a-zA-Z0-9)]{1,10}$/;
    	                if( re.test( row_list[i]) ){
    	                    n+=1;
    	                }else{
    	                    html_list += `
    	                        <div class="error_msg">住戶帳號：不可空白，需輸入英文和數字</div>
    	                    `;
    	                }
    	            }else if(i == 1){
    	                let re = /^[1-75]{1,2}$/;
    	                if( re.test( row_list[i]) ){
    	                    n+=1;
    	                }else{
    	                    html_list += `
    	                        <div class="error_msg">地址編號：不可空白，需輸入數字1~75</div>
    	                    `;
    	                }
    	            }else if(i == 2){
    	                let re = /^[0-9]{4}////[0-9]{1,2}////[0-9]{1,2}$/;
    	                if( re.test( row_list[i]) ){
    	                    n+=1;
    	                }else{
    	                    html_list += `
    	                        <div class="error_msg">繳費期限：不可空白，需輸入日期</div>
    	                    `;
    	                }
    	            }else if(i == 3){
    	                let re = /^[0-9]{4}$/;
    	                if( re.test( row_list[i]) ){
    	                    n+=1;
    	                }else{
    	                    html_list += `
    	                        <div class="error_msg">繳費金額：不可空白，需輸入金額</div>
    	                    `;
    	                }
    	            }else if(i == 4){
    	                let re = /^[0-9]{6}$/;
    	                if( re.test( row_list[i]) ){
    	                    n+=1;
    	                }else{
    	                    html_list += `
    	                        <div class="error_msg">繳費期數：不可空白，需輸入年份+月份 例如:202112</div>
    	                    `;
    	                }
    	            }else if(i == 5){
    	                let re = /^[(a-zA-Z0-9)]{1,10}$/;
    	                if( re.test( row_list[i]) ){
    	                    n+=1;
    	                }else{
    	                    html_list += `
    	                        <div class="error_msg">收費者：不可空白</div>
    	                    `;
    	                }
    	            }else{
    	            	console.log("我沒進入到正則表達");
    	            }
    	        }
    	        let error_block = `<div class="error_block">${html_list}</div>`;

    	        //如果以上有任何一個驗證錯誤，n就不等於3
    	        if(n != 6){
    	            //將錯誤訊息加入燈箱中
    	            $("#paymentform").prepend(error_block);
    	        }else{
    	        	//成功的話傳送資料和關閉燈箱
    	        	//傳送新增資料到Servlet
    	        	 $.ajax({
					    	url:"/okaeri/payment/add",
					    	dataType: "json",
					    	type: "POST",
					    	async: true,
					    	data: {
					    		"memAcct" : $("#memAcct").val(),
								"addrNo" : $("#addrNo").val(),
								"payDeadline" : $("#payDeadline").val(),
								"payAmount" : $("#payAmount").val(),
								"payPeriod" : $("#payPeriod").val(),
								"adminAcct" : $("#adminAcct").val()   //傳給API的參數
					    	},
					    	success: function(data){
					    		//成功的話，執行此區塊
					                alert("success");
					    	},complete: function(xhr){      // request 完成之後執行(在 success / error 事件之後執行)
    							refresh();
  							 }
					  });
    	        	 $("div.hidden_form").css("display", "none");
    	        	 $("div.hidden_form").fadeOut;
    	        }
    		 
    		 
    		 
    	 }
    
    });
    $(".btn_cancel").on("click", function () {
      $("div.hidden_form").css("display", "none");
      $("div.hidden_form").fadeOut;
    });
    
    console.log("111");
});


//將修改的資料傳送到Servlet
$("table").on("click",".fa-save", function() {
	var tr = $(this).closest("tr");
	var td = tr.find("td:eq(10)").text();
	if(td == "已繳費"){
		td = 0;
	}else{
		td= 1;
	}
	$.ajax({
		url:"/okaeri/payment/update",
		dataType: "json",
		type: "POST",
		async: true,
		data: {
			//傳給API的參數
			"payNo" : tr.find("td:eq(0)").text(),
			"memAcct" : tr.find("td:eq(1)").text(),
			"addrNo" :tr.find("td:eq(2)").text(),
			"payDate" : tr.find("td:eq(3)").text(),
			"payDeadline" : tr.find("td:eq(4)").text(), 
			"payAmount" : tr.find("td:eq(5)").text(), 
			"payRecentCall" : tr.find("td:eq(6)").text(),
			"payPeriod" : tr.find("td:eq(7)").text(),
			"payWay" : tr.find("td:eq(8)").text(),
			"adminAcct" : tr.find("td:eq(9)").text(),
			"payState" : td
			
				
		},
		success: function(data){
				//成功的話，執行此區塊
		        alert("success");
		    	},
		});
		
	});
//按下刪除刪除資料.fa-minus-circle
$("table").on("click",".fa-minus-circle", function() {
	var tr = $(this).closest("tr");
	$.ajax({
		url:"http://localhost:8081/Project/PayServlet?action=delete",
		dataType: "json",
		type: "GET",
		async: true,
		data: {
			//傳給API的參數
			"payNo" : tr.find("td:eq(0)").text()
		},
		success: function(data){
				//成功的話，執行此區塊
		        alert("success");
		    	},
		});
	});
$(function () {
	var dataUrl =
	  "/okaeri/payment/init";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl, true);
	xhr.send();
	xhr.onload = function () {
		console.log("請求排程器");
	  }
});
/*請求頁面資料*/	
function refresh() {
  $("table tbody").empty();
  $("div.page").remove();
  console.log("重整");
  var dataUrl =
	  "/okaeri/payment/listAllPayment";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl, true);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (var i = 0; i < data.length; i++) {
		if(data[i].payWay == null ){
			data[i].payWay = "未繳交";
		}  
		if(data[i].payDate == null ){
			data[i].payDate = "";
		}
		if(data[i].payState == 0 ){
			data[i].payState = "未繳費";
		}else if(data[i].payState == 1){
			data[i].payState = "已繳費";
		} 
		$("table tbody").append(
				"<tr><td class='payNo' contenteditable='false'>" + data[i].payNo +"</td>"
				+"<td class='memAcct' contenteditable='false'>" + data[i].memAcct +"</td>"
				+"<td class='addrNo' contenteditable='false'>" + data[i].addrNo +"</td>"
				+"<td class='payDate' contenteditable='false'>" + data[i].payDate +"</td>" 
				+"<td class='payDeadline' contenteditable='false'>" + data[i].payDeadline +"</td>" 
				+"<td class='payAmount' contenteditable='false'>" + data[i].payAmount + "</td>"
				+"<td class='payRecentCall' contenteditable='false'>" + data[i].payRecentCall +"</td>"
				+"<td class='payPeriod' contenteditable='false'>" + data[i].payPeriod +"</td>"
				+"<td class='payWay' contenteditable='false'>" + data[i].payWay +"</td>"
				+"<td class='adminAcct' contenteditable='false'>" + data[i].adminAcct +"</td>"
				+"<td class='payState' contenteditable='false'>" + data[i].payState +"</td>"
				+"<td class='finalActionsCol'><i class='fa fa-minus-circle' aria-hidden='true'></i> <i class='fa fa-edit' aria-hidden='true'></i> </td>" 
				+"</tr>"
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
}