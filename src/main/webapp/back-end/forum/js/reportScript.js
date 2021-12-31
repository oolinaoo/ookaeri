// page
function changCss(obj) {
  var arr = document.getElementsByTagName("a");
  for (var i = 0; i < arr.length; i++) {
    if (obj == arr[i]) {
      // 當前頁樣式
      obj.style.backgroundColor = "#B5495B";
      obj.style.color = "#ffffff";
    } else {
      arr[i].style.color = "";
      arr[i].style.backgroundColor = "";
    }
  }
}



// REPORT report REPORT report REPORT report REPORT report
// json from controller
$(function () {
	$.ajax({
	  url: "/okaeri/forumReport/artJoinAll",
	  type: "GET",
	  data: "",
	  dataType: "json",
	  success: function (data) {
		console.log(data);
		let list_html = "";
		$("table tbody").append(
					`
						<tr>
						  <td class='id'>${data[0].forReptNo}</td>
						  <td class='artId'>${data[0].forArtNo}</td>
						  <td class='msgId'>${data[0].forMsgNoRept == null ? "-" : data[0].forMsgNoRept}</td>
						  <td class='reason'>${data[0].forReptContent}</td>
						  <td class='mem'>${data[0].memAcctRept}</td>
						  <td class='admin'>${data[0].adminAcct == null ? "審核中" : data[0].adminAcct}</td>
						  <td class='state' ondblclick='editState(this)'>${data[0].forReptState == 0 ? "檢舉不成立" : (data[0].forReptState == 1 ? "檢舉成功" : "審核中")}</td>
						  <td class='modal-father'><a class='whole' href='#'>檢舉原因
							  <div class='modal-returned hidden'>
							  <div class='modal-container'>
								<h2>檢舉原因</h2>
								<button class='btn--close-modal-returned'>&times;</button>
								<div class='report_content'>${data[0].forReptContent}
									<div style='text-align: right; font-size: 1em'>${data[0].memAcctRept}</div>
								</div>
								<br /><br /><br />
								<h3>${data[0].forArtTitle}</h3>
								<div class='article_content article_message'>
									<div>發文者：${data[0].memAcct}</div>
									${data[0].forArtContent}
									<div style='text-align: right; font-size: 0.8em'>${data[0].forArtPosttime}</div>
								</div>
								<h3 class='reptrept1'>留言</h3>
								<div class='article_message'>
									<div>留言編號：${data[0].forMsgNo}</div>
									<div>留言者：${data[0].memAcctMsg}</div>
									${data[0].forMsgContent}
									<div style='text-align: right; font-size: 0.8em'>${data[0].forMsgPosttime}</div>
								</div>
							  </div>
							  </div>
						  </a></td>
					  </tr>
					`
		);
		
		for (let i = 1; i < data.length; i++) {
			if (data[i].forReptNo != data[i-1].forReptNo) {
				$("table tbody").append(
								`
								<tr>
								<td class='id'>${data[i].forReptNo}</td>
								<td class='artId'>${data[i].forArtNo}</td>
								<td class='msgId'>${data[i].forMsgNoRept == null ? "-" : data[i].forMsgNoRept}</td>
								<td class='reason'>${data[i].forReptContent}</td>
								<td class='mem'>${data[i].memAcctRept}</td>
								<td class='admin'>${data[i].adminAcct == null ? "審核中" : data[i].adminAcct}</td>
								<td class='state' ondblclick='editState(this)'>${data[i].forReptState == 0 ? "檢舉不成立" : (data[i].forReptState == 1 ? "檢舉成功" : "審核中")}</td>
								<td class='modal-father'><a class='whole' href='#'>檢舉原因
									<div class='modal-returned hidden'>
									<div class='modal-container'>
										<h2>檢舉原因</h2>
										<button class='btn--close-modal-returned'>&times;</button>
										<div class='report_content'>${data[i].forReptContent}
											<div style='text-align: right; font-size: 1em'>${data[i].memAcctRept}</div>
										</div>
										<br /><br /><br />
										<h3>${data[i].forArtTitle}</h3>
										<div class='article_content article_message'>
											<div>發文者：${data[i].memAcct}</div>
											${data[i].forArtContent}
											<div style='text-align: right; font-size: 0.8em'>${data[i].forArtPosttime}</div>
										</div>
										<h3 class='reptrept${data[i].forReptNo}'>留言</h3>
										<div class='article_message'>
											<div>留言編號：${data[i].forMsgNo}</div>
											<div>留言者：${data[i].memAcctMsg}</div>
											${data[i].forMsgContent}
											<div style='text-align: right; font-size: 0.8em'>${data[i].forMsgPosttime}</div>
										</div>
									</div>
									</div>
								</a></td>
								</tr>
								`
				);
			} else {
				$(`.reptrept${data[i].forReptNo}`).after( 
					`
						<div class='article_message'>
							<div>留言編號：${data[i].forMsgNo}</div>
					  		<div>留言者：${data[i].memAcctMsg}</div>
					  		${data[i].forMsgContent}
					  		<div style='text-align: right; font-size: 0.8em'>${data[i].forMsgPosttime}</div>
					  	</div>
					`
				);
			}
		};
  
		var table = $("table");
		var currentPage = 0; // 當前頁默認值為0
		var pageSize = 15; // 每一頁顯示的數目
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
	  },
	  error: function (xhr) {
		console.log("error");
	  },
	});
  });

