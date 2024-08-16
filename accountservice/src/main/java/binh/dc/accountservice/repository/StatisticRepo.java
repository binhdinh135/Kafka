package binh.dc.accountservice.repository;

import binh.dc.accountservice.model.StatisticDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepo extends JpaRepository<StatisticDTO, Integer> {
    List<StatisticDTO> findByStatus(boolean status);
}
