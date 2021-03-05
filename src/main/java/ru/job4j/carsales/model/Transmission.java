package ru.job4j.carsales.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "j_transmission",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"automatic", "drive_type"})})
@Data
@NoArgsConstructor
@EqualsAndHashCode()
@RequiredArgsConstructor(staticName = "of")
@ToString(of = {"id", "automatic", "driveType"})
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "tm_id")
    private Long id;
    @NonNull
    private Boolean automatic;
    @NonNull
    @Column (name = "drive_type")
    private String driveType;
}
