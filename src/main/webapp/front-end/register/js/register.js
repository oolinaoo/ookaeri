//$.datetimepicker.setLocale('zh'); // kr ko ja(日文) en(英文)
//$('#f_date1').datetimepicker({
//    theme: '',          //theme: 'dark', //只有兩個顏色黑跟白可以用
//    timepicker: false,   //timepicker: false, //可以選幾點幾分幾秒
//    step: 1,            //step: 60 (這是timepicker的預設間隔60分鐘) //只能到間隔1分鐘，不能以秒間隔
//    format: 'Y-m-d',
//    value: "",  //起始日期 
//    //disabledDates:    ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含  //這不是寫死的，可以從資料庫撈出來就可以變成是動態的
//    //startDate:	        '2017/07/10',  // 起始日
//    // minDate:           '-1970-01-01', // 去除今日(不含)之前 //只能選到今天以後的日子
//    //maxDate:           //'+1970-01-01'  // 去除今日(不含)之後 //只能選到今日以前的（例如選生日）//寫+2021/11/11會無效，老師在下面有寫萬用的方法
//
//});
//
//
////      2.以下為某一天之後的日期無法選擇
//var somedate2 = new Date();
//$('#f_date1').datetimepicker({
//    beforeShowDay: function(date) {
//    if (  date.getYear() >  somedate2.getYear() || 
//            (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
//            (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
//        ) {
//            return [false, ""]
//        }
//        return [true, ""];
//}});


//==================刪除同住家人==================
$("div.famMemList").on("click", "span.minus", function () {
	//console.log(this);
	//如果不是第一個才刪除框框
	if (!$(this).closest("div.famMem_block").is(":first-child")) {
	    $(this).closest("div.famMem_block").fadeOut(500, function () {
	        //console.log(this);
	        $(this).remove();
	    });
	} else {     
	    $(this).closest("div.famMem_block").find("input").val("");
	}
});

//==================新增同住家人==================
$("div.famMemList").on("click", "span.plus", function () {
    let famMem = $(this).closest("div.famMem_block").children("input").val();

    if(famMem.trim() != ""){
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