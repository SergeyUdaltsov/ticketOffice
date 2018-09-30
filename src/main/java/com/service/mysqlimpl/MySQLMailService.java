package com.service.mysqlimpl;

import com.entity.Letter;
import com.service.MailService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import static com.utils.UtilConstants.*;


/**
 * Created by Serg on 29.09.2018.
 */
public class MySQLMailService implements MailService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLMailService.class);

    @Override
    public void sendMail(Letter letter) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(MAIL_HOST);
            email.setSmtpPort(SMTP_PORT);
            email.setAuthenticator(new DefaultAuthenticator(MAIL_USER_NAME, MAIL_PASSWORD));
            email.setSSLOnConnect(true);
            email.setFrom(MAIL_FROM);
            email.setSubject(letter.getSubject());
            email.setMsg(letter.getText());
            email.addTo(letter.getAddress());
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
