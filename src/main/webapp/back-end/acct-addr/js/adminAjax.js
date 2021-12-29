let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri

//================載入所有資料================//
function init() {
  $.ajax({
    url: `${projectPath}/acct-addr/AdminAjaxServlet.do`,
    type: "GET",
    data: { "action": "listAll" },
    dataType: "json",
    success: function (data) {

      let list_html = '';
      $.each(data, function (index, item) {
        list_html += `
          <tr>
            <td>${item.adminAcct}</td>
            <td>${item.adminPwd}</td>
            <td>${item.adminName}</td>
            <td>${item.adminPhone}</td>
            <td>${item.adminPos == 0 ? "保全" : "管委"}</td>
            <td>${item.adminState == 0 ? "在職" : "離職"}</td>
            <td class='del_edit_btn'>
                <i class='fa fa-edit'></i> 
                <div class="member_overlay" style="border: 1px solid red;"></div>
            </td>
          </tr>
        `;
      });
      //從後端傳回的Json中的職位、狀態的型別都是數值，所以上面的三元運算用「===」的型值相等 來判斷 -> 好像不一定都是數值！！？？
      //console.log(typeof(item.adminPos)); 
      $("#addr_table tbody").append(list_html);
      paging(); //呼叫分頁函式
    },
    error: function (xhr) {
      console.log("error");
      console.log(xhr);
    }
  })

}

//================呼叫載入所有資料的函式================//
$(function () {
  init();
});


