package web.login.service;

public interface LoginService {
	
	boolean checkUser(String memAcct, String memPwd);
	boolean checkAdmin(String adminAcct, String adminPwd);

}
