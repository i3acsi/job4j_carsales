package ru.job4j.carsales.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.job4j.carsales.dto.ModelDto;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "j_car")
@Data
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        System.out.println(this.vin.equals(car.vin));
        System.out.println(this.model.getMark().getName().equals(car.model.getMark().getName()));
        System.out.println(this.model.getName().equals(car.model.getName()));
        return this.vin.equals(car.vin)
                && this.model.getMark().getName().equals(car.model.getMark().getName())
                && this.model.getName().equals(car.model.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, model, created);
    }
}