let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri

//var memAcct = "gina123test1";
//var memPwd = "passwordTest";

//ajax to logout
$("#profile_logout").on("click", function()  {
	console.log("enter");
	let url = "/okaeri/login/logout";
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.send(); 
	xhr.onload = function() {
		let data = JSON.parse(this.responseText);
		console.log(data);
		 if (data == true) {
		 window.location = "/okaeri/login/login.html"
		 }
	};
});


//取得登入的帳號
var memAcct;
function getMemAcct(){ 

	$.ajax({
		url: "/okaeri/login/getUserPhoto",
	    type: "GET",
	    data: "",
	    dataType: "json",
	    success: function (data) {
	      console.log(data);
	      $("#navbar_profile_memAcct").prepend(`
		  	<img class="profile_photo" src="data:image/jpeg;base64,${data.memPhoto}" alt="user" />
	      `);
	    },
	    error: function (xhr) {
	        console.log("error");
	    },
		
	});


	$.ajax({
	  url: "/okaeri/login/getMemSession",
	  type: "GET",
	  data: "",
	  dataType: "json",
	  success: function (data) {
		console.log(data);
        memAcct = data.memAcct;
        console.log(memAcct);
		$("#navbar_profile_memAcct").append(
				`
					<span id="navbar_profile_memAcct_span">${data.memAcct}</span>
				`
			);
		$("#navbar_profile_memAcct_span").after(
				`
					<span hidden>${data.memName}</span>
				`
			);
	  },
	    error: function (xhr) {
	      console.log("error");
	    },
	});
}

$(function(){
    getMemAcct();
});


$("#memPwd_btn_submit").on('click', function(){

    //將錯誤訊息區塊裡面的span標籤清除
    $("div.errBlock").empty();
    
    // let r = confirm("確認修改？");
    // if(!r){
    //     $("#oldPwd").val("");
    //     $("#newPwdOne").val("")
    //     $("#newPwdTwo").val("")
    //     return; //若進入此判斷式，就將輸入過的值清除，並結束程式
    // }


    //抓到使用者輸入舊密碼的值
    let oldPwd = $("#oldPwd").val().trim();

    //抓到使用者輸入新密碼的值
    let newPwdOne = $("#newPwdOne").val().trim();

    //抓到使用者第二次輸入新密碼的值
    let newPwdTwo = $("#newPwdTwo").val().trim();

    let data = {
        "action": "update_pwd",
        "memAcct": memAcct,
        "oldPwd": oldPwd,
        "newPwdOne": newPwdOne,
        "newPwdTwo": newPwdTwo
    };
    //console.log(data);

    function err(errItem, errTag){
        let errHtml = `
            <span class="err">${errItem}</span>
        `;
        $(errTag).append(errHtml);
    }

    $.ajax({
        url: `${projectPath}/mem/MemberServlet.do`, 
        type: "POST",                 
        data: data,     
        dataType: "json",  
        success: function(data){
            //console.log(data);

            if( data.msg == "success" ){
                $("#oldPwd").val("");
                $("#newPwdOne").val("")
                $("#newPwdTwo").val("")
                alert("密碼修改成功！");
            }else if( data.msg == "fail" ){

                //如果Json物件中沒有該key，就會是undefined，undefined會是false
                if(data.errOldPwd != undefined){
                    err(data.errOldPwd, "#errOldPwd");
                }

                if(data.errNewPwdOne != undefined){
                    err(data.errNewPwdOne, "#errNewPwdOne");
                }

                if(data.errNewPwdTwo != undefined){
                    err(data.errNewPwdTwo, "#errNewPwdTwo");
                }
            }else if( data.msg == "wrongOldPwd" ){
                alert("舊密碼錯誤！");
            }
        },
        error: function(xhr){    // request 發生錯誤的話執行
            // $("#oldPwd").val("");
            // $("#newPwdOne").val("")
            // $("#newPwdTwo").val("")
            alert("請求發生錯誤");
            console.log("error");
        },

    });

});