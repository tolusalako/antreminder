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
