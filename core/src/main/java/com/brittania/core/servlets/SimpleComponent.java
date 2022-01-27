package com.brittania.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate=true,service = Servlet.class,property = {
	"sling.servlet.paths=/bin/biscuit/messagefromservlet","sling.servlet.methods=GET"	
})
public class SimpleComponent extends SlingSafeMethodsServlet{
	
	private static final long serialVersionUID = 7284009243295126284L;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Activate
	public void activate() {
		logger.info("INSIDE aCTIVATE mETHOD");
	}
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write("I am from Get method");
	}
	
	@Deactivate
	public void deactivate() {
		logger.info("INSIDE deaCTIVATE mETHOD");
	}

}
