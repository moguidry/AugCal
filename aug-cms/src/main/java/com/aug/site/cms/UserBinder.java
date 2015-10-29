package com.aug.site.cms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.aug.site.cms.Factories.DBFactory;
import com.aug.site.cms.ServiceImpls.HibernateArticleRepositoryService;
import com.aug.site.cms.ServiceInterfaces.ArticleRepositoryService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.Session;

/**
 *
 * @author chris
 */
public class UserBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(HibernateArticleRepositoryService.class).to(ArticleRepositoryService.class);
		bindFactory(DBFactory.class).to(Session.class);
	}

}
