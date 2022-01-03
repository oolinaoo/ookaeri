//ajax get login admin
$(function () {
	$.ajax({
	  url: "/okaeri/login/getAdminSession",
	  type: "GET",
	  data: "",
	  dataType: "json",
	  success: function (data) {
		console.log(data);
		$(".menu span span").append(
			`
				Hi ${data.adminName}
				<span hidden>${data.adminAcct}</span>
			`
		);
	  },
	    error: function (xhr) {
	      console.log("error");
	    },
	});
});

//ajax to logout
$("#logout").on("click", function()  {
	console.log("enter");
	let url = "/okaeri/login/logout";
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send(); 
	xhr.onload = function() {
		let data = JSON.parse(this.responseText);
		console.log(data);
		 if (data == true) {
		 alert("您已登出");
		 window.location = "/okaeri/login/login.html"
		 }
	};
});

