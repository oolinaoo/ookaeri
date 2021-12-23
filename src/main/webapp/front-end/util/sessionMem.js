//ajax get login mem
$(function () {
	$.ajax({
	  url: "/okaeri/login/getMemSession",
	  type: "GET",
	  data: "",
	  dataType: "json",
	  success: function (data) {
		console.log(data);
	  },
	    error: function (xhr) {
	      console.log("error");
	    },
	});
});

