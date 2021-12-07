//================分頁================//
function paging(){
  $('#addr_table').after('<div id="nav"></div>');
  var rowsShown = 8;
  var rowsTotal = $('#addr_table tbody tr').length;
  var numPages = Math.ceil(rowsTotal / rowsShown);
  for (i = 0; i < numPages; i++) {
      var pageNum = i + 1;
      $('#nav').append('<a href="###" id="pageStyle" rel="' + i + '">' + "<span>" + pageNum + "</span>" + '</a> ');
  }
  $('#addr_table tbody tr').hide();
  $('#addr_table tbody tr').slice(0, rowsShown).show();
  $('#nav a:first').addClass('active');
  var $pagess = $("#pageStyle");
  $pagess[0].style.backgroundColor = "#B5495B";
  $pagess[0].style.color = "#ffffff";

  $('#nav a').bind('click', function () {

      $('#nav a').removeClass('active');
      $(this).addClass('active');
      $('#nav a').css('background-color', '').css('color', '');
      $(this).css('background-color', '#B5495B').css('color', '#ffffff');

      var currPage = $(this).attr('rel');
      var startItem = currPage * rowsShown;
      var endItem = startItem + rowsShown;
      $('#addr_table tbody tr').css('opacity', '0.0')
          .hide()
          .slice(startItem, endItem)
          .css('display', 'table-row').animate({ opacity: 1 }, 300);
  });

}



var overlay = `
<article>
    <button type="button" class="mem_btnModalClose">關閉</button>
    <form action="#" method="#" id="address_form">
        <table id="addr_modal_table">
            <tbody>
                <tr>
                    <th>地址編號</th>
                    <td>
                        <input type="text" class="addr_no" value="">
                    </td>
                </tr>
                <tr>
                    <th>棟號</th>
                    <td>
                        <input type="text" class="build_no" value="">
                    </td>
                </tr>
                <tr>
                    <th>樓號</th>
                    <td>
                        <input type="text" class="floor_no" value="">
                    </td>
                </tr>
                <tr>
                    <th>房號</th>
                    <td>
                        <input type="text" class="room_no" value="">
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="confirm_block"></div>
    </form>
</article>
`;

// 關閉 新增功能的燈箱
$("div.member_overlay").on("click", "button.mem_btnModalClose", function () {
  $(this).closest("div.member_overlay").fadeOut();
  //清空燈箱裡的article區塊
  $(this).closest("div.member_overlay").empty();
  //移除燈箱中的按鈕
  //$("div.member_overlay div.confirm_block").empty();
});

// 關閉 後來在地址資料表中增加的燈箱
$("#addr_table").on("click", "button.mem_btnModalClose", function () {
  $(this).closest("div.member_overlay").fadeOut();
  //清空燈箱裡的article區塊
  $(this).closest("div.member_overlay").empty();
});

/**********************************
 * address.html
 **********************************/

// 新增地址
$("a.newPost-button").on("click", function () {
    console.log("hello");
  //將article區塊放入燈箱中
  $("#member_overlay_add").prepend(overlay);
  let mem_btnConfirmAdd = `
    <button type="button" class="mem_btnConfirmAdd">確定新增</button>
    `;

  //將燈箱的表單的action設定為"insert"
  $("input.mem_btnConfirmAdd").closest("form").attr("action", "insert");
  //將「確定新增」的按鈕放入燈箱
  $("#member_overlay_add div.confirm_block").append(mem_btnConfirmAdd);
  $("#member_overlay_add").fadeIn();
});

// 確定新增？
$("div.member_overlay").on("click", "button.mem_btnConfirmAdd", function () {
  let r = confirm("確定新增？");
  if (r) {
    //將燈箱輸入的值放入陣列中
    let row_list = new Array(4);
    for (var i = 0; i <= 3; i++) {
      row_list[i] = $("#addr_modal_table").find("td").eq(i).children().val();
    }

    let member_table_html = `
        <tr>
          <td class="addrNo">${row_list[0]}</td>
          <td class="addrBuild">${row_list[1]}</td>
          <td class="addrFloor">${row_list[2]}</td>
          <td class="addrRoom">${row_list[3]}</td>
          <td class='del_edit_btn'>
            <i class='fa fa-minus-circle'></i> 
            <i class='fa fa-edit'></i> 
            <div class="member_overlay" style="border: 1px solid red;"></div>
          </td>
        </tr>
      `;

    //將新增的資料顯示在表格的資料列上
    $("#addr_table tbody").prepend(member_table_html);

    //燈箱消失
    $("#member_overlay_add").fadeOut();
    //清空燈箱裡的article區塊
    $("#member_overlay_add").empty();
    //移除「確定新增」的按鈕
    // $("div.member_overlay div.confirm_block").empty();
  }
});

//編輯
$("#addr_table").on("click", ".fa-edit", function () {
  //將article區塊放入 點擊編輯的那列資料 的燈箱中
  $(this).closest("td").children("div.member_overlay").prepend(overlay);
  //console.log($(this).closest("td").find("div.confirm_block").text());

  //將燈箱的表單的action設定為"update"
  $(this).closest("td").children("div.member_overlay").find("form").attr("action", "update");
 
  //將地址編號欄位設為不可編輯
  $(this).closest("td").children("div.member_overlay").find("input").eq(0).attr("disabled",true);

  //將「確定修改」的按鈕放入燈箱
  let mem_btnConfirmEdit = `
    <button type="button" class="mem_btnConfirmEdit">確定修改</button>
  `;
  // let mem_btnConfirmEdit = `
  //   <input type="submit" class="mem_btnConfirmEdit" value="確定修改">
  // `;
  $(this).closest("td").find("div.confirm_block").prepend(mem_btnConfirmEdit);
  $(this).closest("td").children("div.member_overlay").fadeIn();

  //點擊編輯的該筆資料，將該筆資料放入陣列
  let row_list = new Array(4);
  for (var i = 0; i <= 3; i++) {
    row_list[i] = $(this).closest("tr").children().eq(i).text();
  }

  //將點擊編輯的該筆資料放入燈箱的表單中
  //console.log($(this).closest("td").find("td").eq(0).children("input").val());
  for (var i = 0; i <= 3; i++) {
    $(this).closest("td").find("td").eq(i).children("input").val(row_list[i]);
  }
});

//確定修改?
$("#addr_table").on("click", "button.mem_btnConfirmEdit", function () {
  let r = confirm("確定修改？");
  if (r) {
    //從燈箱抓修改好的資料放入陣列
    let update_data = new Array(4);
    for (var i = 0; i <= 3; i++) {
        update_data[i] = $(this).closest("#address_form").find("td").eq(i).children("input").val()
    }

    //將修改好的資料放回該筆資料列
    for (var i = 0; i <= 3; i++) {
        $(this).closest("tr").children("td").eq(i).text(update_data[i]);
    }
    //燈箱消失
    $(this).closest("div.member_overlay").fadeOut();

    //清空燈箱裡的article區塊
    $(this).closest("div.member_overlay").empty();

  }
});

//移除
$("#addr_table").on("click", ".fa-minus-circle", function(){
  //要刪除的該筆資料的 地址編號
  let addrNo = $(this).closest("tr").find("td").eq(0).text();
  console.log(addrNo);
  let r = confirm("確認移除？");
  if (r) {
    
    $(this).closest("tr").fadeOut(500, function () {
      //console.log(this);
      $(this).remove();
    });
  }

});