package net.csthings.antreminder.entity.dto;

import lombok.Data;
import net.csthings.common.dto.CommonDto;

@Data
public class ReminderJsonDto implements CommonDto {
    // public AccountReminderDto reminder;
    public String status;
}
