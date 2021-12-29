let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri


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
$("#select_build").on('change', function(){
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
$("#select_floor").on('change', function(){
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

var addrData; //用來存放後端傳來的地址資料

//================載入地址資料================//
function init(){

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

}


//================ 呼叫載入地址資料的函式 ================//
$(function(){
    init();
});



//================ 送出註冊資料 ================//
function famMemsList(famMemsList){
    if(famMemsList.length == 0){
      let famMemList_html = ``;
      famMemList_html = `
        <div class="famMem_block">
            <div class="plus_minus_btn">
              <span class="minus">-</span>
              <span class="plus">+</span>
            </div>
            <input type="text" class="famMems" name="famMem" value="" style="width:80px;margin:0;">
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
                <input type="text" class="famMems" name="famMem" value="${famMemsList[i]}" style="width:80px;margin:0;">
            </div>
        `;
      }
      $("div.famMemList").empty();
      $("div.famMemList").append(famMemList_html);
    }
}

$("button.submitBtn").on("click", function () {

    // 抓輸入的值，準備放入formData物件
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
    formData.append("memPhoto", memPhoto);
    formData.append("action", "register");
    // for(let [key, value] of formData.entries()) {
    //     console.log(key+ ', '+ value);
    // }

    $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`,
        type: "POST",
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
        success: function(data){
            //將錯誤訊息區塊裡面的span標籤清除
            $("span.error").empty();

            if(data.msg=="success"){

                location.href=`${projectPath}/login/login.html`;
                alert("帳號註冊成功，將跳轉到登入畫面");
                
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
                
            }else if(data.msg == "fail"){
                alert(`住戶帳號註冊失敗！`); 
            }
        },
        error: function(xhr){
            console.log("error");
            console.log(xhr);
        }

    })

});



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
$("div.memUploadPic_btn_block").on("change", "#memUploadPic_file",function(e){
    if(this.files.length > 0){
        //console.log(this); // <input type="file" id="memUploadPic_file">
        memUploadPic(this.files[0]); 
    }else{
        let photo_path = `images/user.png`;
        let img_html = `
            <img src="${photo_path}" class="mem_uploadPic" style="height: 100px; width: 100px;">
        `;
        $("div.profilePic_preview").empty();
        $("div.profilePic_preview").append(img_html);
    }
});

//======================= 新增/刪除同住家人 =======================
// 新增同住家人
$("div.famMemList").on("click", "span.plus", function () {
    // console.log("hello");
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
            <input type="text" class="famMems" value="" style="width:80px;margin:0;">
        </div>
        `;
        $(this).closest("div.famMem_block").after(famMemList_html);
    }
});  
  
// 刪除同住家人
$("div.famMemList").on("click", "span.minus", function () {
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


//================== 日期 ==================
$.datetimepicker.setLocale('zh'); // kr ko ja(日文) en(英文)
$('#memBirthday').datetimepicker({
    theme: '',          //theme: 'dark', //只有兩個顏色黑跟白可以用
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
$('#memBirthday').datetimepicker({
    beforeShowDay: function(date) {
    if (  date.getYear() >  somedate2.getYear() || 
            (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
            (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        ) {
            return [false, ""]
        }
        return [true, ""];
}});