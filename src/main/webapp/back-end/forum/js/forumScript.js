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

// TABLE table TABLE table TABLE table TABLE table
// ARTICLE article ARTICLE article ARTICLE article ARTICLE article
// json from servlet
// $(function () {
//   var dataUrl = "http://localhost:8080/##################";
//   var xhr = new XMLHttpRequest();
//   xhr.open("GET", dataUrl, true);
//   xhr.send();
//   xhr.onload = function () {
//     var data = JSON.parse(this.responseText);
//     console.log(data);
//     for (var i = 0; i < data.length; i++) {
//       $("table tbody").append(
//         "<tr><td class='id'>" +
//           data[i].forArtNo +
//           "</td><td class='type'>" +
//           data[i].forTypeNo +
//           "</td><td class='title'>" +
//           data[i].forArtTitle +
//           "</td><td class='content'>" +
//           data[i].forArtContent +
//           "</td><td class='mem'>" +
//           data[i].memAcct +
//           "</td><td class='posttime'>" +
//           data[i].forArtPosttime +
//           "</td><td class='edittime'>" +
//           data[i].forArtEdittime +
//           "</td><td class='state'>" +
//           data[i].ArtStateNo +
//           "</td><td class='whole'>" +
//           "留言內容" +
//           "</td></tr>"
//       );
//     }

//     var table = $("table");
//     var currentPage = 0; // 當前頁默認值為0
//     var pageSize = 8; // 每一頁顯示的數目
//     table.bind("paging", function () {
//       table
//         .find("tbody tr")
//         .hide()
//         .slice(currentPage * pageSize, (currentPage + 1) * pageSize)
//         .show();
//     });
//     var sumRows = table.find("tbody tr").length;
//     var sumPages = Math.ceil(sumRows / pageSize); // 總頁數

//     var pager = $('<div class="page"></div>'); // 新建div，放入a標簽,顯示底部分頁碼
//     for (var pageIndex = 0; pageIndex < sumPages; pageIndex++) {
//       $(
//         '<a href="#" id="pageStyle" onclick="changCss(this)"><span>' +
//           (pageIndex + 1) +
//           "</span></a>"
//       )
//         .bind("click", { newPage: pageIndex }, function (event) {
//           currentPage = event.data["newPage"];
//           table.trigger("paging");
//           // 觸發分頁函數
//         })
//         .appendTo(pager);
//       pager.append(" ");
//     }
//     pager.insertAfter(table);
//     table.trigger("paging");

//     // 默認第一頁的a標簽效果
//     var pagess = $("#pageStyle");
//     pagess[0].style.backgroundColor = "#B5495B";
//     pagess[0].style.color = "#ffffff";
//   };
// });
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
// ARTICLE article ARTICLE article ARTICLE article ARTICLE article

// REPORT report REPORT report REPORT report REPORT report REPORT report
// json from servlet
// $(function () {
//   var dataUrl = "http://localhost:8080/##################";
//   var xhr = new XMLHttpRequest();
//   xhr.open("GET", dataUrl, true);
//   xhr.send();
//   xhr.onload = function () {
//     var data = JSON.parse(this.responseText);
//     console.log(data);
//     for (var i = 0; i < data.length; i++) {
//       $("table tbody").append(
//         "<tr><td class='id'>" +
//           data[i].forReptNo +
//           "</td><td class='number art'>" +
//           data[i].forArtNo +
//           "</td><td class='number msg'>" +
//           data[i].forMsgNo +
//           "</td><td class='mem'>" +
//           data[i].memAcct +
//           "</td><td class='admin'>" +
//           data[i].adminAcct +
//           "</td><td class='repttime'>" +
//           data[i].forReptTime +
//           "</td><td class='checktime'>" +
//           data[i].forReptChecktime +
//           "</td><td class='state'>" +
//           data[i].forReptState +
//           "</td><td class='rept whole'>" +
//           "檢舉原因" +
//           "</td></tr>"
//       );

