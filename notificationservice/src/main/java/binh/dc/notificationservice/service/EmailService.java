package binh.dc.notificationservice.service;

import binh.dc.notificationservice.model.MessageDTO;

public interface EmailService {
    void sendEmail(MessageDTO messageDTO);
}
