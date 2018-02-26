
package com.bi.endpoints;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.bi.user.Session;

/**
 * @author Rohan Kamat/Prasad VS
 */

@Path("/")
public class Controller {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello";
	}

	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("loginId") String loginId,@FormParam("password") String password ,@Context HttpServletRequest requestContext)throws Exception
	{	
		Session session=new Session();
		String response=session.login(loginId, password);
		if ("session".equals(response.substring(2, 9))) {
			ResponseBuilder rBuild = Response.status(Response.Status.OK)
					.header("Access-Control-Allow-Origin", "*");
			return rBuild.type(MediaType.APPLICATION_JSON).entity(response)
					.build();
		}else{
			ResponseBuilder rBuild = Response.status(Response.Status.FORBIDDEN)
					.header("Access-Control-Allow-Origin", "*");
			return rBuild.type(MediaType.APPLICATION_JSON).entity(response).build();
		}
				
	}
	
	
	
}







