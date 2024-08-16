package binh.dc.accountservice;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	NewTopic notification() {
		// topic name, partition numbers, replication number
		return new NewTopic("notification_test1", 2, (short) 3);
	}

	@Bean
	NewTopic statistic() {
		// topic name, partition numbers, replication number
		return new NewTopic("statistic_test1", 1, (short) 3);
	}

}
