package net.csthings.antreminder.services.reminder;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;

@Singleton
@Path(ReminderService.PATH)
public interface ReminderService extends Serializable {
    public static final String PATH = "/reminder";
    public static final int PORT = 2424;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    EmptyResultDto add(UUID accountId, ReminderDto reminder);
    // TODO: make sure you check session. get user from session data

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    ResultDto<Collection<ReminderDto>> get(@QueryParam("accountid") UUID accountId,
            @QueryParam("status") String status);
    //
    // @POST
    // @Path("/delete")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public EmptyResultDto delete(AccountReminderDto reminder);
}
