package ru.job4j.carsales.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "j_engine")
@Data
@EqualsAndHashCode()
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "engine_id")
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    private Integer power;

    @NonNull
    private String fuelType;

    @NonNull
    private Float volume;

    @Override
    public String toString() {
        return name.toUpperCase() + " : " + power + " л/с, " + fuelType + ", V =" + volume + " л.";
    }
}