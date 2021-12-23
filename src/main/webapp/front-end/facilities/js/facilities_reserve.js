$(function () {
  // ajax 的 data 之後要改成動態載入的！！
  // var resData = JSON.stringify({ facNo: 3, histDate: "2021-12-19"});
  var wd = sessionStorage.getItem("whichDay");
  var wm = sessionStorage.getItem("whichMonth");
  var wy = sessionStorage.getItem("whichYear");
  var resdate = `${wy}-${wm}-${wd}`;
  var facNumber = sessionStorage.getItem("facNumber");

  // 載入設施基本資料(如人數)
  var facMax = "";
  function oneFacDetail(facNumber){
    console.log(facNumber);
    $.ajax({
      url: "/okaeri/fac/listOneFac",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {   
        facMax = data.facMax;
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }
  oneFacDetail(facNumber);

  // 載入設施的時段
  var timeLong ="";
  function facDateTime(facNumber){
    $.ajax({
      url: "/okaeri/fac/openTime",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        var resTable = "";
        $.each(data, function(index, item){
          console.log("index = "+ index);
          resTable += `
              <tr class="reserve_info${index+1}">
                <td class="fac_open_date">${wm}/${wd}</td>
                <td class="fac_open_time">${item.facOpenTime}</td>
                <td id="remain${index+1}"></td>
                <td>
                  <select name="ammount${index+1}" id="amount${index+1}"></select>
                </td>
                <td>
                  <input type="checkbox" name="switch"/>
                  <div class="customcheck">
                    <div class="checkitem"></div>
                  </div>
                </td>
                <td class="edit"></td>
              </tr>`;
          timeLong = index + 1;
        });

        $("tbody").append(resTable);
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }
  facDateTime(facNumber);

  // 這邊要先載入設施的預約資料 
  function facResHist(resdate) {
    $.ajax({
      url: "/okaeri/fachist/facResTimeHist",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,
        histDate: resdate
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        console.log("timeLong = " + timeLong);

        for (i = 0; i < timeLong; i++) {
          var open_time = $(`tr.reserve_info${i+1}`).children("td.fac_open_time");
          var maxAmount = facMax; 
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
  facResHist(resdate);
  ///////////////////////////////////


  // 這邊可以先寫新增和刪除的ajax，底下再來呼叫
  // 租借
  function reserved(that){
    var resTime = $(that).closest("td").siblings("td.fac_open_time").html();
    var resAmt = $(that).closest("td").siblings("td").children("select").val();

    $.ajax({
      url: "/okaeri/fachist/facReserve",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,                     
        memAcct: "gina5",             /////////////////////////////////////////
        histDate: resdate,       
        histTime: resTime,    
        histAmount: resAmt    
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
      },
      conplete: function (xhr) {
        facResHist(resdate);
      }
    });

  }

  // 刪除預約紀錄
  function resDelete(that){
    var resTime = $(that).closest("td").siblings("td.fac_open_time").html();
    var resAmt = $(that).closest("td").siblings("td").children("select").val();

    $.ajax({
      url: "/okaeri/fachist/XXXXXXX",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,                     
        memAcct: "gina5",             /////////////////////////////////////////
        histDate: resdate,       
        histTime: resTime,    
        histAmount: resAmt    
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
      },
      conplete: function (xhr) {
        facResHist(resdate);
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
          var that = this;
          resDelete(that)
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
          var that = this;
          reserved(that);
          // facResHist();
        }
      }
    }
  });




  // 編輯按鈕
  // 按下編輯鈕後、讓人數和預約鈕可以改
  $("tbody").on("click", ".fa-edit", function () {
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

  $("tbody").on("click", ".fa-save", function () {
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
