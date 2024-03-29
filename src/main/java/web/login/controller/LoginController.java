package web.login.controller;

import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import redis.clients.jedis.Jedis;
import util.JavaMail.src.xxx.MailService;
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
		System.out.println("登入" + session.getAttribute("memAcct"));
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
	
	@GetMapping("logout")
	@ResponseBody
	public boolean logout(HttpSession session) {
		session.invalidate();
		System.out.println("已登出");
		return true;
	}
	
	@GetMapping("getMemSession")
	@ResponseBody
	public Login getMemSession(HttpSession session) {
		String memAcct = (String)session.getAttribute("memAcct");
		Login member = service.getUser(memAcct);
		return member;
	}
	
	@GetMapping("getAdminSession")
	@ResponseBody
	public Login getAdminSession(HttpSession session) {
		String adminAcct = (String)session.getAttribute("adminAcct");
		Login admin = service.getAdmin(adminAcct);
		return admin;
	}
	
	@GetMapping("getUserPhoto")
	@ResponseBody
	public Login getUserPhoto(HttpSession session) {
		String memAcct = (String)session.getAttribute("memAcct");
		Login mem = service.getUserPhoto(memAcct);
		return mem;
	}
	
	@PostMapping("sendMail")
	@ResponseBody
	public boolean sendMail(@RequestBody Login login) {
		
		String to = login.getMemEmail();
	    String subject = "okaeri住戶密碼更改";
	    String memAcct = login.getMemAcct();
	    String controller = "login/checkToken";
	    MailService mailService = new MailService();
	    mailService.sendMail(to, subject, controller, memAcct);
	    
	    return true;
	}
	
	@GetMapping("checkToken")
	public RedirectView checkToken(String token, HttpSession session) {
		Jedis jedis = new Jedis("localhost", 6379);
		if (token != null && token != "") {
			System.out.println("in checkToken controller");
			String memAcct = jedis.get(token);
			session.setAttribute("memAcct", memAcct);
			System.out.println(session.getAttribute("memAcct"));
		} 
		jedis.close();
		
		return new RedirectView("/okaeri/login/forget-pwd.html");
	}
	
	@PostMapping("updatePassword")
	@ResponseBody
	public boolean updatePassword(@RequestBody Login login, HttpSession session) {
		
		String memAcct = (String)session.getAttribute("memAcct");
		String memPwd = login.getMemPwd();
		String pwdReg = "^[a-zA-Z0-9]{8,25}$";
		if(memPwd.matches(pwdReg)) {
			boolean update = service.updatePassword(memAcct, memPwd);
			return update;
		}
		
		return false;
	}
	
}
