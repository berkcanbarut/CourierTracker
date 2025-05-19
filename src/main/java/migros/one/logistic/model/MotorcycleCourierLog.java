package migros.one.logistic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "motorcyleLog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotorcycleCourierLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "courierId", nullable = false)
    private Integer courierId;

    @Column(name = "lat", nullable = false, precision = 10, scale = 7)
    private BigDecimal lat;

    @Column(name = "lng", nullable = false, precision = 10, scale = 7)
    private BigDecimal lng;

    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant time;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "store_id", referencedColumnName = "id", nullable=false)
    private Store store;
}
