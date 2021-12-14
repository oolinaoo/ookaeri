//================載入所有資料================//
function init(){

    $.ajax({
      url: "http://localhost:8081/Okaeri/back-end/acct-addr/AddrAjaxServlet.do",           // 資料請求的網址
      type: "GET",                  // GET | POST | PUT | DELETE | PATCH
      data: {"action": "listAll"},                  // 傳送資料到指定的 url
      dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
      success: function(data){      // request 成功取得回應後執行
  
        //console.log(data);
  
        let list_html = '';
  
        $.each(data, function(index, item){
          list_html += `
            <tr>
              <td class='addrNo'>${item.addrNo}</td>
              <td class='addrBuild'>${item.addrBuild}</td>
              <td class='addrFloor'>${item.addrFloor}</td>
              <td class='addrRoom'>${item.addrRoom}</td>
              <td class='del_edit_btn'>
                  <i class='fa fa-minus-circle'></i> 
                  <i class='fa fa-edit'></i> 
                  <div class="member_overlay" style="border: 1px solid red;"></div>
              </td>
          </tr>
          `;
        });
  
        $("#addr_table tbody").append(list_html);
        paging(); //呼叫分頁函式
      },
      error: function(xhr, textStatus, errorThrown){    // request 發生錯誤的話執行
        console.log("error");
        console.log(xhr);
        //console.log(textStatus);
        //console.log(errorThrown);
      }
    });
  
}
  
//================呼叫載入所有資料的函式================//
$(function(){
    init();
});
  
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
    //console.log("hello");
    //將article區塊放入燈箱中
    $("#member_overlay_add").prepend(overlay);
    let mem_btnConfirmAdd = `
        <button type="button" class="mem_btnConfirmAdd">確定新增</button>
        `;

    //將燈箱的表單的action設定為"insert"
    $("input.mem_btnConfirmAdd").closest("form").attr("action", "insert");
    //將「確定新增」的按鈕放入燈箱
    $("#member_overlay_add div.confirm_block").append(mem_btnConfirmAdd);

    //將「地址編號」欄位拿掉
    $("#member_overlay_add").find("tr").eq(0).remove();

    $("#member_overlay_add").fadeIn();
});
  