//       // 判斷是文章還是留言被檢舉？
//       // 若文章編號是空的，把留言內容加上彈窗效果(whole)，把檢舉原因加上留言內容的class(msg/art)、用來執行對應的api
//       if (data[i].forArtNo == null) {
//         $("tbody").children("tr").eq(i).children("td.msg").addClass("whole");
//         $("tbody").children("tr").eq(i).children("td.art").removeClass("art");
//         $("tbody").children("tr").eq(i).children("td.rept").addClass("msg");
//       } else if (data[i].forMsgNo == null) {
//         $("tbody").children("tr").eq(i).children("td.art").addClass("whole");
//         $("tbody").children("tr").eq(i).children("td.msg").removeClass("msg");
//         $("tbody").children("tr").eq(i).children("td.rept").addClass("art");
//       }
//
//     }

//     var table = $("table");
//     var currentPage = 0; // 當前頁默認值為0
//     var pageSize = 8; // 每一頁顯示的數目
//     table.bind("paging", function () {
//       table
//         .find("tbody tr")
//         .hide()
//         .slice(currentPage * pageSize, (currentPage + 1) * pageSize)
//         .show();
//     });
//     var sumRows = table.find("tbody tr").length;
//     var sumPages = Math.ceil(sumRows / pageSize); // 總頁數

//     var pager = $('<div class="page"></div>'); // 新建div，放入a標簽,顯示底部分頁碼
//     for (var pageIndex = 0; pageIndex < sumPages; pageIndex++) {
//       $(
//         '<a href="#" id="pageStyle" onclick="changCss(this)"><span>' +
//           (pageIndex + 1) +
//           "</span></a>"
//       )
//         .bind("click", { newPage: pageIndex }, function (event) {
//           currentPage = event.data["newPage"];
//           table.trigger("paging");
//           // 觸發分頁函數
//         })
//         .appendTo(pager);
//       pager.append(" ");
//     }
//     pager.insertAfter(table);
//     table.trigger("paging");

//     // 默認第一頁的a標簽效果
//     var pagess = $("#pageStyle");
//     pagess[0].style.backgroundColor = "#B5495B";
//     pagess[0].style.color = "#ffffff";
//   };
// });
// REPORT report REPORT report REPORT report REPORT report REPORT report

//edit Buttons
$(document).ready(function () {
  $("table").on("click", ".fa-minus-circle", function () {
    if (
      prompt(
        "Are You Sure You Want to Delete this Row? Type 'yes' to Confirm this"
      ) == "yes"
    ) {
      $(this).closest("tr").remove();
    } else {
    }
  });

  $("table").on("click", ".fa-edit, .fa-save", function () {
    var thisRow = $(this).parent().siblings();
    var editOn = $(this).hasClass("editMode");

    $("td:last-child").attr("contenteditable", "false");
    $("td:last-child").css("background-color", "transparent");

    if (editOn == false) {
      $(thisRow).attr("contenteditable", "true");
      $(thisRow).css("background-color", "#EBECF0");
      $(this).removeClass("fa-edit");
      $(this).addClass("fa-save editMode");
    } else if (editOn == true) {
      $(thisRow).attr("contenteditable", "false");
      $(thisRow).css("background-color", "transparent");
      $(this).removeClass("fa-save editMode");
      $(this).addClass("fa-edit");
    }
  });

  $("th", this).mouseout(function () {
    $(this).attr("contenteditable", "false");
  });
});

// FULL ARTICLE & MESSAGE & REPORT  full article & message & report
// ARTICLE article ARTICLE article ARTICLE article ARTICLE article ARTICLE article ARTICLE article ARTICLE article
// $("table.tableContents").on("click", "td.whole", function () {
//   // 若要秀出文章和它全部的留言，看是要用兩個 api 還是一個
//   // 感覺直接用兩個會比較簡單
//   var dataUrl = "http://localhost:8080/##################";
//   var xhr = new XMLHttpRequest();
//   xhr.open("GET", dataUrl, true);
//   xhr.send();
//   xhr.onload = function () {
//     var data = JSON.parse(this.responseText);
//     console.log(data);
//     // article
//     $("div.modal-container h2").append(data.forArtTitle);
//     $("div.modal-container div.article_content").append(data.forArtContent);

