let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri

// let memAcct = "gina1";

//================載入所有資料================//
function init() {
  $.ajax({
    url: `${projectPath}/forumArticle/listByMem`,
    type: "POST",
    data: memData,
    dataType: "json",
    contentType : 'application/json;charset=UTF-8',  //一定要有這一行，不然會請求失敗
    success: function (data) {
      console.log(data);
      let list_html = '';
      $.each(data, function (index, item) {
          //文章狀態若為 2 表示 文章已被刪除
          if(item.artStateNo != 2){
            list_html += `
            <tr>
                <td id="artTitle">${item.forArtTitle}</td>
                <td hidden>${item.forArtNo}</td>
                <td id="artContent">${item.forArtContent}</td>
                <td>${item.type[0].forType}</td>
                <td>${item.forArtView}</td>
                <td>${item.forArtPosttime}</td>
                <td>${item.artStateNo == 0 ? '上架' : '下架'}</td>
                <td>
                    <button type="button" class="addr_btn_edit">編輯</button>
                    <div class="member_overlay" style="border: 1px solid red;"></div>
                </td>
                <td>
                    <button type="button" class="addr_btn_delete">刪除</button>
                </td>
            </tr>
          `;
          }
      });

      $("#mem-article-table tbody").append(list_html);
      paging(); //呼叫分頁函式
    },
    error: function (xhr) {
      console.log("error");
      console.log(xhr);
    }
  })
}

$(function(){  
    getMemAcct();
});

//================== 燈箱中的article區塊 ==================
var overlay = `
    <article>
        <button type="button" class="mem_btnModalClose">關閉</button>
        <form action="#" method="#" id="address_form">
            <table id="addr_modal_table">
                <tbody>
                    <tr>
                        <th>文章標題</th>
                        <td>
                            <input type="text" id="forArtTitle" value="">
                            <div class="errorMsg" id="errforArtTitle">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>文章類別</th>
                        <td>
                          <select id="forTypeNo">
                            <option value="1">社區</option>
                            <option value="2">美食</option>
                            <option value="3">旅遊</option>
                            <option value="4">家電</option>
                            <option value="5">水電</option>
                            <option value="6">其他</option>
                          </select>
                        </td>
                    </tr>
                    <tr>
                        <th>發佈時間</th>
                        <td>
                            <input type="text" id="forArtPosttime" value="" disabled style="width: 200px">
                        </td>
                    </tr>
                    <tr>
                        <th>文章內容</th>
                        <td>
                            <textarea id="forArtContent" value="" required></textarea>
                            <div class="errorMsg" id="errforArtContent">
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="confirm_block"></div>
        </form>
    </article>
`;



//======================== 燈箱關閉 ========================
// 關閉 後來在地址資料表中增加的燈箱
$("#mem-article-table").on("click", "button.mem_btnModalClose", function () {

  $(this).closest("div.member_overlay").fadeOut();
  //清空燈箱裡的article區塊
  $(this).closest("div.member_overlay").empty();
});



var forArtNo; //存 點擊的該筆資料 的 文章編號
//======================= 點擊編輯，將文章的資料顯示在燈箱上=======================
$("#mem-article-table").on('click', "button.addr_btn_edit", function(){

      let that = $(this);
      //將article區塊放入 點擊編輯的那列資料 的燈箱中
      that.closest("td").children("div.member_overlay").prepend(overlay);   
  
      //將「確定修改」的按鈕放入燈箱
      let mem_btnConfirmEdit = `
          <button type="button" class="mem_btnConfirmEdit">確定修改</button>
      `;
      that.closest("td").find("div.confirm_block").prepend(mem_btnConfirmEdit);
      

      //點擊編輯的該筆資料的 文章編號，將文章編號送到後端，抓取該文章編號的資料，顯示在燈箱上
      forArtNo = that.closest("tr").children().eq(1).text();

      let forArtNoData = {
        "forArtNo": forArtNo
      };
      forArtNoData = JSON.stringify(forArtNoData);

      $.ajax({
        url: `${projectPath}/forumArticle/findByArtNo`,
        type: "POST",
        data: forArtNoData,
        dataType: "json",
        contentType : 'application/json;charset=UTF-8',  //一定要有這一行，不然會請求失敗
        success: function (data) {  
          console.log(data);
          $("#forArtTitle").val(data.forArtTitle);
          $("#forTypeNo").val(data.forTypeNo);
          $("#forArtPosttime").val(data.forArtPosttime);
          $("#forArtContent").val(data.forArtContent);
          that.closest("td").children("div.member_overlay").fadeIn();
        },
        error: function (xhr) {
          console.log("error");
          console.log(xhr);
        }
      })

})



