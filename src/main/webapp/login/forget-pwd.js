$("#memPwd_btn_submit").on("click", function() {
	
	let pwd1 = $("#newPwdOne").val();
	let pwd2 = $("#newPwdTwo").val();
	if (pwd2 == pwd1) {
		let form_data = {
				"memPwd" : pwd1
			};
		let pwd = JSON.stringify(form_data); // 將物件資料轉成字串
		console.log(pwd);
		$.ajax({
			url : "/okaeri/login/updatePassword",
			type : "POST",
			data : pwd,
			dataType : "json",
			contentType : "application/json;charset=UTF-8", // 告訴後端是用
			success : function(data) {
				console.log(data);
				if (data == true) {
					alert("修改成功，請再次登入");
					window.location = "/okaeri/login/login.html"
				}
				else {
					alert("請輸入符合規定的密碼");
				}
			},
			error : function(xhr) {
				console.log("error");
			}
		});
	} else {
		alert("新密碼與確認密碼不相符");
	}
});