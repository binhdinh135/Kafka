package binh.dc.accountservice.controller;

import binh.dc.accountservice.model.AccountDTO;
import binh.dc.accountservice.model.MessageDTO;
import binh.dc.accountservice.model.StatisticDTO;
import binh.dc.accountservice.repository.AccountRepo;
import binh.dc.accountservice.repository.MessageRepo;
import binh.dc.accountservice.repository.StatisticRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    StatisticRepo statisticRepo;

    @PostMapping("/new")
    public AccountDTO create(@RequestBody AccountDTO account) {
        StatisticDTO stat = new StatisticDTO();
        stat.setMessage(account.getEmail());
        stat.setCreatedDate(new Date());
        stat.setStatus(false);
        statisticRepo.save(stat);

        // send notificaiton
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(account.getEmail());
        messageDTO.setToName(account.getName());
        messageDTO.setSubject("Welcome to JMaster.io");
        messageDTO.setContent("JMaster is online learning platform.");
        messageDTO.setStatus(false);
        messageRepo.save(messageDTO);
        accountRepo.save(account);

//        accountRepo.save(account);
//        messageRepo.save(messageDTO);
//        statisticRepo.save(stat);
//
//        for (int i = 0; i < 100; i++)
//            kafkaTemplate.send("notification", messageDTO).whenComplete(
//                    (result, ex) -> {
//                        if (ex == null) {
//                            System.out.println("Sent message=[" + messageDTO.getId() +
//                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
//                            messageDTO.setStatus(true);// success
//                            messageRepo.save(messageDTO);
//                        } else {
//                            System.out.println("Unable to send message=[" +
//                                    messageDTO.getId() + "] due to : " + ex.getMessage());
//                        }
//                    }
//            );
//        .addCallback(new KafkaSendCallback<String, Object>() {
//				@Override
//				public void onFailure(KafkaProducerException ex) {
//					// handle fail, save db event failed
//					ex.printStackTrace();
//				}
//				@Override
//				public void onSuccess(SendResult<String, Object> result) {
//					// handle success
//					System.out.println(result.getRecordMetadata().partition());
//				}
//			});
        for (int i = 0; i < 1000; i++) {
            kafkaTemplate.send("notification_test1", messageDTO).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            System.out.println("Sent message=[" + messageDTO.getId() +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            messageDTO.setStatus(true);// success
                            messageRepo.save(messageDTO);
                        } else {
                            System.out.println("Unable to send message=[" +
                                    messageDTO.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );;
        }
        for (int i = 0; i < 1000; i++) {
            stat.setMessage(account.getEmail() + i);
            kafkaTemplate.send("statistic_test1", stat).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            System.out.println("Sent message=[" + stat.getId() +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            stat.setStatus(true);// success
                            statisticRepo.save(stat);
                        } else {
                            System.out.println("Unable to send message=[" +
                                    stat.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );;
        }
        System.out.println("Message sent to topics.");
        return account;
    }
}
