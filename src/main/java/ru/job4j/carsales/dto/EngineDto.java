package ru.job4j.carsales.dto;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "j_engine")
@Data
@EqualsAndHashCode()
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@ToString(of = {"id", "name"})
public class EngineDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "engine_id")
    private Long id;
    @NonNull
    @Column(unique = true)
    private String name;
//    @NonNull
//    private Integer power;
//    @NonNull
//    private String fuelType;
//    @NonNull
//    private String ecoStandard;
//    @NonNull
//    private Float volume;
}