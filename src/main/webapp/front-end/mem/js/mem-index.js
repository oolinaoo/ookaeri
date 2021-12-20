let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri

var user_id = "gina1";

function init(){
  //$("ul.task_list").html('<li style="text-align: center;"><i class="fas fa-spinner fa-spin fa-3x"></i></li>');

  $.ajax({
    url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
    type: "GET",                  // GET | POST | PUT | DELETE | PATCH
    data: {"user_id": user_id, "action":"getAllToDoList"},                  // 傳送資料到指定的 url
    dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
    beforeSend: function(){       // 在 request 發送之前執行
      $("ul.task_list").html('<li style="text-align: center;"><i class="fas fa-spinner fa-spin fa-3x"></i></li>');
    },
    success: function(data){      // request 成功取得回應後執行

      //console.log(data);

      let list_html = '';

      $.each(data, function(index, item){
        list_html += '<li data-id="' + item.item_id + '" data-star="' + item.star + '" data-sort="' + item.sort + '">';
        list_html +=   '<div class="item_flex">';
        list_html +=     '<div class="left_block">';
        list_html +=       '<div class="btn_flex">';
        list_html +=         '<button type="button" class="btn_up">往上</button>';
        list_html +=         '<button type="button" class="btn_down">往下</button>';
        list_html +=       '</div>';
        list_html +=     '</div>';
        list_html +=     '<div class="middle_block">';
        list_html +=       '<div class="star_block">';
        list_html +=         '<span class="star' + (item.star >= 1 ? " -on" : "") + '" data-star="1"><i class="fas fa-star"></i></span>';
        list_html +=         '<span class="star' + (item.star >= 2 ? " -on" : "") + '" data-star="2"><i class="fas fa-star"></i></span>';
        list_html +=         '<span class="star' + (item.star >= 3 ? " -on" : "") + '" data-star="3"><i class="fas fa-star"></i></span>';
        list_html +=         '<span class="star' + (item.star >= 4 ? " -on" : "") + '" data-star="4"><i class="fas fa-star"></i></span>';
        list_html +=         '<span class="star' + (item.star >= 5 ? " -on" : "") + '" data-star="5"><i class="fas fa-star"></i></span>';
        list_html +=       '</div>';
        list_html +=       '<p class="para">' + item.name + '</p>';
        list_html +=       '<input type="text" class="task_name_update -none" placeholder="更新待辦事項…" value="' + item.name + '">';
        list_html +=     '</div>';
        list_html +=     '<div class="right_block">';
        list_html +=       '<div class="btn_flex">';
        list_html +=         '<button type="button" class="btn_update">更新</button>';
        list_html +=         '<button type="button" class="btn_delete">移除</button>';
        list_html +=       '</div>';
        list_html +=     '</div>';
        list_html +=   '</div>';
        list_html += '</li>';

      });

      $("ul.task_list").html(list_html);


    },
    error: function(xhr, textStatus, errorThrown){         // request 發生錯誤的話執行
      console.log("error");
      console.log(xhr);
      //console.log(textStatus);
      //console.log(errorThrown);
    }
  });

}

function weather(){
  $.ajax({
    url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
    type: "GET",                  // GET | POST | PUT | DELETE | PATCH
    data: {"action": "getWeatherData"},                  // 傳送資料到指定的 url
    //processData: false,
    dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
    success: function(data){      // request 成功取得回應後執行
      //console.log(data);
      $("div.temperature").text(`${data.temperature}°C`);
      $("div.phenomenon").text(`${data.phenomenon}`);
      $("div.rain").text(`降雨機率 ${data.rain}%`);
    },
    error: function(xhr){         // request 發生錯誤的話執行
      console.log("error");
      console.log(xhr);
    }
  });

}


// 更新整體的排序
function reload_sort(){
  let sort_item = [];
  $("ul.task_list").children("li").each(function(i, item){
    $(this).attr("data-sort", (i + 1));
    sort_item.push({
      item_id: $(this).attr("data-id"),
      sort: $(this).attr("data-sort")
    });
  });
  //console.log(sort_item);
  sort_item = JSON.stringify(sort_item);

  $.ajax({
    url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
    type: "POST",                  // GET | POST | PUT | DELETE | PATCH
    data: {"action": "toDoListReload", "user_id": user_id, "data": sort_item},                  // 傳送資料到指定的 url
    //processData: false,
    dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
    beforeSend: function(){       // 在 request 發送之前執行
      $("article.task_container").append("<div class='temp_loading'><span><i class='fas fa-spinner fa-spin fa-5x'></i></span></div>");
    },
    success: function(data){      // request 成功取得回應後執行
      console.log(data);
    },
    error: function(xhr){         // request 發生錯誤的話執行
      console.log("error");
      console.log(xhr);
    },
    complete: function(){
      $("article.task_container").children("div.temp_loading").remove();
    }
  });

}

