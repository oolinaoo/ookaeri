//ajax get login mem
$(function () {
	$.ajax({
	  url: "/okaeri/login/getMemSession",
	  type: "GET",
	  data: "",
	  dataType: "json",
	  success: function (data) {
		console.log(data);
		$("#navbar_profile_memAcct_span").append(
				`
					${data.memAcct}
				`
			);
		$("#navbar_profile_memAcct_span").after(
				`
					<span hidden>${data.memName}</span>
				`
			);
	  },
	    error: function (xhr) {
	      console.log("error");
	    },
	});
});

//ajax to logout
$("#profile_logout").on("click", function()  {
	console.log("enter");
	let url = "/okaeri/login/logout";
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send(); 
	xhr.onload = function() {
		let data = JSON.parse(this.responseText);
		console.log(data);
		 if (data == true) {
		 window.location = "/okaeri/login/login.html"
		 }
	};
});