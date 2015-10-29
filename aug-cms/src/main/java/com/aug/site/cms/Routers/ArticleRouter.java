package com.aug.site.cms.Routers;

import com.aug.site.cms.Constants;
import com.aug.site.cal.Models.Article;
import com.aug.site.cms.ServiceInterfaces.ArticleRepositoryService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author chris
 */
@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ArticleRouter {

	ArticleRepositoryService repo;

	@Inject
	public ArticleRouter(ArticleRepositoryService repo) {
		this.repo = repo;
	}

	@GET
	public Response getArticleList(@QueryParam("s") @DefaultValue("0") int start,
			@QueryParam("o") @DefaultValue("10") int count) {

		return Response.ok()
				.entity(repo.getArticleList(start, count))
				.build();
	}

	@GET
	@Path("/hidden")
	public Response getUnpublishedArticleList(@Context SecurityContext ctx,
			@HeaderParam(Constants.X_Auth_Token) String token,
			@QueryParam("s") @DefaultValue("0") int start,
			@QueryParam("o") @DefaultValue("10") int count) {

		if (!ctx.isUserInRole("admin") && !ctx.isUserInRole("contributor")) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.ok()
				.entity(repo.getHiddenArticleList(start, count))
				.header(Constants.X_Auth_Token, token)
				.build();
	}

	@GET
	@Path("{id}")
	public Response getArticle(@PathParam("id") int id) {

		return Response.ok()
				.entity(repo.getArticleById(id))
				.build();
	}

	@GET
	@Path("{id}/pretty")
	@Produces({MediaType.TEXT_HTML})
	public Response getArticlePretty(@PathParam("id") int id) {
		return Response.ok()
				.entity(repo.getArticleById(id).getPretty())
				.build();
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateArticle(@Context SecurityContext ctx,
			@HeaderParam(Constants.X_Auth_Token) String token,
			@PathParam("id") int id, Article article) {

		if (!ctx.isUserInRole("admin") && !ctx.isUserInRole("contributor")) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.ok()
				.entity(repo.updateArticle(ctx.getUserPrincipal().getName(), id, article))
				.header(Constants.X_Auth_Token, token)
				.build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addArticle(@Context SecurityContext ctx,
			@HeaderParam(Constants.X_Auth_Token) String token,
			Article article) {

		if (!ctx.isUserInRole("admin") && !ctx.isUserInRole("contributor")) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.ok()
				.entity(repo.createArticle(ctx.getUserPrincipal().getName(), article))
				.header(Constants.X_Auth_Token, token)
				.build();
	}

	@PUT
	@Path("{id}/show")
	public Response publishArticle(@Context SecurityContext ctx,
			@HeaderParam(Constants.X_Auth_Token) String token,
			@PathParam("id") int id) {

		if (!ctx.isUserInRole("admin") && !ctx.isUserInRole("contributor")) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.ok()
				.entity(repo.publishArticle(ctx.getUserPrincipal().getName(), id))
				.header(Constants.X_Auth_Token, token)
				.build();
	}

	@PUT
	@Path("{id}/hide")
	public Response unpublishArticle(@Context SecurityContext ctx,
			@HeaderParam(Constants.X_Auth_Token) String token,
			@PathParam("id") int id) {

		if (!ctx.isUserInRole("admin") && !ctx.isUserInRole("contributor")) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.ok()
				.entity(repo.unpublishArticle(ctx.getUserPrincipal().getName(), id))
				.header(Constants.X_Auth_Token, token)
				.build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteArticle(@Context SecurityContext ctx,
			@HeaderParam(Constants.X_Auth_Token) String token,
			@PathParam("id") int id) {

		if (!ctx.isUserInRole("admin") && !ctx.isUserInRole("contributor")) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.ok()
				.entity(repo.unpublishArticle(ctx.getUserPrincipal().getName(), id))
				.header(Constants.X_Auth_Token, token)
				.build();
	}
}
