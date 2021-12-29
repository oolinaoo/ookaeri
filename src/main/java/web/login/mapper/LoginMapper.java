package web.login.mapper;

import org.apache.ibatis.annotations.Param;

import web.login.entity.Login;

public interface LoginMapper {
	
	Login checkUser(String memAcct);
	Login checkAdmin(String adminAcct);
	Login checkMail(String memAcct);
	Login getUser(String memAcct);
	Login getAdmin(String adminAcct);
	Login getUserPhoto(String memAcct);
	Integer updatePassword(@Param("memAcct") String memAcct, @Param("memPwd") String memPwd);
	
}
