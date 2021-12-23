//向後端請求包裹資料
$(function () {
	console.log("請求包裹資料");
	refresh();	
	
});
//向後端請求包裹資料
function refresh() {
  $("table tbody").empty();
  $("div.page").remove();
  console.log("重整");
  var dataUrl =
	  "/okaeri/pack/memListReceived";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (var i = 0; i < data.length; i++) {
		  if(data[i].packReceived == null){
			  data[i].packReceived = ""; 
		  }
		  if(data[i].packTypeNo == 0){
			  data[i].packTypeNo = "包裹";
		  }else{
			  data[i].packTypeNo = "信件";
		  }
		  if(data[i].packState == 0){
			  data[i].packState = "未領取"
		  }else{
			  data[i].packState = "已領取"
		  }
		$("table tbody").append(
				"<tr><td class='packNo' name='packNo' contenteditable='false'>" + 
				data[i].packNo +
				"</td><td class='packArrived' name='packArrived'  contenteditable='false'>" +
				data[i].packArrived +
				"</td><td class='packReceived' name='packReceived'  contenteditable='false'>" +
				data[i].packReceived +
				"</td><td class='packLogistics' name='packLogistics'  contenteditable='false'>" +
				data[i].packLogistics +
				"</td><td class='packTypeNo' name='packTypeNo'  contenteditable='false'>" +
				data[i].packTypeNo +
				"</td><td class='packState' name='packState'  contenteditable='false'>" +
				data[i].packState 
		);
			
	  }
	  var table = $("table");
	  var currentPage = 0; // 當前頁默認值為0
	  var pageSize = 8; // 每一頁顯示的數目
	  table.bind("paging", function () {
		table
		  .find("tbody tr")
		  .hide()
		  .slice(currentPage * pageSize, (currentPage + 1) * pageSize)
		  .show();
	  });
	  var sumRows = table.find("tbody tr").length;
	  var sumPages = Math.ceil(sumRows / pageSize); // 總頁數

	  var pager = $('<div class="page"></div>'); // 新建div，放入a標簽,顯示底部分頁碼
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
} 