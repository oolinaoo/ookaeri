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

//// LightBox
//// returned cases //
//const modalReturned = document.querySelector(".modal-returned");
//const overlayReturned = document.querySelector(".overlay-returned");
//const btnCloseModalReturned = document.querySelector(
//  ".btn--close-modal-returned"
//);
//// const btnsOpenModalReturned = document.querySelectorAll(".newPost-button");
//const btnsOpenModalReturned = document.querySelectorAll(".whole");
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