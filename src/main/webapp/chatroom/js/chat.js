
//將修改的資料傳送到Servlet
$(function () {
	//聊天室頁面開關
	$("button.msgicon").on("click", function() {
		if(!$("div.chatroom").hasClass("view")){
			$("div.chatroom").addClass("view");
		}else{
			$("div.chatroom").removeClass("view");
		}
	});
	//取得帳號
	getAcct();
	
});
function getAcct() {
	var dataUrl =
	 	 "/okaeri/chat/adminAcct";
		var xhr = new XMLHttpRequest();
		xhr.open("GET", dataUrl);
		xhr.send();
		xhr.onload = function () {
		adminAcct = JSON.parse(this.responseText);
		$("span.acct").text(adminAcct);
		}
		xhr.onloadend = function(){
		let adminAcct =$("span.acct").html();
		console.log("adminAcct="+adminAcct);
		//連接websocket
		connect();
		}
}
function connect() {
		/*var dataUrl =
	 	 "/okaeri/chat/adminAcct";
		var xhr = new XMLHttpRequest();
		xhr.open("GET", dataUrl);
		xhr.send();
		xhr.onload = function () {
		adminAcct = JSON.parse(this.responseText);
		$("span.acct").text(adminAcct);
		}*/
		// create a websocket
		let adminAcct =$("span.acct").html();
		console.log("adminAcct="+adminAcct);
		let endPointURL = "wss://localhost:8443/okaeri/FriendWS/"+adminAcct;
		console.log("endPointURL="+endPointURL);
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
			console.log(endPointURL);
			
			
		};

		webSocket.onmessage = function(event) {
			var adminAcct =$("span.acct").html();
			console.log("進入接收訊息");
			var jsonObj = JSON.parse(event.data);
			if ("open" === jsonObj.type) {
				console.log("進入open");
				console.log(jsonObj);
				refreshFriendList(jsonObj);
			} else if ("history" === jsonObj.type) {
				//$(".msgbox").empty();
				//var ul = document.createElement('ul');
				//ul.id = "area";
				//$(".msgbox").append('<ul id=area></ul>');
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				//let adminAcct =$("span.acct").html();
				var messages = JSON.parse(jsonObj.message);
				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					var li = document.createElement('li');
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					historyData.sender === adminAcct ? li.className += 'me' : li.className += 'friend';
					li.innerHTML = showMsg;
					$("ul#area").append(li);
					//ul.appendChild(li);
				}
				//messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("chat" === jsonObj.type) {
				//let adminAcct =$("span.acct").html();
				var li = document.createElement('li');
				jsonObj.sender === adminAcct ? li.className += 'me' : li.className += 'friend';
				li.innerHTML = jsonObj.message;
				console.log(li);
				document.getElementById("area").append(li);
				//messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("close" === jsonObj.type) {
				refreshFriendList(jsonObj);
			}
			
		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	$(".send").on("click",function(){
		sendMessage();
	});
	
	function sendMessage() {
		var inputMessage =$(".textbox");
		var friend = $(".statusOutput").text();
		var message = $(".textbox").val();
		let self = $("span.acct").html();
		console.log("message="+message);
		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else if (friend === "") {
			alert("Choose a friend");
		} else {
			var jsonObj = {
				"type" : "chat",
				"sender" : self,
				"receiver" : friend,
				"message" : message
			};
			webSocket.send(JSON.stringify(jsonObj));
			$(".textbox").val("");
			$(".textbox").focus();
		}
	}
	
	// 有好友上線或離線就更新列表
	function refreshFriendList(jsonObj) {
		let adminAcct =$("span.acct").html();
		var friends = jsonObj.users;
		console.log("進入更新列表");
		console.log(friends);
		var row = $("div.friendlist");
		row.empty();
		for (var i = 0; i < friends.length; i++) {
			console.log(friends[i]) ;
			if (friends[i] == adminAcct) {
				 console.log("等於自己"); 
			}else{
				$("div.friendlist").prepend('<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' >' + friends[i] + '</div>');
				//row.prepend("<div>"+friends[i]+"</div><br>")	;
			}
			addListener();
		}
	}
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
	function addListener() {
		let adminAcct =$("span.acct").html();
		console.log("抓取歷史紀錄");
		//var container = document.getElementById("row");
		//var container = $("div.friendlist");
		$("ul#area").empty();
		$("div.friendlist").on("click", function(e){
			var friend = e.target.textContent;
			let self = adminAcct;
			updateFriendName(friend);
			console.log("self="+self);
			console.log("friend="+friend);
			var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : friend,
					"message" : ""
				};
			webSocket.send(JSON.stringify(jsonObj));
		});
		
		//container.addEventListener("click", function(e) {
			//var friend = e.srcElement.textContent;
			//updateFriendName(friend);
			//var jsonObj = {
					//"type" : "history",
					//"sender" : self,
					//"receiver" : friend,
					//"message" : ""
				//};
			//webSocket.send(JSON.stringify(jsonObj));
		//});
	}
	
	function disconnect() {
		webSocket.close();
		
	}
	
	function updateFriendName(name) {
		console.log("進入更新名子");
		$(".statusOutput").text(name);
		$("ul#area").empty();
	}
	