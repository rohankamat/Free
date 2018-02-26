
package com.bi.endpoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
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
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import com.bi.upload.FileUtility;
import com.bi.upload.Load;
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
	public Response login(@FormParam("loginId") String loginId, @FormParam("password") String password,
			@Context HttpServletRequest requestContext) throws Exception {
		Session session = new Session();
		String response = session.login(loginId, password);
		if ("session".equals(response.substring(2, 9))) {
			ResponseBuilder rBuild = Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*");
			return rBuild.type(MediaType.APPLICATION_JSON).entity(response).build();
		} else {
			ResponseBuilder rBuild = Response.status(Response.Status.FORBIDDEN).header("Access-Control-Allow-Origin",
					"*");
			return rBuild.type(MediaType.APPLICATION_JSON).entity(response).build();
		}

	}

	@POST
	@Path("/upload")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("tablename") String tablename, @FormParam("sessionId") String password,
			List<Attachment> file, @Multipart("via") String via, @Multipart("url") String url) throws Exception {

		File dataFile = null;
		InputStream stream = null;
		try {
			FileUtility fileUtility = new FileUtility();
			if ("url".equalsIgnoreCase(via)) {
				dataFile = fileUtility.readFilefromURL(url);
			} else if ("server".equalsIgnoreCase(via)) {
				;
				String fileext = "";
				try {
					fileext = (url.split("\\.(?=[^\\.]+$)"))[1];
				} catch (Exception exception) {
					fileext = "xlsx";
				}
				stream = new FileInputStream(url);
				dataFile = fileUtility.readFileFromResource(stream, "cal", fileext);
			} else {
				dataFile = fileUtility.readAttachedExcelFile(file);

			}
		} catch (Exception exception) {

		}

		Load csvload = new Load();

		String response = csvload.CsvLoad(dataFile, tablename);
		if ("session".equals(response.substring(2, 9))) {
			ResponseBuilder rBuild = Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*");
			return rBuild.type(MediaType.APPLICATION_JSON).entity(response).build();
		} else {
			ResponseBuilder rBuild = Response.status(Response.Status.FORBIDDEN).header("Access-Control-Allow-Origin",
					"*");
			return rBuild.type(MediaType.APPLICATION_JSON).entity(response).build();
		}

	}

}
