package migros.one.logistic.controller;

import migros.one.logistic.dto.CourierInfo;
import migros.one.logistic.dto.CourierLogInfo;
import migros.one.logistic.dto.TravelDistance;
import migros.one.logistic.factory.CourierFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/logistic")
public class LogisticController {

    private final Logger logger = LoggerFactory.getLogger(LogisticController.class);
    private final CourierFactoryImpl courierFactoryImpl;

    public LogisticController(CourierFactoryImpl courierFactoryImpl) {
        this.courierFactoryImpl = courierFactoryImpl;
    }

    @GetMapping
    public ResponseEntity<String> getServiceCheck() {
        logger.info("Migros One Logistic Service is running");
        return new ResponseEntity<>("Service is running",HttpStatus.OK);
    }

    @PostMapping("/entries")
    public ResponseEntity<CourierLogInfo> postEntriesStoreZone(@RequestBody CourierInfo courierInfo) {

        logger.info(courierInfo.toString());

        CourierLogInfo courierLogInfo = this.courierFactoryImpl.loggingCourier(courierInfo);

        if(courierLogInfo == null) {
            HttpHeaders headers = new HttpHeaders();

            headers.add("MigrosOne-Info","No logging required");
            return new ResponseEntity<>(null,headers,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courierLogInfo,HttpStatus.OK);
    }

    @GetMapping("/travel/{courierId}")
    public ResponseEntity<TravelDistance> getTotalTravelDistance(@PathVariable("courierId") Integer courierId, @RequestParam("courierType") String courierType) {

        double totalDistance = this.courierFactoryImpl.calculateTotalDistance(courierId, courierType);
        TravelDistance travelDistance = TravelDistance.builder().courierId(courierId).totalDistance(totalDistance).build();

        return new ResponseEntity<>(travelDistance,HttpStatus.OK);
    }
}
