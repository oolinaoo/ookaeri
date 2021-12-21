package web.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.login.entity.Login;
import web.login.service.LoginService;

@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@PostMapping(path = "mem", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean checkUser(@RequestBody Login login, HttpSession session) {
		session.setAttribute("memAcct", login.getMemAcct());
		boolean checkLogin = service.checkUser(login.getMemAcct(), login.getMemPwd());
		return checkLogin;
	}
	
	@PostMapping(path = "admin", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean checkAdmin(@RequestBody Login login, HttpSession session) {
		session.setAttribute("adminAcct", login.getAdminAcct());
		boolean checkLogin = service.checkAdmin(login.getAdminAcct(), login.getAdminPwd());
		return checkLogin;
	}

}
