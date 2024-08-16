package binh.dc.accountservice.repository;

import binh.dc.accountservice.model.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<MessageDTO, Long> {
    List<MessageDTO> findByStatus(boolean status);
}
