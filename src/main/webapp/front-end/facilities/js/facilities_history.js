/**********************************
 ****                          ****
 ****                          ****
 ****    FACILITIES HISTORY    ****
 ****                          ****
 ****                          ****
 **********************************/


  //========  Pagination 分頁變色 ========
  // a鏈接點擊變色，再點其他回復原色
   function changCss(obj) {
     var arr = document.getElementsByTagName("a");
     for (var i = 0; i < arr.length; i++) {
       if (obj == arr[i]) {
         //當前頁樣式
         obj.style.backgroundColor = "#B5495B";
         obj.style.color = "#ffffff";
       } else {
         arr[i].style.color = "";
         arr[i].style.backgroundColor = "";
       }
     }
   }
   

   
// ======== Ajax Data ========
$(function () {

  function listMemResHis() {
    $.ajax({
      url: "/okaeri/fachist/fachistViewMemacct",
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
                              <td>${item.histDate}</td>
                              <td>${item.facName}</td>
                              <td>${item.histTime}</td>
                              <td>${item.histAmount}</td>
                            </tr>
                            `;
          $("tbody#history_tbody").prepend(historyList);
        });
        


        //========  Pagination  ========
        var $table = $("table");
        var currentPage = 0; //當前頁默認值為0
        var pageSize = 8; //每一頁顯示的數目
        $table.bind("paging", function () {
          $table
            .find("tbody tr")
            .hide()
             .slice(currentPage * pageSize, (currentPage + 1) * pageSize)
             .show();
         });
         var sumRows = $table.find("tbody tr").length;
         var sumPages = Math.ceil(sumRows / pageSize); //總頁數
      
         var $pager = $(
           '<div class="page" style="width: 80%; margin: 0 auto;"></div>'
         ); //新建div，放入a標簽,顯示底部分頁碼
         for (var pageIndex = 0; pageIndex < sumPages; pageIndex++) {
          $(
             '<a href="#" id="pageStyle"  onclick="changCss(this)"><span>' +
               (pageIndex + 1) +
               "</span></a>"
           )
             .bind("click", { newPage: pageIndex }, function (event) {
               currentPage = event.data["newPage"];
               $table.trigger("paging");
               //觸發分頁函數
             })
             .appendTo($pager);
           $pager.append(" ");
         }
         $pager.insertAfter($table);
         $table.trigger("paging");
  
           //默認第一頁的a標簽效果
         var $pagess = $("#pageStyle");
         $pagess[0].style.backgroundColor = "#B5495B";
         $pagess[0].style.color = "#ffffff";
  
         $("a#pageStyle").on("click", function () {
           $("html, body").animate(
             { scrollTop: $("h1.fac_title").offset().top },
             { duration: 500, easing: "swing" }
           );
         });

      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }    

    });
    
  }

  listMemResHis();

});
