package migros.one.logistic.strategy;

import migros.one.logistic.dto.CourierInfo;

public interface LoggerStrategy<T> {
    Double getTotalTravelDistance(Integer courierId);
    T loggingEntriesStoreZone(CourierInfo courierInfo);
}
