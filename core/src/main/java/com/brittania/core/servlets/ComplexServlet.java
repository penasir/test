package com.brittania.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true,service = Servlet.class,property = {
		"sling.servlet.paths=/bin/cookie/servletoutput","sling.servlet.methods=GET"
})
public class ComplexServlet extends SlingSafeMethodsServlet{
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {			
			String pathVal = request.getParameter("path");
			String textVal = request.getParameter("text");
			StringBuilder sb = new StringBuilder();
			Session session = request.getResourceResolver().adaptTo(Session.class);
			QueryManager queryManager;
			try {
				queryManager = session.getWorkspace().getQueryManager();
				Query query = queryManager.createQuery("/jcr:root"+pathVal+"//*[jcr:contains(., '"+textVal+"')] order by @jcr:score", Query.XPATH);
				QueryResult result = query.execute();	
				NodeIterator nodeItr = result.getNodes();
				while(nodeItr.hasNext()) {
					Node node = nodeItr.nextNode();
					String path = node.getPath();
					sb.append(path+"<br>");
				}
				response.setContentType("text/html");
				response.getWriter().write(sb.toString());				
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
