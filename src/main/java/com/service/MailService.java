package com.service;

import com.entity.Letter;

/**
 * Created by Serg on 29.09.2018.
 */
public interface MailService {


    void sendMail(Letter letter);
}
