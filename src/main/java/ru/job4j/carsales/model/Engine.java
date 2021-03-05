package ru.job4j.carsales.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "j_engine")
@Data
@EqualsAndHashCode()
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@ToString(of = {"id", "name", "power", "fuelType", "volume"})
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "engine_id")
    private Long id;
    @NonNull
    @Column(unique = true) // м.б. constraint unique? могут ли быть разные двигатели с одним название?
    private String name;
    @NonNull
    private Integer power;
    @NonNull
    private String fuelType;
    @NonNull
    private Float volume;
}
