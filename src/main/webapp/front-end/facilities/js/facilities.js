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

});
