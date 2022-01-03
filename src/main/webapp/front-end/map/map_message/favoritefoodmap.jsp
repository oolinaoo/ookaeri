<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="web.mapMessage.service.*"%>
<%@ page import="web.mapMessage.entity.*"%>
<%@ page import="java.util.*"%>

<%
	Map_MessageService dao =new Map_MessageService();
	List<Map_MessageVO> msgList =dao.getAll();
	request.setAttribute("msgList", msgList);
%>
<%
	request.setAttribute("userName","gina2");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/map/css/map_tab.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/map/css/map.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/map/css/mapInfo.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/map/css/header_footer_style.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/front-end/pack/css/notify.css" />
<link rel="stylesheet" href="/okaeri/chatroom/css/chat.css" />
<title>美食地圖我的最愛</title>
<link rel="icon" href="<%=request.getContextPath()%>/front-end/map/images/logo2.png" />
</head>

<body>
	<%-- <span>商家編號: ${sessionScope.storeNoUsedInCommentArea}</span> --%>
	<%-- <span>商家資訊: ${sessionScope.storeINFO}</span> --%>
<%-- <span><%=request.getContextPath()%></span> --%>


		<div id="header">
		<p class="header_logo">
			<a href=""> <img
				src="<%=request.getContextPath()%>/front-end/map/images/logo2.png"
				alt="logo" width="80" />
			</a>
		</p>
		<ul class="navbar_list">
			<li class="navbar_list_open" style="width: 130px;"><a href="<%=request.getContextPath()%>/front-end/news/news.html">社區交流</a>
				<ol class="navbar_list_open_sub">
					<li><a href="<%=request.getContextPath()%>/front-end/news/news.html">- 社區公告</a></li>
					<li><a href="<%=request.getContextPath()%>/front-end/rule/rule.html">- 社區公約</a></li>
					<li><a href="<%=request.getContextPath()%>/front-end/forum/article/forum.html">- 社區論壇</a></li>
				</ol></li>
			<li class="navbar_list_open" style="width: 130px;"><a href="<%=request.getContextPath()%>/front-end/payment/managent.html">管理費</a>
				<ol class="navbar_list_open_sub">
					<li><a href="<%=request.getContextPath()%>/front-end/payment/management_paypage.html">- 繳費</a></li>
					<li><a href="<%=request.getContextPath()%>/front-end/payment/management_history.html">- 繳費記錄</a></li>
				</ol></li>
			<li class="navbar_list_open" style="width: 130px;"><a href="<%=request.getContextPath()%>/front-end/pack/package.html">我的包裹</a>
				<ol class="navbar_list_open_sub">
					<li><a href="<%=request.getContextPath()%>/front-end/pack/package_take.html">- 我的包裹</a></li>
					<li><a href="<%=request.getContextPath()%>/front-end/pack/package_history.html">- 包裹記錄</a></li>
				</ol></li>
			<li class="navbar_list_open" style="width: 130px;"><a href="<%=request.getContextPath()%>/front-end/facilities/facilities_index.html">公設預約</a>
				<ol class="navbar_list_open_sub">
					<li><a href="<%=request.getContextPath()%>/front-end/facilities/facilities_index.html">- 我要預約</a></li>
					<li><a href="<%=request.getContextPath()%>/front-end/facilities/facilities_history.html">- 預約記錄</a></li>
				</ol></li>
			<li class="navbar_list_open" style="width: 130px;"><a href="<%=request.getContextPath()%>/front-end/map/map_message/foodmap.jsp">美食地圖</a></li>

			<div class="dropdown">
          <div id="badge"></div>
          <button class="dropbtn">
            <img class="alert_icon" src="<%=request.getContextPath()%>/front-end/map/images/alert.png" alt="alert" />
          </button>
          <div class="dropdown-content">
            <div class="dropdown_selector">

            </div>
          </div>
        </div>
			<li class="navbar_icon navbar_list_open">
          <a href="/okaeri/front-end/mem/mem-index.html"
            ><img class="user_icon" src="<%=request.getContextPath()%>/front-end/map/images/user.png" alt="user"
          /></a>
          <ol class="navbar_list_open_sub navbar_profile">
            <li>
              <div class="navbar_profile_user">
                <a id="navbar_profile_memAcct" href="/okaeri/front-end/mem/mem-index.html"
                  ></a
                >
              </div>
            </li>
            <hr style="border-style: dashed; color: #e9d7b3" />
            <li class="navbar_profile_memberCenter">
              <a id="profile_memberCenter" href="/okaeri/front-end/mem/mem-index.html">住戶中心</a>
            </li>
            <li class="navbar_profile_logout">
              <a id="profile_logout" href="">登出</a>
            </li>
          </ol>
        </li>
		</ul>
	</div>
	<div class="map_tab_container">
		<div class="tab_list_block">
			<ul class="tab_list">
				<li><a
					href="<%=request.getContextPath()%>/front-end/map/map_message/foodmap.jsp"
					data-target="tab1" class="tab">美食地圖</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front-end/map/map_message/favoritefoodmap.jsp"
					data-target="tab2" class="tab -on">我的最愛</a></li>
			</ul>
		</div>
		<div class="tab_contents">
			<div class="tab tab1"></div>
			<div class="tab tab2 -on">

				<div id="mapInfodialog" class="mapInfodialog-layout">
					<div class="mapInfo">
						<span
							style="color: red; position: absolute; top: 75px; color: red; left: 90px;">${requestScope.errorMessage}</span>
						<form class="searchForm"
							action="<%=request.getContextPath()%>/controller/FavServletController.do"
							style="margin: auto; max-width: 300px">
							<input type="text" name="search" style="margin-top: 30px;"
								value="" placeholder="請輸入商家名稱(不含空白)"> <input
								type="hidden" name="action" value="searchStore">
							<button type="submit" style="margin-top: 30px;">
								<i class="fa fa-search"></i>
							</button>
						</form>

						<p class="mapInfoHead">商家資訊</p>
						<p class="mapInfoContent">${sessionScope.storeINFO}</p>
						<form class="buttonFavForm"
							action="<%=request.getContextPath()%>/controller/FavServletController.do">
							<!-- 							<input type="hidden" value="MEM_ACCT" name="mem"> -->
							<input type="hidden"
								value="${sessionScope.storeNoUsedInCommentArea}" name="storeNo" />
							<input type="hidden" value="${sessionScope.memAcct}" name="mem">
							<input type="hidden" value="inputToFav" name="action" />
							<button class="buttonFav" type="submit">加入至最愛</button>
						</form>

						<form class="buttonDelteFavForm"
							action="<%=request.getContextPath()%>/controller/FavServletController.do">
							<!-- 							<input type="hidden" value="MEM_ACCT" name="mem">  -->
							<input type="hidden"
								value="${sessionScope.storeNoUsedInCommentArea}" name="storeNo" />
							<input type="hidden" value="${sessionScope.memAcct}" name="mem">
							<input type="hidden" value="deleteFromFav" name="action" />
							<button class="buttonDelteFav" type="submit">從最愛刪除</button>
						</form>


					</div>
					<div class="message">
					
						<p class="favHead"
							style="margin-top: 0px; margin-bottom: 0px;">我的最愛</p>
					
						<form class="inputcomment"
							action="<%=request.getContextPath()%>/controller/FavServletController.do"
							METHOD="post" style="margin: auto;">
						<table class="fav-area" width="150px">
							
							<tbody>

								<jsp:useBean id="Fav" scope="page" class="web.mapFavorite.service.Map_My_FavoriteService" /><!-- 注意!!!，為了方便，useBean一律跟EL運算式配合 -->
								
								<tr>
									
										<td>
											<select size="1" name="selectFav" >
												<c:forEach var="favVO" items="${Fav.all}">
													<c:if test="${ favVO.MEM_ACCT==sessionScope.memAcct}">
														<c:if test="${favVO.MAP_STORE_NO==1}">
															<option value="萬有全涮羊肉10491台灣台北市中山區南京東路三段223巷8號" >萬有全涮羊肉
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==2}">
															<option value="聚馥園餐廳104台灣台北市中山區南京東路三段219號3樓" >聚馥園餐廳
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==3}">
															<option value="丘一司早午餐遼寧店104台灣台北市中山區遼寧街114號1樓" >丘一司早午餐遼寧店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==4}">
															<option value="熊派餐廳10491台灣台北市中山區南京東路三段194巷1號" >熊派餐廳
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==5}">
															<option value="麥當勞-台北南京五店10491台灣台北市中山區南京東路三段210號1樓" >麥當勞-台北南京五店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==6}">
															<option value="壽司飯丸專門店-兄弟大飯店105台灣台北市松山區南京東路三段255號1樓" >壽司飯丸專門店-兄弟大飯店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==7}">
															<option value="海底撈火鍋慶城店105台灣台北市松山區慶城街1號3F" >海底撈火鍋慶城店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==8}">
															<option value="爍場串燒‧夜食10547台灣台北市松山區復興北路141巷6弄6號1樓" >爍場串燒‧夜食
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==9}">
															<option value="TheBrassMonkey銅猴子復興店10491台灣台北市中山區復興北路166號" >TheBrassMonkey銅猴子復興店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==10}">
															<option value="CinCinOsteria請請義大利餐廳(慶城店)10491台灣台北市中山區復興北路166號" >CinCinOsteria請請義大利餐廳(慶城店)
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==11}">
															<option value="三個傻瓜印度蔬食南京店104台灣台北市中山區長春路318號" >三個傻瓜印度蔬食南京店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==12}">
															<option value="香米泰國料理復北店10491台灣台北市中山區復興北路174號" >香米泰國料理復北店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==13}">
															<option value="ＷonderBar&Lounge105台灣台北市松山區復興北路183號" >ＷonderBar&Lounge
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==14}">
															<option value="HOOTERS美式餐廳105台灣台北市松山區慶城街18號" >HOOTERS美式餐廳
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==15}">
															<option value="慶城海南雞飯105台灣台北市松山區慶城街16巷8號" >慶城海南雞飯
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==16}">
															<option value="ToBeSmoothie台北南京復興店104台灣台北市中山區遼寧街105巷48號" >ToBeSmoothie台北南京復興店
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==17}">
															<option value="HAPPYBOBI波樂手搖茶飲10488台灣台北市中山區遼寧街107-3號1樓" >HAPPYBOBI波樂手搖茶飲
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==18}">
															<option value="蓮RENTaipei105台灣台北市松山區南京東路三段256巷19號" >蓮RENTaipei
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==19}">
															<option value="長壽三好庵105台灣台北市松山區南京東路三段256巷20弄1號" >長壽三好庵
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==20}">
															<option value="犂園湯包館105台灣台北市松山區南京東路三段256巷24號" >犂園湯包館
														</c:if>
														<c:if test="${favVO.MAP_STORE_NO==21}">
															<option value="はま寿司HAMA壽司南京復興店105403台灣台北市松山區南京東路三段270號" >はま寿司HAMA壽司南京復興店
														</c:if>
													</c:if>
												</c:forEach>
											</select>
											
										</td>
									
								</tr>

							</tbody>
						</table>
						<input type="hidden" value="choseFav" name="action">
						<input class="decide" type="submit" value="確定">
					</form>

					</div>
				</div>
				<div id="map" class="map-layout">
					<center>正在讀取地圖資訊.....</center>
				</div>

			</div>
		</div>
	</div>