$(function(){
  weather();
  init();
  // ==== 待辦事項文字框的 focus 事件及 blur 事件觸發 ===== //
  $("input.task_name").on("focus", function(){
    $(this).closest("div.task_add_block").addClass("-on");
    //$("div.task_add_block").addClass("-on");
  });
  $("input.task_name").on("blur", function(){
    $(this).closest("div.task_add_block").removeClass("-on");
    //$("div.task_add_block").removeClass("-on");
  });

  // ==== text 欄位新增待辦事項 ===== //
  $("input.task_name").on("keyup", function(e){
    if(e.which == 13){ // 按下 Enter 鍵
      $("button.task_add").click();
    }
  });
  // 按下新增按鈕
  $("button.task_add").on("click", function(){

    let task_text = ($("input.task_name").val()).trim();
    if(task_text != ""){

      //let form_data = new FormData();
      //form_data.append("user_id", user_id);
      //form_data.append("name", task_text);

      if(!$(this).hasClass("-disabled")){
        let form_data = {
          "user_id": user_id,
          "name": task_text,
          "action": "addToDoList"
        };
        $.ajax({
          url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
          type: "POST",                  // GET | POST | PUT | DELETE | PATCH
          data: form_data,                  // 傳送資料到指定的 url
          dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
          beforeSend: function(){       // 在 request 發送之前執行
            $("button.task_add").addClass("-disabled");
          },
          success: function(data){      // request 成功取得回應後執行

            console.log(data);

            let list_html = "";

            list_html += '<li data-id="' + data.item_id + '" data-star="' + data.star + '" data-sort="' + data.sort + '">';
            list_html +=   '<div class="item_flex">';
            list_html +=     '<div class="left_block">';
            list_html +=       '<div class="btn_flex">';
            list_html +=         '<button type="button" class="btn_up">往上</button>';
            list_html +=         '<button type="button" class="btn_down">往下</button>';
            list_html +=       '</div>';
            list_html +=     '</div>';
            list_html +=     '<div class="middle_block">';
            list_html +=       '<div class="star_block">';
            list_html +=         '<span class="star" data-star="1"><i class="fas fa-star"></i></span>';
            list_html +=         '<span class="star" data-star="2"><i class="fas fa-star"></i></span>';
            list_html +=         '<span class="star" data-star="3"><i class="fas fa-star"></i></span>';
            list_html +=         '<span class="star" data-star="4"><i class="fas fa-star"></i></span>';
            list_html +=         '<span class="star" data-star="5"><i class="fas fa-star"></i></span>';
            list_html +=       '</div>';
            list_html +=       '<p class="para">' + data.name + '</p>';
            list_html +=       '<input type="text" class="task_name_update -none" placeholder="更新待辦事項…" value="' + data.name + '">';
            list_html +=     '</div>';
            list_html +=     '<div class="right_block">';
            list_html +=       '<div class="btn_flex">';
            list_html +=         '<button type="button" class="btn_update">更新</button>';
            list_html +=         '<button type="button" class="btn_delete">移除</button>';
            list_html +=       '</div>';
            list_html +=     '</div>';
            list_html +=   '</div>';
            list_html += '</li>';

            $("ul.task_list").prepend(list_html);
            $("input.task_name").val("");

            reload_sort();


          },
          error: function(xhr){         // request 發生錯誤的話執行
            console.log("error");
          },
          complete: function(){
            $("button.task_add").removeClass("-disabled");
          }
        });
      }

    }
  });
});

// ==== 移除待辦事項 ===== //
$("ul.task_list").on("click", "button.btn_delete", function(){
  let r = confirm("確認移除？")
  if (r){
    let item_id = $(this).closest("li").attr("data-id");
    let that = this;



    $.ajax({
      url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
      type: "POST",                  // GET | POST | PUT | DELETE | PATCH
      data: {"action": "delOneToDo","user_id": user_id, "item_id": item_id},                  // 傳送資料到指定的 url
      //processData: false,
      dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
      beforeSend: function(){       // 在 request 發送之前執行
      },
      success: function(data){      // request 成功取得回應後執行
        console.log(data); //回傳剩下的待辦事項資料

        if(data.msg == "delete success"){
          $(that).closest("li").animate({
            "opacity": 0
          }, 1000, "swing", function(){
            $(this).remove();
            reload_sort();
          });
        }else if(data.msg == "item id not found"){
          alert(data.msg);
        }

      },
      error: function(xhr){         // request 發生錯誤的話執行
        console.log("error");
        console.log(xhr);
      }
    });


  }
});
$("button.btn_empty").on("click", function(){
  let r = confirm("確認清空？")
  if (r){

    $.ajax({
      url: `${projectPath}/mem/MemberServlet.do`,      // 資料請求的網址
      type: "POST",                  // GET | POST | PUT | DELETE | PATCH
      data: {"action": "delAllToDoList","user_id": user_id},    // 傳送資料到指定的 url
      //processData: false,
      dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
      beforeSend: function(){       // 在 request 發送之前執行
      },
      success: function(data){      // request 成功取得回應後執行
        console.log(data); //回傳空陣列

        if(data.msg == "delete all success"){
          $("ul.task_list").children("li").animate({
            "opacity": 0
          }, 1000, "swing", function(){
            $(this).remove();
          });
        }

      },
      error: function(xhr){         // request 發生錯誤的話執行
        console.log("error");
        console.log(xhr);
      }
    });


  }
});

