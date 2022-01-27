package com.brittania.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderModel {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ValueMapValue
	private String logoimagepath;
	
	@ValueMapValue
	private String logolinkurl;
	
	@ScriptVariable
    private Resource resource;
	
	@ChildResource
	private List<Resource> headerlinks;
	
	private List<LinkDetails> headerLinkDetails = new ArrayList<>();

	
	@PostConstruct
	public void init() {
		logger.info(""+resource);
		for(int i=0; i<headerlinks.size();i++) {
			Resource res = headerlinks.get(i);
			ValueMap resVm = res.getValueMap();
			String linktitle = resVm.get("linktitle", "");
			String linkpath = resVm.get("linkpath", "");
			LinkDetails details = new LinkDetails();
			details.setLinkPath(linkpath);
			details.setLinkTitle(linktitle);
			headerLinkDetails.add(details);
		}
	}
	
	public String getLogoImagePath() {
		return logoimagepath;
	}
	
	public String getLogoLinkURL() {
		return logolinkurl;
	}
	
	public List<LinkDetails> getHeaderLinks(){
		return headerLinkDetails;
	}
	
}
