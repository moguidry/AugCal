package com.aug.site.cms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 *
 * @author chris
 */
public class UserResourceConfig extends ResourceConfig {

	public UserResourceConfig() {
		register(new UserBinder());
		register(RolesAllowedDynamicFeature.class);
		packages(true, "com.aug.site.cms");
	}

}