//     // message  如果要同一個 api 就可以查文章和它的相關留言，感覺要 Join 或 View 之類的？
//     var msgList = "";
//     for (var i = 0; i < data.length; i++) {
//       msgList +=
//         "<div class='article_message'>" +
//         "<div>" +
//         data.memAcct +
//         "</div><br />" +
//         data.forMsgContent +
//         "<div style='text-align: right; font-size: 0.8em'>" +
//         data.forMsgEdittime +
//         "</div>" +
//         "<div style='text-align: right; font-size: 0.8em'>檢舉</div>" +
//         "</div>";
//     }
//     $("div.modal-container h3").after(msgList);
//   };
// });
// ARTICLE article ARTICLE article ARTICLE article ARTICLE article ARTICLE article ARTICLE article ARTICLE article




// REPORT report REPORT report REPORT report REPORT report REPORT report REPORT report REPORT report REPORT report
// 先秀出被檢舉的該篇文章或流言就好？
// 全部列出好像有點大費周章、要篩出相關聯的列出又很不容易的感覺

// 若是文章被檢舉，要看看如何傳入文章編號、再把文章內容抓出來呈現
// $("table.tableReports").on("click", "td.art", function () {
//   var dataUrl = "http://localhost:8080/##################";
//   var xhr = new XMLHttpRequest();
//   xhr.open("GET", dataUrl, true);
//   xhr.send();
//   xhr.onload = function () {
//     var data = JSON.parse(this.responseText);
//     console.log(data);

//     // 檢舉原因
//     $("div.report_content").append(data.forReptContent);

//     // 被檢舉文章內容
//     var reptArt = `<h3>${data.forArtTitle}</h3>
//                      <div class="article_content">
//                        <div>${data.memAcct}</div>
//                        <br/>
//                        ${data.forArtContent}
//                        <div style="text-align: right; font-size: 0.8em">${data.forArtEdittime}</div>
//                      </div>`;

//     $("div.confirm_report").before(reptArt);
//   };
// });

// 若是留言被檢舉，要看看如何傳入留言編號、再把留言內容抓出來呈現
// $("table.tableReports").on("click", "td.msg", function () {
//   var dataUrl = "http://localhost:8080/##################";
//   var xhr = new XMLHttpRequest();
//   xhr.open("GET", dataUrl, true);
//   xhr.send();
//   xhr.onload = function () {
//     var data = JSON.parse(this.responseText);
//     console.log(data);

//     // 檢舉原因
//     $("div.report_content").append(data.forReptContent);

//     var reptMsg = `<h3>留言</h3>
//                    <div class="article_message">
//                      <div>${data.memAcct}</div>
//                      <br/>
//                      ${data.forMsgContent}
//                      <div style="text-align: right; font-size: 0.8em">${data.forMsgEdittime}</div>
//                    </div>`;

//     $("div.confirm_report").before(reptMsg);
//   };
// });
// REPORT report REPORT report REPORT report REPORT report REPORT report REPORT report REPORT report REPORT report


// LightBox
// returned cases //
const modalReturned = document.querySelector(".modal-returned");
const overlayReturned = document.querySelector(".overlay-returned");
const btnCloseModalReturned = document.querySelector(
  ".btn--close-modal-returned"
);
// const btnsOpenModalReturned = document.querySelectorAll(".newPost-button");
const btnsOpenModalReturned = document.querySelectorAll(".whole");

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
        var inputs = document.getElementsByClassName("light-table-filter");
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



// REPORT CONFIRM OR CANCEL; report confirm or cancel; REPORT CONFIRM OR CANCEL; report confirm or cancel; REPORT CONFIRM OR CANCEL; report confirm or cancel;
// 檢舉審核的按鈕
$("div.modal-container").on("click", "span.confirm", function(){
  // 確認文章或是留言的檢舉狀態



});

$("div.modal-container").on("click", "span.cancel", function(){
  // 取消文章或是留言的檢舉狀態



});