<!-- 	<!--- 聊天室 ---> -->
<!-- 	<div class="chatroom"> -->
<!--     	<div class="friendlist"> -->
    		
<!--     	</div> -->
<!--     	<div class="chatarea"> -->
<!--     		<div class="statusOutput"></div> -->
<!--     		<div class="msgbox"> -->
<!--     			<ul id="area"></ul> -->
<!--     			<input type="text" class="textbox"> -->
<!--     			<input type="submit" class="send" value="送出"> -->
<!--     		</div> -->
<!--     	</div> -->
<!--     </div> -->
<!--     <button class="msgicon"><img class="msgicon"  src="/okaeri/chatroom/images/messenger.png"></button> -->
<!--     <span class="acct" style="display:none;"></span> -->

	<script>
    function initMap() {
      const options = {
        zoom: 18,
        center: { lat: 25.0519297, lng: 121.543304 }
      }
      const styles = {
        hide: [
          {
            featureType: "poi.business",
            stylers: [{ visibility: "off" }],
          },

        ],
      };
      const map = new google.maps.Map(document.getElementById("map"), options);
      map.setOptions({ styles: styles["hide"] });

      /*
      Marker
      ==========================*/
      const contentString =
        '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">萬有全涮羊肉</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
        '<p>10491台灣台北市中山區南京東路三段223巷8號</p>' +
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">' +
        	'<input type="hidden" value="confirm" name="action"></input>' +
        	'<input type="hidden" value="萬有全涮羊肉10491台灣台北市中山區南京東路三段223巷8號"  name="storeInfo"></input>' +
        	'<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>' +
        '</form>' +
        '</div>';


      const uluru = { lat: 25.05302035821338, lng: 121.5433576441819 };
      const infowindow = new google.maps.InfoWindow({
        content: contentString,

      });

      const marker = new google.maps.Marker({
        position: uluru,
        map,

        label: {
          color: "black",
          fontWeight: 'bold',
          text: "萬有全涮羊肉",
        }


      });

      marker.addListener("click", () => {
        infowindow.open({
          anchor: marker,
          map,
          shouldFocus: false,
        });
      });
      /*
        Marker0
        ==========================*/
      const contentString0 =
        '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">聚馥園餐廳</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
        '<p>104台灣台北市中山區南京東路三段219號3樓</p>' +
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">' +
        	'<input type="hidden" value="confirm" name="action"></input>' +
        	'<input type="hidden" value="聚馥園餐廳104台灣台北市中山區南京東路三段219號3樓" name="storeInfo"></input>' +
        	'<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>' +
        '</form>' +
        '</div>';

      const uluru0 = { lat: 25.0521309129688, lng: 121.54318912993537 };
      const infowindow0 = new google.maps.InfoWindow({
        content: contentString0,
      });

      const marker0 = new google.maps.Marker({
        position: uluru0,
        map,

        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "聚馥園餐廳",
        },
      });

      marker0.addListener("click", () => {
        infowindow0.open({
          anchor: marker0,
          map,
          shouldFocus: false,
        });
      });

      /*
        Marker1
        ==========================*/
      const contentString1 =
        '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">丘一司早午餐遼寧店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
        '<p>104台灣台北市中山區遼寧街114號1樓</p>' +
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
        	'<input type="hidden" value="confirm" name="action"></input>' +
        	'<input type="hidden" value="丘一司早午餐遼寧店104台灣台北市中山區遼寧街114號1樓" name="storeInfo"></input>' +
        	'<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>' +
        '</form>' +
        '</div>';


      const uluru1 = { lat: 25.052763445491113, lng: 121.54194143779485 };
      const infowindow1 = new google.maps.InfoWindow({
        content: contentString1,
      });

      const marker1 = new google.maps.Marker({
        position: uluru1,
        map,

        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "丘一司早午餐遼寧店",
        },
      });

      marker1.addListener("click", () => {
        infowindow1.open({
          anchor: marker1,
          map,
          shouldFocus: false,
        });
      });
      
      /*
      Marker2
      ==========================*/ 
      const contentString2 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">熊派餐廳</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>10491台灣台北市中山區南京東路三段194巷1號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
    		'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="熊派餐廳10491台灣台北市中山區南京東路三段194巷1號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      const contentSend2="熊派餐廳10491台灣台北市中山區南京東路三段194巷1號"
      
      const uluru2 = { lat: 25.051548235064985, lng:121.54283725396347 };
      const infowindow2 = new google.maps.InfoWindow({
        content: contentString2,
      });

      const marker2 = new google.maps.Marker({
        position: uluru2,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "熊派餐廳",
        },
      });

      marker2.addListener("click", () => {
        infowindow2.open({
          anchor: marker2,
          map,
          shouldFocus: false,
        });
      });
      
      /*
      Marker3
      ===============================*/ 
      const contentString3 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">麥當勞-台北南京五店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>10491台灣台北市中山區南京東路三段210號1樓</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="麥當勞-台北南京五店10491台灣台北市中山區南京東路三段210號1樓" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
      
      const uluru3 = { lat: 25.051866429879745, lng:121.5433576441819 };
      const infowindow3 = new google.maps.InfoWindow({
        content: contentString3,
      });

      const marker3 = new google.maps.Marker({
        position: uluru3,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text:  "麥當勞-台北南京五店",
        },
      });

      marker3.addListener("click", () => {
        infowindow3.open({
          anchor: marker3,
          map,
          shouldFocus: false,
        });
      });

      /*Marker4
      ===============================*/ 
      const contentString4 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">壽司飯丸專門店-兄弟大飯店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區南京東路三段255號1樓</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="壽司飯丸專門店-兄弟大飯店105台灣台北市松山區南京東路三段255號1樓" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
      
      const uluru4 = { lat: 25.052288464821686, lng:121.54463557155488 };
      const infowindow4 = new google.maps.InfoWindow({
        content: contentString4,
      });

      const marker4 = new google.maps.Marker({
        position: uluru4,
        map,
        
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "壽司飯丸專門店-兄弟大飯店",
        },
      });

      marker4.addListener("click", () => {
        infowindow4.open({
          anchor: marker4,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker5
      ===============================*/ 
      const contentString5 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">海底撈火鍋慶城店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區慶城街1號3F</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="海底撈火鍋慶城店105台灣台北市松山區慶城街1號3F" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
       
      const uluru5 = { lat: 25.052984153286335, lng:121.54441573900166 };
      const infowindow5 = new google.maps.InfoWindow({
        content: contentString5,
      });

      const marker5 = new google.maps.Marker({
        position: uluru5,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "海底撈火鍋慶城店",
        },
      });

      marker5.addListener("click", () => {
        infowindow5.open({
          anchor: marker5,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker6
      ===============================*/ 
      const contentString6 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">爍場串燒‧夜食</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>10547台灣台北市松山區復興北路141巷6弄6號1樓</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="爍場串燒‧夜食10547台灣台北市松山區復興北路141巷6弄6號1樓" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
       
      const uluru6 = { lat: 25.053790863348787, lng:121.54461958689285 };
      const infowindow6 = new google.maps.InfoWindow({
        content: contentString6,
      });

      const marker6 = new google.maps.Marker({
        position: uluru6,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "爍場串燒‧夜食",
        },
      });

      marker6.addListener("click", () => {
        infowindow6.open({
          anchor: marker6,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker7
      ===============================*/ 
      const contentString7 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">TheBrassMonkey銅猴子復興店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>10491台灣台北市中山區復興北路166號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="TheBrassMonkey銅猴子復興店10491台灣台北市中山區復興北路166號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
       
      const uluru7 = { lat: 25.053829740808045, lng:121.54381492416441 };
      const infowindow7 = new google.maps.InfoWindow({
        content: contentString7,
      });

      const marker7 = new google.maps.Marker({
        position: uluru7,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "銅猴子復興店",
        },
      });

      marker7.addListener("click", () => {
        infowindow7.open({
          anchor: marker7,
          map,
          shouldFocus: false,
        });
      });

      /*Marker8
      ===============================*/ 
      const contentString8 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">CinCinOsteria請請義大利餐廳(慶城店)</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>10491台灣台北市中山區復興北路166號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="CinCinOsteria請請義大利餐廳(慶城店)10491台灣台北市中山區復興北路166號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
      
      const uluru8 = { lat: 25.05299387271629, lng:121.54597142027671 };
      const infowindow8 = new google.maps.InfoWindow({
        content: contentString8,
      });

      const marker8 = new google.maps.Marker({
        position: uluru8,
        map,
      
        label: {
          color: 'black',
          fontWeight: 'bold',
          text:  "CinCinOsteria請請義大利餐廳(慶城店)",
        },
      });

      marker8.addListener("click", () => {
        infowindow8.open({
          anchor: marker8,
          map,
          shouldFocus: false,
        });
      });

      /*Marker9
      ===============================*/ 
      const contentString9 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">三個傻瓜印度蔬食南京店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>104台灣台北市中山區長春路318號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="三個傻瓜印度蔬食南京店104台灣台北市中山區長春路318號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
       
      const uluru9 = { lat: 25.054665603199695, lng:121.54070356161428 };
      const infowindow9 = new google.maps.InfoWindow({
        content: contentString9,
      });

      const marker9 = new google.maps.Marker({
        position: uluru9,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "三個傻瓜印度蔬食南京店",
        },
      });

      marker9.addListener("click", () => {
        infowindow9.open({
          anchor: marker9,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker10
      ===============================*/ 
      const contentString10 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">香米泰國料理復北店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>10491台灣台北市中山區復興北路174號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="香米泰國料理復北店10491台灣台北市中山區復興北路174號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
      
      const uluru10 = { lat: 25.054172219965434, lng:121.5438077876906 };
      const infowindow10 = new google.maps.InfoWindow({
        content: contentString10,
      });

      const marker10 = new google.maps.Marker({
        position: uluru10,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "香米泰國料理復北店",
        },
      });

      marker10.addListener("click", () => {
        infowindow10.open({
          anchor: marker10,
          map,
          shouldFocus: false,
        });
      });

      /*Marker11
      ===============================*/ 
      const contentString11 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">ＷonderBar&Lounge</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區復興北路183號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="ＷonderBar&Lounge105台灣台北市松山區復興北路183號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
      
      const uluru11 = { lat: 25.054172219965434, lng:121.54443542461881 };
      const infowindow11 = new google.maps.InfoWindow({
        content: contentString11,
      });

      const marker11 = new google.maps.Marker({
        position: uluru11,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "ＷonderBar&Lounge",
        },
      });

      marker11.addListener("click", () => {
        infowindow11.open({
          anchor: marker11,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker12
      ===============================*/ 
      const contentString12 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">HOOTERS美式餐廳</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區慶城街18號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="HOOTERS美式餐廳105台灣台北市松山區慶城街18號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
    
      
      const uluru12 = { lat: 25.05337523181257, lng:121.5454385708203 };
      const infowindow12 = new google.maps.InfoWindow({
        content: contentString12,
      });

      const marker12 = new google.maps.Marker({
        position: uluru12,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "HOOTERS美式餐廳",
        },
      });

      marker12.addListener("click", () => {
        infowindow12.open({
          anchor: marker12,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker13
      ===============================*/ 
      const contentString13 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">慶城海南雞飯</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區慶城街16巷8號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="慶城海南雞飯105台灣台北市松山區慶城街16巷8號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      
      const uluru13 = { lat: 25.053005894075923, lng:121.54553513034502 };
      const infowindow13 = new google.maps.InfoWindow({
        content: contentString13,
      });

      const marker13 = new google.maps.Marker({
        position: uluru13,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "慶城海南雞飯",
        },
      });

      marker13.addListener("click", () => {
        infowindow13.open({
          anchor: marker13,
          map,
          shouldFocus: false,
        });
      });

      /*Marker14
      ===============================*/ 
      const contentString14 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">ToBeSmoothie台北南京復興店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>104台灣台北市中山區遼寧街105巷48號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="ToBeSmoothie台北南京復興店104台灣台北市中山區遼寧街105巷48號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      
      
      const uluru14 = { lat: 25.05113002995152, lng:121.54338399870238 };
      const infowindow14 = new google.maps.InfoWindow({
        content: contentString14,
      });

      const marker14 = new google.maps.Marker({
        position: uluru14,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "ToBeSmoothie台北南京復興店",
        },
      });

      marker14.addListener("click", () => {
        infowindow14.open({
          anchor: marker14,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker15
      ===============================*/ 
      const contentString15 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">HAPPYBOBI波樂手搖茶飲</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>10488台灣台北市中山區遼寧街107-3號1樓</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="HAPPYBOBI波樂手搖茶飲10488台灣台北市中山區遼寧街107-3號1樓" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      
      
      const uluru15 = { lat: 25.051008535174546, lng:121.5419999788094 };
      const infowindow15 = new google.maps.InfoWindow({
        content: contentString15,
      });

      const marker15 = new google.maps.Marker({
        position: uluru15,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "HAPPYBOBI波樂手搖茶飲",
        },
      });

      marker15.addListener("click", () => {
        infowindow15.open({
          anchor: marker15,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker16
      ===============================*/ 
      const contentString16 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">蓮RENTaipei</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區南京東路三段256巷19號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="蓮RENTaipei105台灣台北市松山區南京東路三段256巷19號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      
      
      const uluru16 = { lat:   25.051202953547357, lng: 121.54466157555548 };
      const infowindow16 = new google.maps.InfoWindow({
        content: contentString16,
      });

      const marker16 = new google.maps.Marker({
        position: uluru16,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "蓮RENTaipei",
        },
      });

      marker16.addListener("click", () => {
        infowindow16.open({
          anchor: marker16,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker17
      ===============================*/ 
      const contentString17 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">長壽三好庵</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區南京東路三段256巷20弄1號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="長壽三好庵105台灣台北市松山區南京東路三段256巷20弄1號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      
     
      const uluru17 = { lat:  25.050921729051396, lng:  121.54481246221708 };
      const infowindow17 = new google.maps.InfoWindow({
        content: contentString17,
      });

      const marker17 = new google.maps.Marker({
        position: uluru17,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "長壽三好庵",
        },
      });

      marker17.addListener("click", () => {
        infowindow17.open({
          anchor: marker17,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker18
      ===============================*/ 
      const contentString18 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">犂園湯包館</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105台灣台北市松山區南京東路三段256巷24號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="犂園湯包館105台灣台北市松山區南京東路三段256巷24號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      
      
      const uluru18 = { lat:  25.050619777865535, lng:  121.54482250809578 };
      const infowindow18 = new google.maps.InfoWindow({
        content: contentString18,
      });

      const marker18 = new google.maps.Marker({
        position: uluru18,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "犂園湯包館",
        },
      });

      marker18.addListener("click", () => {
        infowindow18.open({
          anchor: marker18,
          map,
          shouldFocus: false,
        });
      });
      
      /*Marker19
      ===============================*/ 
      const contentString19 =
      '<div id="content" style="width:200px; height: 150px;">' +
        '<h2 id="head" style="margin:0px;">はま寿司HAMA壽司南京復興店</h2>' +
        '<div id="bodyContent" style="position: absolute; top:60px">' +
            '<p>105403台灣台北市松山區南京東路三段270號</p>'+
        '</div>' +
        '<form action="<%=request.getContextPath()%>/controller/FavServletController.do" METHOD="post" style="display:inline-block; position:absolute;top:120px;left:90px;">'+
			'<input type="hidden" value="confirm" name="action"></input>' +
            '<input type="hidden" value="はま寿司HAMA壽司南京復興店105403台灣台北市松山區南京東路三段270號" name="storeInfo"></input>'+
            '<button type="submit" style="background-color:white;border-radius: 100px; width:50px">確認</button>'+
        '</form>'+
      '</div>';
      
      
      const uluru19 = { lat:  25.051776406927004, lng:  121.54506927133252 };
      const infowindow19 = new google.maps.InfoWindow({
        content: contentString19,
      });

      const marker19 = new google.maps.Marker({
        position: uluru19,
        map,
       
        label: {
          color: 'black',
          fontWeight: 'bold',
          text: "はま寿司HAMA壽司南京復興店",
        },
      });

      marker19.addListener("click", () => {
        infowindow19.open({
          anchor: marker19,
          map,
          shouldFocus: false,
        });
      });
    }
    
  </script>
    
	<script async
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdTVkuwmGKvXkgQmAxlAa18fLWpl0dMfA&callback=initMap">
    </script>
    <script src="/okaeri/front-end/util/sessionMem.js" charset="UTF-8"></script>
    <script src="/okaeri/front-end/pack/JS/notify.js" charset="UTF-8"></script>
    <script src="/okaeri/chatroom/js/chat.js" charset="UTF-8"></script>
</body>

</html>