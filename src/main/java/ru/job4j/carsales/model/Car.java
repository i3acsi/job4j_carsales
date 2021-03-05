package ru.job4j.carsales.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.job4j.carsales.dto.ModelDto;

import javax.persistence.*;
import java.util.Date;

// У машины предполагается уникальный вин. Модель-марка, дата создания - могут быть устанолены только один раз

@Entity
@Table(name = "j_car")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"vin", "model", "created"})
@RequiredArgsConstructor(staticName = "of")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @NonNull
    @Column(unique = true, name = "car_vin")
    private String vin;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "model_id", updatable = false)
    private ModelDto model;


    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY", timezone = "Asia/Novosibirsk")
    @Column(updatable = false)
    private Date created;
}