//確定修改?
$("#mem-article-table").on("click", "button.mem_btnConfirmEdit", function () {
  let r = confirm("確定修改？");
  if(!r){
      return; //按「取消」，就直接結束程式
  }
  let that = $(this);
  
  //將錯誤區塊中的錯誤訊息清空
  $("div.errorMsg").empty();

  let forArtTitle = $("#forArtTitle").val().trim();
  let forTypeNo = $("#forTypeNo").val();
  let forArtContent = $("#forArtContent").val().trim();

  //*******************驗證內容是否有誤
  let errData={};
  if(forArtTitle == ""){
    errData["errforArtTitle"] = "請輸入文章標題";
  }
  if(forArtContent == ""){
    errData["errforArtContent"] = "請輸入文章內容";
  }

  if( errData != {} ){
    function errMsgs(errItem, errTag){
      let errHtml = `
        <span>${errItem}</span>
      `;
      $(errTag).append(errHtml);
    }

    //如果Json物件中沒有該key，就會是undefined，undefined會是false
    if(errData.errforArtTitle != undefined){
      errMsgs(errData.errforArtTitle, "#errforArtTitle");
    }

    if(errData.errforArtContent != undefined ){
      errMsgs(errData.errforArtContent, "#errforArtContent");
    }

    return; //顯示錯誤訊息在燈箱上，並結束程式
  }

  //*******************驗證完成，將資料傳給後端修改資料
  let formData = {
    "forArtNo": forArtNo,
    "forArtTitle": forArtTitle,
    "forTypeNo": forTypeNo,
    "forArtContent": forArtContent
  };

  formData = JSON.stringify(formData);
  console.log("formData: " + formData);

  //將類別的下拉式選單從數字轉換為文字，再放回資料列
  let forTypeText;
  switch(forTypeNo){
    case "1":
      forTypeText = "社區"
      break;
    case "2":
      forTypeText = "美食"
      break;
    case "3":
      forTypeText = "旅遊"
      break;
    case "4":
      forTypeText = "家電"
      break;
    case "5":
      forTypeText = "水電"
      break;
    case "6":
      forTypeText = "其他"
      break;
  }

  $.ajax({
    url: `${projectPath}/forumArticle/update`,
    type: "POST",
    data: formData,
    dataType: "json",
    contentType : 'application/json;charset=UTF-8',  //一定要有這一行，不然會請求失敗
    success: function (data) {  
      console.log(data);
    
      //將修改好的資料放回該筆資料列
      that.closest("tr").children("td").eq(0).text(forArtTitle);
      that.closest("tr").children("td").eq(2).text(forArtContent);
      that.closest("tr").children("td").eq(3).text(forTypeText);

      //燈箱消失
      that.closest("div.member_overlay").fadeOut();

      //清空燈箱裡的article區塊
      that.closest("div.member_overlay").empty();


    },
    error: function (xhr) {
      console.log("error");
      console.log(xhr);
    }
  })


  
});


//================ 刪除貼文 -> 將資料庫的貼文狀態改為 2 ================//
$("#mem-article-table").on('click', "button.addr_btn_delete", function(){
  let r = confirm("確認刪除？");
  if(!r){
   return;  //按「取消」，就直接結束程式
  }

  let that = $(this);

  //點擊編輯的該筆資料的 文章編號
  forArtNo = that.closest("tr").children().eq(1).text();
  //console.log(forArtNo);

  let formData = {
    "memAcct": memAcct,
    "forArtNo": forArtNo
  };
  console.log(formData);

  $.ajax({
    url: `${projectPath}/mem/updateArtState`,
    type: "GET",
    data: formData,
    dataType: "json",
    contentType : 'application/json;charset=UTF-8',  //一定要有這一行，不然會請求失敗
    success: function (data) {  
      console.log(data);
      //回傳數字1，表示刪除成功
      if(data == 1){
        alert("刪除成功");
        that.closest("tr").fadeOut(500, function () {
          //console.log(this);
          that.remove();
        });
      }

    },
    error: function (xhr) {
      console.log("error");
      console.log(xhr);
    }
  })

});

//================分頁================//
function paging(){
    console.log("hello");
    $('#mem-article-table').after('<div id="nav"></div>');
    var rowsShown = 8;

    var rowsTotal = $('#mem-article-table tbody tr').length;

    var numPages = Math.ceil(rowsTotal / rowsShown);
    
    if(numPages == 0){    //如果資料筆數為0筆，直接結束程式，因為資料筆數為0筆，就不會跑下面的for迴圈
      return;
    }else{
      for (let i = 0; i < numPages ; i++) {
        let pageNum = i + 1;
        $('#nav').append('<a href="###" id="pageStyle" rel="' + i + '">' + "<span>" + pageNum + "</span>" + '</a> ');
      }
    }

    $('#mem-article-table tbody tr').hide();
    $('#mem-article-table tbody tr').slice(0, rowsShown).show();
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
        $('#mem-article-table tbody tr').css('opacity', '0.0')
            .hide()
            .slice(startItem, endItem)
            .css('display', 'table-row').animate({ opacity: 1 }, 300);
    });

}