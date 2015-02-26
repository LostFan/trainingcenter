package finalproject.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorisationFilter implements Filter {
	
	private FilterConfig fc;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.fc = filterConfig;
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if (session.getAttribute("user") == null) {
			resp.sendRedirect(req.getContextPath()+"/login");
			return;
		}
		chain.doFilter(request, response);

	}
	
	public void destroy() {
		
	}
}