package ch.thgroup.greenfarm.repository;

import ch.thgroup.greenfarm.model.SMSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SMSRepository extends JpaRepository<SMSEntity, Long> {
    // Custom method to find all records where checkCount is less than 3
    List<SMSEntity> findByCheckCountLessThan(Short value);
}
