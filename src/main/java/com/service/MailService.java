package com.service;

import com.entity.Letter;

/**
 * The {@code MailService} interface is responsible for sending mail
 */
public interface MailService {

    /**
     *Responsible for sending mail to the user which bought the ticket.
     *
     * @param letter the {@code Letter} instance encapsulating all the data for sending mail.
     * */
    void sendMail(Letter letter);
}
