//Ajax to heart forum article
function collection(obj) {
	let heartColor = $(obj).find(".fa-heart").css("color");
	let mem = $("#navbar_profile_memAcct_span").html();
	  let artNo = $(obj).parent().parent().parent().find(".forum-icon").find("#icon-eye-artNo").html();
	  let form_data = {
	    memAcct: mem,
	    forArtNo: artNo,
	  };
	  console.log(form_data);
	  let data = JSON.stringify(form_data);
	if ( heartColor == "rgb(255, 255, 255)") {
		$.ajax({
		    url: "/okaeri/forumCollections/add",
		    type: "POST",
		    data: data,
		    dataType: "json",
		    contentType : 'application/json;charset=UTF-8',
		    success: function (data) {
		      console.log(data);
		    },
		    error: function (xhr) {
		      console.log("error");
		    },
		  });
		alert("加入收藏");
		$(obj).find(".fa-heart").css("color", "rgb(123, 20, 20)");
	} else {
		$.ajax({
		    url: "/okaeri/forumCollections/delete",
		    type: "POST",
		    data: data,
		    dataType: "json",
		    contentType : 'application/json;charset=UTF-8',
		    success: function (data) {
		      console.log(data);
		    },
		    error: function (xhr) {
		      console.log("error");
		    },
		  });
		alert("取消收藏");
		$(obj).find(".fa-heart").css("color", "rgb(255, 255, 255)");
	}
}