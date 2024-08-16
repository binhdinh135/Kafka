package binh.dc.statisticservice.repository;

import binh.dc.statisticservice.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepo extends JpaRepository<Statistic, Integer> {
}
