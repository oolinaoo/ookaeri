//==================日期的js在專案中，直接寫在jsp中==================
$.datetimepicker.setLocale('zh'); // kr ko ja(日文) en(英文)
$('#f_date1').datetimepicker({
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
$('#f_date1').datetimepicker({
    beforeShowDay: function(date) {
    if (  date.getYear() >  somedate2.getYear() || 
            (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
            (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        ) {
            return [false, ""]
        }
        return [true, ""];
}});
//==================日期的js在專案中，直接寫在jsp中==================


//==================刪除同住家人==================
$("div.famMemList").on("click", "span.minus", function () {
    //console.log(this);

    //jQuery的判斷節點是否存在，如果長度為0，就代表該節點不存在，可印出以下兩個節點做對照
    //console.log($(this).closest("div.famMem_block"));   //節點存在
    //console.log( $(this).closest("div.famMem_block").next());   //節點不存在
    //console.log( $(this).closest("div.famMem_block").next().length == 0 ); //節點不存在，長度為0

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
                <input type="text" name="famMem" value="" style="width:80px;margin:0;">
            </div>
        `;
        $(this).closest("div.famMem_block").after(famMemList_html);
    }
    
});


//==================上傳圖片==================
var memUploadPic_file_btn = document.getElementById("memUploadPic_file");
var mem_uploadPic_element = document.getElementsByClassName("mem_uploadPic")[0];

var memUploadPic = function(file){
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.addEventListener("load", function(){
        var mem_uploadPic_element = document.getElementsByClassName("mem_uploadPic");
        //console.log(reader.result);
        document.querySelector(".mem_uploadPic").style.backgroundImage = "url('" + reader.result + "')";
    });
};

memUploadPic_file_btn.addEventListener("change", function(){
    if(this.files.length > 0){
        memUploadPic(this.files[0]);
    }
})


//================== 圖片上傳保留資料測試 ==================

// memUploadPic_file_btn.addEventListener("change", function(){
//     //console.log(this);
//     //console.log(this.files);
//     if(this.files.length > 0){
//         // console.log(this.files);
//         // console.log(this.files[0]);
//         var send_data = {};
//         send_data.img_base64 = this.files[0];
//         console.log(send_data.img_base64);
//         console.log(send_data);
//         sessionStorage.setItem("form_data", JSON.stringify(send_data));
//         memUploadPic(this.files[0]);
//     }
//     //console.log(send_data);
// })

// var recovery_data =  function(){
//     if(sessionStorage.getItem("form_data") != null){
//       var form_data = JSON.parse(sessionStorage.getItem("form_data"));
//       console.log(form_data.img_base64);
//       //document.getElementById("memUploadPic_file").files=form_data.img_base64;
//     }
//   };
//   recovery_data();

// var img_base64_el = document.querySelector(".mem_uploadPic")
// //console.log(img_base64_el);
// //console.log(img_base64_el.style.backgroundImage);

// var btn_submit_el = document.getElementById("submit_btn");
// btn_submit_el.addEventListener("click", function(){
//     var send_data = {};
//     var img_base64_el = document.querySelector(".mem_uploadPic");
//     // console.log(img_base64_el);
//     if( img_base64_el.style.backgroundImage != ""){
//         send_data.img_base64 = img_base64_el.style.backgroundImage;
//     }
//     //console.log(send_data.img_base64);
//     sessionStorage.setItem("form_data", JSON.stringify(send_data));
//     // console.log(img_base64_el.style.backgroundImage);
//     // if(img_base64_el.style.backgroundImage==""){
//     //     console.log("yes");
//     // }
// });





