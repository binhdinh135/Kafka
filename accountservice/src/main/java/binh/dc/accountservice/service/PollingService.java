package binh.dc.accountservice.service;

import binh.dc.accountservice.model.MessageDTO;
import binh.dc.accountservice.model.StatisticDTO;
import binh.dc.accountservice.repository.MessageRepo;
import binh.dc.accountservice.repository.StatisticRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PollingService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final MessageRepo messageRepo;

    private final StatisticRepo statisticRepo;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedDelay = 1000)
    public void producer() {
        List<MessageDTO> messageDTOs = messageRepo.findByStatus(false);

        for (MessageDTO messageDTO : messageDTOs) {
            kafkaTemplate.send("notification_test1", messageDTO).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            logger.info("Sent message=[" + messageDTO.getId() +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            messageDTO.setStatus(true);// success
                            messageRepo.save(messageDTO);
                        } else {
                            logger.error("Unable to send message=[" +
                                    messageDTO.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );
//            .addCallback(new KafkaSendCallback<String, Object>() {
//                @Override
//                public void onFailure(KafkaProducerException ex) {
//                    // handle fail, save db event failed
//                    logger.error("FAIL ", ex);
//                }
//
//                @Override
//                public void onSuccess(SendResult<String, Object> result) {
//                    // handle success
//                    logger.error("SUCCESS ");
//
//                    messageDTO.setStatus(true);// success
//                    messageRepo.save(messageDTO);
//                }
//            });
        }

        List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(false);
        for (StatisticDTO statisticDTO : statisticDTOs) {
            kafkaTemplate.send("notification_test1", statisticDTO).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            logger.info("Sent message=[" + statisticDTO.getId() +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            statisticDTO.setStatus(true);// success
                            statisticRepo.save(statisticDTO);
                        } else {
                            logger.error("Unable to send message=[" +
                                    statisticDTO.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void delete() {
        List<MessageDTO> messageDTOs = messageRepo.findByStatus(true);
        messageRepo.deleteAllInBatch(messageDTOs);

        List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(true);
        statisticRepo.deleteAllInBatch(statisticDTOs);
    }

}