// 確定新增？
$("div.member_overlay").on("click", "button.mem_btnConfirmAdd", function () {
    let r = confirm("確定新增？");
    if (r) {
        // 若使用者再次輸入錯誤，要將燈箱中的錯誤訊息區塊移除，後面才可再添加新的錯誤訊息區塊
        $("div.error_block").remove();

        //將燈箱輸入的值放入陣列中 //棟號、樓號、房號
        let row_list = new Array(3);

        for (var i = 0; i <= 2; i++) {
            let item = $("#addr_modal_table").find("td").eq(i).children().val();
            row_list[i] = item.trim();
        }

        // for (var i = 0; i <= 3; i++) {
        //     row_list[i] = $("#addr_modal_table").find("td").eq(i).children().val();
        // }

        //正規表達式驗證
        let n = 0;
        let html_list = "";
        for (var i = 0; i <= 2; i++) {
            if(i == 0){
                let re = /^[A-Z]{1}$/;
                if( re.test( row_list[i]) ){
                    n+=1;
                }else{
                    html_list += `
                        <div class="error_msg">棟號：不可空白，需輸入大寫英文字母，且長度為1</div>
                    `;
                }
            }else if(i == 1){
                let re = /^[1-5]{1}$/;
                if( re.test( row_list[i]) ){
                    n+=1;
                }else{
                    html_list += `
                        <div class="error_msg">樓號：不可空白，需輸入數字1~5，且長度為1</div>
                    `;
                }
            }else if(i == 2){
                let re = /^[1-5]{1}$/;
                if( re.test( row_list[i]) ){
                    n+=1;
                }else{
                    html_list += `
                        <div class="error_msg">房號：不可空白，需輸入數字1~5，且長度為1</div>
                    `;
                }
            }

        }

        let error_block = `<div class="error_block">${html_list}</div>`;

        //如果以上有任何一個驗證錯誤，n就不等於3
        if(n != 3){
            //將錯誤訊息加入燈箱中
            $("#addr_modal_table").prepend(error_block);
        }else{
            //將陣列中的值包成JSON格式
            let addr_item= { 
                "addrBuild": row_list[0],
                "addrFloor": row_list[1],
                "addrRoom": row_list[2]
            };
            addr_item = JSON.stringify(addr_item);
            //console.log(addr_item);
            $.ajax({
                url:"http://localhost:8081/Okaeri/back-end/acct-addr/AddrAjaxServlet.do",
                type:"POST",
                data:{"action" : "insert", 
                      "addr": addr_item
                     },
                dataType:"json",
                success: function(data){
                    if(data.msg == "success"){
                        let member_table_html = `
                            <tr>
                            <td class="addrNo">${data.addrNo}</td>
                            <td class="addrBuild">${data.addrBuild}</td>
                            <td class="addrFloor">${data.addrFloor}</td>
                            <td class="addrRoom">${data.addrRoom}</td>
                            <td class='del_edit_btn'>
                                <i class='fa fa-minus-circle'></i> 
                                <i class='fa fa-edit'></i> 
                                <div class="member_overlay" style="border: 1px solid red;"></div>
                            </td>
                            </tr>
                        `;
                        //將新增的資料顯示在表格的資料列上（新增在表格第一頁的第一列）
                        $("#addr_table tbody").prepend(member_table_html);
                        //燈箱消失
                        $("#member_overlay_add").fadeOut();
                        //清空燈箱裡的article區塊
                        $("#member_overlay_add").empty();
                        //將原本的分頁區塊去除
                        $("#nav").remove(); 
                        paging(); //再呼叫分頁函式
                        $("#nav a.active").click(); //因為資料是新增在表格的第一列，所以要點回到分頁的第一頁去看
                    }else if(data.msg == "fail"){
                        alert("新增失敗，資料未成功新增到資料庫！");
                        //燈箱消失
                        $("#member_overlay_add").fadeOut();
                        //清空燈箱裡的article區塊
                        $("#member_overlay_add").empty();
                    }else if(data.msg == "overlap"){
                        let error_overlap = `<div class="error_block">此棟號、樓號、房號已重複！</div>`;
                        $("#addr_modal_table").prepend(error_overlap);

                    }

                },
                error: function(xhr){         // request 發生錯誤的話執行
                    console.log("error");
                },
    
            });

        }

    }
});

//放置原本該筆資料列的原始資料
var original_list = Array(4);
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
    for (var i = 0; i <= 3; i++) {
        original_list[i] = $(this).closest("tr").children().eq(i).text();
    }

    //將點擊編輯的該筆資料放入燈箱的表單中
    //console.log($(this).closest("td").find("td").eq(0).children("input").val());
    for (var i = 0; i <= 3; i++) {
        $(this).closest("td").find("td").eq(i).children("input").val(original_list[i]);
    }
});
  
