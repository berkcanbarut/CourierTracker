package migros.one.logistic.repository;

import migros.one.logistic.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoresRepository extends JpaRepository<Store,Integer> {
}
