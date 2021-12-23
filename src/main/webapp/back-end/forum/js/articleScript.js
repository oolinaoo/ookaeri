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

//ARTICLE article ARTICLE article ARTICLE article ARTICLE article
//json from controller
$(function () {
  $.ajax({
    url: "/okaeri/forumArticle/artJoinTypeJoinMsg",
    type: "GET",
    data: "",
    dataType: "json",
    success: function (data) {
      console.log(data);
      let list_html = "";
      $.each(data, function (i, item) {
        list_html += `
						<tr>
						<td class='id'>${item.forArtNo}</td>
						<td class='type'>${item.type[0].forType}</td>
						<td class='title'>${item.forArtTitle}</td>
						<td class='content'>${item.forArtContent}</td>
						<td class='posttime'>${item.forArtPosttime}</td>
						<td class='edittime'>${item.forArtEdittime == null ? "-" : item.forArtEdittime}</td>
						<td class='mem'>${item.memAcct}</td>
						<td class='state'>${item.artStateNo}</td>
						<td class='modal-father'><a class='whole' href='#'>留言內容
							<div class='modal-returned hidden'>
							<div class='modal-container dataArray${i}'>
							<h2>${item.forArtTitle}</h2>
							<button class='btn--close-modal-returned'>&times;</button>
							<div class='article_content'>${item.forArtContent}
								<div style='text-align: right; font-size: 1em'>${item.memAcct}
									發布時間：${item.forArtPosttime} 編輯時間：${item.forArtEdittime == null ? "-" : item.forArtEdittime}
								</div>
							</div>
							<br /><br /><br />
							<h3>留言</h3>
							</div>
							</div>
						</a></td>
					</tr>
					`;
      });
      $("table tbody").append(list_html);
      $.each(data, function (i, item) {
        $.each(item.message, function (j, obj) {
          $(`.dataArray${i} h3`).after(
            `
				<div class='article_message'>
					<div>${obj.memAcct == null ? "無" : obj.memAcct}
					${obj.forMsgContent == null ? "" : obj.forMsgContent}
					<span style='float: right; font-size: 0.8em'>${obj.forMsgPosttime == null ? "" : obj.forMsgPosttime}</span>
					</div>
				</div>
			`
          );
        });
      });

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

//LightBox
//returned cases
//document.addEventListener("click", function(e){
$("table").click(function (e) {
  e.preventDefault();

  if (e.target.classList.contains("whole")) {
    console.log("1");
    let parent = e.target.closest(".modal-father");
    let modal = parent.querySelector(".modal-returned");
    let overlay = document.querySelector(".overlay-returned");
    let btnCloseModal = parent.querySelector(".btn--close-modal-returned");

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
