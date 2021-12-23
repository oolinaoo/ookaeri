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

@WebFilter("/AdminFilter")
public class AdminFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;
	
	private FilterConfig config;

	public void destroy() {
		config = null;
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = req.getSession(); 
		boolean loggedIn = session != null && session.getAttribute("adminAcct") != null;

			// check if logged in
			if (!loggedIn) {
//				session.setAttribute("location", path); // save html location
				res.sendRedirect(req.getContextPath() + "/login/login.html");
				System.out.println("admin請登入");
				return;
			} else {
//				req.getSession().getAttribute("memAcct"); 
				System.out.println("admin successful");
				chain.doFilter(req, res);
			}
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
