package ru.job4j.carsales.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "j_model",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "mark_id"})})
@Data
@NoArgsConstructor
@EqualsAndHashCode()
@RequiredArgsConstructor(staticName = "of")
@ToString(of = {"id", "name", "mark", "engines"})
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "model_id")
    private Long id;
    @NonNull
    private String name;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "mark_id")
    private Mark mark;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Engine> engines;
}
