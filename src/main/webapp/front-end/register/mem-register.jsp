<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="web.mem.controller.*"%>
<%@ page import="web.mem.entity.*"%>
<%@ page import="java.util.*"%>

<% 
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>
<%-- 
	List<String> addrChoose = (ArrayList<String>) request.getAttribute("addrChoose");
--%>
<%-- 
	List<String> famMemAryList = (ArrayList<String>) request.getAttribute("famMemAryList");
--%>

<%
	Map<String, String> sexMap = new HashMap<String, String>();
	sexMap.put("male", "男");
	sexMap.put("female", "女");
	sexMap.put("other", "其他");
	request.setAttribute("sexMap", sexMap);
	
	List<String> buildList = new ArrayList<String>();
	buildList.add("A");
	buildList.add("B");
	buildList.add("C");
	request.setAttribute("buildList", buildList);
	
	List<String> floorList = new ArrayList<String>();
	floorList.add("1");
	floorList.add("2");
	floorList.add("3");
	floorList.add("4");
	floorList.add("5");
	request.setAttribute("floorList", floorList);
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>mem-register</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="datetimepicker/jquery.datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="css/register.css"/>
</head>

<body>
    <div class="register_head">
        <div class="container">
            <div class="title">
                <h1>sign up now!</h1>
            </div>
        </div>
    </div>

    <div class="register_content">
        <div class="container">
        	<span class="error">${(errorMsgs.other eq "")? "": errorMsgs.other}</span>
            <form method="post" action="MemberServlet.do" name="register_form" enctype="multipart/form-data">
                <div class="row">
                    <label for="acct">住戶帳號:</label>
                    <span class="error">${(errorMsgs.memAcct eq "")? "": errorMsgs.memAcct}</span>
                    <div class="clear"></div>
                    <input value="<%= (memberVO == null)? "ginatest" : memberVO.getMemAcct() %>"
                    type="text" name="memAcct" id="acct" placeholder="輸入住戶帳號" required>
                </div>
                <div class="row">
                    <label for="pwd">密碼:</label>
                    <span class="error">${(errorMsgs.memPwd eq "")? "": errorMsgs.memPwd}</span>
                    <div class="clear"></div>
                    <input value="<%= (memberVO == null)? "passwordTest" : memberVO.getMemPwd() %>" 
                    type="text" name="memPwd" id="pwd" placeholder="輸入密碼" required>
                </div>
                <div class="row">
                    <label for="name">姓名:</label>
                    <span class="error">${(errorMsgs.memName eq "")? "": errorMsgs.memName}</span>
                    <div class="clear"></div>
                    <input value="<%= (memberVO == null)? "王小明" : memberVO.getMemName() %>" 
                    type="text" name="memName" id="name" placeholder="輸入姓名" required>
                </div>
                <div class="row">
                    <label for="f_date1">生日:</label>
                    <span class="error">${(errorMsgs.memBirthday eq "")? "": errorMsgs.memBirthday}</span>
                    <div class="clear"></div>
                    <input name="memBirthday" id="f_date1" type="text" placeholder="請選擇日期">
                </div>
                <div class="row">
                    <label for="idNo">身分證字號:</label>
                    <span class="error">${(errorMsgs.memId eq "")? "": errorMsgs.memId}</span>
                    <div class="clear"></div>
                    <input value="<%= (memberVO == null)? "B200000000" : memberVO.getMemId() %>"
                    type="text" name="memId" id="idNo" placeholder="輸入身分證字號" required>
                </div>
                <div class="row_other">
                    <label for="sex" class="sex">性別:</label>
                    <span class="error">${(errorMsgs.memSex eq "")? "": errorMsgs.memSex}</span>
                    <div class="clear"></div>
                    <div class="other_block">
        	            <input type="radio" id="male" name="memSex" value="${sexMap.male}" ${(memberVO.memSex eq sexMap.male)? 'checked':'' }>
                        <label for="male">${sexMap.male}</label>
                    	<input type="radio" id="female" name="memSex" value="${sexMap.female}" ${(memberVO.memSex eq sexMap.female)? 'checked':'' }>
                    	<label for="female">${sexMap.female}</label>
                    	<input type="radio" id="other" name="memSex" value="${sexMap.other}" ${(memberVO.memSex eq sexMap.other)? 'checked':'' }>
                    	<label for="other">${sexMap.other}</label>
                    </div>
                </div>
                <div class="row">
                    <label for="tel">聯絡電話(手機):</label>
                    <span class="error">${(errorMsgs.memPhone eq "")? "": errorMsgs.memPhone}</span>
                    <div class="clear"></div>
                    <input value="<%= (memberVO == null)? "0900000000" : memberVO.getMemPhone() %>"
                    type="tel" name="memPhone" id="tel" placeholder="格式：0912345678" required>
                </div>
                <div class="row">
                    <label for="email">電子郵件:</label>
                    <span class="error">${(errorMsgs.memEmail eq "")? "": errorMsgs.memEmail}</span>
                    <div class="clear"></div>
                    <input value="<%= (memberVO == null)? "xxxx@gmail.com" : memberVO.getMemEmail() %>"
                    type="email" name="memEmail" id="email" placeholder="輸入電子郵件" required>
                </div>
                <div class="row">
                    <label for="addr">地址:</label>
                    <span class="error">${(errorMsgs.memAddr eq "")? "": errorMsgs.memAddr}</span>
                    <div class="clear"></div>
                    <div class="other_block">
                        <select name="memBuild" id="build">
                        	<option value="--">--</option>
                        	<c:forEach var="build" items="${buildList}">
                        		<option value="${build}" ${(addrChoose[0] eq build) ? 'selected':'' }>${build}</option>
                        	</c:forEach>
                        </select>
                        <span>棟</span>
                        <select name="memFloor" id="floor">
                        	<option value="--">--</option>
                        	<c:forEach var="floor" items="${floorList}">
                        		<option value="${floor}" ${(addrChoose[1] eq floor) ? 'selected':'' }>${floor}</option>
                        	</c:forEach>
                        </select>
                        <span>樓</span>
                        <select name="memRoom" id="room">
                            <option value="--">--</option>
                        	<c:forEach var="floor" items="${floorList}">
                        		<option value="${floor}" ${(addrChoose[2] eq floor) ? 'selected':'' }>${floor}</option>
                        	</c:forEach>
                        </select>
                        <span>房</span>
                    </div>
                </div>
                <div class="row">
                    <label for="fam">住戶家人姓名:</label>
                    <span class="error">${(errorMsgs.famName eq "")? "": errorMsgs.famName}</span>
                    <div class="clear"></div>
                    <div class="famMemList">
                    	<c:if test="${not empty famMemAryList}">
                    		<c:forEach var="famMem" items="${famMemAryList}">
		                        <div class="famMem_block">
		                            <div class="plus_minus_btn">
		                                <span class="minus">-</span>
		                                <span class="plus">+</span>
		                        	</div>
		                        	<input type="text" name="famMem" value="${famMem}" style="width:80px;margin:0;">
		                        </div>
	                        </c:forEach>
                    	</c:if>
                    	<c:if test="${empty famMemAryList}">
	                        <div class="famMem_block">
	                            <div class="plus_minus_btn">
	                                <span class="minus">-</span>
	                                <span class="plus">+</span>
	                        	</div>
	                        	<input type="text" name="famMem" value="" style="width:80px;margin:0;">
	                        </div>
                    	</c:if> 
                    </div>
                </div>
                <div class="row_other">
                    <label for="photo" class="sex">照片:</label>
                    <span class="error">${(errorMsgs.memPhoto eq "")? "": errorMsgs.memPhoto}</span>
                    <div class="clear"></div>
                    <div class="profilePic_preview">
                        <div class="mem_uploadPic"></div>
                    </div>
                    <div class="other_block">
                        <div class="memUploadPic_btn_block">
                            <input type="file" name="memPhoto" id="memUploadPic_file" style="margin-top: 5px;">
                        </div>
                    </div>
                </div>
                <div class="submit">
                    <input type="hidden" name="action" value="register">
                    <input type="submit" value="sign up">
                </div>
            </form>
        </div>
    </div>
    <script src="datetimepicker/jquery.js"></script>
    <script src="datetimepicker/jquery.datetimepicker.full.js"></script>
    <script src="js/register.js"></script>
    <script>
		<%
			java.sql.Date memBirthday = null;
			try{
				memBirthday = memberVO.getMemBirthday();
			} catch(Exception e){
				memBirthday = new java.sql.Date(System.currentTimeMillis());
			}
		%>
	    $.datetimepicker.setLocale('zh'); // kr ko ja(日文) en(英文)
	    $('#f_date1').datetimepicker({
	        theme: '',          //theme: 'dark', //只有兩個顏色黑跟白可以用
	        timepicker: false,   //timepicker: false, //可以選幾點幾分幾秒
	        step: 1,            //step: 60 (這是timepicker的預設間隔60分鐘) //只能到間隔1分鐘，不能以秒間隔
	        format: 'Y-m-d',
	        value: "<%=memBirthday%>",  //起始日期 
	        //disabledDates:    ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含  //這不是寫死的，可以從資料庫撈出來就可以變成是動態的
	        //startDate:	        '2017/07/10',  // 起始日
	        // minDate:           '-1970-01-01', // 去除今日(不含)之前 //只能選到今天以後的日子
	        //maxDate:           //'+1970-01-01'  // 去除今日(不含)之後 //只能選到今日以前的（例如選生日）//寫+2021/11/11會無效，老師在下面有寫萬用的方法
	
	    });
	
	
	    //  2.以下為某一天之後的日期無法選擇
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
    </script>
</body>

</html>