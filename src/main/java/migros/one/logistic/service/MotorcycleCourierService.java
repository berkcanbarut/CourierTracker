package migros.one.logistic.service;

import migros.one.logistic.dto.CourierInfo;
import migros.one.logistic.dto.CourierLogInfo;
import migros.one.logistic.model.MotorcycleCourierLog;
import migros.one.logistic.model.Store;
import migros.one.logistic.repository.McCourierLogRepository;
import migros.one.logistic.strategy.LoggerStrategy;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

@Service("motorcycleCourier")
public class MotorcycleCourierService implements LoggerStrategy<CourierLogInfo> {

    private final Logger logger = LoggerFactory.getLogger(MotorcycleCourierService.class);

    private final StoresService storesService;
    private final CoordinateService coordinateService;
    private final McCourierLogRepository courierLogRepository;
    private final ModelMapper modelMapper;

    public MotorcycleCourierService(StoresService storesService, CoordinateService coordinateService, McCourierLogRepository courierLogRepository, ModelMapper modelMapper) {
        this.storesService = storesService;
        this.coordinateService = coordinateService;
        this.courierLogRepository = courierLogRepository;
        this.modelMapper = modelMapper;
    }

    public CourierLogInfo save(CourierInfo courierInfo, Store store) {
        MotorcycleCourierLog motorcycleCourierLog = modelMapper.map(courierInfo,MotorcycleCourierLog.class);
        motorcycleCourierLog.setId(null);
        motorcycleCourierLog.setStore(store);

        motorcycleCourierLog = courierLogRepository.save(motorcycleCourierLog);

        return modelMapper.map(motorcycleCourierLog, CourierLogInfo.class);
    }

    @Override
    public Double getTotalTravelDistance(Integer courierId) {

        List<MotorcycleCourierLog> courierLogs = courierLogRepository.findByCourierId(courierId, Sort.by(Sort.Direction.ASC,"time"));
        // Kurye id ait herhangi bir log kaydi yoksa veya bir kez log kaydi varsa
        if (courierLogs == null || courierLogs.size() < 2) return 0.0;

        //Loglanan koordinatlari time siralamasina gore uzaklik hesaplanmasi
        return IntStream.range(1, courierLogs.size())
                .mapToDouble(i -> {
                    double lastLat = courierLogs.get(i -1).getLat().doubleValue();
                    double lastLng = courierLogs.get(i -1).getLng().doubleValue();

                    double currentLat = courierLogs.get(i).getLat().doubleValue();
                    double currentLng = courierLogs.get(i).getLng().doubleValue();

                    return coordinateService.calculateDistance(lastLat,lastLng,currentLat,currentLng) / 1000.0;

                })
                .sum();
    }

    @Override
    public CourierLogInfo loggingEntriesStoreZone(CourierInfo courierInfo) {
        List<Store> storeList = storesService.getAllStore();
        CourierLogInfo courierLogInfo = null;

        for(Store store : storeList) {
            // Koordinatlar arasi uzaklik kontrolu
            double distance = coordinateService.calculateDistance(
                    courierInfo.getLat().doubleValue(),courierInfo.getLng().doubleValue(),
                    store.getLat().doubleValue(),store.getLng().doubleValue());

            // 100 metre capli dairesel alan kontrolu
            if(distance <= 100.00) {
                // Ayni magaza icin 1 dakika icerisinde loglama kontrolu
                Instant startTime = courierInfo.getTime().minus(Duration.ofMinutes(1));
                boolean isExist = courierLogRepository.existsBeforeTimeWithOneMinuteOffset(startTime,courierInfo.getTime(),store.getId(), courierInfo.getCourierId());

                if(!isExist) {
                    // Kurye ve magaza loglamasi
                    courierLogInfo = this.save(courierInfo,store);
                }
                break;
            }
        }
        return courierLogInfo;
    }
}
