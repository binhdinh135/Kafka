package binh.dc.statisticservice.service;

import binh.dc.statisticservice.entity.Statistic;
import binh.dc.statisticservice.repository.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticRepo statisticRepo;

    @KafkaListener(id = "statisticGroup", topics = "statistic_test1")
    public void listen(Statistic statistic) {
        logger.info("Received: " + statistic.getMessage());
        statisticRepo.save(statistic);
        System.out.println("Received: " + statistic.getMessage());
//		throw new RuntimeException("failed");
    }

//    @KafkaListener(id = "dltGroup3", topics = "statistic.DLT")
//    public void dltListen(String in) {
//        logger.info("Received from DLT: ");
//        System.out.println(in);
//    }

}
