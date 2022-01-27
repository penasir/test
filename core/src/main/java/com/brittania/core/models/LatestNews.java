package com.brittania.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = {SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LatestNews {
	
	@ScriptVariable
	private Logger log;
	
	@ScriptVariable
	private ResourceResolver resolver;
	
	@ScriptVariable
	private PageManager pageManager;
	
	private List<String> pageList = new ArrayList<>();
	
	@PostConstruct
	protected void init() {
		log.info(resolver+"");
	}
	
	
	public List<String> getPages(){
		Page page = pageManager.getPage("/content/we-retail/language-masters/en");
		Session session = resolver.adaptTo(Session.class);
		try {
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery("SELECT * FROM [cq:Page] AS s WHERE ISDESCENDANTNODE(s,'"+page.getPath()+"')", Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodes = result.getNodes();
			while(nodes.hasNext()) {
				Node next = nodes.nextNode();
				pageList.add(next.getPath());
			}			
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}

		return pageList;
	}
	

}
