//向後端請求未繳費資料
//json from servlet
$(function () {
	var dataUrl =
	  "/okaeri/payment/unPaid";
	var xhr = new XMLHttpRequest();
	xhr.open("GET", dataUrl, true);
	xhr.send();
	xhr.onload = function () {
	  var data = JSON.parse(this.responseText);
	  console.log(data);
	  for (var i = 0; i < data.length; i++) {
		if(data[i].payState == 0 ){
			data[i].payState = "未繳費";
		}else if(data[i].payState == 1){
			data[i].payState = "已繳費";
		} 
		$("table tbody").append(
				"<tr><td class='payNo' >" + data[i].payNo +"</td>"
				+"<td class='payPeriod' >" + data[i].payPeriod +"</td>"
				+"<td class='payAmount' >" + data[i].payAmount +"</td>"
				+"<td class='payDeadline'>" + data[i].payDeadline +"</td>" 
				+"<td class='payState' >" + data[i].payState +"</td>" 
				+"</tr>"
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
});