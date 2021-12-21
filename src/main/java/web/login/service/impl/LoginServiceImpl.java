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
			if (user.getMemAcct().trim().equals(memAcct) && user.getMemPwd().trim().equals(memPwd)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkAdmin(String adminAcct, String adminPwd) {
		Login admin = mapper.checkAdmin(adminAcct);
		if (admin != null) {
			if (admin.getAdminAcct().trim().equals(adminAcct) && admin.getAdminPwd().trim().equals(adminPwd)) {
				return true;
			}
		}
		return false;
	}

}
