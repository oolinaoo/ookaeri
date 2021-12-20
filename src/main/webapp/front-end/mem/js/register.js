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





