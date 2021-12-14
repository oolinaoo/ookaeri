$(function () {
    //json from servlet
  
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
                                 <td class='facOpenDate'>${item.facOpenDate}</td>
                                 <td class='facOpenTime'>${item.facOpenTime}</td>
                                 <td class='facMax'>${item.facMax}</td>
                                 <td class='facState'>${item.facState}</td>
                                 <td class='facPhoto'>
									                  <img src="/okaeri/fac/facPhotoByFacNo?facNo=${item.facNo}" style='height: 50px; margin: 3px 0;'>
								                 </td>
                               </tr>
                               `;
            
            $("tbody#facilities_tbody").append(facilitiesList);
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
  