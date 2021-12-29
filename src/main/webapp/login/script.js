/* Click on login to change and view the effect */
function cambiar_login() {
	document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_login";
	document.querySelector('.cont_form_login').style.display = "block";
	document.querySelector('.cont_form_sign_up').style.opacity = "0";

	setTimeout(function() {
		document.querySelector('.cont_form_login').style.opacity = "1";
	}, 400);

	setTimeout(function() {
		document.querySelector('.cont_form_sign_up').style.display = "none";
	}, 200);
	
}

function cambiar_sign_up(at) {
	document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_sign_up";
	document.querySelector('.cont_form_sign_up').style.display = "block";
	document.querySelector('.cont_form_login').style.opacity = "0";

	setTimeout(function() {
		document.querySelector('.cont_form_sign_up').style.opacity = "1";
	}, 100);

	setTimeout(function() {
		document.querySelector('.cont_form_login').style.display = "none";
	}, 400);

}

function ocultar_login_sign_up() {

	document.querySelector('.cont_forms').className = "cont_forms";
	document.querySelector('.cont_form_sign_up').style.opacity = "0";
	document.querySelector('.cont_form_login').style.opacity = "0";

	setTimeout(function() {
		document.querySelector('.cont_form_sign_up').style.display = "none";
		document.querySelector('.cont_form_login').style.display = "none";
	}, 500);

}

// LightBox
// returned cases //
const modalReturned = document.querySelector(".modal-returned");
const overlayReturned = document.querySelector(".overlay-returned");
const btnCloseModalReturned = document.querySelector(
  ".btn--close-modal-returned"
);
const btnsOpenModalReturned = document.querySelectorAll(".forgetPwdModal");

// returned cases //
const openModalReturned = function (e) {
  e.preventDefault();
  modalReturned.classList.remove("hidden");
  overlayReturned.classList.remove("hidden");
};

const closeModalReturned = function () {
  modalReturned.classList.add("hidden");
  overlayReturned.classList.add("hidden");
};

btnsOpenModalReturned.forEach((btn) =>
  btn.addEventListener("click", openModalReturned)
);
btnCloseModalReturned.addEventListener("click", closeModalReturned);
overlayReturned.addEventListener("click", closeModalReturned);

document.addEventListener("keydown", function (e) {
  if (e.key === "Escape" && !modalReturned.classList.contains("hidden")) {
    closeModalReturned();
  }
});

// ajax to memAcct and memPwd
$("#login-member").on("click", function()  {
		let url = "/okaeri/login/mem";
		let mem_data = {
			"memAcct" : $("#mAcct").val(),
			"memPwd" : $("#mPwd").val()
		}
		let xhr = new XMLHttpRequest();
		xhr.open("POST", url);
		xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用 JSON 格式
		let mem = JSON.stringify(mem_data); // 將物件資料轉成字串
		console.log(mem);
		xhr.send(mem); // 送出字串
		xhr.onload = function() {
			let data = JSON.parse(this.responseText);
			console.log(data);
			 if (data == true) {
			 window.location = "/okaeri/front-end/homepage/homepage.html"
			 } else {
				 alert("請輸入正確的帳號密碼");
			 }
		};
});

//ajax to adminAcct and adminPwd
$("#login-admin").on("click", function()  {
	let url = "/okaeri/login/admin";
	let admin_data = {
		"adminAcct" : $("#aAcct").val(),
		"adminPwd" : $("#aPwd").val()
	}
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用 JSON 格式
	let admin = JSON.stringify(admin_data); // 將物件資料轉成字串
	console.log(admin);
	xhr.send(admin); // 送出字串
	xhr.onload = function() {
		let data = JSON.parse(this.responseText);
		console.log(data);
		 if (data == true) {
		 window.location = "/okaeri/back-end/acct-addr/member.html"
		 } else {
			 alert("請輸入正確的帳號密碼");
		 }
	};
});

//ajax to sendMail
$(".save").on("click", function()  {
	let url = "/okaeri/login/sendMail";
	let mail = $("#email").val();
	let mem = $("#memAccount").val();
	let form_data = {
//		"memAcct" : "gina1",
//		"memEmail" : "xuanooo4@gmail.com"
		"memAcct" : mem,
		"memEmail" : mail
	}
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-type", "application/json"); // 告訴後端是用 JSON 格式
	let memEmail = JSON.stringify(form_data); // 將物件資料轉成字串
	console.log(memEmail);
	xhr.send(memEmail); // 送出字串
	xhr.onload = function() {
		let data = JSON.parse(this.responseText);
		console.log(data);
		alert("請記得至信箱收信")
		$("#email, #memAccount").val("");
		$(".modal-returned, .overlay-returned").fadeOut();
	};
});
