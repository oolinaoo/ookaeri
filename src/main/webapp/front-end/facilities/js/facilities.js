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

  //=========   TOP 按鈕  =========
  $("div.go_top").on("click", function () {
    $("html, body").animate(
      { scrollTop: $("#header").offset().top },
      { duration: 1000, easing: "swing" }
    );
  });

  //=========  操控公設地圖選單  ========
  $(`div#A`).removeAttr("style"); // 預設為 A 棟
  $("select#fac_map").on("change", function(){
    console.log(this.value);
    $(`div#${this.value}`).removeAttr("style");
    $(`div#${this.value}`).siblings("div.floor_plan").attr("style", "display : none");

  });

});
