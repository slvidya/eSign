/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vidya
 */
@Path("/services")
public class ASPClientWebResource {

    @GET
    @Path("/downloadCertificate")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getX509Certificate() throws Exception {
        String certPath = "C:\\Users\\Vidya\\Desktop\\RD_cert.cer";
//        Response.ok().header(MediaType, this);
    return Response.ok(certPath, MediaType.APPLICATION_OCTET_STREAM).header("content-disposition", "attachment; filename = RD_cert.cer" ).build();
    }
}
