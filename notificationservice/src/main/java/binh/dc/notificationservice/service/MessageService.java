package binh.dc.notificationservice.service;

import binh.dc.notificationservice.model.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private EmailService emailService;

    @KafkaListener(id = "notificationGroup", topics = "notification_test1")
    public void listen(MessageDTO messageDTO) {
        MessageDTO msg = new MessageDTO();
        msg.setToName(messageDTO.getToName());
        msg.setTo(messageDTO.getTo());
        msg.setSubject(msg.getSubject());
        msg.setContent(messageDTO.getContent());
        System.out.println("Received message from: " + msg.getTo());
        //        emailService.sendEmail(messageDTO);
    }
}
