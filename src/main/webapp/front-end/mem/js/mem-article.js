let path = window.location.pathname; //webapp的專案路徑
//console.log(path); // /Okaeri/back-end/acct-addr/member.html
var projectPath = path.substring(0, path.indexOf("/", 1)); // /Okaeri

let memAcct = "gina1";

//================載入所有資料================//
function init() {
  $.ajax({
    url: `/okaeri/forumArticle/listByMem`,
    type: "POST",
    data: {"memAcct": memAcct},
    dataType: "json",
    success: function (data) {
	console.log(data);
    //   let list_html = '';
    //   $.each(data, function (index, item) {
    //     list_html += `
    //       <tr>
    //         <td>${item.adminAcct}</td>
    //         <td>${item.adminPwd}</td>
    //         <td>${item.adminName}</td>
    //         <td>${item.adminPhone}</td>
    //         <td>${item.adminPos == 0 ? "保全" : "管委"}</td>
    //         <td>${item.adminState == 0 ? "在職" : "離職"}</td>
    //         <td class='del_edit_btn'>
    //             <i class='fa fa-edit'></i> 
    //             <div class="member_overlay" style="border: 1px solid red;"></div>
    //         </td>
    //       </tr>
    //     `;
    //   });
    //   //從後端傳回的Json中的職位、狀態的型別都是數值，所以上面的三元運算用「===」的型值相等 來判斷 -> 好像不一定都是數值！！？？
    //   //console.log(typeof(item.adminPos)); 
    //   $("#addr_table tbody").append(list_html);
    //   paging(); //呼叫分頁函式
    },
    error: function (xhr) {
      console.log("error");
      console.log(xhr);
    }
  })

}