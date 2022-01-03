let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri

// var memAcct = "gina1";

function famMemsList(famMemsList){
  if(famMemsList.length == 0){
    let famMemList_html = ``;
    famMemList_html = `
      <div class="famMem_block">
        <div class="plus_minus_btn">
            <span class="minus">-</span>
            <span class="plus">+</span>
        </div>
        <input type="text" value="" class="famMems" style="width:75px;">
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
        <input type="text" value="${famMemsList[i]}" class="famMems" style="width:75px;">
      </div>
      `;
    }
    $("div.famMemList").empty();
    $("div.famMemList").append(famMemList_html);
  }
}


//================載入住戶資料================//
function init(){

  //讀資料庫的圖片
  //let path = window.location.pathname; //webapp的專案路徑
  //console.log(path); // /Okaeri/front-end/mem/mem-profile.html(在Eclipse中)
  //console.log(path.substring(0, path.indexOf("/", 1))); // /Okaeri (在Eclipse中)
  //console.log(path.substring(0, path.indexOf("/mem-", 1))); // /Okaeri/front-end/mem (在Eclipse中)
  //console.log($("img.mem_uploadPic").attr("src"));
  // /Okaeri/front-end/mem/MemberServlet.do?action=getImage&memAcct=gina123test1
  
  let src = `${projectPath}/mem/MemberServlet.do?action=getImage&memAcct=${memAcct}`;

  let img_html = `
    <img src="${src}" class="mem_uploadPic">
  `;
  $("div.profilePic_preview").append(img_html);
  //$("img.mem_uploadPic").attr("src", "images/profile_png/Cliff.png");

  $.ajax({
    url: `${projectPath}/mem/MemberServlet.do`,           // 資料請求的網址
    type: "GET",                  // GET | POST | PUT | DELETE | PATCH
    data: {"action": "get_one_mem", 
           "memAcct": memAcct,
          },                  
    dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
    success: function(data){      // request 成功取得回應後執行
      $("#memAcct").val(data.memAcct);
      $("#memName").val(data.memName);
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
      $("#memBirthday").val(data.memBirthday);
      $("#memEmail").val(data.memEmail);
      $("#memPhone").val(data.memPhone);

      $("#addrBuild").val(data.addrBuild);
      $("#addrFloor").val(data.addrFloor);
      $("#addrRoom").val(data.addrRoom);

      //console.log(typeof(data.famMems)); //陣列的Json字串
      let famMemsLst = JSON.parse(data.famMems); //陣列的Json字串轉陣列物件
      //console.log(famMemsLst);
      famMemsList(famMemsLst); //放入的參數名稱不能跟呼叫函式名稱相同，不然會報錯！


    },
    error: function(xhr, textStatus, errorThrown){   
      alert("資料載入失敗");
      console.log("error");
      console.log(xhr);

    }
  });

}

//================呼叫載入所有資料的函式================//
$(function(){
  getMemAcct();
});



