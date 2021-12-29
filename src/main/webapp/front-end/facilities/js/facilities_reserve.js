$(function () {
  // ajax 的 data 之後要改成動態載入的！！
  var wd = sessionStorage.getItem("whichDay");
  var wm = sessionStorage.getItem("whichMonth");
  var wy = sessionStorage.getItem("whichYear");
  var resdate = `${wy}-${wm}-${wd}`;
  var facNumber = sessionStorage.getItem("facNumber");
  var memAcct = "";

  // 這邊要載入設施當前的預約資料 
  function facResHist(resdate) {
    memAcct = $("div#header").find("span#navbar_profile_memAcct_span").html();

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
        // 跑一整 row 時間點
        for (i = 0; i < timeLong; i++) {
          var open_time = $(`tr.reserve_info${i+1}`).children("td.fac_open_time");
          var maxAmount = facMax;     // 該時段的最大人數
          var allRes = 0;     // 該時段的所有借用人數
          var selfRes = 0;    // 該時段自己借用的人數

          $.each(data, function (index, item) {
            if (item.histTime == open_time.text()) {    //比對資料庫的租借時間是否等同於網頁節點的時間值
              
              allRes = allRes + item.histAmount;
              
              if(item.memAcct == memAcct){      // 如果該筆資料是自己的
                open_time.siblings("td").children("select").attr("disabled", "disabled");
                open_time.siblings("td").children("input").attr("disabled", "disabled");
                open_time.siblings("td").children("div.customcheck").addClass("active");
                open_time.siblings("td").children("div.customcheck").children("div.checkitem").addClass("itemactive");
                open_time.siblings("td.edit").html(`<i class="fas fa-edit"></i>`);    // 編輯符號
                selfRes = item.histAmount;
              } 
            }
          });

          $(`td#remain${i + 1}`).html(maxAmount - allRes);  // 所有人看到的剩餘人數

          $(`select#amount${i + 1}`).empty();
          // 如果沒有名額
          if(maxAmount - allRes == 0){
            $(`select#amount${i + 1}`).append(`<option val="0" selected>0</option>`);
            open_time.siblings("td").children("select").attr("disabled", "disabled");
            open_time.siblings("td").children("input").attr("disabled", "disabled");
          }

          // 預約人數 hist amount
          for (let j = 0; j < maxAmount - allRes + selfRes; j++) {
            if(selfRes > 0 && (j + 1)==selfRes){   // 如果自己有預約
              $(`select#amount${i + 1}`).append(`<option val="${j + 1}" selected>${j + 1}</option>`);
            } else {
              $(`select#amount${i + 1}`).append(`<option val="${j + 1}">${j + 1}</option>`);
            }
          }
        };
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }

  // 載入設施基本資料(如人數)
  var facMax = "";
  function oneFacDetail(facNumber, wy, wm, wd){

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
        $("div#reserve_fac").html(data.facName);
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      },
      complete: function(xhr) {
        resdate = `${wy}-${wm}-${wd}`;
        facResHist(resdate);
      }
    });
  }

  // 載入設施的時段
  var timeLong ="";
  function facDateTime(facNumber, wy, wm, wd){

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
      },
      complete: function(xhr) {
        sessionStorage.setItem("whichDay", wd);
        sessionStorage.setItem("whichMonth", wm);
        sessionStorage.setItem("whichYear", wy);

        oneFacDetail(facNumber, wy, wm, wd);
      }
    });
  }
  facDateTime(facNumber, wy, wm, wd);


  // 這邊可以先寫新增和刪除的ajax，底下再來呼叫
  // 租借
  function reserved(that){
    var resTime = $(that).closest("td").siblings("td.fac_open_time").html();
    var resAmt = $(that).closest("td").siblings("td").children("select").val();
    memAcct = $("div#header").find("span#navbar_profile_memAcct_span").html();
    console.log(resdate);

    $.ajax({
      url: "/okaeri/fachist/facReserve",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,                     
        memAcct: memAcct,             
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
        alert("預約成功！");
        location.reload();
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }

  // 刪除預約紀錄
  function resDelete(that){
    var resTime = $(that).closest("td").siblings("td.fac_open_time").html();
    memAcct = $("div#header").find("span#navbar_profile_memAcct_span").html();
    console.log(resdate);

    $.ajax({
      url: "/okaeri/fachist/fachistDelete",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,                     
        memAcct: memAcct,             
        histDate: resdate,       
        histTime: resTime
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        console.log(data);
        alert("成功取消預約！");
        location.reload();
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }

  // 預約人數修改
  function resUpdate(that, newAmt){
    var resTime = $(that).closest("td").siblings("td.fac_open_time").html();
    memAcct = $("div#header").find("span#navbar_profile_memAcct_span").html();
    console.log(resdate);

    $.ajax({
      url: "/okaeri/fachist/fachistAmtUpdate",
      type: "POST",
      data: JSON.stringify({
        facNo: facNumber,                     
        memAcct: memAcct,             
        histDate: resdate,       
        histTime: resTime,    
        histAmount: newAmt    
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        alert(data);
        location.reload();
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
          resDelete(that);
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
        }
      }
    }
  });

  // 編輯按鈕
  // 按下編輯鈕後、讓人數和預約鈕可以改
  var oldAmt = 0;
  var newAmt = 0;
  $("tbody").on("click", ".fa-edit", function () {
    // 換圖示
    $(this).addClass("fa-save");
    $(this).removeClass("fa-edit");

    // 儲存預約人數
    oldAmt =  $(this).parent("td").siblings("td").children("select").val();

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

    // 儲存新的預約人數
    newAmt = $(this).parent("td").siblings("td").children("select").val();

    // 改 switch button 的顏色
    if ($(this).parent("td").siblings("td").children("div").hasClass("active")) {
      // 深綠變編輯完的淺綠
      $(this).parent("td").siblings("td").children("div.customcheck").css("background-color", "#74bba3");
    } else {
      // 深褐色變編輯完的淺褐
      $(this).parent("td").siblings("td").children("div.customcheck").css("background-color", "#E2A3A1");
    }

    // 判斷人數有沒有更改，有更改才跑 update 函式
    if(newAmt == oldAmt){
      alert("預約人數未更改！");
    } else {
      var that = this;
      resUpdate(that, newAmt);
    }
    
  });


  // 按下前一天或是後一天的鈕！
  // 先判斷該月有幾天
  function daysInMonth(month, year) {
    return month === 2 ? (year & 3) || (!(year % 25) && year & 15) ? 28 : 29 : 30 + (month + (month >> 3) & 1);
  }

  $("a.prev_reserve_day").on("click", function(){
    // 先清空表格
    $("table#reserve_list").children("tbody").empty();
    // 如果日 - 1 = 0，要到前一個月、甚至前一年
    // 執行判斷天數
    var wyNo = Number(sessionStorage.getItem("whichYear"));
    var wmNo = Number(sessionStorage.getItem("whichMonth"));
    var wdNo = Number(sessionStorage.getItem("whichDay"));
    dayQty = daysInMonth(wmNo, wyNo);
    console.log(dayQty);

    if(wdNo - 1 == 0){
      if(wmNo - 1 == 0){
        wmNo = 12;
        wyNo -= 1;
      } else {
        wmNo -= 1;
      }
      wdNo = daysInMonth(wmNo, wyNo);
    } else {
      wdNo -= 1
    }

    var preday = new Date(wyNo, wmNo-1, wdNo);
    console.log(preday.getDay());

    // 這邊先比對公休日



    // 執行載入環境
    facDateTime(facNumber, wyNo, wmNo, wdNo);
  });

  $("a.next_reserve_day").on("click", function(){
    // 先清空表格
    $("table#reserve_list").children("tbody").empty();
    // 執行判斷天數
    var wyNo = Number(sessionStorage.getItem("whichYear"));
    var wmNo = Number(sessionStorage.getItem("whichMonth"));
    var wdNo = Number(sessionStorage.getItem("whichDay"));
    dayQty = daysInMonth(wmNo, wyNo);
    console.log(dayQty);

    // 如果超出最後一天、要到下一天、甚至下一年
    if(wdNo + 1 > dayQty){
      wdNo = 1;
      if(wmNo + 1 > 12){
        wmNo = 1;
        wyNo += 1;
      } else {
        wmNo += 1;
      }
    } else {
      wdNo += 1
    }

    var nextday = new Date(wyNo, wmNo-1, wdNo);
    console.log(nextday.getDay());

    // 這邊先比對公休日



    // 執行載入環境
    facDateTime(facNumber, wyNo, wmNo, wdNo);
  });




  // END
});
