//json get mem photo
$(function(){
	$.ajax({
		url: "/okaeri/login/getUserPhoto",
	    type: "GET",
	    data: "",
	    dataType: "json",
	    success: function (data) {
	      console.log(data);
	      $("#navbar_profile_memAcct").prepend(
	      `
	    		<img class="profile_photo" src="data:image/jpeg;base64,${data.memPhoto}" alt="user" />
	      `
	      );
	    },
	    error: function (xhr) {
	        console.log("error");
	    },
		
	});
	$.ajax({
		  url: "/okaeri/login/getMemSession",
		  type: "GET",
		  data: "",
		  dataType: "json",
		  success: function (data) {
			console.log(data);
			$("#navbar_profile_memAcct").append(
					`
						<span id="navbar_profile_memAcct_span">${data.memAcct}</span>
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

//ajax get login mem
$(function () {
	
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
		 alert("您已登出");
		 window.location = "/okaeri/login/login.html"
		 }
	};
});