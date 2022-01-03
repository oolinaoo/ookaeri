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
	refresh();
	/*
	var dataUrl =
	  "/okaeri/pack/listAllPack";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (var i = 0; i < data.length; i++) {
		  if(data[i].packReceived == null){
			  data[i].packReceived = ""; 
		  }
		  if(data[i].packTypeNo == 0){
			  data[i].packTypeNo = "包裹";
		  }else{
			  data[i].packTypeNo = "信件";
		  }
		  if(data[i].packState == 0){
			  data[i].packState = "已領取"
		  }else{
			  data[i].packState = "未領取"
		  }
		$("table tbody").append(
				"<tr><td class='packNo' name='packNo' contenteditable='false'>" + 
				data[i].packNo +
				"</td><td class='addrNo' name='addrNo'  contenteditable='false'>" +
				data[i].addrNo +
				"</td><td class='packArrived' name='packArrived'  contenteditable='false'>" +
				data[i].packArrived +
				"</td><td class='packReceived' name='packReceived'  contenteditable='false'>" +
				data[i].packReceived +
				"</td><td class='packLogistics' name='packLogistics'  contenteditable='false'>" +
				data[i].packLogistics +
				"</td><td class='packTypeNo' name='packTypeNo'  contenteditable='false'>" +
				data[i].packTypeNo +
				"</td><td class='packState' name='packState'  contenteditable='false'>" +
				data[i].packState +
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
	*/
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
      //接收後端資料到前端顯示
      var dataUrl =
    	  "/okaeri/pack/listAddrNo";
    	var xhr = new XMLHttpRequest();
    	xhr.open("GET", dataUrl, true);
    	xhr.send();
    	xhr.onload = function () {
    	  var data = JSON.parse(this.responseText);
    	  console.log(data);
    	  for (var i = 0; i < data.length; i++) {
    		  $("select.select").append(
    				  "<option value="+(i+1)+">"+data[i]+"</option>"
    		  )
    	  }
    	}
      
      
    });
    $(".btn_confirm").on("click", function () {
     console.log("進入函式");
   	 let r = confirm("確定新增？");
   	 if (r) {
   		 $("div.error_block").remove();
   		//將燈箱輸入的值放入陣列中 //住戶帳號、地址編號、繳費期限、繳費金額、繳費期數、收費者、繳費狀態
   	        let row_list = new Array(3);

   	        for (var i = 1; i <= 3; i++) {
   	            let item = $("#packform").find("div").eq(i).children("input").val();
   	            row_list[i] = item.trim();
   	            console.log("row_list[i]");
   	            console.log("i");
   	        }
   	        
   	      //正規表達式驗證
   	        let n = 0;
   	        let html_list = "";
   	        for (var i = 1; i <= 3; i++) {
   	            if(i == 1){
   	                let re = /^[0-9]{4}////[0-9]{1,2}////[0-9]{1,2}$/;
   	                if( re.test( row_list[i]) ){
   	                    n+=1;
   	                }else{
   	                    html_list += `
   	                        <div class="error_msg">包裹到達日：不可空白，請輸入日期</div>
   	                    `;
   	                }
   	            }else if(i == 2){
   	                let re = /^[(\u4E00-\u9FA5)]{1,5}$/;
   	                console.log( re.test( row_list[i]));
   	                if( re.test( row_list[i]) ){
   	                    n+=1;
   	                    
   	                }else{
   	                    html_list += `
   	                        <div class="error_msg">物流商：不可空白，最多五個字</div>
   	                    `;
   	                }
   	            }else if(i == 3){
   	                let re = /^[0-1]{1}$/;
   	                if( re.test( row_list[i]) ){
   	                    n+=1;
   	                }else{
   	                    html_list += `
   	                        <div class="error_msg">包裹種類編號：不可空白，需輸入數字0或1</div>
   	                    `;
   	                }
   	            }else{
   	            	console.log("我沒進入到正則表達");
   	            }
   	        }
   	        let error_block = `<div class="error_block">${html_list}</div>`;

   	        //如果以上有任何一個驗證錯誤，n就不等於3
   	        if(n != 3){
   	            //將錯誤訊息加入燈箱中
   	            $("#packform").prepend(error_block);
   	        }else{
   	        	//成功的話傳送資料和關閉燈箱
   	        	//傳送新增資料到Servlet
   	        	 $.ajax({
					    	url:"/okaeri/pack/add",
					    	dataType: "json",
					    	type: "POST",
					    	async: true,
					    	data: {
					    		"addrNo" : $(".select").val(),
								"packArrived" : $("#packArrived").val(),
								"packLogistics" :$("#packLogistics").val(),
								"packTypeNo" : $("#packTypeNo").val()  //傳給API的參數
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
});
	
//將修改的資料傳送到Servlet
$("table").on("click",".fa-save", function() {
	console.log("AAA");
	var tr = $(this).closest("tr");
	var td = tr.find("td:eq(5)").text();
	var td1 = tr.find("td:eq(5)").text();
	if(td == "包裹"){
		td = 0;	
	}else{
		td = 1;
	}
	if(td1 == "已領取"){
		td1 = 0;	
	}else{
		td1 = 1;
	}
	
	$.ajax({
		
		url:"/okaeri/pack/update",
		dataType: "json",
		type: "POST",
		async: true,
		data: {
			//傳給API的參數
			"packNo" : tr.find("td:eq(0)").text(),
			"addrNo" : tr.find("td:eq(1)").text(),
			"packArrived" :tr.find("td:eq(2)").text(),
			"packReceived" : tr.find("td:eq(3)").text(),
			"packLogistics" : tr.find("td:eq(4)").text(), 
			"packTypeNo" : td, 
			"packState" : td1 
			
				
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
		url:"http://localhost:8081/Project/PackServlet.do?action=delete",
		dataType: "json",
		type: "GET",
		async: true,
		data: {
			//傳給API的參數
			"packNo" : tr.find("td:eq(0)").text()
		},
		success: function(data){
				//成功的話，執行此區塊
		        alert("success");
		    	},
		});
	});
/*請求頁面資料*/	
function refresh() {
  $("table tbody").empty();
  $("div.page").remove();
  console.log("重整");
  var dataUrl =
	  "/okaeri/pack/listAllPack";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (var i = 0; i < data.length; i++) {
		  if(data[i].packReceived == null){
			  data[i].packReceived = ""; 
		  }
		  if(data[i].packTypeNo == 0){
			  data[i].packTypeNo = "包裹";
		  }else{
			  data[i].packTypeNo = "信件";
		  }
		  if(data[i].packState == 0){
			  data[i].packState = "未領取"
		  }else{
			  data[i].packState = "已領取"
		  }
		$("table tbody").append(
				"<tr><td class='packNo' name='packNo' contenteditable='false'>" + 
				data[i].packNo +
				"</td><td class='addrNo' name='addrNo'  contenteditable='false'>" +
				data[i].addrNo +
				"</td><td class='packArrived' name='packArrived'  contenteditable='false'>" +
				data[i].packArrived +
				"</td><td class='packReceived' name='packReceived'  contenteditable='false'>" +
				data[i].packReceived +
				"</td><td class='packLogistics' name='packLogistics'  contenteditable='false'>" +
				data[i].packLogistics +
				"</td><td class='packTypeNo' name='packTypeNo'  contenteditable='false'>" +
				data[i].packTypeNo +
				"</td><td class='packState' name='packState'  contenteditable='false'>" +
				data[i].packState +
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
}
//************ 搜尋框 ************
/*function myFunction(){
  let input, filter, tr, td, txtValue, flag;
  input = document.getElementById("myInput");
  input = input.value.trim();
  //console.log(input);

  //如果沒有輸入東西，就重新呼叫分頁函式，回到原本載入所有資料的狀態
  if(input == ""){
    $("div.page").remove();
    paging();
    //$("#nav a.active").click();
    return; //結束程式
  }

  //如果有輸入東西，就把分頁移除
  $("div.page").remove();
  
  filter = input.toUpperCase(); //如有英文字則全部轉為大寫，如為中文字就會維持原本的中文字
  tr = document.getElementsByTagName("tr");
  for(let i = 1; i < tr.length; i++){
    flag = 0;
    td = tr[i].getElementsByTagName("td");

    // td.length要 -1 是因為最後一個td都是編輯按鈕
    for(let j = 0; j < td.length-1; j++){
      
      txtValue = td[j].textContent || td[j].innerText;
      //console.log(txtValue);  

      // 如果大於 -1，表示該 td標籤中的文字 有包含 關鍵字
      if(txtValue.toUpperCase().indexOf(filter) > -1){ 
        flag = 1; 
        break;
      }
    }
    // 若 flag == 1 表示該tr標籤中有包含關鍵字，就顯示該tr
    if(flag == 1){
      tr[i].style.display = "table-row";
      tr[i].style.opacity = 1;
    }else{
      tr[i].style.display = "none";
      tr[i].style.opacity = 0;
    }

  }
  
}*/