// back-end -> facilities history
$(function () {
  //json from servlet

  function listAllReservedHistory() {
    $.ajax({
      url: "/okaeri/fachist/fachistView",
      type: "POST",
      dataType: "json",
      headers:{
        "Content-Type": "application/json"
      },
      success: function (data) {
        $("tbody#history_tbody").empty();

        var historyList = "";
        $.each(data, function (index, item) {
          historyList = `
                             <tr>
                               <td class='histNo'>${item.histNo}</td>
                               <td class='facName' contenteditable='false'>${item.facName}</td>
                               <td class='memAccout' contenteditable='false'>${item.memAcct}</td>
                               <td class='memAccout' contenteditable='false'>${item.addrBuild}-${item.addrFloor}-${item.addrRoom}</td>
                               <td class='histDate' contenteditable='false'>${item.histDate}</td>
                               <td class='histTime' contenteditable='false'>${item.histTime}</td>
                               <td class='histAmount' contenteditable='false'>${item.histAmount}</td>
                             </tr>
                             `;
          $("tbody#history_tbody").prepend(historyList);
        });

        // 呼叫 編輯按鈕和分頁
        $("body").append("<script src='./js/pagination.js' type='text/javascript'></script>");

      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      },
      complete: function (xhr) {
        // $("body").append("<script src='./js/edit_button.js' type='text/javascript'></script>");

      },
    });
  }

  // 呼叫 抓資料庫的函式
  listAllReservedHistory();

  // $("input.searchTerm").on("input", function(){
  //   $("body").append("<script src='./js/pagination.js' type='text/javascript'></script>");
  // });

});
