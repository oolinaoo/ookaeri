package web.login.mapper;

import web.login.entity.Login;

public interface LoginMapper {
	
	Login checkUser(String memAcct);
	Login checkAdmin(String adminAcct);

}
