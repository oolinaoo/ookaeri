package web.login.mapper;

import web.login.entity.Login;

public interface LoginMapper {
	
	Login checkUser(String memAcct);
	Login checkAdmin(String adminAcct);
	Login checkMail(String memAcct);
	Login getUser(String memAcct);
	Login getAdmin(String adminAcct);

}
