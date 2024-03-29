
//向後端請求訊息通知
$(function () {
	refreshNotify();
}); 
//向後端請求資料
function refreshNotify() {
	$(".dropdown_selector").empty();
  console.log("重整訊息通知");
 var dataUrl =
	  "/okaeri/notify/listNotify";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  if(data != ""){
		  for (var i = 0; i < data.length; i++) {
			  
			$("div.dropdown_selector").prepend(
					'<div class="box"><a class="box" style="display: inline-block" href="#">'+
	                  data[i]+
	                '</a></div>'
			);
				
		  }
		  $("div.dropdown_selector").prepend(
					'<button class="delete_notify">X</button>'
		  );
		  $(" body div.dropdown_selector div:nth-child(odd)").addClass("color");
	  }else{
	      $("div.dropdown_selector").prepend(
					'<h3 class="nomessage">No Message</h3>'+
					'<img class="nomessage" src="/okaeri/front-end/pack/images/no-message.png">'
		  );		
	   }	
	};
} 

(function (document) { 
	 /*訊息通知下拉*/ 
   $(".dropbtn").on("click", function () {
    if (!$(".dropdown-content").hasClass("view")) {
      $(".dropdown-content").addClass("view");
    } else {
      $(".dropdown-content").removeClass("view");
    }
	console.log("我來刪東西囉");
    $("div#badge").attr("style","display:none;");
   /*未讀消息變更為已讀*/
  var dataUrl =
	  "/okaeri/notify/updateNotifyState";
  var xhr = new XMLHttpRequest();
  xhr.open("GET", dataUrl);
  xhr.send();
  xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  $("#badge").html(data);
	};	
  }); 
  $("div.dropdown-content").on("mouseleave", function () {
    $(".dropdown-content").removeClass("view");
  });
  $(" body div.dropdown_selector div:nth-child(odd)").addClass("color");
  
  
})(document); 

$(function () {
	//未讀訊息數量
	var dataUrl =
	  "/okaeri/notify/countNotifyState";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  if(data > 0){
		$("#badge").css("display","inline-block")
		$("#badge").html(data);
	  }else{
		$("#badge").css("display","none")
	}
	  
	};
	/*刪除所有訊息通知*/
	$("body").on("click","button.delete_notify", function () {
	  console.log("有進入刪除");
	  var con = confirm("確定刪除全部訊息?");
	  if(con == true){
		  var dataUrl =
			  "/okaeri/notify/deleteNotify";
		  var xhr = new XMLHttpRequest();
		  xhr.open("GET", dataUrl);
		  xhr.send();
		  xhr.onload = function () {
		  var data = JSON.parse(this.responseText);
		  console.log(data);
		  refreshNotify();
		  };
	  }	
	  
    }); 
	
}); 
	