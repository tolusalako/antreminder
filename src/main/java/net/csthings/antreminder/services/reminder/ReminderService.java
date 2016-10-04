package net.csthings.antreminder.services.reminder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.csthings.antreminder.entity.dto.AccountReminderDto;
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
    EmptyResultDto add(AccountReminderDto reminderJson);
    // TODO: make sure you check session. get user from session data

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    ResultDto<AccountReminderDto> get(@QueryParam("accountid") UUID accountId, @QueryParam("status") String status);

    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
    ResultDto<List<AccountReminderDto>> getAll(@QueryParam("accountid") UUID accountId);

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EmptyResultDto delete(AccountReminderDto reminder);
}
