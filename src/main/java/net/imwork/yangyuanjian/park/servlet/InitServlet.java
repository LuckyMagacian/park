package net.imwork.yangyuanjian.park.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.imwork.yangyuanjian.common.assist.LogFactory;


public class InitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig config) throws ServletException {
		LogFactory.init();
	}
}