// ==== 更新待辦事項 ===== //
$("ul.task_list").on("click", "button.btn_update", function(){
  //console.log($(this).attr("data-edit"));
  if($(this).attr("data-updating") == "true"){ // 有 data-updating 就代表 ajax 正在傳輸中，資料正在更新中
    alert("資料更新中");
    return;
  }

  if($(this).attr("data-edit") == undefined){ // 進入編輯狀態
    $(this).attr("data-edit", true);
    $(this).closest("li").find("p.para").toggleClass("-none");
    $(this).closest("li").find("input.task_name_update").toggleClass("-none");
  }else{ // 進入檢視狀態
    let update_task_name = ($(this).closest("li").find("input.task_name_update").val()).trim();
    if(update_task_name == ""){
      alert("請輸入待辦事項");
    }else{

      $(this).attr("data-updating", true).html('<i class="fas fa-spinner fa-spin"></i>');
      let closest_li = $(this).closest("li");
      let that = this;

      $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
        type: "POST", // GET | POST | PUT | DELETE | PATCH
        data: {
          "action": "updateToDoList",
          user_id: user_id,
          item_id: $(closest_li).attr("data-id"),
          name: update_task_name,
          star: $(closest_li).attr("data-star"),
          sort: $(closest_li).attr("data-sort")
        },                  // 傳送資料到指定的 url
        //processData: false,
        dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
        beforeSend: function(){       // 在 request 發送之前執行
        },
        success: function(data){      // request 成功取得回應後執行
          console.log(data);

          if(data.msg == "item update success"){
            $(closest_li).find("p.para").html(update_task_name).toggleClass("-none");
            $(closest_li).find("input.task_name_update").val(update_task_name).toggleClass("-none");
            $(that).removeAttr("data-updating").removeAttr("data-edit").html("更新");
            alert("更新成功");
          }else{
            alert(data.msg);
          }

        },
        error: function(xhr){         // request 發生錯誤的話執行
          console.log("error");
          console.log(xhr);
        },
        complete: function(xhr){
        }
      });

    }
  }

});

// ==== 排序 ===== //
$("ul.task_list").on("click", "button.btn_up, button.btn_down", function(){

  // 往上
  if($(this).hasClass("btn_up") && !$(this).closest("li").is(':first-child')){
    let clone_html = $(this).closest("li").clone();
    $(this).closest("li").prev().before(clone_html);
    $(this).closest("li").remove();
    reload_sort();
  }

  // 往下
  if($(this).hasClass("btn_down") && !$(this).closest("li").is(':last-child')){
    let clone_html = $(this).closest("li").clone();
    $(this).closest("li").next().after(clone_html);
    $(this).closest("li").remove();
    reload_sort();
  }


});

// ==== 星號的重要性 ===== //
$("ul.task_list").on("click", "span.star", function(){
  let item_id = $(this).closest("li").attr("data-id");
  let current_star = parseInt($(this).attr("data-star"));


  //alert(item_id);
  //alert(current_star);
  var that = this;
  $.ajax({
    url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
    type: "POST",                  // GET | POST | PUT | DELETE | PATCH
    data: {"action": "updateStar", "user_id": user_id, "item_id": item_id, "star": current_star},                  // 傳送資料到指定的 url
    //processData: false,
    dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
    beforeSend: function(){       // 在 request 發送之前執行
      $(that).closest("div.star_block").append('<div class="temp_loading"><span><i class="fas fa-spinner fa-spin"></i></span></div>');
    },
    success: function(data){      // request 成功取得回應後執行
      console.log(data);

      if(data.msg == "star update success"){
        //alert("更新成功");
        $(that).closest("li").attr("data-star", data.star);
        $(that).closest("div.star_block").find("span.star").each(function(i, item){

          if( parseInt($(this).attr("data-star")) <= current_star ){
            $(this).addClass("-on");
          }else{
            $(this).removeClass("-on");
          }

        });

      }

    },
    error: function(xhr){         // request 發生錯誤的話執行
      console.log("error");
      console.log(xhr);
    },
    complete: function(xhr){
      $(that).closest("div.star_block").find("div.temp_loading").remove();
    }
  });

});
