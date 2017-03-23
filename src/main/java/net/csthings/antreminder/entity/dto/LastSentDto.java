/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package net.csthings.antreminder.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.csthings.antreminder.entity.dto.LastSentDto.LastSentPK;

/*
 * Created on: Aug 10, 2016
 * 
 * @author Toluwanimi Salako Last edited: Aug 10, 2016
 * 
 * @purpose -
 */

@Entity
@IdClass(LastSentPK.class)
@Table(name = LastSentDto.TABLE_NAME)
public class LastSentDto implements Serializable {
    public static final String TABLE_NAME = "last_sent";

    @Id
    @OneToOne
    @JoinColumn(name = "accountId", insertable = false, updatable = false)
    private AccountDto account;

    @Id
    @OneToOne
    @JoinColumns({ @JoinColumn(name = "reminderId", insertable = false, updatable = false),
            @JoinColumn(name = "status", insertable = false, updatable = false) })
    private ReminderDto reminder;

    private UUID accountId;
    private String reminderId;
    // @Column(insertable = false, updatable = false)
    private String status;

    private Date lastSent;

    public Date getLastSent() {
        return lastSent;
    }

    public void setLastSent(Date lastSent) {
        this.lastSent = lastSent;
    }

    @Embeddable
    public static class LastSentPK implements Serializable {
        @Id
        @Column(columnDefinition = "BINARY(16)")
        private UUID accountId;
        @Id
        private String reminderId;
        @Id
        private String status;
    }

}
