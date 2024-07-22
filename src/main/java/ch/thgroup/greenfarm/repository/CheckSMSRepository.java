package ch.thgroup.greenfarm.repository;

import ch.thgroup.greenfarm.model.CheckSMSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckSMSRepository extends JpaRepository<CheckSMSEntity, Long> {

    // Custom query methods can be added here

    Optional<CheckSMSEntity> findByMessageId(UUID messageId);

    // Example of a custom query to find records by status
    List<CheckSMSEntity> findByStatus(String status);
}
