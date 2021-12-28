$(function () {
  /*******************************
   ****                       ****
   ****                       ****
   ****    FACILITIES LIST    ****
   ****                       ****
   ****                       ****
   *******************************/

  //=========   取消 a 的預設行為  =========
  $("div.facility a").on("click", function (e) {
    e.preventDefault();
  });


  //=========  操控公設地圖選單  ========
  var addr = "A"; // 預設為 A 棟
  $(`div#${addr}`).removeAttr("style"); 
  $("select#fac_map").on("change", function(){
    console.log(this.value);
    addr = this.value;
    $(`div#${addr}`).removeAttr("style");
    $(`div#${addr}`).siblings("div.floor_plan").attr("style", "display : none");
    facilitiesMap(addr);
  });


  //=========   載入公設地圖資料的函式   =========
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
              fac.attr("href", `/okaeri/fac/facPhotoByFacNo?facNo=${item.facNo}`);
              fac.attr("data-lightbox", `fac_photo${addr}`);
              fac.attr("data-title", `${item.facName}`);
              fac.attr("data-alt", `${item.facName}`);
              fac.children("div").empty();
              fac.children("div").append(`${item.facName}`);
              fac.attr("data-facno", `${item.facNo}`);
              fac.attr("data-state", `${item.facState}`);
              fac.attr("style", "cursor: pointer;");
            } else {
              fac.attr("data-title", `${item.facName}`);
              fac.attr("data-alt", `${item.facName}`);
              fac.children("div").empty();
              fac.children("div").append(`${item.facName}維修中`).attr("style", "color: lightgray; font-style: italic;");
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




  //=========   TOP 按鈕  =========
  $("div.go_top").on("click", function () {
    $("html, body").animate(
      { scrollTop: $("#header").offset().top },
      { duration: 1000, easing: "swing" }
    );
  });

});
