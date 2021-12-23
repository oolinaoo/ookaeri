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
					<span hidden>${data.memName}</span>
				`
			);
	  },
	    error: function (xhr) {
	      console.log("error");
	    },
	});
});

