// json to servlet add
$(".save")
.on(
		"click",
		function() {
			const admin = $('.menu span span span').html();
			const type = $("#type").val();
			const title = $("#title").val();
			const content = $("#content").val();
			let form_data = {
				"adminAcct" : admin,
				"newsTypeNo" : type,
				"newsTitle" : title,
				"newsContent" : content
			};
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
    			if (this.readyState == 4 && this.status == 200) {
    				console.log(xhr);
    			}
    		};
			xhr.open("POST", "/okaeri/news/add"); //post 告知後端
			xhr.setRequestHeader("Content-type", "application/json"); //告訴後端是用 JSON 格式
			var data = JSON.stringify(form_data); //將物件資料轉成字串
			console.log(data);
			xhr.send(data); //送出字串
			alert("新增成功");
			$("#admin, #title, #content").val("");
			$(".modal-returned, .overlay-returned").fadeOut();
		});