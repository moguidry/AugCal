/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aug.site.cms.Factories;

import org.apache.log4j.Logger;
import org.glassfish.hk2.api.Factory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author chris
 */
public class DBFactory implements Factory<Session> {

	static final Logger log = Logger.getLogger(DBFactory.class);

	static final SessionFactory fctry;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();

			StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());
			fctry = configuration.buildSessionFactory(ssrb.build());

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Override
	public void dispose(Session instance) {
		instance.disconnect();
	}

	@Override
	public Session provide() {
		Session ret = fctry.openSession();

		return ret;
	}

}
