package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = LinkedRemindersDto.TABLE_NAME)
public class LinkedRemindersDto {
    public static final String TABLE_NAME = "linked_reminders";

    @Id
    @PrimaryKeyJoinColumn
    private UUID accountId;
    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "reminderId", insertable = false, updatable = false),
            @JoinColumn(name = "status", insertable = false, updatable = false) })
    private ReminderDto reminder;
}
