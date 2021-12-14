$(".forum-type").click(function(e) {
	document.getElementById("clear").innerHTML = "";
	$(".page").remove();
	let url =
	  "/okaeri/forumArticle/typeList";
	let type_data = {
		"forTypeNo" : e.currentTarget.value
	}
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-type", "application/json"); //告訴後端是用 JSON 格式
	let type = JSON.stringify(type_data); //將物件資料轉成字串
	console.log(type);
	xhr.send(type); //送出字串
	xhr.onload = function () {
	  let data = JSON.parse(this.responseText);
	  console.log(data);
	  for (let i = 0; i < data.length; i++) {
			  $(".forum-page").append(
				"<div class='forum-row' >" +
		          "<div class='forum-row-box' >" +
		            "<div class='forum-title'>" +
		              "<h3>" +
		                "<a href='../comment/comment.html' target='_blank'>" +
		                data[i].forArtTitle + "</a>" +
		              "</h3>" +
		            "</div>" +
		            "<div class='forum-content'>" +
		              "<p>" +
		                data[i].forArtContent +
		              "</p>" +
		            "</div>" +
		            "<hr />" +
		            "<div class='forum-icon'>" +
		              "<span><i class='fa fa-clock'></i>" +
		              	data[i].forArtPosttime +
		              "</span>&emsp;" +
		              "<span><i class='fa fa-eye'></i>" + 
		              	data[i].forArtView +
		              "</span>&emsp;" +
		              "<a href='../comment/comment.html'><i class='fa fa-comment-dots'></i>留言</a>&emsp;" +
		              "<a href='#'><i class='fa fa-times'></i>檢舉</a>" +
		            "</div>" +
		          "</div>" +
		        "</div>"
		);
	  }
	  var table = $(".forum-page");
	  var currentPage = 0; // 當前頁默認值為0
	  var pageSize = 5; // 每一頁顯示的數目
	  table.bind("paging", function () {
		table
		  .find(".forum-row")
		  .hide()
		  .slice(currentPage * pageSize, (currentPage + 1) * pageSize)
		  .show();
	  });
	  var sumRows = table.find(".forum-row").length;
	  var sumPages = Math.ceil(sumRows / pageSize); // 總頁數

	  var pager = $('<div class="page" id="clear"></div>'); // 新建div，放入a標簽,顯示底部分頁碼
	  for (var pageIndex = 0; pageIndex < sumPages; pageIndex++) {
		$(
		  '<a href="#" id="pageStyle" onclick="changCss(this)"><span>' +
			(pageIndex + 1) +
			"</span></a>"
		)
		  .bind("click", { newPage: pageIndex }, function (event) {
			currentPage = event.data["newPage"];
			table.trigger("paging");
			// 觸發分頁函數
		  })
		  .appendTo(pager);
		pager.append(" ");
	  }
	  pager.insertAfter(table);
	  table.trigger("paging");

	  // 默認第一頁的a標簽效果
	  var pagess = $("#pageStyle");
	  pagess[0].style.backgroundColor = "#B5495B";
	  pagess[0].style.color = "#ffffff";
	};
});