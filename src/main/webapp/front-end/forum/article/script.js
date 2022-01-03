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
// json from controller
$(function () {
  let url = "/okaeri/forumArticle/listAll";
  let xhr = new XMLHttpRequest();
  xhr.open("GET", url);
  xhr.send();
  xhr.onload = function () {
    let data = JSON.parse(this.responseText);
    console.log(data);
    for (let i = 0; i < data.length; i++) {
      if (data[i].artStateNo != 1) {
        $(".forum-page").append(
          "<div class='forum-row' >" +
            "<div class='forum-row-box' >" +
            "<div class='forum-title'>" +
            "<h3>" +
            "<a onclick='view(this)' href='../comment/comment.html?forArtNo=" +
            data[i].forArtNo +
            "'>" +
            data[i].forArtTitle +
            "</a>" +
            "</h3>" +
            "<span style='float:right;'><a href='#' id='forum-heart' onclick='collection(this)'><i class='fa fa-heart dataHeart" + data[i].forArtNo + "'></i></a>" +
            "</span>" +
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
            "</span>" +
            "<span id='icon-eye'>" +
            data[i].forArtView +
            "</span>&emsp;" +
            "<a href='../comment/comment.html?forArtNo=" +
            data[i].forArtNo +
            "'>" +
            "<i class='fa fa-comment-dots'></i>留言</a>&emsp;" +
            "<span id='icon-eye-artNo' class='dataArray" + data[i].forArtNo + "' hidden>" +
            data[i].forArtNo +
            "</span>" +
            "</div>" +
            "</div>" +
            "</div>"
        );
        $.ajax({
    	    url: "/okaeri/forumCollections/findByMem",
    	    type: "POST",
    	    data: "",
    	    dataType: "json",
    	    contentType : 'application/json;charset=UTF-8',
    	    success: function (hdata) {
    	      $.each(hdata, function (k, item) {
    				if(`${item.forArtNo}` == data[i].forArtNo) {
    					$(`.dataHeart${data[i].forArtNo}`).css("color", "rgb(123, 20, 20)")
    				}
    			});
    	    },
    	    error: function (xhr) {
    	      console.log("error");
    	    },
    	  });
      }
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

// LightBox
// returned cases //
const modalReturned = document.querySelector(".modal-returned");
const overlayReturned = document.querySelector(".overlay-returned");
const btnCloseModalReturned = document.querySelector(
  ".btn--close-modal-returned"
);
const btnsOpenModalReturned = document.querySelectorAll(".forum-button");

// returned cases //
const openModalReturned = function (e) {
  e.preventDefault();
  modalReturned.classList.remove("hidden");
  overlayReturned.classList.remove("hidden");
};

const closeModalReturned = function () {
  modalReturned.classList.add("hidden");
  overlayReturned.classList.add("hidden");
};

btnsOpenModalReturned.forEach((btn) =>
  btn.addEventListener("click", openModalReturned)
);
btnCloseModalReturned.addEventListener("click", closeModalReturned);
overlayReturned.addEventListener("click", closeModalReturned);

document.addEventListener("keydown", function (e) {
  if (e.key === "Escape" && !modalReturned.classList.contains("hidden")) {
    closeModalReturned();
  }
});

// New Post
if (typeof Storage !== "undefined") {
  $(".editor").keypress(function () {
    $(this).find(".saved").detach();
  });
  $(".editor").html(localStorage.getItem("wysiwyg"));

  $('button[data-func="save"]').click(function () {
    $content = $(".editor").html();
    localStorage.setItem("wysiwyg", $content);
    $(".editor")
      .append('<span class="saved"><i class="fa fa-check"></i></span>')
      .fadeIn(function () {
        $(this).find(".saved").fadeOut(500);
      });
  });

  $('button[data-func="clear"]').click(function () {
    $(".editor").val("");
    localStorage.removeItem("wysiwyg");
  });
}

// Ajax to create
$(".post").on("click", function () {
  var mem = $("#navbar_profile_memAcct_span").html();
  var type = $("#newpost-type").val();
  var title = $("#newpost-title").val();
  var content = $("#newpost-content").val();
  let form_data = {
    memAcct: mem,
    forTypeNo: type,
    forArtTitle: title,
    forArtContent: content,
  };
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      console.log(xhr);
    }
  };
  xhr.open("POST", "/okaeri/forumArticle/add"); // post 告知後端
  xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用
  // JSON
  // 格式
  var data = JSON.stringify(form_data); // 將物件資料轉成字串
  console.log(data);
  xhr.send(data); // 送出字串
  alert("發文成功");
  $(".editor, #newpost-title").val("");
  $(".modal-returned, .overlay-returned").fadeOut();
});

// Ajax to update views
function view(obj) {
  let view = $(obj).parent().parent().parent().find(".forum-icon").find("#icon-eye").html();
  let viewInt = parseInt(view) + 1;
  let artNo = $(obj).parent().parent().parent().find(".forum-icon").find("#icon-eye-artNo").html();
  let form_data = {
    forArtView: viewInt,
    forArtNo: artNo,
  };
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      console.log(xhr);
    }
  };
  xhr.open("POST", "/okaeri/forumArticle/updateViews"); // post 告知後端
  xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用
  // JSON
  // 格式
  var data = JSON.stringify(form_data); // 將物件資料轉成字串
  console.log(data);
  xhr.send(data); // 送出字串
}