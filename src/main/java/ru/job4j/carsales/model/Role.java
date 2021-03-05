package ru.job4j.carsales.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "j_role")
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode()
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "role_name", unique = true, nullable = false, updatable = false)
    private String name;
}