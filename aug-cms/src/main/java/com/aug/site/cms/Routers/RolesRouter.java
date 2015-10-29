/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aug.site.cms.Routers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author chris
 */
@Path("/roles")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RolesRouter {

	@GET
	public Response getRolesList() {
		return Response.ok(new String[]{"admin", "contributor"}).build();
	}
}
