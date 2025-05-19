package migros.one.logistic.factory;

import migros.one.logistic.dto.CourierInfo;
import migros.one.logistic.dto.CourierLogInfo;
import migros.one.logistic.error.BadArgumentException;
import migros.one.logistic.strategy.LoggerStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CourierFactoryImpl implements CourierFactory<CourierLogInfo>{

    private final Map<String, LoggerStrategy> loggerStrategyMap;

    public CourierFactoryImpl(Map<String, LoggerStrategy> loggerStrategyMap) {
        this.loggerStrategyMap = loggerStrategyMap;
    }

    private LoggerStrategy getLoggerStrategy(String courierType){
        LoggerStrategy loggerStrategy = this.loggerStrategyMap.get(courierType);
        if(loggerStrategy == null) {
            throw new BadArgumentException("Invalid courierType :" + courierType,400);
        }
        return  loggerStrategy;
    }

    @Override
    public Double calculateTotalDistance(Integer courierId, String courierType) {
        LoggerStrategy loggerStrategy = this.getLoggerStrategy(courierType);

        return loggerStrategy.getTotalTravelDistance(courierId);
    }

    @Override
    public CourierLogInfo loggingCourier(CourierInfo courierInfo) {
        LoggerStrategy loggerStrategy = this.getLoggerStrategy(courierInfo.getCourierType());

        return (CourierLogInfo) loggerStrategy.loggingEntriesStoreZone(courierInfo);

    }
}
