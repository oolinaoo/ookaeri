// json from servlet
$(function () {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", "/okaeri/news/listAllA", true);
	xhr.send();
	xhr.onload = function () {
	  let data = JSON.parse(this.responseText);
	  console.log(data);
	  	for (let i = 0; i < data.length; i++) {
	  		if ($(".hidden").val() == i) {
	  			$(".page-content").after(
	  				 "<div class='modal-returned hidden'>" +
	  				 "<div class='modal-container'>" +
	  				 "<button class='btn--close-modal-returned'>&times;</button>" +
	  				 "<h2 class='modal-header'>" + data[i].newsTitle + "</h2>" +
	  				 "<p class='modal-sub-header'>" +
	  				 data[i].adminAcct + "<span>✎</span>" + "<span class='modal-sub-header_time'>"
	  				 +
	  				 data[i].newsTime + "</span>" +
	  				 "</p>" +
	  				 "<div class='news-content'>" +
	  				 "<p>" + data[i].newsContent + "</p>" +
	  				 "</div>" +
	  				 "</div>" +
	  				 "</div>" +
	  				 "<div class='overlay-returned hidden'></div>"
	  			)
	  		}
	  	}
	 // LightBox
	 // returned cases //
	 const modalReturned = document.querySelector(".modal-returned");
	 const overlayReturned = document.querySelector(".overlay-returned");
	 const btnCloseModalReturned = document.querySelector(
	   ".btn--close-modal-returned"
	 );
	 const btnsOpenModalReturned = document.querySelectorAll(".title_link");

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
	};
  });