//================ 住戶修改資料 formData版===============//
$("#memProfile_btn_submit").on("click", function(){
  let r = confirm("確認修改？");
  if(!r){
   return;  //按「取消」，就直接結束程式
  }
  let memPhoto = document.getElementById("memUploadPic_file").files[0];
  let memName = $("#memName").val().trim();
  let memSex;
  for(let i = 0; i <= $("input.memSex").length; i++){
    if($("input.memSex").eq(i).prop("checked")){  // 判斷有無勾選
      memSex = $("input.memSex").eq(i).val(); // 男/女/其他
      //console.log(memSex);
    }
  }
  let memBirthday = $("#memBirthday").val().trim();
  let memEmail = $("#memEmail").val().trim();
  let memPhone = $("#memPhone").val().trim();

  let famMems = new Array();
  for(let i = 0; i < $("input.famMems").length; i++){
    let aFam = $("input.famMems").eq(i).val().trim();
    famMems[i] = aFam;
  }
  //去除為空字串的元素
  famMems = famMems.filter(function(item){
    return item !== "";
  });
  famMems = JSON.stringify(famMems);

  let memPwd = $("#memPwd").val().trim();

  //將以上的資料塞入fomrData物件
  let formData = new FormData();
  formData.append("action", "profile_update");
  formData.append("memAcct", memAcct);
  formData.append("memPhoto", memPhoto);
  formData.append("memName", memName);
  formData.append("memSex", memSex);
  formData.append("memBirthday", memBirthday);
  formData.append("memEmail", memEmail);
  formData.append("memPhone", memPhone);
  formData.append("famMems", famMems);
  formData.append("memPwd",memPwd);

  // let path = window.location.pathname; //webapp的專案路徑
  // let src = `
  //   ${path.substring(0, path.indexOf("/mem-", 1))}/MemberServlet.do
  // `

  $.ajax({
    url: `${projectPath}/mem/MemberServlet.do`,
    type: "POST",
    data: formData,
    dataType: "json",
    processData: false,
    contentType: false,
    success:function(data){
      console.log(data);
      //將錯誤訊息區塊裡面的span標籤清除
      $("div.col-2").empty();

      if(data.msg == "success"){
        famMems = JSON.parse(famMems);
        famMemsList(famMems);
        $("#memPwd").val("");
        $("#memUploadPic_file").val("");
        alert("資料更新成功！");

      }else if(data.msg == "errorMsgs"){

        function errMsgs(errItem, errTag){
          let errHtml = `
            <span class="errorMsg">${errItem}</span>
          `;
          $(errTag).append(errHtml);
        }

        //如果Json物件中沒有該key，就會是undefined，undefined會是false
        if(data.errName != undefined){
          errMsgs(data.errName, "#errName");
        }

        if(data.errBirth != undefined ){
          errMsgs(data.errBirth, "#errBirth");
        }

        if(data.errEmail != undefined){
          errMsgs(data.errEmail, "#errEmail");
        }
        
        if(data.errPhone != undefined){
          errMsgs(data.errPhone, "#errPhone");
        }

        if(data.errFams != undefined){
          errMsgs(data.errFams, "#errFams");
        }

        famMems = JSON.parse(famMems);
        famMemsList(famMems);
        $("#memPwd").val("");
        $("#memUploadPic_file").val("");

      }else if(data.msg=="wrongPwd"){
        famMems = JSON.parse(famMems);
        famMemsList(famMems);
        $("#memPwd").val("");
        $("#memUploadPic_file").val("");
        alert("密碼錯誤！");

      }

    },
    error: function(xhr, textStatus, errorThrown){   
      alert("請求發生問題");
      console.log("error");
      console.log(xhr);
    }
  });

});


//================== 上傳圖片後，顯示在預覽區塊 start==================
// 圖片預覽
//var img_base64;
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
$("#memUploadPic_file").on("change", function(e){
  if(this.files.length > 0){
    //console.log(this); // <input type="file" id="memUploadPic_file">
    memUploadPic(this.files[0]); 
  }else{

    // let img_html = `
    //   <img src="" class="mem_uploadPic">
    // `;

    //讀資料庫的圖片
    // /Okaeri/mem/MemberServlet.do?action=getImage&memAcct=gina123test1
    let src = `
        ${projectPath}/mem/MemberServlet.do?action=getImage&memAcct=${memAcct}
    `;
    img_html = `
        <img src="${src}" class="mem_uploadPic">
    `;


    $("div.profilePic_preview").empty();
    $("div.profilePic_preview").append(img_html);
  }
});
//================== 上傳圖片後，顯示在預覽區塊 end ==================


//==================刪除同住家人==================
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

//==================新增同住家人==================
$("div.famMemList").on("click", "span.plus", function () {

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
              <input type="text" class="famMems" value="" style="width:75px;">
          </div>
        `;
        $(this).closest("div.famMem_block").after(famMemList_html);
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