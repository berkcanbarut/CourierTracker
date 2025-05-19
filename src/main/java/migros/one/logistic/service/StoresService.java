package migros.one.logistic.service;

import migros.one.logistic.model.Store;
import migros.one.logistic.repository.StoresRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoresService {

    private final StoresRepository storesRepository;

    public StoresService(StoresRepository storesRepository) {
        this.storesRepository = storesRepository;
    }

    public Store save(Store store) {
        store.setId(null);
        Store saveStore = storesRepository.save(store);
        return saveStore;
    }

    public List<Store> getAllStore() {
        return storesRepository.findAll(Sort.by("name"));
    }
}
