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

//================分頁================//
function paging(){
  $('#addr_table').after('<div id="nav"></div>');
  var rowsShown = 8;
  var rowsTotal = $('#addr_table tbody tr').length;
  var numPages = Math.ceil(rowsTotal / rowsShown);

  if(numPages == 0){    //如果資料筆數為0筆，直接結束程式，因為資料筆數為0筆，就不會跑下面的for迴圈
      return;
    }else{
      for (let i = 0; i < numPages ; i++) {
        let pageNum = i + 1;
        $('#nav').append('<a href="###" id="pageStyle" rel="' + i + '">' + "<span>" + pageNum + "</span>" + '</a> ');
      }
  }

  $('#addr_table tbody tr').hide(); // display: none;
  $('#addr_table tbody tr').slice(0, rowsShown).show().css('opacity', '1');; // display: table-row; opacity: 1;
  $('#nav a:first').addClass('active');
  var $pagess = $("#pageStyle");
  $pagess[0].style.backgroundColor = "#B5495B";
  $pagess[0].style.color = "#ffffff";

  $('#nav a').bind('click', function () {

      $('#nav a').removeClass('active');
      $(this).addClass('active');
      $('#nav a').css('background-color', '').css('color', '');
      $(this).css('background-color', '#B5495B').css('color', '#ffffff');

      var currPage = $(this).attr('rel');
      var startItem = currPage * rowsShown;
      var endItem = startItem + rowsShown;
      $('#addr_table tbody tr').css('opacity', '0.0')
          .hide()  // display: none;
          .slice(startItem, endItem)
          .css('display', 'table-row').animate({ opacity: 1 }, 300); // display: table-row; opacity: 1;
  });

}

//************ 搜尋框 ************
function myFunction(){
  let input, filter, tr, td, txtValue, flag;
  input = document.getElementById("myInput");
  input = input.value.trim();
  //console.log(input);

  //如果沒有輸入東西，就重新呼叫分頁函式，回到原本載入所有資料的狀態
  if(input == ""){
    $("#nav").remove();
    paging();
    //$("#nav a.active").click();
    return; //結束程式
  }

  //如果有輸入東西，就把分頁移除
  $("#nav").remove();
  
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
  
}

// page
// function changCss(obj) {
// 	  var arr = document.getElementsByTagName("a");
// 	  for (var i = 0; i < arr.length; i++) {
// 		if (obj == arr[i]) {
// 		  // 當前頁樣式
// 		  obj.style.backgroundColor = "#B5495B";
// 		  obj.style.color = "#ffffff";
// 		} else {
// 		  arr[i].style.color = "";
// 		  arr[i].style.backgroundColor = "";
// 		}
// 	  }
// }


//Search Filter
// (function (document) {
// 	"use strict";

// 	// 建立 LightTableFilter
// 	var LightTableFilter = (function (Arr) {
// 	  var _input;

// 	  // 資料輸入事件處理函數
// 	  function _onInputEvent(e) {
// 		_input = e.target;
// 		var tables = document.getElementsByClassName(
// 		  _input.getAttribute("data-table")
// 		);
// 		Arr.forEach.call(tables, function (table) {
// 		  Arr.forEach.call(table.tBodies, function (tbody) {
// 			Arr.forEach.call(tbody.rows, _filter);
// 		  });
// 		});
// 	  }

// 	  // 資料篩選函數，顯示包含關鍵字的列，其餘隱藏
// 	  function _filter(row) {
// 		var text = row.textContent.toLowerCase(),
// 		  val = _input.value.toLowerCase();
// 		row.style.display = text.indexOf(val) === -1 ? "none" : "table-row";
// 	  }

// 	  return {
// 		// 初始化函數
// 		init: function () {
// 		  var inputs =
// 			document.getElementsByClassName("light-table-filter");
// 		  Arr.forEach.call(inputs, function (input) {
// 			input.oninput = _onInputEvent;
// 		  });
// 		},
// 	  };
// 	})(Array.prototype);

// 	// 網頁載入完成後，啟動 LightTableFilter
// 	document.addEventListener("readystatechange", function () {
// 	  if (document.readyState === "complete") {
// 		LightTableFilter.init();
// 	  }
// 	});
//   })(document);