$("table").click(function(e){

	e.preventDefault();

	if (e.target.classList.contains("whole")) {
		console.log("1");
		let parent = e.target.closest(".modal-father");
		let modal = parent.querySelector(".modal-returned");
		let overlay = document.querySelector(".overlay-returned");
		let btnCloseModal = parent.querySelector(
			".btn--close-modal-returned"
		);
		
		modal.classList.remove("hidden");
		overlay.classList.remove("hidden");
		
		const closeModal = function () {
			modal.classList.add("hidden");
			overlay.classList.add("hidden");
		};
		btnCloseModal.addEventListener("click", closeModal);
		overlay.addEventListener("click", closeModal);

		document.addEventListener("keydown", function (e) {
		if (e.key === "Escape" && !modal.classList.contains("hidden")) {
			closeModal();
		}
		});
	} 
});

//double click edit
function editState(e) {
	select = 
		`
		<div class="inputStyle editStateOnly">
			<select id="editState" onchange="saveState(this)" required style="width: inherit;"> 
			 	<option value="" disabled selected>狀態</option>
				<option value="0">檢舉不成立</option>
				<option value="1">檢舉成功</option>
				<option value="2">審核中</option>
			</select>
		 </div>
		`
	$(e).html(select);
}

// select change event
function saveState(e) {
	let svalue = $(e).val();
	let row = $(e).parent().parent().parent();
	$(e).parent().parent().html(`${svalue == 0 ? "檢舉不成立" : (svalue == 1 ? "檢舉成功" : "審核中") }`);
	$(row).find(".admin").html($('.menu span span span').html());
	$(function() {
		const reptId = $(row).find(".id").html();
		const admin = $('.menu span span span').html();
		const state = `${$(row).find(".state").html() == "檢舉不成立" ? "0" : ($(row).find(".state").html() == "檢舉成功" ? "1" : "2")}`;
		let form_data = {
			"forReptNo" : reptId,
			"adminAcct" : admin,
			"forReptState" : state
		};
		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log(xhr);
			}
		};
		xhr.open("POST", "/okaeri/forumReport/updateState"); // post 告知後端
		xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用JSON格式
		let data = JSON.stringify(form_data); // 將物件資料轉成字串
		console.log(data);
		xhr.send(data); // 送出字串
	})
	if ($(row).find(".msgId").html() == "-") {
		$(function() {
			const artId = $(row).find(".artId").html();
			const state = `${$(row).find(".state").html() == "檢舉成功" ? "1" : "0"}`;
			let form_data = {
				"forArtNo" : artId,
				"artStateNo" : state
			};
			let xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					console.log(xhr);
				}
			};
			xhr.open("POST", "/okaeri/forumArticle/updateState"); // post 告知後端
			xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用JSON格式
			let data = JSON.stringify(form_data); // 將物件資料轉成字串
			console.log(data);
			xhr.send(data); // 送出字串
		})
	} else {
		$(function() {
			const msgId = $(row).find(".msgId").html();
			const state = `${$(row).find(".state").html() == "檢舉成功" ? "1" : "0"}`;
			let form_data = {
				"forMsgNo" : msgId,
				"forMsgState" : state
			};
			let xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					console.log(xhr);
				}
			};
			xhr.open("POST", "/okaeri/forumMessage/updateState"); // post 告知後端
			xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用JSON格式
			let data = JSON.stringify(form_data); // 將物件資料轉成字串
			console.log(data);
			xhr.send(data); // 送出字串
		})
	}
}