var overlay = `
<article>
    <button type="button" class="mem_btnModalClose">關閉</button>
    <form action="#" method="#" id="address_form">
        <table id="addr_modal_table">
            <tbody>
                <tr>
                    <th>後臺帳號</th>
                    <td>
                        <input type="text" value="">
                    </td>
                </tr>
                <tr>
                    <th>密碼</th>
                    <td>
                        <input type="text" value="">
                    </td>
                </tr>
                <tr>
                    <th>姓名</th>
                    <td>
                        <input type="text" value="">
                    </td>
                </tr>
                <tr>
                    <th>電話</th>
                    <td>
                        <input type="text" value="">
                    </td>
                </tr>
                <tr>
                    <th>職稱</th>
                    <td style="text-align:left">
                      <select id = "jobTitle">
                        <option value="0">保全</option>
                        <option value="1">管委</option>    
                      </select>
                    </td>
                </tr>
                <tr>
                    <th>狀態</th>
                    <td style="text-align:left">
                        <select id = "state">
                          <option value="0">在職</option>
                          <option value="1">離職</option>
                        </select>
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


// 新增管委帳號
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
  $("#member_overlay_add").fadeIn();
});


// 確定新增？
$("div.member_overlay").on("click", "button.mem_btnConfirmAdd", function () {
  let r = confirm("確定新增？");
  if (r) {
    // 若使用者再次輸入錯誤，要將燈箱中的錯誤訊息區塊移除，後面才可再添加新的錯誤訊息區塊
    $("div.error_block").remove();

    //將燈箱輸入的值放入陣列中
    let row_list = new Array(5);
    //後臺帳號、密碼、姓名、電話
    for (var i = 0; i <= 3; i++) {
      row_list[i] = $("#addr_modal_table").find("td").eq(i).children().val().trim();
    }
    //職稱
    row_list[4] = $("#addr_modal_table").find("#jobTitle").val(); //職稱
    //狀態
    row_list[5] = $("#addr_modal_table").find("#state").val();  //在職 or 離職 狀態

    //正規表達式驗證
    let n = 0;
    let html_list = "";
    for (var i = 0; i <= 3; i++) {
      if (i == 0) {
        let re = /^[a-zA-Z0-9]{6,25}$/;
        if (re.test(row_list[i])) {
          n += 1;
        } else {
          html_list += `
           <div class="error_msg">＊後台帳號：不可空白，只能是英文字母、數字，且長度必須在6到25之間</div>
          `;
        }
      } else if (i == 1) {
        let re = /^[a-zA-Z0-9]{8,25}$/;
        if (re.test(row_list[i])) {
          n += 1;
        } else {
          html_list += `
            <div class="error_msg">＊密碼：不可空白，只能是英文字母、數字，且長度必須在8到25之間</div>
          `;
        }
      } else if (i == 2) {
        let re = /^[\u4e00-\u9fa5]{2,50}$/;
        if (re.test(row_list[i])) {
          n += 1;
        } else {
          html_list += `
            <div class="error_msg">＊姓名：不可空白，只能是中文，且長度必需在2到50之間</div>
          `;
        }
      } else if (i == 3) {
        let re = /^(09){1}\d{8}$/;
        if (re.test(row_list[i])) {
          n += 1;
        } else {
          html_list += `
            <div class="error_msg">＊電話：不可空白，需為數字，且需符合格式 09xxxxxxxx 共10碼</div>
          `;
        }
      }
    }

    let error_block = `<div class="error_block">${html_list}</div>`;
    //如果以上有任何一個驗證錯誤，n就不等於4
    if (n != 4) {
      //將錯誤訊息加入燈箱中
      $("#addr_modal_table").prepend(error_block);
    } else {
      //將陣列中的值包成JSON格式
      let admin_item = {
        "adminAcct": row_list[0],
        "adminPwd": row_list[1],
        "adminName": row_list[2],
        "adminPhone": row_list[3],
        "adminPos": row_list[4],
        "adminState": row_list[5]
      };
      admin_item = JSON.stringify(admin_item);
      $.ajax({
        url:`${projectPath}/acct-addr/AdminAjaxServlet.do`,
        type:"POST",
        data:{"action" : "insert", 
              "admin": admin_item
             },
        dataType:"json",
        success:function(data){
          if(data.msg == "success"){
            let member_table_html = `
              <tr>
                <td>${data.adminAcct}</td>
                <td>${data.adminPwd}</td>
                <td>${data.adminName}</td>
                <td>${data.adminPhone}</td>
                <td>${data.adminPos == 0 ? "保全" : "管委"}</td>
                <td>${data.adminState == 0 ? "在職" : "離職"}</td>
                <td class='del_edit_btn'>
                  <i class='fa fa-edit'></i> 
                  <div class="member_overlay" style="border: 1px solid red;"></div>
                </td>
              </tr>
            `;
            //將新增的資料顯示在表格的資料列上（新增在表格第一頁的第一列）
            $("#addr_table tbody").prepend(member_table_html);

            alert(`後台帳號：${data.adminAcct}，新增成功`);

            //燈箱消失
            $("#member_overlay_add").fadeOut();
            //清空燈箱裡的article區塊
            $("#member_overlay_add").empty();
            //將原本的分頁區塊去除
            $("#nav").remove(); 
            paging(); //再呼叫分頁函式
            $("#nav a.active").click(); //因為資料是新增在表格第一頁的第一列，所以要點回到分頁的第一頁去看
          }else if(data.msg == "fail"){
            alert("新增失敗，資料未成功新增到資料庫！");
            //燈箱消失
            $("#member_overlay_add").fadeOut();
            //清空燈箱裡的article區塊
            $("#member_overlay_add").empty();
          }else if(data.msg == "overlap"){
            let error_overlap = `<div class="error_block">＊後台帳號：此後台帳號已重複！</div>`;
            $("#addr_modal_table").prepend(error_overlap);
          }
        },
      })
    }

  }
});

//放置原本該筆資料列的原始資料
var original_list = Array(4);
//編輯
$("#addr_table").on("click", ".fa-edit", function () {
  //將article區塊放入 點擊編輯的那列資料 的燈箱中
  $(this).closest("td").children("div.member_overlay").prepend(overlay);

  //將燈箱的表單的action設定為"update"
  $(this).closest("td").children("div.member_overlay").find("form").attr("action", "update");

  //將後台帳號編號欄位設為不可編輯
  $(this).closest("td").children("div.member_overlay").find("input").eq(0).attr("disabled",true);

  //將「確定修改」的按鈕放入燈箱
  let mem_btnConfirmEdit = `
    <button type="button" class="mem_btnConfirmEdit">確定修改</button>
  `;

  $(this).closest("td").find("div.confirm_block").prepend(mem_btnConfirmEdit);
  $(this).closest("td").children("div.member_overlay").fadeIn();

  //點擊編輯的該筆資料，將該筆資料放入陣列
  // original_list = [後台帳號, 密碼, 姓名, 電話, 保全/管委, 在職/離職]
  for (var i = 0; i <= 5; i++) {
    original_list[i] = $(this).closest("tr").children().eq(i).text();
  }
  //console.log(row_list);
  let jobTitle = original_list[4] == "保全" ? 0 : 1;
  let state = original_list[5] == "在職" ? 0 : 1;

  //將點擊編輯的該筆資料放入燈箱的表單中
  for (var i = 0; i <= 3; i++) {
    $(this).closest("td").find("td").eq(i).children("input").val(original_list[i]);
  }
  $("#jobTitle").val(jobTitle);
  $("#state").val(state);
});

//確定修改?
$("#addr_table").on("click", "button.mem_btnConfirmEdit", function () {
  let r = confirm("確定修改？");
  if (r) {

    // 若使用者再次輸入錯誤，要將燈箱中的錯誤訊息區塊移除，後面才可再添加新的錯誤訊息區塊
    $("div.error_block").remove();

    //從燈箱抓修改好的資料放入陣列
    let that = $(this);
    let update_data = new Array(6);
    // update_data = [後台帳號, 密碼, 姓名, 電話, 0/1, 0/1];
    for (var i = 0; i <= 3; i++) {
      let item = that.closest("#address_form").find("td").eq(i).children("input").val();
      update_data[i] = item.trim();
    }
    // update_data = [後台帳號, 密碼, 姓名, 電話, 保全/管委, 在職/離職];
    update_data[4] = $("#jobTitle").val() == 0 ? "保全": "管委";
    update_data[5] = $("#state").val() == 0 ? "在職" : "離職";

    //****************** 如果 使用者修改後的資料 跟 原本的資料 一樣，則直接將燈箱關閉，結束程式
    //original_list = [後台帳號, 密碼, 姓名, 電話, 保全/管委, 在職/離職];
    let count = 0;
    for(var i = 0 ; i <= 5; i++){
      if( original_list[i] == update_data[i] ){
        count++;
      }
    }
    // update_data = [後台帳號, 密碼, 姓名, 電話, 0/1, 0/1];
    update_data[4] = update_data[4] == "保全" ? "0" : "1";
    update_data[5] = update_data[5] == "在職" ? "0" : "1";
    //console.log(update_data);
    if(count === 6){
      //燈箱消失
      that.closest("div.member_overlay").fadeOut();
      //清空燈箱裡的article區塊
      that.closest("div.member_overlay").empty();
      let msg = `後台帳號${update_data[0]} 修改成功`;
      alert(msg);
      //console.log("hello1")
      return; //結束程式
    }
    //console.log("hello2");

    //******************正規表達式驗證
    let n = 0;
    let html_list = "";
    for (var i = 0; i <= 3; i++) {
      if (i == 0) {   // 後台帳號不可修改
        // let re = /^[a-zA-Z0-9]{5,25}$/;
        // if ( re.test(update_data[i]) ) {
        //   n += 1;
        // } else {
        //   html_list += `
        //    <div class="error_msg">＊後台帳號：不可空白，只能是英文字母、數字，且長度必須在5到25之間</div>
        //   `;
        // }
      } else if (i == 1) {
        let re = /^[a-zA-Z0-9]{8,25}$/;
        if ( re.test(update_data[i]) ) {
          n += 1;
        } else {
          html_list += `
            <div class="error_msg">＊密碼：不可空白，只能是英文字母、數字，且長度必須在8到25之間</div>
          `;
        }
      } else if (i == 2) {
        let re = /^[\u4e00-\u9fa5]{2,50}$/;
        if ( re.test(update_data[i]) ) {
          n += 1;
        } else {
          html_list += `
            <div class="error_msg">＊姓名：不可空白，只能是中文，且長度必需在2到50之間</div>
          `;
        }
      } else if (i == 3) {
        let re = /^(09){1}\d{8}$/;
        if (re.test( update_data[i] )) {
          n += 1;
        } else {
          html_list += `
            <div class="error_msg">＊電話：不可空白，需為數字，且需符合格式 09xxxxxxxx 共10碼</div>
          `;
        }
      }
    }

    let error_block = `<div class="error_block">${html_list}</div>`;
    
    //如果以上有任何一個驗證錯誤，n就不等於4
    if (n != 3) {
      //將錯誤訊息加入燈箱中
      $("#addr_modal_table").prepend(error_block);
    } else {
      //將陣列中的值包成JSON格式
      let admin_item = {
        "adminAcct": update_data[0],
        "adminPwd": update_data[1],
        "adminName": update_data[2],
        "adminPhone": update_data[3],
        "adminPos": update_data[4],
        "adminState": update_data[5]
      };
      admin_item = JSON.stringify(admin_item);
      $.ajax({
        url: `${projectPath}/acct-addr/AdminAjaxServlet.do`,
        type:"POST",
        data:{"action" : "update", 
              "admin": admin_item
             },
        dataType:"json",
        success:function(data){
          if(data.msg == "success"){
            let adminPos = data.adminPos == 0 ? "保全": "管委";
            let adminState = data.adminState == 0 ? "在職": "離職";
            let return_list = [data.adminAcct, data.adminPwd, data.adminName, 
                               data.adminPhone, adminPos, adminState];
            //將修改好的資料放回該筆資料列
            for (var i = 0; i <= 5; i++) {
              that.closest("tr").children("td").eq(i).text(return_list[i]);
            }

            let msg = `後台帳號${data.adminAcct} 修改成功！`;
            alert(msg);


            //燈箱消失
            that.closest("div.member_overlay").fadeOut();

            //清空燈箱裡的article區塊
            that.closest("div.member_overlay").empty();

          }else if(data.msg == "fail"){
            alert("修改失敗，資料未成功更新到資料庫！");
            //燈箱消失
            $("#member_overlay_add").fadeOut();
            //清空燈箱裡的article區塊
            $("#member_overlay_add").empty();
          }
          
        },
        error: function(xhr){         // request 發生錯誤的話執行
          console.log("error");
        },

      })
    }

  }
});


//移除
$("#addr_table").on("click", ".fa-minus-circle", function () {
  //要刪除的該筆資料的 後台帳號
  let that = $(this);
  let adminAcct = that.closest("tr").find("td").eq(0).text();
  console.log(adminAcct);
  let r = confirm("確認移除？");
  if (r) {
    $.ajax({
      url:`${projectPath}/acct-addr/AdminAjaxServlet.do`,
      type:"POST",
      data:{"action" : "delete", "adminAcct": adminAcct},
      dataType:"json",
      success: function(data){
          if(data.msg == "success"){
              let msg = `${data.affectedRows}筆資料移除，後台帳號${data.adminAcct} 刪除成功！`;
              alert(msg);
              that.closest("tr").fadeOut(500, function () {
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

