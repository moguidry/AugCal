/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aug.site.cms.ServiceImpls;

import com.aug.site.cms.Constants;
import com.aug.site.cms.Models.User;
import java.io.IOException;
import java.security.Principal;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizeFilter implements ContainerRequestFilter {

	@Inject
	public AuthorizeFilter() {
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		final String token = requestContext.getHeaderString(Constants.X_Auth_Token);
		final User user;
		try {
			ClientConfig cc = new ClientConfig().register(new JacksonFeature());
			Client client = ClientBuilder.newClient(cc);

			Response resp = client.target("http://parich.us/api/user/auth")
					.request(MediaType.APPLICATION_JSON)
					.header(Constants.X_Auth_Token, token)
					.get();

			String new_token = resp.getHeaderString(Constants.X_Auth_Token);
			requestContext.getHeaders().putSingle(Constants.X_Auth_Token, new_token);

			user = resp.readEntity(User.class);

			requestContext.setSecurityContext(new SecurityContext() {

				@Override
				public boolean isSecure() {
					return false;
				}

				@Override
				public String getAuthenticationScheme() {
					return "AUG";
				}

				@Override
				public Principal getUserPrincipal() {
					if (user == null) {
						return null;
					}

					return new Principal() {
						@Override
						public String getName() {
							return user.getId();
						}
					};
				}

				@Override
				public boolean isUserInRole(String role) {
					try {
						return user.getRoles().contains(role);
					} catch (Exception ex) {
						return false;
					}
				}
			});

		} catch (Exception ex) {

			requestContext.setSecurityContext(new SecurityContext() {

				@Override
				public Principal getUserPrincipal() {
					return null;
				}

				@Override
				public boolean isUserInRole(String role) {
					return false;
				}

				@Override
				public boolean isSecure() {
					return false;
				}

				@Override
				public String getAuthenticationScheme() {
					return "AUG";
				}
			});

			requestContext.getHeaders().putSingle(Constants.X_Auth_Token, "");
		}
	}
}
