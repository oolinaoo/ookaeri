package web.login.service;

import web.login.entity.Login;

public interface LoginService {
	
	boolean checkUser(String memAcct, String memPwd);
	boolean checkAdmin(String adminAcct, String adminPwd);
	Login getUser(String memAcct);
	Login getAdmin(String adminAcct);

}
