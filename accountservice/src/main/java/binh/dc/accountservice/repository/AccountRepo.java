package binh.dc.accountservice.repository;

import binh.dc.accountservice.model.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountDTO, Long> {
}
