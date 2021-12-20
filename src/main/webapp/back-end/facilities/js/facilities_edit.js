$(function () {
  // 操控公設地圖選單
  $(`div#A`).removeAttr("style"); // 預設為 A 棟
  var addr = "A";
  // 載入資料的函式
  function facilitiesMap(addr) {
    $.ajax({
      url: "/okaeri/fac/listByFloorAddr",
      type: "GET",
      data: {
        facAddr: addr,
      },
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        $.each(data, function (index, item) {
          var i = `${item.facAddr}`.charCodeAt(0) - 65;
          if ($("div.floor").eq(i).attr("data-addr") == item.facAddr) {
            var fac = $(`a.${item.facAddr}${item.facAddrNo}`);
            fac.attr("data-title", `${item.facName}`);
            fac.children("div").empty();
            fac.children("div").append(`${item.facName}`);
            fac.attr("data-facno", `${item.facNo}`);
            fac.attr("data-state", `${item.facState}`);
          }
        });
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }
  facilitiesMap(addr);

  // 透過下拉選單載入該地址的公設清單
  $("select#fac_map").on("change", function () {
    $(`div#${this.value}`).removeAttr("style");
    $(`div#${this.value}`)
      .siblings("div.floor_plan")
      .attr("style", "display : none");

    // 呼叫載入資料的函式
    addr = this.value;
    facilitiesMap(addr);
  });

  // 新增公設的人數選單
  var max = "";
  for (var i = 2; i <= 40; i++) {
    max += `<option value="${i}">${i}</option>`;
  }

  $("div.overlay").find("select#fmax").append(max);

  // 可拖曳的新增公設
  $("a").children("div").on("dragover", function (e) {
      if ($(this).html() == "") 
        e.preventDefault();
  });

  var faddr = "";
  var ffloor = "";
  var faddrno = "";
  
  // 取得資料並開啟新增公設的彈窗
  $("a").children("div").on("drop", function(e) {
    if($(this).html() == ""){
      faddr = $(this).closest("div.facility").siblings("div.floor").attr("data-addr");
      ffloor = $(this).closest("div.facility").siblings("div.floor").attr("data-floor");
      faddrno = $(this).closest("a").attr("data-addrno");

      $("div.overlay").fadeIn();
    }
  });

  // 編輯時，列出公設資料的 ajax
  function editFac(facNoEdit){
    $.ajax({
      url: "/okaeri/fac/listFacDetail",
      type: "POST",
      data: JSON.stringify({
        facNo: facNoEdit
      }),
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        // 這邊要各種 append 資料
        // var facEditDate = "";
        // var facEditTime = "";

        var facEditDetail = `
          <label for="fname">公設名稱：</label>
          <input type="text" id="fname" name="fname" value="${data[0].facName}">
          <label for="fmax">人數上限：</label>
          <select name="fmax" id="fmax">
          </select>

          <label for="fstate">公設上架：</label>
          <select name="fstate" id="fstate">
            <option value="0" `+ (data[0].facState == 0 ? "selected" :"") + `>上架</option>
            <option value="1" `+ (data[0].facState == 1 ? "selected" :"") + `>下架</option>
          </select>
          <br><br>`;

        //   for(var i = 1; i <= 7; i++ ){
        //     facEditDate += `
        //       <input type="checkbox" id="fdate1" class="fdate" name="fdate1" value="一"
        //       `+ (item.facOpenDate == "一" ? "checked" :"") + `>
        //       <label for="fdate1"> 一 </label>`;



        //   }
        // $.each(data, function(index, item){
        //   facEditDate =  
        //         `<input type="checkbox" id="fdate1" class="fdate" name="fdate1" value="一"
        //         `+ (item.facOpenDate == "一" ? "checked" :"") + `>
        //         <label for="fdate1"> 一 </label>
        //         <input type="checkbox" id="fdate2" class="fdate" name="fdate2" value="二"
        //         `+ (item.facOpenDate == "二" ? "checked" :"") + `>
        //         <label for="fdate2"> 二 </label>
        //         <input type="checkbox" id="fdate3" class="fdate" name="fdate3" value="三"
        //         `+ (item.facOpenDate == "三" ? "checked" :"") + `>
        //         <label for="fdate3"> 三 </label>
        //         <input type="checkbox" id="fdate4" class="fdate" name="fdate4" value="四"
        //         `+ (item.facOpenDate == "四" ? "checked" :"") + `>
        //         <label for="fdate4"> 四 </label>
        //         <input type="checkbox" id="fdate5" class="fdate" name="fdate5" value="五"
        //         `+ (item.facOpenDate == "五" ? "checked" :"") + `>
        //         <label for="fdate5"> 五 </label>
        //         <input type="checkbox" id="fdate6" class="fdate" name="fdate6" value="六"
        //         `+ (item.facOpenDate == "六" ? "checked" :"") + `>
        //         <label for="fdate6"> 六 </label>
        //         <input type="checkbox" id="fdate7" class="fdate" name="fdate7" value="日"
        //         `+ (item.facOpenDate == "日" ? "checked" :"") + `>
        //         <label for="fdate7"> 日 </label>
        //         <br><br>`;
        // });
            
        // $.each(data, function(index, item){

        //         facEditTime = 
        //         `<label for="ftime">公設開放時段：</label><br>
        //         <input type="checkbox" id="ftime1" class="ftime" name="ftime1" value="08">
        //         <label for="ftime1"> 08:00 ~ 10:00 </label>
        //         <input type="checkbox" id="ftime2" class="ftime" name="ftime2" value="10">
        //         <label for="ftime2"> 10:00 ~ 12:00 </label>
        //         <input type="checkbox" id="ftime3" class="ftime" name="ftime3" value="12">
        //         <label for="ftime3"> 12:00 ~ 14:00 </label>
        //         <input type="checkbox" id="ftime4" class="ftime" name="ftime4" value="14">
        //         <label for="ftime4"> 14:00 ~ 16:00 </label>
        //         <br>
        //         <input type="checkbox" id="ftime5" class="ftime" name="ftime5" value="16">
        //         <label for="ftime5"> 16:00 ~ 18:00 </label>
        //         <input type="checkbox" id="ftime6" class="ftime" name="ftime6" value="18">
        //         <label for="ftime6"> 18:00 ~ 20:00 </label>
        //         <input type="checkbox" id="ftime7" class="ftime" name="ftime7" value="20">
        //         <label for="ftime7"> 20:00 ~ 22:00 </label>
        //         <br><br>

        //         <label for="fphoto">公設圖片：</label>
        //         <input type="file" id="fphoto" name="fphoto">
        //         <br><br>`;
        //       });

        var confirmzone = `
          <div class="action">
            <input class="confirm" type="button" value="修改">
            <input class="cancel" type="button" value="取消">
          </div>`;
        
        $("div.edit").find("form").append(confirmzone);
        $("div.edit").find("div.action").before(facEditDetail);

        // 讓人數上限選定？
        var editmax = "";
        for (var i = 2; i <= 40; i++) {
          editmax += `<option value="${i}" `+ (data[0].facMax == i ? "selected" :"") + `>${i}</option>`;
        }
        $("div.edit").find("select#fmax").append(editmax);

        // $("div.edit").find("div.action").before('<label for="fdate">公設開放日：</label>');
        // $("div.edit").find("div.action").before(facEditDate);
        // $("div.edit").find("div.action").before(facEditTime);
        // 圖片之後要用 session 的方式存，不然如果按了瀏覽卻沒有選圖片，原本的可能被蓋掉變成 null
      },
      error: function (xhr) {
          console.log("error");
          console.log(xhr);
      }
    }); 
  }

  // 開啟編輯的彈窗
  $("a").children("div").on("click", function(e){
    if($(this).html() != ""){
      var facNoEdit = $(this).closest("a").attr("data-facno");
      editFac(facNoEdit);
      $("div.edit").fadeIn();
    }
  });


  // 新增公設的開放日
  function newFacDate(newestNo){
    for(var i = 1; i <= 7; i++){
      if($(`input#fdate${i}`).is(":checked")){
        var od = $(`input#fdate${i}`).val();

        $.ajax({
          url: "/okaeri/fac/addNewFacDate",
          type: "GET",
          data: {
            facNo: newestNo,
            facOpenDate: od
          },
          dataType: "json",
          headers: {
            "Content-Type": "application/json",
          },
          success: function (data) {
            console.log("開放日新增成功");
          },
          error: function (xhr) {
              console.log("error");
              console.log(xhr);
          }
        });
      } 
    }
  }

  // 新增公設的開放時段
  function newFacTime(newestNo){
    for(var i = 1; i <= 7; i++){
      if($(`input#ftime${i}`).is(":checked")){
        var fot = $(`input#ftime${i}`).next("label").html();
        var st = Number($(`input#ftime${i}`).val());
        var et = st + 2;

        $.ajax({
          url: "/okaeri/fac/addNewFacTime",
          type: "GET",
          data: {
            facNo: newestNo,
            facOpenTime: fot,
            startTime:  st,
            endTime: et
          },
          dataType: "json",
          headers: {
            "Content-Type": "application/json",
          },
          success: function (data) {
            console.log("開放時間新增成功");
          },
          error: function (xhr) {
              console.log("error");
              console.log(xhr);
          }
        });
      }
    }
  }

  // 新增公設的 ajax
  function facilitiesEdit(){
    $.ajax({
      url: "/okaeri/fac/addNewFac",
      type: "POST",
      data: JSON.stringify({
        facName: $("input#fname").val(),
        facMax: $("select#fmax").val(),
        facState: $("select#fstate").val(),
        // facPhoto: $("input#fphoto")[0].files[0],
        facAddr: faddr,
        facFloor: ffloor,
        facAddrNo: faddrno
      }),
      processData: false,
      contentType : false,
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        if(data != 0){
          newFacDate(data);
          newFacTime(data);  
        } else {
          alert("新增失敗！");
        }
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      },
      complete: function (xhr){
        facilitiesMap(addr);
        // 清空表單
        $("form#fac_details")[0].reset();
      }
    });
  }


  // 關閉新增或修改的彈窗
  $("button.btn_modal_close").on("click", function () {
    $("div.overlay").fadeOut();
    $("form#fac_details")[0].reset();

    $("div.edit").fadeOut();
    $("div.edit").find("form#fac_details").empty();
  });
  $(document).on("keydown", function (e) {
    if (e.key === "Escape") {
      $("div.overlay").fadeOut();
      $("form#fac_details")[0].reset();

      $("div.edit").fadeOut();
      $("div.edit").find("form#fac_details").empty();
    }
  });

  $("div.overlay").on("click", function (e) {
    $("div.overlay").fadeOut();
    $("form#fac_details")[0].reset();
  });
  $("div.edit").on("click", function (e) {
    $("div.edit").fadeOut();
    $("div.edit").find("form#fac_details").empty();
  });
  $("article").on("click", function (e) {
    e.stopPropagation();
  });

  $("input.cancel").on("click", function () {
    $("div.overlay").fadeOut();
    $("form#fac_details")[0].reset();

    $("div.edit").fadeOut();
    $("div.edit").find("form#fac_details").empty();
  });

  // 按下確認新增資料
  $("div.overlay").find("input.confirm").on("click", function(){
    $("div.overlay").fadeOut();
    // 呼叫新增公設的主函式，再由主函式呼叫兩個時間表的新增
    facilitiesEdit();
  });

  // 按下修改確認修改資料

});
