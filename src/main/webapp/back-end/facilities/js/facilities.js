$(function () {
    //json from servlet

    // 公設開放日(底下呼叫)
    function listFacDate(date){
      $.ajax({
        url: "/okaeri/fac/listAllDate",
        type: "GET",
        data:{
          "facNo": date
        },
        dataType: "json",
  	    headers:{
		      "Content-Type": "application/json"
	      },
        success: function (data) {
            $(`td.facOpenDate${date}`).append(data);
        },
        error: function (xhr) {
          console.log("error");
          console.log(xhr);
        }
      })
    }

    // 公設開放時間(底下呼叫)
    function listFacTime(time){
      $.ajax({
        url: "/okaeri/fac/listAllTime",
        type: "GET",
        data:{
          "facNo": time
        },
        dataType: "json",
  	    headers:{
		      "Content-Type": "application/json"
	      },
        success: function (data) {
            $(`td.facOpenTime${time}`).append(data);
        },
        error: function (xhr) {
          console.log("error");
          console.log(xhr);
        }
      })
    }

    // 公設資料表列
    function listAllFacilities() {
      $.ajax({
        url: "/okaeri/fac/listAllFac",
        type: "POST",
        dataType: "json",
  	    headers:{
		      "Content-Type": "application/json"
	      },
        success: function (data) {
          $("tbody#facilities_tbody").empty();
  
          var facilitiesList = "";
          $.each(data, function (index, item) {
            facilitiesList = `
                               <tr>
                                 <td class='facNo'>${item.facNo}</td>
                                 <td class='facName'>${item.facName}</td>
                                 <td class='facOpenDate${item.facNo}'></td>
                                 <td class='facOpenTime${item.facNo}'></td>
                                 <td class='facMax'>${item.facMax}</td>
                                 <td class='facState'>${item.facState == 0 ? "上架(0)" : "下架(1)"}</td>
                                 <td class='facPhoto'>
									                  <img src="/okaeri/fac/facPhotoByFacNo?facNo=${item.facNo}" style='height: 50px; margin: 3px 0;'>
								                 </td>
                               </tr>
                               `;
            
            $("tbody#facilities_tbody").append(facilitiesList);
            listFacDate(item.facNo);
            listFacTime(item.facNo);
          });
  
          // 呼叫 分頁
          $("body").append("<script src='./js/pagination.js' type='text/javascript'></script>");
        },
        error: function (xhr) {
          console.log("error");
          console.log(xhr);
        }
      });
    }
  
    // 呼叫 抓資料庫的函式
    listAllFacilities();

});
  