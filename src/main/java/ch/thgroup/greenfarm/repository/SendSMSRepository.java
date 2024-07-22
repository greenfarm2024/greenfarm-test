package ch.thgroup.greenfarm.repository;

import ch.thgroup.greenfarm.model.SendSMSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendSMSRepository extends JpaRepository<SendSMSEntity, Long> {
}
