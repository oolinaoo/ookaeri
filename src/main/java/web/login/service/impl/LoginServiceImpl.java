package web.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.login.entity.Login;
import web.login.mapper.LoginMapper;
import web.login.service.LoginService;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper mapper;

	@Override
	public boolean checkUser(String memAcct, String memPwd) {
		Login user = mapper.checkUser(memAcct);
		if (user != null) {
			if (user.getMemAcct().equals(memAcct) && user.getMemPwd().equals(memPwd)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkAdmin(String adminAcct, String adminPwd) {
		Login admin = mapper.checkAdmin(adminAcct);
		if (admin != null) {
			if (admin.getAdminAcct().equals(adminAcct) && admin.getAdminPwd().equals(adminPwd)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Login getUser(String memAcct) {
		Login mem = mapper.getUser(memAcct);
		if (mem != null) {
			return mem;
		}
		return null;
	}

	@Override
	public Login getAdmin(String adminAcct) {
		Login admin = mapper.getAdmin(adminAcct);
		if (admin != null) {
			return admin;
		}
		return null;
	}

}
