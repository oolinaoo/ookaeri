let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri

//================載入所有資料================//
function init(){

    $.ajax({
      url: `${projectPath}/mem/MemberServlet.do`,      
      type: "GET",            
      data: {"action": "listAllForBackEnd"},            
      dataType: "json",            
      success: function(data){    
        //console.log(data);
        let list_html = '';
  
        $.each(data, function(index, item){

            //模擬從後端傳來的地址資料
            // let item = {
            //     "memAcct":"1234",
            //     "memAddr":{"memBuild":"A","memFloor":"5","memRoom":"5"},
            // };

            let memBuild = item.memAddr.addrBuild;
            let memFloor = item.memAddr.addrFloor;
            //console.log(typeof(memFloor)); //number
            let memRoom = item.memAddr.addrRoom;
            
            list_html += `
            <tr>
                <td>${item.memAcct}</td>
                <td>${item.memPwd}</td>
                <td>${item.memName}</td>
                <td>${item.memId}</td>
                <td>${item.memPhone}</td>
                <td>${item.memEmail}</td>
                <td>${memBuild}棟${memFloor}樓${memRoom}房</td>
                <td>${item.memState=='0'?"啟用中":"停用"}</td>
                <td class='del_edit_btn'>
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
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>密碼</th>
                        <td>
                            <input type="text" id="memPwd" value="">
                            <div class="errorMsg" id="errPwd">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>姓名</th>
                        <td>
                            <input type="text" id="memName" value="">
                            <div class="errorMsg" id="errName">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>生日</th>
                        <td>
                            <input id="memBirthday" type="text" autocomplete="off" placeholder="請選擇日期" value="">
                            <div class="errorMsg" id="errBirthday">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>身分證字號</th>
                        <td>
                            <input type="text" id="memId" value="">
                            <div class="errorMsg" id="errId">
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
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>聯絡電話(手機)</th>
                        <td>
                            <input type="text" id="memPhone" value="">
                            <div class="errorMsg" id="errPhone">
                            </div>
                        </td>
                    </tr>  
                    <tr>
                        <th>電子信箱</th>
                        <td>
                            <input type="text" id="memEmail" value="">
                            <div class="errorMsg" id="errEmail">
                            </div>
                        </td>
                    </tr>      
                    <tr>
                        <th>地址</th>
                        <td>
                            <select id="select_build" name="select_build"></select>
                            <span>棟</span>
                            <select id="select_floor" name="select_floor"></select>
                            <span>樓</span>
                            <select id="select_room" name="select_room"></select>
                            <span>房</span>
                            <div class="errorMsg" id="errAddr">
                            </div>
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
                                    <input type="text" class="famMems" value="" style="width:65px;" >
                                </div>
                            </div>
                            <div class="errorMsg" id="errFams">
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
                                    <!-- <img src="images/user.png" class="mem_uploadPic" style="height: 100px; width: 100px;"> -->
                                </div>
                                <div class="memUploadPic_btn_block">
                                    <input type="file" id="memUploadPic_file" style="margin-top: 20px;width: 220px">
                                </div>
                            </div>
                            <div class="errorMsg" id="errPhoto">
                            </div>
                        </td>
                    </tr>  
                </tbody>
            </table>
            <div class="confirm_block"></div>
        </form>
    </article>
`;


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
// 製作下拉式選單的函式
function select(item){
    //console.log(addrData);
    let html = `<option value="">請選擇</option>`;
    for(let i = 0; i < item.length; i++){
        html += `
            <option value="${item[i]}">${item[i]}</option>
        `;
    }
    return html;
}

var build;
//選擇 棟號 會觸發此函式
$("body").on('change', "#select_build",function(){
    //console.log("hello1");
    if($("#select_build").val() == ""){ //棟號選單 選「請選擇」
        $("#select_floor").empty();
        $("#select_room").empty();
        return;
    }
    $("#select_floor").empty();
    $("#select_room").empty();
    build = $(this).val(); // "A"
    let floor = addrData[build]["addrFloor"]; // [1,2,3,4]
    floor = JSON.parse(floor);
    $("#select_floor").append( select(floor) );

});

//選擇 樓號 會觸發此函式
$("body").on('change', "#select_floor",function(){
    //console.log("hello2");
    if($("#select_floor").val() == ""){ //樓號選單 選「請選擇」
        $("#select_room").empty();
        return;
    }
    $("#select_room").empty();
    let floor = $(this).val(); // "1"
    let room = addrData[build]["addrRoom"][floor]; 
    room = JSON.parse(room);
    $("#select_room").append( select(room) );

});

var addrData; //用來存放後端傳來的地址資料 -> 點擊「新增」、「編輯」按鈕時，都會用到

// 新增
$("a.newPost-button").on("click", function () {
    
    //將article區塊放入燈箱中
    $("#member_overlay_add").prepend(overlay);

    //將「確定新增」的按鈕放入燈箱
    let mem_btnConfirmAdd = `
        <button type="button" id="add_btn" class="mem_btnConfirmAdd">確定新增</button>
    `;
    $("#member_overlay_add div.confirm_block").append(mem_btnConfirmAdd);

    //若開啟 新增的彈窗，則先顯示 預設的圖片
    let preview_html = `
        <img src="images/user.png" class="mem_uploadPic" style="height: 100px; width: 100px;">
    `;
    // $("div.profilePic_preview").empty();
    $("div.profilePic_preview").append(preview_html);
    //用於辨識是否為 新增 的 彈窗，在檔案上傳函式會拿來使用
    $("#memUploadPic_file").addClass("add_modal"); 

    // 將住戶狀態欄位拿掉，「新增」不需設定住戶狀態
    $("#trMemState").remove();

    //按下「新增」按鈕後，去抓後端的地址資料，製作動態的下拉式選單
    $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`,      
        type: "GET",            
        data: {"action": "getAddrData"},            
        dataType: "json",
        success: function(data){
            //console.log(data);
            addrData = data;
            let addrBuild = addrData.addrBuild;
            //console.log(addrBuild); // ["A","B","C","D"] 為Json格式的字串
            addrBuild = JSON.parse(addrBuild);
            //console.log(addrBuild); // ['A', 'B', 'C', 'D'] 為陣列物件
            $("#select_build").append(select(addrBuild));
        },
        error: function(xhr){
            console.log("error");
            console.log(xhr);
        }

    });

    $("#member_overlay_add").fadeIn();  
});


// 確定新增？
function famMemsList(famMemsList){
    if(famMemsList.length == 0){
      let famMemList_html = ``;
      famMemList_html = `
        <div class="famMem_block">
            <div class="plus_minus_btn">
              <span class="minus">-</span>
              <span class="plus">+</span>
            </div>
            <input type="text" value="" class="famMems" style="width:65px;">
        </div>
      `;
      $("div.famMemList").empty();
      $("div.famMemList").append(famMemList_html);
    }else{
      let famMemList_html = ``;
      for(var i = 0; i < famMemsList.length; i++){
        famMemList_html+=`
            <div class="famMem_block">
                <div class="plus_minus_btn">
                    <span class="minus">-</span>
                    <span class="plus">+</span>
                </div>
                <input type="text" value="${famMemsList[i]}" class="famMems" style="width:65px;">
            </div>
        `;
      }
      $("div.famMemList").empty();
      $("div.famMemList").append(famMemList_html);
    }
}

$("div.member_overlay").on("click", "button.mem_btnConfirmAdd", function () {

    let r = confirm("確定新增？");
    if(!r){
        return; //按「取消」，就直接結束程式
    }

    // 抓燈箱輸入的值，準備放入formData物件
    let memAcct = $("#memAcct").val().trim();
    let memPwd = $("#memPwd").val().trim();
    let memName = $("#memName").val().trim();
    let memBirthday = $("#memBirthday").val().trim();
    let memId = $("#memId").val().trim();
    //jQuery取Radio的值: https://ithelp.ithome.com.tw/articles/10019707
    let memSex = $('input[name=memSex]:checked').val(); 
    let memPhone = $("#memPhone").val().trim();
    let memEmail = $("#memEmail").val().trim();
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

    let memState = "啟用中"; 
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
    //formData.append("memState", memState);
    formData.append("memPhoto", memPhoto);
    formData.append("action", "addMemForBackEnd");
    for(let [key, value] of formData.entries()) {
        console.log(key+ ', '+ value);
    }

    $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`,
        type: "POST",
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
        success: function(data){
            //將錯誤訊息區塊裡面的span標籤清除
            $("div.errorMsg").empty();

            if(data.msg=="success"){
                let member_table_html = `
                    <tr>
                        <td>${memAcct}</td>
                        <td>${memPwd}</td>
                        <td>${memName}</td>
                        <td>${memId}</td>
                        <td>${memPhone}</td>
                        <td>${memEmail}</td>
                        <td>${memBuild}棟${memFloor}樓${memRoom}房</td>
                        <td>${memState}</td>
                        <td class='del_edit_btn'>
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
                alert(`帳號${memAcct}，新增成功！`);

            }else if(data.msg == "errorMsgs"){

                function errMsgs(errItem, errTag){
                    let errHtml = `
                        <span>${errItem}</span>
                    `;
                    $(errTag).append(errHtml);
                }
          
                //如果Json物件中沒有該key，就會是undefined，undefined會是false
                if(data.errAcct != undefined){
                errMsgs(data.errAcct, "#errAcct");
                }

                if(data.errPwd != undefined){
                    errMsgs(data.errPwd, "#errPwd");
                }

                if(data.errName != undefined){
                errMsgs(data.errName, "#errName");
                }

                if(data.errBirthday != undefined ){
                    errMsgs(data.errBirthday, "#errBirthday");
                }

                if(data.errId != undefined ){
                    errMsgs(data.errId, "#errId");
                }   
                
                if(data.errSex != undefined ){
                    errMsgs(data.errSex, "#errSex");
                }  

                if(data.errPhone != undefined){
                    errMsgs(data.errPhone, "#errPhone");
                }

                if(data.errEmail != undefined ){
                errMsgs(data.errEmail, "#errEmail");
                }
        
                if(data.errAddr != undefined){
                errMsgs(data.errAddr, "#errAddr");
                }
            
                if(data.errFams != undefined){
                errMsgs(data.errFams, "#errFams");
                }
                
                if(data.errPhoto != undefined){
                    errMsgs(data.errPhoto, "#errPhoto");
                }

                famMems = JSON.parse(famMems);
                famMemsList(famMems);
                alert(`資料格式有誤，請查看！`); 
            }else if(data.msg == "fail"){
                alert(`住戶帳號新增失敗！`); 
            }
        },
        error: function(xhr){
            console.log("error");
            console.log(xhr);
        }

    })

});

var memAcct_forPic;  //用於圖片上傳，在編輯彈窗中顯示資料庫中原本的圖片
//========================= 修改住戶資料 =========================
//編輯 從資料庫抓該住戶的資料，並顯示在燈箱上
$("#addr_table").on("click", ".fa-edit", function () {
    
    let that = $(this);
    //將article區塊放入 點擊編輯的那列資料 的燈箱中
    that.closest("td").children("div.member_overlay").prepend(overlay);

    //將帳號欄位設為不可編輯
    $("#memAcct").prop("disabled", true);

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
        <button type="button" id="edit_btn" class="mem_btnConfirmEdit">確定修改</button>
    `;
    that.closest("td").find("div.confirm_block").prepend(mem_btnConfirmEdit);

    //用於辨識是否為 編輯 的 彈窗，在檔案上傳函式會拿來使用
    $("#memUploadPic_file").addClass("edit_modal");

    // 抓取地址資料
    $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`,      
        type: "GET",            
        data: {"action": "getAddrData"},            
        dataType: "json",
        success: function(data){
            //console.log(data);
            addrData = data;
            let addrBuild = addrData.addrBuild;
            //console.log(addrBuild); // ["A","B","C","D"] 為Json格式的字串
            addrBuild = JSON.parse(addrBuild);
            //console.log(addrBuild); // ['A', 'B', 'C', 'D'] 為陣列物件
            $("#select_build").append(select(addrBuild));
        },
        error: function(xhr){
            console.log("error");
            console.log(xhr);
        }
    });

    //點擊編輯的該筆資料的 住戶帳號，將住戶帳號送到後端，抓取該帳號的資料，顯示在燈箱上
    let memAcct = that.closest("tr").children().eq(0).text();
    memAcct_forPic = memAcct;
    //讀資料庫的圖片
    // /Okaeri/mem/MemberServlet.do?action=getImage&memAcct=gina123test1
    let src = `
        ${projectPath}/mem/MemberServlet.do?action=getImage&memAcct=${memAcct}
    `;
    let img_html = `
        <img src="${src}" class="mem_uploadPic">
    `;
    $("div.profilePic_preview").append(img_html);
    
    

    $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`,      
        type: "GET",            
        data: {"action": "getOneMemFroBackEnd", "memAcct": memAcct},            
        dataType: "json",            
        success: function(data){    
            //console.log(data);
            $("#memAcct").val(data.memAcct);
            $("#memPwd").val(data.memPwd);
            $("#memName").val(data.memName);
            $("#memBirthday").val(data.memBirthday);
            $("#memId").val(data.memId);
            let memSex = data.memSex;
            switch(memSex){
              case "男":
                $("#male").prop("checked", true);
                break;
              case "女":
                $("#female").prop("checked", true);
                break;
              case "其他":
                $("#other").prop("checked", true);
                break;
            }
            $("#memPhone").val(data.memPhone);
            $("#memEmail").val(data.memEmail);
            
            // 顯示該住戶的棟/樓/房 + 動態下拉式選單
            $("#select_build").val(data.addrBuild).change();
            $("#select_floor").val(data.addrFloor).change();
            $("#select_room").val(data.addrRoom);

            let famMemsLst = JSON.parse(data.famMems); //陣列的Json字串轉陣列物件
            famMemsList(famMemsLst); 

            $("#memState").val(data.memState);
            $("#acctCreatTime").val(data.acctCreatTime);
            that.closest("td").children("div.member_overlay").fadeIn();
            
        },
        error: function(xhr){    // request 發生錯誤的話執行
          console.log("error");
          console.log(xhr);
        }
      });


});


//確定修改?
$("#addr_table").on("click", "button.mem_btnConfirmEdit", function () {
    let r = confirm("確定修改？");
    if(!r){
        return; //按「取消」，就直接結束程式
    }

    let that = $(this);

    // 抓燈箱輸入的值，準備放入formData物件
    let memAcct = $("#memAcct").val();
    let memPwd = $("#memPwd").val().trim();
    let memName = $("#memName").val().trim();
    let memBirthday = $("#memBirthday").val().trim();
    let memId = $("#memId").val().trim();
    let memSex = $('input[name=memSex]:checked').val(); 
    let memPhone = $("#memPhone").val().trim();
    let memEmail = $("#memEmail").val().trim();
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
    formData.append("action", "updateMemForBackEnd");
    for(let [key, value] of formData.entries()) {
        console.log(key+ ', '+ value);
    }

    //＊＊＊＊修改資料，後端要驗證地址是不是跟原本的相同，而不是回傳重複的訊息！！
    $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`,
        type: "POST",
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
        success: function(data){

            //將錯誤訊息區塊裡面的span標籤清除
            $("div.errorMsg").empty();

            if(data.msg=="success"){

                //將修改好的資料放回該筆資料列
                that.closest("tr").children("td").eq(0).text(memAcct);
                that.closest("tr").children("td").eq(1).text(memPwd);
                that.closest("tr").children("td").eq(2).text(memName);
                that.closest("tr").children("td").eq(3).text(memId);
                that.closest("tr").children("td").eq(4).text(memPhone);
                that.closest("tr").children("td").eq(5).text(memEmail);
                let addr = `${memBuild}棟${memFloor}樓${memRoom}房`;
                that.closest("tr").children("td").eq(6).text(addr);
                let state = memState == "0" ? "啟用中 ":" 停用" ;
                that.closest("tr").children("td").eq(7).text(state);

                //燈箱消失
                that.closest("div.member_overlay").fadeOut();

                //清空燈箱裡的article區塊
                that.closest("div.member_overlay").empty();
                
                alert(`住戶帳號${memAcct}，修改資料成功`);

            }else if( data.msg == "errorMsgs" ){

                function errMsgs(errItem, errTag){
                    let errHtml = `
                        <span>${errItem}</span>
                    `;
                    $(errTag).append(errHtml);
                }
          
                //如果Json物件中沒有該key，就會是undefined，undefined會是false
                if(data.errPwd != undefined){
                    errMsgs(data.errPwd, "#errPwd");
                }

                if(data.errName != undefined){
                errMsgs(data.errName, "#errName");
                }

                if(data.errBirthday != undefined ){
                    errMsgs(data.errBirthday, "#errBirthday");
                }

                if(data.errId != undefined ){
                    errMsgs(data.errId, "#errId");
                }   

                if(data.errPhone != undefined){
                    errMsgs(data.errPhone, "#errPhone");
                }

                if(data.errEmail != undefined ){
                errMsgs(data.errEmail, "#errEmail");
                }
        
                if(data.errAddr != undefined){
                errMsgs(data.errAddr, "#errAddr");
                }
            
                if(data.errFams != undefined){
                errMsgs(data.errFams, "#errFams");
                }
    
                famMems = JSON.parse(famMems);
                famMemsList(famMems);
                alert(`資料格式有誤，請查看！`); 
            }else if(data.msg == "fail"){
                alert(`住戶帳號更新失敗！`); 
            }
        },
        error: function(xhr){
            console.log("error");
            console.log(xhr);
        }

    });


    
});


//========================== 刪除住戶資料 ==========================
//移除
// 不能刪除住戶資料，因為很多其他表會關聯到住戶帳號(FK)！！！
$("#addr_table").on("click", ".fa-minus-circle", function () {

    let that = $(this);

    //要刪除的該筆資料的 住戶帳號
    let memAcct = that.closest("tr").find("td").eq(0).text();
    console.log(memAcct);

    let r = confirm("確認移除？");
    if(!r){
        return;
    }

    $.ajax({
        url:`${projectPath}/mem/MemberServlet.do`,
        type:"POST",
        data:{"action" : "deleteMemForBackEnd", "memAcct": memAcct},
        dataType:"json",
        success: function(data){
            if(data.msg == "success"){
                let msg = `住戶帳號${data.memAcct} 刪除成功！`;
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
    

});



//======================= 新增/刪除同住家人 =======================
// 新增同住家人
$("body").on("click", "span.plus", function () {
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
$("body").on("click", "span.minus", function () {
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
$("body").on("change", "#memUploadPic_file",function(e){
    //console.log("hello0");

    if(this.files.length > 0){
        //console.log("hello00");
        //console.log(this); // <input type="file" id="memUploadPic_file">
        memUploadPic(this.files[0]); 
    }else{
        let img_html;
        //如果是 編輯 的 彈窗，若使用者沒選圖片，則顯示資料庫中原本的圖片
        if( $(this).hasClass("edit_modal") ){  
            //console.log("hello2");

            //讀資料庫的圖片
            // /Okaeri/mem/MemberServlet.do?action=getImage&memAcct=gina123test1
            let src = `
                ${projectPath}/mem/MemberServlet.do?action=getImage&memAcct=${memAcct_forPic}
            `;
            img_html = `
                <img src="${src}" class="mem_uploadPic">
            `;
            
            //如果是 新增 的 彈窗，若使用者沒選圖片，則顯示 預設的圖片，告知使用者是儲存 預設的圖片
        }else{   
            //console.log("hello3");
            let photo_path = `images/user.png`;
            img_html = `
                <img src="${photo_path}" class="mem_uploadPic" style="height: 100px; width: 100px;">
            `;

        }
        
        $("div.profilePic_preview").empty();
        $("div.profilePic_preview").append(img_html);
    }
});