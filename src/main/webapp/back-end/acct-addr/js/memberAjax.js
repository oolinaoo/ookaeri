//================載入所有資料================//
function init(){

    $.ajax({
      url: "http://localhost:8081/Okaeri/front-end/mem/MemberServlet.do",      
      type: "GET",            
      data: {"action": "listAllForBackEnd"},            
      dataType: "json",            
      success: function(data){    

        let list_html = '';
  
        $.each(data, function(index, item){

            //模擬從後端傳來的地址資料
            // let item = {
            //     "memAcct":"1234",
            //     "memAddr":{"memBuild":"A","memFloor":"5","memRoom":"5"},
            // };

            let memBuild = item.memAddr.memBuild;
            let memFloor = item.memAddr.memFloor;
            let memRoom = item.memAddr.memRoom;
            
            list_html += `
            <tr>
                <td>${item.memAcct}</td>
                <td>${item.memPwd}</td>
                <td>${item.memName}</td>
                <td>${item.memId}</td>
                <td>${item.memEmail}</td>
                <td>${memBuild}棟${memFloor}樓${memRoom}房</td>
                <td>${item.memPhone}</td>
                <td>${item.memState=='0'?"啟用中":"停用"}</td>
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
      }
    });
}


//================呼叫載入所有資料的函式================//
$(function(){
    init();
});



//===================== 分頁 =====================
function paging() {
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

//================== 燈箱中的article區塊 ==================
var overlay = `
  <article>
      <button type="button" class="mem_btnModalClose">關閉</button>
      <form action="#" method="#" id="address_form">
          <table id="addr_modal_table">
              <tbody>
                  <tr>
                      <th>住戶帳號</th>
                      <td>
                          <input type="text" id="memAcct" value="">
                          <div class="errorMsg" id="errAcct">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <th>密碼</th>
                      <td>
                          <input type="text" id="memPwd" value="">
                          <div class="errorMsg" id="errPwd">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <th>姓名</th>
                      <td>
                          <input type="text" id="memName" value="">
                          <div class="errorMsg" id="errName">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <th>生日</th>
                      <td>
                          <input id="memBirthday" type="text" autocomplete="off" placeholder="請選擇日期">
                          <div class="errorMsg" id="errBirthday">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <th>身分證字號</th>
                      <td>
                          <input type="text" id="memId" value="">
                          <div class="errorMsg" id="errId">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <th>性別</th>
                      <td>
                          <input type="radio" id="male" name="memSex" class="memSex" value="男">
                          <label for="male">男</label>
                          <input type="radio" id="female" name="memSex" class="memSex" value="女">
                          <label for="female">女</label>
                          <input type="radio" id="other" name="memSex" class="memSex" value="其他">
                          <label for="other">其他</label>
                          <div class="errorMsg" id="errSex">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <th>聯絡電話(手機)</th>
                      <td>
                          <input type="text" id="memPhone" value="">
                          <div class="errorMsg" id="errPhone">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>  
                  <tr>
                      <th>電子信箱</th>
                      <td>
                          <input type="text" id="memEmail" value="">
                          <div class="errorMsg" id="errEmail">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>      
                  <tr>
                      <th>地址</th>
                      <td>
                          <select id="select_build" name="select_build">
                              <option value="">請選擇</option>
                              <option value="A">A</option>
                              <option value="B">B</option>
                              <option value="C">C</option>
                          </select>
                          <span>棟</span>
                          <select id="select_floor" name="select_floor">
                              <option value="">請選擇</option>
                              <option value="1">1</option>
                              <option value="2">2</option>
                              <option value="3">3</option>
                          </select>
                          <span>樓</span>
                          <select id="select_room" name="select_room">
                              <option value="">請選擇</option>
                              <option value="1">1</option>
                              <option value="2">2</option>
                              <option value="3">3</option>
                          </select>
                          <span>房</span>
                      </td>
                  </tr>  
                  <tr>
                      <th>住戶家人姓名</th>
                      <td>
                          <div class="famMemList">
                              <div class="famMem_block">
                                  <div class="plus_minus_btn">
                                      <span class="minus">-</span>
                                      <span class="plus">+</span>
                                  </div>
                                  <input type="text" class="famMems" value=""style="width:65px;" >
                              </div>
                          </div>
                          <div class="errorMsg" id="errFams">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>  
                  <tr id="trMemState">
                      <th>住戶狀態</th>
                      <td>
                          <select id="memState">
                              <option value="0">啟用中</option>
                              <option value="1">停用</option>
                          </select>
                      </td>
                  </tr>  
                  <tr>
                      <th>照片</th>
                      <td>
                          <div class="mem_photo">
                              <div class="profilePic_preview">
                                  <!-- <div class="mem_uploadPic"></div> -->
                                  <!-- <img src="images/mug-shot/man-hair.png" class="mem_uploadPic"> -->
                              </div>
                              <div class="memUploadPic_btn_block">
                                  <input type="file" id="memUploadPic_file" style="margin-top: 20px;width:220px">
                              </div>
                          </div>
                          <div class="errorMsg" id="errFams">
                              <span>錯誤訊息</span>
                          </div>
                      </td>
                  </tr>  
              </tbody>
          </table>
          <div class="confirm_block"></div>
      </form>
  </article>
`;




//================== 上傳圖片後，顯示在預覽區塊==================
// 圖片預覽
function memUploadPic(file){
  var reader = new FileReader();
  reader.readAsDataURL(file);
  reader.addEventListener("load", function(){
    let img_html = `
      <img src="" class="mem_uploadPic">
    `;
    $("div.profilePic_preview").empty();
    $("div.profilePic_preview").append(img_html);
    let mem_uploadPic_element = document.getElementsByClassName("mem_uploadPic")[0];
    mem_uploadPic_element.setAttribute("src", reader.result);
  });

}


//選擇圖片
$("div.member_overlay").on("change", "#memUploadPic_file",function(e){
if(this.files.length > 0){
  //console.log(this); // <input type="file" id="memUploadPic_file">
  memUploadPic(this.files[0]); 
}else{
  let img_html = `
      <img src="" class="mem_uploadPic">
  `;
  $("div.profilePic_preview").empty();
  $("div.profilePic_preview").append(img_html);
}
});


//======================== 燈箱關閉 ========================
// 關閉 新增功能的燈箱
$("div.member_overlay").on("click", "button.mem_btnModalClose", function () {

  $(this).closest("div.member_overlay").fadeOut();
  //清空燈箱裡的article區塊
  $(this).closest("div.member_overlay").empty();
});

// 關閉 後來在地址資料表中增加的燈箱
$("#addr_table").on("click", "button.mem_btnModalClose", function () {

  $(this).closest("div.member_overlay").fadeOut();
  //清空燈箱裡的article區塊
  $(this).closest("div.member_overlay").empty();
});

//========================= 新增住戶資料 =========================
// 新增
$("a.newPost-button").on("click", function () {
  
  //將article區塊放入燈箱中
  $("#member_overlay_add").prepend(overlay);

  //將「確定新增」的按鈕放入燈箱
  let mem_btnConfirmAdd = `
      <button type="button" class="mem_btnConfirmAdd">確定新增</button>
  `;
  $("#member_overlay_add div.confirm_block").append(mem_btnConfirmAdd);

  // 將住戶狀態欄位拿掉，「新增」不需設定住戶狀態
  // $("#trMemState").remove();

  $("#member_overlay_add").fadeIn();  
});


// 確定新增？
$("div.member_overlay").on("click", "button.mem_btnConfirmAdd", function () {

  let r = confirm("確定新增？");
  if(!r){
      return; //按「取消」，就直接結束程式
  }

  // 要抓燈箱輸入的值，再放入陣列中（從這繼續寫！）
  let memAcct = $("#memAcct").val();
  let memPwd = $("#memPwd").val();
  let memName = $("#memName").val();
  let memBirthday = $("#memBirthday").val();
  let memId = $("#memId").val();
  //jQuery取Radio的值: https://ithelp.ithome.com.tw/articles/10019707
  let memSex = $('input[name=memSex]:checked').val(); 
  let memPhone = $("#memPhone").val();
  let memEmail = $("#memEmail").val();
  let memBuild = $("#select_build").val();
  let memFloor = $("#select_floor").val();
  let memRoom = $("#select_room").val();
  
  let famMems = new Array();
  for(let i = 0; i < $("input.famMems").length; i++){
    let aFam = $("input.famMems").eq(i).val().trim();
    famMems[i] = aFam;
  }
  //去除為空字串的元素
  famMems = famMems.filter(function(item){
    return item !== "";
  });
  famMems = JSON.stringify(famMems); //將陣列轉為JSON格式的字串

  let memState = $("#memState").val(); 
  let memPhoto = document.getElementById("memUploadPic_file").files[0];
  

  //將以上的資料塞入fomrData物件
  let formData = new FormData();
  formData.append("memAcct", memAcct);
  formData.append("memPwd", memPwd);
  formData.append("memName", memName);
  formData.append("memBirthday", memBirthday);
  formData.append("memId", memId);
  formData.append("memSex", memSex);
  formData.append("memPhone", memPhone);
  formData.append("memEmail", memEmail);
  formData.append("memBuild", memBuild);
  formData.append("memFloor", memFloor);
  formData.append("memRoom", memRoom);
  formData.append("famMems", famMems);
  formData.append("memState", memState);
  formData.append("memPhoto", memPhoto);
  for(let [key, value] of formData.entries()) {
      console.log(key+ ', '+ value);
  }

  let member_table_html = `
      <tr>
          <td>${memAcct}</td>
          <td>${memPwd}</td>
          <td>${memName}</td>
          <td>${memId}</td>
          <td>${memEmail}</td>
          <td>${memBuild}棟${memFloor}樓${memRoom}房</td>
          <td>${memPhone}</td>
          <td>${memState == '0' ? '啟用中':'停用'}</td>
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
  //將原本的分頁區塊去除
  $("#nav").remove(); 
  paging(); //再呼叫分頁函式
  $("#nav a.active").click(); //因為資料是新增在表格的第一列，所以要點回到分頁的第一頁去看

});

//========================= 修改住戶資料 =========================
var original_list = Array(4);
//編輯
$("#addr_table").on("click", ".fa-edit", function () {
  //將article區塊放入 點擊編輯的那列資料 的燈箱中
  $(this).closest("td").children("div.member_overlay").prepend(overlay);

  //加入帳號建立時間的欄位
  let acctCreatTime_html=`                    
      <tr>
          <th>帳號建立時間</th>
          <td>
              <input type="text" id="acctCreatTime" value="" disabled>
          </td>
      </tr>   
  `;
  $("#trMemState").after(acctCreatTime_html);    

  //將「確定修改」的按鈕放入燈箱
  let mem_btnConfirmEdit = `
      <button type="button" class="mem_btnConfirmEdit">確定修改</button>
  `;
  $(this).closest("td").find("div.confirm_block").prepend(mem_btnConfirmEdit);
  $(this).closest("td").children("div.member_overlay").fadeIn();

  //點擊編輯的該筆資料，將該筆資料放入陣列
  //let row_list = new Array(4);
  // for (var i = 0; i <= 3; i++) {
  //     original_list[i] = $(this).closest("tr").children().eq(i).text();
  // }


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

      //如果使用者修改後的資料，跟原本的資料一樣，則直接將燈箱關閉，結束程式
      let count = 0;
      for (var i = 1; i <= 3; i++) {
          if (original_list[i] === update_data[i]) {
              count++;
          }
      }
      //console.log(count);
      if (count === 3) {
          $(this).closest("div.member_overlay").fadeOut();
          $(this).closest("div.member_overlay").empty();
          let msg = `地址編號${update_data[0]} 修改成功`;
          alert(msg);
          return;
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


//========================== 刪除住戶資料 ==========================
//移除
$("#addr_table").on("click", ".fa-minus-circle", function () {
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



//======================= 新增/刪除同住家人 =======================
// 新增同住家人
$("div.member_overlay").on("click", "span.plus", function () {
  //console.log("hello");
  //點擊的那個框裡面的值
  let famMem = $(this).closest("div.famMem_block").children("input").val(); 
  //點擊的下一個，長度等於0，表示同層的下一個節點不存在
  let nextFam = $(this).closest("div.famMem_block").next().length; 
  //console.log(nextFam);
  if(famMem.trim() != "" && nextFam == 0){
      //console.log("hello1");
      let famMemList_html = `
      <div class="famMem_block">
          <div class="plus_minus_btn">
              <span class="minus">-</span>
              <span class="plus">+</span>
          </div>
          <input type="text" class="famMems" value="" style="width:65px;">
      </div>
      `;
      $(this).closest("div.famMem_block").after(famMemList_html);
  }
});  

// 刪除同住家人
$("div.member_overlay").on("click", "span.minus", function () {
  //console.log(this);

  //如果不是第一個才刪除框框
  if (!$(this).closest("div.famMem_block").is(":first-child")) {
      //console.log("hello1");
      $(this).closest("div.famMem_block").fadeOut(500, function () {
          $(this).remove();
      });

  }else{
      //jQuery的判斷節點是否存在，如果長度為0，就代表該節點不存在
      // 是第一個框框，但是下一個框框存在，因此可以移除
      if( $(this).closest("div.famMem_block").next().length != 0 ){
          //console.log("hello2");
          $(this).closest("div.famMem_block").fadeOut(500, function () {
              $(this).remove();
          });
      // 是第一個框框，但是下一個框框不存在，因此不可移除，只能清除裡面的值
      }else{
          //console.log("hello3");
          $(this).closest("div.famMem_block").find("input").val("");
      }
  }


});

//======================= 日期 =======================
// https://xdsoft.net/jqplugins/datetimepicker/
/*  
  彈窗無法使用日曆的解決辦法：（ 因為彈窗內的東西都是之後才append上去的節點，非原有頁面的東西）
  https://laracasts.com/discuss/channels/laravel/datetime-picker-not-working-in-dynamic-modal
*/
$.datetimepicker.setLocale('zh'); // kr ko ja(日文) en(英文)
$('body').on('focus',"#memBirthday",function(){
  $(this).datetimepicker({
      theme: 'dark',          //theme: 'dark', //只有兩個顏色黑跟白可以用
      timepicker: false,   //timepicker: false, //可以選幾點幾分幾秒
      step: 1,            //step: 60 (這是timepicker的預設間隔60分鐘) //只能到間隔1分鐘，不能以秒間隔
      format: 'Y-m-d',
      value: "",  //起始日期 
      //disabledDates:    ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含  //這不是寫死的，可以從資料庫撈出來就可以變成是動態的
      //startDate:	        '2017/07/10',  // 起始日
      // minDate:           '-1970-01-01', // 去除今日(不含)之前 //只能選到今天以後的日子
      //maxDate:           //'+1970-01-01'  // 去除今日(不含)之後 //只能選到今日以前的（例如選生日）//寫+2021/11/11會無效，老師在下面有寫萬用的方法
  
  });

  //      2.以下為某一天之後的日期無法選擇
  var somedate2 = new Date();
  $(this).datetimepicker({
      beforeShowDay: function(date) {
      if (  date.getYear() >  somedate2.getYear() || 
              (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
              (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
          ) {
              return [false, ""]
          }
          return [true, ""];
  }});
  
})