//確定修改?
$("#addr_table").on("click", "button.mem_btnConfirmEdit", function () {
    let r = confirm("確定修改？");
    if (r) {

        // 若使用者再次輸入錯誤，要將燈箱中的錯誤訊息區塊移除，後面才可再添加新的錯誤訊息區塊
        $("div.error_block").remove();

        //從燈箱抓修改好的資料放入陣列  //地址編號（不可編輯）、棟號、樓號、房號
        let that = $(this);
        let update_data = new Array(4);
        for (var i = 0; i <= 3; i++) {
            let item = that.closest("#address_form").find("td").eq(i).children("input").val();
            update_data[i] = item.trim();
        }

        //如果 使用者修改後的資料 跟 原本的資料 一樣，則直接將燈箱關閉，結束程式
        let count = 0;
        for (var i = 1; i <= 3; i++) {
            if( original_list[i] === update_data[i] ){
                count++;
            }
        }
        console.log(count);
        if(count === 3){
            //燈箱消失
            that.closest("div.member_overlay").fadeOut();
            //清空燈箱裡的article區塊
            that.closest("div.member_overlay").empty();
            let msg = `地址編號${update_data[0]} 修改成功`;
            alert(msg);
            //console.log("hello1")
            return; //結束程式
        }
        //console.log("hello2");

        //正規表達式驗證
        let n = 0;
        let html_list = "";
        for (var i = 0; i <= 3; i++) {
            if(i == 1){
                let re = /^[A-Z]{1}$/;
                if( re.test( update_data[i]) ){
                    n += 1;
                }else{
                    html_list += `
                        <div class="error_msg">棟號：不可空白，需輸入大寫英文字母，且長度為1</div>
                    `;
                }
            }else if(i == 2){
                let re = /^[1-9]{1,2}$|^[1][0]{1,2}$/;
                if( re.test( update_data[i]) ){
                    n += 1;
                }else{
                    html_list += `
                        <div class="error_msg">樓號：不可空白，需輸入數字1~100</div>
                    `;
                }
            }else if(i == 3){
                let re = /^[1-9]{1,2}$|^[1][0]{1,2}$/;
                if( re.test( update_data[i]) ){
                    n += 1;
                }else{
                    html_list += `
                        <div class="error_msg">房號：不可空白，需輸入數字1~100</div>
                    `;
                }
            }

        }


        let error_block = `<div class="error_block">${html_list}</div>`;

        //如果以上有任何一個驗證錯誤，n就不等於3
        if(n != 3){
            //將錯誤訊息加入燈箱中
            $("#addr_modal_table").prepend(error_block);
        }else{
            //將陣列中的值包成JSON格式
            let addr_item= { 
                "addrNo":update_data[0],
                "addrBuild": update_data[1],
                "addrFloor": update_data[2],
                "addrRoom": update_data[3]
            };
            addr_item = JSON.stringify(addr_item);
            //console.log(addr_item);
            $.ajax({
                url:"http://localhost:8081/Okaeri/back-end/acct-addr/AddrAjaxServlet.do",
                type:"POST",
                data:{"action" : "update", 
                      "addr": addr_item
                     },
                dataType:"json",
                success: function(data){
                    if(data.msg == "success"){
                        let return_list = [data.addrNo, data.addrBuild, data.addrFloor, data.addrRoom];
                        //將修改好的資料放回該筆資料列
                        for (var i = 0; i <= 3; i++) {
                            that.closest("tr").children("td").eq(i).text(return_list[i]);
                        }

                        let msg = `地址編號${data.addrNo} 修改成功！`;
                        alert(msg);

                        //燈箱消失
                        that.closest("div.member_overlay").fadeOut();

                        //清空燈箱裡的article區塊
                        that.closest("div.member_overlay").empty();

                    }else if(data.msg == "fail"){
                        alert("修改失敗，資料未成功更新到資料庫！");
                        //燈箱消失
                        that.closest("div.member_overlay").fadeOut();
                        //清空燈箱裡的article區塊
                        that.closest("div.member_overlay").empty();
                    }else if(data.msg == "overlap"){
                        let error_overlap = `<div class="error_block">此棟號、樓號、房號已重複！</div>`;
                        $("#addr_modal_table").prepend(error_overlap);

                    }

                },
                error: function(xhr){         // request 發生錯誤的話執行
                    console.log("error");
                },
    
            });

        }

    }
});
  
//移除
$("#addr_table").on("click", ".fa-minus-circle", function(){
    //要刪除的該筆資料的 地址編號
    let that = $(this);
    let addrNo = that.closest("tr").find("td").eq(0).text();
    //console.log(addrNo);
    let r = confirm("確認移除？");
    if (r) {
        $.ajax({
            url:"http://localhost:8081/Okaeri/back-end/acct-addr/AddrAjaxServlet.do",
            type:"POST",
            data:{"action" : "delete", "addrNo": addrNo},
            dataType:"json",
            success: function(data){
                if(data.msg == "success"){
                    let msg = `${data.affectedRows}筆資料移除，地址編號${data.addrNo} 刪除成功！`;
                    alert(msg);
                    that.closest("tr").fadeOut(500, function () {
                        //console.log(this);
                        that.remove();
                    });
                }else if(data.msg == "fail"){
                    alert("刪除失敗！");
                }
            },
            error: function(xhr){
                console.log("error");
            }
        });

    }

});