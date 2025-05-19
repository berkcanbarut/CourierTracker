package migros.one.logistic.factory;

import migros.one.logistic.dto.CourierInfo;

public interface CourierFactory<T> {

    Double calculateTotalDistance(Integer courierId, String courierType);
    T loggingCourier(CourierInfo courierInfo);
}
