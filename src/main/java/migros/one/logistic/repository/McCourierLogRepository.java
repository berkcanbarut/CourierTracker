package migros.one.logistic.repository;

import migros.one.logistic.model.MotorcycleCourierLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface McCourierLogRepository extends JpaRepository<MotorcycleCourierLog,Integer> {

    List<MotorcycleCourierLog> findByCourierId(Integer courierId, Sort sort);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM MOTORCYLE_LOG WHERE time between ?1 and ?2 and store_id = ?3 and courier_id = ?4)", nativeQuery = true)
    boolean existsBeforeTimeWithOneMinuteOffset(Instant startTime, Instant endTime, Integer storeId, Integer courierId);
}
