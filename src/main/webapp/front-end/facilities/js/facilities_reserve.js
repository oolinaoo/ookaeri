$(function () {
  // ajax 的 data 之後要改成動態載入的！！
  // var resData = JSON.stringify({ facNo: 3, histDate: "2021-12-19"});
  var wd = sessionStorage.getItem("whichDay");
  var wm = sessionStorage.getItem("whichMonth");
  var wy = sessionStorage.getItem("whichYear");
  var date = `${wy}-${wm}-${wd}`;
  var facNumber = sessionStorage.getItem("facName");
  // 這邊要先載入預約資料 
  function facResHist(date) {
    $.ajax({
      url: "/okaeri/fachist/facResTimeHist",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,
        histDate: date
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        var facResTime="";
        var facResAmtSameTime="";

        for (i = 0; i < 7; i++) {
          var open_time = $("tr.reserve_info").eq(i).children("td.fac_open_time");
          var maxAmount = 30;  //之後從資料庫抓
          var remain = maxAmount;
          // var userHist = false;   用來儲存該會員在該時段是否預約過

          $.each(data, function (index, item) {
            if (item.histTime == open_time.text()) {
              //比對資料庫的租借時間是否等同於網頁節點的時間值
              open_time.siblings("td").children("select").attr("disabled", "disabled");
              open_time.siblings("td").children("input").attr("disabled", "disabled");
              remain = remain - item.histAmount;
              // if(item.memAcct == ){userHist = true;}
            }
          });

          // 之後要加上 && userHist == true
          if(remain != maxAmount){
            open_time.siblings("td.edit").html(`<i class="fas fa-edit"></i>`);
          }

          $(`td#remain${i + 1}`).html(remain); 

          // 預約人數 hist amount
          (function () {
            $(`select#amount${i + 1}`).empty();
            for (let j = 0; j < remain; j++) {
              $(`select#amount${i + 1}`).append(`<option>${j + 1}</option>`);
            }
          })();

        };
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }
  facResHist(date);
  ///////////////////////////////////


  // 這邊可以先寫新增和刪除的ajax，底下再來呼叫
  // 租借
  function reserved(){
    $.ajax({
      url: "/okaeri/fachist/facReserve",
      type: "POST",
      data: JSON.stringify({
        facNo: 3,                     /////////////////////////////////////////
        memAcct: "gina5",             /////////////////////////////////////////
        histDate: "2021-12-19",       /////////////////////////////////////////
        histTime: "12:00 ~ 14:00",    /////////////////////////////////////////
        histAmount: 4                 /////////////////////////////////////////
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        console.log(data);
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });

  }





  // 確認是否預約的 select button & confirm
  $("table#reserve_list").on("click", "div.customcheck", function () {
    var checkbutton = $(this).children("div.checkitem");
    var checkbuttonBg = $(this);
    var checkbuttonIp = checkbuttonBg.prev("input[name='switch']");

//之後要改成如果是自己有預約的，才能用編輯！
    // 判斷該項能否編輯
    if (checkbuttonIp.attr("disabled") != "disabled") {
      // 如果是 Activated, Deactivate it !
      if (checkbutton.hasClass("itemactive")) {
        var yes = confirm("確定要取消預約嗎？"); // confirm 彈窗警告
        if (yes) {
          checkbutton.removeClass("itemactive");
          checkbuttonBg.removeClass("active");
          checkbuttonBg.css("background-color", "#B56662");
          checkbuttonIp.prop("checked", false);

          // 執行刪除的 ajax
        }
      } else {
        // 如果是 Deactivated, Activate it !
        var yes = confirm("確定要預約嗎？"); // confirm 彈窗警告
        if (yes) {
          checkbutton.addClass("itemactive");
          checkbuttonBg.addClass("active");
          checkbuttonBg.css("background-color", "#278364");
          checkbuttonIp.prop("checked", true);

          // 執行新增的 ajax
          reserved();
          // facResHist();
        }
      }
    }
  });






  // 編輯按鈕
  // 按下編輯鈕後、讓人數和預約鈕可以改
  $("td").on("click", ".fa-edit", function () {
    // 換圖示
    $(this).addClass("fa-save");
    $(this).removeClass("fa-edit");
    // 讓選單和 switch button 可以編輯
    $(this).parent("td").siblings("td").children("select").attr("disabled", false);
    $(this).parent("td").siblings("td").children("input").attr("disabled", false);
    // 改 switch button 的顏色
    if ($(this).parent("td").siblings("td").children("div").hasClass("active")) {
      // 原先淺綠變可編輯的深綠
      $(this).parent("td").siblings("td").children("div.customcheck").css("background-color", "#278364");
    } else {
      // 原先淺褐色變可編輯的深褐色
      $(this).parent("td").siblings("td").children("div.customcheck").css("background-color", "#B56662");
    }
  });

  $("td").on("click", ".fa-save", function () {
    // 換圖示
    $(this).addClass("fa-edit");
    $(this).removeClass("fa-save");
    $(this).parent("td").siblings("td").children("select").attr("disabled", "disabled");
    $(this).parent("td").siblings("td").children("input").attr("disabled", "disabled");
    // 改 switch button 的顏色
    if (
      $(this).parent("td").siblings("td").children("div").hasClass("active")
    ) {
      // 深綠變編輯完的淺綠
      $(this).parent("td").siblings("td").children("div.customcheck").css("background-color", "#74bba3");
    } else {
      // 深褐色變編輯完的淺褐
      $(this).parent("td").siblings("td").children("div.customcheck").css("background-color", "#E2A3A1");
    }

    // 這邊要把變數存入資料數
    // $.ajax({});
    // 如果有資料存入，記得加上編輯鈕
  });

  // END
});
