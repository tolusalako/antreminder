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
package net.csthings.antreminder.services.email;

import java.util.Map;

import net.csthings.antreminder.services.email.exception.EmailException;

public interface EmailService {
    /**
     * Sends email to the recipients
     * @param subject
     * @param text
     * @param to
     * @param cc
     * @param bcc
     * @returns the id if the email sent
     */
    String sendEmail(String subject, String text, String[] to, String[] cc, String[] bcc) throws EmailException;

    /**
     * Retrieves all the successful/failed emails by id
     * @param id
     * @param emails
     * @return
     */
    Map<String, Boolean> getEmailReport(String id, String[] emails) throws Exception;

    String sendHtmlEmail(String subject, String text, String[] to, String[] cc, String[] bcc) throws EmailException;
}
