package web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.Core;

@WebFilter("/LoginFilter")
public class LoginFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;

	public LoginFilter() {
	}

	public void destroy() {
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = request.getSession(); 
		String path = request.getServletPath();
		boolean loggedIn = session != null && session.getAttribute("memAcct") != null;

		if (path == "/member") { // && /shop
			// check if logged in
			if (!loggedIn) {
				// because our login is a modal, can i just send a boolean to the front end
				// and pop out the modal? add class--> yes, but
//				session.setAttribute("location", path); // save html location
				Core core = new Core();
				core.setSuccess(false); // how do i send this to the front end?
//				response.sendRedirect("/login.html");
			} else {
//				request.getSession().getAttribute("memberId"); 
				chain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
