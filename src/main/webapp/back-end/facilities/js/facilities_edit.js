$(function () {
  // 清空各種欄位的函式
  function clean(){
    $("form#fac_details")[0].reset();
    $(`input`).removeAttr("checked");
    $(`input`).prop("checked", false);
    $("div.photo").empty();
    $("div").find("input#fphoto").val("");
  }

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
            if(item.facState == 0){
              fac.attr("data-title", `${item.facName}`);
              fac.children("div").empty();
              fac.children("div").append(`${item.facName}`);
              fac.attr("data-facno", `${item.facNo}`);
              fac.attr("data-state", `${item.facState}`);
            } else {
              fac.attr("data-title", `${item.facName}`);
              fac.children("div").empty();
              fac.children("div").append(`${item.facName}（下架）`).attr("style", "font-style: italic;");
              fac.attr("data-facno", `${item.facNo}`);
              fac.attr("data-state", `${item.facState}`);
            }
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

  // 透過地圖下拉選單載入該地址的公設清單
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




  // **************************  新增公設  *********************//
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

  // 新增公設的圖，轉 byte[]
  var fphoto =[];
  $("div").find("input#fphoto").on("change", function(e){
    fphoto =[];
    var arrayBuffer = "";
    var array = "";
    var reader = new FileReader();
    if(this.files[0] != undefined){
      reader.readAsArrayBuffer(this.files[0]);
      reader.onload = function (file) {
        if(file.target.readyState == FileReader.DONE){
          arrayBuffer = file.target.result;
          array = new Uint8Array(arrayBuffer);
          for(var i = 0; i < array.length; i++){
            fphoto.push(array[i]);
          }
        }
      }
    } else {
      $("div.overlay").find("div.preview_photo").remove();
      $("div.edit").find("div.old_preview_photo").remove();
    }
  });

  // 新增公設的圖片預覽
  function preview_picture(file){
    let prereader = new FileReader();
    prereader.addEventListener("load", function(){
      $("div.overlay").find("div.photo").empty();
      $("div.edit").find("div.preview_photo").remove();
      $("div").find("div.photo").append(`<div class="preview_photo">預覽圖：<img src = "${prereader.result}" class = "preview_img"></div>`);      
    });
    prereader.readAsDataURL(file);  // 讀檔
  }

  $("div").find("input#fphoto").on("change", function(e){
    if (this.files[0] != undefined){
      preview_picture(this.files[0]);
    } else {
      $("div.overlay").find("div.preview_photo").remove();
      $("div.edit").find("div.old_preview_photo").remove();
    }
  });


  // 新增公設的開放日
  function newFacDate(newestNo){
    for(var i = 1; i <= 7; i++){
      if($(`input#fdate${i}`).is(":checked") || $(`input#fdate${i}`).attr("checked")=="checked"){
        var od = $(`input#fdate${i}`).val();
        console.log($(`input#fdate${i}`).prop("checked"));

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
      if($(`input#ftime${i}`).is(":checked") || $(`input#ftime${i}`).attr("checked")=="checked"){
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
  function facilitiesAdd(){
    $.ajax({
      url: "/okaeri/fac/addNewFac",
      type: "POST",
      data: JSON.stringify({
        facName: $("div.overlay").find("input#fname").val(),
        facMax: $("div.overlay").find("select#fmax").val(),
        facState: $("div.overlay").find("select#fstate").val(),
        facPhoto: fphoto,
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
        clean();
      }
    });
  }
  // **************************  新增公設  *********************//




  // **************************  編輯公設  *********************//
  // 編輯時，列出公設資料的 ajax
  function editFac(facNoEdit){
    for(var i = 1; i<=7; i++){
      $("div.edit").find(`input#fdate${i}`).prop("checked", false);
      $("div.edit").find(`input#ftime${i}`).prop("checked", false);
    }
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
        $("div.edit").find("input#fname").val(`${data[0].facName}`);
        $("div.edit").find("select#fmax").append(max);
        $("div.edit").find("select#fmax").val(`${data[0].facMax}`);
        $("div.edit").find("select#fstate").val(`${data[0].facState}`);

        for(var i = 1; i <= 7; i++ ){
          $.each(data, function(index, item){
            if($("div.edit").find(`input#fdate${i}`).val()==item.facOpenDate){
              $("div.edit").find(`input#fdate${i}`).prop("checked", true);  
            }
            if($("div.edit").find(`input#ftime${i}`).val()==item.startTime){
              $("div.edit").find(`input#ftime${i}`).prop("checked", true);
            }             
          });
        }

        $("div.edit").find("div.photo").append(`<div class = "old_preview_photo">原圖： <img src = "/okaeri/fac/facPhotoByFacNo?facNo=${data[0].facNo}"></div>`);      
      },
      error: function (xhr) {
          console.log("error");
          console.log(xhr);
      }
    }); 
  }

  // 開啟編輯的彈窗
  var facNoEdit = "";
  $("a").children("div").on("click", function(e){
    if($(this).html() != ""){
      facNoEdit = $(this).closest("a").attr("data-facno");
      editFac(facNoEdit);
      $("div.edit").fadeIn();
    }
  });


  // 設施日期和時間刪除
  function facDateTimeDelete(facNoEdit){
    $.ajax({
      url: "/okaeri/fac/facDateTimeDelete",
      type: "POST",
      data: JSON.stringify({
        facNo: facNoEdit,
      }),
      processData: false,
      contentType : false,
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        console.log(data);
          newFacDate(facNoEdit);
          newFacTime(facNoEdit);
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      }
    });
  }


  // 設施資料更新
  function facilitiesUpdate(facNoEdit){
    $.ajax({
      url: "/okaeri/fac/facEdit",
      type: "POST",
      data: JSON.stringify({
        facNo: facNoEdit,
        facName: $("div.edit").find("input#fname").val(),
        facMax: $("div.edit").find("select#fmax").val(),
        facState: $("div.edit").find("select#fstate").val(),
        facPhoto: fphoto
      }),
      processData: false,
      contentType : false,
      dataType: "json",
      headers: {
        "Content-Type": "application/json",
      },
      success: function (data) {
        if(data==1){
          facDateTimeDelete(facNoEdit);
        }
      },
      error: function (xhr) {
        console.log("error");
        console.log(xhr);
      },
      complete: function (xhr){
        facilitiesMap(addr);
        // clean();
        $("div.photo").empty();
        $("div").find("input#fphoto").val("");
      }
    });
  }
  // **************************  編輯公設  *********************//
  // 關閉新增或修改的彈窗
  $("button.btn_modal_close").on("click", function () {
    $("div.overlay").fadeOut();
    $("div.edit").fadeOut();
    clean();
  });
  $(document).on("keydown", function (e) {
    if (e.key === "Escape") {
      $("div.overlay").fadeOut();
      $("div.edit").fadeOut();
      clean();
    }
  });

  // 點外面關掉彈窗
  $("div.overlay").on("click", function (e) {
    $("div.overlay").fadeOut();
    clean();
  });
  $("div.edit").on("click", function (e) {
    $("div.edit").fadeOut();
    clean();
  });
  $("article").on("click", function (e) {
    e.stopPropagation();
  });

  $("div").find("input.cancel").on("click", function () {
    $("div.overlay").fadeOut();
    $("div.edit").fadeOut();
    clean();
  });

  $("div").find("input.reset").on("click", function(){
    $("div.photo").empty();
  });

  var nameRE = /^[\u4e00-\u9fa5_a-zA-Z()（）]{1,7}/;
  // 按下確認新增資料
  $("div.overlay").find("input.confirm").on("click", function(){
    if($("div.overlay").find("input#fname").val().match(nameRE) != null){
      $("div.overlay").fadeOut();
      // 呼叫新增公設的主函式，再由主函式呼叫兩個時間表的新增
      facilitiesAdd(); 
    } else {
      alert("設施名稱格式有誤！");
    }
  });

  // 按下修改確認修改資料
  $("div.edit").find("input.confirm").on("click", function(){
    if($("div.overlay").find("input#fname").val().match(nameRE) != null){
      $("div.edit").fadeOut();
      // 呼叫修改的主函式 
      facilitiesUpdate(facNoEdit);
    } else {
      alert("設施名稱格式有誤！");
    }
  });

  // // 
  // $("input").on("click", function(){
  //   console.log(this);
  //   $(this).attr("checked") == "checked" ? $(this).attr("checked", false) : $(this).attr("checked","checked");
  // });



});
